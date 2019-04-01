package org.kpi.kurs.web.data_analyze;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.kpi.kurs.KursApplicationTests;
import org.kpi.kurs.dao.preAnalyzedData.DataAnalyzeRepository;
import org.kpi.kurs.dao.preAnalyzedData.TempDiffsEntity;
import org.kpi.kurs.dao.rawData.RawDataEntity;
import org.kpi.kurs.web.rawData.SourcesEnum;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.is;

public class HistoricalDataAnalyzeTest extends KursApplicationTests {
    public static final Logger logger = LogManager.getLogger(HistoricalDataAnalyzeTest.class);
    @Autowired
    DataAnalyzeRepository dataAnalyzeRepository;

    /**
     * Tests also checks composite primary key that should update value in db and reject all duplicates
     */
    @Test
    public void getByDateCountDiffAndSaveTest(){
        LocalDate localDateNow = LocalDate.of(2019,03,15);
        LocalDate localDateBefore = localDateNow.minusDays(5);
        Iterable<RawDataEntity> queryResultsForPeriod = rawDataRepository
                .findByBaseDateBetweenAndSourceId(Date.valueOf(localDateBefore), Date.valueOf(localDateNow), SourcesEnum.GISMETEO);
        HistoricalDataManager dataManager = new HistoricalDataManager(queryResultsForPeriod);
        HistoricalDataAnalyze dataAnalyze = new HistoricalDataAnalyze(dataManager);
        dataAnalyze.calculateDifs();
        List<TempDiffsEntity> comparisonResult = dataAnalyze.getComparisonResult();

        for(TempDiffsEntity o: comparisonResult){
            dataAnalyzeRepository.save(o);
        }
        List<TempDiffsEntity> actualInsertRes = dataAnalyzeRepository
                .findAllByTempDiffIdentitySourceAndTempDiffIdentityBaselineDate(SourcesEnum.GISMETEO, Date.valueOf(localDateNow));

        Assertions.assertThat(comparisonResult).containsAll(actualInsertRes);
    }

    /**
     * 02/19 - starts from
     * 02/22/is missed
     */
    @Test
    public void dataManagerContainsPeriodWithGaps(){
        LocalDate localDateNow = LocalDate.of(2019,02,24); // date should be included in result
        LocalDate localDateBefore = localDateNow.minusDays(4); // date should be EXCLUDED in result
        Iterable<RawDataEntity> all = rawDataRepository.findByBaseDateBetweenAndSourceId(Date.valueOf(localDateBefore), Date.valueOf(localDateNow), SourcesEnum.GISMETEO);
        HistoricalDataManager dataManager = new HistoricalDataManager(all, Date.valueOf(localDateNow), Date.valueOf(localDateBefore));
        HistoricalDataAnalyze dataAnalyze = new HistoricalDataAnalyze(dataManager);
        dataAnalyze.calculateDifs();
        List<TempDiffsEntity> comparisonResult = dataAnalyze.getComparisonResult();
    }
}
