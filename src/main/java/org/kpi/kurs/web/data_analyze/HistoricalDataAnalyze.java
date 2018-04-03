package org.kpi.kurs.web.data_analyze;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kpi.kurs.dao.preAnalyzedData.TempDiffsEntity;
import org.kpi.kurs.web.rawData.SourcesEnum;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Class should be provided with DataManager object that stores data within date range that should be analyzed
 * Expected date range is up to 7 days
 */
public class HistoricalDataAnalyze {
    private static final Logger logger = LogManager.getLogger(HistoricalDataAnalyze.class);
    private HistoricalDataManager historicalDataManager;
    private HistoricalDataDto baseLineDto;
    private SourcesEnum currentSource;
    private LocalDate baseLineDate;
    private double baseLineMinTemp;
    private double baseLineMaxTemp;
    private final int BACKWARD_DEPTH = 6;

    private List<TempDiffsEntity> comparisonResult;

    public HistoricalDataAnalyze(HistoricalDataManager historicalDataManager) {
        this.historicalDataManager = historicalDataManager;
        comparisonResult = new ArrayList<>();
    }

    public void calculateDifs() {
        initBaseLine();

        compareBaseLineWithHistoricalData();
    }

    private void compareBaseLineWithHistoricalData() {
        logger.debug("Analyzing historical data with baseline -> " + baseLineDto.getBaseDate());
        for (int i = 1; i <= BACKWARD_DEPTH-1; i++) {
            Optional<HistoricalDataDto> oneOfPreviousDate = historicalDataManager.getHistoricalDataDtoByDate(java.sql.Date.valueOf(baseLineDate.minusDays(i)));

            TempDiffsEntity tempDiffsDto = comparePreviousDateWithBaseLine(oneOfPreviousDate.get(), i);
            comparisonResult.add(tempDiffsDto);
        }
        baseLineDto = historicalDataManager.getHistoricalDataDtoWithLatestDate();
        logger.trace("Comparison result:-> " + comparisonResult.toString());
    }

    private TempDiffsEntity comparePreviousDateWithBaseLine(HistoricalDataDto previousDay, int daysBeforeBaseLine) {
        logger.trace("Base date that will be compared with baseline:-> " + previousDay.getBaseDate());
        double previousDayMinTemp = previousDay.getMinTempList().get(daysBeforeBaseLine-1);
        double previousDayMaxTemp = previousDay.getMaxTempList().get(daysBeforeBaseLine-1);

        double minTempDiff = calcAbsoluteDiff(baseLineMinTemp, previousDayMinTemp);
        double maxTempDiff = calcAbsoluteDiff(baseLineMaxTemp, previousDayMaxTemp);

        TempDiffsEntity res = new TempDiffsEntity().setBaselineDate(java.sql.Date.valueOf(baseLineDate))
                .setSource(currentSource)
                .setBackwardDeepness(daysBeforeBaseLine)
                .setMinTempDiff(minTempDiff)
                .setMaxTempDiff(maxTempDiff);
        return res;
    }

    private double calcAbsoluteDiff(double baseLine, double toBeCompared) {
        logger.trace(String.format("Calculating diff of temparature, baseline value is %s, previous value is %s", String.valueOf(baseLine), String.valueOf(toBeCompared)));
        double res = baseLine - toBeCompared;
        return res > 0 ? res : res * -1;

    }

    private void initBaseLine() {
        baseLineDto = historicalDataManager.getHistoricalDataDtoWithLatestDate();
        baseLineDate = convertDateToLocalDate(baseLineDto.getBaseDate());
        baseLineMinTemp = baseLineDto.getMinTempList().get(0);
        baseLineMaxTemp = baseLineDto.getMaxTempList().get(0);
        currentSource = baseLineDto.getSource();
        logger.trace("Baseline data(retrieved as latest dto)" + baseLineDto.toString());
    }

    private LocalDate convertDateToLocalDate(Date date) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
        return localDate;
    }

    public List<TempDiffsEntity> getComparisonResult() {
        return comparisonResult;
    }
}
