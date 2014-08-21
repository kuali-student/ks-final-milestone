package org.kuali.student.enrollment.class1.hold.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.student.common.uif.service.impl.KSMaintainableImpl;
import org.kuali.student.enrollment.class1.hold.dto.HoldIssueMaintenanceWrapper;
import org.kuali.student.enrollment.class1.hold.util.HoldIssueResourceLoader;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.constants.HoldServiceConstants;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;

import java.util.Map;

public class HoldInfoMaintainableImpl extends KSMaintainableImpl {


    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {

        HoldIssueMaintenanceWrapper dataObject = new HoldIssueMaintenanceWrapper();

        String holdId = dataObjectKeys.get("id");
        setupDataObject(dataObject, holdId);

        return dataObject;
    }

    public void setupDataObject(HoldIssueMaintenanceWrapper dataObject, String holdId) {
        HoldIssueInfo holdIssueInfo = null;
        try {
            holdIssueInfo = HoldIssueResourceLoader.getHoldService().getHoldIssue(holdId, createContextInfo());
            if (!holdIssueInfo.equals(null)) {
                dataObject.setId(holdIssueInfo.getId());
                dataObject.setName(holdIssueInfo.getName());
                dataObject.setOrganizationId(holdIssueInfo.getOrganizationId());
                dataObject.setTypeKey(holdIssueInfo.getTypeKey());
                dataObject.setStateKey(holdIssueInfo.getStateKey());
                dataObject.setDescr(holdIssueInfo.getDescr().getPlain());
                dataObject.setHoldIssueInfo(holdIssueInfo);
            }
        } catch (Exception e) {

            convertServiceExceptionsToUI(e);
        }
    }

    @Override
    public void saveDataObject() {
        HoldIssueMaintenanceWrapper holdWrapper = (HoldIssueMaintenanceWrapper) getDataObject();
        HoldIssueInfo holdIssueInfo = new HoldIssueInfo();
        holdIssueInfo.setName(holdWrapper.getName());
        holdIssueInfo.setTypeKey(holdWrapper.getTypeKey());
        holdIssueInfo.setStateKey(HoldServiceConstants.ISSUE_ACTIVE_STATE_KEY);
        holdIssueInfo.setOrganizationId(holdWrapper.getOrganizationId());
        RichTextInfo richTextInfo = new RichTextInfo();
        richTextInfo.setPlain(holdWrapper.getDescr());
        holdIssueInfo.setDescr(richTextInfo);
        if (StringUtils.isBlank(holdWrapper.getId())) {

            try {
                HoldIssueInfo createHoldIssueInfo = HoldIssueResourceLoader.getHoldService().createHoldIssue(holdIssueInfo.getTypeKey(), holdIssueInfo, createContextInfo());
            } catch (Exception e) {

                convertServiceExceptionsToUI(e);
            }
        } else {
            try {
                holdIssueInfo.setId(holdWrapper.getId());
                HoldIssueInfo updatedHoldIssueInfo = HoldIssueResourceLoader.getHoldService().updateHoldIssue(holdIssueInfo.getId(), holdIssueInfo, createContextInfo());
            } catch (Exception e) {

                convertServiceExceptionsToUI(e);
            }
        }

    }

}
