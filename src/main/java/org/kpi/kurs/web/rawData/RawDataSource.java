package org.kpi.kurs.web.rawData;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.util.List;

public abstract class RawDataSource {
    private static final Logger logger = LogManager.getLogger(RawDataSource.class);
    protected WebDriver driver;
    protected String baseUrl;

    protected String daysXpath ;
    protected String minTempXpath ;
    protected String maxTempXpath ;

    protected List<WebElement> days;
    protected List<WebElement> minTemp;
    protected List<WebElement> maxTemp;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    private void setupDriver(){
        this.driver = applicationContext.getBean(WebDriver.class);
    }

    public List<RawDataDto> collectData(){
        logger.trace("Raw data collection started");
        openSource();
        getRawData();
        verifyRawData();
        return transformRawDataToDto();
    }

    protected void getRawData(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        days = driver.findElements(By.xpath(daysXpath));
        maxTemp = driver.findElements(By.xpath(maxTempXpath));
        minTemp = driver.findElements(By.xpath(minTempXpath));
    }

    protected void verifyRawData(){
        if(days.size() == maxTemp.size() &&
                maxTemp.size() == minTemp.size()){
            logger.trace("Length of all rawData parts is the same");
        }else {
            logger.warn("WARN raw data size is different");
        }
    }

    protected void openSource(){
        driver.get(baseUrl);
    }

    protected abstract List<RawDataDto> transformRawDataToDto();

    public void closeWebDriver(){
        this.driver.close();
    }

}
