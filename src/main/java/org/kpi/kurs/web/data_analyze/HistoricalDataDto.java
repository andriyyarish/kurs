package org.kpi.kurs.web.data_analyze;

import org.kpi.kurs.web.rawData.SourcesEnum;

import java.util.ArrayList;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class HistoricalDataDto {
    private Date baseDate;
    private SourcesEnum source;
    private List<Double> minTempList;
    private List<Double> maxTempList;

    public HistoricalDataDto(Date baseDate, SourcesEnum source) {
        this.baseDate = baseDate;
        this.source = source;
//        minTempList = new ArrayList<>();
//        maxTempList = new ArrayList<>();
        minTempList = new ArrayList<>();
        minTempList.addAll(Arrays.asList(777.0, 777.0, 777.0, 777.0, 777.0, 777.0));
        maxTempList = new ArrayList<>();
        maxTempList.addAll(Arrays.asList(777.0, 777.0, 777.0, 777.0, 777.0, 777.0));
    }

    public HistoricalDataDto(HistoricalDataDto copyFrom){
        this.baseDate = copyFrom.getBaseDate();
        this.source = copyFrom.getSource();
        minTempList = new ArrayList<>();
        minTempList.addAll(Arrays.asList(777.0, 777.0, 777.0, 777.0, 777.0, 777.0));
        maxTempList = new ArrayList<>();
        maxTempList.addAll(Arrays.asList(777.0, 777.0, 777.0, 777.0, 777.0, 777.0));
    }

    public List<Double> getMinTempList() {
        return minTempList;
    }

    public List<Double> getMaxTempList() {
        return maxTempList;
    }

    public void addValueToMinTempList(int index, double value){
        minTempList.add(index, value);
    }

    public void addValueToMaxTempList(int index, double value){
        maxTempList.add(index, value);
    }

    public Date getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(Date baseDate) {
        this.baseDate = baseDate;
    }

    public SourcesEnum getSource() {
        return source;
    }

    @Override
    public String toString() {
        return "HistoricalDataDto{" +
                "baseDate=" + baseDate +
                ", source=" + source +
                ", minTempList=" + minTempList +
                ", maxTempList=" + maxTempList +
                '}';
    }
}
