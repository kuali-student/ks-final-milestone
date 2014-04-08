package org.kuali.student.enrollment.registration.engine.node.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequest;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemResultInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestEngineMessage;
import org.kuali.student.enrollment.registration.engine.node.AbstractCourseRegistrationNode;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;

public class CourseRegistrationVerifyRegRequestNode extends AbstractCourseRegistrationNode<RegistrationRequestEngineMessage, RegistrationRequestEngineMessage> {

    private CourseRegistrationService courseRegistrationService;
    private LprService lprService;

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
        List<ValidationResultInfo> validationResults;
        try {
            validationResults = this.getCourseRegistrationService().verifyRegistrationRequestForSubmission(message.
                    getRegistrationRequest().getId(), contextInfo);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        List<ValidationResultInfo> errors = this.getErrors(validationResults);
        if (errors.isEmpty()) {
            return message;
        }
        message.setStopProcessing(true);
        LprTransactionInfo trans;
        try {
            trans = getLprService().getLprTransaction(regRequest.getId(), contextInfo);
            int itemFailures = 0;
            for (LprTransactionItemInfo item : trans.getLprTransactionItems()) {
                LprTransactionItemResultInfo result = item.getLprTransactionItemResult();
                if (result == null) {
                    result = new LprTransactionItemResultInfo();
                }
                List<ValidationResultInfo> errors4item = this.getResultsForItem(errors, item);
                if (!errors4item.isEmpty()) {
                    result.setMessage(computeSingleErrorMessage(errors4item));
                    getLprService().updateLprTransactionItem(item.getId(), item, contextInfo);
                    getLprService().changeLprTransactionItemState(item.getId(), LprServiceConstants.LPRTRANS_ITEM_FAILED_STATE_KEY,
                            contextInfo);
                    itemFailures++;
                }
            }
            List<ValidationResultInfo> generalErrors = this.getGeneralResults(errors);
            if (errors.size() == (itemFailures + generalErrors.size())) {
                // this shouldn't happen
                throw new RuntimeException(
                        "coding error -- the errors did not all fall into an item error nor general error for display back to the user: " + errors);
            }
            // TODO: setup a message structure on the overall request instead of using the description
            trans.setDescr(new RichTextHelper().fromPlain(this.computeSingleErrorMessage(generalErrors, itemFailures)));
            trans = getLprService().updateLprTransaction(trans.getId(), trans, contextInfo);
            getLprService().changeLprTransactionState(regRequest.getId(), LprServiceConstants.LPRTRANS_FAILED_STATE_KEY, contextInfo);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return message;
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

    public static final String REQUEST_ITEM_ID_PREFIX = "requestItemId:";

    private List<ValidationResultInfo> getResultsForItem(List<ValidationResultInfo> results, LprTransactionItemInfo item) {
        // Note we shouldn't actually get any of these YET because we have not implemented item specific errors like pre-req checking
        List<ValidationResultInfo> filtered = new ArrayList<ValidationResultInfo>();
        for (ValidationResultInfo vr : results) {
            if (vr.getElement().contains(REQUEST_ITEM_ID_PREFIX + item.getId())) {
                filtered.add(vr);
            }
        }
        return filtered;
    }

    private List<ValidationResultInfo> getGeneralResults(List<ValidationResultInfo> results) {
        List<ValidationResultInfo> filtered = new ArrayList<ValidationResultInfo>();
        for (ValidationResultInfo vr : results) {
            if (!vr.getElement().contains(REQUEST_ITEM_ID_PREFIX)) {
                filtered.add(vr);
            }
        }
        return filtered;
    }

    private String computeSingleErrorMessage(List<ValidationResultInfo> errors) {
        StringBuilder bldr = new StringBuilder();
        String comma = "";
        for (ValidationResultInfo vr : errors) {
            bldr.append(comma);
            comma = "; ";
            bldr.append(vr.getMessage());
        }
        return bldr.toString();
    }

    private String computeSingleErrorMessage(List<ValidationResultInfo> errors, int itemFailures) {

        StringBuilder bldr = new StringBuilder();
        String comma = "";
        for (ValidationResultInfo vr : errors) {
            bldr.append(comma);
            comma = "; ";
            bldr.append(vr.getMessage());
        }
        bldr.append(comma);
        bldr.append(itemFailures + " individual items failed");
        return bldr.toString();
    }

    @Override
    protected void notifyDestinations(Object outputMessage, String groupId) {
        RegistrationRequestEngineMessage mess = (RegistrationRequestEngineMessage) outputMessage;
        if (mess.isStopProcessing()) {
            return;
        }
        super.notifyDestinations(outputMessage, groupId);
    }

}
