package org.kuali.student.enrollment.class1.hold.rule;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.MessageMap;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.uif.rule.KsMaintenanceDocumentRuleBase;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class1.hold.dto.AuthorizedOrgWrapper;
import org.kuali.student.enrollment.class1.hold.dto.HoldIssueMaintenanceWrapper;
import org.kuali.student.enrollment.class1.hold.util.HoldIssueConstants;
import org.kuali.student.enrollment.class1.hold.util.HoldResourceLoader;
import org.kuali.student.enrollment.class2.acal.util.AcalCommonUtils;
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

import java.util.ArrayList;
import java.util.List;

public class HoldIssueRule extends KsMaintenanceDocumentRuleBase {

    @Override
    protected boolean isDocumentValidForSave(MaintenanceDocument maintenanceDocument) {

        boolean isValid = super.isDocumentValidForSave(maintenanceDocument);

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

        isValid &= validateBasicHold(holdIssue);
        isValid &= validateHold(holdWrapper);
        if (holdIssue.getIsHoldIssueTermBased()) {
            isValid &= validateTerm(holdWrapper, holdIssue);
        } else {
            holdIssue.setFirstApplicationTermId(null);
            holdIssue.setLastApplicationTermId(null);
        }

        return isValid;
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
        MessageMap messages = GlobalVariables.getMessageMap();

        //Check if the StartDate and EndDate is in range
        if (!AcalCommonUtils.isValidDateRange(holdIssue.getFirstAppliedDate(), holdIssue.getLastAppliedDate())) {
            messages.putError(HoldIssueConstants.HOLD_ISSUE_PROP_NAME_LAST_APPLIED_DATE, HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_INVALID_DATE_RANGE,
                    AcalCommonUtils.formatDate(holdIssue.getFirstAppliedDate()), AcalCommonUtils.formatDate(holdIssue.getLastAppliedDate()));
            isValid = false;
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
                messages.putError(HoldIssueConstants.HOLD_ISSUE_PROP_NAME_CODE, HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_HOLDCODE_ALREADY_EXISTS);
                isValid = false;
            }
        } catch (Exception e) {
            messages.putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, e.getMessage());
            isValid = false;
        }

        // Validate the authorized orgs.
        List<String> orgIds = new ArrayList<String>();
        for (int i = 0; i < holdIssueWrapper.getAuthorizedOrgs().size(); i++) {
            AuthorizedOrgWrapper authorizedOrg = holdIssueWrapper.getAuthorizedOrgs().get(i);
            if (authorizedOrg.getId() == null) {
                messages.putError(HoldIssueConstants.HOLD_ISSUE_PROP_NAME_AUTH_ORGS + "[" + i + "]." + HoldIssueConstants.HOLD_ISSUE_PROP_NAME_AUTH_ORG_NAME,
                        HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_AUTHORIZED_ORG_REQUIRED);
                isValid = false;
            }
            if (orgIds.contains(authorizedOrg.getId())) {
                messages.putError(HoldIssueConstants.HOLD_ISSUE_PROP_NAME_AUTH_ORGS + "[" + i + "]." + HoldIssueConstants.HOLD_ISSUE_PROP_NAME_AUTH_ORG_NAME,
                        HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_AUTHORIZED_ORG_DUPLICATE);
                isValid = false;
            }
            if (!authorizedOrg.isAuthOrgApply() && !authorizedOrg.isAuthOrgExpire()) {
                messages.putError(HoldIssueConstants.HOLD_ISSUE_PROP_NAME_AUTH_ORGS + "[" + i + "]." + HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_AUTHORIZED_ORG_REQUIRED,
                        HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_AUTHORIZED_ORG);
                isValid = false;
            }
            orgIds.add(authorizedOrg.getId());
        }

        // Check if term exist for code.
        return isValid;
    }

    private boolean validateBasicHold(HoldIssueInfo holdIssueInfo) {

        try {
            List<ValidationResultInfo> errors = HoldResourceLoader.getHoldService().validateHoldIssue(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                    holdIssueInfo, createContextInfo());
            if (errors.isEmpty()) {
                return true;
            } else {
                for (ValidationResultInfo error : errors) {
                    if (StringUtils.equals(error.getElement(), HoldIssueConstants.HOLD_ISSUE_ORG_ID)) {
                        GlobalVariables.getMessageMap().putError(error.getElement(), HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_HOLD_ISSUE_ORG_ID_REQUIRED);
                    } else {
                        error.setElement(HoldIssueConstants.HOLD_ISSUE_HOLDISSUE_PATH + "." + error.getElement());
                        GlobalVariables.getMessageMap().putError(error.getElement(), RiceKeyConstants.ERROR_CUSTOM, error.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            //  Capture the error if the service call fails.
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, e.getMessage());
        }

        return false;
    }

    /**
     * Performs a service layer validation of the HoldIssue. This validation is repeated in the call to
     *
     * @param holdIssueWrapper
     * @param holdIssue
     * @return True if the validation succeeds. Otherwise, false.
     */
    private boolean validateTerm(HoldIssueMaintenanceWrapper holdIssueWrapper, HoldIssueInfo holdIssue) {

        boolean isValid = true;
        if (StringUtils.isBlank(holdIssueWrapper.getFirstTerm())) {
            GlobalVariables.getMessageMap().putError(HoldIssueConstants.HOLD_ISSUE_PROP_NAME_FIRST_TERM, HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_FIRST_TERM_REQUIRED);
            isValid = false;
        } else {
            holdIssue.setFirstApplicationTermId(resolveTermId(holdIssueWrapper.getFirstTerm(), HoldIssueConstants.HOLD_ISSUE_PROP_NAME_FIRST_TERM));
            if (holdIssue.getFirstApplicationTermId() == null) {
                isValid = false;
            }
        }
        if (!StringUtils.isBlank(holdIssueWrapper.getLastTerm())) {
            holdIssue.setLastApplicationTermId(resolveTermId(holdIssueWrapper.getLastTerm(), HoldIssueConstants.HOLD_ISSUE_PROP_NAME_LAST_TERM));
            if (holdIssue.getLastApplicationTermId() == null) {
                isValid = false;
            }
        }
        return isValid;
    }

    private String resolveTermId(String termCode, String propertyName) {
        try {
            TermInfo firstTermInfo = searchForTermIdByCode(termCode);
            if (firstTermInfo != null) {
                return firstTermInfo.getId();
            } else {
                GlobalVariables.getMessageMap().putError(propertyName, HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_INVALID_TERM, termCode);
            }
        } catch (Exception e) {
            GlobalVariables.getMessageMap().putError(propertyName, HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_FIRST_APPLICATION_TERMID);
        }
        return null;
    }

    private static TermInfo searchForTermIdByCode(String termCode)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {

        if ((termCode == null) || (termCode.isEmpty())) {
            return null;
        }

        List<TermInfo> results = HoldResourceLoader.getAcademicCalendarService().getTermsByCode(termCode, ContextUtils.createDefaultContextInfo());
        return KSCollectionUtils.getOptionalZeroElement(results);
    }

    private ContextInfo createContextInfo() {
        return ContextUtils.createDefaultContextInfo();
    }
}
