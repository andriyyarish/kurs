package org.kpi.kurs.web.rawData;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RawDataToDbAdapter {
    private static final Logger logger = LogManager.getLogger(RawDataToDbAdapter.class);
    private static SourcesEnum SOURCE_NAME;
    private String tempPattern = "(\\-?\\−?\\d+)"; // −4 gismeteo has some specific minus symbol

    @Autowired
    private RawDataRepository rawDataRepository;

    private List<RawDataDto> rawDataDtoList;
    private RawDataEntity rawDataEntity;

//    public RawDataToDbAdapter(List<RawDataDto> rawDataDtoList) {
//        this.rawDataDtoList = rawDataDtoList;
//        this.SOURCE_NAME = rawDataDtoList.get(0).getSourceName();
//    }

    public RawDataToDbAdapter(List<RawDataDto> rawDataDtoList, RawDataRepository rawDataRepository) {
        this.rawDataDtoList = rawDataDtoList;
        this.rawDataRepository = rawDataRepository;
        this.SOURCE_NAME = rawDataDtoList.get(0).getSourceName();
    }


    public void saveToDb(){
        rawDataEntity = new RawDataEntity();
        rawDataEntity.setSourceName(SOURCE_NAME);
        rawDataEntity.setBaseDate(rawDataDtoList.get(0).getBaseDate());

        int index = 0;
        for (RawDataDto rdto: rawDataDtoList){
            fillByDate(rdto, index);
            index++;
        }

        rawDataRepository.save(rawDataEntity);
    }

    private double convertTemp(String temp){
        String rawTemp = temp;
        Pattern p = Pattern.compile(tempPattern);
        Matcher matcher = p.matcher(rawTemp);

        String group = "777";
        if (matcher.find()) {
            group = matcher.group(0);
        }
        if(group.equals("777"))
            logger.warn("Parsing fails on value -> " + temp);

        return Double.valueOf(group.replace("−", "-"));
    }

    private void fillByDate(RawDataDto rawDataDto, int index){
        switch (index){
            case 0:
                rawDataEntity.setFirstDayMinTemp(convertTemp(rawDataDto.getMin()));
                rawDataEntity.setFirstDayMaxTemp(convertTemp(rawDataDto.getMax()));
                break;
            case  1:
                rawDataEntity.setSecondDayMinTemp(convertTemp(rawDataDto.getMin()));
                rawDataEntity.setSecondDayMaxTemp(convertTemp(rawDataDto.getMax()));
                break;
            case 2:
                rawDataEntity.setThirdDayMinTemp(convertTemp(rawDataDto.getMin()));
                rawDataEntity.setThirdDayMaxTemp(convertTemp(rawDataDto.getMax()));
                break;
            case 3:
                rawDataEntity.setFourthDayMinTemp(convertTemp(rawDataDto.getMin()));
                rawDataEntity.setFourthDayMaxTemp(convertTemp(rawDataDto.getMax()));
                break;
            case 4:
                rawDataEntity.setFifthDayMinTemp(convertTemp(rawDataDto.getMin()));
                rawDataEntity.setFifthDayMaxTemp(convertTemp(rawDataDto.getMax()));
                break;
            case 5:
                rawDataEntity.setSixDayMinTemp(convertTemp(rawDataDto.getMin()));
                rawDataEntity.setSixDayMaxTemp(convertTemp(rawDataDto.getMax()));
                break;
            case 6:
                rawDataEntity.setSevenDayMinTemp(convertTemp(rawDataDto.getMin()));
                rawDataEntity.setSevenDayMaxTemp(convertTemp(rawDataDto.getMax()));
                break;

        }
    }


}
