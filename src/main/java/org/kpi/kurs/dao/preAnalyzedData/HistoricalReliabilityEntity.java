package org.kpi.kurs.dao.preAnalyzedData;

import org.kpi.kurs.web.rawData.SourcesEnum;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "sources_historical_reliability")
public class HistoricalReliabilityEntity {

    @Id
    private Long id;
    private SourcesEnum source;
    private Date baselineDate;
    private int backwardDeepness;
    private double reliabilityRation;

    public HistoricalReliabilityEntity(SourcesEnum source, Date baselineDate, int backwardDeepness) {
        this.source = source;
        this.baselineDate = baselineDate;
        this.backwardDeepness = backwardDeepness;
    }

    public HistoricalReliabilityEntity(){

    }

    public Long getId() {
        return id;
    }

    public HistoricalReliabilityEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public SourcesEnum getSource() {
        return source;
    }

    public HistoricalReliabilityEntity setSource(SourcesEnum source) {
        this.source = source;
        return this;
    }

    public Date getBaselineDate() {
        return baselineDate;
    }

    public HistoricalReliabilityEntity setBaselineDate(Date baselineDate) {
        this.baselineDate = baselineDate;
        return this;
    }

    public int getBackwardDeepness() {
        return backwardDeepness;
    }

    public HistoricalReliabilityEntity setBackwardDeepness(int backwardDeepness) {
        this.backwardDeepness = backwardDeepness;
        return this;
    }

    public double getReliabilityRation() {
        return reliabilityRation;
    }

    public HistoricalReliabilityEntity setReliabilityRatio(double reliabilityRation) {
        this.reliabilityRation = reliabilityRation;
        return this;
    }

    @Override
    public String toString() {
        return "HistoricalReliabilityEntity{" +
                "id=" + id +
                ", source=" + source +
                ", baselineDate=" + baselineDate +
                ", backwardDeepness=" + backwardDeepness +
                ", reliabilityRation=" + reliabilityRation +
                "}\n";
    }
}
