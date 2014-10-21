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

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcService;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.core.organization.ui.client.mvc.model.MembershipInfo;
import org.kuali.student.core.organization.ui.client.mvc.model.OrgPositionPersonRelationInfo;
import org.kuali.student.core.organization.ui.client.mvc.model.SectionConfigInfo;
import org.kuali.student.r1.common.assembly.data.AssemblyException;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.organization.dto.*;

import java.util.List;
import java.util.Map;

/**
* This class lists all of the methods that the remote calls between client and servlet make, 
* most of these will be verbatim from the web service  
*/
@RemoteServiceRelativePath("rpcservices/OrgRpcService")
public interface OrgRpcService extends RemoteService, BaseDataOrchestrationRpcService {
	
	public OrgInfo createOrganization(OrgInfo orgInfo);
    public OrgOrgRelationInfo createOrgOrgRelation(OrgOrgRelationInfo orgOrgRelationInfo);
    public List<OrgHierarchyInfo> getOrgHierarchies();
    public OrgInfo getOrganization(String orgId);
    public List<OrgInfo> getOrganizationsByIdList(List<String> orgIdList);
    public List<OrgOrgRelationInfo> getOrgOrgRelationsByOrg(String orgId);
    public List<OrgOrgRelationInfo> getOrgOrgRelationsByRelatedOrg(String orgId);
    public List<String> getAllDescendants(String orgId, String orgHierarchy);
    public List<TypeInfo> getOrgOrgRelationTypes();
    public TypeInfo getOrgOrgRelationType(String orgOrgRelationTypeKey);
    public List<TypeInfo> getOrgPersonRelationTypes();
    public List<TypeInfo> getOrgTypes();
    public List<OrgPositionRestrictionInfo> getPositionRestrictionsByOrg(String orgId);
    public OrgPositionRestrictionInfo addPositionRestrictionToOrg(OrgPositionRestrictionInfo orgPositionRestrictionInfo);
    public List<OrgTreeInfo> getOrgDisplayTree(String orgId, String orgHierarchy, int maxLevels);
    public OrgInfo updateOrganization(OrgInfo orgInfo);
    public OrgPositionRestrictionInfo updatePositionRestrictionForOrg(OrgPositionRestrictionInfo orgPositionRestrictionInfo);
    public OrgOrgRelationInfo updateOrgOrgRelation(OrgOrgRelationInfo orgOrgRelationInfo);
    public OrgPersonRelationInfo createOrgPersonRelation(String orgId, String personId, String orgPersonRelationTypeKey, OrgPersonRelationInfo orgPersonRelationInfo);
    public OrgPersonRelationInfo updateOrgPersonRelation(String orgPersonRelationId, OrgPersonRelationInfo orgPersonRelationInfo);
    public StatusInfo removeOrgPersonRelation(String orgPersonRelationId);
    public List<TypeInfo> getOrgPersonRelationTypesForOrgType(String orgTypeKey);
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByOrg(String orgId);
    public StatusInfo removeOrgOrgRelation(String orgOrgRelationId);
    public StatusInfo deleteOrgPositionRestriction(String orgPositionRestrictionId);
    public DataSaveResult saveOrgProposal(Data proposal) throws AssemblyException, org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
    public SectionConfigInfo getSectionConfig() throws org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
    public Data fetchOrg(String orgId);
    public List<OrgPositionPersonRelationInfo> getOrgPositionPersonRelation(String orgId);
    public Map<String, MembershipInfo> getNamesForPersonIds(List<String> personIds);
    public List<ValidationResultInfo> validate(Data data);
}
