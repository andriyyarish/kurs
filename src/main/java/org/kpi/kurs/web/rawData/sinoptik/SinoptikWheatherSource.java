package org.kpi.kurs.web.rawData.sinoptik;

import org.kpi.kurs.web.rawData.RawDataDto;
import org.kpi.kurs.web.rawData.RawDataSource;

import java.util.List;

public class SinoptikWheatherSource extends RawDataSource {

    public SinoptikWheatherSource() {
        baseUrl = "https://www.meteoprog.ua/ua/weather/Kyiv/6_10/#detail";
        daysXpath = "//*[contains(@class, 'dayoffMonth')]";
        minTempXpath = "//*[contains(@class, 'dayoffMonth')]/following-sibling::span/span/span[@class='from']";
        maxTempXpath = "//*[contains(@class, 'dayoffMonth')]/following-sibling::span/span/span[@class='to']";

    }

    @Override
    protected List<RawDataDto> transformRawDataToDto() {
        return null;
    }
}
