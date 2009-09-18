package org.kuali.student.common.ui.server.applicationstate.dao;

import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.server.applicationstate.entity.ApplicationState;
import org.kuali.student.core.dao.CrudDao;
import org.kuali.student.core.dao.SearchableDao;
import org.kuali.student.core.exceptions.AlreadyExistsException;

public interface ApplicationStateDao extends CrudDao, SearchableDao {

	public ApplicationState getApplicationState(String applicationId, String referenceKey, String referenceType, String userId);

	public List<ApplicationState> getApplicationState(String applicationId, String referenceKey, String referenceType);

	public List<ApplicationState> getApplicationState(String applicationId);

	public List<ApplicationState> getApplicationState(String applicationId, String userId);

	public ApplicationState createApplicationState(String applicationId, String referenceKey, String referenceType, Map<String, String> applicationStateMap)
			throws AlreadyExistsException;

	public ApplicationState createApplicationState(String applicationId, String referenceKey, String referenceType, String userId, Map<String, String> applicationStateMap)
			throws AlreadyExistsException;

	public ApplicationState createApplicationState(ApplicationState appState)
			throws AlreadyExistsException;
}
