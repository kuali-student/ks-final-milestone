package org.kuali.student.common.ui.server.applicationstate.dao;

import java.util.Collection;
import java.util.List;

import org.kuali.student.common.ui.server.applicationstate.entity.ApplicationState;
import org.kuali.student.core.dao.CrudDao;
import org.kuali.student.core.dao.SearchableDao;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DoesNotExistException;

/**
 * This data access interface stores the GUI (page, section, widget, etc.) 
 * application states as key value pairs in a database.
 */
public interface ApplicationStateDao extends CrudDao, SearchableDao {

	/**
	 * Gets a list of application states by 
	 * <code>applicationId</code>, <code>referenceKey</code>,  
	 * <code>referenceType</code> and <code>userId</code>.
	 * 
	 * @param applicationId Application id
	 * @param referenceKey Reference key
	 * @param referenceType Reference type
	 * @param userId User id
	 * @return An application state
	 * @throws DoesNotExistException Thrown if application state does not exist
	 */
	public ApplicationState getApplicationState(String applicationId, String referenceKey, String referenceType, String userId) throws DoesNotExistException;

	/**
	 * Gets a list of application states by 
	 * <code>applicationId</code>, <code>referenceKey</code> and 
	 * <code>referenceType</code>.
	 * 
	 * @param applicationId Application id
	 * @param referenceKey Reference key
	 * @param referenceType Reference type
	 * @return A list of application states
	 * @throws DoesNotExistException Thrown if application state does not exist
	 */
	public ApplicationState getApplicationState(String applicationId, String referenceKey, String referenceType) throws DoesNotExistException;

	/**
	 * Creates and returns an application state.
	 * 
	 * @param appState Application state
	 * @return A new application state
	 * @throws AlreadyExistsException Thrown if application state already exists
	 */
	public ApplicationState createApplicationState(ApplicationState appState)
		throws AlreadyExistsException;

	/**
	 * Creates a collection of application states and returns their ids.
	 * 
	 * @param appStateList collection of application states
	 * @return A list of newly created application state ids
	 * @throws AlreadyExistsException Thrown if application state already exists
	 */
	public List<String> createApplicationState(Collection<ApplicationState> appStates)
		throws AlreadyExistsException;
}
