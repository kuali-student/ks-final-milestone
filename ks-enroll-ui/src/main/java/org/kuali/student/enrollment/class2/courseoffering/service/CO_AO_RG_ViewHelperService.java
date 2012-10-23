package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;

public interface CO_AO_RG_ViewHelperService extends ViewHelperService {
    public ActivityOfferingWrapper convertAOInfoToWrapper(ActivityOfferingInfo ao) throws Exception;
}
