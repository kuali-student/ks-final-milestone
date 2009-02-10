package org.kuali.student.lum.lu.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.kuali.student.core.dao.impl.AbstractCrudDaoImpl;
import org.kuali.student.lum.lu.dao.LuDao;

public class LuDaoImpl extends AbstractCrudDaoImpl implements LuDao{
	@PersistenceContext(unitName = "Lu")
	@Override
	public void setEm(EntityManager em) {
		super.setEm(em);
	}
	
	
}
