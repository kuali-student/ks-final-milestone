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
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private OrganizationService organizationService;
    private GroupService groupService;

    public static final String HOLD_AUTHORIZATION_ORGID = "org.kuali.student.hold.authorization.orgId";
    public static final String HOLD_AUTHORIZATION_ISSUEIDS = "org.kuali.student.hold.authorization.holdIssueIds";

    public static final String HOLD_AUTHORIZATION_GROUP_TYPE_NAME = "KS Hold Org Authorization Group Type";
    public static final String HOLD_AUTHORIZATION_ROLE_TYPE_NAME = "KS Hold Issue Authorization Role Type";

    @Override
    public List<Role> getHoldFunctions(ContextInfo contextInfo) {
        QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(PredicateFactory.and(
                PredicateFactory.in("namespaceCode", PermissionServiceConstants.KS_HLD_NAMESPACE)));
        return getRoleService().findRoles(query).getResults();
    }

    @Override
    public void storeBinding(String holdIssueId, String orgId, Role role, ContextInfo contextInfo)
            throws DoesNotExistException, MissingParameterException, InvalidParameterException, OperationFailedException,
            PermissionDeniedException {

        Group group = findCreateGroupMatchingOrg(orgId, contextInfo);
        RoleMembership roleMembership = findCreateRoleMembershipForGroupAndRole(group.getId(), role, contextInfo);
        if (!containsHoldIssueId(roleMembership.getQualifier(), holdIssueId)) {
            addHoldIssueIdToQualifier(roleMembership.getQualifier(), holdIssueId);
            updateRoleMembership(roleMembership, contextInfo);
        }
    }

    protected void addHoldIssueIdToQualifier(Map<String, String> qualifier, String holdIssueId) {

        String issueIds = qualifier.get(HOLD_AUTHORIZATION_ISSUEIDS);
        if (issueIds == null) {
            qualifier.put(HOLD_AUTHORIZATION_ISSUEIDS, holdIssueId);
        } else {
            issueIds = issueIds + "," + holdIssueId;
            qualifier.put(HOLD_AUTHORIZATION_ISSUEIDS, issueIds);
        }
    }

    @Override
    public void removeBinding(String holdIssueId, String orgId, Role role, ContextInfo contextInfo)
            throws DoesNotExistException, MissingParameterException, InvalidParameterException, OperationFailedException,
            PermissionDeniedException {

        Group group = findCreateGroupMatchingOrg(orgId, contextInfo);
        RoleMembership roleMembership = findRoleMembershipForGroupAndRole(group.getId(), role.getId(), contextInfo);
        if (containsHoldIssueId(roleMembership.getQualifier(), holdIssueId)) {
            removeHoldIssueIdFromQualifier(roleMembership.getQualifier(), holdIssueId);
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

    protected void removeHoldIssueIdFromQualifier(Map<String, String> qualifier, String holdIssueId) {

        String issueIds = qualifier.get(HOLD_AUTHORIZATION_ISSUEIDS);
        Set<String> issueIdSet = StringUtils.commaDelimitedListToSet(issueIds);
        issueIdSet.remove(holdIssueId);

        qualifier.put(HOLD_AUTHORIZATION_ISSUEIDS, StringUtils.collectionToCommaDelimitedString(issueIdSet));
    }

    protected void updateRoleMembership(RoleMembership roleMembership, ContextInfo contextInfo) {
        // have to convert because rice has 2 objects for role membership!!!!
        RoleMember.Builder roleMemberBldr = RoleMember.Builder.create(roleMembership.getRoleId(), roleMembership.getId(), roleMembership.getMemberId(),
                roleMembership.getType(), null, null, roleMembership.getQualifier(), roleMembership.getType().name(), PermissionServiceConstants.KS_HLD_NAMESPACE);

        roleMemberBldr.setId(roleMembership.getId());
        // Rice is messed up in roleMember the qualifiers are called attributes!
        roleMemberBldr.setAttributes(roleMembership.getQualifier());
        this.getRoleService().updateRoleMember(roleMemberBldr.build());
    }

    protected RoleMembership findCreateRoleMembershipForGroupAndRole(String groupId, Role role, ContextInfo contextInfo)
            throws OperationFailedException {

        RoleMembership roleMembership = findRoleMembershipForGroupAndRole(groupId, role.getId(), contextInfo);
        if (roleMembership == null) {
            roleMembership = createRoleMembershipForGroupAndRole(groupId, role, contextInfo);
        }
        return roleMembership;
    }

    protected RoleMembership findRoleMembershipForGroupAndRole(String groupId, String roleId, ContextInfo contextInfo)
            throws OperationFailedException {

        QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(PredicateFactory.and(
                PredicateFactory.in("roleId", roleId), PredicateFactory.in("typeCode", MemberType.GROUP.getCode()),
                PredicateFactory.in("memberId", groupId)));
        List<RoleMembership> roleMemberships = KimApiServiceLocator.getRoleService().findRoleMemberships(query).getResults();
        return KSCollectionUtils.getOptionalZeroElement(roleMemberships);
    }

    protected RoleMembership createRoleMembershipForGroupAndRole(String groupId, Role role, ContextInfo contextInfo)
            throws OperationFailedException {

        Map<String, String> qualifications = new HashMap<String, String>();
        this.getRoleService().assignGroupToRole(groupId, PermissionServiceConstants.KS_HLD_NAMESPACE,
                role.getName(), qualifications);
        return findRoleMembershipForGroupAndRole(groupId, role.getId(), contextInfo);
    }

    protected Group findCreateGroupMatchingOrg(String orgId, ContextInfo contextInfo)
            throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException {

        KimType holdAuthType = KimApiServiceLocator.getKimTypeInfoService().findKimTypeByNameAndNamespace(PermissionServiceConstants.KS_HLD_NAMESPACE, HOLD_AUTHORIZATION_GROUP_TYPE_NAME);
        Group group = findGroupMatchingOrg(orgId, holdAuthType.getId(), contextInfo);
        if (group == null) {
            group = createGroupMatchingOrg(orgId, holdAuthType.getId(), contextInfo);
        }
        return group;
    }

    protected Group findGroupMatchingOrg(String orgId, String typeId, ContextInfo contextInfo)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException,
            DoesNotExistException {

        QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(PredicateFactory.and(
                PredicateFactory.in("namespaceCode", PermissionServiceConstants.KS_HLD_NAMESPACE), PredicateFactory.equal("kimTypeId", typeId),
                PredicateFactory.like("attributeDetails.attributeValue", "%"+orgId+"%")));
        List<Group> groups = this.getGroupService().findGroups(query).getResults();
        return KSCollectionUtils.getOptionalZeroElement(groups);
    }

    protected Group createGroupMatchingOrg(String orgId, String typeId, ContextInfo contextInfo)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException,
            DoesNotExistException {

        OrgInfo org = this.getOrganizationService().getOrg(orgId, contextInfo);
        Group.Builder builder = Group.Builder.create(PermissionServiceConstants.KS_HLD_NAMESPACE, "Hold Functionaries for " + org.getShortName(),
                typeId);
        builder.setActive(true);

        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put(HOLD_AUTHORIZATION_ORGID, org.getId());
        builder.setAttributes(attributes);
        return this.getGroupService().createGroup(builder.build());
    }

    @Override
    public List<String> getBindingByRoleAndHoldIssue(String roleId, String holdIssueId, ContextInfo contextInfo)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {

        QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(PredicateFactory.and(
                PredicateFactory.equal("roleId", roleId), PredicateFactory.equal("typeCode", MemberType.GROUP.getCode()),
        PredicateFactory.like("attributeDetails.attributeValue", "%"+holdIssueId+"%")));
        List<RoleMembership> roleMemberships = KimApiServiceLocator.getRoleService().findRoleMemberships(query).getResults();

        List<String> groupIds = new ArrayList<>();
        for (RoleMembership roleMembership : roleMemberships) {
            groupIds.add(roleMembership.getMemberId());
        }

        List<String> orgIds = new ArrayList<>();
        for (String groupId : groupIds) {
            Map<String, String> attributes = this.getGroupService().getAttributes(groupId);
            if (attributes.containsKey(HOLD_AUTHORIZATION_ORGID)) {
                orgIds.add(attributes.get(HOLD_AUTHORIZATION_ORGID));
            }
        }

        return orgIds;
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

    public OrganizationService getOrganizationService() {
        return organizationService;
    }

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }
}
