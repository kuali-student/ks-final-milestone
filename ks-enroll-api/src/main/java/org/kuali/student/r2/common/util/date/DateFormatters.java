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


    // Fast Thread Safe Formatter. Use this instead of SimpleDateFormat
    public static final KSDateTimeFormatter STATE_CHANGE_DATE_FORMATTER = new KSDateTimeFormatter(STATE_CHANGE_DATE_FORMAT);


}
