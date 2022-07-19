package com.hsd.common;

import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public final class DateUtil {

    private static final String HHMM = "HH:mm";
    private static final String STRIKE_MONTH_DAY_TIME_CHINESE = "MM月dd日 HH:mm";

    public static Date add(Date source, long amountToAdd, TemporalUnit unit) {
        LocalDateTime sourceLocalDateTime = convertToLocalDateTime(source);
        LocalDateTime destLocalDateTime = sourceLocalDateTime.plus(amountToAdd, unit);
        return convertToDate(destLocalDateTime);
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    private static LocalDateTime convertToLocalDateTime(Date source) {
        return source.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private static Date convertToDate(LocalDateTime source) {
        return Date.from(source.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Pair<Date, Date> getStartEndDate(Integer orderMonitorSecondSpan) {
        Date now = new Date();
        Integer spanSeconds = getSpanSeconds(now, orderMonitorSecondSpan);
        Date endDate = DateUtil.add(now, -spanSeconds, ChronoUnit.SECONDS);
        endDate = getDateGiveUpMills(endDate);
        Date startDate = DateUtil.add(endDate, -orderMonitorSecondSpan, ChronoUnit.SECONDS);
        return new Pair<>(startDate, endDate);
    }

    private static Date getDateGiveUpMills(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private static Integer getSpanSeconds(Date date, Integer orderErrorMonitorSpan) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int seconds = calendar.get(Calendar.SECOND);
        int count = 60 / orderErrorMonitorSpan;
        int n = 0;
        for (int i = 0; i < count; i++) {
            if (i * orderErrorMonitorSpan <= seconds && (i + 1) * orderErrorMonitorSpan > seconds) {
                n = i;
                break;
            }
        }
        return seconds - n * orderErrorMonitorSpan;
    }

}
