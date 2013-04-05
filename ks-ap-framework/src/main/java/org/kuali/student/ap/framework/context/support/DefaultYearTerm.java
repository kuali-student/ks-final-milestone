package org.kuali.student.ap.framework.context.support;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.YearTerm;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.atp.dto.AtpInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Data Storage for the Term and Year of a single atp.
 * Formats different output forms of the data
 */
public class DefaultYearTerm implements YearTerm,Comparable<YearTerm> {
    private int year;
    private int term;

    public DefaultYearTerm(int year, int term) {
        this.year = year;
        this.term = term;
    }

    public DefaultYearTerm(String year, String term) {
        this.year = Integer.parseInt(year);
        try{
            this.term = Integer.parseInt(term);
        }catch(NumberFormatException e){
            this.term= getTermMap().get(term.toLowerCase());
            return;
        }

    }

    public DefaultYearTerm(String atpId) {

        TermInfo term;
        String termName;
        try {
            term = KsapFrameworkServiceLocator
                    .getAcademicCalendarService().getTerm(atpId, KsapFrameworkServiceLocator.getContext().getContextInfo());
            assert term != null : "No term from acal service";
            if (term != null) {
                termName=term.getName();
            }else{
                termName=atpId;
            }
        } catch (Throwable t) {
           termName=atpId;
        }
        String newTerm = termName.substring(0,termName.lastIndexOf(" "));
        String newYear = termName.substring(termName.lastIndexOf(" "));
        this.year=Integer.parseInt(newYear.trim());
        try{
            this.term = Integer.parseInt(newTerm);
        }catch(NumberFormatException e){
            this.term=getTermMap().get(newTerm.toLowerCase());
            return;
        }
    }
    public DefaultYearTerm(int year, String term){
        this.year=year;

        this.term=getTermMap().get(term.toLowerCase());
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
    //will need to handle the having types summer1, summer2 and summer
    //Look at adding a map for this.
    @Override
    public String toATP() {
        List<AtpInfo> atpInfos = null;
        String type = getAtpTypeMap().get(term);

        Date date =  DateFormatters.DEFAULT_DATE_FORMATTER.parse(year+"-"+getTypeMonthDayMap().get(term));

        try {
            atpInfos = KsapFrameworkServiceLocator.getAtpService().getAtpsByDateAndType
                    (date,type,KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (Throwable t) {
            if (t instanceof RuntimeException)
                throw (RuntimeException) t;
            if (t instanceof Error)
                throw (Error) t;
            throw new IllegalStateException(
                    "Unexpected error in ATP lookup", t);
        }
        if(atpInfos!=null && atpInfos.size()>0){
            return atpInfos.get(0).getId();
        }
        return "";
    }

    @Override
    public String toTermName() {
        return getTermNameList()[(getTerm() - 1)] + " " + getYearAsString();
    }

    @Override
    public String[] getTermNameList() {
        Map<String,Integer> termMap = getTermMap();
        String termNames[] = new String[termMap.size()];
        Iterator<Map.Entry<String,Integer>> iter = termMap.entrySet().iterator();
        for(int i=0;i<termMap.size();i++){
            termNames[i]=iter.next().getKey();
        }

        //String[] termNames = {"Winter","Spring","Summer1", "Summer2", "Fall","Summer I", "Summer II"};
        return termNames;
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

        return "year: " + year + " term: " + term + " (" + getTermNameList()[term-1] +")";
    }

    @Override
    public int getMAX_TERM_INDEX(){
        return getAtpTypeMap().size();
    }

    public Map<String,Integer> getTermMap(){
        Map termMap = KsapFrameworkServiceLocator.getAtpHelper().getTermMap();
        if(termMap==null){
            termMap = new HashMap<String,Integer>();
            termMap.put("winter",1);
            termMap.put("spring",2);
            termMap.put("summer1",3);
            termMap.put("summer2",4);
            termMap.put("fall",5);
            termMap.put("summer i",3);
            termMap.put("summer ii",4);
            termMap.put("summer",3);
        }

        return termMap;
    }

    public Map<Integer,String> getAtpTypeMap(){
        Map<Integer,String> types = KsapFrameworkServiceLocator.getAtpHelper().getAtpTypeMap();
        if(types == null){
            types = new HashMap<Integer,String>();
            String typeBase = "kuali.atp.type.";
            types.put(1,typeBase+"Winter");
            types.put(2,typeBase+"Spring");
            types.put(3,typeBase+"Summer1");
            types.put(4,typeBase+"Summer2");
            types.put(5,typeBase+"Fall");
        }
        return types;
    }

    public Map<Integer,String> getTypeMonthDayMap(){
        Map<Integer,String> types= KsapFrameworkServiceLocator.getAtpHelper().getTypeMonthDayMap();
        if(types == null){
            types = new HashMap<Integer,String>();
            types.put(1,"01-15");
            types.put(2,"03-01");
            types.put(3,"06-15");
            types.put(4,"08-01");
            types.put(5,"10-01");
        }
        return types;
    }



}
