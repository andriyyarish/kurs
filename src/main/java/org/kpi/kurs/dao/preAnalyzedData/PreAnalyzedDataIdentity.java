package org.kpi.kurs.dao.preAnalyzedData;

import org.kpi.kurs.web.rawData.SourcesEnum;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 * Primary key for source_historical_reliability and rawdata_analysis
 */
@Embeddable
public class PreAnalyzedDataIdentity implements Serializable {
    @NotNull
    private SourcesEnum source;
    @NotNull
    private int backwardDeepness;
    @NotNull
    private Date baselineDate;

    public PreAnalyzedDataIdentity(SourcesEnum source, int backwardDeepness, Date baselineDate) {
        this.source = source;
        this.backwardDeepness = backwardDeepness;
        this.baselineDate = baselineDate;
    }

    public PreAnalyzedDataIdentity(){

    }

    public SourcesEnum getSource() {
        return source;
    }

    public PreAnalyzedDataIdentity setSource(SourcesEnum source) {
        this.source = source;
        return this;
    }

    public int getBackwardDeepness() {
        return backwardDeepness;
    }

    public PreAnalyzedDataIdentity setBackwardDeepness(int backwardDeepness) {
        this.backwardDeepness = backwardDeepness;
        return this;
    }

    public Date getBaselineDate() {
        return baselineDate;
    }

    public PreAnalyzedDataIdentity setBaselineDate(Date baselineDate) {
        this.baselineDate = baselineDate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PreAnalyzedDataIdentity)) return false;
        PreAnalyzedDataIdentity that = (PreAnalyzedDataIdentity) o;
        return backwardDeepness == that.backwardDeepness &&
                source == that.source &&
                Objects.equals(baselineDate, that.baselineDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(source, backwardDeepness, baselineDate);
    }

    @Override
    public String toString() {
        return "PreAnalyzedDataIdentity{" +
                "source=" + source +
                ", backwardDeepness=" + backwardDeepness +
                ", baselineDate=" + baselineDate +
                '}';
    }
}
