package org.kuali.student.enrollment.class1.hold.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.student.common.uif.service.impl.KSMaintainableImpl;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class1.hold.dto.HoldIssueMaintenanceWrapper;
import org.kuali.student.enrollment.class1.hold.util.HoldResourceLoader;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;

import java.util.Date;
import java.util.Map;

public class HoldInfoMaintainableImpl extends KSMaintainableImpl {

    public static final String NEW_HOLD_ISSUE_DOCUMENT_TEXT = "New Hold Issue Document";
    public static final String MODIFY_HOLD_ISSUE_DOCUMENT_TEXT = "Modify Hold Issue Document";

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {

        String holdId = dataObjectKeys.get("id");
        if (holdId == null) {
            return setupDataObjectForCreate();
        } else {
            return setupDataObjectForEdit(holdId);
        }
    }

    public HoldIssueMaintenanceWrapper setupDataObjectForCreate() {

        HoldIssueMaintenanceWrapper dataObject = new HoldIssueMaintenanceWrapper();
        try {
            HoldIssueInfo holdIssueInfo = new HoldIssueInfo();
            holdIssueInfo.setIsHoldIssueTermBased(false);
            holdIssueInfo.setMaintainHistoryOfApplicationOfHold(false);
            holdIssueInfo.setFirstAppliedDate(new Date());
            dataObject.setTermBased(false);
            dataObject.setHoldIssue(holdIssueInfo);

        } catch (Exception e) {
            convertServiceExceptionsToUI(e);
        }
        return dataObject;
    }

    public HoldIssueMaintenanceWrapper setupDataObjectForEdit(String holdId) {

        HoldIssueMaintenanceWrapper dataObject = new HoldIssueMaintenanceWrapper();
        try {
            HoldIssueInfo holdIssueInfo = HoldResourceLoader.getHoldService().getHoldIssue(holdId, ContextUtils.createDefaultContextInfo());
            dataObject.setDescr(holdIssueInfo.getDescr() != null ? holdIssueInfo.getDescr().getPlain() : StringUtils.EMPTY);

            // Set term information.
            dataObject.setTermBased(holdIssueInfo.getIsHoldIssueTermBased());
            if (holdIssueInfo.getIsHoldIssueTermBased()) {
                dataObject.setFirstTerm(getTermCodeForId(holdIssueInfo.getFirstApplicationTermId()));
                dataObject.setLastTerm(getTermCodeForId(holdIssueInfo.getLastApplicationTermId()));
            }

            dataObject.setHoldIssue(holdIssueInfo);

        } catch (Exception e) {
            convertServiceExceptionsToUI(e);
        }
        return dataObject;
    }

    public String getTermCodeForId(String termId) {
        if(termId==null){
            return StringUtils.EMPTY;
        }

        try {
            TermInfo term = HoldResourceLoader.getAcademicCalendarService().getTerm(termId, ContextUtils.createDefaultContextInfo());
            term.getCode();
        } catch (Exception e){
            convertServiceExceptionsToUI(e);
        }

        return StringUtils.EMPTY;
    }

    @Override
    public void saveDataObject() {
        HoldIssueMaintenanceWrapper holdWrapper = (HoldIssueMaintenanceWrapper) getDataObject();
        HoldIssueInfo holdIssueInfo = holdWrapper.getHoldIssue();

        try {
            if (StringUtils.isBlank(holdWrapper.getHoldIssue().getId())) {
                HoldResourceLoader.getHoldService().createHoldIssue(holdIssueInfo.getTypeKey(), holdIssueInfo, ContextUtils.createDefaultContextInfo());
            } else {
                holdIssueInfo.setId(holdWrapper.getHoldIssue().getId());
                HoldResourceLoader.getHoldService().updateHoldIssue(holdIssueInfo.getId(), holdIssueInfo, ContextUtils.createDefaultContextInfo());
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
        document.getDocumentHeader().setDocumentDescription(NEW_HOLD_ISSUE_DOCUMENT_TEXT);
    }

    @Override
    public void processAfterEdit(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        super.processAfterEdit(document, requestParameters);
        document.getDocumentHeader().setDocumentDescription(MODIFY_HOLD_ISSUE_DOCUMENT_TEXT);
    }

}
