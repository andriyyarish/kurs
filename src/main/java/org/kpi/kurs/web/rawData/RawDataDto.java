package org.kpi.kurs.web.rawData;

import java.sql.Date;
import java.time.LocalDate;

public class RawDataDto {
    private Long id;
    private SourcesEnum sourceName;
    private Date baseDate;
    private String rawDate;
    private String min;
    private String max;

    public Date getBaseDate() {
        return baseDate;
    }

    public RawDataDto setBaseDate(Date baseDate) {
        this.baseDate = baseDate;
        return this;
    }

    public RawDataDto setBaseDate(LocalDate baseDate) {
        this.baseDate = Date.valueOf(baseDate);
        return this;
    }

    public String getRawDate() {
        return rawDate;
    }

    public RawDataDto setRawDate(String rawDate) {
        this.rawDate = rawDate;
        return this;
    }

    public String getMin() {
        return min;
    }

    public RawDataDto setMin(String min) {
        this.min = min;
        return this;
    }

    public String getMax() {
        return max;
    }

    public RawDataDto setMax(String max) {
        this.max = max;
        return this;
    }

    public SourcesEnum getSourceName() {
        return sourceName;
    }

    public RawDataDto setSourceName(SourcesEnum sourceName) {
        this.sourceName = sourceName;
        return this;
    }

    @Override
    public String toString() {
        return "RawDataDto{" +
                "sourceName=" + sourceName.toString() +
                ", baseDate=" + baseDate +
                ", rawDate='" + rawDate + '\'' +
                ", min='" + min + '\'' +
                ", max='" + max + '\'' +
                '}';
    }
}
