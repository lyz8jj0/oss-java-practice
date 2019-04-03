package com.lxy.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateTimeUtil {

    public static final String DAY_FORMAT = "yyyy-MM-dd";
    public static final String FULL_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String getLocalDate(LocalDate fromDate) {
        return getLocalDate(fromDate, DAY_FORMAT);
    }

    /**
     * LocalDate转化为指定格式字符串
     *
     * @param fromDate
     * @param dateFormat
     * @return
     */
    public static String getLocalDate(LocalDate fromDate, String dateFormat) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat);
        if (fromDate != null) {
            String dateStr = fromDate.format(df);
            return dateStr;
        }
        return null;

    }

    public static String getLocalDateTime(LocalDateTime fromDateTime) {
        return getLocalDateTime(fromDateTime, FULL_FORMAT);
    }

    /**
     * LocalDateTime转化为指定格式字符串
     *
     * @param fromDateTime
     * @param dateTimeFotmat
     * @return
     */
    public static String getLocalDateTime(LocalDateTime fromDateTime, String dateTimeFotmat) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateTimeFotmat);
        if (fromDateTime != null) {
            String localTime = fromDateTime.format(df);
            return localTime;
        }
        return null;

    }

    public static LocalDate fromString2LocalDate(String beginDate) {
        return fromString2LocalDate(beginDate, DAY_FORMAT);
    }

    /**
     * 时间格式字符串转化为指定格式的时间
     *
     * @param beginDate
     * @param dateFormat
     * @return
     */
    public static LocalDate fromString2LocalDate(String beginDate, String dateFormat) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat);
        try {
            LocalDate fromDate = LocalDate.parse(beginDate, df);
            return fromDate;
        } catch (Exception e) {
            return null;
        }

    }

    public static LocalDateTime fromString2LocalDateTime(String beginDateTime) {
        return fromString2LocalDateTime(beginDateTime, FULL_FORMAT);
    }

    /**
     * 时间格式字符串转化为指定格式的时间
     *
     * @param beginDateTime
     * @param dateFormat
     * @return
     */
    public static LocalDateTime fromString2LocalDateTime(String beginDateTime, String dateFormat) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat);
        try {
            LocalDateTime fromDateTime = LocalDateTime.parse(beginDateTime, df);
            return fromDateTime;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取精确到秒的时间戳
     *
     * @return
     */
    public static String getMilliSecondTimestamp() {

        return String.valueOf(new Date().getTime());
    }


    /**
     * Date类型转LocalDate类型
     *
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localPriceDate = instant.atZone(zoneId).toLocalDate();
        return localPriceDate;
    }

    /**
     * LocalDate类型转Date类型
     *
     * @param localDate
     * @return Date
     */
    public static Date localDateToDate(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }

}
