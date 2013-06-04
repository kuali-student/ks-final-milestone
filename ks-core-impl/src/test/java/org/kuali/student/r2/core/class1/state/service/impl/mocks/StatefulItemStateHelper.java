package org.kuali.student.r2.core.class1.state.service.impl.mocks;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.class1.state.service.StateHelper;

/**
 *  A simple StateHelper impl for testing.
 */
public class StatefulItemStateHelper implements StateHelper {

    private StatefulItemServiceImpl statefulItemService;

    public void setStatefulItemService(StatefulItemServiceImpl statefulItemService) {
        this.statefulItemService = statefulItemService;
    }

    @Override
    public StatusInfo updateState(String entityId, String nextStateKey, ContextInfo context) {
        return statefulItemService.updateItemState(entityId, nextStateKey, context);
    }

    @Override
    public String getStateKey(String entityId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException {
        return statefulItemService.getItemId(entityId);
    }
}
