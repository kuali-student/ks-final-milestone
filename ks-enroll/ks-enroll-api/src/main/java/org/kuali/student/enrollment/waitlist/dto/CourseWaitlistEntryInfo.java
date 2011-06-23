package org.kuali.student.enrollment.waitlist.dto;

import java.io.Serializable;
import java.util.Date;

import org.kuali.student.enrollment.waitlist.course.infc.CourseWaitlistEntry;
import org.kuali.student.r2.common.dto.IdEntityInfo;

public class CourseWaitlistEntryInfo extends IdEntityInfo implements CourseWaitlistEntry, Serializable {

    @Override
    public Date getEffectiveDate() {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Date getExpirationDate() {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public String getStudentId() {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public String getCourseWaitlistOptionId() {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Date getDate() {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Integer getPosition() {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Boolean getHasCheckedIn() {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}
