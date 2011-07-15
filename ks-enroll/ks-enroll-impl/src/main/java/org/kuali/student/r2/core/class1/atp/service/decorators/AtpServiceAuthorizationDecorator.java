package org.kuali.student.r2.core.class1.atp.service.decorators;

import org.kuali.rice.kim.service.PermissionService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.HoldsPermissionService;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpServiceDecorator;

public class AtpServiceAuthorizationDecorator extends AtpServiceDecorator implements HoldsPermissionService {
    
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
    public AtpInfo getAtp(
            String atpKey, ContextInfo context)
    throws DoesNotExistException,
    InvalidParameterException,
    MissingParameterException,
    OperationFailedException,
    PermissionDeniedException {
        
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), "KS-ENROLL", "getAtp", null, null)) {
	        return this.nextDecorator.getAtp(atpKey, context);
        } else {
           throw new PermissionDeniedException();
        }
        
    }
}
