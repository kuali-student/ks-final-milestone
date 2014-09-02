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
import org.kuali.student.enrollment.class1.hold.dto.AuthorizedOrgWrapper;
import org.kuali.student.enrollment.class1.hold.dto.HoldIssueMaintenanceWrapper;
import org.kuali.student.enrollment.class1.hold.util.HoldIssueConstants;
import org.kuali.student.enrollment.class1.hold.util.HoldResourceLoader;
import org.kuali.student.enrollment.class2.acal.util.AcalCommonUtils;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.acal.dto.TermInfo;
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

        if (holdIssue.getDescr() != null) {
            holdIssue.getDescr().setPlain(holdWrapper.getDescr());
        } else {
            holdIssue.setDescr(new RichTextInfo());
            holdIssue.getDescr().setPlain(holdWrapper.getDescr());
        }

        holdIssue.setIsHoldIssueTermBased(holdWrapper.getTermBased());
        holdIssue.setMaintainHistoryOfApplicationOfHold(holdWrapper.getHoldHistory());
        holdIssue.setMaintainHistoryOfApplicationOfHold(holdWrapper.getHoldHistory());

        isValid &= validateHold(holdWrapper);

        return isValid;
    }

    public static TermInfo searchForTermIdByCode(String termCode)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {

        if ((termCode == null) || (termCode.isEmpty())) {
            return null;
        }

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal(CourseOfferingConstants.ATP_CODE, termCode));


        List<TermInfo> results = HoldResourceLoader.getAcademicCalendarService().getTermsByCode(termCode,   ContextUtils.createDefaultContextInfo());
        return KSCollectionUtils.getOptionalZeroElement(results);
    }

    /**
     * Performs a service layer validation of the HoldIssue. This validation is repeated in the call to
     *
     * @param holdIssueWrapper
     * @return True if the validation succeeds. Otherwise, false.
     */
    private boolean validateHold(HoldIssueMaintenanceWrapper holdIssueWrapper) {

        boolean isValid = true;
        HoldIssueInfo holdIssue = holdIssueWrapper.getHoldIssue();

        //Check if the StartDate and EndDate is in range
        if (!AcalCommonUtils.isValidDateRange(holdIssue.getFirstAppliedDate(), holdIssue.getLastAppliedDate())) {
            GlobalVariables.getMessageMap().putError(HoldIssueConstants.HOLD_ISSUE_PROP_NAME_LAST_APPLIED_DATE, HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_INVALID_DATE_RANGE,
                    AcalCommonUtils.formatDate(holdIssue.getFirstAppliedDate()), AcalCommonUtils.formatDate(holdIssue.getLastAppliedDate()));
            isValid = false;
        }

        // Check if term exist for code.
        if(holdIssue.getIsHoldIssueTermBased()) {
            isValid &= validateTerm(holdIssueWrapper,holdIssue);
        }

        // Check if holdCode is unique.
        try {
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            if (holdIssue.getId() == null) {
                qbcBuilder.setPredicates(PredicateFactory.equal(HoldIssueConstants.HOLD_ISSUE_CODE, holdIssue.getHoldCode()));
            } else {
                qbcBuilder.setPredicates(PredicateFactory.and(PredicateFactory.equal(HoldIssueConstants.HOLD_ISSUE_CODE, holdIssue.getHoldCode()),
                        PredicateFactory.notEqual(HoldIssueConstants.HOLD_ISSUE_ID, holdIssue.getId())));
            }
            List<String> issueIds = HoldResourceLoader.getHoldService().searchForHoldIssueIds(qbcBuilder.build(), createContextInfo());

            if (issueIds.size() > 0) {
                GlobalVariables.getMessageMap().putError(HoldIssueConstants.HOLD_ISSUE_PROP_NAME_CODE, HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_HOLDCODE_ALREADY_EXISTS);
                isValid = false;
            }
        } catch (Exception e) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, e.getMessage());
            isValid = false;
        }

        // Validate the authorized orgs.
        for (int i = 0; i < holdIssueWrapper.getAuthorizedOrgs().size(); i++) {
            AuthorizedOrgWrapper authorizedOrg = holdIssueWrapper.getAuthorizedOrgs().get(i);
            if (!authorizedOrg.isAuthOrgApply() && !authorizedOrg.isAuthOrgExpire()) {
                GlobalVariables.getMessageMap().putError(HoldIssueConstants.HOLD_ISSUE_PROP_NAME_AUTH_ORGS + "[" + i + "]." + HoldIssueConstants.HOLD_ISSUE_PROP_NAME_AUTH_ORG_NAME,
                        HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_AUTHORIZED_ORG);
                isValid = false;
            }
        }

        //  If any errors were found, put them in the message map.
        List<ValidationResultInfo> errors = transformValidationErrors(getValidationErrors(holdIssue));
        if (!errors.isEmpty()) {
            for (ValidationResultInfo error : errors) {
                String elementPath = error.getElement();
                if (StringUtils.equals(elementPath, HoldIssueConstants.HOLD_ISSUE_HOLDISSUE_PATH + "." + HoldIssueConstants.HOLD_ISSUE_ORG_ID)) {
                    GlobalVariables.getMessageMap().putError(error.getElement(), HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_HOLD_ISSUE_ORG_ID_REQUIRED);
                } else {
                    GlobalVariables.getMessageMap().putError(error.getElement(), RiceKeyConstants.ERROR_CUSTOM, error.getMessage());
                }

            }
            isValid = false;
        }

        return isValid;
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
            error.setElement(HoldIssueConstants.HOLD_ISSUE_HOLDISSUE_PATH + "." + error.getElement());
        }

        return validationErrors;

   }

    /**
     * Performs a service layer validation of the HoldIssue. This validation is repeated in the call to
     *
     * @param holdIssueWrapper
     * @param holdIssue
     * @return True if the validation succeeds. Otherwise, false.
     */
    private boolean validateTerm(HoldIssueMaintenanceWrapper holdIssueWrapper, HoldIssueInfo holdIssue ) {

        TermInfo firstTermInfo = null;
        TermInfo lastTermInfo = null;
        if(StringUtils.isBlank(holdIssueWrapper.getFirstTerm())){
            GlobalVariables.getMessageMap().putError(HoldIssueConstants.HOLD_ISSUE_PROP_NAME_FIRST_TERM, HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_FIRST_TERM_REQUIRED);
            return false;
        }
        try {
            firstTermInfo = searchForTermIdByCode(holdIssueWrapper.getFirstTerm());
            if(firstTermInfo != null){
            holdIssue.setFirstApplicationTermId(firstTermInfo.getId());
            }
            else{
                GlobalVariables.getMessageMap().putError(HoldIssueConstants.HOLD_ISSUE_PROP_NAME_FIRST_TERM, HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_INVALID_TERM, holdIssueWrapper.getFirstTerm());
            }
        } catch (Exception e) {
            GlobalVariables.getMessageMap().putError(HoldIssueConstants.HOLD_ISSUE_PROP_NAME_FIRST_TERM, HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_FIRST_APPLICATION_TERMID);
            return false;
        }
        try {
            if(!StringUtils.isBlank(holdIssueWrapper.getLastTerm())){
                lastTermInfo =searchForTermIdByCode(holdIssueWrapper.getLastTerm());
                if(lastTermInfo != null){
                   holdIssue.setLastApplicationTermId(lastTermInfo.getId());
                }
                else{
                    GlobalVariables.getMessageMap().putError(HoldIssueConstants.HOLD_ISSUE_PROP_NAME_LAST_TERM, HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_INVALID_TERM,holdIssueWrapper.getLastTerm());
                }
            }
        } catch (Exception e) {
            GlobalVariables.getMessageMap().putError(HoldIssueConstants.HOLD_ISSUE_PROP_NAME_LAST_TERM, HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_LAST_APPLICATION_TERMID);
            return false;
        }
        return true;
    }

    private ContextInfo createContextInfo() {
        return ContextUtils.createDefaultContextInfo();
    }
}
