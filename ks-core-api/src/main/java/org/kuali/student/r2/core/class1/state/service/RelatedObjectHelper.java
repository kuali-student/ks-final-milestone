package org.kuali.student.r2.core.class1.state.service;

import org.kuali.student.r2.common.dto.ContextInfo;
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
public interface RelatedObjectHelper {

    public Map<String,String> getRelatedObjectsIdAndState(String entityId, ContextInfo contextInfo)  throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

}
