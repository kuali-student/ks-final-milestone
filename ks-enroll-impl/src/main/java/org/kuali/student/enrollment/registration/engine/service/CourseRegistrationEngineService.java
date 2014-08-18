package org.kuali.student.enrollment.registration.engine.service;

import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequestItem;
import org.kuali.student.enrollment.courseseatcount.infc.SeatCount;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestEngineMessage;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestItemEngineMessage;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import java.util.Date;
import java.util.List;

/**
 * This class has high level methods for the registration engine to interact with the service layer
 */
public interface CourseRegistrationEngineService {

    public LprTransactionItemInfo updateLprTransactionItemResult(String lprTransactionId, String lprTransactionItemId, String lprTransactionItemStateKey, String resultingLprId, String message, boolean status, ContextInfo contextInfo) throws DoesNotExistException, PermissionDeniedException, OperationFailedException, VersionMismatchException, InvalidParameterException, MissingParameterException, DataValidationErrorException, ReadOnlyException;

    public void registerPersonForCourse(RegistrationRequestItemEngineMessage message, ContextInfo contextInfo) throws PermissionDeniedException, ReadOnlyException, OperationFailedException, VersionMismatchException, InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException;

    public List<LprInfo> updateOptionsOnRegisteredLprs(String masterLprId, String credits, String gradingOptionId, Date effDate, ContextInfo contextInfo)
            throws OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException,
            InvalidParameterException, ReadOnlyException, MissingParameterException, DoesNotExistException;

    public List<LprInfo> dropLprs(String masterLprId, ContextInfo contextInfo) throws OperationFailedException,
            PermissionDeniedException, DataValidationErrorException, VersionMismatchException,
            InvalidParameterException, ReadOnlyException, MissingParameterException, DoesNotExistException;

    public RegistrationRequestEngineMessage initializeRegistrationRequest(String regReqId, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException;

    public List<LprInfo> addWaitlistLprs(String regGroupId, String termId, String credits, String gradingOptionId, Date effDate, String crossList, ContextInfo contextInfo);
    public List<LprInfo> updateOptionsOnWaitlistLprs(String masterLprId, String credits, String gradingOptionId, Date effDate, ContextInfo contextInfo) throws OperationFailedException, PermissionDeniedException, MissingParameterException, InvalidParameterException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException;
    public void removeCourseWaitlistEntry(RegistrationRequestItemEngineMessage message, ContextInfo contextInfo) throws PermissionDeniedException, ReadOnlyException, OperationFailedException, VersionMismatchException, InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException;
    public List<LprInfo> addLprsFromWaitlist(String masterLprId, ContextInfo contextInfo) throws OperationFailedException, PermissionDeniedException, MissingParameterException, InvalidParameterException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException;

    public List<SeatCount> getSeatCountsForActivityOfferings(List<String> activityOfferingIds, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException;
    public boolean areSeatsAvailable(List<SeatCount> seatCounts, List<String> activityOfferingIds, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException;
    public boolean isThereAWaitlist(List<SeatCount> seatCounts, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException;
    public boolean isWaitlistFull(List<SeatCount> seatCounts, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException;




}
