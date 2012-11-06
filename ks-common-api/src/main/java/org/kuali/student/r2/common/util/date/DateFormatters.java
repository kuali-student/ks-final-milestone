package org.kuali.student.r2.common.util.date;

/**
 * Created with IntelliJ IDEA.
 * User: swedev
 * Date: 11/6/12
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class DateFormatters {

    public static final String STATE_CHANGE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_TIMESTAMP_FORMAT = "yyyy-MM-dd-hh.mm.ss";

    // Fast Thread Safe Formatter. Use this instead of SimpleDateFormat
    public static final KSDateTimeFormatter STATE_CHANGE_DATE_FORMATTER = new KSDateTimeFormatter(STATE_CHANGE_DATE_FORMAT);
    public static final KSDateTimeFormatter DEFAULT_DATE_FORMATTER = new KSDateTimeFormatter(DEFAULT_DATE_FORMAT);
    public static final KSDateTimeFormatter DEFAULT_TIMESTAMP_FORMATTER = new KSDateTimeFormatter(DEFAULT_TIMESTAMP_FORMAT);




}
