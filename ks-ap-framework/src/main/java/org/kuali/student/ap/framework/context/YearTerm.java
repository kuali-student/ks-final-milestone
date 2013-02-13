package org.kuali.student.ap.framework.context;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 2/11/13
 * Time: 3:27 PM
 * To change this template use File | Settings | File Templates.
 */
public interface YearTerm {

    public int getYear();

    public String getYearAsString();

    public int getTerm();

    public String getTermAsID() ;

    // "kuali.atp.1999.1"
    public String toATP();
    // "Winter 1999"
    public String toLabel() ;

    // "WIN+1999"
    public String toQTRYRParam() ;

}
