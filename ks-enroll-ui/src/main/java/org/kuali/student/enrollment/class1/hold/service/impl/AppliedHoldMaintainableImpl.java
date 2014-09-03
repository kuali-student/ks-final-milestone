package org.kuali.student.enrollment.class1.hold.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.student.common.uif.service.impl.KSMaintainableImpl;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class1.hold.dto.AppliedHoldMaintenanceWrapper;
import org.kuali.student.enrollment.class1.hold.service.facade.HoldIssueAuthorizingOrgFacade;
import org.kuali.student.enrollment.class1.hold.util.HoldsConstants;
import org.kuali.student.enrollment.class1.hold.util.HoldsResourceLoader;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;

import java.util.Map;

public class AppliedHoldMaintainableImpl extends KSMaintainableImpl {

    private transient HoldIssueAuthorizingOrgFacade holdIssueAuthorizingOrgFacade;

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {

        String holdId = dataObjectKeys.get(HoldsConstants.HOLD_ISSUE_ID);
        if (holdId == null) {
            return setupDataObjectForCreate();
        } else {
            return setupDataObjectForEdit(holdId);
        }
    }

    public AppliedHoldMaintenanceWrapper setupDataObjectForCreate() {

        AppliedHoldMaintenanceWrapper dataObject = new AppliedHoldMaintenanceWrapper();
        try {
            AppliedHoldInfo appliedHold = new AppliedHoldInfo();
            dataObject.setAppliedHold(appliedHold);

        } catch (Exception e) {
            convertServiceExceptionsToUI(e);
        }
        return dataObject;
    }

    public AppliedHoldMaintenanceWrapper setupDataObjectForEdit(String appliedHoldId) {

        AppliedHoldMaintenanceWrapper dataObject = new AppliedHoldMaintenanceWrapper();
        try {
            AppliedHoldInfo appliedHold = HoldsResourceLoader.getHoldService().getAppliedHold(appliedHoldId, ContextUtils.createDefaultContextInfo());
            dataObject.setAppliedHold(appliedHold);

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