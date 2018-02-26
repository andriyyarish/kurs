package org.kpi.kurs.web.rawData.meteoprog;

import org.kpi.kurs.web.rawData.RawDataDto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Debug {

    @Autowired
    WebDriver driver;

    public static void main(String [] args) throws InterruptedException {

        LocalDate localDate = LocalDate.now();
        Date date = Date.valueOf(localDate);

        System.out.println(date);

    }


}
