package com.wmeimon.api.fbssw.holder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @param
 * @author Ricky Li
 * @version 1.0
 * @date 2021-07-22 23:51
 */
public class DateUtils {

    /**
     * 要用到的DATE Format的定义
     */
    public static final String DATE_FORMAT_DATEONLY = "yyyy-MM-dd";
    public static final String DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String SHORTDATEFORMAT = "yyyyMMdd";
    public static final String HMS_FORMAT = "HH:mm:ss";

    public static String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT_DATETIME));
    }
}
