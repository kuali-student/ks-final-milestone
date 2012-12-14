package org.kuali.student.myplan.audit.service.model;

public class CourseTaken {
    private String quarter = "XX";
    // aka subjectArea
    private String dept = "XYZZY";
    private String number = "000";


    private String description = "Xxxxx xx Xxxxx";
    private float credits;

    // IP, CR, 0.0,
    private String grade = "??";
    private boolean inProgress = false;

    private String cluid;


    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCredits() {
        return Credits.smartZero(credits);
    }

    public void setCredits(float credits) {
        this.credits = credits;
    }

    public String getGrade() {
        return getInProgress() ? "IP" : grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public boolean getInProgress() {
        return inProgress;
    }

    public void setInProgress( boolean inProgress )
    {
        this.inProgress = inProgress;
    }

    public String getCluid() {
        return cluid;
    }

    public void setCluid( String cluid ) {
        this.cluid = cluid;
    }

    public boolean hasCluid() {
        return cluid != null;
    }
}
