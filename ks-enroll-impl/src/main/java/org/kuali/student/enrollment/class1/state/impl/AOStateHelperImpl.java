package org.kuali.student.enrollment.class1.state.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.core.class1.state.service.StateHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public class AOStateHelperImpl implements StateHelper {
	
	private static final Logger log = LoggerFactory.getLogger(AOStateHelperImpl.class);
	
    CourseOfferingService courseOfferingService;
    public AOStateHelperImpl() {
    }

    protected CourseOfferingService getCourseOfferingService(){
        if (courseOfferingService == null){
            courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE, CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return  courseOfferingService;
    }

    @Override
    public StatusInfo updateState(String id, String nextStateKey, ContextInfo contextInfo) {
        StatusInfo si = new StatusInfo();
        try {
            si = getCourseOfferingService().changeActivityOfferingState(id, nextStateKey, contextInfo);
            si.setSuccess(true);
        } catch (Exception e) {
        	log.warn(String.format("updateState failed to change ActivityOfferingState: (id=%s, nextStateKey=%s)", id, nextStateKey), e);
            si.setSuccess(false);
        }
        return si;
    }

    @Override
    public String getStateKey(String entityId, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException {
        ActivityOfferingInfo ao = getCourseOfferingService().getActivityOffering(entityId, context);
        return ao.getStateKey();
    }
}
