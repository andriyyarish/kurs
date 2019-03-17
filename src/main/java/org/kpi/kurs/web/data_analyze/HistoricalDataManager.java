package org.kpi.kurs.web.data_analyze;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kpi.kurs.dao.rawData.RawDataEntity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.kpi.kurs.web.data_analyze.DateUtils.convertDateToLocalDate;

/**
 * Convert raw data into sorted list of rawData entities for further proccesing on program side
 */
public class HistoricalDataManager {
    private static final Logger logger = LogManager.getLogger(HistoricalDataManager.class);
    private Iterable<RawDataEntity> rawDataEntitiesList;
    private List<HistoricalDataDto> historicalDataDtos;
    private static final int MIN_EXPECTED_PERIOD_LENGTH = 5;

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

        if(historicalDataDtos.size()>MAX_EXPECTED_PERIOD_LENGTH || historicalDataDtos.size()<MIN_EXPECTED_PERIOD_LENGTH) {
            logger.warn(String.format("Looks like that retrieved data set size (%d) is bigger than max expected period %d. Please remove duplicates",
                    historicalDataDtos.size(), MAX_EXPECTED_PERIOD_LENGTH));
            fillMissedValues();
        }
    }

    private void fillMissedValues(){
        LocalDate localDate = convertDateToLocalDate(baseDate);
        HistoricalDataDto baseLine = getHistoricalDataDtoWithLatestDate();
        double baseLineMinTemp = getHistoricalDataDtoWithLatestDate().getMinTempList().get(0);
        for (int i = 1; i< MAX_EXPECTED_PERIOD_LENGTH; i++){
            Date misseDate = Date.valueOf(localDate.minusDays(i));
            Optional<HistoricalDataDto> historicalDataDtoByDate = getHistoricalDataDtoByDate(misseDate);
            if(!historicalDataDtoByDate.isPresent()){
                logger.warn("Historical data dto for is missed for date " + misseDate.toString());
                HistoricalDataDto mockData = new HistoricalDataDto(misseDate, baseLine.getSource());
                mockData.addValueToMinTempList(i, baseLine.getMinTempList().get(0));
                mockData.addValueToMaxTempList(i, baseLine.getMaxTempList().get(0));
                historicalDataDtos.add(mockData);
            }
        }
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
