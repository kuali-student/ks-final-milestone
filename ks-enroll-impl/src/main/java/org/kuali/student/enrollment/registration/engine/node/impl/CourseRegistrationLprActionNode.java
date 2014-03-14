package org.kuali.student.enrollment.registration.engine.node.impl;

import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequestItem;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestItemEngineMessage;
import org.kuali.student.enrollment.registration.engine.node.AbstractCourseRegistrationNode;
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

/**
 * This class handles processing of all Lpr actions (drop/swap/update/add)
 */
public class CourseRegistrationLprActionNode extends AbstractCourseRegistrationNode<RegistrationRequestItemEngineMessage, RegistrationRequestItemEngineMessage> {

    private CourseRegistrationEngineService courseRegistrationEngineService;

    @Override
    public RegistrationRequestItemEngineMessage process(RegistrationRequestItemEngineMessage message) {
        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
            contextInfo.setPrincipalId(message.getRequestItem().getPersonId());
            RegistrationRequestItem registrationRequestItem = message.getRequestItem();

            //Select the request type
            if (registrationRequestItem.getTypeKey().equals(LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY)) {

                //Handle Add requests
                //Do a seat check
                if (courseRegistrationEngineService.areSeatsAvailable(message, contextInfo)) {

                    //Register person
                    registerPersonForCourse(message, contextInfo);

                } else {

                    //Handle no seats available
                    noSeatsAvailable(message, contextInfo);

                }

            } else if (registrationRequestItem.getTypeKey().equals(LprServiceConstants.REQ_ITEM_DROP_TYPE_KEY)) {

                //Handle Drop
                dropPersonFromCourse(message, contextInfo);

            } else if (registrationRequestItem.getTypeKey().equals(LprServiceConstants.REQ_ITEM_UPDATE_TYPE_KEY)) {

                //Handle Update
                updateRegistration(message, contextInfo);

            } else {
                throw new UnsupportedOperationException("Unknown Registration Request Item Type: " + registrationRequestItem.getTypeKey());
            }
            return message;
        } catch (Exception e) {
            throw new RuntimeException("Error processing", e);
        }
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

    private void noSeatsAvailable(RegistrationRequestItemEngineMessage requestItemEngineMessage, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException, VersionMismatchException, DataValidationErrorException {
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
