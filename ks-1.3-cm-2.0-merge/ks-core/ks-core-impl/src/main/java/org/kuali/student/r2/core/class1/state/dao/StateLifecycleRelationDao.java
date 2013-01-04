package org.kuali.student.r2.core.class1.state.dao;

import java.util.List;

import javax.persistence.Query;

import org.kuali.student.r2.common.class1.state.model.StateEntity;
import org.kuali.student.r2.common.class1.state.model.StateLifecycleRelationEntity;
import org.kuali.student.r2.common.dao.GenericEntityDao;

public class StateLifecycleRelationDao extends GenericEntityDao<StateLifecycleRelationEntity>{
	@SuppressWarnings("unchecked")
	public List<StateEntity> getInitialValidStates(String processKey){
		Query query = em.createNamedQuery("StateProcess.getInitialValidStates");
		query.setParameter("processKey", processKey);
		List<StateEntity> initStates = query.getResultList();
		return initStates;
	}
	
	public StateEntity getNextHappyState(String processKey, String currentStateKey){
		Query query = em.createNamedQuery("StateProcess.getNextHappyState");
		query.setParameter("processKey", processKey);
		query.setParameter("stateKey", currentStateKey);
		StateEntity nextState = (StateEntity) query.getSingleResult();
		return nextState;
	}	
}
