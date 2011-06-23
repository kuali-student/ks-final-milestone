package org.kuali.student.enrollment.waitlist.dto;

import java.io.Serializable;

import org.kuali.student.enrollment.waitlist.course.infc.CourseWaitlistOption;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.infc.TimeAmount;

public class CourseWaitlistOptionInfo extends IdEntityInfo implements CourseWaitlistOption, Serializable {

    @Override
    public String getRegGroupId() {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public String getClearingStrategy() {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Boolean getCheckInRequired() {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TimeAmount getCheckInFrequency() {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}
