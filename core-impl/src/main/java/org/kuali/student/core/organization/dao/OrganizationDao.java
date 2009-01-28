package org.kuali.student.core.organization.dao;

import java.util.List;

import org.kuali.student.core.dao.CrudDao;
import org.kuali.student.core.organization.entity.Org;
import org.kuali.student.core.organization.entity.OrgOrgRelation;
import org.kuali.student.core.organization.entity.OrgPersonRelation;
import org.kuali.student.core.organization.entity.OrgPositionRestriction;

public interface OrganizationDao extends CrudDao{

    public List<OrgPersonRelation> getAllOrgPersonRelationsByPerson(String personId);
    public List<OrgPersonRelation> getAllOrgPersonRelationsByOrg(String orgId);
    public List<String> getPersonIdsForOrgByRelationType(String orgId, String orgPersonRelationTypeKey);
	public List<Org> getOrganizationsByIdList(List<String> orgIdList);
	public List<OrgOrgRelation> getOrgOrgRelationsByOrg(String orgId);
	public List<OrgPositionRestriction> getPositionRestrictionsByOrg(String orgId);
}
