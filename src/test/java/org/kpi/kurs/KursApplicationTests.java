package org.kpi.kurs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kpi.kurs.web.rawData.RawDataEntity;
import org.kpi.kurs.web.rawData.RawDataRepository;
import org.kpi.kurs.web.rawData.RawDataSource;
import org.kpi.kurs.web.rawData.SourcesEnum;
import org.kpi.kurs.web.rawData.meteoprog.Debug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KursApplicationTests {

	@Autowired
	Debug debug;

	@Autowired
	RawDataSource rawDataSource;

	@Autowired
	RawDataRepository rawDataRepository;


	@Test
	public void contextLoads() throws InterruptedException {
		rawDataSource.collectData();
		RawDataEntity rawDataEntity = new RawDataEntity();
		Date date = new Date(2018,02,04);
		rawDataEntity.setBaseDate(date)
				.setFifthDayMaxTemp(2.5)
				.setSourceName(SourcesEnum.GISMETEO);
		rawDataRepository.save(rawDataEntity);
		Iterable<RawDataEntity> all = rawDataRepository.findAll();
		System.out.println("!!!!!!!!!!!!!!!!");
		System.out.println(all);

	}

}
