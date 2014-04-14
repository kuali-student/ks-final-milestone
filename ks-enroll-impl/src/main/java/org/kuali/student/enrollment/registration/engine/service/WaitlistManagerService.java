package org.kuali.student.enrollment.registration.engine.service;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

import java.util.List;
import java.util.Map;

/**
 * Interface for handling waitlist management (how to process people off of the waitlist in the correct order)
 */
public interface WaitlistManagerService {
    public Map<String, List<String>> getPeopleToProcessFromWaitlist(List<String> aoIds, Map<String, Integer> aoid2openSeatsMap, ContextInfo contextInfo) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException;
}
