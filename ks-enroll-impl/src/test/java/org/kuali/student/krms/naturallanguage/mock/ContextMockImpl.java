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

package org.kuali.student.krms.naturallanguage.mock;

import org.kuali.rice.krms.api.repository.term.TermDefinitionContract;
import org.kuali.student.r2.core.krms.naturallanguage.TermParameterTypes;
import org.kuali.student.krms.naturallanguage.config.context.BasicContextImpl;
import org.kuali.student.krms.naturallanguage.config.context.util.NLCluSet;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetTreeViewInfo;

import java.util.List;
import java.util.Map;


/**
 * This class creates the template context for course list types.
 */
public class ContextMockImpl extends BasicContextImpl {
	/**
	 * <code>clu</code> token (key) references a Clu object used in templates.
	 * e.g. 'Student must have completed all of 
	 * $clu.getOfficialIdentifier().getShortName()'
	 */
	public final static String CLU_TOKEN = "clu";
	public final static String COURSE_CLU_TOKEN = "courseClu";
	public final static String PROGRAM_CLU_TOKEN = "programClu";
	public final static String TEST_CLU_TOKEN = "testClu";

	/**
	 * <code>cluSet</code> token (key) references a Clu set object
	 * used in templates.
	 * e.g. 'Student must have completed all of $cluSet.getCluSetAsCode()'
	 */
	public final static String CLU_SET_TOKEN = "cluSet";
	public final static String COURSE_CLU_SET_TOKEN = "courseCluSet";
	public final static String PROGRAM_CLU_SET_TOKEN = "programCluSet";
	public final static String TEST_CLU_SET_TOKEN = "testCluSet";

	/**
     * Gets a CLU.
     *
     * @param cluId CLU id
     * @return CLU
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException If retrieving CLU fails
     */
    public CluInfo getCluInfo(String cluId, ContextInfo contextInfo) throws OperationFailedException {
		if (cluId == null) {
			return null;
		}
		return new CluInfo();  //TODO populate fields if needed
    }

    private CluInfo getClu(Map<String, Object> parameters, String key, ContextInfo contextInfo) throws OperationFailedException {
        if(parameters.containsKey(key)) {
	    	String cluId = (String) parameters.get(key);
	    	return getCluInfo(cluId, contextInfo);
        }
        return null;
    }

    /**
     * Gets a CLU set.
     *
     * @param cluSetId CLU set id
     * @return CLU set
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException If retrieving CLU set fails
     */
    public CluSetInfo getCluSetInfo(String cluSetId, ContextInfo contextInfo) throws OperationFailedException {
		if (cluSetId == null) {
			return null;
		}
		return new CluSetInfo();   //TODO populate fields if needed
    }

    /**
     * Gets the CLU set.
     *
     * @param cluSetId CLU set id
     * @return CLU set
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException If building a custom CLU set fails
     */
    public NLCluSet getCluSet(String cluSetId, ContextInfo contextInfo) throws OperationFailedException {
		if (cluSetId == null) {
			return null;
		}
    	CluSetInfo cluSet = getCluSetInfo(cluSetId, contextInfo);
//		try {
//	    	List<CluInfo> list = new ArrayList<CluInfo>();
//	    	CluSetTreeViewInfo tree = cluService.getCluSetTreeView(cluSetId, contextInfo);
//	    	findClusInCluSet(tree, list);
//	    	return new NLCluSet(cluSet.getId(), list, cluSet);
//		} catch(Exception e) {
//			throw new OperationFailedException(e.getMessage(), e);
//		}
        return new NLCluSet(cluSet.getId(),null,cluSet); //TODO populate fields properly if needed
    }

    private static void findClusInCluSet(CluSetTreeViewInfo tree, List<CluInfo> cluList) {
    	if (tree.getCluSets() != null) {
			for (CluSetTreeViewInfo cluSet : tree.getCluSets()) {
				findClusInCluSet(cluSet, cluList);
			}
		} else {
			for (CluInfo clu : tree.getClus()) {
				if (!containsClu(cluList, clu)) {
					cluList.add(clu);
				}
			}
		}
	}

	private static boolean containsClu(List<CluInfo> cluList, CluInfo clu) {
		for (CluInfo clu2 : cluList) {
			if (clu2.getId().equals(clu.getId())) {
				return true;
			}
		}
		return false;
	}

    /**
     * Gets a custom CLU set from a requirement component.
     *
     * @param parameters
     * @return custom CLU set
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException If building a custom CLU set fails
     */
    public NLCluSet getCluSet(Map<String, Object> parameters, String key, ContextInfo contextInfo) throws OperationFailedException {
        NLCluSet cluSet = null;
    	if(parameters.containsKey(key)) {
        	String cluSetId = (String) parameters.get(key);
            cluSet = getCluSet(cluSetId, contextInfo);
        }
    	return cluSet;
    }

    /**
     * Creates the context map (template data) for the requirement component.
     *
     *
     * @param term Requirement component
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException Creating context map fails
     */
    @Override
    public Map<String, Object> createContextMap(Map<String, Object> parameters, ContextInfo contextInfo) throws OperationFailedException {
        Map<String, Object> contextMap = super.createContextMap(parameters, contextInfo);

        CluInfo clu = getClu(parameters, TermParameterTypes.CLU_KEY.getId(), contextInfo);
        if(clu != null) {
	        contextMap.put(CLU_TOKEN, clu);
        }
        CluInfo courseClu = getClu(parameters, TermParameterTypes.COURSE_CLU_KEY.getId(), contextInfo);
        if(courseClu != null) {
	        contextMap.put(COURSE_CLU_TOKEN, courseClu);
        }
        CluInfo programClu = getClu(parameters, TermParameterTypes.PROGRAM_CLU_KEY.getId(), contextInfo);
        if(programClu != null) {
	        contextMap.put(PROGRAM_CLU_TOKEN, programClu);
        }
        CluInfo testClu = getClu(parameters, TermParameterTypes.TEST_CLU_KEY.getId(), contextInfo);
        if(testClu != null) {
	        contextMap.put(TEST_CLU_TOKEN, testClu);
        }

        NLCluSet cluSet = getCluSet(parameters, TermParameterTypes.CLUSET_KEY.getId(), contextInfo);
        if(cluSet != null) {
        	contextMap.put(CLU_SET_TOKEN, cluSet);
        }
        NLCluSet courseCluSet = getCluSet(parameters, TermParameterTypes.COURSE_CLUSET_KEY.getId(), contextInfo);
        if(courseCluSet != null) {
        	contextMap.put(COURSE_CLU_SET_TOKEN, courseCluSet);
        }
        NLCluSet programCluSet = getCluSet(parameters, TermParameterTypes.PROGRAM_CLUSET_KEY.getId(), contextInfo);
        if(programCluSet != null) {
        	contextMap.put(PROGRAM_CLU_SET_TOKEN, programCluSet);
        }
        NLCluSet testCluSet = getCluSet(parameters, TermParameterTypes.TEST_CLUSET_KEY.getId(), contextInfo);
        if(testCluSet != null) {
        	contextMap.put(TEST_CLU_SET_TOKEN, testCluSet);
        }

        return contextMap;
    }
}
