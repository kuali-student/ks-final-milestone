package org.kuali.student.r2.common.util.date;

/**
 * Created with IntelliJ IDEA.
 * User: swedev
 * Date: 11/6/12
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class DateFormatters {

    // List of formats used in KS
    public static final String STATE_CHANGE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_TIMESTAMP_FORMAT = "yyyy-MM-dd-hh.mm.ss";
    public static final String SERVER_DATE_PARSER_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String DYNAMIC_ATTRIBUTE_DATE_FORMAT = "yyyy MMM d HH:mm:ss zzz";
    public static final String MONTH_DAY_YEAR_TIME_DATE_FORMAT = "MM/dd/yyyy hh:mm aa";
    public static final String MONTH_DAY_YEAR_DATE_FORMAT = "MM/dd/yyyy";
    public static final String YEAR_MONTH_DAY_CONCAT_DATE_FORMAT =  "yyyyMMdd";
    public static final String COURSE_OFFERING_VIEW_HELPER_DATE_FORMAT ="EEE, MMMMM d, yyyy";
    public static final String COURSE_OFFERING_VIEW_HELPER_DATE_TIME_FORMAT = "MMMMM d, yyyy, h:mm a";
    public static final String HOUR_MINUTE_TIME_FORMAT = "hh:mm";
    public static final String AM_PM_TIME_FORMAT = "a";


    // Fast Thread Safe Formatter. Use this instead of SimpleDateFormat
    public static final KSDateTimeFormatter STATE_CHANGE_DATE_FORMATTER = new KSDateTimeFormatter(STATE_CHANGE_DATE_FORMAT);
    public static final KSDateTimeFormatter DEFAULT_DATE_FORMATTER = new KSDateTimeFormatter(DEFAULT_DATE_FORMAT);
    public static final KSDateTimeFormatter DEFAULT_TIMESTAMP_FORMATTER = new KSDateTimeFormatter(DEFAULT_TIMESTAMP_FORMAT);
    public static final KSDateTimeFormatter SERVER_DATE_PARSER_FORMATTER = new KSDateTimeFormatter(SERVER_DATE_PARSER_FORMAT);
    public static final KSDateTimeFormatter DYNAMIC_ATTRIBUTE_DATE_FORMATTER = new KSDateTimeFormatter(DYNAMIC_ATTRIBUTE_DATE_FORMAT);
    public static final KSDateTimeFormatter MONTH_DAY_YEAR_TIME_DATE_FORMATTER = new KSDateTimeFormatter(MONTH_DAY_YEAR_TIME_DATE_FORMAT);
    public static final KSDateTimeFormatter MONTH_DAY_YEAR_DATE_FORMATTER = new KSDateTimeFormatter(MONTH_DAY_YEAR_DATE_FORMAT);
    public static final KSDateTimeFormatter YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER = new KSDateTimeFormatter(YEAR_MONTH_DAY_CONCAT_DATE_FORMAT);
    public static final KSDateTimeFormatter COURSE_OFFERING_VIEW_HELPER_DATE_FORMATTER = new KSDateTimeFormatter(COURSE_OFFERING_VIEW_HELPER_DATE_FORMAT);
    public static final KSDateTimeFormatter COURSE_OFFERING_VIEW_HELPER_DATE_TIME_FORMATTER = new KSDateTimeFormatter(COURSE_OFFERING_VIEW_HELPER_DATE_TIME_FORMAT);
    public static final KSDateTimeFormatter HOUR_MINUTE_TIME_FORMATTER = new KSDateTimeFormatter(HOUR_MINUTE_TIME_FORMAT);
    public static final KSDateTimeFormatter AM_PM_TIME_FORMATTER = new KSDateTimeFormatter(AM_PM_TIME_FORMAT);




}
