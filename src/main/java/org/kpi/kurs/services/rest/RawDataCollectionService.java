package org.kpi.kurs.services.rest;

import com.google.common.collect.Lists;
import org.kpi.kurs.web.data_analyze.HistoricalDataManager;
import org.kpi.kurs.web.rawData.RawDataDto;
import org.kpi.kurs.dao.rawData.RawDataEntity;
import org.kpi.kurs.dao.rawData.RawDataRepository;
import org.kpi.kurs.web.rawData.RawDataSource;
import org.kpi.kurs.web.rawData.RawDataToDbAdapter;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RawDataCollectionService {

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

//    @Autowired
//    WebDriver driver;

    @Async
    @GetMapping(value = "collectData")
    public List<RawDataEntity> collectRawData(){

        return null;
    }



    private Iterable<RawDataEntity> saveToDb(List<RawDataDto> rawDataDtos){
//        RawDataToDbAdapter rawDataToDbAdapter = new RawDataToDbAdapter(rawDataDtos, rawDataRepository);
//        rawDataToDbAdapter.saveToDb();
//
//        Iterable<RawDataEntity> all = rawDataRepository.findBySourceId(rawDataDtos.get(0).getSourceName());
//        return all;
        return null;
    }
}
