package org.kpi.kurs.web.data_analyze;

import org.kpi.kurs.web.rawData.SourcesEnum;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Embeddable
public class TempDiffIdentity implements Serializable {
    @NotNull
    private SourcesEnum source;
    @NotNull
    private int backwardDeepness;
    @NotNull
    private Date baselineDate;

    public TempDiffIdentity(SourcesEnum source, int backwardDeepness, Date baselineDate) {
        this.source = source;
        this.backwardDeepness = backwardDeepness;
        this.baselineDate = baselineDate;
    }

    public TempDiffIdentity(){

    }

    public SourcesEnum getSource() {
        return source;
    }

    public TempDiffIdentity setSource(SourcesEnum source) {
        this.source = source;
        return this;
    }

    public int getBackwardDeepness() {
        return backwardDeepness;
    }

    public TempDiffIdentity setBackwardDeepness(int backwardDeepness) {
        this.backwardDeepness = backwardDeepness;
        return this;
    }

    public Date getBaselineDate() {
        return baselineDate;
    }

    public TempDiffIdentity setBaselineDate(Date baselineDate) {
        this.baselineDate = baselineDate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TempDiffIdentity)) return false;
        TempDiffIdentity that = (TempDiffIdentity) o;
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
        return "TempDiffIdentity{" +
                "source=" + source +
                ", backwardDeepness=" + backwardDeepness +
                ", baselineDate=" + baselineDate +
                '}';
    }
}
