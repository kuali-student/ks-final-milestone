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

package org.kuali.student.core.statement.config.context.lu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.statement.entity.ReqComponent;
import org.kuali.student.core.statement.naturallanguage.AbstractContext;
import org.kuali.student.core.statement.naturallanguage.ReqComponentFieldTypeKeys;

public abstract class AbstractLuContext<T> extends AbstractContext<T> {

	private final static Map<String, CluInfo> cluMap = new HashMap<String, CluInfo>();
	private final static Map<String, CluSetInfo> cluSetMap = new HashMap<String, CluSetInfo>();

	/**
	 * Template tokens (keys).
	 */
	protected final static String EXPECTED_VALUE_TOKEN = "expectedValue";
	protected final static String OPERATOR_TOKEN = "relationalOperator";

	/**
	 * Constructor.
	 */
	public AbstractLuContext() {
	}

	public final static void setCluInfo(List<CluInfo> list) {
		for(CluInfo clu : list) {
			cluMap.put(clu.getId(), clu);
		}
	}

	public final static void setCluSetInfo(List<CluSetInfo> list) {
		for(CluSetInfo cluSet : list) {
			cluSetMap.put(cluSet.getId(), cluSet);
		}
	}

	/**
     * Gets a CLU.
     * 
     * @param cluId CLU id
     * @return CLU
     * @throws OperationFailedException If retrieving CLU fails
     */
    public CluInfo getCluInfo(String cluId) throws OperationFailedException {
		try {
			//CluInfo clu = this.luService.getClu(cluId);
			//return clu;
			return cluMap.get(cluId);
		} catch(Exception e) {
			throw new OperationFailedException(e.getMessage(), e);
		}
    }
    
    /**
     * Gets a CLU set.
     * 
     * @param cluSetId CLU set id
     * @return CLU set
     * @throws OperationFailedException If retrieving CLU set fails
     */
    public CluSetInfo getCluSetInfo(String cluSetId) throws OperationFailedException {
		try {
	    	//CluSetInfo cluSet = this.luService.getCluSetInfo(cluSetId);
	    	//return cluSet;
			return cluSetMap.get(cluSetId);
		} catch(Exception e) {
			throw new OperationFailedException(e.getMessage(), e);
		}
    }

    /**
     * Gets the CLU set.
     * 
     * @param cluSetId CLU set id
     * @return CLU set
     * @throws OperationFailedException If building a custom CLU set fails
     */
    public CluSetInfo getCluSet(String cluSetId) throws OperationFailedException {
    	CluSetInfo cluSet = getCluSetInfo(cluSetId);
        List<CluInfo> list = new ArrayList<CluInfo>(cluSet.getCluIds().size());
		for(String cluId : cluSet.getCluIds()) {
        	CluInfo clu = getCluInfo(cluId);
        	list.add(clu);
        }
    	return new CluSetInfo(cluSet.getId(), list);
    }

    /**
     * Gets a new CLU set from comma separated list of CLU ids.
     * 
     * @param cluIds Comma separated list of CLU ids
     * @return A new CLU set
     * @throws OperationFailedException If building a custom CLU set fails
     */
    public CluSetInfo getClusAsCluSet(String cluIds) throws OperationFailedException {
    	String[] cluIdArray = cluIds.split("\\s*,\\s*");
    	List<CluInfo> list = new ArrayList<CluInfo>();
    	for(String cluId : cluIdArray) {
    		CluInfo clu = getCluInfo(cluId);
    		list.add(clu);
    	}
    	return new CluSetInfo(null, list);
    }

    /**
     * Gets a custom CLU set from a requirement component.
     * 
     * @param reqComponent Requirement component
     * @return custom CLU set
     * @throws OperationFailedException If building a custom CLU set fails
     */
    public CluSetInfo getCluSet(ReqComponent reqComponent) throws OperationFailedException {
        Map<String, String> map = getReqComponentFieldMap(reqComponent);
    	CluSetInfo cluSet = null;
    	if(map.containsKey(ReqComponentFieldTypeKeys.CLU_KEY.getKey())) {
        	String cluIds = map.get(ReqComponentFieldTypeKeys.CLU_KEY.getKey());
        	cluSet = getClusAsCluSet(cluIds);
        } else if(map.containsKey(ReqComponentFieldTypeKeys.CLUSET_KEY.getKey())) {
        	String cluSetId = map.get(ReqComponentFieldTypeKeys.CLUSET_KEY.getKey());
            cluSet = getCluSet(cluSetId);
        }
    	return cluSet;
    }

    /**
     * Creates the context map (template data) for the requirement component.
     * Also, adds the field token map to the context map.
     * 
     * @param reqComponent Requirement component
     * @throws DoesNotExistException If CLU, CluSet or relation does not exist
     */
    public Map<String, Object> createContextMap(ReqComponent reqComponent) throws OperationFailedException {
        Map<String, Object> contextMap = new HashMap<String, Object>();
        contextMap.put(FIELDS_TOKEN, getReqComponentFieldMap(reqComponent));
		contextMap.put(EXPECTED_VALUE_TOKEN, getReqComponentFieldValue(reqComponent, ReqComponentFieldTypeKeys.REQUIRED_COUNT_KEY.getKey()));
        contextMap.put(OPERATOR_TOKEN, getReqComponentFieldValue(reqComponent, ReqComponentFieldTypeKeys.OPERATOR_KEY.getKey()));
        return contextMap;
    }
}
