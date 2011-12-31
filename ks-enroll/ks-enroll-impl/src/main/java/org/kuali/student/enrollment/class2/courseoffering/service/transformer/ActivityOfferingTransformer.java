package org.kuali.student.enrollment.class2.courseoffering.service.transformer;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;

public class ActivityOfferingTransformer {
    public ActivityOfferingInfo transform(LuiInfo lui) {

        ActivityOfferingInfo ao = new ActivityOfferingInfo();
        ao.setId(lui.getId());
        ao.setMeta(lui.getMeta());
        ao.setStateKey(lui.getStateKey());
        ao.setTypeKey(lui.getTypeKey());
        ao.setDescr(lui.getDescr());
        ao.setAttributes(lui.getAttributes());
        ao.setActivityId(lui.getCluId());
        ao.setTermId(lui.getAtpId());
        ao.setMeetingSchedules(lui.getMeetingSchedules());

        // TODO: ao.setGradingOptionIds --- lui.getResultOptionIds() call
        // LRCService.getResultValuesByIdList

        // rest fields no mapping doc

        return ao;

    }

    public LuiInfo transformActivityOfferingToLui(ActivityOfferingInfo ao) {

        LuiInfo lui = new LuiInfo();
        lui.setId(ao.getId());
        lui.setTypeKey(ao.getTypeKey());
        lui.setStateKey(ao.getStateKey());
        lui.setDescr(ao.getDescr());
        lui.setMeta(ao.getMeta());
        lui.setAttributes(ao.getAttributes());
        lui.setCluId(ao.getActivityId());
        lui.setAtpId(ao.getTermId());
        lui.setMeetingSchedules(ao.getMeetingSchedules());
        return lui;

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
