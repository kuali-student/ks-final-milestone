package org.kuali.student.myplan.academicplan.service;

import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.student.myplan.academicplan.dto.LearningPlanInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.infc.HoldsPermissionService;

public class AcademicPlanServiceAuthorizationDecorator
        extends AcademicPlanServiceDecorator
        implements HoldsPermissionService {

	private PermissionService permissionService;
		
	@Override
	public PermissionService getPermissionService() {
		return permissionService;
	}

	@Override
	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

    @Override
    public LearningPlanInfo getLearningPlan(String learningPlanId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), AcademicPlanServiceConstants.NAMESPACE,
                AcademicPlanServiceConstants.SERVICE_NAME + "/getLearningPlan", null)) {
	        return getNextDecorator().getLearningPlan(learningPlanId, context);
        } else {
        	throw new OperationFailedException("Permission Denied.");
        }
    }
}
