package org.kuali.student.enrollment.class1.hold.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.student.common.uif.service.impl.KSMaintainableImpl;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class1.hold.dto.AuthorizedOrgWrapper;
import org.kuali.student.enrollment.class1.hold.dto.HoldIssueMaintenanceWrapper;
import org.kuali.student.enrollment.class1.hold.util.HoldIssueConstants;
import org.kuali.student.enrollment.class1.hold.service.facade.HoldIssueAuthorizingOrgFacade;
import org.kuali.student.enrollment.class1.hold.util.HoldResourceLoader;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;
import org.kuali.student.r2.core.organization.dto.OrgInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HoldInfoMaintainableImpl extends KSMaintainableImpl {

    private transient HoldIssueAuthorizingOrgFacade holdIssueAuthorizingOrgFacade;

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {

        String holdId = dataObjectKeys.get(HoldIssueConstants.HOLD_ISSUE_ID);
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

            // Setup AdminOrg <OrgInfo> data
            if(holdIssueInfo.getOrganizationId()!=null) {
                dataObject.setOrgName(HoldResourceLoader.getOrganizationService().getOrg(holdIssueInfo.getOrganizationId(),
                        ContextUtils.createDefaultContextInfo()).getShortName());
            }
            dataObject.setHoldHistory(holdIssueInfo.getMaintainHistoryOfApplicationOfHold());
            dataObject.setHoldIssue(holdIssueInfo);

            dataObject.setAuthorizedOrgs(getAuthorizedOrgs(holdIssueInfo));
        } catch (Exception e) {
            convertServiceExceptionsToUI(e);
        }
        return dataObject;
    }

    private List<AuthorizedOrgWrapper> getAuthorizedOrgs(HoldIssueInfo holdIssueInfo)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {

        Map<String, AuthorizedOrgWrapper> authorizedOrgs = new HashMap<String, AuthorizedOrgWrapper>();
        List<Role> roles = HoldResourceLoader.getHoldIssueAuthorizingOrgFacade().getHoldFunctions(ContextUtils.createDefaultContextInfo());
        for (Role role : roles) {

            List<String> orgIds = HoldResourceLoader.getHoldIssueAuthorizingOrgFacade().getBindingByRoleAndHoldIssue(role.getId(),
                    holdIssueInfo.getId(), ContextUtils.createDefaultContextInfo());

            for (String orgId : orgIds) {
                AuthorizedOrgWrapper authorizedOrg = authorizedOrgs.get(orgId);
                if (authorizedOrg == null) {
                    authorizedOrg = new AuthorizedOrgWrapper();
                    authorizedOrg.setId(orgId);
                    authorizedOrg.setName(HoldResourceLoader.getOrganizationService().getOrg(orgId,
                            ContextUtils.createDefaultContextInfo()).getShortName());
                    authorizedOrgs.put(orgId, authorizedOrg);
                }
                if (role.getName().equals(HoldIssueConstants.APPLY_HOLD_ROLE_PERMISSION)) {
                    authorizedOrg.setAuthOrgApply(true);
                }
                if (role.getName().equals(HoldIssueConstants.EXPIRE_APPLIED_HOLD_ROLE_PERMISSION)) {
                    authorizedOrg.setAuthOrgExpire(true);
                }
            }

        }
        return new ArrayList<AuthorizedOrgWrapper>(authorizedOrgs.values());
    }

    public String getTermCodeForId(String termId) {
        if (termId == null) {
            return StringUtils.EMPTY;
        }

        try {
            TermInfo term = HoldResourceLoader.getAcademicCalendarService().getTerm(termId, ContextUtils.createDefaultContextInfo());
            return term.getCode();
        } catch (Exception e){
            convertServiceExceptionsToUI(e);
        }

        return StringUtils.EMPTY;
    }

    @Override
    public void saveDataObject() {
        HoldIssueMaintenanceWrapper holdWrapper = (HoldIssueMaintenanceWrapper) getDataObject();
        HoldIssueInfo holdIssueInfo = null;
        try {
            if (StringUtils.isBlank(holdWrapper.getHoldIssue().getId())) {
                holdIssueInfo = HoldResourceLoader.getHoldService().createHoldIssue(holdWrapper.getHoldIssue().getTypeKey(),
                        holdWrapper.getHoldIssue(), ContextUtils.createDefaultContextInfo());
            } else {
                holdIssueInfo = HoldResourceLoader.getHoldService().updateHoldIssue(holdWrapper.getHoldIssue().getId(),
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

            List<Role> roles = HoldResourceLoader.getHoldIssueAuthorizingOrgFacade().getHoldFunctions(ContextUtils.createDefaultContextInfo());
            for (Role role : roles) {
                if (role.getName().equals(HoldIssueConstants.APPLY_HOLD_ROLE_PERMISSION)) {
                    maintainAuthorizedOrgs(holdIssueInfo, applyOrgs, role);
                } else if (role.getName().equals(HoldIssueConstants.EXPIRE_APPLIED_HOLD_ROLE_PERMISSION)) {
                    maintainAuthorizedOrgs(holdIssueInfo, expireOrgs, role);
                }

            }
        } catch (Exception e) {
            convertServiceExceptionsToUI(e);
        }

    }

    private void maintainAuthorizedOrgs(HoldIssueInfo holdIssueInfo, List<String> applyOrgs, Role role)
            throws DoesNotExistException, MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {

        List<String> orgIds = HoldResourceLoader.getHoldIssueAuthorizingOrgFacade().getBindingByRoleAndHoldIssue(role.getId(), holdIssueInfo.getId(),
                ContextUtils.createDefaultContextInfo());

        for(String orgId : applyOrgs){
            if(!orgIds.contains(orgId)){
                HoldResourceLoader.getHoldIssueAuthorizingOrgFacade().storeBinding(holdIssueInfo.getId(), orgId,
                        role, ContextUtils.createDefaultContextInfo());
            }
            orgIds.remove(orgId);
        }

        for(String orgId : orgIds){
            HoldResourceLoader.getHoldIssueAuthorizingOrgFacade().removeBinding(holdIssueInfo.getId(), orgId,
                    role, ContextUtils.createDefaultContextInfo());
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        super.processAfterNew(document, requestParameters);
        document.getDocumentHeader().setDocumentDescription(HoldIssueConstants.NEW_HOLD_ISSUE_DOCUMENT_TEXT);
    }

    @Override
    public void processAfterEdit(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        super.processAfterEdit(document, requestParameters);
        document.getDocumentHeader().setDocumentDescription(HoldIssueConstants.MODIFY_HOLD_ISSUE_DOCUMENT_TEXT);
    }

}
