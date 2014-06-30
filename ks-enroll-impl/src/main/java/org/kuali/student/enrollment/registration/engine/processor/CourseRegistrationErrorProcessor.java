package org.kuali.student.enrollment.registration.engine.processor;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequest;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.registration.client.service.impl.util.RegistrationValidationResultsUtil;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestEngineMessage;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestItemEngineMessage;
import org.kuali.student.enrollment.registration.engine.service.CourseRegistrationEngineService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;

/**
 * This class processes errors encountered during the course registration process.
 */
public class CourseRegistrationErrorProcessor {

    public static final Logger LOGGER = LoggerFactory.getLogger(CourseRegistrationErrorProcessor.class);

    private CourseRegistrationEngineService courseRegistrationEngineService;
    private LprService lprService;

    /**
     * This method processes errors occurring at the item level. The item's state is set to failed,
     * and processing continues. Other items in this registration request will be unaffected.
     *
     * @param message the jms message coming in
     * @return the jms message going out
     */
    public RegistrationRequestItemEngineMessage process(RegistrationRequestItemEngineMessage message) {

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        contextInfo.setPrincipalId(message.getRequestItem().getPersonId());

        String transactionItemId = message.getRequestItem().getId();

        try {
            courseRegistrationEngineService.updateLprTransactionItemResult(message.getRequestItem().getRegistrationRequestId(),
                    transactionItemId,
                    LprServiceConstants.LPRTRANS_ITEM_FAILED_STATE_KEY,
                    null,
                    RegistrationValidationResultsUtil.marshallSimpleMessage(LprServiceConstants.LPRTRANS_ITEM_EXCEPTION_MESSAGE_KEY),
                    false,
                    contextInfo);
            LOGGER.error("Failed item transaction id {} due to internal server error", transactionItemId);
        } catch (Exception ex) {
            LOGGER.error("Unable to update item transaction {} with failure state", transactionItemId, ex);
        }

        return message;
    }

    /**
     * This method processes errors occurring at the registration request level. Because we don't
     * know which item(s) caused the error, we have to fail everything.
     *
     * @param message the jms message coming in
     * @return the jms message going out
     */
    public RegistrationRequestEngineMessage process(RegistrationRequestEngineMessage message) {

        RegistrationRequest regRequest = message.getRegistrationRequest();
        ContextInfo contextInfo = message.getContextInfo();
        contextInfo.setPrincipalId(regRequest.getRequestorId());
        String transactionId = regRequest.getId();

        RegistrationRequestInfo updatedRequestInfo = new RegistrationRequestInfo(message.getRegistrationRequest());
        updatedRequestInfo.setStateKey(LprServiceConstants.LPRTRANS_FAILED_STATE_KEY);

        try {
            LprTransactionInfo trans = getLprService().getLprTransaction(transactionId, contextInfo);
            trans.setStateKey(LprServiceConstants.LPRTRANS_FAILED_STATE_KEY);
            for (LprTransactionItemInfo item : trans.getLprTransactionItems()) {
                ValidationResultInfo vr =
                    new ValidationResultInfo(trans.getId(), ValidationResult.ErrorLevel.ERROR,
                            "Exception occurred during processing. Entire transaction will roll back.");
                vr.setMessage(RegistrationValidationResultsUtil.
                    marshallSimpleMessage(LprServiceConstants.LPRTRANS_ITEM_EXCEPTION_MESSAGE_KEY));
                updateRequestItemsToError(item, updatedRequestInfo, vr);
            }
            getLprService().updateLprTransaction(trans.getId(), trans, contextInfo);
            message.setRegistrationRequest(updatedRequestInfo);
            LOGGER.error("Failed transaction {} due to internal server error", transactionId);
        }  catch (Exception ex) {
            LOGGER.error("Unable to update transaction {} with failure state", transactionId, ex);
        }

        return message;
    }

    /**
     * Update an LPR transaction item with the "failed" state
     *
     * @param item the item to update
     * @param updatedMessage the full registration request we are are updating
     * @param error validation result info about the error
     */
    public void updateRequestItemsToError(LprTransactionItemInfo item, RegistrationRequestInfo updatedMessage, ValidationResultInfo error) {
        //Update the item with the failed validation state and result
        item.getValidationResults().add(new ValidationResultInfo(error));
        item.setStateKey(LprServiceConstants.LPRTRANS_ITEM_FAILED_STATE_KEY);

        //Update the message too
        for (RegistrationRequestItemInfo requestItem:updatedMessage.getRegistrationRequestItems()) {
            if (requestItem.getId().equals(item.getId())) {
                requestItem.setStateKey(LprServiceConstants.LPRTRANS_ITEM_FAILED_STATE_KEY);
                requestItem.getValidationResults().add(new ValidationResultInfo(error));
            }
        }
    }

    private LprService getLprService() {
        if (lprService == null) {
            lprService = GlobalResourceLoader.getService(new QName(LprServiceConstants.NAMESPACE,
                    LprServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lprService;
    }

    public void setCourseRegistrationEngineService(CourseRegistrationEngineService courseRegistrationEngineService) {
        this.courseRegistrationEngineService = courseRegistrationEngineService;
    }
}

