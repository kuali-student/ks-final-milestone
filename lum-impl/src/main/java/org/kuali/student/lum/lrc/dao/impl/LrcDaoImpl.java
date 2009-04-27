package org.kuali.student.lum.lrc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.core.dao.impl.AbstractSearchableCrudDaoImpl;
import org.kuali.student.lum.lrc.dao.LrcDao;
import org.kuali.student.lum.lrc.entity.Credit;

public class LrcDaoImpl extends AbstractSearchableCrudDaoImpl implements LrcDao {
	@PersistenceContext(unitName = "Lrc")
	@Override
	public void setEm(EntityManager em) {
		super.setEm(em);
	}

	@Override
	public List<Credit> getCreditsByKeyList(List<String> creditKeyList) {
	    Query query = em.createNamedQuery("Credit.getCreditsByKeyList");
        query.setParameter("creditKeyList", creditKeyList);
        @SuppressWarnings("unchecked")
        List<Credit> resultList = query.getResultList();
        return resultList;
	}
}
