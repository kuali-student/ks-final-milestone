package org.kuali.student.enrollment.class1.state.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingInfo;
import org.kuali.student.enrollment.examoffering.service.ExamOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.ExamOfferingServiceConstants;
import org.kuali.student.r2.core.class1.state.service.StateHelper;

import javax.xml.namespace.QName;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public class EOStateHelperImpl implements StateHelper {
    ExamOfferingService examOfferingService;
    public EOStateHelperImpl() {
    }

    protected ExamOfferingService getExamOfferingService(){
        if (examOfferingService == null){
            examOfferingService = (ExamOfferingService) GlobalResourceLoader.getService(new QName(ExamOfferingServiceConstants.NAMESPACE, ExamOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return  examOfferingService;
    }

    @Override
    public StatusInfo updateState(String id, String nextStateKey, ContextInfo contextInfo) {
        StatusInfo si = null;
        try {
            si = getExamOfferingService().changeExamOfferingState(id, nextStateKey, contextInfo);
            si.setSuccess(true);
        } catch (Exception e) {
            si.setSuccess(false);
        }
        return si;
    }

    @Override
    public String getStateKey(String entityId, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException {
        ExamOfferingInfo eo = getExamOfferingService().getExamOffering(entityId, context);
        return eo.getStateKey();
    }
}
