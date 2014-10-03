package org.kuali.student.enrollment.class1.hold.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.student.common.uif.service.impl.KSMaintainableImpl;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class1.hold.dto.AuthorizedOrgWrapper;
import org.kuali.student.enrollment.class1.hold.dto.HoldIssueMaintenanceWrapper;
import org.kuali.student.enrollment.class1.hold.util.HoldIssueHelper;
import org.kuali.student.enrollment.class1.hold.util.HoldsConstants;
import org.kuali.student.enrollment.class1.hold.service.facade.HoldIssueAuthorizingOrgFacade;
import org.kuali.student.enrollment.class1.hold.util.HoldsResourceLoader;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HoldIssueMaintainableImpl extends KSMaintainableImpl {

    private transient HoldIssueAuthorizingOrgFacade holdIssueAuthorizingOrgFacade;

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {

        try {
            String holdId = dataObjectKeys.get(HoldsConstants.HOLD_ISSUE_ID);
            if (holdId != null) {
                return HoldIssueHelper.constructHoldIssueWrapper(holdId);
            }
        } catch (Exception e) {
            convertServiceExceptionsToUI(e);
        }

        return HoldIssueHelper.constructNewHoldIssueWrapper();
    }

    @Override
    public void saveDataObject() {
        HoldIssueMaintenanceWrapper holdWrapper = (HoldIssueMaintenanceWrapper) getDataObject();
        HoldIssueInfo holdIssueInfo = null;
        try {
            if (StringUtils.isBlank(holdWrapper.getHoldIssue().getId())) {
                holdIssueInfo = HoldsResourceLoader.getHoldService().createHoldIssue(holdWrapper.getHoldIssue().getTypeKey(),
                        holdWrapper.getHoldIssue(), ContextUtils.createDefaultContextInfo());
            } else {
                holdIssueInfo = HoldsResourceLoader.getHoldService().updateHoldIssue(holdWrapper.getHoldIssue().getId(),
                        holdWrapper.getHoldIssue(), ContextUtils.createDefaultContextInfo());
            }

            List<String> applyOrgs = new ArrayList<String>();
            List<String> expireOrgs = new ArrayList<String>();
            for (AuthorizedOrgWrapper authorizedOrg : holdWrapper.getAuthorizedOrgs()) {
                if (authorizedOrg.isAuthOrgApply()) {
                    applyOrgs.add(authorizedOrg.getId());
                }
                if (authorizedOrg.isAuthOrgExpire()) {
                    expireOrgs.add(authorizedOrg.getId());
                }
            }

            List<Role> roles = HoldsResourceLoader.getHoldIssueAuthorizingOrgFacade().getHoldFunctions(ContextUtils.createDefaultContextInfo());
            for (Role role : roles) {
                if (role.getName().equals(HoldsConstants.APPLY_HOLD_ROLE_PERMISSION)) {
                    maintainAuthorizedOrgs(holdIssueInfo, applyOrgs, role);
                } else if (role.getName().equals(HoldsConstants.EXPIRE_APPLIED_HOLD_ROLE_PERMISSION)) {
                    maintainAuthorizedOrgs(holdIssueInfo, expireOrgs, role);
                }

            }
        } catch (Exception e) {
            convertServiceExceptionsToUI(e);
        }

    }

    private void maintainAuthorizedOrgs(HoldIssueInfo holdIssueInfo, List<String> applyOrgs, Role role)
            throws DoesNotExistException, MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {

        List<String> orgIds = HoldsResourceLoader.getHoldIssueAuthorizingOrgFacade().getBindingByRoleAndHoldIssue(role.getId(), holdIssueInfo.getId(),
                ContextUtils.createDefaultContextInfo());

        for (String orgId : applyOrgs) {
            if (!orgIds.contains(orgId)) {
                HoldsResourceLoader.getHoldIssueAuthorizingOrgFacade().storeBinding(holdIssueInfo.getId(), orgId,
                        role, ContextUtils.createDefaultContextInfo());
            }
            orgIds.remove(orgId);
        }

        for (String orgId : orgIds) {
            HoldsResourceLoader.getHoldIssueAuthorizingOrgFacade().removeBinding(holdIssueInfo.getId(), orgId,
                    role, ContextUtils.createDefaultContextInfo());
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
