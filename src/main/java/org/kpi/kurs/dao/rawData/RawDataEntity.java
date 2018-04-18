package org.kpi.kurs.dao.rawData;


import org.kpi.kurs.web.rawData.SourcesEnum;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "rawdata")
public class RawDataEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private SourcesEnum sourceId;
    private Date baseDate;
    private double firstDayMinTemp;
    private double firstDayMaxTemp;
    private double secondDayMinTemp;
    private double secondDayMaxTemp;
    private double thirdDayMinTemp;
    private double thirdDayMaxTemp;
    private double fourthDayMinTemp;
    private double fourthDayMaxTemp;
    private double fifthDayMinTemp;
    private double fifthDayMaxTemp;
    private double sixDayMinTemp;
    private double sixDayMaxTemp;
    private double sevenDayMinTemp;
    private double sevenDayMaxTemp;

    public RawDataEntity() {
    }

    public Long getId() {
        return id;
    }

    public RawDataEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public SourcesEnum getSourceName() {
        return sourceId;
    }

    public RawDataEntity setSourceName(SourcesEnum sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public Date getBaseDate() {
        return baseDate;
    }

    public RawDataEntity setBaseDate(Date baseDate) {
        this.baseDate = baseDate;
        return this;
    }

    public double getFirstDayMinTemp() {
        return firstDayMinTemp;
    }

    public RawDataEntity setFirstDayMinTemp(double firstDayMinTemp) {
        this.firstDayMinTemp = firstDayMinTemp;
        return this;
    }

    public double getFirstDayMaxTemp() {
        return firstDayMaxTemp;
    }

    public RawDataEntity setFirstDayMaxTemp(double firstDayMaxTemp) {
        this.firstDayMaxTemp = firstDayMaxTemp;
        return this;
    }

    public double getSecondDayMinTemp() {
        return secondDayMinTemp;
    }

    public RawDataEntity setSecondDayMinTemp(double secondDayMinTemp) {
        this.secondDayMinTemp = secondDayMinTemp;
        return this;
    }

    public double getSecondDayMaxTemp() {
        return secondDayMaxTemp;
    }

    public RawDataEntity setSecondDayMaxTemp(double secondDayMaxTemp) {
        this.secondDayMaxTemp = secondDayMaxTemp;
        return this;
    }

    public double getThirdDayMinTemp() {
        return thirdDayMinTemp;
    }

    public RawDataEntity setThirdDayMinTemp(double thirdDayMinTemp) {
        this.thirdDayMinTemp = thirdDayMinTemp;
        return this;
    }

    public double getThirdDayMaxTemp() {
        return thirdDayMaxTemp;
    }

    public RawDataEntity setThirdDayMaxTemp(double thirdDayMaxTemp) {
        this.thirdDayMaxTemp = thirdDayMaxTemp;
        return this;
    }

    public double getFourthDayMinTemp() {
        return fourthDayMinTemp;
    }

    public RawDataEntity setFourthDayMinTemp(double fourthDayMinTemp) {
        this.fourthDayMinTemp = fourthDayMinTemp;
        return this;
    }

    public double getFourthDayMaxTemp() {
        return fourthDayMaxTemp;
    }

    public RawDataEntity setFourthDayMaxTemp(double fourthDayMaxTemp) {
        this.fourthDayMaxTemp = fourthDayMaxTemp;
        return this;
    }

    public double getFifthDayMinTemp() {
        return fifthDayMinTemp;
    }

    public RawDataEntity setFifthDayMinTemp(double fifthDayMinTemp) {
        this.fifthDayMinTemp = fifthDayMinTemp;
        return this;
    }

    public double getFifthDayMaxTemp() {
        return fifthDayMaxTemp;
    }

    public RawDataEntity setFifthDayMaxTemp(double fifthDayMaxTemp) {
        this.fifthDayMaxTemp = fifthDayMaxTemp;
        return this;
    }

    public double getSixDayMinTemp() {
        return sixDayMinTemp;
    }

    public RawDataEntity setSixDayMinTemp(double sixDayMinTemp) {
        this.sixDayMinTemp = sixDayMinTemp;
        return this;
    }

    public double getSixDayMaxTemp() {
        return sixDayMaxTemp;
    }

    public RawDataEntity setSixDayMaxTemp(double sixDayMaxTemp) {
        this.sixDayMaxTemp = sixDayMaxTemp;
        return this;
    }

    public double getSevenDayMinTemp() {
        return sevenDayMinTemp;
    }

    public RawDataEntity setSevenDayMinTemp(double sevenDayMinTemp) {
        this.sevenDayMinTemp = sevenDayMinTemp;
        return this;
    }

    public double getSevenDayMaxTemp() {
        return sevenDayMaxTemp;
    }

    public RawDataEntity setSevenDayMaxTemp(double sevenDayMaxTemp) {
        this.sevenDayMaxTemp = sevenDayMaxTemp;
        return this;
    }

    @Override
    public String toString() {
        return "RawDataEntity{" +
                "id=" + id +
                ", sourceName=" + sourceId +
                ", baseDate=" + baseDate +
                ", firstDayMinTemp=" + firstDayMinTemp +
                ", firstDayMaxTemp=" + firstDayMaxTemp +
                ", secondDayMinTemp=" + secondDayMinTemp +
                ", secondDayMaxTemp=" + secondDayMaxTemp +
                ", thirdDayMinTemp=" + thirdDayMinTemp +
                ", thirdDayMaxTemp=" + thirdDayMaxTemp +
                ", fourthDayMinTemp=" + fourthDayMinTemp +
                ", fourthDayMaxTemp=" + fourthDayMaxTemp +
                ", fifthDayMinTemp=" + fifthDayMinTemp +
                ", fifthDayMaxTemp=" + fifthDayMaxTemp +
                ", sixDayMinTemp=" + sixDayMinTemp +
                ", sixDayMaxTemp=" + sixDayMaxTemp +
                ", sevenDayMinTemp=" + sevenDayMinTemp +
                ", sevenDayMaxTemp=" + sevenDayMaxTemp +
                '}';
    }
}
