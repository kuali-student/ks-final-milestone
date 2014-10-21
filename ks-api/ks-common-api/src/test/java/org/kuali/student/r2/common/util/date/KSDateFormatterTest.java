package org.kuali.student.r2.common.util.date;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: gtaylor
 * Date: 11/8/12
 * Time: 10:34 AM
 * Any time you add a new format, make sure you test and make sure it works.
 */
public class KSDateFormatterTest {

    @Test
    public void testDateFormatters(){
        Date dateTime = new DateTime().toDate();

        printFormat(DateFormatters.STATE_CHANGE_DATE_FORMAT, dateTime);
        printFormat(DateFormatters.DEFAULT_DATE_FORMAT, dateTime);
        printFormat(DateFormatters.DEFAULT_TIMESTAMP_FORMAT, dateTime);
        printFormat(DateFormatters.SERVER_DATE_PARSER_FORMAT, dateTime);
        printFormat(DateFormatters.DYNAMIC_ATTRIBUTE_DATE_FORMAT, dateTime);
        printFormat(DateFormatters.MONTH_DAY_YEAR_TIME_DATE_FORMAT, dateTime);
        printFormat(DateFormatters.MONTH_DAY_YEAR_DATE_FORMAT, dateTime);
        printFormat(DateFormatters.MONTH_NOZERO_DAY_YEAR_DATE_FORMAT, dateTime);
        printFormat(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMAT, dateTime);
        printFormat(DateFormatters.COURSE_OFFERING_VIEW_HELPER_DATE_FORMAT, dateTime);
        printFormat(DateFormatters.COURSE_OFFERING_VIEW_HELPER_DATE_TIME_FORMAT, dateTime);
        printFormat(DateFormatters.HOUR_MINUTE_TIME_FORMAT, dateTime);
        printFormat(DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMAT, dateTime);
        printFormat(DateFormatters.HOUR_NOZERO_MINUTE_AM_PM_TIME_FORMAT, dateTime);
        printFormat(DateFormatters.HOUR_MINUTE_NOSPACE_AM_PM_TIME_FORMAT, dateTime);
        printFormat(DateFormatters.AM_PM_TIME_FORMAT, dateTime);
        printFormat(DateFormatters.DEFULT_YEAR_FORMAT, dateTime);
        printFormat(DateFormatters.SIMPLE_TIMESTAMP_FORMAT, dateTime);
        printFormat(DateFormatters.DEFAULT_MONTH_YEAR_TIME_DATE_FORMAT, dateTime);
        printFormat(DateFormatters.DEFAULT_YEAR_MONTH_24HOUR_MILLISECONDS_FORMAT, dateTime);
        printFormat(DateFormatters.SHORTMONTH_DAY_FORMAT, dateTime);
        printFormat(DateFormatters.DAY_SHORTMONTH_DATE_FORMAT, dateTime);
        printFormat(DateFormatters.DAY_MONTH_DATE_FORMAT, dateTime);
        printFormat(DateFormatters.DAY_MONTH_DATE_YEAR_FORMAT, dateTime);
        printFormat(DateFormatters.MONTH_DAY_FORMAT, dateTime);

        // Can only format. Parse does not work with short time zone notation
        printFormatOnly(DateFormatters.DYNAMIC_ATTRIBUTE_SHORT_ZONE_DATE_FORMAT, dateTime);



    }

    @Test
    public void parseTest(){
        String dateStr = "2014-06-28 16:32:46.368";     // This timestamp was getting errors so we created a test.

        DateFormatters.DEFAULT_YEAR_MONTH_24HOUR_MILLISECONDS_FORMATTER.parse(dateStr);

    }

    private void printFormatOnly(String format, Date dateTime){
        KSDateTimeFormatter kdf = new KSDateTimeFormatter(format);

        kdf.format(dateTime);
    }

    private void printFormat(String format, Date dateTime)   {

        KSDateTimeFormatter kdf = new KSDateTimeFormatter(format);

        kdf.format(dateTime);

        kdf.format(kdf.parse(kdf.format(dateTime)));
    }

}
