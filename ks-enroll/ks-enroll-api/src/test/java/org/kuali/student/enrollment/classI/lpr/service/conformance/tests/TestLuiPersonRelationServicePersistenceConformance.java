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
			ApplicationContext appContext =
                new ClassPathXmlApplicationContext(new String[]{ "classpath:ks-mock-lpr-service-configuration.xml"});
			service = (LuiPersonRelationService) appContext.getBean("mockLprPersistenceService");
            System.out.println ("Running LuiPersonRelationServicePersistence Conformance Test on " + service.getClass().getName());
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
		LuiPersonRelationInfo.Builder lprBldr = new LuiPersonRelationInfo.Builder();
		lprBldr.setEffectiveDate(parseDate("2010-01-01"));		
		LuiPersonRelationInfo luiPersonRelationInfo = lprBldr.build();
		ContextInfo context = TestHelper.getContext1();

		List<String> lprIds = getService().createBulkRelationshipsForPerson(personId, luiIdList, relationState, luiPersonRelationType, luiPersonRelationInfo, context);
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
		System.out.println("testLuiPersonRelationLifeCycle");
		String personId = "personId.1";
		String luiId = "luiId.1";
		String luiPersonRelationType = LuiPersonRelationServiceConstants.REGISTRANT_TYPE_KEY;

		LuiPersonRelationInfo.Builder orig = new LuiPersonRelationInfo.Builder();
        orig.setPersonId(personId);
        orig.setTypeKey(luiPersonRelationType);
        orig.setLuiId(luiId);
        orig.setStateKey(LuiPersonRelationServiceConstants.APPLIED_STATE_KEY);
        orig.setEffectiveDate(parseDate("2010-01-01"));
		AttributeInfo.Builder da = new AttributeInfo.Builder();
		List<AttributeInfo> das = new ArrayList<AttributeInfo>();
		da.setKey("dynamic.attribute.key.1");
        da.setValue("dynamic attribute value 1");
		das.add(da.build());
		da = new AttributeInfo.Builder();
        da.setKey("dynamic.attribute.key.2");
        da.setValue("dynamic attribute value 2a");
		das.add(da.build());
		da = new AttributeInfo.Builder();
        da.setKey("dynamic.attribute.key.2");
        da.setValue("dynamic attribute value 2b");
		das.add(da.build());
		orig.setAttributes(das);
		// orig.setAttributes(das);
		ContextInfo context = TestHelper.getContext1();
		Date beforeCreate = new Date();
        String lprId = null;
        try {
		 lprId = getService().createLuiPersonRelation(personId, luiId, luiPersonRelationType, orig.build (), context);
        } catch (DataValidationErrorException ex) {
            System.out.println (ex.getValidationResults().size() + " validation errors found");
          for (ValidationResult vri : ex.getValidationResults()) {
              System.out.println (vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
          }
          throw ex;
        }
		Date afterCreate = new Date();
		assertNotNull(lprId);

		// fetch
		LuiPersonRelationInfo fetched = getService().fetchLuiPersonRelation(lprId, context);
		assertNotSame(orig, fetched);
		assertEquals(lprId, fetched.getId());
		assertEquals(personId, fetched.getPersonId());
		assertEquals(luiId, fetched.getLuiId());
		assertEquals(luiPersonRelationType, fetched.getTypeKey());
		assertEquals(orig.getStateKey(), fetched.getStateKey());
		assertEquals(orig.getEffectiveDate(), fetched.getEffectiveDate());
		assertEquals(orig.getExpirationDate(), fetched.getExpirationDate());
		assertEquals(orig.getAttributes().size(), fetched.getAttributes().size());
		assertNotSame(orig.getAttributes(), fetched.getAttributes());

		for (Attribute origDa : orig.getAttributes()) {
			AttributeInfo fetchedDa = findMatching(origDa, fetched.getAttributes());
			assertNotNull(fetchedDa);
			assertNotSame(origDa, fetchedDa);
			assertEquals(origDa.getKey(), fetchedDa.getKey());
			assertEquals(origDa.getValue(), fetchedDa.getValue());
		}
		assertNotNull(fetched.getMetaInfo());
		assertEquals(context.getPrincipalId(), fetched.getMetaInfo().getCreateId());
		assertEquals(context.getPrincipalId(), fetched.getMetaInfo().getUpdateId());
		assertNotNull(fetched.getMetaInfo().getCreateTime());
		assertNotNull(fetched.getMetaInfo().getUpdateTime());
		assertNotNull(fetched.getMetaInfo().getVersionInd());
		if (fetched.getMetaInfo().getCreateTime().before(beforeCreate)) {
			fail("Create time before call to create");
		}
		if (fetched.getMetaInfo().getUpdateTime().before(beforeCreate)) {
			fail("Update time before call to create");
		}
		if (fetched.getMetaInfo().getCreateTime().after(afterCreate)) {
			fail("Create time after call to create");
		}
		if (fetched.getMetaInfo().getUpdateTime().after(afterCreate)) {
			fail("Update time after call to create");
		}

		// update method
		LuiPersonRelationInfo.Builder builder = new LuiPersonRelationInfo.Builder(fetched);
		builder.setPersonId("personId.2");
		builder.setLuiId("luiId.2");
		builder.setStateKey(LuiPersonRelationServiceConstants.ADMITTED_STATE_KEY);
		builder.setEffectiveDate(parseDate("2010-01-01"));
		builder.setExpirationDate(parseDate("2010-02-01"));
		fetched = builder.build();
		AttributeInfo.Builder aBldr = new AttributeInfo.Builder();
		aBldr.setKey("dynamic.attribute.key.3");
		aBldr.setValue("dynamic.attribute.value.3");
	    AttributeInfo newDa = aBldr.build();
		das = new ArrayList<AttributeInfo> (fetched.getAttributes());
		das.add(newDa);
		AttributeInfo.Builder a1bldr = new AttributeInfo.Builder(das.get(1));
		a1bldr.setValue("dynamic.attribute.value.2C");
		das.set(1, a1bldr.build());
		das.remove(0);
		LuiPersonRelationInfo.Builder lfbldr = new LuiPersonRelationInfo.Builder(fetched);
		lfbldr.setAttributes(das);
		fetched = lfbldr.build();
		context = TestHelper.getContext2();

		Date beforeUpdate = new Date();
		LuiPersonRelationInfo updated = getService().updateLuiPersonRelation(fetched.getId(), fetched, context);
		Date afterUpdate = new Date();
		assertNotSame(fetched, updated);
		assertEquals(fetched.getId(), updated.getId());
		assertEquals(fetched.getPersonId(), updated.getPersonId());
		assertEquals(fetched.getLuiId(), updated.getLuiId());
		assertEquals(fetched.getTypeKey(), updated.getTypeKey());
		assertEquals(fetched.getPersonId(), updated.getPersonId());
		assertEquals(fetched.getLuiId(), updated.getLuiId());
		assertEquals(fetched.getTypeKey(), updated.getTypeKey());
		assertEquals(fetched.getStateKey(), updated.getStateKey());
		assertEquals(fetched.getEffectiveDate(), updated.getEffectiveDate());
		assertEquals(fetched.getExpirationDate(), updated.getExpirationDate());
		assertEquals(fetched.getAttributes().size(), updated.getAttributes().size());
		assertNotSame(fetched.getAttributes(), updated.getAttributes());

		for (AttributeInfo fetchedDa : fetched.getAttributes()) {
			AttributeInfo updateDa = findMatching(fetchedDa, updated.getAttributes());
			assertNotNull(updateDa);
			assertNotSame(fetchedDa, updateDa);
			assertEquals(fetchedDa.getKey(), updateDa.getKey());
			assertEquals(fetchedDa.getValue(), updateDa.getValue());
		}
		assertNotNull(updated.getMetaInfo());
		assertEquals(context.getPrincipalId(), updated.getMetaInfo().getUpdateId());
		assertEquals(fetched.getMetaInfo().getCreateId(), updated.getMetaInfo().getCreateId());
		assertEquals(fetched.getMetaInfo().getCreateTime(), updated.getMetaInfo().getCreateTime());
		if (updated.getMetaInfo().getUpdateTime().before(beforeUpdate)) {
			fail("Update time before call to create");
		}
		if (updated.getMetaInfo().getUpdateTime().after(afterUpdate)) {
			fail("Update time after call to create");
		}
		assertNotNull(updated.getMetaInfo().getVersionInd());
		if (updated.getMetaInfo().getVersionInd().equals(fetched.getMetaInfo().getVersionInd())) {
			fail("The version ind was not changed by the update");
		}

		StatusInfo status = getService().deleteLuiPersonRelation(lprId, context);
		assertEquals(Boolean.TRUE, status.isSuccess());

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

	private AttributeInfo findMatching(Attribute search, List<? extends AttributeInfo> list) {
		// TODO: when AttributeInfo gets it's own ID do the find by ID instead of values
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
