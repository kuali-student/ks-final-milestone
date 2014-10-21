package org.kuali.student.r2.core.process.context;

import java.util.Date;

public class MilestoneCheckContext extends CheckContext {

    private String atpKey;
    private Date dateToTest;
    private int comparison;
    private String studentId;
    public static final int START_DATE = 0;
    public static final int END_DATE = 1;
    public static final int PERIOD = 2;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getAtpKey() {
        return atpKey;
    }

    public void setAtpKey(String atpKey) {
        this.atpKey = atpKey;
    }

    public int getComparison() {
        return comparison;
    }

    public void setComparison(int comparison) {
        this.comparison = comparison;
    }

    public Date getDateToTest() {
        return dateToTest;
    }

    public void setDateToTest(Date dateToTest) {
        this.dateToTest = dateToTest;
    }
}
