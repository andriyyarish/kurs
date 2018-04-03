package org.kpi.kurs.web.rawData.meteoprog;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kpi.kurs.web.rawData.RawDataDto;
import org.kpi.kurs.web.rawData.RawDataSource;
import org.kpi.kurs.web.rawData.SourcesEnum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MeteoProgWheatherSource extends RawDataSource {
    private static final Logger logger = LogManager.getLogger(MeteoProgWheatherSource.class);

    public MeteoProgWheatherSource() {
        baseUrl = "https://www.meteoprog.ua/ua/weather/Kyiv/6_10/#detail";
        daysXpath = "//*[contains(@class, 'dayoffMonth')]";
        minTempXpath = "//*[contains(@class, 'dayoffMonth')]/following-sibling::span/span/span[@class='from']";
        maxTempXpath = "//*[contains(@class, 'dayoffMonth')]/following-sibling::span/span/span[@class='to']";
    }

    @Override
    protected List<RawDataDto> transformRawDataToDto() {
        List<RawDataDto> res = new ArrayList<>();
        for (int i = 0; i<days.size(); i++) {
            String text = days.get(i).getText();
            String text1 = minTemp.get(i).getText();
            String text2 = maxTemp.get(i).getText();
            RawDataDto rawDataDto = new RawDataDto()
                    .setBaseDate(LocalDate.now())
                    .setSourceName(SourcesEnum.METEOPROG)
                    .setRawDate(text)
                    .setMin(text1)
                    .setMax(text2);
            res.add(rawDataDto);
        }
        return res;
    }


}
