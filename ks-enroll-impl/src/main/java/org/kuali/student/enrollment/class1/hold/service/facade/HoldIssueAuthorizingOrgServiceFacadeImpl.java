package org.kuali.student.enrollment.class1.hold.service.facade;

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.membership.MemberType;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.group.Group;
import org.kuali.rice.kim.api.group.GroupService;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.api.role.RoleMembership;
import org.kuali.rice.kim.api.role.RoleService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.api.type.KimType;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.kuali.student.kim.permission.map.KimPermissionConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;
import org.kuali.student.r2.core.hold.service.HoldService;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the Application Service Layer to provide the functionally specified functionality
 * using several service calls.
 *
 * @author Kuali Student Team
 */
public class HoldIssueAuthorizingOrgServiceFacadeImpl implements HoldIssueAuthorizingOrgFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(HoldIssueAuthorizingOrgServiceFacadeImpl.class);
    private RoleService roleService;
    private PermissionService permissionService;
    private HoldService holdService;
    private OrganizationService organizationService;
    private GroupService groupService;

    public static final String APPLY_HOLD_ROLE_PERMISSION = "Hold Apply Hold Role";
    public static final String EXPIRE_APPLIED_HOLD_ROLE_PERMISSION = "Hold Expire Applied Hold Role";

    public static final String HOLD_AUTHORIZATION_ORGID = "org.kuali.student.hold.authorization.orgId";
    public static final String HOLD_AUTHORIZATION_ISSUEIDS = "org.kuali.student.hold.authorization.holdIssueIds";

    @Override
    public List<Role> getHold(ContextInfo contextInfo) {
        QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(PredicateFactory.and(
                PredicateFactory.in("namespaceCode", PermissionServiceConstants.KS_HLD_NAMESPACE)));
        List<Role> roles = getRoleService().findRoles(query).getResults();
        return roles;
    }

    @Override
    public List<OrgInfo> getPossibleHoldsOrgs(ContextInfo contextInfo) {
        // return organizationService.getOrgsByType(OrganizationServiceConstants.ORGANIZATION_SUB_DEPARTMENT_TYPE_KEY);
        // this needs to be configurable so that implementing schools can specify their own types
        return null;
    }

    @Override
    public void storeBinding(String holdIssueId, String orgId, Role role, ContextInfo contextInfo)
            throws DoesNotExistException, MissingParameterException, InvalidParameterException, OperationFailedException,
            PermissionDeniedException {

        Group group = findCreateGroupMatchingOrg(orgId, contextInfo);
        RoleMembership roleMembership = findCreateRoleMembershipForGroupAndRole(group.getId(), role, contextInfo);
        if (!containsHoldIssueId(roleMembership.getQualifier(), holdIssueId)) {
            addHoldIssueToQualifier(roleMembership, holdIssueId, contextInfo);
            updateRoleMembership(roleMembership, contextInfo);
        }
    }

    protected boolean containsHoldIssueId(Map<String, String> qualifiers, String holdIssueId) {
        String issueIds = qualifiers.get(HOLD_AUTHORIZATION_ISSUEIDS);
        if (issueIds == null) {
            return false;
        }
        return issueIds.contains(holdIssueId);
    }

    protected RoleMembership addHoldIssueIdToQualifier(RoleMembership roleMembership, String holdIssueId) {
        if (containsHoldIssueId(roleMembership.getQualifier(), holdIssueId)) {
            return roleMembership;
        }

        String issueIds = roleMembership.getQualifier().get(HOLD_AUTHORIZATION_ISSUEIDS);
        if (issueIds == null) {
            roleMembership.getQualifier().put(HOLD_AUTHORIZATION_ISSUEIDS, holdIssueId);
            return roleMembership;
        }
        issueIds = issueIds + "," + holdIssueId;
        roleMembership.getQualifier().put(HOLD_AUTHORIZATION_ISSUEIDS, issueIds);
        return roleMembership;
    }

    protected void addHoldIssueToQualifier(RoleMembership roleMembership, String holdIssueId, ContextInfo contextInfo) {
        addHoldIssueIdToQualifier(roleMembership, holdIssueId);
    }

    protected void updateRoleMembership(RoleMembership roleMembership, ContextInfo contextInfo) {
        // have to convert because rice has 2 objects for role membership!!!!
        RoleMember roleMember = convertRoleMembership2RoleMember(roleMembership, contextInfo);
        this.getRoleService().updateRoleMember(roleMember);
    }

    protected RoleMember convertRoleMembership2RoleMember(RoleMembership roleMembership, ContextInfo contextInfo) {

        RoleMember.Builder roleMemberBldr = RoleMember.Builder.create(roleMembership.getRoleId(), roleMembership.getId(), roleMembership.getMemberId(),
                roleMembership.getType(), null, null, roleMembership.getQualifier(), roleMembership.getType().name(), PermissionServiceConstants.KS_HLD_NAMESPACE);

        roleMemberBldr.setId(roleMembership.getId());
        // Rice is messed up in roleMember the qualifiers are called attributes!
        roleMemberBldr.setAttributes(roleMembership.getQualifier());
        return roleMemberBldr.build();
    }

    protected RoleMembership findCreateRoleMembershipForGroupAndRole(String groupId, Role role, ContextInfo contextInfo)
            throws OperationFailedException {

        RoleMembership roleMembership = findRoleMembershipForGroupAndRole(groupId, role, contextInfo);
        if (roleMembership == null) {
            roleMembership = createRoleMembershipForGroupAndRole(groupId, role, contextInfo);
        }
        return roleMembership;
    }

    protected RoleMembership findRoleMembershipForGroupAndRole(String groupId, Role role, ContextInfo contextInfo)
            throws OperationFailedException {

        QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(PredicateFactory.and(
                PredicateFactory.in("roleId", role.getId()), PredicateFactory.in("typeCode", MemberType.GROUP.getCode()),
                PredicateFactory.in("memberId", groupId)));
        List<RoleMembership> roleMemberships = KimApiServiceLocator.getRoleService().findRoleMemberships(query).getResults();
        return KSCollectionUtils.getOptionalZeroElement(roleMemberships);
    }

    protected RoleMembership createRoleMembershipForGroupAndRole(String groupId, Role role, ContextInfo contextInfo)
            throws OperationFailedException {

        Map<String, String> qualifications = new HashMap<String, String>();
        RoleMember roleMember = this.getRoleService().assignGroupToRole(groupId, PermissionServiceConstants.KS_HLD_NAMESPACE,
                role.getName(), qualifications);
        return findRoleMembershipForGroupAndRole(groupId, role, contextInfo);
    }

    protected Group findCreateGroupMatchingOrg(String orgId, ContextInfo contextInfo)
            throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException {

        Group group = findGroupMatchingOrg(orgId, contextInfo);
        if (group == null) {
            group = createGroupMatchingOrg(orgId, contextInfo);
        }
        return group;
    }

    protected Group findGroupMatchingOrg(String orgId, ContextInfo contextInfo)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException,
            DoesNotExistException {

        OrgInfo org = this.getOrganizationService().getOrg(orgId, contextInfo);
        QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(PredicateFactory.and(
                PredicateFactory.in("namespaceCode", PermissionServiceConstants.KS_HLD_NAMESPACE), PredicateFactory.in("name", "Hold Functionaries for " + org.getShortName())));
        List<Group> groups = this.getGroupService().findGroups(query).getResults();
        return KSCollectionUtils.getOptionalZeroElement(groups);
    }

    protected Group createGroupMatchingOrg(String orgId, ContextInfo contextInfo)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException,
            DoesNotExistException {

        OrgInfo org = this.getOrganizationService().getOrg(orgId, contextInfo);
        KimType holdAuthType = KimApiServiceLocator.getKimTypeInfoService().findKimTypeByNameAndNamespace(PermissionServiceConstants.KS_HLD_NAMESPACE, "KS Hold Authorization Role Type");
        Group.Builder builder = Group.Builder.create(PermissionServiceConstants.KS_HLD_NAMESPACE, "Hold Functionaries for " + org.getShortName(),
                holdAuthType.getId());
        builder.setNamespaceCode(PermissionServiceConstants.KS_HLD_NAMESPACE);
        builder.setName("Hold Functionaries for " + org.getShortName());
        builder.setActive(true);
        Group group = this.getGroupService().createGroup(builder.build());

        builder = Group.Builder.create(group);
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put(HOLD_AUTHORIZATION_ORGID, org.getId());
        builder.setAttributes(attributes);
        return this.getGroupService().updateGroup(builder.build());
    }

    @Override
    public List<OrgInfo> getBindingByRoleAndHoldIssue(String roleId, String holdIssueId, ContextInfo contextInfo)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {

        QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(PredicateFactory.and(
                PredicateFactory.in("roleId", roleId), PredicateFactory.in("typeCode", MemberType.GROUP.getCode())));
        List<RoleMembership> roleMemberships = KimApiServiceLocator.getRoleService().findRoleMemberships(query).getResults();

        List<String> groupIds = new ArrayList<>();
        for(RoleMembership roleMembership : roleMemberships){
            if(roleMembership.getQualifier().containsKey(HOLD_AUTHORIZATION_ISSUEIDS)){
                String holdIssueIds = roleMembership.getQualifier().get(HOLD_AUTHORIZATION_ISSUEIDS);
                if(holdIssueIds.contains(holdIssueId)){
                    groupIds.add(roleMembership.getMemberId());
                }
            }
        }

        List<OrgInfo> orgs = new ArrayList<>();
        for(String groupId : groupIds){
            Map<String, String> attributes = this.getGroupService().getAttributes(groupId);
            if(attributes.containsKey(HOLD_AUTHORIZATION_ORGID)) {
                orgs.add(this.getOrganizationService().getOrg(attributes.get(HOLD_AUTHORIZATION_ORGID), contextInfo));
            }
        }

        return orgs;
    }

    public PermissionService getPermissionService() {
        if (permissionService == null) {
            permissionService = KimApiServiceLocator.getPermissionService();
        }
        return permissionService;
    }

    public RoleService getRoleService() {
        if (roleService == null) {
            roleService = KimApiServiceLocator.getRoleService();
        }
        return roleService;
    }


    public GroupService getGroupService() {
        if (groupService == null) {
            groupService = KimApiServiceLocator.getGroupService();
        }
        return groupService;
    }

    public HoldService getHoldService() {
        return holdService;
    }

    public void setHoldService(HoldService holdService) {
        this.holdService = holdService;
    }

    public OrganizationService getOrganizationService() {
        return organizationService;
    }

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }
}
