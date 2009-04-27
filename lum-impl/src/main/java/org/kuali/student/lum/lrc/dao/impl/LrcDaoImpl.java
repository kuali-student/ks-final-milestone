package org.kuali.student.lum.lrc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.core.dao.impl.AbstractSearchableCrudDaoImpl;
import org.kuali.student.lum.lrc.dao.LrcDao;
import org.kuali.student.lum.lrc.entity.Credential;
import org.kuali.student.lum.lrc.entity.Credit;

public class LrcDaoImpl extends AbstractSearchableCrudDaoImpl implements LrcDao {
	@PersistenceContext(unitName = "Lrc")
	@Override
	public void setEm(EntityManager em) {
		super.setEm(em);
	}

    @Override
    public List<Credential> getCredentialsByIdList(List<String> credentialIdList) {
        Query query = em.createNamedQuery("Credential.getCredentialsByIdList");
        query.setParameter("credentialIdList", credentialIdList);
        @SuppressWarnings("unchecked")
        List<Credential> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List<String> getCredentialIdsByCredentialType(String credentialTypeId) {
        Query query = em.createNamedQuery("Credential.getCredentialIdsByCredentialType");
        query.setParameter("credentialTypeId", credentialTypeId);
        @SuppressWarnings("unchecked")
        List<String> resultList = query.getResultList();
        return resultList;
    }

	@Override
	public List<Credit> getCreditsByIdList(List<String> creditIdList) {
	    Query query = em.createNamedQuery("Credit.getCreditsByIdList");
        query.setParameter("creditIdList", creditIdList);
        @SuppressWarnings("unchecked")
        List<Credit> resultList = query.getResultList();
        return resultList;
	}

	@Override
    public List<String> getCreditIdsByCreditType(String creditTypeId) {
        Query query = em.createNamedQuery("Credit.getCreditIdsByCreditType");
        query.setParameter("creditTypeId", creditTypeId);
        @SuppressWarnings("unchecked")
        List<String> resultList = query.getResultList();
        return resultList;
    }
}
