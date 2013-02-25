package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.uif.service.KSViewHelperService;

public interface CO_AO_RG_ViewHelperService extends KSViewHelperService {
    public ActivityOfferingWrapper convertAOInfoToWrapper(ActivityOfferingInfo ao) throws Exception;
}
