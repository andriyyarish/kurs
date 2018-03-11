package org.kpi.kurs.web.data_analyze;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kpi.kurs.dao.preAnalyzedData.DataAnalyzeRepository;
import org.kpi.kurs.dao.preAnalyzedData.HistoricalReliabilityEntity;
import org.kpi.kurs.dao.preAnalyzedData.TempDiffsEntity;
import org.kpi.kurs.web.rawData.SourcesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.*;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

/**
 * Calculates reliability ratings by Source for each backward deepness.
 * It accept base date to select pre analyzed raw data using DataAnalyzeRepository
 */
@Component
public class HistoricalReliabalitiAnalyze {
    private static final Logger logger = LogManager.getLogger(HistoricalReliabalitiAnalyze.class);
    //TODO This property should be injected and synchronized within all classes
    private static final int BACKWARD_DEEPNESS = 5;

    @Autowired
    private DataAnalyzeRepository dataAnalyzeRepository;

    private Date date;
    private final List<TempDiffsEntity> mergedInputData;
    private final Map<Integer, Double> sumMaxByBackwardDeepness;
    private final Map<Integer, Double> sumMinByBackwardDeepness;

    public HistoricalReliabalitiAnalyze(Date date) {
        this.date = date;
        mergedInputData = new ArrayList<>();
        sumMaxByBackwardDeepness = new HashMap<>();
        sumMinByBackwardDeepness = new HashMap<>();
    }

    public HistoricalReliabalitiAnalyze() {
        mergedInputData = new ArrayList<>();
        sumMaxByBackwardDeepness = new HashMap<>();
        sumMinByBackwardDeepness = new HashMap<>();
    }

    public List<HistoricalReliabilityEntity> calculateReliabilityRates(){
        initInputData();
        initSumForBackwardDeepness();
        List<HistoricalReliabilityEntity> res = new ArrayList<>();
        for (int i = 1; i <= BACKWARD_DEEPNESS; i++){
            for(SourcesEnum s: SourcesEnum.values()) {
                HistoricalReliabilityEntity ent = new HistoricalReliabilityEntity(s, date, i);
                ent.setReliabilityRatio(calculateRateForSourceByDeepness(s, i));
                res.add(ent);
            }
        }
        return res;
    }

    private void initInputData(){
        for(SourcesEnum s: SourcesEnum.values()){
            //TODO add some assertion here
            List<TempDiffsEntity> tempDiffsList = dataAnalyzeRepository.findAllByTempDiffIdentitySourceAndTempDiffIdentityBaselineDate(s, date);
            mergedInputData.addAll(tempDiffsList);
        }
    }
    
    private void initSumForBackwardDeepness(){
        for (int i=1; i<=BACKWARD_DEEPNESS; i++ ){
            ToDoubleFunction<TempDiffsEntity> maxTFunc = el -> el.getMaxTempDiff();
            ToDoubleFunction<TempDiffsEntity> minTFunc = el -> el.getMinTempDiff();
            sumMaxByBackwardDeepness.put(i, getSumByBackwardDeepness(mergedInputData, i, maxTFunc ));
            sumMinByBackwardDeepness.put(i, getSumByBackwardDeepness(mergedInputData, i, minTFunc));
        }
    }

    private Double getSumByBackwardDeepness(List<TempDiffsEntity> list, int backwardDeep, ToDoubleFunction<TempDiffsEntity> func){
        return list.stream()
                .filter(el -> el.getBackwardDeepness() == backwardDeep )
                .collect(Collectors.summingDouble(func));
    }

    private double calculateRateForSourceByDeepness(SourcesEnum source, int deepness){
        double maxMinSumForSources = sumMaxByBackwardDeepness.get(deepness) + sumMinByBackwardDeepness.get(deepness);
        double rate = 0;
        try {
            TempDiffsEntity tempDiffsEntity = mergedInputData.stream()
                    .filter(el -> el.getSource().equals(source) && el.getBackwardDeepness()==deepness)
                    .findAny().get();
            rate = (tempDiffsEntity.getMaxTempDiff() + tempDiffsEntity.getMinTempDiff()) / maxMinSumForSources;
        }catch (NoSuchElementException ne){
            logger.warn("Historical Temp diffs is absent for source -> " + source);
        }finally {
            return rate;
        }
    }

    public Date getDate() {
        return date;
    }

    public HistoricalReliabalitiAnalyze setDate(Date date) {
        this.date = date;
        return this;
    }
}
