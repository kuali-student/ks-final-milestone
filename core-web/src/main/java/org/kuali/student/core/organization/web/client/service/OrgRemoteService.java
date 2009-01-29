package org.kuali.student.core.organization.web.client.service;

import java.util.List;

import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;

/**
 * @author Daniel Epstein
 * This class lists all of the methods that the remote calls between client and servlet make, 
 * most of these will be verbatim from the web service  
 *
 */
//TODO how do we do exceptions
public interface OrgRemoteService {

    public List<OrgHierarchyInfo> getOrgHierarchies();
    public List<OrgInfo> getOrganizationsByIdList(List<String> orgIdList);
    public List<OrgOrgRelationInfo> getOrgOrgRelationsByOrg(String orgId);
    public List<String> getAllDescendants(String orgId, String orgHierarchy);
}
