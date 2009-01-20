package org.kuali.student.core.organization.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.kuali.student.core.dao.impl.AbstractCrudDaoImpl;
import org.kuali.student.core.organization.dao.OrganizationDao;

public class OrganizationDaoImpl extends AbstractCrudDaoImpl implements OrganizationDao {

	@PersistenceContext(unitName = "Organization")
	@Override
	public void setEm(EntityManager em) {
		super.setEm(em);
	}
}
