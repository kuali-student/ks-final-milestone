package org.kuali.student.common.ui.server.applicationstate.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.common.ui.server.applicationstate.dao.ApplicationStateDao;
import org.kuali.student.common.ui.server.applicationstate.entity.ApplicationState;
import org.kuali.student.common.ui.server.applicationstate.entity.KeyValuePair;
import org.kuali.student.core.exceptions.AlreadyExistsException;

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

	public List<KeyValuePair> getKeyValuePairList(int startIndex, int n) {
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
	public void testCreateGetApplicationState1() throws Exception {
		List<KeyValuePair> list = getKeyValuePairList(1,3);
		ApplicationState appState = getApplicationState("1", "2", "course", null, list);
		ApplicationState newAppState = dao.createApplicationState(appState);
		
		List<ApplicationState> appStateList = dao.getApplicationState("1", "2", "course");
		
		Assert.assertEquals(1, appStateList.size());
		Map<String, String> map2 = getMap(appStateList.get(0).getKeyValueList());

		Assert.assertEquals(3, map2.size());
		Assert.assertEquals("value-1", map2.get("key-1"));
		Assert.assertEquals("value-2", map2.get("key-2"));
		Assert.assertEquals("value-3", map2.get("key-3"));

		dao.delete(ApplicationState.class, newAppState.getId());
	}

	@Test
	public void testCreateGetApplicationStateCollection() throws Exception {
		List<ApplicationState> newAppStateList = new ArrayList<ApplicationState>();
		for(int i=0; i<10; i++) {
			List<KeyValuePair> list = getKeyValuePairList(1,3);
			ApplicationState appState = getApplicationState("appId-1", "refKey"+i, "course"+i, null, list);
			newAppStateList.add(appState);
		}
		List<String> idList = dao.createApplicationState(newAppStateList);
		
		List<ApplicationState> appStateList = dao.getApplicationState("appId-1");
		
		Assert.assertEquals(10, appStateList.size());
		Map<String, String> map2 = getMap(appStateList.get(0).getKeyValueList());

		Assert.assertEquals(3, map2.size());
		Assert.assertEquals("value-1", map2.get("key-1"));
		Assert.assertEquals("value-2", map2.get("key-2"));
		Assert.assertEquals("value-3", map2.get("key-3"));

		for(String id : idList) {
			dao.delete(ApplicationState.class, id);
		}
	}

	@Test
	public void testGetApplicationState_NoApplicationState1() throws Exception {
		List<ApplicationState> appStateList = dao.getApplicationState("1", "2", "course");
		Assert.assertEquals(0, appStateList.size());
	}

	@Test
	public void testGetApplicationState_NoApplicationState2() throws Exception {
		try {
			dao.getApplicationState("1", "2", "course", "wil");
		} catch(javax.persistence.NoResultException e) {
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
	public void testCreateGetApplicationState2() throws Exception {
		List<KeyValuePair> list = new ArrayList<KeyValuePair>();
		list.add(new KeyValuePair("key-1", "value-1"));
		list.add(new KeyValuePair("key-2", "value-2"));
		list.add(new KeyValuePair("key-3", "value-3"));

		ApplicationState appState = new ApplicationState();
		appState.setApplicationId("1");
		appState.setKeyValueList(list);
		appState.setReferenceKey("2");
		appState.setReferenceType("course");
		appState.setUserId("tom");
		ApplicationState newAppState = dao.createApplicationState(appState);
		
		List<ApplicationState> appStateList = dao.getApplicationState("1", "2", "course");
		
		Assert.assertEquals(1, appStateList.size());
		Map<String, String> map2 = getMap(appStateList.get(0).getKeyValueList());

		Assert.assertEquals(3, map2.size());
		Assert.assertEquals(list.get(0).getValue(), map2.get("key-1"));
		Assert.assertEquals(list.get(1).getValue(), map2.get("key-2"));
		Assert.assertEquals(list.get(2).getValue(), map2.get("key-3"));

		dao.delete(ApplicationState.class, newAppState.getId());
	}

	@Test
	public void testCreateGetApplicationState3() throws Exception {
		List<KeyValuePair> list1 = getKeyValuePairList(1,2);
		ApplicationState appState1 = getApplicationState("1", "2", "course1", null, list1);
		ApplicationState newAppState1 = dao.createApplicationState(appState1);

		List<KeyValuePair> list2 = getKeyValuePairList(3,2);
		ApplicationState appState2 = getApplicationState("11", "22", "course2", null, list2);
		ApplicationState newAppState2 = dao.createApplicationState(appState2);
		
		List<ApplicationState> appStateList1 = dao.getApplicationState("1", "2", "course1");
		Assert.assertEquals(1, appStateList1.size());
		Map<String, String> returnedMap1 = getMap(appStateList1.get(0).getKeyValueList());
		Assert.assertEquals(2, returnedMap1.size());
		Assert.assertEquals("value-1", returnedMap1.get("key-1"));
		Assert.assertEquals("value-2", returnedMap1.get("key-2"));

		List<ApplicationState> appStateList2 = dao.getApplicationState("11", "22", "course2");
		Assert.assertEquals(1, appStateList2.size());
		Map<String, String> returnedMap2 = getMap(appStateList2.get(0).getKeyValueList());
		Assert.assertEquals(2, returnedMap2.size());
		Assert.assertEquals("value-3", returnedMap2.get("key-3"));
		Assert.assertEquals("value-4", returnedMap2.get("key-4"));

		dao.delete(newAppState1);
		dao.delete(newAppState2);
	}

	@Test
	public void testCreateGetApplicationState4() throws Exception {
		List<KeyValuePair> list1 = getKeyValuePairList(1, 2);
		List<KeyValuePair> list2 = getKeyValuePairList(1, 2);
		List<KeyValuePair> list3 = getKeyValuePairList(1, 2);
		ApplicationState appState1 = getApplicationState("1", "2", "course", "tom", list1);
		ApplicationState appState2 = getApplicationState("1", "2", "course", "dick", list2);
		ApplicationState appState3 = getApplicationState("1", "2", "course", "harry", list3);
		ApplicationState newAppState1 = dao.createApplicationState(appState1);
		ApplicationState newAppState2 = dao.createApplicationState(appState2);
		ApplicationState newAppState3 = dao.createApplicationState(appState3);
		
		List<ApplicationState> appState = dao.getApplicationState("1", "2", "course");
		Assert.assertEquals(3, appState.size());
		Map<String, String> map = getMap(appState.get(0).getKeyValueList());

		Assert.assertEquals(2, map.size());
		Assert.assertEquals("value-1", map.get("key-1"));
		Assert.assertEquals("value-2", map.get("key-2"));

		dao.delete(newAppState1);
		dao.delete(newAppState2);
		dao.delete(newAppState3);
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

	@Test
	public void testGetApplicationStateByApplicationId() throws Exception {
		List<KeyValuePair> list1 = getKeyValuePairList(1,3);
		List<KeyValuePair> list2 = getKeyValuePairList(1,3);
		List<KeyValuePair> list3 = getKeyValuePairList(1,3);
		ApplicationState appState1 = getApplicationState("1", "1", "course1", null, list1);
		ApplicationState appState2 = getApplicationState("1", "2", "course2", null, list2);
		ApplicationState appState3 = getApplicationState("2", "1", "course1", null, list3);
		ApplicationState newAppState1 = dao.createApplicationState(appState1);
		ApplicationState newAppState2 = dao.createApplicationState(appState2);
		ApplicationState newAppState3 = dao.createApplicationState(appState3);
		
		List<ApplicationState> appStateList = dao.getApplicationState("1");
		
		Assert.assertEquals(2, appStateList.size());
		Assert.assertEquals("1", appStateList.get(0).getApplicationId());
		Assert.assertEquals("1", appStateList.get(1).getApplicationId());
		Assert.assertEquals("1", appStateList.get(0).getReferenceKey());
		Assert.assertEquals("2", appStateList.get(1).getReferenceKey());
		Assert.assertEquals("course1", appStateList.get(0).getReferenceType());
		Assert.assertEquals("course2", appStateList.get(1).getReferenceType());

		Map<String, String> map1 = getMap(appStateList.get(0).getKeyValueList());
		Map<String, String> map2 = getMap(appStateList.get(1).getKeyValueList());

		Assert.assertEquals(3, map1.size());
		Assert.assertEquals("value-1", map1.get("key-1"));
		Assert.assertEquals("value-2", map1.get("key-2"));
		Assert.assertEquals("value-3", map1.get("key-3"));
		Assert.assertEquals(3, map2.size());
		Assert.assertEquals("value-1", map2.get("key-1"));
		Assert.assertEquals("value-2", map2.get("key-2"));
		Assert.assertEquals("value-3", map2.get("key-3"));

		dao.delete(ApplicationState.class, newAppState1.getId());
		dao.delete(ApplicationState.class, newAppState2.getId());
		dao.delete(ApplicationState.class, newAppState3.getId());
	}

	@Test
	public void testGetApplicationStateByApplicationIdUserId() throws Exception {
		List<KeyValuePair> list1 = getKeyValuePairList(1,1);
		List<KeyValuePair> list2 = getKeyValuePairList(1,1);
		List<KeyValuePair> list3 = getKeyValuePairList(1,1);
		List<KeyValuePair> list4 = getKeyValuePairList(1,1);
		ApplicationState appState1 = getApplicationState("1", "1", "course1", "wil", list1);
		ApplicationState appState2 = getApplicationState("1", "2", "course2", "wil", list2);
		ApplicationState appState3 = getApplicationState("1", "3", "course1", "tom", list3);
		ApplicationState appState4 = getApplicationState("2", "1", "course1", "wil", list4);
		ApplicationState newAppState1 = dao.createApplicationState(appState1);
		ApplicationState newAppState2 = dao.createApplicationState(appState2);
		ApplicationState newAppState3 = dao.createApplicationState(appState3);
		ApplicationState newAppState4 = dao.createApplicationState(appState4);
		
		List<ApplicationState> appStateList = dao.getApplicationState("1", "wil");
		
		Assert.assertEquals(2, appStateList.size());
		Assert.assertEquals("1", appStateList.get(0).getApplicationId());
		Assert.assertEquals("1", appStateList.get(1).getApplicationId());
		Assert.assertEquals("1", appStateList.get(0).getReferenceKey());
		Assert.assertEquals("2", appStateList.get(1).getReferenceKey());
		Assert.assertEquals("course1", appStateList.get(0).getReferenceType());
		Assert.assertEquals("course2", appStateList.get(1).getReferenceType());
		Assert.assertEquals("wil", appStateList.get(0).getUserId());
		Assert.assertEquals("wil", appStateList.get(1).getUserId());

		Map<String, String> map1 = getMap(appStateList.get(0).getKeyValueList());
		Map<String, String> map2 = getMap(appStateList.get(1).getKeyValueList());

		Assert.assertEquals(1, map1.size());
		Assert.assertEquals("value-1", map1.get("key-1"));
		Assert.assertEquals(1, map2.size());
		Assert.assertEquals("value-1", map2.get("key-1"));

		dao.delete(ApplicationState.class, newAppState1.getId());
		dao.delete(ApplicationState.class, newAppState2.getId());
		dao.delete(ApplicationState.class, newAppState3.getId());
		dao.delete(ApplicationState.class, newAppState4.getId());
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
