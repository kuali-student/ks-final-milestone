package org.kuali.student.common.ui.server.applicationstate.dao;

import java.util.Map;

public interface ApplicationStateDAO {
	public Map<String, String> getApplicationState(String applicationId, String referenceKey, String referenceType);
	
	public Map<String, String> getApplicationState(String applicationId, String referenceKey, String referenceType, String userId);

	public void saveApplicationState(String applicationId, String referenceKey, String referenceType, Map<String, String> applicationStateMap);

	public void saveApplicationState(String applicationId, String referenceKey, String referenceType, String userId, Map<String, String> applicationStateMap);
}
