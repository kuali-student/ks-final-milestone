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
package org.kuali.student.kim.permission.mock;

import java.util.*;
import org.kuali.rice.kim.bo.Group;
import org.kuali.rice.kim.bo.Role;
import org.kuali.rice.kim.bo.group.dto.GroupInfo;
import org.kuali.rice.kim.bo.group.dto.GroupMembershipInfo;
import org.kuali.rice.kim.service.GroupService;
import org.kuali.rice.kim.service.GroupUpdateService;

/**
 * @author nwright
 */
public class GroupServiceMockImpl implements GroupService,
        GroupUpdateService {

    private transient Map<String, GroupInfo> groupCache = new HashMap<String, GroupInfo>();
    private transient Map<String, GroupMembershipInfo> groupMembershipCache = new HashMap<String, GroupMembershipInfo>();

    @Override
    public List<String> getDirectGroupIdsForPrincipal(String principalId) {
        List<String> groups = new ArrayList<String>();
        for (GroupMembershipInfo groupMembership : this.groupMembershipCache.values()) {
            if (groupMembership.getMemberTypeCode().equals(Role.PRINCIPAL_MEMBER_TYPE)) {
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
        for (GroupMembershipInfo groupMembership : this.groupMembershipCache.values()) {
            if (groupMembership.getMemberTypeCode().equals(Role.GROUP_MEMBER_TYPE)) {
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
        for (GroupMembershipInfo groupMembership : this.groupMembershipCache.values()) {
            if (groupMembership.getMemberTypeCode().equals(Role.PRINCIPAL_MEMBER_TYPE)) {
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
        for (GroupMembershipInfo groupMembership : this.groupMembershipCache.values()) {
            if (groupMembership.getMemberTypeCode().equals(Role.PRINCIPAL_MEMBER_TYPE)) {
                if (groupMembership.getMemberId().equals(groupId)) {
                    members.add(groupMembership.getGroupId());
                }
            }
        }
        return members;
    }

    @Override
    public Map<String, String> getGroupAttributes(String groupId) {
        return this.getGroupInfo(groupId).getAttributes();
    }

    @Override
    public List<String> getGroupIdsForPrincipal(String principalId) {
        List<String> allGroups = new ArrayList<String>();
        List<String> directGroups = getDirectGroupIdsForPrincipal(principalId);
        allGroups.addAll(directGroups);
        for (String groupId : directGroups) {
            List<String> ancestors = this.getParentGroupIds(groupId);
            allGroups.addAll(ancestors);
        }
        return allGroups;
    }

    @Override
    public List<String> getGroupIdsForPrincipalByNamespace(String principalId,
            String namespaceCode) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GroupInfo getGroupInfo(String groupId) {
        return this.groupCache.get(groupId);
    }

    @Override
    public GroupInfo getGroupInfoByName(String namespaceCode, String groupName) {
        for (GroupInfo group : this.groupCache.values()) {
            if (namespaceCode.equals(group.getNamespaceCode())) {
                if (groupName.equals(group.getGroupName())) {
                    return group;
                }
            }
        }
        return null;
    }

    @Override
    public Map<String, GroupInfo> getGroupInfos(Collection<String> groupIds) {
        Map<String, GroupInfo> groups = new HashMap<String, GroupInfo>();
        for (String groupId : groupIds) {
            GroupInfo info = this.getGroupInfo(groupId);
            groups.put(groupId, info);
        }
        return groups;
    }

    @Override
    public Collection<GroupMembershipInfo> getGroupMembers(List<String> groupIds) {
        Collection<GroupMembershipInfo> infos = new ArrayList<GroupMembershipInfo>();
        for (GroupMembershipInfo info : this.groupMembershipCache.values()) {
            if (groupIds.contains(info.getGroupId())) {
                infos.add(info);
            }
        }
        return infos;
    }

    @Override
    public Collection<GroupMembershipInfo> getGroupMembersOfGroup(String groupId) {
        Collection<GroupMembershipInfo> infos = new ArrayList<GroupMembershipInfo>();
        for (GroupMembershipInfo info : this.groupMembershipCache.values()) {
            if (groupId.equals(info.getGroupId())) {
                infos.add(info);
            }
        }
        return infos;
    }

    @Override
    public List<GroupInfo> getGroupsForPrincipal(String principalId) {
        Collection<String> groupIds = this.getGroupIdsForPrincipal(principalId);
        List<GroupInfo> groups = new ArrayList<GroupInfo>();
        for (String groupId : groupIds) {
            GroupInfo group = this.getGroupInfo(groupId);
            groups.add(group);
        }
        return groups;
    }

    @Override
    public List<GroupInfo> getGroupsForPrincipalByNamespace(String principalId,
            String namespaceCode) {
        List<GroupInfo> groups = new ArrayList<GroupInfo>();
        for (GroupInfo group : this.getGroupsForPrincipal(principalId)) {
            if (namespaceCode.equals(group.getNamespaceCode())) {
                groups.add(group);
            }
        }
        return groups;
    }

    @Override
    public List<String> getMemberGroupIds(String groupId) {
        List<String> groupIds = new ArrayList<String>();
        for (GroupMembershipInfo info : this.groupMembershipCache.values()) {
            if (groupId.equals(info.getGroupId())) {
                if (info.getMemberTypeCode().equals(Role.GROUP_MEMBER_TYPE)) {
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
        for (GroupMembershipInfo info : this.groupMembershipCache.values()) {
            if (groupId.equals(info.getGroupId())) {
                if (info.getMemberTypeCode().equals(Role.GROUP_MEMBER_TYPE)) {
                    principalIds.addAll(this.getMemberPrincipalIds(info.getMemberId()));
                } else if (info.getMemberTypeCode().equals(Role.PRINCIPAL_MEMBER_TYPE)) {
                    principalIds.add(info.getMemberId());
                }
            }
        }
        return principalIds;
    }

    @Override
    public List<String> getParentGroupIds(String groupId) {
        List<String> groupIds = new ArrayList<String>();
        for (GroupMembershipInfo info : this.groupMembershipCache.values()) {
            if (info.getMemberTypeCode().equals(Role.GROUP_MEMBER_TYPE)) {
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
        for (GroupMembershipInfo info : this.groupMembershipCache.values()) {
            if (groupId.equals(info.getGroupId())) {
                if (info.getMemberTypeCode().equals(Role.PRINCIPAL_MEMBER_TYPE)) {
                    if (principalId.equals(info.getMemberId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean isGroupActive(String groupId) {
        return this.getGroupInfo(groupId).isActive();
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
    public List<String> lookupGroupIds(Map<String, String> searchCriteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<? extends Group> lookupGroups(Map<String, String> searchCriteria) {
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
        GroupMembershipInfo groupMembership = new GroupMembershipInfo(parentId,
                UUID.randomUUID().toString(),
                childId,
                Role.GROUP_MEMBER_TYPE,
                new Date(),
                null);
        this.groupMembershipCache.put(groupMembership.getGroupMemberId(), groupMembership);
        return true;
    }

    @Override
    public boolean addPrincipalToGroup(String principalId, String groupId)
            throws UnsupportedOperationException {
        if (this.getDirectMemberPrincipalIds(groupId).contains(principalId)) {
            return false;
        }
        GroupMembershipInfo groupMembership = new GroupMembershipInfo(groupId,
                UUID.randomUUID().toString(),
                principalId,
                Role.PRINCIPAL_MEMBER_TYPE,
                new Date(),
                null);
        this.groupMembershipCache.put(groupMembership.getGroupMemberId(), groupMembership);
        return true;
    }

    @Override
    public GroupInfo createGroup(GroupInfo groupInfo)
            throws UnsupportedOperationException {
        GroupInfo copy = new MockHelper().copy(groupInfo);
        if (copy.getGroupId() != null) {
            if (this.getGroupInfo(copy.getGroupId()) != null) {
                throw new IllegalArgumentException("duplicate id");
            }
        } else {
            copy.setGroupId(UUID.randomUUID().toString());
        }
        if (this.getGroupInfoByName(copy.getNamespaceCode(), copy.getGroupName()) != null) {
            throw new IllegalArgumentException("name in use");
        }
        this.groupCache.put(copy.getGroupId(), copy);
        return copy;

    }

    @Override
    public void removeAllGroupMembers(String groupId) throws UnsupportedOperationException {
        for (GroupMembershipInfo info : this.groupMembershipCache.values()) {
            if (info.getGroupId().equals(groupId)) {
                groupMembershipCache.remove(info.getGroupMemberId());
            }
        }
    }

    @Override
    public boolean removeGroupFromGroup(String childId, String parentId) throws UnsupportedOperationException {
        for (GroupMembershipInfo info : this.groupMembershipCache.values()) {
            if (info.getGroupId().equals(parentId)) {
                if (info.getMemberTypeCode().equals(Role.GROUP_MEMBER_TYPE)) {
                    if (info.getMemberId().equals(childId)) {
                        groupMembershipCache.remove(info.getGroupMemberId());
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
        for (GroupMembershipInfo info : this.groupMembershipCache.values()) {
            if (info.getGroupId().equals(groupId)) {
                if (info.getMemberTypeCode().equals(Role.GROUP_MEMBER_TYPE)) {
                    if (this.removePrincipalFromGroup(principalId, info.getMemberId())) {
                        return true;
                    }
                    if (info.getMemberId().equals(principalId)) {
                        groupMembershipCache.remove(info.getGroupMemberId());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public GroupInfo updateGroup(String groupId, GroupInfo groupInfo) throws UnsupportedOperationException {
        if (groupId == null) {
            throw new IllegalArgumentException("Group id cannot be null");
        }
        if (groupInfo.getGroupId() == null) {
            throw new IllegalArgumentException("New group id cannot be null");
        }
        GroupInfo existing = this.getGroupInfo(groupId);
        if (existing == null) {
            throw new IllegalArgumentException("group id not found");
        }
        GroupInfo matching = this.getGroupInfoByName(groupInfo.getNamespaceCode(), groupInfo.getGroupName());
        if (matching != null) {
            if (matching != existing) {
                throw new IllegalArgumentException("name in use");
            }
        }
        GroupInfo copy = new MockHelper().copy(groupInfo);
        this.groupCache.put(copy.getGroupId(), copy);
        return copy;
    }
}

