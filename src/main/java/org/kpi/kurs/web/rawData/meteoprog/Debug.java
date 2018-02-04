package org.kpi.kurs.web.rawData.meteoprog;

import org.kpi.kurs.web.rawData.RawDataDto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;


public class Debug {

    @Autowired
    WebDriver driver;

    public void run() throws InterruptedException {

        driver.get("https://www.meteoprog.ua/ua/weather/Kyiv/6_10/#detail");
        Thread.sleep(3000);
        List<WebElement> days = driver.findElements(By.xpath("//*[contains(@class, 'dayoffMonth')]"));
        List<WebElement> minTemp = driver.findElements(By.xpath("//*[contains(@class, 'dayoffMonth')]/following-sibling::span/span/span[@class='from']"));
        List<WebElement> maxTemp = driver.findElements(By.xpath("//*[contains(@class, 'dayoffMonth')]/following-sibling::span/span/span[@class='to']"));

        List<RawDataDto> res = new ArrayList<>();

//        for (int i = 0; i<days.size(); i++) {
//            String text = days.get(i).getText();
//            String text1 = minTemp.get(i).getText();
//            String text2 = maxTemp.get(i).getText();
//            RawDataDto rawDataDto = new RawDataDto()
//                    .setRawDate(text)
//                    .setMin(text1)
//                    .setMax(text2);
//            res.add(rawDataDto);
//        }
        System.out.println(res);
        driver.close();
    }


}
