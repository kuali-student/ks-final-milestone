package org.kuali.student.enrollment.class2.acal.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.TermInfo;

import java.util.Date;

public class AcademicTermForm  extends UifFormBase {

    private static final long serialVersionUID = 4898118410378641665L;

    private TermInfo termInfo;

    private String name;
    private int instructionalDays;
    private String termLength;
    private String termType;
    private Date startDate;
    private Date endDate;

    public AcademicTermForm(){
       termInfo = new TermInfo();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public TermInfo getTermInfo() {
        return termInfo;
    }

    public void setTermInfo(TermInfo termInfo) {
        this.termInfo = termInfo;
    }

    public int getInstructionalDays() {
        return instructionalDays;
    }

    public void setInstructionalDays(int instructionalDays) {
        this.instructionalDays = instructionalDays;
    }

    public String getTermLength() {
        return termLength;
    }

    public void setTermLength(String termLength) {
        this.termLength = termLength;
    }

}
