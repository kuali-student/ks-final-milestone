package org.kuali.student.r2.common.util.date;

/**
 * Created with IntelliJ IDEA.
 * User: gtaylor
 * Date: 11/6/12
 * Time: 1:44 PM
 * <p/>
 * This file contains a default list of usable date format strings as well as formatters that can be called from code.
 * It was created because we were using SimpleDateFormat throughout the system and that is both expensive and not
 * thread safe.
 */
public class DateFormatters {


    /**
     * ***********************************************************
     * MAKE SURE YOU ADD ALL NEW FORMATS TO THE UNIT TEST CLASS: KSDateFormatterTest.java
     * ***********************************************************
     */


    // List of formats used in KS
    public static final String STATE_CHANGE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";                   // 2012-11-08T14:52:26-0500
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";                                    // 2012-11-08
    public static final String DEFAULT_TIMESTAMP_FORMAT = "yyyy-MM-dd-hh.mm.ss";                      // 2012-11-08-02.52.26
    public static final String SERVER_DATE_PARSER_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";              // 2012-11-08T14:52:26.564-0500
    public static final String DYNAMIC_ATTRIBUTE_DATE_FORMAT = "yyyy MMM d HH:mm:ss ZZZ";             // 2012 Nov 8 15:05:19 America/New_York
    public static final String MONTH_DAY_YEAR_TIME_DATE_FORMAT = "MM/dd/yyyy hh:mm aa";               // 11/08/2012 02:52 PM
    public static final String MONTH_DAY_YEAR_DATE_FORMAT = "MM/dd/yyyy";                             // 11/08/2012
    public static final String YEAR_MONTH_DAY_CONCAT_DATE_FORMAT = "yyyyMMdd";                        // 20121108
    public static final String COURSE_OFFERING_VIEW_HELPER_DATE_FORMAT = "EEE, MMMMM d, yyyy";        // Thu, November 8, 2012
    public static final String COURSE_OFFERING_VIEW_HELPER_DATE_TIME_FORMAT = "MMMMM d, yyyy, h:mm a";// November 8, 2012, 2:52 PM
    public static final String HOUR_MINUTE_TIME_FORMAT = "hh:mm";                                     // 02:52
    public static final String HOUR_MINUTE_AM_PM_TIME_FORMAT = "hh:mm a";                             // 02:52 PM
    public static final String AM_PM_TIME_FORMAT = "a";                                               // PM
    public static final String DEFULT_YEAR_FORMAT = "yyyy";                                           // 2012
    public static final String SIMPLE_TIMESTAMP_FORMAT = "MM/dd/yyyy HH:mm:ss";                       // 11/08/2012 02:52:26
    public static final String DEFAULT_MONTH_YEAR_TIME_DATE_FORMAT = "MM-dd-yyyy hh:mm a";            // 11-08-2012 02:52 pm
    public static final String DEFAULT_YEAR_MONTH_24HOUR_MILLISECONDS_FORMAT = "yyyy-MM-dd HH:mm:ss.S";                  // 2012-08-11 02:52:26.9

    /**
     * DO NOT USE THIS CONSTANT unless you absolutly have to and for Date->String only.
     * The lower case z tells the formatter to use a 'short' timezone. ie. EST. This can
     * be format printted, but not parsed. EST is ambigious and cannot be parsed from a String to a Date.
     */
    public static final String DYNAMIC_ATTRIBUTE_SHORT_ZONE_DATE_FORMAT = "yyyy MMM d HH:mm:ss z";    // 2012 Nov 8 15:05:19 EST


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
    public static final KSDateTimeFormatter HOUR_MINUTE_AM_PM_TIME_FORMATTER = new KSDateTimeFormatter(HOUR_MINUTE_AM_PM_TIME_FORMAT);
    public static final KSDateTimeFormatter AM_PM_TIME_FORMATTER = new KSDateTimeFormatter(AM_PM_TIME_FORMAT);
    public static final KSDateTimeFormatter DEFULT_YEAR_FORMATTER = new KSDateTimeFormatter(DEFULT_YEAR_FORMAT);
    public static final KSDateTimeFormatter SIMPLE_TIMESTAMP_FORMATTER = new KSDateTimeFormatter(SIMPLE_TIMESTAMP_FORMAT);
    public static final KSDateTimeFormatter DEFAULT_MONTH_YEAR_TIME_DATE_FORMATTER = new KSDateTimeFormatter(DEFAULT_MONTH_YEAR_TIME_DATE_FORMAT);
    public static final KSDateTimeFormatter DEFAULT_YEAR_MONTH_24HOUR_MILLISECONDS_FORMATTER = new KSDateTimeFormatter(DEFAULT_YEAR_MONTH_24HOUR_MILLISECONDS_FORMAT);


}
