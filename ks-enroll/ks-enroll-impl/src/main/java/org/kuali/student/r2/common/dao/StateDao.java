package org.kuali.student.r2.common.dao;

import java.util.List;

import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.r2.common.model.StateEntity;

public class StateDao extends GenericEntityDao<StateEntity>{
	public StateEntity getState(String processKey, String stateKey){
		return (StateEntity)em.createQuery("from StateEntity se where se.id=:stateKey and se.processKey=:processKey")
		.setParameter("stateKey", stateKey)
		.setParameter("processKey", processKey)
		.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<StateEntity> getStatesByProcess(String processKey){
		return  (List<StateEntity>)em.createQuery("from StateEntity se where se.processKey=:processKey")
		.setParameter("processKey", processKey)
		.getResultList();		
	}
}
