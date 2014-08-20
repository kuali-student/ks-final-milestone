package org.kuali.student.enrollment.class1.hold.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.common.uif.service.impl.KSMaintainableImpl;
import org.kuali.student.enrollment.class1.hold.dto.HoldIssueMaintenanceWrapper;
import org.kuali.student.enrollment.class1.hold.service.HoldInfoMaintainable;
import org.kuali.student.enrollment.class1.hold.util.HoldIssueConstants;
import org.kuali.student.enrollment.class1.hold.util.HoldIssueResourceLoader;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;

public class HoldInfoMaintainableImpl extends KSMaintainableImpl implements HoldInfoMaintainable {

    @Override
    public HoldIssueInfo createHoldIssue(HoldIssueInfo holdIssue){
        HoldIssueInfo createHoldIssueInfo = null;
        try {
            createHoldIssueInfo = HoldIssueResourceLoader.getHoldService().createHoldIssue(holdIssue.getTypeKey(), holdIssue, createContextInfo());
        } catch (Exception e) {

            convertServiceExceptionsToUI(e);
        }
        return createHoldIssueInfo;
    }

    @Override
    public HoldIssueInfo updateHoldIssue(HoldIssueInfo holdIssue){
        HoldIssueInfo updatedHoldIssueInfo = null;
        try {
            updatedHoldIssueInfo = HoldIssueResourceLoader.getHoldService().updateHoldIssue(holdIssue.getId(), holdIssue, createContextInfo());
        } catch (Exception e) {

            convertServiceExceptionsToUI(e);
        }
        return updatedHoldIssueInfo;
    }

    @Override
    public void validateHold(HoldIssueMaintenanceWrapper holdIssueWrapper){
        if (StringUtils.isBlank(holdIssueWrapper.getName())) {
            GlobalVariables.getMessageMap().putError(HoldIssueConstants.HOLD_ISSUE_NAME, HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_HOLD_ISSUE_NAME_REQUIRED);
        }
        if (StringUtils.isBlank(holdIssueWrapper.getCode())) {
            GlobalVariables.getMessageMap().putError(HoldIssueConstants.HOLD_ISSUE_CODE, HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_HOLD_ISSUE_CODE_REQUIRED);
        }
        if (StringUtils.isBlank(holdIssueWrapper.getTypeKey())) {
            GlobalVariables.getMessageMap().putError(HoldIssueConstants.HOLD_ISSUE_TYPE, HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_HOLD_ISSUE_TYPE_REQUIRED);
        }

        if (StringUtils.isBlank(holdIssueWrapper.getOrganizationId())) {
            GlobalVariables.getMessageMap().putError(HoldIssueConstants.HOLD_ISSUE_ORG_ID, HoldIssueConstants.HOLDS_ISSUE_MSG_ERROR_HOLD_ISSUE_ORG_ID_REQUIRED);
        }

    }


}
