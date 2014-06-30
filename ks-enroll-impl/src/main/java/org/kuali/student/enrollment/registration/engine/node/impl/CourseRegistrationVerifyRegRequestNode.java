package org.kuali.student.enrollment.registration.engine.node.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequest;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequestItem;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestEngineMessage;
import org.kuali.student.enrollment.registration.engine.node.AbstractCourseRegistrationNode;
import org.kuali.student.enrollment.registration.engine.processor.CourseRegistrationErrorProcessor;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

public class CourseRegistrationVerifyRegRequestNode extends AbstractCourseRegistrationNode<RegistrationRequestEngineMessage, RegistrationRequestEngineMessage> {

    public static final Logger LOG = LoggerFactory.getLogger(CourseRegistrationVerifyRegRequestNode.class);
    private CourseRegistrationService courseRegistrationService;
    private LprService lprService;
    private CourseRegistrationErrorProcessor courseRegistrationErrorProcessor;

    public CourseRegistrationService getCourseRegistrationService() {
        if (courseRegistrationService == null) {
            courseRegistrationService = GlobalResourceLoader.getService(CourseRegistrationServiceConstants.Q_NAME);
        }

        return courseRegistrationService;
    }

    public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }

    public LprService getLprService() {
        if (lprService == null) {
            lprService = GlobalResourceLoader.getService(new QName(LprServiceConstants.NAMESPACE,
                    LprServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lprService;
    }

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }

    @Override
    public RegistrationRequestEngineMessage process(RegistrationRequestEngineMessage message) {

        RegistrationRequest regRequest = message.getRegistrationRequest();
        ContextInfo contextInfo = message.getContextInfo();
        contextInfo.setPrincipalId(regRequest.getRequestorId());
        List<ValidationResultInfo> validationResults = new ArrayList<ValidationResultInfo>();
        Exception transactionException = null; // if an exception happens during processing we should fail the entire transaction
        try {
            if(shouldValidate(regRequest)){
                validationResults.addAll(this.getCourseRegistrationService().verifyRegistrationRequestForSubmission(message.
                        getRegistrationRequest().getId(), contextInfo));
            }
        } catch (Exception ex) {
            transactionException = ex;
            LOG.error("Error during rules execution.", ex);
        }
        List<ValidationResultInfo> errors = this.getErrors(validationResults);
        if (errors.isEmpty() && transactionException == null) {
            return message;
        }

        if (transactionException != null) {
            message = courseRegistrationErrorProcessor.process(message); // roll back the entire transaction
        } else {
            //Error out the items
            RegistrationRequestInfo updatedMessage = new RegistrationRequestInfo(message.getRegistrationRequest());

            try {
                LprTransactionInfo trans = getLprService().getLprTransaction(regRequest.getId(), contextInfo);
                trans.setStateKey(LprServiceConstants.LPRTRANS_FAILED_STATE_KEY);
                updatedMessage.setStateKey(LprServiceConstants.LPRTRANS_FAILED_STATE_KEY);
                for (LprTransactionItemInfo item : trans.getLprTransactionItems()) {
                    for (ValidationResultInfo error : errors) {
                        //Match each error with the corresponding id.
                        String itemId = error.getElement().replaceFirst("registrationRequestItems\\['([^']*)'\\]", "$1");
                        if (item.getId().equals(itemId)) {
                            courseRegistrationErrorProcessor.updateRequestItemsToError(item, updatedMessage, error);
                        }
                    }
                }
                getLprService().updateLprTransaction(trans.getId(), trans, contextInfo);

                message.setRegistrationRequest(updatedMessage);

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        return message;

    }

    private boolean shouldValidate(RegistrationRequest regRequest) {
        //Check if this is only a drop from waitlist request (if so no need to validate).
        for(RegistrationRequestItem item:regRequest.getRegistrationRequestItems()){
            if(!LprServiceConstants.REQ_ITEM_DROP_WAITLIST_TYPE_KEY.equals(item.getTypeKey())){
                return true;
            }
        }
        return false;
    }

    private List<ValidationResultInfo> getErrors(List<ValidationResultInfo> results) {
        List<ValidationResultInfo> errors = new ArrayList<ValidationResultInfo>();
        for (ValidationResultInfo vr : results) {
            if (vr.isError()) {
                errors.add(vr);
            }
        }
        return errors;
    }

    public void setCourseRegistrationErrorProcessor(CourseRegistrationErrorProcessor courseRegistrationErrorProcessor) {
        this.courseRegistrationErrorProcessor = courseRegistrationErrorProcessor;
    }
}
