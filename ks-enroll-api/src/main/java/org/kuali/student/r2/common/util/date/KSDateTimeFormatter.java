package org.kuali.student.r2.common.util.date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Helper class that wraps jada's DateTimeFormatter. Has helper methods to mimic standard java methods and make
 * refactoring cleaner
 *
 * User: gtaylor
 * Date: 11/6/12
 * Time: 2:47 PM
 */
public class KSDateTimeFormatter  {

     protected DateTimeFormatter formatter;

    /**
     * Creates a new formatter
     *
     * @param pattern  regex pattern for the format
     */
    public KSDateTimeFormatter(String pattern) {
        formatter = DateTimeFormat.forPattern(pattern);
    }

    /**
     * Helper method that allows for a straight string to java.util.Date conversion
     *
     * @param stringDate
     * @return
     */
    public Date parse(String stringDate){
        return formatter.parseDateTime(stringDate).toDate();
    }

    /**
     * Helper method that allows a stright  java.util.Date to String converstion
     * @param javaDate
     * @return
     */
    public String format(Date javaDate){
        return this.formatter.print(new DateTime(javaDate));
    }

    /**
     * Get underlying Jada formatter
     *
     * @return
     */
    public DateTimeFormatter getFormatter(){
        return formatter;
    }

}
