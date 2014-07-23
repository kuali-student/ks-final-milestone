package org.kuali.student.enrollment.registration.engine.node.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
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
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

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
        List<ValidationResultInfo> validationResults = new ArrayList<>();
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

        try {
            if (transactionException != null) {
                message = courseRegistrationErrorProcessor.processRequest(message); // roll back the entire transaction
            } else {
                //Error out the items
                LprTransactionInfo trans = updateLprTransactionWithErrors(regRequest.getId(), errors, contextInfo);

                // the operation above has changed the registration request. Pull from the database and update existing
                // message.
                RegistrationRequestInfo updatedRequestInfo = getCourseRegistrationService().getRegistrationRequest(trans.getId(), contextInfo);
                updateRegRequestWithErrors(updatedRequestInfo, errors);
                message.setRegistrationRequest(updatedRequestInfo);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return message;

    }

    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    protected LprTransactionInfo updateLprTransactionWithErrors(String lprTransactionId, List<ValidationResultInfo> errors, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException, VersionMismatchException, DataValidationErrorException, ReadOnlyException {

        LprTransactionInfo trans = getLprService().getLprTransaction(lprTransactionId, contextInfo);

        for (LprTransactionItemInfo item : trans.getLprTransactionItems()) {
            for (ValidationResultInfo error : errors) {
                //Match each error with the corresponding id.
                String itemId = error.getElement().replaceFirst("registrationRequestItems\\['([^']*)'\\]", "$1");
                if (item.getId().equals(itemId)) {
                    //Update the item with the failed validation state and result
                    item.getValidationResults().add(new ValidationResultInfo(error));
                    item.setStateKey(LprServiceConstants.LPRTRANS_ITEM_FAILED_STATE_KEY);
                }
            }
        }
        return getLprService().updateLprTransaction(lprTransactionId, trans, contextInfo);


    }

    protected void updateRegRequestWithErrors(RegistrationRequestInfo updatedRequestInfo, List<ValidationResultInfo> errors){
        for (ValidationResultInfo error : errors) {
            //Match each error with the corresponding id.
            String itemId = error.getElement().replaceFirst("registrationRequestItems\\['([^']*)'\\]", "$1");
            for (RegistrationRequestItemInfo requestItem:updatedRequestInfo.getRegistrationRequestItems()) {
                if (requestItem.getId().equals(itemId)) {
                    requestItem.setStateKey(LprServiceConstants.LPRTRANS_ITEM_FAILED_STATE_KEY);
                    requestItem.getValidationResults().add(new ValidationResultInfo(error));
                }
            }
        }


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
        List<ValidationResultInfo> errors = new ArrayList<>();
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
