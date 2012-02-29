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

package org.kuali.student.common.ui.server.applicationstate.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.common.ui.server.applicationstate.dao.ApplicationStateDao;
import org.kuali.student.common.ui.server.applicationstate.entity.ApplicationState;
import org.kuali.student.common.ui.server.applicationstate.entity.KeyValuePair;

@PersistenceFileLocation("classpath:META-INF/application-state-persistence.xml")
public class ApplicationStateDaoImplTest extends AbstractTransactionalDaoTest {

	@Dao(value = "org.kuali.student.common.ui.server.applicationstate.dao.impl.ApplicationStateDaoImpl")
	public ApplicationStateDao dao;

	private Map<String, String> getMap(List<KeyValuePair> list) {
		Map<String, String> map = new HashMap<String, String>();
		for(KeyValuePair pair : list) {
			map.put(pair.getKey(), pair.getValue());
		}
		return map;
	}

	private List<KeyValuePair> getKeyValuePairList(int startIndex, int n) {
		List<KeyValuePair> list = new ArrayList<KeyValuePair>();
		for(int i=startIndex; i<(startIndex+n); i++) {
			list.add(new KeyValuePair("key-"+i, "value-"+i));
		}
		return list;
	}
	
	private ApplicationState getApplicationState(String appId, String refKey, String refType, String userId, List<KeyValuePair> list) {
		ApplicationState appState = new ApplicationState();
		appState.setApplicationId(appId);
		appState.setKeyValueList(list);
		appState.setReferenceKey(refKey);
		appState.setReferenceType(refType);
		appState.setUserId(userId);
		return appState;
	}

	@Test
	public void testUpdateApplicationState1() throws Exception {
		List<KeyValuePair> list = getKeyValuePairList(1,3);
		ApplicationState appState = getApplicationState("1", "2", "course", null, list);
		ApplicationState newAppState = dao.createApplicationState(appState);
		
		ApplicationState getAppState = dao.getApplicationState("1", "2", "course");

		list = new ArrayList<KeyValuePair>();
		list.add(new KeyValuePair("k1", "v1"));
		list.add(new KeyValuePair("k2", "v2"));
		getAppState.setKeyValueList(list);
		
		dao.update(getAppState);
		
		getAppState = dao.getApplicationState("1", "2", "course");
		Assert.assertNotNull(getAppState);
		Assert.assertEquals(newAppState.toString(), getAppState.toString());
		Assert.assertEquals(newAppState.getKeyValueList().toString(), getAppState.getKeyValueList().toString());

		ApplicationState getAppState2 = dao.fetch(ApplicationState.class, newAppState.getId());
		Assert.assertEquals(getAppState.toString(), getAppState2.toString());
		Assert.assertEquals(getAppState.getKeyValueList().toString(), getAppState2.getKeyValueList().toString());
		
		dao.delete(ApplicationState.class, newAppState.getId());
	}

	@Test
	public void testUpdateApplicationState2() throws Exception {
		List<KeyValuePair> list = getKeyValuePairList(1,3);
		ApplicationState appState = getApplicationState("1", "2", "course", null, list);
		ApplicationState newAppState = dao.createApplicationState(appState);
		
		ApplicationState getAppState = dao.getApplicationState("1", "2", "course");

		getAppState.setApplicationId("A");
		getAppState.setReferenceKey("B");
		getAppState.setReferenceType("program");
		list = new ArrayList<KeyValuePair>();
		list.add(new KeyValuePair("k1", "v1"));
		list.add(new KeyValuePair("k2", "v2"));
		getAppState.setKeyValueList(list);

		dao.update(getAppState);
		
		getAppState = dao.getApplicationState("A", "B", "program");

		Assert.assertNotNull(getAppState);
		Assert.assertEquals(newAppState.toString(), getAppState.toString());
		Assert.assertEquals(newAppState.getKeyValueList().toString(), getAppState.getKeyValueList().toString());

		ApplicationState getAppState2 = dao.fetch(ApplicationState.class, newAppState.getId());
		Assert.assertEquals(getAppState.toString(), getAppState2.toString());
		Assert.assertEquals(getAppState.getKeyValueList().toString(), getAppState2.getKeyValueList().toString());
		
		dao.delete(ApplicationState.class, newAppState.getId());
	}

	@Test
	public void testCreateGetApplicationState1() throws Exception {
		List<KeyValuePair> list = getKeyValuePairList(1,3);
		ApplicationState appState = getApplicationState("1", "2", "course", null, list);
		ApplicationState newAppState = dao.createApplicationState(appState);
		
		ApplicationState getAppState = dao.getApplicationState("1", "2", "course");
		
		Assert.assertNotNull(getAppState);
		Map<String, String> map2 = getMap(getAppState.getKeyValueList());

		Assert.assertEquals(3, map2.size());
		Assert.assertEquals("value-1", map2.get("key-1"));
		Assert.assertEquals("value-2", map2.get("key-2"));
		Assert.assertEquals("value-3", map2.get("key-3"));

		dao.delete(ApplicationState.class, newAppState.getId());
	}

	@Test
	public void testGetApplicationState_NoApplicationState() throws Exception {
		try {
			dao.getApplicationState("1", "2", "course");
			Assert.fail("Should have thrown an exception for no application state");
		} catch(DoesNotExistException e) {
			Assert.assertNotNull(e);
		}
	}

	@Test
	public void testGetApplicationState_NoApplicationStateforUserId() throws Exception {
		try {
			dao.getApplicationState("1", "2", "course", "wil");
			Assert.fail("Should have thrown an exception for no application state");
		} catch(DoesNotExistException e) {
			Assert.assertNotNull(e);
		}
	}

	@Test
	public void testCreateApplicationState_NullParameters() throws Exception {
		try {
			ApplicationState appState = getApplicationState(null, null, null, null, null);
			dao.createApplicationState(appState);
			Assert.fail("Should have thrown a persistence exception for null parameters");
		} catch(javax.persistence.PersistenceException e) {
			Assert.assertNotNull(e);
		}
	}

	@Test
	public void testCreateApplicationState1_Error_DuplicateKey() throws Exception {
		List<KeyValuePair> list = new ArrayList<KeyValuePair>();
		list.add(new KeyValuePair("key-1", "value-1"));
		ApplicationState appState = getApplicationState("1", "2", "course", null, list);

		ApplicationState newAppState1 = null;
		
		try {
			newAppState1 = dao.createApplicationState(appState);
			dao.createApplicationState(appState);
			dao.getApplicationState("1", "2", "course");
			Assert.fail("Should have thrown a duplicate key persistence exception");
		} catch(AlreadyExistsException e ) {
			Assert.assertTrue(true);
			Assert.assertTrue(e.getMessage() != null);
		} finally {
			dao.delete(ApplicationState.class, newAppState1.getId());
		}
	}

	@Test
	public void testCreateApplicationState1_Error_DuplicateKeyUserId() throws Exception {
		ApplicationState newAppState = null;
		List<KeyValuePair> list = getKeyValuePairList(1,3);
		ApplicationState appState = getApplicationState("1", "2", "course", "tom", list);
		
		try {
			newAppState = dao.createApplicationState(appState);
			dao.createApplicationState(appState);
			dao.getApplicationState("1", "2", "course");
			Assert.fail("Should have thrown a duplicate key persistence exception");
		} catch(AlreadyExistsException  e ) {
			Assert.assertTrue(true);
			Assert.assertTrue(e.getMessage() != null);
		} finally {
			dao.delete(newAppState);
		}
	}

	@Test
	public void testCreateGetApplicationState3() throws Exception {
		List<KeyValuePair> list1 = getKeyValuePairList(1,2);
		ApplicationState appState1 = getApplicationState("1", "2", "course1", null, list1);
		ApplicationState newAppState1 = dao.createApplicationState(appState1);

		List<KeyValuePair> list2 = getKeyValuePairList(3,2);
		ApplicationState appState2 = getApplicationState("11", "22", "course2", null, list2);
		ApplicationState newAppState2 = dao.createApplicationState(appState2);
		
		ApplicationState getAppState1 = dao.getApplicationState("1", "2", "course1");
		Assert.assertNotNull(getAppState1);
		Map<String, String> returnedMap1 = getMap(getAppState1.getKeyValueList());
		Assert.assertEquals(2, returnedMap1.size());
		Assert.assertEquals("value-1", returnedMap1.get("key-1"));
		Assert.assertEquals("value-2", returnedMap1.get("key-2"));

		ApplicationState getAppState2 = dao.getApplicationState("11", "22", "course2");
		Assert.assertNotNull(getAppState2);
		Map<String, String> returnedMap2 = getMap(getAppState2.getKeyValueList());
		Assert.assertEquals(2, returnedMap2.size());
		Assert.assertEquals("value-3", returnedMap2.get("key-3"));
		Assert.assertEquals("value-4", returnedMap2.get("key-4"));

		dao.delete(newAppState1);
		dao.delete(newAppState2);
	}

	@Test
	public void testGetApplicationStateByAppIdRefIdRefTypeUserId() throws Exception {
		List<KeyValuePair> list1 = getKeyValuePairList(1,2);
		ApplicationState appState1 = getApplicationState("1", "2", "course1", "tom", list1);
		ApplicationState newAppState1 = dao.createApplicationState(appState1);

		List<KeyValuePair> list2 = getKeyValuePairList(3,2);
		ApplicationState appState2 = getApplicationState("11", "22", "course2", "jim", list2);
		ApplicationState newAppState2 = dao.createApplicationState(appState2);
		
		ApplicationState as1 = dao.getApplicationState("1", "2", "course1", "tom");
		Map<String, String> returnedMap1 = getMap(as1.getKeyValueList());
		Assert.assertEquals(2, returnedMap1.size());
		Assert.assertEquals("value-1", returnedMap1.get("key-1"));
		Assert.assertEquals("value-2", returnedMap1.get("key-2"));

		ApplicationState as2 = dao.getApplicationState("11", "22", "course2", "jim");
		Map<String, String> returnedMap2 = getMap(as2.getKeyValueList());
		Assert.assertEquals(2, returnedMap2.size());
		Assert.assertEquals("value-3", returnedMap2.get("key-3"));
		Assert.assertEquals("value-4", returnedMap2.get("key-4"));

		dao.delete(newAppState1);
		dao.delete(newAppState2);
	}

/*
	@Test
	public void testGetApplicationState_User_Perf1() throws Exception {
		int n = 100;
		long t1 = System.nanoTime();
		List<KeyValuePair> list1 = getKeyValuePairList(0,n);
		ApplicationState appState1 = getApplicationState("1", "2", "course1", "lisa", list1);
		dao.createApplicationState(appState1);
		long t2 = System.nanoTime();
		long total = (t2-t1)/1000000;
		System.out.println("\ntestGetApplicationState_User_Perf1:createApplicationState(" + n + ") = " + total + " millis");

		t1 = System.nanoTime();
		ApplicationState appState = dao.getApplicationState("1", "2", "course1", "lisa");
		t2 = System.nanoTime();
		total = (t2-t1)/1000000;
		Assert.assertEquals(n, appState.getKeyValueList().size());
		System.out.println("testGetApplicationState_User_Perf1:getApplicationState(" + n + ") = " + total + " millis");
	}	

	@Test
	public void testGetApplicationState_User_Perf2() throws Exception {
		int n = 100;
		long t1 = System.nanoTime();
		for(int i=0; i<n; i++) {
			List<KeyValuePair> list = getKeyValuePairList(1,1);
			ApplicationState appState = getApplicationState("appId"+i, "refId"+i, "refType"+i, "tom"+i, list);
			dao.createApplicationState(appState);
		}
		long t2 = System.nanoTime();
		long total = (t2-t1)/1000000;
		System.out.println("\ntestGetApplicationState_User_Perf2:createApplicationState(" + n + ") = " + total + " millis");
		
		t1 = System.nanoTime();
		for(int i=0; i<n; i++) {
			ApplicationState appState = dao.getApplicationState("appId"+i, "refId"+i, "refType"+i, "tom"+i);
		}
		t2 = System.nanoTime();
		total = (t2-t1)/1000000;
		System.out.println("testGetApplicationState_User_Perf2:getApplicationState(" + n + ") = " + total + " millis");
	}	

	@Test
	public void testGetApplicationState_User_Perf3() throws Exception {
		int n = 100;
		long t1 = System.nanoTime();
		for(int i=0; i<n; i++) {
			List<KeyValuePair> list = getKeyValuePairList(0,n/5);
			ApplicationState appState = getApplicationState("appId"+i, "refId"+i, "refType"+i, "tom"+i, list);
			dao.createApplicationState(appState);
		}
		long t2 = System.nanoTime();
		long total = (t2-t1)/1000000;
		System.out.println("\ntestGetApplicationState_User_Perf3:createApplicationState(" + n + ") = " + total + " millis");
		
		t1 = System.nanoTime();
		for(int i=0; i<n; i++) {
			ApplicationState appState = dao.getApplicationState("appId"+i, "refId"+i, "refType"+i, "tom"+i);
		}
		t2 = System.nanoTime();
		total = (t2-t1)/1000000;
		System.out.println("testGetApplicationState_User_Perf3:getApplicationState(" + n + ") = " + total + " millis");
	}

	@Test
	public void testCreateApplicationStateCollection_Perf4() throws Exception {
		int n = 100;
		List<ApplicationState> newAppStateList = new ArrayList<ApplicationState>();
		for(int i=0; i<n; i++) {
			List<KeyValuePair> list = getKeyValuePairList(1,n/5);
			ApplicationState appState = getApplicationState("appId-1", "refKey"+i, "course"+i, null, list);
			newAppStateList.add(appState);
		}
		long t1 = System.nanoTime();
		List<String> idList = dao.createApplicationState(newAppStateList);
		long t2 = System.nanoTime();
		long total = (t2-t1)/1000000;
		System.out.println("\ntestCreateApplicationStateCollection_Perf4:createApplicationState(" + n + ") = " + total + " millis");
		
		t1 = System.nanoTime();
		List<ApplicationState> appStateList = dao.getApplicationState("appId-1");
		t2 = System.nanoTime();
		total = (t2-t1)/1000000;
		System.out.println("testCreateApplicationStateCollection_Perf4:getApplicationState(" + n + ") = " + total + " millis");
		
		Assert.assertEquals(n, appStateList.size());
		Map<String, String> map2 = getMap(appStateList.get(0).getKeyValueList());

		t1 = System.nanoTime();
		for(String id : idList) {
			dao.delete(ApplicationState.class, id);
		}
		t2 = System.nanoTime();
		total = (t2-t1)/1000000;
		System.out.println("testCreateApplicationStateCollection_Perf4:delete(" + n + ") = " + total + " millis");
	}
*/
}
