package org.kuali.student.enrollment.class1.hold.service.facade;

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.membership.MemberType;
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
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
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
 * Implementation of HoldIssueAuthorizingOrgFacade to create bindings between hold functions and organizations.
 *
 * This needs to be configured ahead of time:
 * A new namespace for holds KS-HLD
 * A role for each function to be managed, i.e. "Apply Holds To Students", "Expire Holds on Students"
 *
 * The org id is persisted as an attribute on a kim group with type "KS Hold Org Authorization Group Type" and
 * name "Functionaries for Orgname".
 *
 * This group is then added as a role member to a role for the specific function i.e. "Apply Holds to Students"
 * with the associated hold issue id as an attribute on the role membership.
 *
 * @author Kuali Student Blue Team
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

        // Retrieve group and role membership
        Group group = findCreateGroupMatchingOrg(orgId, contextInfo);
        RoleMembership roleMembership = findCreateRoleMembershipForGroupAndRole(group.getId(), role, contextInfo);

        // Check if hold issue is not yet in the list.
        if (!containsHoldIssueId(roleMembership.getQualifier(), holdIssueId)) {
            // Add hold issue id to attribute/qualifier.
            addHoldIssueIdToQualifier(roleMembership.getQualifier(), holdIssueId);
            // Persist the update role membership.
            updateRoleMembership(roleMembership, contextInfo);
        }
    }

    /**
     * Add the given hold issue id the list of hold issue id on the qualifier map.
     *
     * @param qualifier
     * @param holdIssueId
     */
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

        // Retrieve group and role membership
        Group group = findCreateGroupMatchingOrg(orgId, contextInfo);
        RoleMembership roleMembership = findRoleMembershipForGroupAndRole(group.getId(), role.getId(), contextInfo);

        // Check if hold issue id is in the list.
        if (containsHoldIssueId(roleMembership.getQualifier(), holdIssueId)) {
            // Remove id from list.
            removeHoldIssueIdFromQualifier(roleMembership.getQualifier(), holdIssueId);
            // Persist the updated list.
            updateRoleMembership(roleMembership, contextInfo);
        }
    }

    /**
     * Check if the given hold issue id is already on the list of hold issue ids on the qualifier map.
     *
     * @param qualifiers
     * @param holdIssueId
     * @return
     */
    protected boolean containsHoldIssueId(Map<String, String> qualifiers, String holdIssueId) {
        String issueIds = qualifiers.get(HOLD_AUTHORIZATION_ISSUEIDS);
        if (issueIds == null) {
            return false;
        }
        return issueIds.contains(holdIssueId);
    }

    /**
     * Removes the given hold issue id from the hold issue id list on the qualifier map.
     *
     * @param qualifiers
     * @param holdIssueId
     * @return
     */
    protected void removeHoldIssueIdFromQualifier(Map<String, String> qualifier, String holdIssueId) {

        String issueIds = qualifier.get(HOLD_AUTHORIZATION_ISSUEIDS);
        Set<String> issueIdSet = StringUtils.commaDelimitedListToSet(issueIds);
        issueIdSet.remove(holdIssueId);

        qualifier.put(HOLD_AUTHORIZATION_ISSUEIDS, StringUtils.collectionToCommaDelimitedString(issueIdSet));
    }

    /**
     * Update the role membership with the new qualifier map.
     *
     * @param roleMembership
     * @param contextInfo
     */
    protected void updateRoleMembership(RoleMembership roleMembership, ContextInfo contextInfo) {
        // have to convert because rice has 2 objects for role membership!!!!
        RoleMember.Builder roleMemberBldr = RoleMember.Builder.create(roleMembership.getRoleId(), roleMembership.getId(), roleMembership.getMemberId(),
                roleMembership.getType(), null, null, roleMembership.getQualifier(), roleMembership.getType().name(), PermissionServiceConstants.KS_HLD_NAMESPACE);

        roleMemberBldr.setId(roleMembership.getId());
        // Rice is messed up in roleMember the qualifiers are called attributes!
        roleMemberBldr.setAttributes(roleMembership.getQualifier());
        this.getRoleService().updateRoleMember(roleMemberBldr.build());
    }

    /**
     * Retrieve the Role membership for the given group id and role. If the role membership does not exist, create
     * a new one for the given group id.
     *
     * @param groupId
     * @param role
     * @param contextInfo
     * @return
     * @throws OperationFailedException
     */
    protected RoleMembership findCreateRoleMembershipForGroupAndRole(String groupId, Role role, ContextInfo contextInfo)
            throws OperationFailedException {

        RoleMembership roleMembership = findRoleMembershipForGroupAndRole(groupId, role.getId(), contextInfo);
        if (roleMembership == null) {
            roleMembership = createRoleMembershipForGroupAndRole(groupId, role, contextInfo);
        }
        return roleMembership;
    }

    /**
     * Search for existing rolemembership relationshiop for given group and role.
     *
     * @param groupId
     * @param roleId
     * @param contextInfo
     * @return
     * @throws OperationFailedException
     */
    protected RoleMembership findRoleMembershipForGroupAndRole(String groupId, String roleId, ContextInfo contextInfo)
            throws OperationFailedException {

        QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(PredicateFactory.and(
                PredicateFactory.in("roleId", roleId), PredicateFactory.in("typeCode", MemberType.GROUP.getCode()),
                PredicateFactory.in("memberId", groupId)));
        List<RoleMembership> roleMemberships = KimApiServiceLocator.getRoleService().findRoleMemberships(query).getResults();
        return KSCollectionUtils.getOptionalZeroElement(roleMemberships);
    }

    /**
     * Create a new role membership for the group and role.
     *
     * @param groupId
     * @param role
     * @param contextInfo
     * @return
     * @throws OperationFailedException
     */
    protected RoleMembership createRoleMembershipForGroupAndRole(String groupId, Role role, ContextInfo contextInfo)
            throws OperationFailedException {

        Map<String, String> qualifications = new HashMap<String, String>();
        this.getRoleService().assignGroupToRole(groupId, PermissionServiceConstants.KS_HLD_NAMESPACE,
                role.getName(), qualifications);
        return findRoleMembershipForGroupAndRole(groupId, role.getId(), contextInfo);
    }

    /**
     * Retrieve group for given org id. If the org does noet have an existing group, create a new one.
     *
     * @param orgId
     * @param contextInfo
     * @return
     * @throws MissingParameterException
     * @throws PermissionDeniedException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    protected Group findCreateGroupMatchingOrg(String orgId, ContextInfo contextInfo)
            throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException {

        KimType holdAuthType = KimApiServiceLocator.getKimTypeInfoService().findKimTypeByNameAndNamespace(PermissionServiceConstants.KS_HLD_NAMESPACE, HOLD_AUTHORIZATION_GROUP_TYPE_NAME);
        Group group = findGroupMatchingOrg(orgId, holdAuthType.getId(), contextInfo);
        if (group == null) {
            group = createGroupMatchingOrg(orgId, holdAuthType.getId(), contextInfo);
        }
        return group;
    }

    /**
     * Search for groups matching the given org id and type.
     *
     * @param orgId
     * @param typeId
     * @param contextInfo
     * @return
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    protected Group findGroupMatchingOrg(String orgId, String typeId, ContextInfo contextInfo)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException,
            DoesNotExistException {

        QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(PredicateFactory.and(
                PredicateFactory.in("namespaceCode", PermissionServiceConstants.KS_HLD_NAMESPACE), PredicateFactory.equal("kimTypeId", typeId),
                PredicateFactory.like("attributeDetails.attributeValue", "%"+orgId+"%")));
        List<Group> groups = this.getGroupService().findGroups(query).getResults();
        return KSCollectionUtils.getOptionalZeroElement(groups);
    }

    /**
     * Create a new group for the given org id and type. Use the shortname of the org to create a default group name. The
     * org id must be added an attribute on the group.
     *
     * @param orgId
     * @param typeId
     * @param contextInfo
     * @return
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
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

        // Search for rolememberships for given role with hold issue id as attribute.
        QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(PredicateFactory.and(
                PredicateFactory.equal("roleId", roleId), PredicateFactory.equal("typeCode", MemberType.GROUP.getCode()),
        PredicateFactory.like("attributeDetails.attributeValue", "%"+holdIssueId+"%")));
        List<RoleMembership> roleMemberships = KimApiServiceLocator.getRoleService().findRoleMemberships(query).getResults();

        // Retrieve the group ids from the relationshiops.
        List<String> groupIds = new ArrayList<>();
        for (RoleMembership roleMembership : roleMemberships) {
            groupIds.add(roleMembership.getMemberId());
        }

        // Retrieve the org ids from the group attributes.
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
