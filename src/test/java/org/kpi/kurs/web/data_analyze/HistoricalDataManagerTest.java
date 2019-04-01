package org.kpi.kurs.web.data_analyze;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kpi.kurs.dao.rawData.RawDataEntity;
import org.kpi.kurs.dao.rawData.RawDataRepository;
import org.kpi.kurs.web.rawData.RawDataNormalizationService;
import org.kpi.kurs.web.rawData.RawDataSource;
import org.kpi.kurs.web.rawData.SourcesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Profile("default")
public class HistoricalDataManagerTest {
    public static final double ERROR_MARKER = 777.0;

    @Autowired
    @Qualifier("MeteoProg")
    RawDataSource rawDataSourceMP;

    @Autowired
    @Qualifier("GisMeteo")
    RawDataSource rawDataSourceGM;

    @Autowired
    @Qualifier("Sinoptik")
    RawDataSource rawDataSourceSIN;

    @Autowired
    RawDataRepository rawDataRepository;

    @Autowired
    RawDataNormalizationService rawDataNormalizationService;

    @Test
    public void dataManagerGetByDate_LatestDate_Test(){
        LocalDate localDateNow = LocalDate.of(2019,03,16); // date should be included in result
        LocalDate localDateBefore = localDateNow.minusDays(5); // date should be EXCLUDED in result
        Iterable<RawDataEntity> all = rawDataRepository.findByBaseDateBetweenAndSourceId(Date.valueOf(localDateBefore), Date.valueOf(localDateNow), SourcesEnum.GISMETEO);
        HistoricalDataManager dataManager = new HistoricalDataManager(all);

        Date baseDate = dataManager.getHistoricalDataDtoWithLatestDate().getBaseDate();
        Assert.assertEquals(baseDate.compareTo(Date.valueOf(localDateNow)), 0);
    }

    @Test
    public void dataManagerGetByDate_DatesSortOrderTest_Test(){
        LocalDate localDateNow = LocalDate.of(2019,03,10); // date should NOT be included in result
        LocalDate localDateBefore = localDateNow.minusDays(5); // date should be included in result
        Iterable<RawDataEntity> all = rawDataRepository.findByBaseDateBetweenAndSourceId(Date.valueOf(localDateBefore), Date.valueOf(localDateNow), SourcesEnum.GISMETEO);
        HistoricalDataManager dataManager = new HistoricalDataManager(all);
        List<HistoricalDataDto> historicalDataDtos = dataManager.getHistoricalDataDtos();
        for (int i=0; i<historicalDataDtos.size()-2; i++) {
            java.util.Date current = historicalDataDtos.get(i).getBaseDate();
            java.util.Date next = historicalDataDtos.get(i+1).getBaseDate();

            Assert.assertThat(next.compareTo(current), Matchers.greaterThan(0));
        }
    }

    @Test
    public void dataManagerExcHandlingAndNormalization(){
        rawDataNormalizationService.replaceDuplicatesWithAvarage();
        LocalDate localDateNow = LocalDate.of(2018,04,19); // date should be included in result
        LocalDate localDateBefore = localDateNow.minusDays(5); // date should be EXCLUDED in result
        Iterable<RawDataEntity> all = rawDataRepository.findByBaseDateBetweenAndSourceId(Date.valueOf(localDateBefore), Date.valueOf(localDateNow), SourcesEnum.GISMETEO);
        HistoricalDataManager dataManager = new HistoricalDataManager(all, Date.valueOf(localDateNow.minusDays(1)), Date.valueOf(localDateBefore));

    }


}
