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
package org.kuali.student.enrollment.lpr.service.impl;
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
import org.kuali.student.common.infc.AttributeBean;
import org.kuali.student.common.infc.AttributeInfc;
import org.kuali.student.common.infc.ContextBean;
import static org.junit.Assert.*;
import org.kuali.student.common.infc.ContextInfc;
import org.kuali.student.common.infc.StatusInfc;
import org.kuali.student.common.infc.ValidationResultInfc;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationBean;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationCriteriaInfc;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationInfc;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationServiceInfc;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationStateInfc;

/**
 *
 * @author nwright
 */
public class LuiPersonRelationServiceMockPersistenceImplTest {

 public LuiPersonRelationServiceMockPersistenceImplTest() {
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
 private LuiPersonRelationServiceInfc service = new LuiPersonRelationServiceMockPersistenceImpl ();

 public LuiPersonRelationServiceInfc getService() {
  return service;
 }

 public void setService(LuiPersonRelationServiceInfc service) {
  this.service = service;
 }

 private ContextInfc getContext1() {
  ContextInfc context = new ContextBean();
  context.setPrincipalId("principalId.1");
  context.setLocaleLanguage("en");
  context.setLocaleRegion("us");
  return context;
 }


 private ContextInfc getContext2() {
  ContextInfc context = new ContextBean();
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
  String personId = "person1";
  List<String> luiIdList = new ArrayList();
  luiIdList.add("luiId1");
  luiIdList.add("luiId2");
  luiIdList.add("luiId3");
  String relationState = "relation.state.1";
  String luiPersonRelationType = "person.relation.type.1";
  LuiPersonRelationInfc luiPersonRelationInfo = new LuiPersonRelationBean();
  luiPersonRelationInfo.setEffectiveDate(parseDate("2010-01-01"));
  ContextInfc context = getContext1();

  List<String> lprIds = service.createBulkRelationshipsForPerson(personId, luiIdList, relationState, luiPersonRelationType, luiPersonRelationInfo, context);
  assertEquals(3, lprIds.size());
  Set<String> unique = new HashSet(lprIds.size());
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
  String luiPersonRelationType = "person.relation.type.1";
  LuiPersonRelationInfc orig = new LuiPersonRelationBean();
  orig.setState("relation.state.1");
  orig.setEffectiveDate(parseDate("2010-01-01"));
  AttributeInfc da = null;
  List<AttributeInfc> das = new ArrayList();
  da = new AttributeBean();
  da.setKey("dynamic.attribute.key.1");
  da.setValue("dynamic attribute value 1");
  das.add(da);
  da = new AttributeBean();
  da.setKey("dynamic.attribute.key.2");
  da.setValue("dynamic attribute value 2a");
  das.add(da);
  da = new AttributeBean();
  da.setKey("dynamic.attribute.key.2");
  da.setValue("dynamic attribute value 2b");
  das.add(da);
  orig.setAttributes(das);
  ContextInfc context = getContext1();
  Date beforeCreate = new Date();
  String lprId = service.createLuiPersonRelation(personId, luiId, luiPersonRelationType, orig, context);
  Date afterCreate = new Date();
  assertNotNull(lprId);

  // fetch
  LuiPersonRelationInfc fetched = service.fetchLUIPersonRelation(lprId, context);
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
  assertNotNull (fetched.getMetaInfo().getVersionInd());
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
  fetched.setState("relation.state.2");
  fetched.setEffectiveDate(parseDate ("2010-01-01"));
  fetched.setExpirationDate(parseDate ("2010-02-01"));
  da = new AttributeBean ();
  da.setKey("dynamic.attribute.key.3");
  da.setValue ("dynamic.attribute.value.3");
  fetched.getAttributes().add(da);
  fetched.getAttributes().get(1).setValue("dynamic.attribute.value.2C");
  fetched.getAttributes().remove(0);
  context = getContext2 ();

  Date beforeUpdate = new Date();
  LuiPersonRelationInfc updated = service.updateLuiPersonRelation(fetched.getId(), fetched, context);
  Date afterUpdate = new Date();
  assertNotSame(fetched, updated);
  assertEquals(fetched.getId(), updated.getId());
  assertEquals(fetched.getPersonId(), updated.getPersonId());
  assertEquals(fetched.getLuiId (), updated.getLuiId());
  assertEquals(fetched.getType(), updated.getType());
  assertEquals(fetched.getPersonId(), updated.getPersonId());
  assertEquals(fetched.getLuiId(), updated.getLuiId());
  assertEquals(fetched.getType(), updated.getType());
  assertEquals(fetched.getState(), updated.getState());
  assertEquals(fetched.getEffectiveDate(), updated.getEffectiveDate());
  assertEquals(fetched.getExpirationDate(), updated.getExpirationDate());
  assertEquals(fetched.getAttributes().size(), updated.getAttributes().size());
  assertNotSame(fetched.getAttributes(), updated.getAttributes());

  for (AttributeInfc origDa : fetched.getAttributes()) {
   AttributeInfc fetchedDa = findMatching(origDa, updated.getAttributes());
   assertNotNull(fetchedDa);
   assertNotSame(origDa, fetchedDa);
   assertEquals(origDa.getKey(), fetchedDa.getKey());
   assertEquals(origDa.getValue(), fetchedDa.getValue());
  }
  assertNotNull(updated.getMetaInfo());
  assertEquals (context.getPrincipalId(), updated.getMetaInfo().getUpdateId());
  assertEquals (fetched.getMetaInfo().getCreateId(), updated.getMetaInfo().getCreateId());
  assertEquals (fetched.getMetaInfo ().getCreateTime(), updated.getMetaInfo().getCreateTime ());
  if (updated.getMetaInfo().getUpdateTime().before(beforeUpdate)) {
   fail("Update time before call to create");
  }
  if (updated.getMetaInfo().getUpdateTime().after(afterUpdate)) {
   fail("Update time after call to create");
  }
  assertNotNull (updated.getMetaInfo().getVersionInd());
  if (updated.getMetaInfo().getVersionInd().equals(fetched.getMetaInfo().getVersionInd()))
  {
   fail ("The version ind was not changed by the update");
  }

  StatusInfc status = service.deleteLuiPersonRelation(lprId, context);
  assertEquals (Boolean.TRUE, status.isSuccess());

  // fetch
  try
  {
   fetched = service.fetchLUIPersonRelation(lprId, context);
   if (fetched == null)
   {
    fail ("returned null from fetch when it should have thrown DoesNotExistException");
   }
   fail ("delete did not work");
  }
  catch (DoesNotExistException ex)
  {
   // expected
  }

 }

 private AttributeInfc findMatching(AttributeInfc search, List<AttributeInfc> list) {
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
//  LuiPersonRelationServiceMockPersistenceImpl service = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = service.findAllValidLuisForPerson(personId, luiPersonRelationType, relationState, atpId, context);
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
//  LuiPersonRelationServiceMockPersistenceImpl service = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = service.findAllValidPeopleForLui(luiId, luiPersonRelationType, relationState, context);
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
//  LuiPersonRelationServiceMockPersistenceImpl service = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = service.findAllowedRelationStates(luiPersonRelationType, context);
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
//  LuiPersonRelationServiceMockPersistenceImpl service = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = service.findLuiIdsRelatedToPerson(personId, luiPersonRelationType, relationState, context);
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
//  LuiPersonRelationServiceMockPersistenceImpl service = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = service.findLuiPersonRelationIds(personId, luiId, context);
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
//  LuiPersonRelationServiceMockPersistenceImpl service = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = service.findLuiPersonRelationIdsForLui(luiId, context);
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
//  LuiPersonRelationServiceMockPersistenceImpl service = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = service.findLuiPersonRelationIdsForPerson(personId, context);
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
//  LuiPersonRelationServiceMockPersistenceImpl service = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = service.findLuiPersonRelationStates(context);
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
//  LuiPersonRelationServiceMockPersistenceImpl service = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = service.findLuiPersonRelationTypes(context);
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
//  LuiPersonRelationServiceMockPersistenceImpl service = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = service.findLuiPersonRelationTypesForLuiPersonRelation(personId, luiId, relationState, context);
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
//  LuiPersonRelationServiceMockPersistenceImpl service = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = service.findLuiPersonRelations(personId, luiId, context);
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
//  LuiPersonRelationServiceMockPersistenceImpl service = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = service.findLuiPersonRelationsByIdList(luiPersonRelationIdList, context);
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
//  LuiPersonRelationServiceMockPersistenceImpl service = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = service.findLuiPersonRelationsForLui(luiId, context);
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
//  LuiPersonRelationServiceMockPersistenceImpl service = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = service.findLuiPersonRelationsForPerson(personId, context);
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
//  LuiPersonRelationServiceMockPersistenceImpl service = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = service.findOrderedRelationStatesForLuiPersonRelation(luiPersonRelationId, context);
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
//  LuiPersonRelationServiceMockPersistenceImpl service = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = service.findPersonIdsRelatedToLui(luiId, luiPersonRelationType, relationState, context);
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
//  LuiPersonRelationServiceMockPersistenceImpl service = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = service.findValidRelationStatesForLuiPersonRelation(personId, luiId, luiPersonRelationType, context);
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
//  LuiPersonRelationServiceMockPersistenceImpl service = new LuiPersonRelationServiceMockPersistenceImpl();
//  Boolean expResult = null;
//  Boolean result = service.isRelated(personId, luiId, luiPersonRelationType, relationState, context);
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
//  LuiPersonRelationServiceMockPersistenceImpl service = new LuiPersonRelationServiceMockPersistenceImpl();
//  Boolean expResult = null;
//  Boolean result = service.isValidLuiPersonRelation(personId, luiId, luiPersonRelationType, relationState, context);
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
//  LuiPersonRelationServiceMockPersistenceImpl service = new LuiPersonRelationServiceMockPersistenceImpl();
//  List expResult = null;
//  List result = service.searchForLuiPersonRelationIds(luiPersonRelationCriteria, context);
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
//  LuiPersonRelationServiceMockPersistenceImpl service = new LuiPersonRelationServiceMockPersistenceImpl();
//  StatusInfc expResult = null;
//  StatusInfc result = service.updateRelationState(luiPersonRelationId, relationState, context);
//  assertEquals(expResult, result);
//  // TODO review the generated test code and remove the default call to fail.
//  fail("The test case is a prototype.");
// }

}
