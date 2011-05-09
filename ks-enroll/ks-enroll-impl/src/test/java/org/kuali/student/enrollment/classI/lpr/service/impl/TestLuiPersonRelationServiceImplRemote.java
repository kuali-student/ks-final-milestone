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
package org.kuali.student.enrollment.classI.lpr.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.enrollment.classI.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.classI.lpr.service.LuiPersonRelationService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @Author sambit
 */

@Daos( { @Dao(value = "org.kuali.student.enrollment.classI.lpr.dao.LprDao", testSqlFile = "classpath:ks-lpr.sql"),
		 @Dao(value = "org.kuali.student.enrollment.classI.lpr.dao.LprStateDao"),
		 @Dao(value = "org.kuali.student.enrollment.classI.lpr.dao.LprTypeDao") } )
@PersistenceFileLocation("classpath:META-INF/persistence_jta.xml")
public class TestLuiPersonRelationServiceImplRemote extends AbstractServiceTest {

    @Client(value = "org.kuali.student.enrollment.classI.lpr.service.impl.LuiPersonRelationServiceImpl")
	public LuiPersonRelationService lprService;
	public ApplicationContext appContext;
	public static String principalId = "123";
	public ContextInfo callContext = new ContextInfo.Builder().build();
    private static String LUIID2 = "testLuiId2";
    private static String PERSONID2 = "testPersonId2";
 
	@Before
	public void setUp() {
		principalId = "123";
		appContext = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
		callContext = new ContextInfo.Builder(callContext).principalId(principalId).build();
	}

	@Test
	public void testFetchLuiPersonRelation() {
		try {
			LuiPersonRelationInfo lpr = lprService.fetchLuiPersonRelation("testLprId1", callContext);
			assertNotNull(lpr);
			assertEquals("testLuiId1", lpr.getLuiId());
			assertEquals("testPersonId1", lpr.getPersonId());
		} catch (Exception ex) {
			fail("exception from service call :" + ex.getMessage());
		}
		
	}

	@Test
	public void testCreateLuiPersonRelation() {
		LuiPersonRelationInfo.Builder builder = new LuiPersonRelationInfo.Builder();
		builder.setLuiId(LUIID2);
		builder.setPersonId(PERSONID2);
		builder.setTypeKey("kuali.lpr.type.registrant");
		builder.setStateKey("kuali.lpr.state.registered");
		builder.setEffectiveDate(new Date());
		builder.setExpirationDate(DateUtils.addYears(new Date(), 20));
		String lprId = null;
		LuiPersonRelationInfo lpr2 = null;
		try {
			lprId = lprService.createLuiPersonRelation(PERSONID2, LUIID2, "kuali.lpr.type.registrant", builder.build(), callContext);
			assertNotNull(lprId);
			lpr2 = lprService.fetchLuiPersonRelation(lprId, callContext);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertNotNull(lpr2);
		assertEquals(LUIID2, lpr2.getLuiId());
		assertEquals(PERSONID2, lpr2.getPersonId());
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
