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


    // TODO: KRAD workaround to enable a TableLayoutManager
    public PlannedTerm getTerm0() {
        if(terms.size() == 0) throw new IndexOutOfBoundsException("Max terms per year range available: " + terms.size());
        return terms.get(0);
    }

    public PlannedTerm getTerm1() {
        if(terms.size() <= 1) throw new IndexOutOfBoundsException("Max terms per year range available: " + terms.size());
        return terms.get(1);
    }

    public PlannedTerm getTerm2() {
        if(terms.size() <= 2) throw new IndexOutOfBoundsException("Max terms per year range available: " + terms.size());
        return terms.get(2);
    }

    public PlannedTerm getTerm3() {
        if(terms.size() <= 3) throw new IndexOutOfBoundsException("Max terms per year range available: " + terms.size());
        return terms.get(3);
    }

    public PlannedTerm getTerm4() {
        if(terms.size() <= 4) throw new IndexOutOfBoundsException("Max terms per year range available: " + terms.size());
        return terms.get(4);
    }

    public PlannedTerm getTerm5() {
        if(terms.size() <= 5) throw new IndexOutOfBoundsException("Max terms per year range available: " + terms.size());
        return terms.get(5);
    }

    public PlannedTerm getTerm6() {
        if(terms.size() <= 6) throw new IndexOutOfBoundsException("Max terms per year range available: " + terms.size());
        return terms.get(6);
    }

    public PlannedTerm getTerm7() {
        if(terms.size() <= 7) throw new IndexOutOfBoundsException("Max terms per year range available: " + terms.size());
        return terms.get(7);
    }

    public PlannedTerm getTerm8() {
        if(terms.size() <= 8) throw new IndexOutOfBoundsException("Max terms per year range available: " + terms.size());
        return terms.get(8);
    }

    public PlannedTerm getTerm9() {
        if(terms.size() <= 9) throw new IndexOutOfBoundsException("Max terms per year range available: " + terms.size());
        return terms.get(9);
    }

}
