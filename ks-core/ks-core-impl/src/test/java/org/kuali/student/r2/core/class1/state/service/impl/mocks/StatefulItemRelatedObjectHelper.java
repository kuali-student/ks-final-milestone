package org.kuali.student.r2.core.class1.state.service.impl.mocks;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.class1.state.service.RelatedObjectHelper;

import java.util.Map;

/**
 *  A state change related object helper for testing.
 */
public class StatefulItemRelatedObjectHelper implements RelatedObjectHelper {

    private StatefulItemServiceImpl service;

    public void setService(StatefulItemServiceImpl service) {
        this.service = service;
    }

    public Map<String, String> getRelatedObjectsIdAndState(String entityId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return service.getItemStorage();
    }
}
