package org.kuali.student.enrollment.registration.engine.processor;

import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequestItem;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestItemEngineMessage;
import org.kuali.student.enrollment.registration.engine.service.CourseRegistrationEngineService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;

import java.util.List;

public class CourseRegistrationLprActionProcessor {

    private CourseRegistrationEngineService courseRegistrationEngineService;

    public RegistrationRequestItemEngineMessage process(RegistrationRequestItemEngineMessage message) {
        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
            contextInfo.setPrincipalId(message.getRequestItem().getPersonId());
            RegistrationRequestItem registrationRequestItem = message.getRequestItem();

            //Select the request type
            if (registrationRequestItem.getTypeKey().equals(LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY)) {

                //Handle Add requests
                //Do a seat check
                if (areSeatsAvailable(message, contextInfo)) {

                    //Register person
                    registerPersonForCourse(message, contextInfo);

                } else {
                    //No Seats available
                    if (isThereAWaitlist(message, contextInfo)) {

                        if (isWaitlistFull(message, contextInfo)) {

                            //The waitlist is full for this request
                            notifyWaitlistIsFull(message, contextInfo);

                        } else {
                            if (doesStudentWantToWaitlist(message, contextInfo)) {

                                //Add the student to the waitlist
                                addStudentToWaitList(message, contextInfo);
                            }

                            //Notify waitlist is available
                            notifyWaitlistAvailable(message, contextInfo);
                        }
                    } else {
                        //Handle no waitlist available and no seats
                        notifyNoSeatsAvailable(message, contextInfo);
                    }

                }

            } else if (registrationRequestItem.getTypeKey().equals(LprServiceConstants.REQ_ITEM_DROP_TYPE_KEY)) {

                //Handle Drop
                dropPersonFromCourse(message, contextInfo);

            } else if (registrationRequestItem.getTypeKey().equals(LprServiceConstants.REQ_ITEM_UPDATE_TYPE_KEY)) {

                //Handle Update
                updateRegistration(message, contextInfo);

            } else if (registrationRequestItem.getTypeKey().equals(LprServiceConstants.REQ_ITEM_DROP_WAITLIST_TYPE_KEY)) {

                //Handle WL Update
                updateWaitlist(message, contextInfo);

            } else if (registrationRequestItem.getTypeKey().equals(LprServiceConstants.REQ_ITEM_UPDATE_WAITLIST_TYPE_KEY)) {

                //Handle WL Drop
                dropWaitlist(message, contextInfo);

            } else {
                throw new UnsupportedOperationException("Unknown Registration Request Item Type: " + registrationRequestItem.getTypeKey());
            }

            return message;

        } catch (Exception e) {
            throw new RuntimeException("Error processing", e);
        }
    }

    private void dropWaitlist(RegistrationRequestItemEngineMessage requestItemEngineMessage, ContextInfo contextInfo) throws PermissionDeniedException, OperationFailedException, VersionMismatchException, InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException {
        RegistrationRequestItem registrationRequestItem = requestItemEngineMessage.getRequestItem();
        courseRegistrationEngineService.removeCourseWaitlistEntry(registrationRequestItem.getExistingCourseRegistrationId(), contextInfo);
        courseRegistrationEngineService.updateLprTransactionItemResult(requestItemEngineMessage.getRequestItem().getRegistrationRequestId(),
                requestItemEngineMessage.getRequestItem().getId(),
                LprServiceConstants.LPRTRANS_ITEM_SUCCEEDED_STATE_KEY,
                registrationRequestItem.getExistingCourseRegistrationId(),
                "Student removed from waitlist",
                true,
                contextInfo);
    }

    private void updateWaitlist(RegistrationRequestItemEngineMessage requestItemEngineMessage, ContextInfo contextInfo) throws DataValidationErrorException, PermissionDeniedException, OperationFailedException, VersionMismatchException, InvalidParameterException, ReadOnlyException, MissingParameterException, DoesNotExistException {
        RegistrationRequestItem registrationRequestItem = requestItemEngineMessage.getRequestItem();
        String creditStr = requestItemEngineMessage.getRequestItem().getCredits() == null ? "" : requestItemEngineMessage.getRequestItem().getCredits().bigDecimalValue().setScale(1).toPlainString();
        courseRegistrationEngineService.updateCourseWaitlistEntry(registrationRequestItem.getExistingCourseRegistrationId(), creditStr, registrationRequestItem.getGradingOptionId(), contextInfo);
        courseRegistrationEngineService.updateLprTransactionItemResult(requestItemEngineMessage.getRequestItem().getRegistrationRequestId(),
                requestItemEngineMessage.getRequestItem().getId(),
                LprServiceConstants.LPRTRANS_ITEM_SUCCEEDED_STATE_KEY,
                registrationRequestItem.getExistingCourseRegistrationId(),
                "Waitlist options were updated",
                true,
                contextInfo);
    }

    private void notifyWaitlistAvailable(RegistrationRequestItemEngineMessage message, ContextInfo contextInfo) {
        //TODO KSENROLL-12306
    }

    private void addStudentToWaitList(RegistrationRequestItemEngineMessage requestItemEngineMessage, ContextInfo contextInfo) throws PermissionDeniedException, OperationFailedException, VersionMismatchException, InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException {
        String creditStr = requestItemEngineMessage.getRequestItem().getCredits() == null ? "" : requestItemEngineMessage.getRequestItem().getCredits().bigDecimalValue().setScale(1).toPlainString();
        List<LprInfo> registeredLprs = courseRegistrationEngineService.addCourseWaitlistEntry(requestItemEngineMessage.getRequestItem().getRegistrationGroupId(), requestItemEngineMessage.getRegistrationGroup().getTermId(), creditStr, requestItemEngineMessage.getRequestItem().getGradingOptionId(), contextInfo);
        String masterLprId = registeredLprs.get(0).getMasterLprId();
        courseRegistrationEngineService.updateLprTransactionItemResult(requestItemEngineMessage.getRequestItem().getRegistrationRequestId(),
                requestItemEngineMessage.getRequestItem().getId(),
                LprServiceConstants.LPRTRANS_ITEM_SUCCEEDED_STATE_KEY,
                masterLprId,
                "Waitlisted",
                true,
                contextInfo);
    }

    private boolean doesStudentWantToWaitlist(RegistrationRequestItemEngineMessage message, ContextInfo contextInfo) {
        return false;//TODO KSENROLL-12307
    }

    private void notifyWaitlistIsFull(RegistrationRequestItemEngineMessage message, ContextInfo contextInfo) {
        //TODO KSENROLL-12314
    }

    private boolean isWaitlistFull(RegistrationRequestItemEngineMessage message, ContextInfo contextInfo) {
        return false;//TODO KSENROLL-12314
    }

    private boolean isThereAWaitlist(RegistrationRequestItemEngineMessage message, ContextInfo contextInfo) {
        return false;//TODO KSENROLL-12306
    }

    private boolean areSeatsAvailable(RegistrationRequestItemEngineMessage message, ContextInfo contextInfo) throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        return courseRegistrationEngineService.areSeatsAvailable(message, contextInfo);
    }

    public void updateRegistrationRequestStatus(List<RegistrationRequestItemEngineMessage> regItems) throws PermissionDeniedException, OperationFailedException, VersionMismatchException, InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException {
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        String regReqId = null;
        String requestorId = null;

        // for this implementation we just want the requestId and the id of the person initiating the request.
        // in the future we might want to inspect each item
        for (RegistrationRequestItemEngineMessage regItem : regItems) {
            regReqId = regItem.getRequestItem().getRegistrationRequestId();
            requestorId = regItem.getRequestItem().getPersonId();
            break;
        }

        contextInfo.setPrincipalId(requestorId);

        courseRegistrationEngineService.updateLprTransaction(regReqId, LprServiceConstants.LPRTRANS_SUCCEEDED_STATE_KEY, contextInfo);

    }

    private void updateRegistration(RegistrationRequestItemEngineMessage requestItemEngineMessage, ContextInfo contextInfo) throws DoesNotExistException, PermissionDeniedException, OperationFailedException, VersionMismatchException, InvalidParameterException, ReadOnlyException, MissingParameterException, DataValidationErrorException {
        RegistrationRequestItem registrationRequestItem = requestItemEngineMessage.getRequestItem();
        String creditStr = requestItemEngineMessage.getRequestItem().getCredits() == null ? "" : requestItemEngineMessage.getRequestItem().getCredits().bigDecimalValue().setScale(1).toPlainString();
        courseRegistrationEngineService.updateLprInfos(registrationRequestItem.getExistingCourseRegistrationId(), creditStr, registrationRequestItem.getGradingOptionId(), contextInfo);
        courseRegistrationEngineService.updateLprTransactionItemResult(requestItemEngineMessage.getRequestItem().getRegistrationRequestId(),
                requestItemEngineMessage.getRequestItem().getId(),
                LprServiceConstants.LPRTRANS_ITEM_SUCCEEDED_STATE_KEY,
                registrationRequestItem.getExistingCourseRegistrationId(),
                "Course was updated",
                true,
                contextInfo);
    }

    private void dropPersonFromCourse(RegistrationRequestItemEngineMessage requestItemEngineMessage, ContextInfo contextInfo) throws DoesNotExistException, PermissionDeniedException, OperationFailedException, VersionMismatchException, InvalidParameterException, ReadOnlyException, MissingParameterException, DataValidationErrorException {
        RegistrationRequestItem registrationRequestItem = requestItemEngineMessage.getRequestItem();
        courseRegistrationEngineService.dropLprInfos(registrationRequestItem.getExistingCourseRegistrationId(), contextInfo);
        courseRegistrationEngineService.updateLprTransactionItemResult(requestItemEngineMessage.getRequestItem().getRegistrationRequestId(),
                requestItemEngineMessage.getRequestItem().getId(),
                LprServiceConstants.LPRTRANS_ITEM_SUCCEEDED_STATE_KEY,
                registrationRequestItem.getExistingCourseRegistrationId(),
                "Course was dropped",
                true,
                contextInfo);
    }

    private void notifyNoSeatsAvailable(RegistrationRequestItemEngineMessage requestItemEngineMessage, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException, VersionMismatchException, DataValidationErrorException {
        courseRegistrationEngineService.updateLprTransactionItemResult(requestItemEngineMessage.getRequestItem().getRegistrationRequestId(),
                requestItemEngineMessage.getRequestItem().getId(),
                LprServiceConstants.LPRTRANS_ITEM_FAILED_STATE_KEY,
                null,
                "No Seats Available",
                false,
                contextInfo);
    }


    private void registerPersonForCourse(RegistrationRequestItemEngineMessage requestItemEngineMessage, ContextInfo contextInfo) throws DataValidationErrorException, PermissionDeniedException, OperationFailedException, VersionMismatchException, InvalidParameterException, ReadOnlyException, MissingParameterException, DoesNotExistException {
        String creditStr = requestItemEngineMessage.getRequestItem().getCredits() == null ? "" : requestItemEngineMessage.getRequestItem().getCredits().bigDecimalValue().setScale(1).toPlainString();
        List<LprInfo> registeredLprs = courseRegistrationEngineService.addLprInfo(requestItemEngineMessage.getRequestItem().getRegistrationGroupId(), requestItemEngineMessage.getRegistrationGroup().getTermId(), creditStr, requestItemEngineMessage.getRequestItem().getGradingOptionId(), contextInfo);
        String masterLprId = registeredLprs.get(0).getMasterLprId();
        courseRegistrationEngineService.updateLprTransactionItemResult(requestItemEngineMessage.getRequestItem().getRegistrationRequestId(),
                requestItemEngineMessage.getRequestItem().getId(),
                LprServiceConstants.LPRTRANS_ITEM_SUCCEEDED_STATE_KEY,
                masterLprId,
                "Registered",
                true,
                contextInfo);
    }

    public void setCourseRegistrationEngineService(CourseRegistrationEngineService courseRegistrationEngineService) {
        this.courseRegistrationEngineService = courseRegistrationEngineService;
    }
}
