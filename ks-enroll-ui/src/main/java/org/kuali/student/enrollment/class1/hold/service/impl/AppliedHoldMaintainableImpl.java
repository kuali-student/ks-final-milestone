package org.kuali.student.enrollment.class1.hold.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.student.common.uif.service.impl.KSMaintainableImpl;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class1.hold.dto.AppliedHoldMaintenanceWrapper;
import org.kuali.student.enrollment.class1.hold.util.HoldsConstants;
import org.kuali.student.enrollment.class1.hold.util.HoldsResourceLoader;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;

import java.util.Date;
import java.util.Map;

public class AppliedHoldMaintainableImpl extends KSMaintainableImpl {

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {

        String action = dataObjectKeys.get(HoldsConstants.HOLDS_URL_PARAMETERS_ACTION);
        if (HoldsConstants.APPLIED_HOLDS_ACTION_APPLY.equals(action)) {
            String personId = dataObjectKeys.get(HoldsConstants.HOLDS_URL_PARAMETERS_PERSON_ID);
            return setupDataObjectForCreate(personId, action);
        } else {
            String appliedHoldId = dataObjectKeys.get(HoldsConstants.HOLDS_URL_PARAMETERS_APPLIED_HOLD_ID);
            return setupDataObjectForEdit(appliedHoldId, action);
        }
    }

    public AppliedHoldMaintenanceWrapper setupDataObjectForCreate(String personId, String action) {

        AppliedHoldMaintenanceWrapper dataObject = new AppliedHoldMaintenanceWrapper();
        dataObject.setAction(action);

        AppliedHoldInfo appliedHold = new AppliedHoldInfo();
        appliedHold.setEffectiveDate(new Date());
        appliedHold.setPersonId(personId);
        dataObject.setAppliedHold(appliedHold);

        return dataObject;
    }

    public AppliedHoldMaintenanceWrapper setupDataObjectForEdit(String appliedHoldId, String action) {

        AppliedHoldMaintenanceWrapper dataObject = new AppliedHoldMaintenanceWrapper();
        dataObject.setAction(action);

        try {
            AppliedHoldInfo appliedHold = HoldsResourceLoader.getHoldService().getAppliedHold(appliedHoldId, ContextUtils.createDefaultContextInfo());
            dataObject.setAppliedHold(appliedHold);

            HoldIssueInfo holdIssueInfo = HoldsResourceLoader.getHoldService().getHoldIssue(appliedHold.getHoldIssueId(), ContextUtils.createDefaultContextInfo());
            dataObject.setHoldCode(holdIssueInfo.getHoldCode());
        } catch (Exception e) {
            convertServiceExceptionsToUI(e);
        }
        return dataObject;
    }

    @Override
    public void saveDataObject() {
        AppliedHoldMaintenanceWrapper holdWrapper = (AppliedHoldMaintenanceWrapper) getDataObject();
        try {
            if (StringUtils.isBlank(holdWrapper.getAppliedHold().getId())) {
                AppliedHoldInfo appliedHold = holdWrapper.getAppliedHold();
                HoldsResourceLoader.getHoldService().createAppliedHold(appliedHold.getPersonId(), appliedHold.getHoldIssueId(),
                        appliedHold.getTypeKey(), holdWrapper.getAppliedHold(), ContextUtils.createDefaultContextInfo());
            } else {
                HoldsResourceLoader.getHoldService().updateAppliedHold(holdWrapper.getAppliedHold().getId(),
                        holdWrapper.getAppliedHold(), ContextUtils.createDefaultContextInfo());
            }

        } catch (Exception e) {
            convertServiceExceptionsToUI(e);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        super.processAfterNew(document, requestParameters);
        document.getDocumentHeader().setDocumentDescription(HoldsConstants.NEW_HOLD_ISSUE_DOCUMENT_TEXT);
    }

    @Override
    public void processAfterEdit(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        super.processAfterEdit(document, requestParameters);
        document.getDocumentHeader().setDocumentDescription(HoldsConstants.MODIFY_HOLD_ISSUE_DOCUMENT_TEXT);
    }

}
