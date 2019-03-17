package org.kpi.kurs.web.data_analyze;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static LocalDate convertDateToLocalDate(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        LocalDate localDate = LocalDate.of(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH)+1,
                cal.get(Calendar.DAY_OF_MONTH)); // instant.atZone(defaultZoneId).toLocalDate();
        return localDate;
    }
}
