/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.server.applicationstate.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.common.ui.server.applicationstate.dao.ApplicationStateDao;
import org.kuali.student.common.ui.server.applicationstate.entity.ApplicationState;
import org.kuali.student.r1.common.dao.impl.AbstractSearchableCrudDaoImpl;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;

/**
 * This data access class stores the GUI (page, section, widget, etc.) 
 * application states as key value pairs in a database.
 */
@Deprecated
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
	 * @throws DoesNotExistException Thrown if application state does not exist
	 */
	public ApplicationState getApplicationState(String applicationId, String referenceKey, String referenceType, String userId) throws DoesNotExistException {
		Query query = em.createNamedQuery("ApplicationState.getApplicationStateByAppRefUserId");
		query.setParameter("applicationId", applicationId);
		query.setParameter("referenceKey", referenceKey);
		query.setParameter("referenceType", referenceType);
		query.setParameter("userId", userId);

		try {
			ApplicationState appState = (ApplicationState) query.getSingleResult();
			return appState;
		} catch(javax.persistence.NoResultException e) {
			throw new DoesNotExistException("Application state does not exist");
		}
	}

	/**
	 * Gets an application states by <code>applicationId</code>, 
	 * <code>referenceKey</code> and <code>referenceType</code>.
	 * 
	 * @param applicationId Application id
	 * @param referenceKey Reference key
	 * @param referenceType Reference type
	 * @return A list of application states
	 * @throws DoesNotExistException Thrown if application state does not exist
	 */
	public ApplicationState getApplicationState(String applicationId, String referenceKey, String referenceType) throws DoesNotExistException {
		return getApplicationState(applicationId, referenceKey, referenceType, DEFAULT_USER_ID);
	}

	/**
	 * Creates and returns an application state.
	 * 
	 * @param appState Application state
	 * @return A new application state
	 * @throws AlreadyExistsException Thrown if application state already exists
	 */
	public ApplicationState createApplicationState(ApplicationState appState) throws AlreadyExistsException {
		if(appState.getUserId() == null) {
			appState.setUserId(DEFAULT_USER_ID);
		}

		try {
			getApplicationState(appState.getApplicationId(), appState.getReferenceKey(), appState.getReferenceType(), appState.getUserId());
		} catch(DoesNotExistException e) {
			return super.create(appState);
		}
		throw new AlreadyExistsException("Application state already exists");
	}

	/**
	 * Creates a collection of application states and returns their ids.
	 * 
	 * @param appStateList collection of application states
	 * @return A list of newly created application state ids
	 * @throws AlreadyExistsException Thrown if application state already exists
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
