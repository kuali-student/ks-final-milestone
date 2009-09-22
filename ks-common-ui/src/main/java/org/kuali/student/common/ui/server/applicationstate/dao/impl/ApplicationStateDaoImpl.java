package org.kuali.student.common.ui.server.applicationstate.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.common.ui.server.applicationstate.dao.ApplicationStateDao;
import org.kuali.student.common.ui.server.applicationstate.entity.ApplicationState;
import org.kuali.student.core.dao.impl.AbstractSearchableCrudDaoImpl;
import org.kuali.student.core.exceptions.AlreadyExistsException;

/**
 * This data access class stores the GUI (page, section, widget, etc.) 
 * application states as key value pairs in a database.
 */
public class ApplicationStateDaoImpl extends AbstractSearchableCrudDaoImpl implements ApplicationStateDao {

	private final static String DEFAULT_USER_ID = "APPLICATION";
	
	@PersistenceContext(unitName = "ApplicationState")
	@Override
	public void setEm(EntityManager em) {
		super.setEm(em);
	}

	/**
	 * Gets an application states by <code>applicationId</code>, 
	 * <code>referenceKey</code>, <code>referenceType</code> and 
	 * <code>userId</code>.
	 * 
	 * @param applicationId Application id
	 * @param referenceKey Reference key
	 * @param referenceType Reference type
	 * @param userId User id
	 * @return An application state
	 */
	public ApplicationState getApplicationState(String applicationId, String referenceKey, String referenceType, String userId) {
		Query query = em.createNamedQuery("ApplicationState.getApplicationStateByAppRefUserId");
		query.setParameter("applicationId", applicationId);
		query.setParameter("referenceKey", referenceKey);
		query.setParameter("referenceType", referenceType);
		query.setParameter("userId", userId);

		ApplicationState appState = (ApplicationState) query.getSingleResult();
		return appState;
	}

	/**
	 * Gets an application states by <code>applicationId</code>, 
	 * <code>referenceKey</code> and <code>referenceType</code>.
	 * 
	 * @param applicationId Application id
	 * @param referenceKey Reference key
	 * @param referenceType Reference type
	 * @return A list of application states
	 */
	public ApplicationState getApplicationState(String applicationId, String referenceKey, String referenceType) {
		return getApplicationState(applicationId, referenceKey, referenceType, DEFAULT_USER_ID);
	}

	/**
	 * Creates and returns an application state.
	 * 
	 * @param appState Application state
	 * @return A new application state
	 * @throws AlreadyExistsException
	 */
	public ApplicationState createApplicationState(ApplicationState appState) throws AlreadyExistsException {
		if(appState.getUserId() == null) {
			appState.setUserId(DEFAULT_USER_ID);
		}

		try {
			getApplicationState(appState.getApplicationId(), appState.getReferenceKey(), appState.getReferenceType(), appState.getUserId());
		} catch(NoResultException e) {
			return super.create(appState);
		}
		throw new AlreadyExistsException("Application state already exists");
	}

	/**
	 * Creates a collection of application states and returns their ids.
	 * 
	 * @param appStateList collection of application states
	 * @return A list of newly created application state ids
	 * @throws AlreadyExistsException
	 */
	public List<String> createApplicationState(Collection<ApplicationState> appStates) throws AlreadyExistsException {
		List<String> newAppStateList = new ArrayList<String>();
		for(ApplicationState appState : appStates) {
			ApplicationState newAppState = createApplicationState(appState);
			newAppStateList.add(newAppState.getId());
		}
		return newAppStateList;
	}

}
