package org.kuali.student.enrollment.class1.state.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
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

import javax.xml.namespace.QName;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public class COStateHelperImpl implements StateHelper {
        CourseOfferingService courseOfferingService;
        public COStateHelperImpl() {
        }

        protected CourseOfferingService getCourseOfferingService(){
            if (courseOfferingService == null){
                courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE, CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
            }
            return  courseOfferingService;
        }

        @Override
        public StatusInfo updateState(String id, String nextStateKey, ContextInfo contextInfo) {
            StatusInfo si = null;
            try {
                si = getCourseOfferingService().updateCourseOfferingState(id, nextStateKey, contextInfo);
                si.setSuccess(true);
            } catch (Exception e) {
                si.setSuccess(false);
            }
            return si;
        }

        @Override
        public String getStateKey(String entityId, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException {
            CourseOfferingInfo co = getCourseOfferingService().getCourseOffering(entityId, context);
            return co.getStateKey();
        }
    }