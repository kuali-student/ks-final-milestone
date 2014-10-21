/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.kim.permission.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;


import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.group.Group;
import org.kuali.rice.kim.api.group.GroupMember;
import org.kuali.rice.kim.api.group.GroupMemberQueryResults;
import org.kuali.rice.kim.api.group.GroupQueryResults;
import org.kuali.rice.kim.api.group.GroupService;
import org.kuali.student.common.mock.MockService;


/**
 * @author nwright
 */
public class GroupServiceMapImpl implements GroupService, MockService {

    private transient Map<String, Group> groupCache = new HashMap<String, Group>();
    private transient Map<String, GroupMember> groupMembershipCache = new HashMap<String, GroupMember>();

    
    @Override
	public void clear() {
    	this.groupCache.clear();
    	this.groupMembershipCache.clear();
    	
	}

	@Override
    public List<String> getDirectGroupIdsByPrincipalId(String principalId) {
        List<String> groups = new ArrayList<String>();
        for (GroupMember groupMembership : this.groupMembershipCache.values()) {
            if (groupMembership.getType().getCode().equals(KimConstants.KimGroupMemberTypes.PRINCIPAL_MEMBER_TYPE)) {
                if (groupMembership.getMemberId().equals(principalId)) {
                    groups.add(groupMembership.getGroupId());
                }
            }
        }
        return groups;
    }

    @Override
    public List<String> getDirectMemberGroupIds(String groupId) {
        List<String> members = new ArrayList<String>();
        for (GroupMember groupMembership : this.groupMembershipCache.values()) {
            if (groupMembership.getType().getCode().equals(KimConstants.KimGroupMemberTypes.GROUP_MEMBER_TYPE)) {
                if (groupMembership.getGroupId().equals(groupId)) {
                    members.add(groupMembership.getMemberId());
                }
            }
        }
        return members;
    }

    @Override
    public List<String> getDirectMemberPrincipalIds(String groupId) {
        List<String> members = new ArrayList<String>();
        for (GroupMember groupMembership : this.groupMembershipCache.values()) {
            if (groupMembership.getType().getCode().equals(KimConstants.KimGroupMemberTypes.PRINCIPAL_MEMBER_TYPE)) {
                if (groupMembership.getGroupId().equals(groupId)) {
                    members.add(groupMembership.getMemberId());
                }
            }
        }
        return members;
    }

    @Override
    public List<String> getDirectParentGroupIds(String groupId) {
        List<String> members = new ArrayList<String>();
        for (GroupMember groupMembership : this.groupMembershipCache.values()) {
            if (groupMembership.getType().getCode().equals(KimConstants.KimGroupMemberTypes.PRINCIPAL_MEMBER_TYPE)) {
                if (groupMembership.getMemberId().equals(groupId)) {
                    members.add(groupMembership.getGroupId());
                }
            }
        }
        return members;
    }


    @Override
    public GroupMemberQueryResults findGroupMembers(QueryByCriteria queryByCriteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<String, String> getAttributes(String groupId) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
     

    @Override
    public List<Group> getGroups(Collection<String> groupIds) {
        List<Group> groups = new ArrayList<Group>();
        for (String groupId : groupIds) {
            Group info = this.getGroup(groupId);
            groups.add(info);
        }
        return groups;
    }

    
    
    @Override
    public List<String> getGroupIdsByPrincipalId(String principalId) {
        List<String> allGroups = new ArrayList<String>();
        List<String> directGroups = getDirectGroupIdsByPrincipalId(principalId);
        allGroups.addAll(directGroups);
        for (String groupId : directGroups) {
            List<String> ancestors = this.getParentGroupIds(groupId);
            allGroups.addAll(ancestors);
        }
        return allGroups;
    }

    @Override
    public List<String> getGroupIdsByPrincipalIdAndNamespaceCode(String principalId,
            String namespaceCode) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Group getGroup(String groupId) {
        return this.groupCache.get(groupId);
    }

    @Override
    public Group getGroupByNamespaceCodeAndName(String namespaceCode, String groupName) {
        for (Group group : this.groupCache.values()) {
            if (namespaceCode.equals(group.getNamespaceCode())) {
                if (groupName.equals(group.getName())) {
                    return group;
                }
            }
        }
        return null;
    }

  

    @Override
    public List<GroupMember> getMembers(List<String> groupIds) {
        List<GroupMember> infos = new ArrayList<GroupMember>();
        for (GroupMember info : this.groupMembershipCache.values()) {
            if (groupIds.contains(info.getGroupId())) {
                infos.add(info);
            }
        }
        return infos;
    }

    @Override
    public List<GroupMember> getMembersOfGroup(String groupId) {
        List<GroupMember> infos = new ArrayList<GroupMember>();
        for (GroupMember info : this.groupMembershipCache.values()) {
            if (groupId.equals(info.getGroupId())) {
                infos.add(info);
            }
        }
        return infos;
    }

    @Override
    public List<GroupMember> getMembersOfGroupWithDate(String groupId, DateTime asOfDate) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Group> getGroupsByPrincipalId(String principalId) throws RiceIllegalArgumentException {
        Collection<String> groupIds = this.getGroupIdsByPrincipalId(principalId);
        List<Group> groups = new ArrayList<Group>();
        for (String groupId : groupIds) {
            Group group = this.getGroup(groupId);
            groups.add(group);
        }
        return groups;
    }

    @Override
    public List<Group> getGroupsByPrincipalIdAndNamespaceCode(String principalId,
            String namespaceCode) {
        List<Group> groups = new ArrayList<Group>();
        for (Group group : this.getGroupsByPrincipalId(principalId)) {
            if (namespaceCode.equals(group.getNamespaceCode())) {
                groups.add(group);
            }
        }
        return groups;
    }

    @Override
    public List<String> getMemberGroupIds(String groupId) {
        List<String> groupIds = new ArrayList<String>();
        for (GroupMember info : this.groupMembershipCache.values()) {
            if (groupId.equals(info.getGroupId())) {
                if (info.getType().getCode().equals(KimConstants.KimGroupMemberTypes.GROUP_MEMBER_TYPE)) {
                    groupIds.add(info.getMemberId());
                    groupIds.addAll(this.getMemberGroupIds(info.getMemberId()));
                }

            }
        }
        return groupIds;
    }

    @Override
    public List<String> getMemberPrincipalIds(String groupId) {
        List<String> principalIds = new ArrayList<String>();
        for (GroupMember info : this.groupMembershipCache.values()) {
            if (groupId.equals(info.getGroupId())) {
                if (info.getType().getCode().equals(KimConstants.KimGroupMemberTypes.GROUP_MEMBER_TYPE)) {
                    principalIds.addAll(this.getMemberPrincipalIds(info.getMemberId()));
                } else if (info.getType().getCode().equals(KimConstants.KimGroupMemberTypes.PRINCIPAL_MEMBER_TYPE)) {
                    principalIds.add(info.getMemberId());
                }
            }
        }
        return principalIds;
    }

    @Override
    public List<String> getParentGroupIds(String groupId) {
        List<String> groupIds = new ArrayList<String>();
        for (GroupMember info : this.groupMembershipCache.values()) {
            if (info.getType().getCode().equals(KimConstants.KimGroupMemberTypes.GROUP_MEMBER_TYPE)) {
                if (groupId.equals(info.getMemberId())) {
                    groupIds.add(info.getGroupId());
                    groupIds.addAll(this.getParentGroupIds(info.getGroupId()));
                }
            }
        }
        return groupIds;
    }

    @Override
    public boolean isDirectMemberOfGroup(String principalId, String groupId) {
        for (GroupMember info : this.groupMembershipCache.values()) {
            if (groupId.equals(info.getGroupId())) {
                if (info.getType().getCode().equals(KimConstants.KimGroupMemberTypes.PRINCIPAL_MEMBER_TYPE)) {
                    if (principalId.equals(info.getMemberId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // no longer provided in api
    public boolean isGroupActive(String groupId) {
        return this.getGroup(groupId).isActive();
    }

    @Override
    public boolean isGroupMemberOfGroup(String groupMemberId, String groupId) {
        for (String directChildGroupId : this.getDirectMemberGroupIds(groupId)) {
            if (directChildGroupId.equals(groupMemberId)) {
                return true;
            }
            if (this.isGroupMemberOfGroup(groupMemberId, directChildGroupId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isMemberOfGroup(String principalId, String groupId) {
        return this.getMemberPrincipalIds(groupId).contains(principalId);
    }

    @Override
    public List<String> findGroupIds(QueryByCriteria queryByCriteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GroupQueryResults findGroups(QueryByCriteria queryByCriteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    

    @Override
    public boolean addGroupToGroup(String childId, String parentId)
            throws UnsupportedOperationException {
        if (childId.equals(parentId)) {
            throw new IllegalArgumentException("Can't add group to itself.");
        }
        if (isGroupMemberOfGroup(parentId, childId)) {
            throw new IllegalArgumentException("Circular group reference.");
        }
        if (this.getDirectMemberGroupIds(parentId).contains(childId)) {
            return false;
        }
        GroupMember.Builder GMB = GroupMember.Builder.create(parentId,
        		childId,
                KimConstants.KimGroupMemberTypes.GROUP_MEMBER_TYPE);
        GMB.setId(UUID.randomUUID().toString());
        GroupMember groupMembership = GMB.build();
        this.groupMembershipCache.put(groupMembership.getMemberId(), groupMembership);
        return true;
    }

    @Override
    public boolean addPrincipalToGroup(String principalId, String groupId)
            throws UnsupportedOperationException {
        if (this.getDirectMemberPrincipalIds(groupId).contains(principalId)) {
            return false;
        }
        GroupMember.Builder GMB = GroupMember.Builder.create(groupId,
                principalId,
                KimConstants.KimGroupMemberTypes.PRINCIPAL_MEMBER_TYPE);
        GMB.setId(UUID.randomUUID().toString());
        GroupMember groupMembership = GMB.build();
        
        
        
        this.groupMembershipCache.put(groupMembership.getMemberId(), groupMembership);
        return true;
    }

    @Override
    public Group createGroup(Group group)
            throws UnsupportedOperationException {
        Group copy = new MapHelper().copy(group);
        if (copy.getId() != null) {
            if (this.getGroup(copy.getId()) != null) {
                throw new IllegalArgumentException("duplicate id");
            }
        } else {
            Group.Builder.create(group) .setId(UUID.randomUUID().toString());
        }
        if (this.getGroupByNamespaceCodeAndName(copy.getNamespaceCode(), copy.getName()) != null) {
            throw new IllegalArgumentException("name in use");
        }
        this.groupCache.put(copy.getId(), copy);
        return copy;

    }

    @Override
    public void removeAllMembers(String groupId) throws UnsupportedOperationException {
        for (GroupMember info : this.groupMembershipCache.values()) {
            if (info.getGroupId().equals(groupId)) {
                groupMembershipCache.remove(info.getMemberId());
            }
        }
    }

    @Override
    public boolean isGroupMemberOfGroupWithDate(String groupMemberId, String groupId, DateTime asOfDate) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isMemberOfGroupWithDate(String principalId, String groupId, DateTime asOfDate) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getDirectMemberGroupIdsWithDate(String groupId) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getDirectParentGroupIdsWithDate(String groupId, DateTime asOfDate) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<GroupMember> getMembersWithDate(List<String> groupIds, DateTime asOfDate) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeGroupFromGroup(String childId, String parentId) throws UnsupportedOperationException {
        for (GroupMember info : this.groupMembershipCache.values()) {
            if (info.getGroupId().equals(parentId)) {
                if (info.getType().getCode().equals(KimConstants.KimGroupMemberTypes.GROUP_MEMBER_TYPE)) {
                    if (info.getMemberId().equals(childId)) {
                        groupMembershipCache.remove(info.getMemberId());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean removePrincipalFromGroup(String principalId, String groupId)
            throws UnsupportedOperationException {
        for (GroupMember info : this.groupMembershipCache.values()) {
            if (info.getGroupId().equals(groupId)) {
                //or MemberType.GROUP.getCode()
                if (info.getType().getCode().equals(KimConstants.KimGroupMemberTypes.GROUP_MEMBER_TYPE)) {
                    if (this.removePrincipalFromGroup(principalId, info.getMemberId())) {
                        return true;
                    }
                    if (info.getMemberId().equals(principalId)) {
                        groupMembershipCache.remove(info.getMemberId());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Group updateGroup(String groupId, Group Group) throws UnsupportedOperationException {
        if (groupId == null) {
            throw new IllegalArgumentException("Group id cannot be null");
        }
        if (Group.getId() == null) {
            throw new IllegalArgumentException("New group id cannot be null");
        }
        Group existing = this.getGroup(groupId);
        if (existing == null) {
            throw new IllegalArgumentException("group id not found");
        }
        Group matching = this.getGroupByNamespaceCodeAndName(Group.getNamespaceCode(), Group.getName());
        if (matching != null) {
            if (matching != existing) {
                throw new IllegalArgumentException("name in use");
            }
        }
        Group copy = new MapHelper().copy(Group);
        this.groupCache.put(copy.getId(), copy);
        return copy;
    }
    
    @Override
    // Redirects to the above method in order to satisfy the new requirements of interface GroupUpdateService
    public Group updateGroup(Group Group) throws UnsupportedOperationException {
    	return updateGroup(Group.getId(), Group);
    }

    @Override
    public GroupMember createGroupMember(GroupMember groupMember) throws RiceIllegalArgumentException{
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GroupMember updateGroupMember(GroupMember groupMember) throws RiceIllegalArgumentException{
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

