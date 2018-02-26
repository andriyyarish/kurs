package org.kpi.kurs.web.data_analyze;

import org.kpi.kurs.web.rawData.RawDataEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class HistoricalDataManager {
    private Iterable<RawDataEntity> rawDataEntitiesList;
    private List<HistoricalDataDto> historicalDataDtos;

    public HistoricalDataManager(Iterable<RawDataEntity> rawDataEntitiesList) {
        this.rawDataEntitiesList = rawDataEntitiesList;
        initHistoricalDataDto();
    }

    private void initHistoricalDataDto(){
        historicalDataDtos = new ArrayList<>();

        for(RawDataEntity e: rawDataEntitiesList){
            HistoricalDataDto dto = new HistoricalDataDto(e.getBaseDate(), e.getSourceName());
            fillTemp(e, dto);
            historicalDataDtos.add(dto);
        }

    }

    private void fillTemp(RawDataEntity source, HistoricalDataDto target){
         target.addValueToMinTempList(0, source.getFirstDayMinTemp());
         target.addValueToMaxTempList(0, source.getFirstDayMaxTemp());

         target.addValueToMinTempList(1, source.getSecondDayMinTemp());
         target.addValueToMaxTempList(1, source.getSecondDayMaxTemp());

         target.addValueToMinTempList(2, source.getThirdDayMinTemp());
         target.addValueToMaxTempList(2, source.getThirdDayMaxTemp());

         target.addValueToMinTempList(3, source.getFourthDayMinTemp());
         target.addValueToMaxTempList(3, source.getFourthDayMaxTemp());

         target.addValueToMinTempList(4, source.getFifthDayMinTemp());
         target.addValueToMaxTempList(4, source.getFifthDayMaxTemp());


         //TODO add more days later
    }

    public Optional<HistoricalDataDto> getHistoricalDataDtoByDate(Date date){
        return historicalDataDtos.stream().filter(o -> o.getBaseDate().compareTo(date) == 0).findAny();
    }




}
