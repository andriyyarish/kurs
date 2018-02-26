package org.kpi.kurs.web.rawData.sinoptik;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kpi.kurs.web.rawData.RawDataDto;
import org.kpi.kurs.web.rawData.RawDataSource;
import org.kpi.kurs.web.rawData.SourcesEnum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SinoptikWheatherSource extends RawDataSource {
    private static final Logger logger = LogManager.getLogger(SinoptikWheatherSource.class);

    public SinoptikWheatherSource() {
        baseUrl = "https://ua.sinoptik.ua/%D0%BF%D0%BE%D0%B3%D0%BE%D0%B4%D0%B0-%D0%BA%D0%B8%D1%97%D0%B2";
        daysXpath = "//div[@class='tabs']//*[contains(@class,'date ')]";
        minTempXpath = "//div[@class='tabs']//*[contains(@class,'date ')]//following-sibling::div[@class='temperature']/div[@class='min']/span";
        maxTempXpath = "//div[@class='tabs']//*[contains(@class,'date ')]//following-sibling::div[@class='temperature']/div[@class='max']/span";

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
                    .setSourceName(SourcesEnum.SINOPTIK)
                    .setRawDate(text)
                    .setMin(text1)
                    .setMax(text2);
            res.add(rawDataDto);
        }

        logger.debug(res);
        return res;
    }
}
