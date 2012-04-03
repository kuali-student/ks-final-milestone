package org.kuali.student.enrollment.class2.courseoffering.service.transformer;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;

public class ActivityOfferingTransformer {
    public void lui2Activity(ActivityOfferingInfo ao, LuiInfo lui) {
        ao.setId(lui.getId());
        ao.setMeta(lui.getMeta());
        ao.setStateKey(lui.getStateKey());
        ao.setTypeKey(lui.getTypeKey());
        ao.setDescr(lui.getDescr());
        ao.setAttributes(lui.getAttributes());
        ao.setActivityId(lui.getCluId());
        ao.setTermId(lui.getAtpId());
        ao.setMinimumEnrollment(lui.getMinimumEnrollment());
        ao.setMaximumEnrollment(lui.getMaximumEnrollment());
    }

    public void activity2Lui (ActivityOfferingInfo ao, LuiInfo lui) {
        lui.setId(ao.getId());
        lui.setTypeKey(ao.getTypeKey());
        lui.setStateKey(ao.getStateKey());
        lui.setDescr(ao.getDescr());
        lui.setMeta(ao.getMeta());
        lui.setAttributes(ao.getAttributes());
        lui.setCluId(ao.getActivityId());
        lui.setAtpId(ao.getTermId());
        lui.setMinimumEnrollment(ao.getMinimumEnrollment());
        lui.setMaximumEnrollment(ao.getMaximumEnrollment());
    }

    public OfferingInstructorInfo transformInstructorForActivityOffering(LuiPersonRelationInfo lpr) {
        OfferingInstructorInfo instructor = new OfferingInstructorInfo();
        instructor.setPersonId(lpr.getPersonId());
        instructor.setPercentageEffort(lpr.getCommitmentPercent());
        instructor.setId(lpr.getId());
        instructor.setTypeKey(lpr.getTypeKey());
        instructor.setStateKey(lpr.getStateKey());
        return instructor;

    }
}
