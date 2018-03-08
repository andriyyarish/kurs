package org.kpi.kurs.web.data_analyze;

import org.kpi.kurs.web.rawData.SourcesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class HistoricalReliabalitiAnalyze {

    @Autowired
    private DataAnalyzeRepository dataAnalyzeRepository;

    private Date date;

    public HistoricalReliabalitiAnalyze(Date date) {
        this.date = date;
    }

    public void calculateReliabilityRates(){
        for(SourcesEnum s: SourcesEnum.values()){
            dataAnalyzeRepository.findAllByTempDiffIdentitySourceAndTempDiffIdentityBaselineDate(s, date);
        }
    }


}
