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
import org.kuali.student.r2.core.acal.dto.TermInfo;
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

        HoldIssueInfo holdIssue = holdWrapper.getHoldIssue();
        appliedHold.setHoldIssueId(holdIssue.getId());
        appliedHold.setTypeKey(holdIssue.getTypeKey());
        appliedHold.setName(holdIssue.getName());
        appliedHold.setDescr(holdIssue.getDescr());

        if (appliedHold.getExpirationDate() == null) {
            appliedHold.setExpirationDate(holdIssue.getLastAppliedDate());
        }

        isValid &= validateBasicHold(appliedHold);
        isValid &= validateAppliedHold(appliedHold, holdWrapper);
        if (holdIssue.getIsHoldIssueTermBased()) {
            if (holdWrapper.getExpirationTerm() != null && holdWrapper.getAction().equals(HoldsConstants.APPLIED_HOLDS_ACTION_EXPIRE)) {
                appliedHold.setApplicationExpirationTermId(resolveTermId(holdWrapper.getExpirationTerm(), HoldsConstants.APPLIED_HOLDS_PROP_NAME_EXPIRATION_TERM));
            } else {
                appliedHold.setApplicationExpirationTermId(holdIssue.getLastApplicationTermId());
            }
            isValid &= validateTerm(holdWrapper, appliedHold);
        }
        isValid &= validateHoldsDates(appliedHold, holdIssue);

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

    private boolean validateAppliedHold(AppliedHoldInfo appliedHold, AppliedHoldMaintenanceWrapper holdWrapper) {

        boolean isValid = true;
        MessageMap messages = GlobalVariables.getMessageMap();

        try {

            if (appliedHold.getEffectiveDate() != null) {
                //Check if the StartDate and EndDate is in range
                if (!AcalCommonUtils.isValidDateRange(appliedHold.getEffectiveDate(), appliedHold.getExpirationDate())) {
//                    messages.putError(HoldsConstants.APPLIED_HOLDS_PROP_NAME_EXPIRATION_DATE, HoldsConstants.HOLDS_ISSUE_MSG_ERROR_INVALID_DATE_RANGE,
//                            AcalCommonUtils.formatDate(appliedHold.getEffectiveDate()), AcalCommonUtils.formatDate(appliedHold.getExpirationDate()));
//                    isValid = false;

                    if (holdWrapper.getAction().equals(HoldsConstants.APPLIED_HOLDS_ACTION_APPLY)) {
                        messages.putError(HoldsConstants.APPLIED_HOLDS_PROP_NAME_EFFECTIVE_DATE, HoldsConstants.HOLDS_ISSUE_MSG_ERROR_INVALID_DATE_RANGE,
                                AcalCommonUtils.formatDate(appliedHold.getEffectiveDate()), AcalCommonUtils.formatDate(appliedHold.getExpirationDate()));
                        isValid = false;
                    } else if (holdWrapper.getAction().equals(HoldsConstants.APPLIED_HOLDS_ACTION_EXPIRE)) {
                        messages.putError(HoldsConstants.APPLIED_HOLDS_PROP_NAME_EXPIRATION_DATE, HoldsConstants.HOLDS_ISSUE_MSG_ERROR_INVALID_DATE_RANGE,
                                AcalCommonUtils.formatDate(appliedHold.getEffectiveDate()), AcalCommonUtils.formatDate(appliedHold.getExpirationDate()));
                        isValid = false;
                    }
                } else if (appliedHold.getId() == null) {
                    List<AppliedHoldInfo> appliedHolds = HoldsResourceLoader.getHoldService().getAppliedHoldsByIssueAndPerson(appliedHold.getHoldIssueId(),
                            appliedHold.getPersonId(), createContextInfo());
                    for (AppliedHoldInfo existingAppliedHold : appliedHolds) {
                        if (existingAppliedHold.getExpirationDate() == null) {
                            messages.putError(HoldsConstants.APPLIED_HOLDS_PROP_NAME_CODE, HoldsConstants.APPLIED_HOLDS_MSG_ERROR_ACTIVE_HOLD_CODE_ALREADY_APPLIED);
                            isValid = false;
                        } else {
                            if (appliedHold.getEffectiveDate().before(existingAppliedHold.getExpirationDate())) {
                                messages.putError(HoldsConstants.APPLIED_HOLDS_PROP_NAME_EFFECTIVE_DATE, HoldsConstants.APPLIED_HOLDS_MSG_ERROR_EXISTING_HOLD_CODE_INVALID_DATE_RANGE);
                                isValid = false;
                            }
                        }
                    }
                }
            } else {
                messages.putError(HoldsConstants.APPLIED_HOLDS_PROP_NAME_EFFECTIVE_DATE, HoldsConstants.APPLIED_HOLDS_MSG_ERROR_EFFECTIVE_DATE_REQUIRED);
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
    private boolean validateTerm(AppliedHoldMaintenanceWrapper holdWrapper, AppliedHoldInfo appliedHold) {

        boolean isValid = true;
        HoldIssueInfo holdIssue = holdWrapper.getHoldIssue();
        if (StringUtils.isBlank(holdWrapper.getEffectiveTerm())) {
            GlobalVariables.getMessageMap().putError(HoldsConstants.APPLIED_HOLDS_PROP_NAME_EFFECTIVE_TERM, HoldsConstants.APPLIED_HOLDS_MSG_ERROR_EFFECTIVE_TERM_REQUIRED);
            isValid = false;
        } else {
            appliedHold.setApplicationEffectiveTermId(resolveTermId(holdWrapper.getEffectiveTerm(), HoldsConstants.APPLIED_HOLDS_PROP_NAME_EFFECTIVE_TERM));
            if (appliedHold.getApplicationEffectiveTermId() == null) {
                isValid = false;
            } else {
                try {
                    //validate terms Dates
                    TermInfo firstTerm = searchForTermIdById(holdIssue.getFirstApplicationTermId());
                    TermInfo effectiveTerm = searchForTermIdById(appliedHold.getApplicationEffectiveTermId());

                    TermInfo expirationTerm = null;
                    if (appliedHold.getApplicationExpirationTermId() != null) {
                        expirationTerm = searchForTermIdById(appliedHold.getApplicationExpirationTermId());
                    }

                    TermInfo lastTerm = null;
                    if (holdIssue.getLastApplicationTermId() != null) {
                        lastTerm = searchForTermIdById(holdIssue.getLastApplicationTermId());
                    }

                    if (effectiveTerm != null && firstTerm != null) {
                        if (!isDateBiggerThanOrEqual(effectiveTerm.getStartDate(), firstTerm.getStartDate())) {
                            GlobalVariables.getMessageMap().putError(HoldsConstants.APPLIED_HOLDS_PROP_NAME_EFFECTIVE_TERM,
                                    HoldsConstants.APPLIED_HOLDS_MSG_ERROR_EFFECTIVE_TERM_AFTER_INVALID_DATE_RANGE, firstTerm.getCode());
                            isValid = false;
                        }
                        if (lastTerm != null){
                            if (!isDateSmallerThanOrEqual(effectiveTerm.getStartDate(), lastTerm.getEndDate())) {
                                GlobalVariables.getMessageMap().putError(HoldsConstants.APPLIED_HOLDS_PROP_NAME_EFFECTIVE_TERM,
                                        HoldsConstants.APPLIED_HOLDS_MSG_ERROR_EFFECTIVE_TERM_BEFORE_INVALID_DATE_RANGE, lastTerm.getCode());
                                isValid = false;
                            }
                        }
                    }
                    if (expirationTerm != null && firstTerm != null ) {
                        if (!isDateBiggerThanOrEqual(expirationTerm.getStartDate(), effectiveTerm.getStartDate())) {
                            GlobalVariables.getMessageMap().putError(HoldsConstants.APPLIED_HOLDS_PROP_NAME_EXPIRATION_TERM,
                                    HoldsConstants.APPLIED_HOLDS_MSG_ERROR_EXPIRATION_TERM_BEFORE_INVALID_DATE_RANGE, effectiveTerm.getCode());
                            isValid = false;
                        } else if (!isDateBiggerThanOrEqual(expirationTerm.getStartDate(), firstTerm.getStartDate())) {
                            GlobalVariables.getMessageMap().putError(HoldsConstants.APPLIED_HOLDS_PROP_NAME_EXPIRATION_TERM,
                                   HoldsConstants.APPLIED_HOLDS_MSG_ERROR_EXPIRATION_TERM_BEFORE_INVALID_DATE_RANGE, firstTerm.getCode());
                            isValid = false;
                        }
                        if (lastTerm != null) {
                            if (!isDateSmallerThanOrEqual(expirationTerm.getEndDate(), lastTerm.getEndDate())) {
                                GlobalVariables.getMessageMap().putError(HoldsConstants.APPLIED_HOLDS_PROP_NAME_EXPIRATION_TERM,
                                        HoldsConstants.APPLIED_HOLDS_MSG_ERROR_EXPIRATION_TERM_AFTER_INVALID_DATE_RANGE, lastTerm.getCode());
                                isValid = false;
                            }
                        }
                    }

                } catch (Exception e) {
                    //  Capture the error if the service call fails.
                    GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, e.getMessage());
                }
            }
        }
        return isValid;
    }

    private boolean validateHoldsDates(AppliedHoldInfo appliedHold, HoldIssueInfo holdIssue) {

        boolean isValid = true;
        MessageMap messages = GlobalVariables.getMessageMap();

        try {
            if (appliedHold.getEffectiveDate() != null) {
                if (!isDateBiggerThanOrEqual(appliedHold.getEffectiveDate(), holdIssue.getFirstAppliedDate())) {
                    GlobalVariables.getMessageMap().putError(HoldsConstants.APPLIED_HOLDS_PROP_NAME_EFFECTIVE_DATE,
                            HoldsConstants.APPLIED_HOLDS_MSG_ERROR_EFFECTIVE_DATE_AFTER_INVALID_DATE_RANGE,
                            AcalCommonUtils.formatDate(holdIssue.getFirstAppliedDate()));
                    isValid = false;
                }
                if (holdIssue.getLastAppliedDate() != null) {
                    if (!isDateSmallerThanOrEqual(appliedHold.getEffectiveDate(), holdIssue.getLastAppliedDate())) {
                        GlobalVariables.getMessageMap().putError(HoldsConstants.APPLIED_HOLDS_PROP_NAME_EFFECTIVE_DATE,
                                HoldsConstants.APPLIED_HOLDS_MSG_ERROR_EFFECTIVE_DATE_BEFORE_INVALID_DATE_RANGE,
                                AcalCommonUtils.formatDate(holdIssue.getLastAppliedDate()));
                        isValid = false;
                    }
                }
            }

            if (appliedHold.getExpirationDate() != null) {
                if (!isDateBiggerThanOrEqual(appliedHold.getExpirationDate(), holdIssue.getFirstAppliedDate())) {
                    GlobalVariables.getMessageMap().putError(HoldsConstants.APPLIED_HOLDS_PROP_NAME_EXPIRATION_DATE,
                            HoldsConstants.APPLIED_HOLDS_MSG_ERROR_EXPIRATION_DATE_AFTER_INVALID_DATE_RANGE,
                            AcalCommonUtils.formatDate(holdIssue.getFirstAppliedDate()));
                    isValid = false;
                }
                if (holdIssue.getLastAppliedDate() != null) {
                    if (!isDateSmallerThanOrEqual(appliedHold.getExpirationDate(), holdIssue.getLastAppliedDate())) {
                        GlobalVariables.getMessageMap().putError(HoldsConstants.APPLIED_HOLDS_PROP_NAME_EXPIRATION_DATE,
                                HoldsConstants.APPLIED_HOLDS_MSG_ERROR_EXPIRATION_DATE_BEFORE_INVALID_DATE_RANGE,
                                AcalCommonUtils.formatDate(holdIssue.getLastAppliedDate()));
                        isValid = false;
                    }
                }
            }
        } catch (Exception e) {
            //  Capture the error if the service call fails.
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, e.getMessage());
        }
        return isValid;
    }

}
