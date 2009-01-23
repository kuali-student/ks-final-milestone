package org.kuali.student.core.organization.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.core.dao.impl.AbstractCrudDaoImpl;
import org.kuali.student.core.organization.dao.OrganizationDao;
import org.kuali.student.core.organization.entity.OrgHierarchy;
import org.kuali.student.core.organization.entity.OrgPersonRelation;

public class OrganizationDaoImpl extends AbstractCrudDaoImpl implements OrganizationDao {

	@PersistenceContext(unitName = "Organization")
	@Override
	public void setEm(EntityManager em) {
		super.setEm(em);
	}

    @Override
    public List<OrgPersonRelation> getAllOrgPersonRelationsByPerson(String personId) {
        Query query = em.createQuery("select opr from OrgPersonRelation opr where personId = :personId");
        query.setParameter("personId", personId);
        @SuppressWarnings("unchecked")
        List<OrgPersonRelation> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List<OrgHierarchy> getOrgHierarchies() {
        @SuppressWarnings("unchecked")
        List<OrgHierarchy> resultList = em.createQuery("select oh from OrgHierarchy oh").getResultList();
        return resultList;
    }

}
