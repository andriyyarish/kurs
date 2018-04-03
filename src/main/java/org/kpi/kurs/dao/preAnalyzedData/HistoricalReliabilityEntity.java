package org.kpi.kurs.dao.preAnalyzedData;

import org.kpi.kurs.web.rawData.SourcesEnum;

import javax.persistence.*;
import java.sql.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "sources_historical_reliability")
public class HistoricalReliabilityEntity {

    //@Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
    private Long id;

    @EmbeddedId
    private PreAnalyzedDataIdentity tempDiffsEntity;
    private double reliabilityRatio;

    public HistoricalReliabilityEntity(SourcesEnum source, Date baselineDate, int backwardDeepness) {
        tempDiffsEntity = new PreAnalyzedDataIdentity();
        tempDiffsEntity.setSource(source);
        tempDiffsEntity.setBaselineDate(baselineDate);
        tempDiffsEntity.setBackwardDeepness(backwardDeepness);
    }

    public HistoricalReliabilityEntity(){

    }

//    public Long getId() {
//        return id;
//    }
//
//    public HistoricalReliabilityEntity setId(Long id) {
//        this.id = id;
//        return this;
//    }

    public SourcesEnum getSource() {
        return tempDiffsEntity.getSource();
    }

    public HistoricalReliabilityEntity setSource(SourcesEnum source) {
        tempDiffsEntity.setSource(source);
        return this;
    }

    public Date getBaselineDate() {
        return tempDiffsEntity.getBaselineDate();
    }

    public HistoricalReliabilityEntity setBaselineDate(Date baselineDate) {
        tempDiffsEntity.setBaselineDate(baselineDate);
        return this;
    }

    public int getBackwardDeepness() {
        return tempDiffsEntity.getBackwardDeepness();
    }

    public HistoricalReliabilityEntity setBackwardDeepness(int backwardDeepness) {
        tempDiffsEntity.setBackwardDeepness(backwardDeepness);
        return this;
    }

    public double getReliabilityRatio() {
        return reliabilityRatio;
    }

    public HistoricalReliabilityEntity setReliabilityRatio(double reliabilityRation) {
        this.reliabilityRatio = reliabilityRation;
        return this;
    }

    @Override
    public String toString() {
        return "HistoricalReliabilityEntity{" +
 //               "id=" + id +
                ", source=" + tempDiffsEntity +
                ", reliabilityRation=" + reliabilityRatio +
                "}\n";
    }
}
