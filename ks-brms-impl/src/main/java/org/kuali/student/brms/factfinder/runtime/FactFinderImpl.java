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

package org.kuali.student.brms.factfinder.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.NoResultException;

import org.kuali.student.brms.factfinder.dao.FactFinderDAO;
import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.factfinder.dto.FactStructureInfo;
import org.kuali.student.brms.factfinder.dto.FactTypeInfo;
import org.kuali.student.brms.factfinder.entity.LUIPerson;
import org.kuali.student.brms.factfinder.util.FactDataSupport;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class FactFinderImpl implements FactFinder {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(FactFinderImpl.class);
    		
    // Hard Code List of Facts
    private FactDataSupport factDataSupport;
    private FactFinderDAO factFinderDAO;

    /**
     * @param factDataSupport
     *            the factDataSupport to set
     */
    public void setFactDataSupport(FactDataSupport factDataSupport) {
        this.factDataSupport = factDataSupport;
    }

    /**
     * @param factFinderDAO
     *            the factFinderDAO to set
     */
    public void setFactFinderDAO(FactFinderDAO factFinderDAO) {
        this.factFinderDAO = factFinderDAO;
    }
    
    @Override
    public FactResultInfo getFact(String factTypeKey, FactStructureInfo factStructure) throws OperationFailedException, DoesNotExistException {
    	if(logger.isInfoEnabled()) {
    		logger.info("\n---------- fetchFact ----------\n" +
    				"factStructureId="+factStructure.getFactStructureId()+
    				"\nfactTypeKey="+factTypeKey+
    				"\nfactStructure.paramValueMap="+factStructure.getParamValueMap()+
    				"\n-----------------------------------------");
    	}
    	FactResultInfo result = new FactResultInfo();
        
        result.setFactResultTypeInfo(getFactType(factTypeKey).getFactResultTypeInfo());

        String studentId = factStructure.getParamValueMap().get("factParam.studentId");
        List<LUIPerson> luiPersonList = factFinderDAO.lookupByStudentId(studentId);

        List<Map<String, String>> resultValueList = new ArrayList<Map<String, String>>();
   
        /*
         * Only Two Fact Type Keys are known currently. 
         * TODO: Change the implementation to a more generic one
         */
        if ("fact.earned_credit_list".equalsIgnoreCase(factTypeKey)) {
        	// Get the set of courses for which credit is computed
            String courseList = factStructure.getParamValueMap().get("factParam.clusetId");
            Set<String> courseSet = getAsSet(courseList);
                        
            // Get the set of courses for which credit
            String courseExcludeList = factStructure.getParamValueMap().get("factParam.excludeCluSet");
            Set<String> courseExcludeSet = getAsSet(courseExcludeList);

            for (LUIPerson lpr : luiPersonList) {
                if (courseSet.contains(lpr.getCluId()) && !courseExcludeSet.contains(lpr.getCluId())) {
                    Map<String, String> resultColumn = new HashMap<String, String>();
                    resultColumn.put("resultColumn.cluId", String.valueOf(lpr.getCluId()));
                    resultColumn.put("resultColumn.credit", String.valueOf(lpr.getCredits()));
                    resultColumn.put("resultColumn.description", String.valueOf(lpr.getDescription()));
                    resultValueList.add(resultColumn);
                }
            }
        } else if ("fact.completed_course_list".equalsIgnoreCase(factTypeKey)) {
            for (LUIPerson lpr : luiPersonList) {
                Map<String, String> resultColumn = new HashMap<String, String>();
                resultColumn.put("resultColumn.cluId", lpr.getCluId());
                resultColumn.put("resultColumn.description", String.valueOf(lpr.getDescription()));
                resultValueList.add(resultColumn);
            }
        } else if ("fact.clusetId".equalsIgnoreCase(factTypeKey)) {
            Map<String, String> resultColumn = new HashMap<String, String>();
            String[] cluSet = factStructure.getParamValueMap().get("factParam.clusetId").split(",");
            for(String cluId : cluSet) {
	            resultColumn.put("resultColumn.cluId", cluId);
	            resultValueList.add(resultColumn);
            }
        }

        result.setResultList(resultValueList);

        if(logger.isInfoEnabled()) {
    		logger.info("resultValueList="+resultValueList);
    	}

        return result;
    }

    @Override
    public FactTypeInfo getFactType(String factTypeKey) throws OperationFailedException, DoesNotExistException {
        // TODO Change hard coded implementation to look at actual known types
        FactTypeInfo result = null;

        try {
            List<FactTypeInfo> factTypeList = factDataSupport.getFactTypeInfoList();
            if (null != factTypeList) {
                for (FactTypeInfo ft : factTypeList) {
                    if (factTypeKey.equals(ft.getId())) {
                        result = ft;
                        break;
                    }
                }
            }
        } catch (NoResultException nre) {
            throw new DoesNotExistException("No fact exists with type key: " + factTypeKey);
        }

        return result;
    }

    @Override
    public List<FactTypeInfo> getFactTypes() throws OperationFailedException {
        // TODO Change from hard coded values to actual persisted implementation
        return factDataSupport.getFactTypeInfoList();
    }

    private Set<String> getAsSet(String list) {
        list = (null == list)? "" : list;
        
        String[] array = list.split(",");
        Set<String> set = new HashSet<String>();
        
        for(String s : array) {
            set.add(s.trim());
        }
        
        return set;
    }
}
