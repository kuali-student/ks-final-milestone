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
package org.kuali.student.enrollment.lpr.conformance.tests;

import org.junit.*;
import org.kuali.student.common.infc.*;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationInfc;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationServiceInfc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.kuali.student.common.dto.AttributeInfo;
import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.mock.LuiPersonRelationStateEnum;
import org.kuali.student.enrollment.lpr.mock.LuiPersonRelationTypeEnum;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * @author nwright
 */
public class LuiPersonRelationServicePersistenceConformanceTest {

	public LuiPersonRelationServicePersistenceConformanceTest() {
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
	private LuiPersonRelationServiceInfc service = null;

	public LuiPersonRelationServiceInfc getService() {
		if (service == null) {
			ApplicationContext appContext =
                new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml", "classpath:testContext.xml"});
			service = (LuiPersonRelationServiceInfc) appContext.getBean("lprServiceToTest");
            System.out.println ("Running LuiPersonRelationServicePersistence Conformance Test on " + service.getClass().getName());
		}
		return service;
	}

	public void setService(LuiPersonRelationServiceInfc service) {
		this.service = service;
	}

	private ContextInfc getContext1() {
		ContextInfc context = new ContextInfo();
		context.setPrincipalId("principalId.1");
		context.setLocaleLanguage("en");
		context.setLocaleRegion("us");
		return context;
	}

	private ContextInfc getContext2() {
		ContextInfc context = new ContextInfo();
		context.setPrincipalId("principalId.2");
		context.setLocaleLanguage("fr");
		context.setLocaleRegion("ca");
		return context;
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
		String relationState = LuiPersonRelationStateEnum.APPLIED.getKey();
		String luiPersonRelationType = LuiPersonRelationTypeEnum.STUDENT.getKey();
		LuiPersonRelationInfc luiPersonRelationInfo = new LuiPersonRelationInfo();
		luiPersonRelationInfo.setEffectiveDate(parseDate("2010-01-01"));
		ContextInfc context = getContext1();

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
		String luiPersonRelationType = LuiPersonRelationTypeEnum.STUDENT.getKey();
		LuiPersonRelationInfc orig = new LuiPersonRelationInfo();
		orig.setState(LuiPersonRelationStateEnum.APPLIED.getKey());
		orig.setEffectiveDate(parseDate("2010-01-01"));
		AttributeInfc da = null;
		List<AttributeInfc> das = new ArrayList();
		da = new AttributeInfo();
		da.setKey("dynamic.attribute.key.1");
		da.setValue("dynamic attribute value 1");
		das.add(da);
		da = new AttributeInfo();
		da.setKey("dynamic.attribute.key.2");
		da.setValue("dynamic attribute value 2a");
		das.add(da);
		da = new AttributeInfo();
		da.setKey("dynamic.attribute.key.2");
		da.setValue("dynamic attribute value 2b");
		das.add(da);
		orig.setAttributes(das);
		ContextInfc context = getContext1();
		Date beforeCreate = new Date();
		String lprId = getService().createLuiPersonRelation(personId, luiId, luiPersonRelationType, orig, context);
		Date afterCreate = new Date();
		assertNotNull(lprId);

		// fetch
		LuiPersonRelationInfc fetched = getService().fetchLUIPersonRelation(lprId, context);
		assertNotSame(orig, fetched);
		assertEquals(lprId, fetched.getId());
		assertEquals(personId, fetched.getPersonId());
		assertEquals(luiId, fetched.getLuiId());
		assertEquals(luiPersonRelationType, fetched.getType());
		assertEquals(orig.getState(), fetched.getState());
		assertEquals(orig.getEffectiveDate(), fetched.getEffectiveDate());
		assertEquals(orig.getExpirationDate(), fetched.getExpirationDate());
		assertEquals(orig.getAttributes().size(), fetched.getAttributes().size());
		assertNotSame(orig.getAttributes(), fetched.getAttributes());

		for (AttributeInfc origDa : orig.getAttributes()) {
			AttributeInfc fetchedDa = findMatching(origDa, fetched.getAttributes());
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
		fetched.setPersonId("personId.2");
		fetched.setLuiId("luiId.2");
		fetched.setState(LuiPersonRelationStateEnum.ADMITTED.getKey());
		fetched.setEffectiveDate(parseDate("2010-01-01"));
		fetched.setExpirationDate(parseDate("2010-02-01"));
	    AttributeInfc newDa = new AttributeInfo();
		newDa.setKey("dynamic.attribute.key.3");
		newDa.setValue("dynamic.attribute.value.3");
		das = new ArrayList<AttributeInfc> (fetched.getAttributes());
		das.add(newDa);
		das.get(1).setValue("dynamic.attribute.value.2C");
		das.remove(0);
		fetched.setAttributes(das);
		context = getContext2();

		Date beforeUpdate = new Date();
		LuiPersonRelationInfc updated = getService().updateLuiPersonRelation(fetched.getId(), fetched, context);
		Date afterUpdate = new Date();
		assertNotSame(fetched, updated);
		assertEquals(fetched.getId(), updated.getId());
		assertEquals(fetched.getPersonId(), updated.getPersonId());
		assertEquals(fetched.getLuiId(), updated.getLuiId());
		assertEquals(fetched.getType(), updated.getType());
		assertEquals(fetched.getPersonId(), updated.getPersonId());
		assertEquals(fetched.getLuiId(), updated.getLuiId());
		assertEquals(fetched.getType(), updated.getType());
		assertEquals(fetched.getState(), updated.getState());
		assertEquals(fetched.getEffectiveDate(), updated.getEffectiveDate());
		assertEquals(fetched.getExpirationDate(), updated.getExpirationDate());
		assertEquals(fetched.getAttributes().size(), updated.getAttributes().size());
		assertNotSame(fetched.getAttributes(), updated.getAttributes());

		for (AttributeInfc fetchedDa : fetched.getAttributes()) {
			AttributeInfc updateDa = findMatching(fetchedDa, updated.getAttributes());
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

		StatusInfc status = getService().deleteLuiPersonRelation(lprId, context);
		assertEquals(Boolean.TRUE, status.isSuccess());

		// fetch
		try {
			fetched = getService().fetchLUIPersonRelation(lprId, context);
			if (fetched == null) {
				fail("returned null from fetch when it should have thrown DoesNotExistException");
			}
			fail("delete did not work");
		} catch (DoesNotExistException ex) {
			// expected
		}

	}

	private AttributeInfc findMatching(AttributeInfc search, List<? extends AttributeInfc> list) {
		// TODO: when AttributeInfo gets it's own ID do the find by ID instead of values
		for (AttributeInfc da : list) {
			if (search.getKey().equals(da.getKey())) {
				if (search.getValue().equals(da.getValue())) {
					return da;
				}
			}
		}
		return null;
	}
// /**
//  * Test of findAllValidLuisForPerson method, of class LuiPersonRelationServiceMockPersistenceImpl.
//  */
// @Test
// public void testFindAllValidLuisForPerson() throws Exception {
//  System.out.println("findAllValidLuisForPerson");
//  String personId = "";
//  String luiPersonRelationType = "";
//  String relationState = "";
//  String atpId = "";
//  ContextInfc context = null;
//  LuiPersonRelationServiceMockPersistenceImpl getService () = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = getService ().findAllValidLuisForPerson(personId, luiPersonRelationType, relationState, atpId, context);
//  assertEquals(expResult, result);
//  // TODO review the generated test code and remove the default call to fail.
//  fail("The test case is a prototype.");
// }
//
// /**
//  * Test of findAllValidPeopleForLui method, of class LuiPersonRelationServiceMockPersistenceImpl.
//  */
// @Test
// public void testFindAllValidPeopleForLui() throws Exception {
//  System.out.println("findAllValidPeopleForLui");
//  String luiId = "";
//  String luiPersonRelationType = "";
//  String relationState = "";
//  ContextInfc context = null;
//  LuiPersonRelationServiceMockPersistenceImpl getService () = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = getService ().findAllValidPeopleForLui(luiId, luiPersonRelationType, relationState, context);
//  assertEquals(expResult, result);
//  // TODO review the generated test code and remove the default call to fail.
//  fail("The test case is a prototype.");
// }
//
// /**
//  * Test of findAllowedRelationStates method, of class LuiPersonRelationServiceMockPersistenceImpl.
//  */
// @Test
// public void testFindAllowedRelationStates() throws Exception {
//  System.out.println("findAllowedRelationStates");
//  String luiPersonRelationType = "";
//  ContextInfc context = null;
//  LuiPersonRelationServiceMockPersistenceImpl getService () = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = getService ().findAllowedRelationStates(luiPersonRelationType, context);
//  assertEquals(expResult, result);
//  // TODO review the generated test code and remove the default call to fail.
//  fail("The test case is a prototype.");
// }
//
// /**
//  * Test of findLuiIdsRelatedToPerson method, of class LuiPersonRelationServiceMockPersistenceImpl.
//  */
// @Test
// public void testFindLuiIdsRelatedToPerson() throws Exception {
//  System.out.println("findLuiIdsRelatedToPerson");
//  String personId = "";
//  String luiPersonRelationType = "";
//  String relationState = "";
//  ContextInfc context = null;
//  LuiPersonRelationServiceMockPersistenceImpl getService () = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = getService ().findLuiIdsRelatedToPerson(personId, luiPersonRelationType, relationState, context);
//  assertEquals(expResult, result);
//  // TODO review the generated test code and remove the default call to fail.
//  fail("The test case is a prototype.");
// }
//
// /**
//  * Test of findLuiPersonRelationIds method, of class LuiPersonRelationServiceMockPersistenceImpl.
//  */
// @Test
// public void testFindLuiPersonRelationIds() throws Exception {
//  System.out.println("findLuiPersonRelationIds");
//  String personId = "";
//  String luiId = "";
//  ContextInfc context = null;
//  LuiPersonRelationServiceMockPersistenceImpl getService () = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = getService ().findLuiPersonRelationIds(personId, luiId, context);
//  assertEquals(expResult, result);
//  // TODO review the generated test code and remove the default call to fail.
//  fail("The test case is a prototype.");
// }
//
// /**
//  * Test of findLuiPersonRelationIdsForLui method, of class LuiPersonRelationServiceMockPersistenceImpl.
//  */
// @Test
// public void testFindLuiPersonRelationIdsForLui() throws Exception {
//  System.out.println("findLuiPersonRelationIdsForLui");
//  String luiId = "";
//  ContextInfc context = null;
//  LuiPersonRelationServiceMockPersistenceImpl getService () = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = getService ().findLuiPersonRelationIdsForLui(luiId, context);
//  assertEquals(expResult, result);
//  // TODO review the generated test code and remove the default call to fail.
//  fail("The test case is a prototype.");
// }
//
// /**
//  * Test of findLuiPersonRelationIdsForPerson method, of class LuiPersonRelationServiceMockPersistenceImpl.
//  */
// @Test
// public void testFindLuiPersonRelationIdsForPerson() throws Exception {
//  System.out.println("findLuiPersonRelationIdsForPerson");
//  String personId = "";
//  ContextInfc context = null;
//  LuiPersonRelationServiceMockPersistenceImpl getService () = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = getService ().findLuiPersonRelationIdsForPerson(personId, context);
//  assertEquals(expResult, result);
//  // TODO review the generated test code and remove the default call to fail.
//  fail("The test case is a prototype.");
// }
//
// /**
//  * Test of findLuiPersonRelationStates method, of class LuiPersonRelationServiceMockPersistenceImpl.
//  */
// @Test
// public void testFindLuiPersonRelationStates() throws Exception {
//  System.out.println("findLuiPersonRelationStates");
//  ContextInfc context = null;
//  LuiPersonRelationServiceMockPersistenceImpl getService () = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = getService ().findLuiPersonRelationStates(context);
//  assertEquals(expResult, result);
//  // TODO review the generated test code and remove the default call to fail.
//  fail("The test case is a prototype.");
// }
//
// /**
//  * Test of findLuiPersonRelationTypes method, of class LuiPersonRelationServiceMockPersistenceImpl.
//  */
// @Test
// public void testFindLuiPersonRelationTypes() throws Exception {
//  System.out.println("findLuiPersonRelationTypes");
//  ContextInfc context = null;
//  LuiPersonRelationServiceMockPersistenceImpl getService () = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = getService ().findLuiPersonRelationTypes(context);
//  assertEquals(expResult, result);
//  // TODO review the generated test code and remove the default call to fail.
//  fail("The test case is a prototype.");
// }
//
// /**
//  * Test of findLuiPersonRelationTypesForLuiPersonRelation method, of class LuiPersonRelationServiceMockPersistenceImpl.
//  */
// @Test
// public void testFindLuiPersonRelationTypesForLuiPersonRelation() throws Exception {
//  System.out.println("findLuiPersonRelationTypesForLuiPersonRelation");
//  String personId = "";
//  String luiId = "";
//  String relationState = "";
//  ContextInfc context = null;
//  LuiPersonRelationServiceMockPersistenceImpl getService () = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = getService ().findLuiPersonRelationTypesForLuiPersonRelation(personId, luiId, relationState, context);
//  assertEquals(expResult, result);
//  // TODO review the generated test code and remove the default call to fail.
//  fail("The test case is a prototype.");
// }
//
// /**
//  * Test of findLuiPersonRelations method, of class LuiPersonRelationServiceMockPersistenceImpl.
//  */
// @Test
// public void testFindLuiPersonRelations() throws Exception {
//  System.out.println("findLuiPersonRelations");
//  String personId = "";
//  String luiId = "";
//  ContextInfc context = null;
//  LuiPersonRelationServiceMockPersistenceImpl getService () = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = getService ().findLuiPersonRelations(personId, luiId, context);
//  assertEquals(expResult, result);
//  // TODO review the generated test code and remove the default call to fail.
//  fail("The test case is a prototype.");
// }
//
// /**
//  * Test of findLuiPersonRelationsByIdList method, of class LuiPersonRelationServiceMockPersistenceImpl.
//  */
// @Test
// public void testFindLuiPersonRelationsByIdList() throws Exception {
//  System.out.println("findLuiPersonRelationsByIdList");
//  List<String> luiPersonRelationIdList = null;
//  ContextInfc context = null;
//  LuiPersonRelationServiceMockPersistenceImpl getService () = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = getService ().findLuiPersonRelationsByIdList(luiPersonRelationIdList, context);
//  assertEquals(expResult, result);
//  // TODO review the generated test code and remove the default call to fail.
//  fail("The test case is a prototype.");
// }
//
// /**
//  * Test of findLuiPersonRelationsForLui method, of class LuiPersonRelationServiceMockPersistenceImpl.
//  */
// @Test
// public void testFindLuiPersonRelationsForLui() throws Exception {
//  System.out.println("findLuiPersonRelationsForLui");
//  String luiId = "";
//  ContextInfc context = null;
//  LuiPersonRelationServiceMockPersistenceImpl getService () = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = getService ().findLuiPersonRelationsForLui(luiId, context);
//  assertEquals(expResult, result);
//  // TODO review the generated test code and remove the default call to fail.
//  fail("The test case is a prototype.");
// }
//
// /**
//  * Test of findLuiPersonRelationsForPerson method, of class LuiPersonRelationServiceMockPersistenceImpl.
//  */
// @Test
// public void testFindLuiPersonRelationsForPerson() throws Exception {
//  System.out.println("findLuiPersonRelationsForPerson");
//  String personId = "";
//  ContextInfc context = null;
//  LuiPersonRelationServiceMockPersistenceImpl getService () = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = getService ().findLuiPersonRelationsForPerson(personId, context);
//  assertEquals(expResult, result);
//  // TODO review the generated test code and remove the default call to fail.
//  fail("The test case is a prototype.");
// }
//
// /**
//  * Test of findOrderedRelationStatesForLuiPersonRelation method, of class LuiPersonRelationServiceMockPersistenceImpl.
//  */
// @Test
// public void testFindOrderedRelationStatesForLuiPersonRelation() throws Exception {
//  System.out.println("findOrderedRelationStatesForLuiPersonRelation");
//  String luiPersonRelationId = "";
//  ContextInfc context = null;
//  LuiPersonRelationServiceMockPersistenceImpl getService () = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = getService ().findOrderedRelationStatesForLuiPersonRelation(luiPersonRelationId, context);
//  assertEquals(expResult, result);
//  // TODO review the generated test code and remove the default call to fail.
//  fail("The test case is a prototype.");
// }
//
// /**
//  * Test of findPersonIdsRelatedToLui method, of class LuiPersonRelationServiceMockPersistenceImpl.
//  */
// @Test
// public void testFindPersonIdsRelatedToLui() throws Exception {
//  System.out.println("findPersonIdsRelatedToLui");
//  String luiId = "";
//  String luiPersonRelationType = "";
//  String relationState = "";
//  ContextInfc context = null;
//  LuiPersonRelationServiceMockPersistenceImpl getService () = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = getService ().findPersonIdsRelatedToLui(luiId, luiPersonRelationType, relationState, context);
//  assertEquals(expResult, result);
//  // TODO review the generated test code and remove the default call to fail.
//  fail("The test case is a prototype.");
// }
//
// /**
//  * Test of findValidRelationStatesForLuiPersonRelation method, of class LuiPersonRelationServiceMockPersistenceImpl.
//  */
// @Test
// public void testFindValidRelationStatesForLuiPersonRelation() throws Exception {
//  System.out.println("findValidRelationStatesForLuiPersonRelation");
//  String personId = "";
//  String luiId = "";
//  String luiPersonRelationType = "";
//  ContextInfc context = null;
//  LuiPersonRelationServiceMockPersistenceImpl getService () = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = getService ().findValidRelationStatesForLuiPersonRelation(personId, luiId, luiPersonRelationType, context);
//  assertEquals(expResult, result);
//  // TODO review the generated test code and remove the default call to fail.
//  fail("The test case is a prototype.");
// }
//
// /**
//  * Test of isRelated method, of class LuiPersonRelationServiceMockPersistenceImpl.
//  */
// @Test
// public void testIsRelated() throws Exception {
//  System.out.println("isRelated");
//  String personId = "";
//  String luiId = "";
//  String luiPersonRelationType = "";
//  String relationState = "";
//  ContextInfc context = null;
//  LuiPersonRelationServiceMockPersistenceImpl getService () = new LuiPersonRelationServiceMockPersistenceImpl();
//  Boolean expResult = null;
//  Boolean result = getService ().isRelated(personId, luiId, luiPersonRelationType, relationState, context);
//  assertEquals(expResult, result);
//  // TODO review the generated test code and remove the default call to fail.
//  fail("The test case is a prototype.");
// }
//
// /**
//  * Test of isValidLuiPersonRelation method, of class LuiPersonRelationServiceMockPersistenceImpl.
//  */
// @Test
// public void testIsValidLuiPersonRelation() throws Exception {
//  System.out.println("isValidLuiPersonRelation");
//  String personId = "";
//  String luiId = "";
//  String luiPersonRelationType = "";
//  String relationState = "";
//  ContextInfc context = null;
//  LuiPersonRelationServiceMockPersistenceImpl getService () = new LuiPersonRelationServiceMockPersistenceImpl();
//  Boolean expResult = null;
//  Boolean result = getService ().isValidLuiPersonRelation(personId, luiId, luiPersonRelationType, relationState, context);
//  assertEquals(expResult, result);
//  // TODO review the generated test code and remove the default call to fail.
//  fail("The test case is a prototype.");
// }
//
// /**
//  * Test of searchForLuiPersonRelationIds method, of class LuiPersonRelationServiceMockPersistenceImpl.
//  */
// @Test
// public void testSearchForLuiPersonRelationIds() throws Exception {
//  System.out.println("searchForLuiPersonRelationIds");
//  LuiPersonRelationCriteriaInfc luiPersonRelationCriteria = null;
//  ContextInfc context = null;
//  LuiPersonRelationServiceMockPersistenceImpl getService () = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = getService ().searchForLuiPersonRelationIds(luiPersonRelationCriteria, context);
//  assertEquals(expResult, result);
//  // TODO review the generated test code and remove the default call to fail.
//  fail("The test case is a prototype.");
// }
//
//
// /**
//  * Test of updateRelationState method, of class LuiPersonRelationServiceMockPersistenceImpl.
//  */
// @Test
// public void testUpdateRelationState() throws Exception {
//  System.out.println("updateRelationState");
//  String luiPersonRelationId = "";
//  LuiPersonRelationStateInfc relationState = null;
//  ContextInfc context = null;
//  LuiPersonRelationServiceMockPersistenceImpl getService () = new LuiPersonRelationServiceMockPersistenceImpl();
//  StatusInfc expResult = null;
//  StatusInfc result = getService ().updateRelationState(luiPersonRelationId, relationState, context);
//  assertEquals(expResult, result);
//  // TODO review the generated test code and remove the default call to fail.
//  fail("The test case is a prototype.");
// }
}
