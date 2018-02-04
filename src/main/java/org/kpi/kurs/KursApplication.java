package org.kpi.kurs;

import org.kpi.kurs.web.rawData.RawDataSource;
import org.kpi.kurs.web.rawData.meteoprog.Debug;
import org.kpi.kurs.web.rawData.meteoprog.MeteoProgWheatherSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

@SpringBootApplication
public class KursApplication {

	public static void main(String[] args) {
		SpringApplication.run(KursApplication.class, args);
	}

	@Bean(name = "Driver")
	public WebDriver getDriver(){
		System.setProperty("webdriver.driver", "chrome");
		System.setProperty("webdriver.chrome.driver",
				"D:\\Development\\kurs\\chromedriver.exe");
		return new ChromeDriver();
	}

	@DependsOn("Driver")
	@Bean
	public Debug debug(){
		return new Debug();
	}

	@DependsOn("Driver")
	@Bean
	public RawDataSource meteoProg(){
		return new MeteoProgWheatherSource();
	}

}
