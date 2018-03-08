package org.kpi.kurs.web.data_analyze;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.kpi.kurs.KursApplicationTests;
import org.kpi.kurs.web.rawData.RawDataEntity;
import org.kpi.kurs.web.rawData.RawDataRepository;
import org.kpi.kurs.web.rawData.RawDataSource;
import org.kpi.kurs.web.rawData.SourcesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.is;

public class HistoricalDataAnalyzeTest extends KursApplicationTests {

    @Autowired
    DataAnalyzeRepository dataAnalyzeRepository;

    /**
     * Tests checks composite primary key that should update value in db and reject all duplicates
     */
    @Test
    public void dataManagerGetByDate_LatestDate_Test(){
        LocalDate localDateNow = LocalDate.of(2018,02,25);  // date should be EXCLUDED in result
        LocalDate localDateBefore = localDateNow.minusDays(6); // date should be included in result //
        Iterable<RawDataEntity> all = rawDataRepository.findByBaseDateBetweenAndSourceId(Date.valueOf(localDateBefore), Date.valueOf(localDateNow), SourcesEnum.GISMETEO);
        HistoricalDataManager dataManager = new HistoricalDataManager(all);
        HistoricalDataAnalyze dataAnalyze = new HistoricalDataAnalyze(dataManager);
        dataAnalyze.calculateDifs();
        List<TempDiffsDto> comparisonResult = dataAnalyze.getComparisonResult();

        double randomMaxTemp = new Random().nextDouble();
        for(TempDiffsDto o: comparisonResult){
            o.setMaxTempDiff(randomMaxTemp);
            dataAnalyzeRepository.save(o);
        }
        List<TempDiffsDto> actualInsertRes = dataAnalyzeRepository.findAllByTempDiffIdentitySourceAndTempDiffIdentityBaselineDate(SourcesEnum.GISMETEO, Date.valueOf(localDateNow.minusDays(1)));
        Assert.assertThat(actualInsertRes.containsAll(comparisonResult), is(true));
    }
}
