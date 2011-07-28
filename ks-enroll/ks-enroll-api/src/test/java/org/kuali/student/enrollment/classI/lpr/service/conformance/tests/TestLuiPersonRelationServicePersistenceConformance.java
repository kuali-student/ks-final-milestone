/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.classI.lpr.service.conformance.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelation;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;
import org.kuali.student.test.utilities.TestHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author nwright
 */

public class TestLuiPersonRelationServicePersistenceConformance {

	public TestLuiPersonRelationServicePersistenceConformance() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	private LuiPersonRelationService service = null;

	public LuiPersonRelationService getService() {
		if (service == null) {
			ApplicationContext appContext = new ClassPathXmlApplicationContext(
					new String[] { "classpath:ks-mock-lpr-service-configuration.xml" });
			service = (LuiPersonRelationService) appContext
					.getBean("mockLprPersistenceService");
		}
		return service;
	}

	public void setService(LuiPersonRelationService service) {
		this.service = service;
	}

	private Date parseDate(String str) {
		DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		Date date = null;
		try {
			date = df.parse(str);
		} catch (ParseException ex) {
			throw new IllegalArgumentException(str, ex);
		}
		return date;
	}

	/**
	 * Test of createBulkRelationshipsForPerson method,
	 */
	@Test
	public void testCreateBulkRelationshipsForPerson() throws Exception {
		System.out.println("createBulkRelationshipsForPerson");
		String personId = "personId1";
		List<String> luiIdList = new ArrayList<String>();
		luiIdList.add("luiId1");
		luiIdList.add("luiId2");
		luiIdList.add("luiId3");
		String relationState = LuiPersonRelationServiceConstants.APPLIED_STATE_KEY;
		String luiPersonRelationType = LuiPersonRelationServiceConstants.REGISTRANT_TYPE_KEY;
		LuiPersonRelationInfo lprInfo = new LuiPersonRelationInfo();
		lprInfo.setEffectiveDate(parseDate("2010-01-01"));
		ContextInfo context = TestHelper.getContext1();

		List<String> lprIds = getService().createBulkRelationshipsForPerson(
				personId, luiIdList, relationState, luiPersonRelationType,
				lprInfo, context);
		assertEquals(3, lprIds.size());
		Set<String> unique = new HashSet<String>(lprIds.size());
		for (String lprId : lprIds) {
			if (!unique.add(lprId)) {
				fail("lprIds are not unique");
			}
		}
	}

	/**
	 * Test testLuiPersonRelationLifeCycle
	 */
	@Test
	public void testLuiPersonRelationLifeCycle() throws Exception {
		String personId = "personId.1";
		String luiId = "luiId.1";
		String luiPersonRelationType = LuiPersonRelationServiceConstants.REGISTRANT_TYPE_KEY;

		LuiPersonRelationInfo orig = new LuiPersonRelationInfo();
		orig.setPersonId(personId);
		orig.setTypeKey(luiPersonRelationType);
		orig.setLuiId(luiId);
		orig.setStateKey(LuiPersonRelationServiceConstants.APPLIED_STATE_KEY);
		orig.setEffectiveDate(parseDate("2010-01-01"));
		AttributeInfo attInfo = new AttributeInfo();
		List<AttributeInfo> attInfos = new ArrayList<AttributeInfo>();
		attInfo.setKey("dynamic.attribute.key.1");
		attInfo.setValue("dynamic attribute value 1");
		attInfos.add(attInfo);
		attInfo = new AttributeInfo();
		attInfo.setKey("dynamic.attribute.key.2");
		attInfo.setValue("dynamic attribute value 2a");
		attInfos.add(attInfo);
		attInfo = new AttributeInfo();
		attInfo.setKey("dynamic.attribute.key.2");
		attInfo.setValue("dynamic attribute value 2b");
		attInfos.add(attInfo);
		orig.setAttributes(attInfos);
		ContextInfo context = TestHelper.getContext1();
		Date beforeCreate = new Date();
		String lprId = null;
		try {
			lprId = getService().createLuiPersonRelation(personId, luiId,
					luiPersonRelationType, orig, context);
		} catch (DataValidationErrorException ex) {
			System.out.println(ex.getValidationResults().size()
					+ " validation errors found");
			for (ValidationResult vri : ex.getValidationResults()) {
				System.out.println(vri.getElement() + " " + vri.getLevel()
						+ " " + vri.getMessage());
			}
			throw ex;
		}
		Date afterCreate = new Date();
		assertNotNull(lprId);

		// fetch
		LuiPersonRelationInfo fetched = getService().fetchLuiPersonRelation(
				lprId, context);
		assertNotSame(orig, fetched);
		assertEquals(lprId, fetched.getId());
		assertEquals(personId, fetched.getPersonId());
		assertEquals(luiId, fetched.getLuiId());
		assertEquals(luiPersonRelationType, fetched.getTypeKey());
		assertEquals(orig.getStateKey(), fetched.getStateKey());
		assertEquals(orig.getEffectiveDate(), fetched.getEffectiveDate());
		assertEquals(orig.getExpirationDate(), fetched.getExpirationDate());
		assertEquals(orig.getAttributes().size(), fetched.getAttributes()
				.size());
		assertNotSame(orig.getAttributes(), fetched.getAttributes());

		for (Attribute origDa : orig.getAttributes()) {
			AttributeInfo fetchedDa = findMatching(origDa,
					fetched.getAttributes());
			assertNotNull(fetchedDa);
			assertNotSame(origDa, fetchedDa);
			assertEquals(origDa.getKey(), fetchedDa.getKey());
			assertEquals(origDa.getValue(), fetchedDa.getValue());
		}
		assertNotNull(fetched.getMeta());
		assertEquals(context.getPrincipalId(), fetched.getMeta().getCreateId());
		assertEquals(context.getPrincipalId(), fetched.getMeta().getUpdateId());
		assertNotNull(fetched.getMeta().getCreateTime());
		assertNotNull(fetched.getMeta().getUpdateTime());
		assertNotNull(fetched.getMeta().getVersionInd());
		if (fetched.getMeta().getCreateTime().before(beforeCreate)) {
			fail("Create time before call to create");
		}
		if (fetched.getMeta().getUpdateTime().before(beforeCreate)) {
			fail("Update time before call to create");
		}
		if (fetched.getMeta().getCreateTime().after(afterCreate)) {
			fail("Create time after call to create");
		}
		if (fetched.getMeta().getUpdateTime().after(afterCreate)) {
			fail("Update time after call to create");
		}

		// update method
		LuiPersonRelationInfo lpr = new LuiPersonRelationInfo(fetched);
		lpr.setPersonId("personId.2");
		lpr.setLuiId("luiId.2");
		lpr.setStateKey(LuiPersonRelationServiceConstants.ADMITTED_STATE_KEY);
		lpr.setEffectiveDate(parseDate("2010-01-01"));
		lpr.setExpirationDate(parseDate("2010-02-01"));
		attInfo = new AttributeInfo();
		attInfo.setKey("dynamic.attribute.key.3");
		attInfo.setValue("dynamic.attribute.value.3");
		attInfos = new ArrayList<AttributeInfo>(lpr.getAttributes());
		attInfos.add(attInfo);
		AttributeInfo attInfo2 = new AttributeInfo(attInfos.get(1));
		attInfo2.setValue("dynamic.attribute.value.2C");
		attInfos.set(1, attInfo2);
		attInfos.remove(0);
		LuiPersonRelationInfo lpri = new LuiPersonRelationInfo(lpr);
		lpri.setAttributes(attInfos);
		context = TestHelper.getContext2();

		Date beforeUpdate = new Date();
		LuiPersonRelationInfo updated = getService().updateLuiPersonRelation(
				lpri.getId(), lpri, context);
		Date afterUpdate = new Date();
		assertNotSame(lpri, updated);
		assertEquals(lpri.getId(), updated.getId());
		assertEquals(lpri.getPersonId(), updated.getPersonId());
		assertEquals(lpri.getLuiId(), updated.getLuiId());
		assertEquals(lpri.getTypeKey(), updated.getTypeKey());
		assertEquals(lpri.getPersonId(), updated.getPersonId());
		assertEquals(lpri.getLuiId(), updated.getLuiId());
		assertEquals(lpri.getTypeKey(), updated.getTypeKey());
		assertEquals(lpri.getStateKey(), updated.getStateKey());
		assertEquals(lpri.getEffectiveDate(), updated.getEffectiveDate());
		assertEquals(lpri.getExpirationDate(), updated.getExpirationDate());
		assertEquals(lpri.getAttributes().size(), updated.getAttributes()
				.size());
		assertNotSame(lpri.getAttributes(), updated.getAttributes());

		for (AttributeInfo lpriDa : lpri.getAttributes()) {
			AttributeInfo updateDa = findMatching(lpriDa,
					updated.getAttributes());
			assertNotNull(updateDa);
			assertNotSame(lpriDa, updateDa);
			assertEquals(lpriDa.getKey(), updateDa.getKey());
			assertEquals(lpriDa.getValue(), updateDa.getValue());
		}
		assertNotNull(updated.getMeta());
		assertEquals(context.getPrincipalId(), updated.getMeta().getUpdateId());
		assertEquals(lpri.getMeta().getCreateId(), updated.getMeta()
				.getCreateId());
		assertEquals(lpri.getMeta().getCreateTime(), updated.getMeta()
				.getCreateTime());
		if (updated.getMeta().getUpdateTime().before(beforeUpdate)) {
			fail("Update time before call to create");
		}
		if (updated.getMeta().getUpdateTime().after(afterUpdate)) {
			fail("Update time after call to create");
		}
		assertNotNull(updated.getMeta().getVersionInd());
		if (updated.getMeta().getVersionInd()
				.equals(fetched.getMeta().getVersionInd())) {
			fail("The version ind was not changed by the update");
		}

		StatusInfo status = getService()
				.deleteLuiPersonRelation(lprId, context);
		assertEquals(Boolean.TRUE, status.getIsSuccess());

		// fetch
		try {
			fetched = getService().fetchLuiPersonRelation(lprId, context);
			if (fetched == null) {
				fail("returned null from fetch when it should have thrown DoesNotExistException");
			}
			fail("delete did not work");
		} catch (DoesNotExistException ex) {
			// expected
		}

	}

	private AttributeInfo findMatching(Attribute search,
			List<? extends AttributeInfo> list) {
		// TODO: when AttributeInfo gets it's own ID do the find by ID instead
		// of values
		for (AttributeInfo da : list) {
			if (search.getKey().equals(da.getKey())) {
				if (search.getValue().equals(da.getValue())) {
					return da;
				}
			}
		}
		return null;
	}

}
