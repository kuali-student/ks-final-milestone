/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


/**
 * This class tests methods in fact finder service implementation 
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 *
 */
package org.kuali.student.rules.factfinder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.Daos;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.poc.common.ws.exceptions.AlreadyExistsException;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.poc.common.ws.exceptions.PermissionDeniedException;
import org.kuali.student.rules.factfinder.dto.FactCriteriaTypeInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactParamDTO;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactResultTypeInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.factfinder.dto.FactTypeInfoDTO;
import org.kuali.student.rules.factfinder.service.FactFinderService;
import org.kuali.student.rules.internal.common.entity.FactParamDefTimeKey;

@Daos({@Dao(value = "org.kuali.student.rules.factfinder.dao.impl.FactFinderDAOImpl", testDataFile = "classpath:fact-data-beans.xml")})
@PersistenceFileLocation("classpath:META-INF/factfinder-persistence.xml")
public class TestFactFinderServiceImpl extends AbstractServiceTest {
    @Client(value = "org.kuali.student.rules.factfinder.service.impl.FactFinderServiceImpl", port = "8181")
    public FactFinderService client;

    private boolean containsResult(List<Map<String,String>> set, String column, String value) {
    	for(Map<String,String> map : set) {
    		if (map.get(column).equals(value)) {
    			return true;
    		}
    	}
    	return false;
    }

    @Test
    public void testFetchFact_EarnedCreditList() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        
        String factTypeKey = "fact.earned_credit_list";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("factParam.studentId", "student1");
        paramMap.put("factParam.clusetId", "PSYC 200,PSYC 201,PSYC 202");
        paramMap.put("factParam.excludeCluSet", "PSYC 200");
        
        FactStructureDTO factStructureDTO = new FactStructureDTO();
        factStructureDTO.setFactTypeKey(factTypeKey);
        factStructureDTO.setParamValueMap(paramMap);
        
        FactResultDTO result = client.fetchFact(factTypeKey, factStructureDTO);
        
        assertEquals(result.getFactResultTypeInfo().getKey(), "result.courseCreditInfo");
        assertEquals(3, result.getFactResultTypeInfo().getResultColumnsMap().size());
        
        assertEquals(2, result.getResultList().size());
        // Can't assume any order of the result list
        assertTrue(containsResult(result.getResultList(), "resultColumn.credit", "2.5"));
        assertTrue(containsResult(result.getResultList(), "resultColumn.credit", "3.5"));
        assertTrue(containsResult(result.getResultList(), "resultColumn.description", "Psychology 201 (2.5) Contemporary Issues in Psychology"));
        assertTrue(containsResult(result.getResultList(), "resultColumn.description", "Psychology 202 (3.5) Thinking Clearly about Psychology"));
    }
    
    @Test
    public void testFetchFact_CompletedCourseList() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        
        String factTypeKey = "fact.completed_course_list";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("factParam.studentId", "student1");
        //paramMap.put("factParam.clusetId", "PSYC 200,PSYC 201,PSYC 202");
        
        FactStructureDTO factStructureDTO = new FactStructureDTO();
        factStructureDTO.setFactTypeKey(factTypeKey);
        factStructureDTO.setParamValueMap(paramMap);
        
        FactResultDTO result = client.fetchFact(factTypeKey, factStructureDTO);
        
        assertEquals(result.getFactResultTypeInfo().getKey(), "result.completedCourseInfo");
        assertEquals(2, result.getFactResultTypeInfo().getResultColumnsMap().size());
        
        assertEquals(3, result.getResultList().size());
        // Can't assume any order of the result list
        assertTrue(containsResult(result.getResultList(), "resultColumn.cluId", "PSYC 200"));
        assertTrue(containsResult(result.getResultList(), "resultColumn.cluId", "PSYC 201"));
        assertTrue(containsResult(result.getResultList(), "resultColumn.cluId", "PSYC 202"));
        assertTrue(containsResult(result.getResultList(), "resultColumn.description", "Psychology 200 (3) Experimental Psychology and Laboratory"));
        assertTrue(containsResult(result.getResultList(), "resultColumn.description", "Psychology 201 (2.5) Contemporary Issues in Psychology"));
        assertTrue(containsResult(result.getResultList(), "resultColumn.description", "Psychology 202 (3.5) Thinking Clearly about Psychology"));
    }
    
    @Test
    public void testFetchFactType() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        FactTypeInfoDTO factType = client.fetchFactType("fact.earned_credit_list");
        
        assertEquals("fact.earned_credit_list", factType.getName());
        
        FactResultTypeInfoDTO factResultType = factType.getFactResultTypeInfo();        
        assertEquals("resultColumn.credit", factResultType.getResultColumnsMap().get("resultColumn.credit").getKey());
        assertEquals("resultColumn.cluId", factResultType.getResultColumnsMap().get("resultColumn.cluId").getKey());
        assertEquals("resultColumn.description", factResultType.getResultColumnsMap().get("resultColumn.description").getKey());
        
        FactCriteriaTypeInfoDTO factCriteriaType = factType.getFactCriteriaTypeInfo();
        assertEquals(3, factCriteriaType.getFactParamMap().size());
        assertTrue(factCriteriaType.getFactParamMap().containsKey("factParam.clusetId"));
        
        FactParamDTO factParam = factCriteriaType.getFactParamMap().get("factParam.excludeCluSet");
        assertEquals(FactParamDefTimeKey.KUALI_FACT_DEFINITION_TIME_KEY.toString(), factParam.getDefTime());
    }    

    @Test
    public void testFindFactTypes()  throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        List<FactTypeInfoDTO> factTypeInfoList = client.findFactTypes();
        
        assertEquals(2, factTypeInfoList.size());
        assertEquals("fact.earned_credit_list", factTypeInfoList.get(0).getFactTypeKey());
        assertEquals("fact.completed_course_list", factTypeInfoList.get(1).getFactTypeKey());
    }
}