package org.kpi.kurs.dao.rawData;

import org.kpi.kurs.web.rawData.SourcesEnum;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class RawDataIdentity implements Serializable {
    @NotNull
    private SourcesEnum sourceId;
    @NotNull
    private Date baseDate;

    public RawDataIdentity(SourcesEnum sourceId, Date baseDate) {
        this.sourceId = sourceId;
        this.baseDate = baseDate;
    }

    public RawDataIdentity() {
    }

    public SourcesEnum getSourceId() {
        return sourceId;
    }

    public RawDataIdentity setSourceId(SourcesEnum sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public Date getBaseDate() {
        return baseDate;
    }

    public RawDataIdentity setBaseDate(Date baseDate) {
        this.baseDate = baseDate;
        return this;
    }
}
