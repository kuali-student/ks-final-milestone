package org.kuali.student.enrollment.class1.state.impl;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.class1.state.service.StateHelper;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public class AOStateHelperImpl implements StateHelper {
    CourseOfferingService courseOfferingService;
    public AOStateHelperImpl(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    @Override
    public StatusInfo updateState(String id, String nextStateKey, ContextInfo contextInfo) {
        StatusInfo si = null;
        try {
            si = courseOfferingService.updateActivityOfferingState(id, nextStateKey, contextInfo);
            si.setSuccess(true);
        } catch (Exception e) {
            si.setSuccess(false);
        }
        return si;
    }

    @Override
    public String getStateKey(String entityId, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException {
        ActivityOfferingInfo ao = courseOfferingService.getActivityOffering(entityId, context);
        return ao.getStateKey();
    }
}
