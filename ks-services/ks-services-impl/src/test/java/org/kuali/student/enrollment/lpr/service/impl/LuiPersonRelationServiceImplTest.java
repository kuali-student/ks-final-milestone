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

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.infc.ContextInfc;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationTypeInfc;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationServiceInfc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;


/**
 * @Author sambit
 */

public class LuiPersonRelationServiceImplTest {


	public LuiPersonRelationServiceInfc lprService;
	public ApplicationContext appContext;
	public static String principalId = "123";
	public ContextInfc callContext = new ContextInfo();


	public void setLprService(LuiPersonRelationServiceInfc lprService) {
		this.lprService = lprService;
	}
 
	@Before
	public void setUp() {
		principalId = "123";
		appContext = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
		lprService = (LuiPersonRelationServiceInfc) appContext.getBean("lprService");
		callContext.setPrincipalId(principalId);
	}

	
	@After
	public void tearDown() {
	}
	
	@Ignore
	public void testFindLuiPersonRelationTypes() {
		try {
			List<LuiPersonRelationTypeInfc> relationTypes = lprService.findLuiPersonRelationTypes(callContext);
			assertNotNull(relationTypes);
		} catch (Exception ex) {
			fail("exception from service call :" + ex.getMessage());
		}
		
	}

	@Test
	public void testCreateBulkRelationshipsForPerson() {
		try {
			List<String> createResults = lprService.createBulkRelationshipsForPerson(principalId, new ArrayList<String>(), "", "", new LuiPersonRelationInfo(), callContext);
			assertNotNull(createResults);
			assertEquals(1, createResults.size());
		} catch (Exception ex) {
			fail("exception from service call :" + ex.getMessage());
		}


	}


}