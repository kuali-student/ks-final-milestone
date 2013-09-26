package org.kuali.student.poc.eventproc.api;

import org.kuali.student.poc.eventproc.event.KSEvent;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

/**
 * Interface for external use of event processor
 */
public interface KSEventProcessor {
    void registerHandler(KSHandler handler);
    void fireEvent(KSEvent event, ContextInfo context)
            throws DataValidationErrorException, PermissionDeniedException, OperationFailedException,
            VersionMismatchException, InvalidParameterException, ReadOnlyException, MissingParameterException,
            DoesNotExistException;
}
