package org.kuali.student.r2.core.class1.state.dao;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.state.model.StateEntity;

import java.util.List;

public class StateDao extends GenericEntityDao<StateEntity>{

	public StateEntity getState(String stateKey){
		return (StateEntity)em.createQuery("from StateEntity se where se.id=:stateKey")
		.setParameter("stateKey", stateKey)
		.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<StateEntity> getStatesByLifecycle(String processKey){
		return  (List<StateEntity>)em.createQuery("from StateEntity se where se.lifecycleKey=:lifecycleKey")
		.setParameter("lifecycleKey", processKey)
		.getResultList();		
	}

    /**
     * Returns a list of state-key IDs which are valid initial-states for a given lifecycle.
     *
     * @param lifecycle
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<String> getInitialStateKeys (String lifecycle) {
        return em.createQuery( "SELECT se.id FROM StateEntity se WHERE se.lifecycleKey=:lifecycleKey and se.initialState=1")
                 .setParameter( "lifecycleKey", lifecycle )
                .getResultList();
    }

}
