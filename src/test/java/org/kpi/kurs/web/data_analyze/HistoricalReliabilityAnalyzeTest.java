package org.kpi.kurs.web.data_analyze;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kpi.kurs.KursApplicationTests;
import org.kpi.kurs.dao.preAnalyzedData.HistoricalReliabilityEntity;
import org.kpi.kurs.dao.preAnalyzedData.HistoricalReliabilityRepository;
import org.kpi.kurs.dao.preAnalyzedData.TempDiffsEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class HistoricalReliabilityAnalyzeTest extends KursApplicationTests {
    //TODO this value should be injected as parametr
    public static final int BACKWARD_DEEPNESS = 5;
    public static final Logger logger = LogManager.getLogger(HistoricalReliabilityAnalyzeTest.class);

    @Autowired
    HistoricalReliabalitiAnalyze historicalDataReliabilityAnalyze;
    @Autowired
    HistoricalReliabilityRepository historicalReliabilityRepository;

    List<TempDiffsEntity> testData;

    @BeforeClass
    public static void prepareData(){

    }

    @Test
    public void checkThatRatioSumByDeepnessIsCloseToOne(){
        historicalDataReliabilityAnalyze.setDate(Date.valueOf(localDateNowForTest.plusDays(1)));
        List<HistoricalReliabilityEntity> historicalReliabilityEntities = historicalDataReliabilityAnalyze.calculateReliabilityRates();

        for (int i=1; i<=BACKWARD_DEEPNESS; i++){
            double sumByDeepness = historicalReliabilityEntities.stream()
                    .filter(el -> el.getBackwardDeepness()==1)
                    .collect(Collectors.summingDouble(el -> el.getReliabilityRatio()));
            logger.debug("Backward deepness:-> " + i + ". Sum of reliability ratings is:-> " + sumByDeepness);
            Assert.assertThat(sumByDeepness, Matchers.closeTo(1,0.05));
        }

        historicalReliabilityRepository.save(historicalReliabilityEntities);
    }
}
