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

package org.kuali.student.brms.statement.config.context.lu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.brms.statement.entity.ReqComponent;
import org.kuali.student.brms.statement.naturallanguage.AbstractContext;
import org.kuali.student.brms.statement.naturallanguage.util.ReqComponentTypes;

public abstract class AbstractLuContext<T> extends AbstractContext<T> {

	private final static Map<String, CluInfo> cluMap = new HashMap<String, CluInfo>();
	private final static Map<String, CluSetInfo> cluSetMap = new HashMap<String, CluSetInfo>();

	/**
	 * <p>These common shared tokens are needed since velocity doesn't 
	 * allow periods in tokens.</p>
	 * <p>E.g. reqCompFieldType.totalCredits must either be convert to 
	 * totalCredits or reqCompFieldType_totalCredits so a template would look 
	 * like:</p>
	 * <p>'Student must take $totalCredits of MATH 100'</p>
	 * or
	 * <p>'Student must take $reqCompFieldType_totalCredits of MATH 100'</p>
	 */
	protected final static String CLU_TOKEN = "clu";
	protected final static String CLU_SET_TOKEN = "cluSet";

	/*
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
        Map<String, String> map = getReqCompField(reqComponent);
    	CluSetInfo cluSet = null;
    	if(map.containsKey(ReqComponentTypes.ReqCompFieldTypes.CLU_KEY.getKey())) {
        	String cluIds = map.get(ReqComponentTypes.ReqCompFieldTypes.CLU_KEY.getKey());
        	cluSet = getClusAsCluSet(cluIds);
        } else if(map.containsKey(ReqComponentTypes.ReqCompFieldTypes.CLUSET_KEY.getKey())) {
        	String cluSetId = map.get(ReqComponentTypes.ReqCompFieldTypes.CLUSET_KEY.getKey());
            cluSet = getCluSet(cluSetId);
        }
    	return cluSet;
    }
}
