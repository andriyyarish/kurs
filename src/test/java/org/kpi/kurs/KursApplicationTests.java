package org.kpi.kurs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kpi.kurs.web.rawData.*;
import org.kpi.kurs.web.rawData.meteoprog.Debug;
import org.kpi.kurs.web.rawData.meteoprog.RawDataToDbAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.List;

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
		List<RawDataDto> rawDataDtos = rawDataSource.collectData();
		RawDataToDbAdapter rawDataToDbAdapter = new RawDataToDbAdapter(rawDataDtos);
		rawDataToDbAdapter.saveToDb();


		Iterable<RawDataEntity> all = rawDataRepository.findAll();
		System.out.println("!!!!!!!!!!!!!!!!");
		System.out.println(all);

	}

}
