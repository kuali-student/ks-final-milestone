package org.kuali.student.enrollment.class1.hold.rule;

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
import org.kuali.student.enrollment.class1.hold.dto.AppliedHoldMaintenanceWrapper;
import org.kuali.student.enrollment.class1.hold.util.HoldsConstants;
import org.kuali.student.enrollment.class1.hold.util.HoldsResourceLoader;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.core.constants.HoldServiceConstants;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Maintenance document rule class to perform bussiness rule validation on applied holds.
 *
 * @author Kuali Student Blue Team (SA)
 */
public class AppliedHoldRule extends KsMaintenanceDocumentRuleBase {

    @Override
    protected boolean isDocumentValidForSave(MaintenanceDocument maintenanceDocument) {

        boolean isValid = super.isDocumentValidForSave(maintenanceDocument);

        AppliedHoldMaintenanceWrapper holdWrapper = (AppliedHoldMaintenanceWrapper) maintenanceDocument.getNewMaintainableObject().getDataObject();
        AppliedHoldInfo appliedHold = holdWrapper.getAppliedHold();
        if (StringUtils.isBlank(appliedHold.getStateKey())) {
            appliedHold.setStateKey(HoldServiceConstants.ISSUE_ACTIVE_STATE_KEY);
        }

        isValid &= validateAppliedHold(holdWrapper, appliedHold);
        isValid &= validateBasicHold(appliedHold);

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
                    error.setElement(HoldsConstants.HOLD_ISSUE_HOLDISSUE_PATH + "." + error.getElement());
                    GlobalVariables.getMessageMap().putError(error.getElement(), RiceKeyConstants.ERROR_CUSTOM, error.getMessage());
                }
            }
        } catch (Exception e) {
            //  Capture the error if the service call fails.
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, e.getMessage());
        }

        return false;
    }

    private boolean validateAppliedHold(AppliedHoldMaintenanceWrapper holdWrapper, AppliedHoldInfo appliedHold) {
        boolean isValid = true;
        try {
            HoldIssueInfo holdIssueInfo = searchHoldIssueByCode(holdWrapper.getHoldCode());
            if (!holdIssueInfo.equals(null)) {
                List<AppliedHoldInfo> appliedHolds = HoldsResourceLoader.getHoldService().getActiveAppliedHoldsByIssueAndPerson(holdIssueInfo.getId(), appliedHold.getPersonId(), createContextInfo());
                if (appliedHolds.size() == 0) {
                    appliedHold.setHoldIssueId(holdIssueInfo.getId());
                    appliedHold.setTypeKey(holdIssueInfo.getTypeKey());
                    appliedHold.setName(holdIssueInfo.getName());
                    appliedHold.setDescr(holdIssueInfo.getDescr());
                } else {
                    isValid = false;
                    //Message for hold already applied to student?.
                    GlobalVariables.getMessageMap().putError(HoldsConstants.APPLIED_HOLDS_PROP_NAME_CODE, HoldsConstants.APPLIED_HOLDS_MSG_ERROR_HOLD_CODE_INVALID);
                }
            } else {
                //Invalid Hold Code
                GlobalVariables.getMessageMap().putError(HoldsConstants.APPLIED_HOLDS_PROP_NAME_CODE, HoldsConstants.APPLIED_HOLDS_MSG_ERROR_HOLD_CODE_INVALID);
                isValid = false;
            }
        } catch (Exception e) {
            //  Capture the error if the service call fails.
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, e.getMessage());
        }
        return isValid;
    }


    public HoldIssueInfo searchHoldIssueByCode(String holdCode) {
        HoldIssueInfo holdIssueInfo = new HoldIssueInfo();
        List<HoldIssueInfo> holdIssueInfos = new ArrayList<HoldIssueInfo>();
        QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(PredicateFactory.and(
                PredicateFactory.in(HoldsConstants.HOLD_ISSUE_CODE, holdCode)));

        try {
            holdIssueInfos = HoldsResourceLoader.getHoldService().searchForHoldIssues(query, createContextInfo());
            holdIssueInfo = KSCollectionUtils.getOptionalZeroElement(holdIssueInfos);
        } catch (Exception e) {

            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, e.getMessage());
        }
        return holdIssueInfo;
    }

    private ContextInfo createContextInfo() {
        return ContextUtils.createDefaultContextInfo();
    }
}
