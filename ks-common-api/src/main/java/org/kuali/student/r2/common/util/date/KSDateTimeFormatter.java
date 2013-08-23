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
    protected String pattern;

    /**
     * Creates a new formatter
     *
     * @param pattern  regex pattern for the format
     */
    public KSDateTimeFormatter(String pattern) {
        try{
            this.pattern = pattern;

            this.formatter = DateTimeFormat.forPattern(pattern);
        }
        catch (IllegalArgumentException ex){

            throw new IllegalArgumentException("Illegal pattern cannot be parsed. pattern["+ pattern +"].");
        }

    }

    /**
     * Helper method that allows for a straight string to java.util.Date conversion.
     * returns null if it cannot parse the string
     *
     * @param stringDate
     * @return A java.util.Date, or null if the input could not be parsed
     * @throws UnsupportedOperationException if parsing is not supported
     * @throws IllegalArgumentException if the text to parse is invalid
     */
    public Date parse(String stringDate){
        Date dRet = null;

        try{
            DateTime dt = formatter.parseDateTime(stringDate);
            dRet = dt.toDate();
        }catch (IllegalArgumentException ex){
            dRet = null;
            throw new IllegalArgumentException(stringDate + " cannot be parsed with pattern["+ this.pattern +"].");
        }

        return dRet;
    }

    /**
     * Helper method that allows a stright  java.util.Date to String converstion
     * @param javaDate
     * @return
     * @throws IllegalArgumentException if the javaDate is invalid
     */
    public String format(Date javaDate){
        return this.formatter.print(new DateTime(javaDate));
    }

    /**
     * Helper method that allows a stright  java.util.Date to String converstion
     * @param strDate
     * @return
     * @throws IllegalArgumentException if the javaDate is invalid
     */
    public String format(String strDate){
        return this.formatter.print(new DateTime(strDate));
    }

    /**
     * Get underlying Jada formatter
     *
     * @return
     */
    public DateTimeFormatter getFormatter(){
        return formatter;
    }

    /**
     * This returns the pattern used to create the formatter
     *
     * @return
     */
    public String getPattern() {
        return pattern;
    }
}
