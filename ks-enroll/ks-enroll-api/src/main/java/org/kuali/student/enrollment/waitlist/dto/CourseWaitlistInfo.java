package org.kuali.student.enrollment.waitlist.dto;

import java.io.Serializable;
import java.util.List;

import org.kuali.student.enrollment.waitlist.course.infc.CourseWaitlist;
import org.kuali.student.enrollment.waitlist.course.infc.CourseWaitlistOption;
import org.kuali.student.r2.common.dto.IdEntityInfo;

public class CourseWaitlistInfo extends IdEntityInfo implements CourseWaitlist, Serializable {

    @Override
    public String getCourseOfferingId() {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public String getRegistrationType() {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getCourseWailtistEntryIds() {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<CourseWaitlistOption> getWaitlistOptions() {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}
