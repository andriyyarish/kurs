package org.kpi.kurs.web.rawData.gismeteo;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kpi.kurs.web.rawData.RawDataDto;
import org.kpi.kurs.web.rawData.RawDataSource;
import org.kpi.kurs.web.rawData.SourcesEnum;
import org.openqa.selenium.By;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GisMeteoWheatherSource extends RawDataSource {
    private static final Logger logger = LogManager.getLogger(GisMeteoWheatherSource.class);
    public GisMeteoWheatherSource() {
        baseUrl = "https://www.gismeteo.ua/ua/weather-kyiv-4944/14-days/";
        daysXpath = "//div[@class = 's_date']";
        minTempXpath = "(//div[@class = 's_date']//..//..)//td[@class='temp'][1]//span[1]";
        maxTempXpath = "(//div[@class = 's_date']//..//..)//td[@class='temp'][2]//span[1]";

    }

    @Override
    protected void openSource() {
        super.openSource();
        driver.findElement(By.xpath("//*[contains(text(), 'Розгорнути всі дні')]")).click();
        driver.findElement(By.xpath("//*[contains(text(), 'Згорнути всі дні')]")).click();
    }

    @Override
    protected List<RawDataDto> transformRawDataToDto() {
        List<RawDataDto> res = new ArrayList<>();
        for (int i = 0; i<days.size(); i++) {
            String text = days.get(i).getText();
            String text1 = minTemp.get(i).getText();
            String text2 = maxTemp.get(i).getText();
            RawDataDto rawDataDto = new RawDataDto()
                    .setSourceName(SourcesEnum.GISMETEO)
                    .setBaseDate(LocalDate.now())
                    .setRawDate(text)
                    .setMin(text1)
                    .setMax(text2);
            res.add(rawDataDto);
        }
        logger.debug(res);
        return res;
    }
}
