package org.kuali.student.enrollment.registration.engine.service;

import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequest;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;

import java.util.List;
import java.util.Map;

/**
 * Interface for handling waitlist management (how to process people off of the waitlist in the correct order)
 */
public interface WaitlistManagerService {
    public List<WaitlistInfo> getPeopleToProcessFromWaitlist(List<String> aoIds, Map<String, Integer> aoid2openSeatsMap, ContextInfo contextInfo) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException;

    public List<RegistrationRequest> processPeopleOffOfWaitlist(List<String> aoIds, ContextInfo contextInfo) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DataValidationErrorException, ReadOnlyException, AlreadyExistsException;

    /**
     * Structure to store waitlist information
     */
    public class WaitlistInfo {
        public WaitlistInfo(String rgId, String personId, String masterLprId, String atpId) {
            super();
            this.rgId = rgId;
            this.personId = personId;
            this.masterLprId = masterLprId;
            this.atpId = atpId;
        }

        public String rgId;
        public String personId;
        public String masterLprId;
        public String atpId;
    }
}
