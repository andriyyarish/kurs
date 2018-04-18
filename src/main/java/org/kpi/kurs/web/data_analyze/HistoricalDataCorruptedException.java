package org.kpi.kurs.web.data_analyze;

public class HistoricalDataCorruptedException extends RuntimeException {
    public HistoricalDataCorruptedException() {
    }

    public HistoricalDataCorruptedException(String message) {
        super(message);
    }
}
