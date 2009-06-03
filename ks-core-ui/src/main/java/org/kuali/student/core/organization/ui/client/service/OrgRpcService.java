package org.kuali.student.core.organization.ui.client.service;

import java.util.List;

import org.kuali.student.common.ui.client.service.BaseRpcService;
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
import org.kuali.student.core.organization.service.OrganizationService;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
* This class lists all of the methods that the remote calls between client and servlet make, 
* most of these will be verbatim from the web service  
*/
@RemoteServiceRelativePath("OrgRpcService")
public interface OrgRpcService extends RemoteService, BaseRpcService {
	
	public OrgInfo createOrganization(OrgInfo orgInfo);
    public OrgOrgRelationInfo createOrgOrgRelation(OrgOrgRelationInfo orgOrgRelationInfo);
    
    public List<OrgHierarchyInfo> getOrgHierarchies();
    public OrgInfo getOrganization(String orgId);
    public List<OrgInfo> getOrganizationsByIdList(List<String> orgIdList);
    public List<OrgOrgRelationInfo> getOrgOrgRelationsByOrg(String orgId);
    public List<String> getAllDescendants(String orgId, String orgHierarchy);
    public List<OrgOrgRelationTypeInfo> getOrgOrgRelationTypes();
    public List<OrgPersonRelationTypeInfo> getOrgPersonRelationTypes();
    public List<OrgTypeInfo> getOrgTypes();
    public List<OrgPositionRestrictionInfo> getPositionRestrictionsByOrg(String orgId);
       
    public OrgPositionRestrictionInfo addPositionRestrictionToOrg(OrgPositionRestrictionInfo orgPositionRestrictionInfo);
    public List<OrgTreeInfo> getOrgDisplayTree(String orgId, String orgHierarchy, int maxLevels);

    public OrgInfo updateOrganization(OrgInfo orgInfo);
    public OrgPositionRestrictionInfo updatePositionRestrictionForOrg(OrgPositionRestrictionInfo orgPositionRestrictionInfo);
    public OrgOrgRelationInfo updateOrgOrgRelation(OrgOrgRelationInfo orgOrgRelationInfo);
       
    public OrgPersonRelationInfo createOrgPersonRelation(String orgId, String personId, String orgPersonRelationTypeKey, OrgPersonRelationInfo orgPersonRelationInfo);
    public OrgPersonRelationInfo updateOrgPersonRelation(String orgPersonRelationId, OrgPersonRelationInfo orgPersonRelationInfo);
    public StatusInfo removeOrgPersonRelation(String orgPersonRelationId);
    public List<OrgPersonRelationTypeInfo> getOrgPersonRelationTypesForOrgType(String orgTypeKey);
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByOrg(String orgId);
    public StatusInfo removeOrgOrgRelation(String orgOrgRelationId);
    public StatusInfo removePositionRestrictionFromOrg(String orgId, String orgPersonRelationTypeKey);

}
