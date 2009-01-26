package org.kuali.student.core.web.organization.client.service;

import java.util.List;

import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface OrgRpcServiceAsync {
    public void getOrgHierarchies(AsyncCallback<List<OrgHierarchyInfo>> callback); 
    public void getOrganizationsByIdList(List<String> orgIdList, AsyncCallback<List<OrgInfo> > callback);
    public void getOrgOrgRelationsByOrg(String orgId, AsyncCallback<List<OrgOrgRelationInfo>> callback);
}
