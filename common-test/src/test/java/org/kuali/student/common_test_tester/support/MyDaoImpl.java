package org.kuali.student.common_test_tester.support;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class MyDaoImpl implements MyDao {
	
	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public Long createValue(Value value) {
		entityManager.persist(value);
		return value.getId();
	}

	@Override
	public String findValue(Long id) {
		Value tv =  entityManager.find(Value.class, id);
		if(tv==null){
			return null;
		}
		return tv.getValue();
	}

}
