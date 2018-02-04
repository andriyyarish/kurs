package org.kpi.kurs.web.rawData.gismeteo;

import org.kpi.kurs.web.rawData.RawDataDto;
import org.kpi.kurs.web.rawData.RawDataSource;

import java.util.List;

public class GisMeteoWheatherSource extends RawDataSource {

    public GisMeteoWheatherSource() {
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
