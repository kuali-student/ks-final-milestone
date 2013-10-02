package org.kuali.student.enrollment.class2.coursewaitlist.service.facade;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;

/**
 * Business Logic for activating and deactivating Waitlists for AO level by a specified CO
 *
 * @author Kuali Student Team
 */
public interface CourseWaitListServiceFacade {
    public void activateActivityOfferingWaitlistsByCourseOffering(String coId, String termId, ContextInfo context) throws Exception;
    public void deactivateActivityOfferingWaitlistsByCourseOffering(String coId, ContextInfo context) throws Exception;

    /* Create a new CourseWaitListInfo (CWLI) for a specified AO and persist it in DB
       1)set AOInfo.id to courseWaitListInfo.activityOfferingIds
       2)set AOInfo.formatOfferingId to courseWaitListInfo.formatOfferingIds
       3)if COInfo.hasWaitList = true, set courseWaitListInfo.stateKey to active and set automaticallyProcessed,
            confirmationRequired, checkInRequired, and allowHoldUntilEntries in courseWaitListInfo to true
       4)if COInfo.hasWaitList = false, set courseWaitListInfo.stateKey to inactive and set automaticallyProcessed,
            confirmationRequired, checkInRequired, and allowHoldUntilEntries in courseWaitListInfo to false
    */
    public CourseWaitListInfo createDefaultCourseWaitlist(ActivityOfferingInfo aoInfo, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException, DataValidationErrorException, ReadOnlyException;

    /* Create a new CourseWaitListInfo (CWLI) for a specified AO and persist it in DB

        If possible, use this method. The other Method is MUCH less efficient.

       1)set AOInfo.id to courseWaitListInfo.activityOfferingIds
       2)set AOInfo.formatOfferingId to courseWaitListInfo.formatOfferingIds
       3)if COInfo.hasWaitList = true, set courseWaitListInfo.stateKey to active and set automaticallyProcessed,
            confirmationRequired, checkInRequired, and allowHoldUntilEntries in courseWaitListInfo to true
       4)if COInfo.hasWaitList = false, set courseWaitListInfo.stateKey to inactive and set automaticallyProcessed,
            confirmationRequired, checkInRequired, and allowHoldUntilEntries in courseWaitListInfo to false
    */
    public CourseWaitListInfo createDefaultCourseWaitlist(String foId, String aoId, boolean coHasWaitlist, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException, DataValidationErrorException, ReadOnlyException;

    public boolean getHasWaitlist();
}
