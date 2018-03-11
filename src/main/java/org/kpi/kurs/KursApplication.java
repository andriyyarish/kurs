package org.kpi.kurs;

import org.kpi.kurs.web.rawData.RawDataSource;
import org.kpi.kurs.web.rawData.gismeteo.GisMeteoWheatherSource;
import org.kpi.kurs.web.rawData.meteoprog.Debug;
import org.kpi.kurs.web.rawData.meteoprog.MeteoProgWheatherSource;
import org.kpi.kurs.web.rawData.sinoptik.SinoptikWheatherSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"org.kpi.kurs.dao"})
@EntityScan(basePackages = {"org.kpi.kurs"} )
public class KursApplication {

	public static void main(String[] args) {
		System.setProperty("webdriver.driver", "chrome");
		System.setProperty("webdriver.chrome.driver",
				"D:\\Development\\kurs\\chromedriver.exe");
		SpringApplication.run(KursApplication.class, args);
	}

	@Bean(name = "Driver")
	@Scope(value = WebApplicationContext.SCOPE_REQUEST,
			proxyMode = ScopedProxyMode.TARGET_CLASS)
	public WebDriver getDriver(){

		return new ChromeDriver();
	}


	//@DependsOn("Driver")
	@Bean(name = "MeteoProg")
	//@Scope(scopeName = "request")
	public RawDataSource meteoProg(){
		return new MeteoProgWheatherSource();
	}

	//@DependsOn("Driver")
	@Bean(name = "GisMeteo")
	//@Scope(scopeName = "request")
	public RawDataSource gismeteo(){
		return new GisMeteoWheatherSource();
	}

	//@DependsOn("Driver")
	@Bean(name = "Sinoptik")
	//@Scope(scopeName = "request")
	public RawDataSource sinoptik(){
		return new SinoptikWheatherSource();
	}

}
