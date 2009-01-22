package org.kuali.student.core.organization.dao;

import java.util.List;

import org.kuali.student.core.dao.CrudDao;
import org.kuali.student.core.organization.entity.OrgHierarchy;
import org.kuali.student.core.organization.entity.OrgPersonRelation;

public interface OrganizationDao extends CrudDao{

    List<OrgPersonRelation> getAllOrgPersonRelationsByPerson(String personId);
    List<OrgHierarchy> getOrgHierarchies();
    OrgHierarchy getOrgHierarchy(String orgHierarchyKey);
}
