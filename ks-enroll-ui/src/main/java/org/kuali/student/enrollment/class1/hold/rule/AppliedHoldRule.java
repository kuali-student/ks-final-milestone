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
import org.kuali.student.enrollment.class1.hold.dto.AppliedHoldMaintenanceWrapper;
import org.kuali.student.enrollment.class1.hold.dto.AuthorizedOrgWrapper;
import org.kuali.student.enrollment.class1.hold.dto.HoldIssueMaintenanceWrapper;
import org.kuali.student.enrollment.class1.hold.util.HoldsConstants;
import org.kuali.student.enrollment.class1.hold.util.HoldsResourceLoader;
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

    private ContextInfo createContextInfo() {
        return ContextUtils.createDefaultContextInfo();
    }
}
