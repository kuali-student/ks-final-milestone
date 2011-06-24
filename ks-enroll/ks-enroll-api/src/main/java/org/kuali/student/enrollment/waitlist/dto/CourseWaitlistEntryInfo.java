package org.kuali.student.enrollment.waitlist.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.kuali.student.enrollment.waitlist.course.infc.CourseWaitlistEntry;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.w3c.dom.Element;

public class CourseWaitlistEntryInfo extends RelationshipInfo implements CourseWaitlistEntry, Serializable {

    private static final long serialVersionUID = 1L;

    private String studentId;

    private String courseWaitlistOptionId;

    private Integer position;

    private Boolean hasCheckedIn;
    
    private List<Element> _futureElements;


    public void setHasCheckedIn(Boolean hasCheckedIn) {
        this.hasCheckedIn = hasCheckedIn;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setCourseWaitlistOptionId(String courseWaitlistOptionId) {
        this.courseWaitlistOptionId = courseWaitlistOptionId;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public String getStudentId() {
        return studentId;
    }

    @Override
    public String getCourseWaitlistOptionId() {
        return courseWaitlistOptionId;
    }

    @Override
    public Integer getPosition() {
        return position;
    }

    @Override
    public Boolean getHasCheckedIn() {
        return hasCheckedIn;
    }

}
