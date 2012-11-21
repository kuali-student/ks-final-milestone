package org.kuali.student.r2.core.class1.state.service;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

import java.util.Map;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface StateTransitionsHelper {
    /**
     *
     * @param entityId
     * @param nextStateKey
     * @param context
     * @return success if the constraints are satisfied
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     * @impl KRMS agenda processing is currently out of scope
     */
    StatusInfo processStateConstraints(String entityId, String nextStateKey, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException, DoesNotExistException;

    /**
     * Process the propagations
     * @param entityId
     * @param nextStateKey
     * @param context
     * @return a map of StatePropagation id to it's propagation status
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     * @impl  Service.updateEntityState is expected process the propagation status and make a decision on any roll backs
     */
    Map<String, StatusInfo> processStatePropagations(String entityId, String nextStateKey, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException, DoesNotExistException;
}
