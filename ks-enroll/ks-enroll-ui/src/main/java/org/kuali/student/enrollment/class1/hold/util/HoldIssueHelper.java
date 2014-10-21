package org.kuali.student.enrollment.class1.hold.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class1.hold.dto.AuthorizedOrgWrapper;
import org.kuali.student.enrollment.class1.hold.dto.HoldIssueMaintenanceWrapper;
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

/**
 * Created by SW Genis on 2014-09-30.
 */
public class HoldIssueHelper {

    public static HoldIssueMaintenanceWrapper constructNewHoldIssueWrapper() {

        HoldIssueMaintenanceWrapper dataObject = new HoldIssueMaintenanceWrapper();
        HoldIssueInfo holdIssueInfo = new HoldIssueInfo();
        holdIssueInfo.setIsHoldIssueTermBased(false);
        holdIssueInfo.setMaintainHistoryOfApplicationOfHold(false);
        holdIssueInfo.setFirstAppliedDate(new Date());
        dataObject.setTermBased(false);
        dataObject.setHoldIssue(holdIssueInfo);

        return dataObject;
    }

    public static HoldIssueMaintenanceWrapper constructHoldIssueWrapper(String holdId)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {

        HoldIssueMaintenanceWrapper dataObject = new HoldIssueMaintenanceWrapper();
        HoldIssueInfo holdIssueInfo = HoldsResourceLoader.getHoldService().getHoldIssue(holdId, ContextUtils.createDefaultContextInfo());
        dataObject.setDescr(holdIssueInfo.getDescr() != null ? holdIssueInfo.getDescr().getPlain() : StringUtils.EMPTY);

        // Set term information.
        dataObject.setTermBased(holdIssueInfo.getIsHoldIssueTermBased());
        if (holdIssueInfo.getIsHoldIssueTermBased()) {
            dataObject.setFirstTerm(getTermCodeForId(holdIssueInfo.getFirstApplicationTermId()));
            dataObject.setLastTerm(getTermCodeForId(holdIssueInfo.getLastApplicationTermId()));
        }

        // Setup AdminOrg <OrgInfo> data
        if (holdIssueInfo.getOrganizationId() != null) {
            dataObject.setOrgName(HoldsResourceLoader.getOrganizationService().getOrg(holdIssueInfo.getOrganizationId(),
                    ContextUtils.createDefaultContextInfo()).getShortName());
        }
        dataObject.setHoldHistory(holdIssueInfo.getMaintainHistoryOfApplicationOfHold());
        dataObject.setHoldIssue(holdIssueInfo);

        dataObject.setAuthorizedOrgs(getAuthorizedOrgs(holdIssueInfo));

        return dataObject;
    }

    private static List<AuthorizedOrgWrapper> getAuthorizedOrgs(HoldIssueInfo holdIssueInfo)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {

        Map<String, AuthorizedOrgWrapper> authorizedOrgs = new HashMap<String, AuthorizedOrgWrapper>();
        List<Role> roles = HoldsResourceLoader.getHoldIssueAuthorizingOrgFacade().getHoldFunctions(ContextUtils.createDefaultContextInfo());
        for (Role role : roles) {

            List<String> orgIds = HoldsResourceLoader.getHoldIssueAuthorizingOrgFacade().getBindingByRoleAndHoldIssue(role.getId(),
                    holdIssueInfo.getId(), ContextUtils.createDefaultContextInfo());

            for (String orgId : orgIds) {
                AuthorizedOrgWrapper authorizedOrg = authorizedOrgs.get(orgId);
                if (authorizedOrg == null) {
                    authorizedOrg = new AuthorizedOrgWrapper();
                    authorizedOrg.setId(orgId);
                    authorizedOrg.setName(HoldsResourceLoader.getOrganizationService().getOrg(orgId,
                            ContextUtils.createDefaultContextInfo()).getShortName());
                    authorizedOrgs.put(orgId, authorizedOrg);
                }
                if (role.getName().equals(HoldsConstants.APPLY_HOLD_ROLE_PERMISSION)) {
                    authorizedOrg.setAuthOrgApply(true);
                }
                if (role.getName().equals(HoldsConstants.EXPIRE_APPLIED_HOLD_ROLE_PERMISSION)) {
                    authorizedOrg.setAuthOrgExpire(true);
                }
            }

        }
        return new ArrayList<AuthorizedOrgWrapper>(authorizedOrgs.values());
    }

    public static String getTermCodeForId(String termId)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {

        if (termId == null) {
            return StringUtils.EMPTY;
        }

        TermInfo term = HoldsResourceLoader.getAcademicCalendarService().getTerm(termId, ContextUtils.createDefaultContextInfo());
        return term.getCode();

    }

}
