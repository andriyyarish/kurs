package org.kpi.kurs.web.rawData;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class RawDataSource {

    @Autowired
    protected WebDriver driver;
    protected String baseUrl;

    protected String daysXpath ;
    protected String minTempXpath ;
    protected String maxTempXpath ;

    protected List<WebElement> days;
    protected List<WebElement> minTemp;
    protected List<WebElement> maxTemp;

    public List<RawDataDto> collectData(){
        openSource();
        getRawData();
        verifyRawData();
        return transformRawDataToDto();
    }

    protected void getRawData(){
        days = driver.findElements(By.xpath(daysXpath));
        maxTemp = driver.findElements(By.xpath(maxTempXpath));
        minTemp = driver.findElements(By.xpath(minTempXpath));
    }

    protected void verifyRawData(){
        if(days.size() == maxTemp.size() &&
                maxTemp.size() == minTemp.size()){
            System.out.println("Length of all rawData parts is the same");
        }else {
            System.out.println("WARN raw data size is different");
        }
    }

    protected void openSource(){
        driver.get(baseUrl);
    }

    protected abstract List<RawDataDto> transformRawDataToDto();

}
