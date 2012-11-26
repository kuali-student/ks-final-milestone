package org.kuali.student.r2.core.class1.state.service.impl.mocks;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.class1.state.service.RelatedObjectHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  A state change related object helper for testing.
 */
public class StatefulItemRelatedObjectHelper implements RelatedObjectHelper {

    private StatefulItemServiceImpl service;

    public void setService(StatefulItemServiceImpl service) {
        this.service = service;
    }

    @Override
    public Set<String> getRelatedObjectStateKeys(String entityId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        Set<String> roStateKeys = new HashSet<String>();
        for (String key : service.getEntityStateKeys()) {
            roStateKeys.add(key);
        }
        return roStateKeys;
    }

    @Override
    public List<String> getRelatedObjectIds(String entityId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> ids = new ArrayList<String>();
        for (String key : service.getIds()) {
            ids.add(key);
        }
        return ids;
    }
}
