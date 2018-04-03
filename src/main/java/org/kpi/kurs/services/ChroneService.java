package org.kpi.kurs.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.kpi.kurs.dao.preAnalyzedData.DataAnalyzeRepository;
import org.kpi.kurs.dao.preAnalyzedData.TempDiffsEntity;
import org.kpi.kurs.dao.rawData.RawDataEntity;
import org.kpi.kurs.dao.rawData.RawDataRepository;
import org.kpi.kurs.web.data_analyze.HistoricalDataAnalyze;
import org.kpi.kurs.web.data_analyze.HistoricalDataManager;
import org.kpi.kurs.web.rawData.RawDataDto;
import org.kpi.kurs.web.rawData.RawDataSource;
import org.kpi.kurs.web.rawData.RawDataToDbAdapter;
import org.kpi.kurs.web.rawData.SourcesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Component
public class ChroneService {
    private static final Logger logger = LogManager.getLogger(ChroneService.class);

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
    DataAnalyzeRepository dataAnalyzeRepository;


    @Scheduled(fixedRate = 50000)
    public void collectRawData() {
        verifyRawDataAndSave(rawDataSourceGM, rawDataSourceMP, rawDataSourceSIN);
    }

    /**
     * Current date should be baseline date. But in this case raw data should be collected continuously date by date
     */
    @Scheduled(fixedRate = 70000)
    public void analyzeHistoricalData(){
        LocalDate localDateNow = LocalDate.of(2018,02,25);  // date should be EXCLUDED in result
        LocalDate localDateBefore = localDateNow.minusDays(6); // date should be included in result //
        Iterable<RawDataEntity> all = rawDataRepository.findByBaseDateBetweenAndSourceId(Date.valueOf(localDateBefore), Date.valueOf(localDateNow), SourcesEnum.GISMETEO);
        HistoricalDataManager dataManager = new HistoricalDataManager(all);
        HistoricalDataAnalyze dataAnalyze = new HistoricalDataAnalyze(dataManager);
        dataAnalyze.calculateDifs();
        List<TempDiffsEntity> comparisonResult = dataAnalyze.getComparisonResult();
        dataAnalyzeRepository.save(comparisonResult);
    }

    private void verifyRawDataAndSave(RawDataSource... sources) {
        logger.debug("RawData collection started. Data will be saved to db if verification passed.");
        List<RawDataDto> result = new LinkedList<>();
        try {
            for (RawDataSource s : sources) {

                List<RawDataDto> rawDataDtos = s.collectData();
                Assert.assertTrue("Size of collected data is less than 5",rawDataDtos.size() >= 5);
                result.addAll(rawDataDtos);
            }
            saveToDb(result);
            logger.debug(String.format("Raw data successfully saved. Size of saved list is %d", result.size()));
        } catch (AssertionError e) {
            logger.warn(String.format("Verification of collected results failed. Details: -> \n %s", e.getMessage()));
        }

    }


    private void saveToDb(List<RawDataDto> rawDataDtos) {
        RawDataToDbAdapter rawDataToDbAdapter = new RawDataToDbAdapter(rawDataDtos, rawDataRepository);
        rawDataToDbAdapter.saveToDb();
    }

    private List<RawDataEntity> retrieveRawDataFromDb(){
        List<RawDataEntity> result = new LinkedList<>();
        for(SourcesEnum s: SourcesEnum.values()){
            List<RawDataEntity> bySourceId = rawDataRepository.findBySourceId(s);
            result.addAll(bySourceId);
        }
        return result;
    }

}
