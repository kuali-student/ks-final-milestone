package org.kuali.student.core.organization.dao;

import java.util.List;

import org.kuali.student.core.dao.CrudDao;
import org.kuali.student.core.organization.entity.OrgHierarchy;
import org.kuali.student.core.organization.entity.OrgPersonRelation;

public interface OrganizationDao extends CrudDao{

    public List<OrgPersonRelation> getAllOrgPersonRelationsByPerson(String personId);
    public List<OrgHierarchy> getOrgHierarchies();
}
