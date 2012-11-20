package org.kuali.student.enrollment.class1.state.impl;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.class1.state.service.RelatedObjectHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */

public class RelatedObjectHelperAOtoFOImpl implements RelatedObjectHelper {
    private CourseOfferingService courseOfferingService;
    public RelatedObjectHelperAOtoFOImpl(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    @Override
    public List<String> getRelatedObjectIds(String entityId, ContextInfo contextInfo) {
        List<String> foIds = new ArrayList<String>();
        try {
            ActivityOfferingInfo ao = courseOfferingService.getActivityOffering(entityId, contextInfo);
            String foId = ao.getFormatOfferingId();
            foIds.add(foId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return foIds;
    }
}