package org.kuali.student.lum.atp.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.kuali.student.core.entity.Type;
import org.kuali.student.lum.atp.dao.AtpDao;

public class AtpDaoImpl implements AtpDao {
	@PersistenceContext(unitName = "Atp")
	private EntityManager em;



	@Override
	public Type createType(Type type) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
}
