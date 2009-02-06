package org.kuali.student.core.organization.web.server.gwt;

import java.util.List;

import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.dto.OrgTreeInfo;
import org.kuali.student.core.organization.dto.OrgTypeInfo;
import org.kuali.student.core.organization.web.client.service.OrgRpcService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class OrgRpcServiceGwt extends RemoteServiceServlet implements OrgRpcService{

	private static final long serialVersionUID = 1L;

	private OrgRpcService serviceImpl;
	
	public OrgRpcService getServiceImpl() {
		return serviceImpl;
	}

	public void setServiceImpl(OrgRpcService serviceImpl) {
		this.serviceImpl = serviceImpl;
	}

	public List<OrgInfo> getOrganizationsByIdList(List<String> orgIdList) {
		return serviceImpl.getOrganizationsByIdList(orgIdList);
	}

	public List<OrgHierarchyInfo> getOrgHierarchies() {
		return serviceImpl.getOrgHierarchies();
	}

	public List<OrgOrgRelationInfo> getOrgOrgRelationsByOrg(String orgId) {
		return serviceImpl.getOrgOrgRelationsByOrg(orgId);
	}

    public List<String> getAllDescendants(String orgId, String orgHierarchy) {
        return serviceImpl.getAllDescendants(orgId, orgHierarchy);
    }

    public OrgPositionRestrictionInfo addPositionRestrictionToOrg(OrgPositionRestrictionInfo orgPositionRestrictionInfo) {
        return serviceImpl.addPositionRestrictionToOrg(orgPositionRestrictionInfo);
    }


    public OrgInfo createOrganization(OrgInfo orgInfo) {
        return serviceImpl.createOrganization(orgInfo);
    }

    public OrgOrgRelationInfo createOrgOrgRelation(OrgOrgRelationInfo orgOrgRelationInfo) {
        return serviceImpl.createOrgOrgRelation(orgOrgRelationInfo);
    }

    public List<OrgPersonRelationTypeInfo> getOrgPersonRelationTypes() {
        return serviceImpl.getOrgPersonRelationTypes();
    }

    public List<OrgTypeInfo> getOrgTypes() {
        return serviceImpl.getOrgTypes();
    }

    public List<OrgOrgRelationTypeInfo> getOrgOrgRelationTypes() {
        return serviceImpl.getOrgOrgRelationTypes();
    }

    public OrgInfo getOrganization(String orgId) {
        return serviceImpl.getOrganization(orgId);
    }

    public List<OrgPositionRestrictionInfo> getPositionRestrictionsByOrg(String orgId) {
        return serviceImpl.getPositionRestrictionsByOrg(orgId);
    }
           
	public List<OrgTreeInfo> getOrgDisplayTree(String orgId, String orgHierarchy, int maxLevels) {
		return serviceImpl.getOrgDisplayTree(orgId, orgHierarchy, maxLevels);
	}

    public OrgInfo updateOrganization(OrgInfo orgInfo) {
        return serviceImpl.updateOrganization(orgInfo); 
    }

    public OrgPositionRestrictionInfo updatePositionRestrictionForOrg(OrgPositionRestrictionInfo orgPositionRestrictionInfo) {
        return serviceImpl.updatePositionRestrictionForOrg(orgPositionRestrictionInfo);
    }

    public OrgOrgRelationInfo updateOrgOrgRelation(OrgOrgRelationInfo orgOrgRelationInfo) {
        return serviceImpl.updateOrgOrgRelation(orgOrgRelationInfo);
    }
	
	
}
