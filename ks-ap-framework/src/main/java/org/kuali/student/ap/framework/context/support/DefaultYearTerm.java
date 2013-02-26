package org.kuali.student.ap.framework.context.support;

import org.kuali.student.ap.framework.context.CourseSearchConstants;
import org.kuali.student.ap.framework.context.YearTerm;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Data Storage for the Term and Year of a single atp.
 * Formats different output forms of the data
 */
public class DefaultYearTerm implements YearTerm,Comparable<YearTerm> {
    private final int year;
    private final int term;

    public DefaultYearTerm(int year, int term) {
        this.year = year;
        this.term = term;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public String getYearAsString() {
        return Integer.toString(getYear());
    }

    @Override
    public int getTerm() {
        return term;
    }
    @Override
    public String getTermAsID() {
        return CourseSearchConstants.TERM_LABELS_LIST.get(getTerm() - 1);
    }

    /**
     * Converts year and term to the ATP idea using the string pattern defined in ATP_Format
     * @return "20131"
     */
    @Override
    public String toATP() {
        return "kuali.atp."+this.year+this.getTermAsID();
        //return String.format(CourseSearchConstants.ATP_FORMAT, year, term);
    }

    // "Winter 1999"
    @Override
    public String toLabel() {
        return CourseSearchConstants.TERM_LABELS_LIST.get(getTerm() - 1) + " " + getYearAsString();
    }

    // "WIN+1999"
    @Override
    public String toQTRYRParam() {
        return CourseSearchConstants.TERM_ID_LIST.get(getTerm() - 1).substring(0, 3).toUpperCase() + "+" + getYearAsString();
    }

    @Override
    public int compareTo( YearTerm that ) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;
        if( this == that ) return EQUAL;
        int a = this.year * 10 + this.term;
        int b = that.getYear() * 10 + that.getTerm();
        if( a < b ) return BEFORE;
        if( a > b ) return AFTER;
        return EQUAL;
    }

    @Override
    public boolean equals( Object obj ) {
        if( this == obj ) return true;
        if( obj instanceof YearTerm ) {
            YearTerm that = (YearTerm) obj;
            return this.year == that.getYear() && this.term == that.getTerm();
        }
        return false;
    }
    public String toString() {
        return "year: " + year + " term: " + term + " (" + getTermAsID() +")";
    }
}
