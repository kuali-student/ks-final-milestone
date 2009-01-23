package org.kuali.student.core.organization.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.core.dao.impl.AbstractCrudDaoImpl;
import org.kuali.student.core.organization.dao.OrganizationDao;
import org.kuali.student.core.organization.entity.OrgPersonRelation;

public class OrganizationDaoImpl extends AbstractCrudDaoImpl implements OrganizationDao {

	@PersistenceContext(unitName = "Organization")
	@Override
	public void setEm(EntityManager em) {
		super.setEm(em);
	}

    @Override
    public List<OrgPersonRelation> getAllOrgPersonRelationsByPerson(String personId) {
        Query query = em.createQuery("select distinct opr from OrgPersonRelation opr where personId = :personId");
        query.setParameter("personId", personId);
        @SuppressWarnings("unchecked")
        List<OrgPersonRelation> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List<OrgPersonRelation> getAllOrgPersonRelationsByOrg(String orgId) {
        Query query = em.createQuery("select distinct opr from OrgPersonRelation opr join opr.org o where o.id = :orgId");
        query.setParameter("orgId", orgId);
        @SuppressWarnings("unchecked")
        List<OrgPersonRelation> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List<String> getPersonIdsForOrgByRelationType(String orgId, String orgPersonRelationTypeKey) {
        Query query = em.createQuery("select distinct opr.personId from OrgPersonRelation opr join opr.org o join opr.type t where o.id = :orgId and t.key = :orgPersonRelationTypeKey");
        query.setParameter("orgId", orgId);
        query.setParameter("orgPersonRelationTypeKey", orgPersonRelationTypeKey);
        @SuppressWarnings("unchecked")
        List<String> resultList = query.getResultList();
        return resultList;
    }

}
