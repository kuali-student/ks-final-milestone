package org.kuali.student.enrollment.registration.engine.processor;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequestItem;
import org.kuali.student.enrollment.courseseatcount.infc.SeatCount;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.infc.LprTransaction;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.registration.client.service.impl.util.RegistrationValidationResultsUtil;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestItemEngineMessage;
import org.kuali.student.enrollment.registration.engine.service.CourseRegistrationConstants;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

public class CourseRegistrationLprActionProcessor {

    public static final Logger LOGGER = LoggerFactory.getLogger(CourseRegistrationLprActionProcessor.class);

    private CourseRegistrationEngineService courseRegistrationEngineService;
    private LprService lprService;

    private JmsTemplate jmsTemplate;  // needed to call ActiveMQ based Registration Engine

    public RegistrationRequestItemEngineMessage process(RegistrationRequestItemEngineMessage message) {
        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
            contextInfo.setPrincipalId(message.getRequestItem().getPersonId());
            RegistrationRequestItem registrationRequestItem = message.getRequestItem();

            if(LprServiceConstants.LPRTRANS_ITEM_FAILED_STATE_KEY.equals(registrationRequestItem.getStateKey()) ||
                    LprServiceConstants.LPRTRANS_ITEM_WAITLIST_AVAILABLE_STATE_KEY.equals(registrationRequestItem.getStateKey())){
                //Don't process this if it has failed.
                return message;
            }

            //Select the request type
            if (registrationRequestItem.getTypeKey().equals(LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY)) {

                //Handle Add requests
                //Do a seat check & waitlist check
                List<SeatCount> seatCounts = getSeatCountsForActivityOfferings(message, contextInfo);

                if (areSeatsAvailable(seatCounts, message, contextInfo)) {
                    //Register person
                    registerPersonForCourse(message, contextInfo);
                } else {
                    //No Seats available
                    if (isThereAWaitlist(seatCounts, contextInfo)) {
                        if (isWaitlistFull(seatCounts, contextInfo)) {
                            //The waitlist is full for this request
                            notifyWaitlistIsFull(message, contextInfo);
                        } else {
                            if (doesStudentWantToWaitlist(message)) {
                                //Add the student to the waitlist
                                addStudentToWaitList(message, contextInfo);
                            } else {
                                //Notify waitlist is available
                                notifyWaitlistAvailable(message, contextInfo);
                            }
                        }
                    } else {
                        //Handle no waitlist available and no seats
                        notifyNoSeatsNoWaitlistAvailable(message, contextInfo);
                    }
                }
            } else if (registrationRequestItem.getTypeKey().equals(LprServiceConstants.REQ_ITEM_DROP_TYPE_KEY)) {
                //Handle Drop
                dropPersonFromCourse(message, contextInfo);
            } else if (registrationRequestItem.getTypeKey().equals(LprServiceConstants.REQ_ITEM_UPDATE_TYPE_KEY)) {
                //Handle Update
                updateRegistration(message, contextInfo);
            } else if (registrationRequestItem.getTypeKey().equals(LprServiceConstants.REQ_ITEM_DROP_WAITLIST_TYPE_KEY)) {
                //Handle WL Drop
                dropWaitlist(message, contextInfo);
            } else if (registrationRequestItem.getTypeKey().equals(LprServiceConstants.REQ_ITEM_UPDATE_WAITLIST_TYPE_KEY)) {
                //Handle WL Update
                updateWaitlist(message, contextInfo);
            } else if (registrationRequestItem.getTypeKey().equals(LprServiceConstants.REQ_ITEM_ADD_FROM_WAITLIST_TYPE_KEY)) {
                //Handle autoWL (request is coming back to add course from WL to registered ones)
                addFromWaitlist(message, contextInfo);
            } else {
                throw new UnsupportedOperationException("Unknown Registration Request Item Type: " + registrationRequestItem.getTypeKey());
            }

            return message;
        } catch (Exception e) {
            throw new RuntimeException("Error processing", e);
        }
    }

    private void dropWaitlist(RegistrationRequestItemEngineMessage message, ContextInfo contextInfo) throws PermissionDeniedException, OperationFailedException, VersionMismatchException, InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException {
        RegistrationRequestItem registrationRequestItem = message.getRequestItem();
        courseRegistrationEngineService.removeCourseWaitlistEntry(registrationRequestItem.getExistingCourseRegistrationId(), contextInfo);
        courseRegistrationEngineService.updateLprTransactionItemResult(message.getRequestItem().getRegistrationRequestId(),
                message.getRequestItem().getId(),
                LprServiceConstants.LPRTRANS_ITEM_SUCCEEDED_STATE_KEY,
                registrationRequestItem.getExistingCourseRegistrationId(),
                RegistrationValidationResultsUtil.marshallSimpleMessage(LprServiceConstants.LPRTRANS_ITEM_WAITLIST_STUDENT_REMOVED_MESSAGE_KEY),
                        true,
                        contextInfo);
    }

    private void updateWaitlist(RegistrationRequestItemEngineMessage message, ContextInfo contextInfo) throws DataValidationErrorException, PermissionDeniedException, OperationFailedException, VersionMismatchException, InvalidParameterException, ReadOnlyException, MissingParameterException, DoesNotExistException {
        RegistrationRequestItem registrationRequestItem = message.getRequestItem();
        String creditStr = message.getRequestItem().getCredits() == null ? "" : message.getRequestItem().getCredits().bigDecimalValue().setScale(1).toPlainString();
        courseRegistrationEngineService.updateOptionsOnWaitlistLprs(registrationRequestItem.getExistingCourseRegistrationId(), creditStr, registrationRequestItem.getGradingOptionId(), contextInfo);
        courseRegistrationEngineService.updateLprTransactionItemResult(message.getRequestItem().getRegistrationRequestId(),
                message.getRequestItem().getId(),
                LprServiceConstants.LPRTRANS_ITEM_SUCCEEDED_STATE_KEY,
                registrationRequestItem.getExistingCourseRegistrationId(),
                RegistrationValidationResultsUtil.marshallSimpleMessage(LprServiceConstants.LPRTRANS_ITEM_WAITLIST_OPTIONS_UPDATED_MESSAGE_KEY),
                true,
                contextInfo);
    }

    private void notifyWaitlistAvailable(RegistrationRequestItemEngineMessage message, ContextInfo contextInfo) throws PermissionDeniedException, OperationFailedException, VersionMismatchException, InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException {
        courseRegistrationEngineService.updateLprTransactionItemResult(message.getRequestItem().getRegistrationRequestId(),
                message.getRequestItem().getId(),
                LprServiceConstants.LPRTRANS_ITEM_WAITLIST_AVAILABLE_STATE_KEY,
                null,
                RegistrationValidationResultsUtil.marshallSimpleMessage(LprServiceConstants.LPRTRANS_ITEM_WAITLIST_AVAILABLE_MESSAGE_KEY),
                false,
                contextInfo);
    }

    private void addStudentToWaitList(RegistrationRequestItemEngineMessage message, ContextInfo contextInfo) throws PermissionDeniedException, OperationFailedException, VersionMismatchException, InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException {
        String creditStr = message.getRequestItem().getCredits() == null ? "" : message.getRequestItem().getCredits().bigDecimalValue().setScale(1).toPlainString();
        List<LprInfo> registeredLprs = courseRegistrationEngineService.addWaitlistLprs(message.getRequestItem().getRegistrationGroupId(), message.getRegistrationGroup().getTermId(), creditStr, message.getRequestItem().getGradingOptionId(), contextInfo);
        String masterLprId = registeredLprs.get(0).getMasterLprId();
        courseRegistrationEngineService.updateLprTransactionItemResult(message.getRequestItem().getRegistrationRequestId(),
                message.getRequestItem().getId(),
                LprServiceConstants.LPRTRANS_ITEM_WAITLIST_STATE_KEY,
                masterLprId,
                RegistrationValidationResultsUtil.marshallSimpleMessage(LprServiceConstants.LPRTRANS_ITEM_WAITLIST_WAITLISTED_MESSAGE_KEY),
                true,
                contextInfo);
    }

    private boolean doesStudentWantToWaitlist(RegistrationRequestItemEngineMessage message) {
        return BooleanUtils.toBoolean(message.getRequestItem().getOkToWaitlist());
    }

    private void notifyWaitlistIsFull(RegistrationRequestItemEngineMessage message, ContextInfo contextInfo) throws PermissionDeniedException, OperationFailedException, VersionMismatchException, InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException {
        courseRegistrationEngineService.updateLprTransactionItemResult(message.getRequestItem().getRegistrationRequestId(),
                message.getRequestItem().getId(),
                LprServiceConstants.LPRTRANS_ITEM_FAILED_STATE_KEY,
                null,
                RegistrationValidationResultsUtil.marshallSimpleMessage(LprServiceConstants.LPRTRANS_ITEM_WAITLIST_FULL_MESSAGE_KEY),
                false,
                contextInfo);
    }

    private List<SeatCount> getSeatCountsForActivityOfferings(RegistrationRequestItemEngineMessage message, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        return courseRegistrationEngineService.getSeatCountsForActivityOfferings(message.getRegistrationGroup().getActivityOfferingIds(), contextInfo);
    }

    private boolean isWaitlistFull(List<SeatCount> seatCounts, ContextInfo contextInfo) throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        return courseRegistrationEngineService.isWaitlistFull(seatCounts, contextInfo);
    }

    private boolean isThereAWaitlist(List<SeatCount> seatCounts, ContextInfo contextInfo) throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        return courseRegistrationEngineService.isThereAWaitlist(seatCounts, contextInfo);
    }

    private boolean areSeatsAvailable(List<SeatCount> seatCounts, RegistrationRequestItemEngineMessage message, ContextInfo contextInfo) throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        return courseRegistrationEngineService.areSeatsAvailable(seatCounts, message.getRegistrationGroup().getActivityOfferingIds(), contextInfo);
    }

    @Transactional
    public void updateRegistrationRequestStatus(List<RegistrationRequestItemEngineMessage> regItems) throws PermissionDeniedException, OperationFailedException, VersionMismatchException, InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException {
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        String regReqId = null;
        String requestorId = null;
        String lprTransFinalState = LprServiceConstants.LPRTRANS_SUCCEEDED_STATE_KEY;



        // for this implementation we just want the requestId and the id of the person initiating the request.
        // in the future we might want to inspect each item
        for (RegistrationRequestItemEngineMessage regItem : regItems) {
            regReqId = regItem.getRequestItem().getRegistrationRequestId();
            requestorId = regItem.getRequestItem().getPersonId();
            break;
        }

        // Each node can update the LPR transaction. If an exception happens, the LPR trans state should have been
        // updated to failure. Pull it now
        LprTransaction lprTransaction = getLprService().getLprTransaction(regReqId, contextInfo);
        if(!lprTransaction.getStateKey().equals(LprServiceConstants.LPRTRANS_PROCESSING_STATE_KEY)){
            lprTransFinalState = lprTransaction.getStateKey();
        }

        contextInfo.setPrincipalId(requestorId);
        getLprService().changeLprTransactionState(regReqId, lprTransFinalState, contextInfo);

    }

    private void updateRegistration(RegistrationRequestItemEngineMessage message, ContextInfo contextInfo) throws DoesNotExistException, PermissionDeniedException, OperationFailedException, VersionMismatchException, InvalidParameterException, ReadOnlyException, MissingParameterException, DataValidationErrorException {
        RegistrationRequestItem registrationRequestItem = message.getRequestItem();
        String creditStr = message.getRequestItem().getCredits() == null ? "" : message.getRequestItem().getCredits().bigDecimalValue().setScale(1).toPlainString();
        courseRegistrationEngineService.updateOptionsOnRegisteredLprs(registrationRequestItem.getExistingCourseRegistrationId(), creditStr, registrationRequestItem.getGradingOptionId(), contextInfo);
        courseRegistrationEngineService.updateLprTransactionItemResult(message.getRequestItem().getRegistrationRequestId(),
                message.getRequestItem().getId(),
                LprServiceConstants.LPRTRANS_ITEM_SUCCEEDED_STATE_KEY,
                registrationRequestItem.getExistingCourseRegistrationId(),
                RegistrationValidationResultsUtil.marshallSimpleMessage(LprServiceConstants.LPRTRANS_ITEM_COURSE_UPDATED_MESSAGE_KEY),
                true,
                contextInfo);
    }

    private void dropPersonFromCourse(RegistrationRequestItemEngineMessage message, ContextInfo contextInfo) throws DoesNotExistException, PermissionDeniedException, OperationFailedException, VersionMismatchException, InvalidParameterException, ReadOnlyException, MissingParameterException, DataValidationErrorException {
        RegistrationRequestItem registrationRequestItem = message.getRequestItem();
        courseRegistrationEngineService.dropLprs(registrationRequestItem.getExistingCourseRegistrationId(), contextInfo);
        courseRegistrationEngineService.updateLprTransactionItemResult(message.getRequestItem().getRegistrationRequestId(),
                message.getRequestItem().getId(),
                LprServiceConstants.LPRTRANS_ITEM_SUCCEEDED_STATE_KEY,
                registrationRequestItem.getExistingCourseRegistrationId(),
                RegistrationValidationResultsUtil.marshallSimpleMessage(LprServiceConstants.LPRTRANS_ITEM_COURSE_DROPPED_MESSAGE_KEY),
                true,
                contextInfo);
        // checking if event is removing student from the course = open seat(s)
        if (StringUtils.equals(registrationRequestItem.getTypeKey(), LprServiceConstants.REQ_ITEM_DROP_TYPE_KEY)) {
            // Getting list of AO IDs for the given Waitlist to pass it to the listener
            List<LprInfo> lprInfos = getLprService().getLprsByMasterLprId(registrationRequestItem.getExistingCourseRegistrationId(), contextInfo);
            ArrayList<String> aoIDs = new ArrayList<String>();
            for (LprInfo lprInfo : lprInfos) {
                if (StringUtils.equals(lprInfo.getTypeKey(), LprServiceConstants.REGISTRANT_AO_LPR_TYPE_KEY)) {
                    aoIDs.add(lprInfo.getLuiId());
                }
            }
            // Passing event and AO IDs
            try {
                ObjectMessage objectMessage = new ActiveMQObjectMessage();
                objectMessage.setObject(aoIDs);
                jmsTemplate.convertAndSend(CourseRegistrationConstants.SEAT_OPEN_QUEUE, objectMessage);
            } catch (JMSException jmsEx) {
                throw new RuntimeException("Error submitting open seat request.", jmsEx);
            }
        }
    }

    private void notifyNoSeatsNoWaitlistAvailable(RegistrationRequestItemEngineMessage message, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException, VersionMismatchException, DataValidationErrorException, ReadOnlyException {
        courseRegistrationEngineService.updateLprTransactionItemResult(message.getRequestItem().getRegistrationRequestId(),
                message.getRequestItem().getId(),
                LprServiceConstants.LPRTRANS_ITEM_FAILED_STATE_KEY,
                null,
                RegistrationValidationResultsUtil.marshallSimpleMessage(LprServiceConstants.LPRTRANS_ITEM_WAITLIST_NOT_OFFERED_MESSAGE_KEY),
                false,
                contextInfo);
    }

    private void registerPersonForCourse(RegistrationRequestItemEngineMessage message, ContextInfo contextInfo) throws DataValidationErrorException, PermissionDeniedException, OperationFailedException, VersionMismatchException, InvalidParameterException, ReadOnlyException, MissingParameterException, DoesNotExistException {
        String creditStr = message.getRequestItem().getCredits() == null ? "" : message.getRequestItem().getCredits().bigDecimalValue().setScale(1).toPlainString();
        List<LprInfo> registeredLprs = courseRegistrationEngineService.addRegisteredLprs(message.getRequestItem().getRegistrationGroupId(), message.getRegistrationGroup().getTermId(), creditStr, message.getRequestItem().getGradingOptionId(), contextInfo);
        String masterLprId = registeredLprs.get(0).getMasterLprId();
        courseRegistrationEngineService.updateLprTransactionItemResult(message.getRequestItem().getRegistrationRequestId(),
                message.getRequestItem().getId(),
                LprServiceConstants.LPRTRANS_ITEM_SUCCEEDED_STATE_KEY,
                masterLprId,
                RegistrationValidationResultsUtil.marshallSimpleMessage(LprServiceConstants.LPRTRANS_ITEM_PERSON_REGISTERED_MESSAGE_KEY),
                true,
                contextInfo);
    }

    private void addFromWaitlist(RegistrationRequestItemEngineMessage message, ContextInfo contextInfo) throws DataValidationErrorException, PermissionDeniedException, OperationFailedException, VersionMismatchException, InvalidParameterException, ReadOnlyException, MissingParameterException, DoesNotExistException {
        List<LprInfo> registeredLprs = courseRegistrationEngineService.addLprsFromWaitlist(message.getRequestItem().getExistingCourseRegistrationId(), contextInfo);
        String masterLprId = registeredLprs.get(0).getMasterLprId();
        courseRegistrationEngineService.updateLprTransactionItemResult(message.getRequestItem().getRegistrationRequestId(),
                message.getRequestItem().getId(),
                LprServiceConstants.LPRTRANS_ITEM_SUCCEEDED_STATE_KEY,
                masterLprId,
                RegistrationValidationResultsUtil.marshallSimpleMessage(LprServiceConstants.LPRTRANS_ITEM_ADD_FROM_WAITLIST_MESSAGE_KEY),
                true,
                contextInfo);
    }

    public void setCourseRegistrationEngineService(CourseRegistrationEngineService courseRegistrationEngineService) {
        this.courseRegistrationEngineService = courseRegistrationEngineService;
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public LprService getLprService() {
        if (lprService == null) {
            lprService = (LprService) GlobalResourceLoader.getService(new QName(LprServiceConstants.NAMESPACE, LprServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lprService;
    }

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }
}
