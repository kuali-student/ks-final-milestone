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

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;
import org.kuali.student.poc.common.ws.exceptions.AlreadyExistsException;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.poc.common.ws.exceptions.PermissionDeniedException;
import org.kuali.student.rules.factfinder.dto.FactCriteriaTypeInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactParamDTO;
import org.kuali.student.rules.factfinder.dto.FactResultTypeInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactTypeInfoDTO;
import org.kuali.student.rules.factfinder.service.FactFinderService;

public class TestFactFinderServiceImpl extends AbstractServiceTest {
    @Client(value = "org.kuali.student.rules.factfinder.service.impl.FactFinderServiceImpl", port = "8181")
    public FactFinderService client;

    @Test
    public void testFetchFactType() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        FactTypeInfoDTO factType = client.fetchFactType("fact.earned_credit_list");
        
        assertEquals("fact.earned_credit_list", factType.getName());
        
        FactResultTypeInfoDTO factResultType = factType.getFactResultTypeInfo();        
        assertEquals("resultColumn.credit", factResultType.getResultColumnsMap().get("resultColumn.credit").getKey());
        
        FactCriteriaTypeInfoDTO factCriteriaType = factType.getFactCriteriaTypeInfo();
        assertEquals(3, factCriteriaType.getFactParamMap().size());
        assertTrue(factCriteriaType.getFactParamMap().containsKey("factParam.clusetId"));
        
        FactParamDTO factParam = factCriteriaType.getFactParamMap().get("factParam.excludeCluSet");
        assertEquals(FactParamDTO.FactParamDefTime.DEFINITION, factParam.getDefTime());
    }    

    @Test
    public void testFindFactTypes()  throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        List<FactTypeInfoDTO> factTypeInfoList = client.findFactTypes();
        
        assertEquals(2, factTypeInfoList.size());
        assertEquals("fact.earned_credit_list", factTypeInfoList.get(0).getFactTypeKey());
    }
}