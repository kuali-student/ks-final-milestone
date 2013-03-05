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
    private int year;
    private int term;

    private String[] termNames = {"Winter","Spring","Summer1", "Summer2", "Fall","Summer I", "Summer II"};
    private int[] termIndexes = {1,2,3,4,5,3,4};
    private final int MAX_TERM_INDEX = 5;

    public DefaultYearTerm(int year, int term) {
        this.year = year;
        this.term = term;
    }

    public DefaultYearTerm(String year, String term) {
        this.year = Integer.parseInt(year);
        try{
            this.term = Integer.parseInt(term);
        }catch(NumberFormatException e){
            for(int x = 0;x<termNames.length;x++){
                if(termNames[x].equalsIgnoreCase(term)){
                    this.term=this.termIndexes[x];
                    return;
                }
            }
        }

    }

    public DefaultYearTerm(String atpId) {
        if(atpId.contains("kuali.atp.")){
            String tempAtpId=atpId.replaceAll("kuali.atp.","");

            String year = tempAtpId.substring(0,4);
            this.year = Integer.parseInt(year);

            String tempName = tempAtpId.substring(4);
            for(int x = 0;x<termNames.length;x++){
                if(termNames[x].equals(tempName)){
                    this.term=this.termIndexes[x];
                    return;
                }
            }
        }

        String yearString = atpId.substring(atpId.lastIndexOf(" "));
        String termString = atpId.substring(0,atpId.lastIndexOf(" "));
        YearTerm temp = new DefaultYearTerm (Integer.parseInt(yearString.trim()),termString.trim());
        this.year=temp.getYear();
        this.term=temp.getTerm();

    }
    public DefaultYearTerm(int year, String term){
        this.year=year;

        for(int x = 0;x<termNames.length;x++){
            if(termNames[x].equalsIgnoreCase(term)){
                this.term=this.termIndexes[x];
                return;
            }
        }
        this.term=0;

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
    public String getTermAsString(){
        return Integer.toString(getTerm());
    }
    /**
     * Converts year and term to the ATP idea using the string pattern defined in ATP_Format
     * @return "20131"
     */
    @Override
    public String toATP() {
        return "kuali.atp."+this.year+this.getTermNameList()[this.term-1];
    }

    @Override
    public String toTermName() {
        return termNames[(getTerm() - 1)] + " " + getYearAsString();
    }

    @Override
    public String[] getTermNameList() {
        return this.termNames;
    }

    // "WIN+1999"
    @Override
    public String toQTRYRParam() {
        return this.getTermNameList()[getTerm() - 1].substring(0, 3).toUpperCase() + "+" + getYearAsString();
    }

    @Override
    public int compareTo( YearTerm that ) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;
        if( this == that ) return EQUAL;
        int a = this.year * 10 + this.term;
        int b = that.getYear() * 10 + that.getTerm();
        if( a > b ) return BEFORE;
        if( a < b ) return AFTER;
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
        return "year: " + year + " term: " + term + " (" + termNames[term-1] +")";
    }

    @Override
    public int getMAX_TERM_INDEX(){
        return this.MAX_TERM_INDEX;
    }
}
