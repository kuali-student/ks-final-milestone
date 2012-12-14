package org.kuali.student.myplan.audit.service.model;

public class CourseAcceptable {
    // aka subjectArea
    private String dept;
    private String number;
    String description = "Xxxxx xx Xxxxx";
    String cluid;
    String quarter = "XX";

    public String getDept() {
        return this.dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getNumber() {
        return this.number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    //    public String getCode() { return code; }
    public String getDescription() { return description; }
    public void setDescription( String description ) { this.description = description; }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getCluid() { return cluid; }

    public void setCluId(String courseId) {
       this.cluid = courseId;
    }
    public boolean hasCluid() {
        return cluid != null;
    }
}
