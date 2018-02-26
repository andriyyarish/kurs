package org.kpi.kurs;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kpi.kurs.web.data_analyze.HistoricalDataDto;
import org.kpi.kurs.web.data_analyze.HistoricalDataManager;
import org.kpi.kurs.web.rawData.*;
import org.kpi.kurs.web.rawData.meteoprog.Debug;
import org.kpi.kurs.web.rawData.meteoprog.RawDataToDbAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.closeTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@EnableJpaRepositories(basePackages = {"org.kpi.kurs.web.rawData"})
//@DataJpaTest
//@EntityScan(basePackages = {"org.kpi.kurs.web"} )
@Profile("default")
public class KursApplicationTests {

	public static final double ERROR_MARKER = 777.0;

	@Autowired
	@Qualifier("MeteoProg")
	RawDataSource rawDataSourceMP;

	@Autowired
	@Qualifier("GisMeteo")
	RawDataSource rawDataSourceGM;

	@Autowired
	@Qualifier("Sinoptik")
	RawDataSource rawDataSourceSIN;

	@Autowired
	RawDataRepository rawDataRepository;

	@Test
	public void meteoprog() throws InterruptedException {
		List<RawDataDto> rawDataDtos = rawDataSourceMP.collectData();

		Iterable<RawDataEntity> res = saveToDb(rawDataDtos);
		verifyEntity(res);
	}

	@Test
	public void gismeteo() throws InterruptedException {
		List<RawDataDto> rawDataDtos = rawDataSourceGM.collectData();

		Iterable<RawDataEntity> res = saveToDb(rawDataDtos);
		verifyEntity(res);
	}

	@Test
	public void sinoptik() throws InterruptedException {
		List<RawDataDto> rawDataDtos = rawDataSourceSIN.collectData();

		Iterable<RawDataEntity> res = saveToDb(rawDataDtos);
		verifyEntity(res);
	}

	@Test
	public void historicalDataAnalyze(){
		LocalDate localDateNow = LocalDate.now();
		LocalDate localDateBefore = localDateNow.minusDays(11);

		Iterable<RawDataEntity> all = rawDataRepository.findByBaseDateBetweenAndSourceName(Date.valueOf(localDateBefore), Date.valueOf(localDateNow), SourcesEnum.GISMETEO);

		HistoricalDataManager dataManager = new HistoricalDataManager(all);

		Optional<HistoricalDataDto> historicalDataDtoByDate = dataManager.getHistoricalDataDtoByDate(Date.valueOf(localDateBefore.minusDays(3)));
		HistoricalDataDto historicalDataDto = historicalDataDtoByDate.get();

	}

	private Iterable<RawDataEntity> saveToDb(List<RawDataDto> rawDataDtos){
		RawDataToDbAdapter rawDataToDbAdapter = new RawDataToDbAdapter(rawDataDtos, rawDataRepository);
		rawDataToDbAdapter.saveToDb();

		Iterable<RawDataEntity> all = rawDataRepository.findAll();
		System.out.println("!!!!!!!!!!!!!!!!");
		System.out.println(all);
		return all;

	}

	private void verifyEntity(Iterable <RawDataEntity> list){
		for(RawDataEntity e: list){
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
