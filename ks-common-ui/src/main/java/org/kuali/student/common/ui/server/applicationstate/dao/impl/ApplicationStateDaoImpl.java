package org.kuali.student.common.ui.server.applicationstate.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.common.ui.server.applicationstate.dao.ApplicationStateDao;
import org.kuali.student.common.ui.server.applicationstate.entity.ApplicationState;
import org.kuali.student.common.ui.server.applicationstate.entity.KeyValuePair;
import org.kuali.student.core.dao.impl.AbstractSearchableCrudDaoImpl;
import org.kuali.student.core.exceptions.AlreadyExistsException;

public class ApplicationStateDaoImpl extends AbstractSearchableCrudDaoImpl implements ApplicationStateDao {

	private final static String DEFAULT_USER_ID = "APPLICATION";
	
	@PersistenceContext(unitName = "ApplicationState")
	@Override
	public void setEm(EntityManager em) {
		super.setEm(em);
	}

	@Override
	public ApplicationState getApplicationState(String applicationId, String referenceKey, String referenceType, String userId) {
		Query query = em.createNamedQuery("ApplicationState.getApplicationStateByAppRefUserId");
		query.setParameter("applicationId", applicationId);
		query.setParameter("referenceKey", referenceKey);
		query.setParameter("referenceType", referenceType);
		query.setParameter("userId", userId);

		ApplicationState appState = (ApplicationState) query.getSingleResult();
		return appState;
	}

	@Override
	public List<ApplicationState> getApplicationState(String applicationId, String referenceKey, String referenceType) {
		Query query = em.createNamedQuery("ApplicationState.getApplicationState");
		query.setParameter("applicationId", applicationId);
		query.setParameter("referenceKey", referenceKey);
		query.setParameter("referenceType", referenceType);

		@SuppressWarnings("unchecked")
		List<ApplicationState> appStateList = (List<ApplicationState>) query.getResultList();
		return appStateList;
	}

	public List<ApplicationState> getApplicationState(String applicationId) {
		Query query = em.createNamedQuery("ApplicationState.getApplicationStateByAppId");
		query.setParameter("applicationId", applicationId);

		@SuppressWarnings("unchecked")
		List<ApplicationState> appStateList = (List<ApplicationState>) query.getResultList();
		return appStateList;
	}
	
	public List<ApplicationState> getApplicationState(String applicationId, String userId) {
		Query query = em.createNamedQuery("ApplicationState.getApplicationStateByAppUserId");
		query.setParameter("applicationId", applicationId);
		query.setParameter("userId", userId);

		@SuppressWarnings("unchecked")
		List<ApplicationState> appStateList = (List<ApplicationState>) query.getResultList();
		return appStateList;
	}
	
	private List<KeyValuePair> getList(Map<String, String> applicationStateMap) {
		List<KeyValuePair> keyValueList = new ArrayList<KeyValuePair>();
		for(Map.Entry<String, String> entry : applicationStateMap.entrySet()) {
			KeyValuePair pair = new KeyValuePair();
			pair.setKey(entry.getKey());
			pair.setValue(entry.getValue());
			keyValueList.add(pair);
		}
		return keyValueList;
	}
	
	@Override
	public ApplicationState createApplicationState(String applicationId, String referenceKey, String referenceType, Map<String, String> applicationStateMap) throws AlreadyExistsException {
		return createApplicationState(applicationId, referenceKey, referenceType, DEFAULT_USER_ID, applicationStateMap);
	}

	@Override
	public ApplicationState createApplicationState(String applicationId, String referenceKey, String referenceType, String userId,
			Map<String, String> applicationStateMap) throws AlreadyExistsException {

		ApplicationState appState = new ApplicationState();
		appState.setApplicationId(applicationId);
		appState.setReferenceKey(referenceKey);
		appState.setReferenceType(referenceType);
		appState.setUserId(userId);
		List<KeyValuePair> keyValueList = getList(applicationStateMap);
		appState.setKeyValueList(keyValueList);
		
		return createApplicationState(appState);
	}

	public ApplicationState createApplicationState(ApplicationState appState) throws AlreadyExistsException {
		try {
			getApplicationState(appState.getApplicationId(), appState.getReferenceKey(), appState.getReferenceType(), appState.getUserId());
		} catch(NoResultException e) {
			return super.create(appState);
		}
		throw new AlreadyExistsException("Application state already exists");
	}

}
