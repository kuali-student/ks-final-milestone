package org.kuali.student.enrollment.class1.hold.rule;

import edu.emory.mathcs.backport.java.util.Collections;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.uif.rule.KsMaintenanceDocumentRuleBase;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class1.hold.dto.HoldIssueMaintenanceWrapper;
import org.kuali.student.enrollment.class1.hold.util.HoldIssueConstants;
import org.kuali.student.enrollment.class1.hold.util.HoldResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.registration.admin.util.AdminRegResourceLoader;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.constants.HoldServiceConstants;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;

import java.util.List;

public class HoldIssueRule extends KsMaintenanceDocumentRuleBase {

    @Override
    protected boolean isDocumentValidForSave(MaintenanceDocument maintenanceDocument) {
        if (!super.isDocumentValidForSave(maintenanceDocument)) {
            return false;
        }

        boolean isValid = true;
        HoldIssueMaintenanceWrapper holdWrapper = (HoldIssueMaintenanceWrapper) maintenanceDocument.getNewMaintainableObject().getDataObject();
        HoldIssueInfo holdIssue = holdWrapper.getHoldIssue();
        if (StringUtils.isBlank(holdIssue.getStateKey())) {
            holdIssue.setStateKey(HoldServiceConstants.ISSUE_ACTIVE_STATE_KEY);
        }

        if(holdIssue.getDescr() != null){
            holdIssue.getDescr().setPlain(holdWrapper.getDescr());
        } else{
            holdIssue.setDescr(new RichTextInfo());
            holdIssue.getDescr().setPlain(holdWrapper.getDescr());
        }

        holdIssue.setIsHoldIssueTermBased(holdWrapper.getTermBased());
        holdIssue.setMaintainHistoryOfApplicationOfHold(holdWrapper.getHoldHistory());
        try {
            holdIssue.setFirstApplicationTermId(searchForTermIdByCode(holdWrapper.getFirstTerm()));
        } catch (Exception e){
            GlobalVariables.getMessageMap().putError(HoldIssueConstants.HOLD_ISSUE_FIRST_APP_TERM_ID, HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_FIRST_APPLICATION_TERMID);
        }
        try {
            holdIssue.setLastApplicationTermId(searchForTermIdByCode(holdWrapper.getLastTerm()));
        } catch (Exception e){
            GlobalVariables.getMessageMap().putError(HoldIssueConstants.HOLD_ISSUE_LAST_APP_TERM_ID, HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_LAST_APPLICATION_TERMID);
        }
        holdIssue.setMaintainHistoryOfApplicationOfHold(holdWrapper.getHoldHistory());

        isValid &= validateHold(holdWrapper);

        return isValid;
    }

    public static String searchForTermIdByCode(String termCode)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {

        if((termCode==null) || (termCode.isEmpty())){
            return null;
        }

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal(CourseOfferingConstants.ATP_CODE, termCode));

        List<String> termIds = AdminRegResourceLoader.getAcademicCalendarService().searchForTermIds(qbcBuilder.build(),
                ContextUtils.createDefaultContextInfo());
        return KSCollectionUtils.getOptionalZeroElement(termIds);
    }

    /**
     * Performs a service layer validation of the HoldIssue. This validation is repeated in the call to
     *
     * @param holdIssueWrapper
     * @return True if the validation succeeds. Otherwise, false.
     */
    private boolean validateHold(HoldIssueMaintenanceWrapper holdIssueWrapper) {

        // Check if holdCode is unique.
        HoldIssueInfo holdIssueInfo = holdIssueWrapper.getHoldIssue();
        try {
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            if (holdIssueInfo.getId() == null) {
                qbcBuilder.setPredicates(PredicateFactory.equal(HoldIssueConstants.HOLD_ISSUE_CODE, holdIssueInfo.getHoldCode()));
            } else {
                qbcBuilder.setPredicates(PredicateFactory.and(PredicateFactory.equal(HoldIssueConstants.HOLD_ISSUE_CODE, holdIssueInfo.getHoldCode()),
                        PredicateFactory.notEqual(HoldIssueConstants.HOLD_ISSUE_ID, holdIssueInfo.getId())));
            }
            List<String> issueIds = HoldResourceLoader.getHoldService().searchForHoldIssueIds(qbcBuilder.build(), createContextInfo());

            if (issueIds.size() > 0) {
                GlobalVariables.getMessageMap().putError(HoldIssueConstants.HOLD_ISSUE_CODE, HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_HOLDCODE_ALREADY_EXISTS);
            }
        } catch (Exception e) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, e.getMessage());
        }

        List<ValidationResultInfo> errors = transformValidationErrors(getValidationErrors(holdIssueInfo));

        //  If any errors were found, put them in the message map.
        if (!errors.isEmpty()) {
            for (ValidationResultInfo error : errors) {
                GlobalVariables.getMessageMap().putError(error.getElement(), RiceKeyConstants.ERROR_CUSTOM, error.getMessage());
            }
            return false;
        }

        return true;
    }

    private List<ValidationResultInfo> getValidationErrors(HoldIssueInfo holdIssueInfo) {

        List<ValidationResultInfo> errors = Collections.emptyList();
        try {
            errors = HoldResourceLoader.getHoldService().validateHoldIssue(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), holdIssueInfo, createContextInfo());
        } catch (Exception e) {
            //  Capture the error if the service call fails.
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, e.getMessage());
        }

        return errors;
    }

    private List<ValidationResultInfo> transformValidationErrors(List<ValidationResultInfo> validationErrors) {

        for (ValidationResultInfo error : validationErrors) {
            error.setElement(HoldIssueConstants.HOLD_ISSUE_HOLDISSUE_ELEMENTPATH + "." + error.getElement());
        }

        return validationErrors;
    }

    private ContextInfo createContextInfo() {
        return ContextUtils.createDefaultContextInfo();
    }
}
