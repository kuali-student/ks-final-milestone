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

package org.kuali.student.r1.core.statement.config.context.lu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.naturallanguage.AbstractContext;
import org.kuali.student.r1.core.statement.naturallanguage.ReqComponentFieldTypes;
import org.kuali.student.r2.common.dto.ContextInfo;

public abstract class AbstractLuContext<T> extends AbstractContext<T> {

	private final static Map<String, MockCluInfo> cluMap = new HashMap<String, MockCluInfo>();
	private final static Map<String, MockCluSetInfo> cluSetMap = new HashMap<String, MockCluSetInfo>();

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

	public final static void setCluInfo(List<MockCluInfo> list) {
		for(MockCluInfo clu : list) {
			cluMap.put(clu.getId(), clu);
		}
	}

	public final static void setCluSetInfo(List<MockCluSetInfo> list) {
		for(MockCluSetInfo cluSet : list) {
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
    public MockCluInfo getCluInfo(String cluId) throws OperationFailedException {
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
    public MockCluSetInfo getCluSetInfo(String cluSetId) throws OperationFailedException {
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
    public MockCluSetInfo getCluSet(String cluSetId) throws OperationFailedException {
    	MockCluSetInfo cluSet = getCluSetInfo(cluSetId);
        List<MockCluInfo> list = new ArrayList<MockCluInfo>(cluSet.getCluIds().size());
		for(String cluId : cluSet.getCluIds()) {
        	MockCluInfo clu = getCluInfo(cluId);
        	list.add(clu);
        }
    	return new MockCluSetInfo(cluSet.getId(), list);
    }

    /**
     * Gets a new CLU set from comma separated list of CLU ids.
     * 
     * @param cluIds Comma separated list of CLU ids
     * @return A new CLU set
     * @throws OperationFailedException If building a custom CLU set fails
     */
    /*public CluSetInfo getClusAsCluSet(String cluIds) throws OperationFailedException {
    	String[] cluIdArray = cluIds.split("\\s*,\\s*");
    	List<CluInfo> list = new ArrayList<CluInfo>();
    	for(String cluId : cluIdArray) {
    		CluInfo clu = getCluInfo(cluId);
    		list.add(clu);
    	}
    	return new CluSetInfo(null, list);
    }*/
    /**
     * Gets a new CLU set from a CLU id.
     * 
     * @param cluIds CLU id
     * @return A new CLU set
     * @throws OperationFailedException If building a custom CLU set fails
     */
    public MockCluSetInfo getCluAsCluSet(String cluId) throws OperationFailedException {
    	List<MockCluInfo> list = new ArrayList<MockCluInfo>();
		MockCluInfo clu = getCluInfo(cluId);
		list.add(clu);
    	return new MockCluSetInfo(null, list);
    }

    /**
     * Gets a custom CLU set from a requirement component.
     * 
     * @param reqComponent Requirement component
     * @return custom CLU set
     * @throws OperationFailedException If building a custom CLU set fails
     */
    public MockCluSetInfo getCluSet(ReqComponentInfo reqComponent) throws OperationFailedException {
        Map<String, String> map = getReqComponentFieldMap(reqComponent);
    	MockCluSetInfo cluSet = null;
    	if(map.containsKey(ReqComponentFieldTypes.CLU_KEY.getType())) {
        	String cluIds = map.get(ReqComponentFieldTypes.CLU_KEY.getType());
//        	cluSet = getClusAsCluSet(cluIds);
        	cluSet = getCluAsCluSet(cluIds);
        } else if(map.containsKey(ReqComponentFieldTypes.CLUSET_KEY.getType())) {
        	String cluSetId = map.get(ReqComponentFieldTypes.CLUSET_KEY.getType());
            cluSet = getCluSet(cluSetId);
        }
    	return cluSet;
    }

    /**
     * Creates the context map (template data) for the requirement component.
     * Also, adds the field token map to the context map.
     * 
     *
     *
     *
     *
     * @param reqComponent Requirement component
     * @param contextInfo
     * @throws DoesNotExistException If CLU, CluSet or relation does not exist
     */
    public Map<String, Object> createContextMap(ReqComponentInfo reqComponent, ContextInfo contextInfo) throws OperationFailedException {
        Map<String, Object> contextMap = super.createContextMap(reqComponent, contextInfo);
		contextMap.put(EXPECTED_VALUE_TOKEN, getReqComponentFieldValue(reqComponent, ReqComponentFieldTypes.REQUIRED_COUNT_KEY.getType()));
        contextMap.put(OPERATOR_TOKEN, getReqComponentFieldValue(reqComponent, ReqComponentFieldTypes.OPERATOR_KEY.getType()));
        return contextMap;
    }
}
