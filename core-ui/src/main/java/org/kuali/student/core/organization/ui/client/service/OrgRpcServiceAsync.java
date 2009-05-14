package org.kuali.student.core.organization.ui.client.service;

import java.util.List;

import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.dto.OrgTreeInfo;
import org.kuali.student.core.organization.dto.OrgTypeInfo;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface OrgRpcServiceAsync extends BaseRpcServiceAsync {
    public void createOrganization(OrgInfo orgInfo, AsyncCallback<OrgInfo> callback);
    public void createOrgOrgRelation(OrgOrgRelationInfo orgOrgRelationInfo, AsyncCallback<OrgOrgRelationInfo> callback);

    public void getOrgHierarchies(AsyncCallback<List<OrgHierarchyInfo>> callback); 
    public void getOrganization(String orgId, AsyncCallback<OrgInfo> callback);
    public void getOrganizationsByIdList(List<String> orgIdList, AsyncCallback<List<OrgInfo> > callback);
    public void getOrgOrgRelationsByOrg(String orgId, AsyncCallback<List<OrgOrgRelationInfo>> callback);
    public void getAllDescendants(String orgId, String orgHierarchy, AsyncCallback<List<String>> callback);
    public void getOrgOrgRelationTypes(AsyncCallback<List<OrgOrgRelationTypeInfo>> callback);
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
}
