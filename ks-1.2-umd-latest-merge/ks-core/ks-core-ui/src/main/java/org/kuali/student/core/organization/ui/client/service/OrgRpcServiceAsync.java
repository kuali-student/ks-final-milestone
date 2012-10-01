/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.core.organization.ui.client.service;

import java.util.List;
import java.util.Map;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcServiceAsync;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.dto.OrgTreeInfo;
import org.kuali.student.core.organization.dto.OrgTypeInfo;
import org.kuali.student.core.organization.ui.client.mvc.model.MembershipInfo;
import org.kuali.student.core.organization.ui.client.mvc.model.OrgPositionPersonRelationInfo;
import org.kuali.student.core.organization.ui.client.mvc.model.SectionConfigInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface OrgRpcServiceAsync extends BaseDataOrchestrationRpcServiceAsync {
    public void createOrganization(OrgInfo orgInfo, AsyncCallback<OrgInfo> callback);
    public void createOrgOrgRelation(OrgOrgRelationInfo orgOrgRelationInfo, AsyncCallback<OrgOrgRelationInfo> callback);

    public void getOrgHierarchies(AsyncCallback<List<OrgHierarchyInfo>> callback);
    public void getOrganization(String orgId, AsyncCallback<OrgInfo> callback);
    public void getOrganizationsByIdList(List<String> orgIdList, AsyncCallback<List<OrgInfo> > callback);
    public void getOrgOrgRelationsByOrg(String orgId, AsyncCallback<List<OrgOrgRelationInfo>> callback);
    public void getOrgOrgRelationsByRelatedOrg(String orgId,AsyncCallback<List<OrgOrgRelationInfo>> callback);
    public void getAllDescendants(String orgId, String orgHierarchy, AsyncCallback<List<String>> callback);
    public void getOrgOrgRelationTypes(AsyncCallback<List<OrgOrgRelationTypeInfo>> callback);
    public void getOrgOrgRelationType(String orgOrgRelationTypeKey,AsyncCallback<OrgOrgRelationTypeInfo> callback);
    public void getOrgPersonRelationTypes(AsyncCallback<List<OrgPersonRelationTypeInfo>> callback);
    public void getOrgTypes(AsyncCallback<List<OrgTypeInfo>> callback);
    public void getOrgDisplayTree(String orgId, String orgHierarchy, int maxLevels, AsyncCallback<List<OrgTreeInfo>> callback);

    public void getPositionRestrictionsByOrg(String orgId, AsyncCallback<List<OrgPositionRestrictionInfo>> callback);

    public void addPositionRestrictionToOrg(OrgPositionRestrictionInfo orgPositionRestrictionInfo, AsyncCallback<OrgPositionRestrictionInfo> callback);

    public void updateOrganization(OrgInfo orgInfo, AsyncCallback<OrgInfo> callback);
    public void updatePositionRestrictionForOrg(OrgPositionRestrictionInfo orgPositionRestrictionInfo, AsyncCallback<OrgPositionRestrictionInfo> callback);
    public void updateOrgOrgRelation(OrgOrgRelationInfo orgOrgRelationInfo, AsyncCallback<OrgOrgRelationInfo> callback);

    public void createOrgPersonRelation(String orgId, String personId, String orgPersonRelationTypeKey, OrgPersonRelationInfo orgPersonRelationInfo, AsyncCallback<OrgPersonRelationInfo> callback);
    public void updateOrgPersonRelation(String orgPersonRelationId, OrgPersonRelationInfo orgPersonRelationInfo, AsyncCallback<OrgPersonRelationInfo> callback);
    public void removeOrgPersonRelation(String orgPersonRelationId, AsyncCallback<StatusInfo> callback);
    public void getOrgPersonRelationTypesForOrgType(String orgTypeKey, AsyncCallback<List<OrgPersonRelationTypeInfo>> callback);
    public void getOrgPersonRelationsByOrg(String orgId, AsyncCallback<List<OrgPersonRelationInfo>> callback);
    public void removeOrgOrgRelation(String orgOrgRelationId, AsyncCallback<StatusInfo> callback);
	public void removePositionRestrictionFromOrg(String orgId, String orgPersonRelationTypeKey, AsyncCallback<StatusInfo> callback);
	public void saveOrgProposal(Data proposal, AsyncCallback<DataSaveResult> callback);
	public void getSectionConfig(AsyncCallback<SectionConfigInfo> callback);
	public void fetchOrg(String orgId,AsyncCallback<Data> callback);
	public void getOrgPositionPersonRelation(String orgId, AsyncCallback<List<OrgPositionPersonRelationInfo>> callback);
	public void getNamesForPersonIds(List<String> personIds, AsyncCallback<Map<String, MembershipInfo>> callback);
}
