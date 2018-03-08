package org.kpi.kurs.web.data_analyze;

import org.kpi.kurs.web.rawData.RawDataEntity;

import java.util.*;

public class HistoricalDataManager {
    private Iterable<RawDataEntity> rawDataEntitiesList;
    private List<HistoricalDataDto> historicalDataDtos;

    public HistoricalDataManager(Iterable<RawDataEntity> rawDataEntitiesList) {
        this.rawDataEntitiesList = rawDataEntitiesList;
        initHistoricalDataDto();
        sortHistoricalDataByDate();
    }

    private void initHistoricalDataDto(){
        historicalDataDtos = new ArrayList<>();

        for(RawDataEntity e: rawDataEntitiesList){
            HistoricalDataDto dto = new HistoricalDataDto(e.getBaseDate(), e.getSourceName());
            fillTemp(e, dto);
            historicalDataDtos.add(dto);
        }
    }

    private void sortHistoricalDataByDate(){
        historicalDataDtos.sort(new Comparator<HistoricalDataDto>() {
            @Override
            public int compare(HistoricalDataDto o1, HistoricalDataDto o2) {
                return o1.getBaseDate().compareTo(o2.getBaseDate());
            }
        });
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

    public HistoricalDataDto getHistoricalDataDtoWithLatestDate(){
        return historicalDataDtos.get(historicalDataDtos.size()-1);
    }

    List<HistoricalDataDto> getHistoricalDataDtos() {
        return historicalDataDtos;
    }
}
