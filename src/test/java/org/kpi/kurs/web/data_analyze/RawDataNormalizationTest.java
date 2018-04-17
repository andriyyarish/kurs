package org.kpi.kurs.web.data_analyze;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kpi.kurs.KursApplicationTests;
import org.kpi.kurs.web.rawData.RawDataNormalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Profile("default")
public class RawDataNormalizationTest extends KursApplicationTests {

    @Autowired
    RawDataNormalizationService rawDataNormalizationService;

    @Test
    public void test(){
        rawDataNormalizationService.replaceDuplicatesWithAvarage();
    }
}
