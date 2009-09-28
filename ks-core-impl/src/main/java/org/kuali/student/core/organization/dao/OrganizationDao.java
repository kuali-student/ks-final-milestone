package org.kuali.student.core.organization.dao;

import java.util.List;

import org.kuali.student.core.dao.CrudDao;
import org.kuali.student.core.dao.SearchableDao;
import org.kuali.student.core.organization.dto.OrgTreeInfo;
import org.kuali.student.core.organization.entity.Org;
import org.kuali.student.core.organization.entity.OrgOrgRelation;
import org.kuali.student.core.organization.entity.OrgOrgRelationType;
import org.kuali.student.core.organization.entity.OrgPersonRelation;
import org.kuali.student.core.organization.entity.OrgPersonRelationType;
import org.kuali.student.core.organization.entity.OrgPositionRestriction;

public interface OrganizationDao extends CrudDao, SearchableDao{

    public List<OrgPersonRelation> getAllOrgPersonRelationsByPerson(String personId);
    public List<OrgPersonRelation> getAllOrgPersonRelationsByOrg(String orgId);
    public List<String> getPersonIdsForOrgByRelationType(String orgId, String orgPersonRelationTypeKey);
	public List<Org> getOrganizationsByIdList(List<String> orgIdList);
	public List<OrgOrgRelation> getOrgOrgRelationsByOrg(String orgId);
	public List<OrgPositionRestriction> getPositionRestrictionsByOrg(String orgId);
	public List<String> getAllAncestors(String orgId, String orgHierarchy);
	public List<String> getAllDescendants(String orgId, String orgHierarchy);
	public List<OrgOrgRelationType> getOrgOrgRelationTypesForOrgHierarchy(String orgHierarchyKey);
	public boolean validatePositionRestriction(String orgId, String orgPersonRelationTypeKey);
	public List<OrgPersonRelationType> getOrgPersonRelationTypesForOrgType(String orgTypeKey);
	public List<OrgOrgRelation> getOrgOrgRelationsByIdList(List<String> orgOrgRelationIdList);
	public List<OrgPersonRelation> getOrgPersonRelationsByIdList(List<String> orgPersonRelationIdList);
	public List<OrgPersonRelation> getOrgPersonRelationsByPerson(String personId, String orgId);
	public List<OrgOrgRelationType> getOrgOrgRelationTypesForOrgType(String orgTypeKey);
	public List<OrgOrgRelation> getOrgOrgRelationsByRelatedOrg(String relatedOrgId);
	public List<OrgTreeInfo> getOrgTreeInfo(String rootOrgId, String orgHierarchyId);
	public boolean hasOrgOrgRelation(String orgId, String comparisonOrgId, String orgOrgRelationTypeKey);
	public boolean hasOrgPersonRelation(String orgId, String personId, String orgPersonRelationTypeKey);
	public OrgPositionRestriction getPositionRestrictionByOrgAndPersonRelationTypeKey(String orgId, String orgPersonRelationTypeKey);
}
