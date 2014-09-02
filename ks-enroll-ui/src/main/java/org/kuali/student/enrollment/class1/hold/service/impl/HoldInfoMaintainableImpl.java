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
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;
import org.kuali.student.r2.core.organization.dto.OrgInfo;

import java.util.ArrayList;
import java.util.Date;
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
        List<AuthorizedOrgWrapper> AuthOrgs = new ArrayList<AuthorizedOrgWrapper>();
        try {
            HoldIssueInfo holdIssueInfo = HoldResourceLoader.getHoldService().getHoldIssue(holdId, ContextUtils.createDefaultContextInfo());
            dataObject.setDescr(holdIssueInfo.getDescr() != null ? holdIssueInfo.getDescr().getPlain() : StringUtils.EMPTY);

            // Set term information.
            dataObject.setTermBased(holdIssueInfo.getIsHoldIssueTermBased());
            if (holdIssueInfo.getIsHoldIssueTermBased()) {
                dataObject.setFirstTerm(getTermCodeForId(holdIssueInfo.getFirstApplicationTermId()));
                dataObject.setLastTerm(getTermCodeForId(holdIssueInfo.getLastApplicationTermId()));
            }
            //Setup AdminOrg <OrgInfo> data
            if(holdIssueInfo.getOrganizationId()!= null){
                dataObject.setOrgName(HoldResourceLoader.getOrganizationService().getOrg(holdIssueInfo.getOrganizationId(),ContextUtils.createDefaultContextInfo()).getShortName());
            }
            dataObject.setHoldHistory(holdIssueInfo.getMaintainHistoryOfApplicationOfHold());
            dataObject.setHoldIssue(holdIssueInfo);
            List<OrgInfo> orgInfos = new ArrayList<OrgInfo>();
            List<Role> roles = HoldResourceLoader.getHoldIssueAuthorizingOrgFacade().getHold(ContextUtils.createDefaultContextInfo());
            for (Role role : roles) {
                orgInfos = HoldResourceLoader.getHoldIssueAuthorizingOrgFacade().getBindingByRoleAndHoldIssue(role.getId(), holdIssueInfo.getId(), ContextUtils.createDefaultContextInfo());
                for (OrgInfo org : orgInfos) {
                    AuthorizedOrgWrapper authorizedOrgWrapper = new AuthorizedOrgWrapper();
                    authorizedOrgWrapper.setId(org.getId());
                    authorizedOrgWrapper.setName(org.getShortName());
                    if (role.getName().equals(HoldIssueConstants.APPLY_HOLD_ROLE_PERMISSION)) {
                        authorizedOrgWrapper.isAuthOrgApply();
                    }
                    if (role.getName().equals(HoldIssueConstants.EXPIRE_APPLIED_HOLD_ROLE_PERMISSION)) {
                        authorizedOrgWrapper.isAuthOrgExpire();
                    }
                    AuthOrgs.add(authorizedOrgWrapper);
                }

            }
            dataObject.setAuthorizedOrgs(AuthOrgs);


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
            return term.getCode();
        } catch (Exception e){
            convertServiceExceptionsToUI(e);
        }

        return StringUtils.EMPTY;
    }

    @Override
    public void saveDataObject() {
        HoldIssueMaintenanceWrapper holdWrapper = (HoldIssueMaintenanceWrapper) getDataObject();
        HoldIssueInfo holdIssueInfo = new HoldIssueInfo();
        try {
            if (StringUtils.isBlank(holdWrapper.getHoldIssue().getId())) {
                holdIssueInfo = HoldResourceLoader.getHoldService().createHoldIssue(holdWrapper.getHoldIssue().getTypeKey(), holdWrapper.getHoldIssue(), ContextUtils.createDefaultContextInfo());
            } else {
                holdIssueInfo = HoldResourceLoader.getHoldService().updateHoldIssue(holdWrapper.getHoldIssue().getId(), holdWrapper.getHoldIssue(), ContextUtils.createDefaultContextInfo());
            }
            List<Role> roles = HoldResourceLoader.getHoldIssueAuthorizingOrgFacade().getHold(ContextUtils.createDefaultContextInfo());

            if (!holdWrapper.getAuthorizedOrgs().isEmpty()) {
                for (AuthorizedOrgWrapper authorizedOrgWrapper : holdWrapper.getAuthorizedOrgs()) {
                    if (authorizedOrgWrapper.isAuthOrgApply()) {
                        for (Role role : roles) {
                            if (role.getName().equals(HoldIssueConstants.APPLY_HOLD_ROLE_PERMISSION)) {
                                HoldResourceLoader.getHoldIssueAuthorizingOrgFacade().storeBinding(holdIssueInfo.getId(), authorizedOrgWrapper.getId(), role, ContextUtils.createDefaultContextInfo());
                            }
                        }
                    }

                    if (authorizedOrgWrapper.isAuthOrgExpire()) {
                        for (Role role : roles) {
                            if (role.getName().equals(HoldIssueConstants.EXPIRE_APPLIED_HOLD_ROLE_PERMISSION)) {
                                HoldResourceLoader.getHoldIssueAuthorizingOrgFacade().storeBinding(holdIssueInfo.getId(), authorizedOrgWrapper.getId(), role, ContextUtils.createDefaultContextInfo());

                            }
                        }
                    }
                }
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
        document.getDocumentHeader().setDocumentDescription(HoldIssueConstants.NEW_HOLD_ISSUE_DOCUMENT_TEXT);
    }

    @Override
    public void processAfterEdit(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        super.processAfterEdit(document, requestParameters);
        document.getDocumentHeader().setDocumentDescription(HoldIssueConstants.MODIFY_HOLD_ISSUE_DOCUMENT_TEXT);
    }

}
