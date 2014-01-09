package org.kuali.student.myplan.plan.dataobject;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: hemanthg
 * Date: 4/13/12
 * Time: 1:50 PM
 * To change this template use File | Settings | File Templates.
 */

public class FullPlanItemsDataObject {


    private String totalCredits;

    private String yearRange;

    private List<PlannedTerm> terms=new ArrayList<PlannedTerm>();

    private boolean currentYear;

    public String getYearRange() {
        return yearRange;
    }

    public void setYearRange(String yearRange) {
        this.yearRange = yearRange;
    }


    public List<PlannedTerm> getTerms() {
        return terms;
    }

    public void setTerms(List<PlannedTerm> terms) {
        this.terms = terms;
    }

    public String getTotalCredits() {
       return totalCredits;

    }

    public void setTotalCredits(String totalCredits) {
        this.totalCredits = totalCredits;
    }

    public boolean isCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(boolean currentYear) {
        this.currentYear = currentYear;
    }

}
