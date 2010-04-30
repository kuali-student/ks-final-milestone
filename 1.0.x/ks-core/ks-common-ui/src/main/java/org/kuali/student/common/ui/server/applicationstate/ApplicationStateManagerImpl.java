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

package org.kuali.student.common.ui.server.applicationstate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.server.applicationstate.dao.ApplicationStateDao;
import org.kuali.student.common.ui.server.applicationstate.entity.ApplicationState;
import org.kuali.student.common.ui.server.applicationstate.entity.KeyValuePair;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor={Throwable.class})
public class ApplicationStateManagerImpl implements ApplicationStateManager {

    static final String appStateKey = "rationale";
    static final String applicationId = "APPID";
    static final String referenceKey = "refKey";
    static final String referenceType = "CluInfo";
    static final String userId = "Test User";
    //Concerns: When and how application state deletes its data
    
    private ApplicationStateDao applicationStateDao;
    
	/**
	 * Creates or updates a <code>model</code> application state.
	 * 
	 * @param model ModelDTO
	 * @throws Thrown if creating an application state already exists
	 */
	public void createOrUpdateApplicationState(ModelDTO model) throws AlreadyExistsException {
        Map<String, String> applicationStateMap = new HashMap<String, String>();
        applicationStateMap.put(appStateKey, model.getApplicationState(appStateKey));
        
		ApplicationState appState = new ApplicationState();
		appState.setApplicationId(applicationId);
		appState.setKeyValueList(getKeyValuePairList(model));
		appState.setReferenceKey(referenceKey);
		appState.setReferenceType(referenceType);
		appState.setUserId(userId);

    	try {
			ApplicationState as = applicationStateDao.getApplicationState(applicationId, referenceKey, referenceType, userId);
			appState.setId(as.getId());
			appState = applicationStateDao.update(appState);
		} catch (DoesNotExistException e) {
        	appState = applicationStateDao.createApplicationState(appState);
		}
    }
	
	/**
	 * Gets a <code>model</code> application state
	 * @param model
	 * @return
	 * @throws DoesNotExistException Thrown if application state does not exists
	 */
    public ModelDTO getApplicationState(ModelDTO model) throws DoesNotExistException { 
    	ApplicationState appState = applicationStateDao.getApplicationState(applicationId, referenceKey, referenceType, userId);
    	
    	for (KeyValuePair pair : appState.getKeyValueList()) {
    	     model.putApplicationState(pair.getKey(), pair.getValue());
    	}   	
   
        return model;
    }
    
	private List<KeyValuePair> getKeyValuePairList(ModelDTO model) {
		List<KeyValuePair> list = new ArrayList<KeyValuePair>();
		for(Entry<String, String> entry : model.getApplicationStateEntrySet()) {
			list.add(new KeyValuePair(entry.getKey(), entry.getValue()));
		}
		return list;
	}

	public void setApplicationStateDao(ApplicationStateDao applicationStateDao) {
		this.applicationStateDao = applicationStateDao;
	}
}
