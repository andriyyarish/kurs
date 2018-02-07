package org.kpi.kurs.web.rawData.meteoprog;

import org.kpi.kurs.web.rawData.RawDataDto;
import org.kpi.kurs.web.rawData.RawDataEntity;
import org.kpi.kurs.web.rawData.RawDataRepository;
import org.kpi.kurs.web.rawData.SourcesEnum;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RawDataToDbAdapter {
    private static final SourcesEnum SOURCE_NAME = SourcesEnum.METEOPROG;
    private String tempPattern = "(-?\\d+)";

    @Autowired
    private RawDataRepository rawDataRepository;

    private List<RawDataDto> rawDataDtoList;
    private RawDataEntity rawDataEntity;

    public RawDataToDbAdapter(List<RawDataDto> rawDataDtoList) {
        this.rawDataDtoList = rawDataDtoList;
    }

    public RawDataToDbAdapter(List<RawDataDto> rawDataDtoList, RawDataRepository rawDataRepository) {
        this.rawDataDtoList = rawDataDtoList;
        this.rawDataRepository = rawDataRepository;
    }


    public void saveToDb(){
        rawDataEntity = new RawDataEntity();
        rawDataEntity.setSourceName(SOURCE_NAME);

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

        return Double.valueOf(group);
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


        }
    }


}