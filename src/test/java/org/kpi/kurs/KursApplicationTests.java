package org.kpi.kurs;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kpi.kurs.web.rawData.*;
import org.kpi.kurs.web.rawData.meteoprog.Debug;
import org.kpi.kurs.web.rawData.meteoprog.RawDataToDbAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableJpaRepositories(basePackages = {"org.kpi.kurs.web.rawData"})
@DataJpaTest
@EntityScan(basePackages = {"org.kpi.kurs.web"} )
public class KursApplicationTests {

	public static final double ERROR_MARKER = 777.0;
	@Autowired
	Debug debug;

	@Autowired
	RawDataSource rawDataSource;

	@Autowired
	RawDataRepository rawDataRepository;


	@Test
	public void contextLoads() throws InterruptedException {
		List<RawDataDto> rawDataDtos = rawDataSource.collectData();
		RawDataToDbAdapter rawDataToDbAdapter = new RawDataToDbAdapter(rawDataDtos, rawDataRepository);
		rawDataToDbAdapter.saveToDb();


		Iterable<RawDataEntity> all = rawDataRepository.findAll();
		System.out.println("!!!!!!!!!!!!!!!!");
		System.out.println(all);
		for(RawDataEntity e: all){
			Assert.assertThat(e.getFirstDayMinTemp(), not(closeTo(ERROR_MARKER, 1.0)));
			Assert.assertThat(e.getFirstDayMaxTemp(), not(closeTo(ERROR_MARKER, 1.0)));
			Assert.assertThat(e.getSecondDayMinTemp(), not(closeTo(ERROR_MARKER, 1.0)));
			Assert.assertThat(e.getSecondDayMinTemp(), not(closeTo(ERROR_MARKER, 1.0)));
			Assert.assertThat(e.getThirdDayMinTemp(), not(closeTo(ERROR_MARKER, 1.0)));
			Assert.assertThat(e.getThirdDayMaxTemp(), not(closeTo(ERROR_MARKER, 1.0)));
			Assert.assertThat(e.getFourthDayMinTemp(), not(closeTo(ERROR_MARKER, 1.0)));
			Assert.assertThat(e.getFourthDayMaxTemp(), not(closeTo(ERROR_MARKER, 1.0)));
			Assert.assertThat(e.getFifthDayMinTemp(), not(closeTo(ERROR_MARKER, 1.0)));
			Assert.assertThat(e.getFifthDayMaxTemp(), not(closeTo(ERROR_MARKER, 1.0)));

		}


	}

}
