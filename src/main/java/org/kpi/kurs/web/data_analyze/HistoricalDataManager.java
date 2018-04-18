package org.kpi.kurs.web.data_analyze;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kpi.kurs.dao.rawData.RawDataEntity;

import java.util.*;

/**
 * Convert raw data into sorted list of rawData entities for further proccesing on program side
 */
public class HistoricalDataManager {
    private static final Logger logger = LogManager.getLogger(HistoricalDataManager.class);
    private Iterable<RawDataEntity> rawDataEntitiesList;
    private List<HistoricalDataDto> historicalDataDtos;

    private Date baseDate;
    private Date earliestBackWardDate;

    private static final int MAX_EXPECTED_PERIOD_LENGTH = 7;

    public HistoricalDataManager(Iterable<RawDataEntity> rawDataEntitiesList) {
        this.rawDataEntitiesList = rawDataEntitiesList;
        initHistoricalDataDto();
        sortHistoricalDataByDate();
        logger.info(String.format("Raw data converted to historical dto. Dates range is %s -> %s",
                historicalDataDtos.get(0).getBaseDate().toString(),
                historicalDataDtos.get(historicalDataDtos.size()-1).getBaseDate().toString()));
    }

    public HistoricalDataManager(Iterable<RawDataEntity> rawDataEntities, Date baseDate, Date earliestBackWardDate){
        this(rawDataEntities);
        this.baseDate = baseDate;
        this.earliestBackWardDate = earliestBackWardDate;
        verifyAndNormalize();
    }

    private void verifyAndNormalize(){

        if(!(historicalDataDtos.get(historicalDataDtos.size()-1).getBaseDate().equals(baseDate))) {
            logger.error("Based date expected:-> " + baseDate.toString());
            logger.error("Base date actual:-> " + historicalDataDtos.get(historicalDataDtos.size()-1).getBaseDate());
            throw new HistoricalDataCorruptedException("Looks like that expected base date was not retrieved from DB");
        }

        if(historicalDataDtos.size()>MAX_EXPECTED_PERIOD_LENGTH)
            throw new HistoricalDataCorruptedException(String.format("Looks like that retrieved data set size (%d) is bigger than max expected period %d. Please remove duplicates",
                    historicalDataDtos.size(), MAX_EXPECTED_PERIOD_LENGTH));

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
