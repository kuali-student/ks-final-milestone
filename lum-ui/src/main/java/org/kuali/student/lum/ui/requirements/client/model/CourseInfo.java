package org.kuali.student.lum.ui.requirements.client.model;

import org.kuali.student.core.dto.Idable;

public class CourseInfo implements Idable {

    private String id;
    private PrereqInfo statement;
    private String courseCode;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PrereqInfo getStatement() {
        return statement;
    }

    public void setStatement(PrereqInfo statement) {
        this.statement = statement;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    
}
