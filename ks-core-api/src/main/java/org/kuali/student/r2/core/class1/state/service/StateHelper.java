package org.kuali.student.r2.core.class1.state.service;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface StateHelper {
    public abstract StatusInfo updateState(String entityId, String nextStateKey, ContextInfo context);
    public abstract String getStateKey(String entityId, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException;
}