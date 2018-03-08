package org.kpi.kurs.web.data_analyze;

import org.hibernate.annotations.NaturalId;
import org.kpi.kurs.web.rawData.SourcesEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Objects;


/**
 * Data transfer object that stores results of  forecasts from the past with some baseline
 * This data should be stored in DB for further 'trust' score calculation for each data source
 */
@Entity
@Table(name = "rawdata_analysis")
public class TempDiffsDto {
    //@NaturalId
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //private Long id;
    @EmbeddedId
    private TempDiffIdentity tempDiffIdentity;
    @NotNull
    private Double maxTempDiff;
    @NotNull
    private Double minTempDiff;

    public TempDiffsDto() {
        tempDiffIdentity = new TempDiffIdentity();
    }

    public SourcesEnum getSource() {
        return tempDiffIdentity.getSource();
    }

    public TempDiffsDto setSource(SourcesEnum sources) {
        tempDiffIdentity.setSource(sources);
        return this;
    }

    public int getBackwardDeepness() {
        return tempDiffIdentity.getBackwardDeepness();
    }

    public TempDiffsDto setBackwardDeepness(int backwardDeepness) {
        tempDiffIdentity.setBackwardDeepness(backwardDeepness);
        return this;
    }

    public Date getBaselineDate() {
        return tempDiffIdentity.getBaselineDate();
    }

    public TempDiffsDto setBaselineDate(Date baselineDate) {
        tempDiffIdentity.setBaselineDate(baselineDate);
        return this;
    }

    public Double getMaxTempDiff() {
        return maxTempDiff;
    }

    public TempDiffsDto setMaxTempDiff(Double maxTempDiff) {
        this.maxTempDiff = maxTempDiff;
        return this;
    }

    public Double getMinTempDiff() {
        return minTempDiff;
    }

    public TempDiffsDto setMinTempDiff(Double minTempDiff) {
        this.minTempDiff = minTempDiff;
        return this;
    }



    @Override
    public String toString() {
        return "TempDiffsDto{" +
                ", tempDiffIdentity=" + tempDiffIdentity +
                ", maxTempDiff=" + maxTempDiff +
                ", minTempDiff=" + minTempDiff +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TempDiffsDto)) return false;
        TempDiffsDto that = (TempDiffsDto) o;
        return Objects.equals(tempDiffIdentity, that.tempDiffIdentity) &&
                Objects.equals(maxTempDiff, that.maxTempDiff) &&
                Objects.equals(minTempDiff, that.minTempDiff);
    }

    @Override
    public int hashCode() {

        return Objects.hash(tempDiffIdentity, maxTempDiff, minTempDiff);
    }
}
