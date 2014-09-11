package org.kuali.student.enrollment.class1.hold.rule;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.MessageMap;
import org.kuali.student.enrollment.class1.hold.dto.AppliedHoldMaintenanceWrapper;
import org.kuali.student.enrollment.class1.hold.util.HoldsConstants;
import org.kuali.student.enrollment.class1.hold.util.HoldsResourceLoader;
import org.kuali.student.enrollment.class2.acal.util.AcalCommonUtils;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.core.constants.HoldServiceConstants;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;

import java.util.List;

/**
 * Maintenance document rule class to perform bussiness rule validation on applied holds.
 *
 * @author Kuali Student Blue Team (SA)
 */
public class AppliedHoldRule extends BasicHoldsRule {

    @Override
    protected boolean isDocumentValidForSave(MaintenanceDocument maintenanceDocument) {

        boolean isValid = super.isDocumentValidForSave(maintenanceDocument);

        AppliedHoldMaintenanceWrapper holdWrapper = (AppliedHoldMaintenanceWrapper) maintenanceDocument.getNewMaintainableObject().getDataObject();
        AppliedHoldInfo appliedHold = holdWrapper.getAppliedHold();
        HoldIssueInfo holdIssueInfo = holdWrapper.getHoldIssue();

        if (StringUtils.isBlank(appliedHold.getStateKey())) {
            appliedHold.setStateKey(HoldServiceConstants.HOLD_ACTIVE_STATE_KEY);
        }

        HoldIssueInfo holdIssue = holdWrapper.getHoldIssue();
        appliedHold.setHoldIssueId(holdIssue.getId());
        appliedHold.setTypeKey(holdIssue.getTypeKey());
        appliedHold.setName(holdIssue.getName());
        appliedHold.setDescr(holdIssue.getDescr());

        if (appliedHold.getExpirationDate() == null) {
            appliedHold.setExpirationDate(holdIssue.getLastAppliedDate());
        }


        isValid &= validateBasicHold(appliedHold);
        isValid &= validateAppliedHold(appliedHold);
        if (holdIssue.getIsHoldIssueTermBased()) {
            holdWrapper.setLastTerm(resolveTermCode(holdIssue.getLastApplicationTermId()));
            isValid &= validateTerm(holdWrapper);
        } else {
            //set applied hold terms to null.
        }

        return isValid;
    }

    private boolean validateBasicHold(AppliedHoldInfo appliedHold) {

        try {

            List<ValidationResultInfo> errors = HoldsResourceLoader.getHoldService().validateAppliedHold(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                    appliedHold, createContextInfo());
            if (errors.isEmpty()) {
                return true;
            } else {
                for (ValidationResultInfo error : errors) {
                    error.setElement(HoldsConstants.APPLIED_HOLDS_PATH + "." + error.getElement());
                    GlobalVariables.getMessageMap().putError(error.getElement(), RiceKeyConstants.ERROR_CUSTOM, error.getMessage());
                }
            }
        } catch (Exception e) {
            //  Capture the error if the service call fails.
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, e.getMessage());
        }

        return false;
    }

    private boolean validateAppliedHold(AppliedHoldInfo appliedHold) {

        boolean isValid = true;
        MessageMap messages = GlobalVariables.getMessageMap();

        try {

            //Check if hold already applied to student?/Date based? TODO: KSENROLL-14775
            List<AppliedHoldInfo> appliedHolds = HoldsResourceLoader.getHoldService().getActiveAppliedHoldsByIssueAndPerson(appliedHold.getHoldIssueId(),
                    appliedHold.getPersonId(), createContextInfo());
            if (appliedHolds.size() > 0) {
                GlobalVariables.getMessageMap().putError(HoldsConstants.APPLIED_HOLDS_PROP_NAME_CODE, HoldsConstants.APPLIED_HOLDS_MSG_ERROR_HOLD_CODE_ALREADY_APPLIED);
                isValid = false;
            }
            if (appliedHold.getEffectiveDate() != null) {
                //Check if the StartDate and EndDate is in range
                if (!AcalCommonUtils.isValidDateRange(appliedHold.getEffectiveDate(), appliedHold.getExpirationDate())) {
                    messages.putError(HoldsConstants.APPLIED_HOLDS_PROP_NAME_FIRST_APPLIED_DATE, HoldsConstants.HOLDS_ISSUE_MSG_ERROR_INVALID_DATE_RANGE,
                            AcalCommonUtils.formatDate(appliedHold.getEffectiveDate()), AcalCommonUtils.formatDate(appliedHold.getExpirationDate()));
                    isValid = false;
                }
            } else {
                messages.putError(HoldsConstants.APPLIED_HOLDS_PROP_NAME_FIRST_APPLIED_DATE, HoldsConstants.APPLIED_HOLDS_MSG_ERROR_FIRST_APPLIED_DATE_REQUIRED);
                isValid = false;
            }

        } catch (Exception e) {
            //  Capture the error if the service call fails.
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, e.getMessage());
        }
        return isValid;
    }

    /**
     * Performs a service layer validation of the HoldIssue. This validation is repeated in the call to
     *
     * @param holdWrapper
     * @return True if the validation succeeds. Otherwise, false.
     */
    private boolean validateTerm(AppliedHoldMaintenanceWrapper holdWrapper) {

        boolean isValid = true;
        if (StringUtils.isBlank(holdWrapper.getFirstTerm())) {
            GlobalVariables.getMessageMap().putError(HoldsConstants.APPLIED_HOLDS_PROP_NAME_FIRST_TERM, HoldsConstants.APPLIED_HOLDS_MSG_ERROR_FIRST_TERM_REQUIRED);
            isValid = false;
        } //else {
        //  holdIssue.setFirstApplicationTermId(resolveTermId(holdWrapper.getFirstTerm(), HoldsConstants.HOLD_ISSUE_PROP_NAME_FIRST_TERM));
        //  if (holdIssue.getFirstApplicationTermId() == null) {
        //      isValid = false;
        //  }
        //}
        //if (!StringUtils.isBlank(holdWrapper.getLastTerm())) {
        //   holdIssue.setLastApplicationTermId(resolveTermId(holdWrapper.getLastTerm(), HoldsConstants.HOLD_ISSUE_PROP_NAME_LAST_TERM));
        //    if (holdIssue.getLastApplicationTermId() == null) {
        //       isValid = false;
        //   }
        //}
        return isValid;
    }

}
