package org.kuali.student.enrollment.registration.engine.service;

import org.kuali.student.common.eventing.EventMessage;
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
     * This method notifies the waitlist manager that a lui has changed. It determines if there are seats available
     * in the particular LUI and if so processes people from the waitlist.
     * @param message
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     * @throws ReadOnlyException
     * @throws DataValidationErrorException
     * @throws AlreadyExistsException
     */
    public void processLuiChangeEvent(EventMessage message) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, AlreadyExistsException;

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
