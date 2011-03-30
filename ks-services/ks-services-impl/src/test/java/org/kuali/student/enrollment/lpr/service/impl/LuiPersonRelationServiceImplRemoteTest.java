/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.lpr.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationStateInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationTypeInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @Author sambit
 */

public class LuiPersonRelationServiceImplRemoteTest extends AbstractServiceTest {

    @Client(value = "org.kuali.student.enrollment.lpr.service.impl.LuiPersonRelationServiceImpl")
	public LuiPersonRelationService lprService;
	public ApplicationContext appContext;
	public static String principalId = "123";
	public ContextInfo callContext = new ContextInfo.Builder().build();
 
	@Before
	public void setUp() {
		principalId = "123";
		appContext = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
		callContext = new ContextInfo.Builder(callContext).principalId(principalId).build();
	}

	
	@After
	public void tearDown() {
	}
	

	@Test
	public void testFindLuiPersonRelationStates() {
		try {
			List<LuiPersonRelationStateInfo> relationStates = lprService.findLuiPersonRelationStates(callContext);
			assertNull(relationStates); // service currently returns null for empty List
		} catch (Exception ex) {
			fail("exception from service call :" + ex.getMessage());
		}
		
	}

	@Test
	public void testFindAllowedRelationStates() {
		try {
			List<LuiPersonRelationStateInfo> relationStates = lprService.findAllowedRelationStates("kuali.lpr.type.registrant", callContext);
			assertNull(relationStates); // service currently returns null for empty List
		} catch (Exception ex) {
			fail("exception from service call :" + ex.getMessage());
		}
		
	}

	@Test
	public void testFetchLUIPersonRelation() {
		try {
			LuiPersonRelationInfo lpr = lprService.fetchLUIPersonRelation(principalId, callContext);
			assertNull(lpr); 
		} catch (Exception ex) {
			fail("exception from service call :" + ex.getMessage());
		}
		
	}

	@Test
	public void testCreateBulkRelationshipsForPerson() {
		try {
			List<String> createResults = lprService.createBulkRelationshipsForPerson(principalId, new ArrayList<String>(), "", "", new LuiPersonRelationInfo.Builder().build(), callContext);
			assertNotNull(createResults);
			assertEquals(1, createResults.size());
		} catch (Exception ex) {
			fail("exception from service call :" + ex.getMessage());
		}


	}

	@Test
	public void testFindLuiPersonRelationsForLui() {
		try {
			List<LuiPersonRelationInfo> relations = lprService.findLuiPersonRelationsForLui("123", callContext);
			assertNull(relations); // service currently returns null for empty List
		} catch (Exception ex) {
			fail("exception from service call :" + ex.getMessage());
		}
		
	}


}
