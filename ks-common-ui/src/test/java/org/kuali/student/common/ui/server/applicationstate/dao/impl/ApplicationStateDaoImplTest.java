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

	@Test
	public void testGetApplicationState1() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("key-1", "value-1");
		map.put("key-2", "value-2");
		map.put("key-3", "value-3");
		ApplicationState newAppState = dao.createApplicationState("1", "2", "course", map);
		
		List<ApplicationState> appStateList = dao.getApplicationState("1", "2", "course");
		
		Assert.assertEquals(1, appStateList.size());
		Map<String, String> map2 = appStateList.get(0).getApplicationStateMap();

		Assert.assertEquals(3, map2.size());
		Assert.assertEquals("value-1", map2.get("key-1"));
		Assert.assertEquals("value-2", map2.get("key-2"));
		Assert.assertEquals("value-3", map2.get("key-3"));

		dao.delete(ApplicationState.class, newAppState.getId());
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
	public void testGetApplicationState1_Error_DuplicateKey() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("key-1", "value-1");

		ApplicationState newAppState1 = null;
		
		try {
			newAppState1 = dao.createApplicationState("1", "2", "course", map);
			dao.createApplicationState("1", "2", "course", map);
			
			List<ApplicationState> appStateList = dao.getApplicationState("1", "2", "course");
			Assert.fail("Should have thrown a duplicate key persistence exception");
		} catch(AlreadyExistsException e ) {
			Assert.assertTrue(true);
			Assert.assertTrue(e.getMessage() != null);
		} finally {
			dao.delete(ApplicationState.class, newAppState1.getId());
		}
	}

	@Test
	public void testGetApplicationState1_Error_DuplicateKeyUserId() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("key-1", "value-1");

		ApplicationState newAppState1 = null;
		
		try {
			newAppState1 = dao.createApplicationState("1", "2", "course", "tom", map);
			dao.createApplicationState("1", "2", "course", "tom", map);
			
			List<ApplicationState> appStateList = dao.getApplicationState("1", "2", "course");
			Assert.fail("Should have thrown a duplicate key persistence exception");
		} catch(AlreadyExistsException  e ) {
			Assert.assertTrue(true);
			Assert.assertTrue(e.getMessage() != null);
		} finally {
			dao.delete(ApplicationState.class, newAppState1.getId());
		}
	}

	@Test
	public void testGetApplicationState2() throws Exception {
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
		Map<String, String> map2 = appStateList.get(0).getApplicationStateMap();

		Assert.assertEquals(3, map2.size());
		Assert.assertEquals(list.get(0).getValue(), map2.get("key-1"));
		Assert.assertEquals(list.get(1).getValue(), map2.get("key-2"));
		Assert.assertEquals(list.get(2).getValue(), map2.get("key-3"));

		dao.delete(ApplicationState.class, newAppState.getId());
	}

	@Test
	public void testGetApplicationState3() throws Exception {
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("key-1", "value-1");
		map1.put("key-2", "value-2");
		ApplicationState newAppState1 = dao.createApplicationState("1", "2", "course1", map1);

		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("key-3", "value-3");
		map2.put("key-4", "value-4");
		ApplicationState newAppState2 = dao.createApplicationState("11", "22", "course2", map2);
		
		List<ApplicationState> appStateList1 = dao.getApplicationState("1", "2", "course1");
		Assert.assertEquals(1, appStateList1.size());
		Map<String, String> returnedMap1 = appStateList1.get(0).getApplicationStateMap();
		Assert.assertEquals(2, returnedMap1.size());
		Assert.assertEquals("value-1", returnedMap1.get("key-1"));
		Assert.assertEquals("value-2", returnedMap1.get("key-2"));

		List<ApplicationState> appStateList2 = dao.getApplicationState("11", "22", "course2");
		Assert.assertEquals(1, appStateList2.size());
		Map<String, String> returnedMap2 = appStateList2.get(0).getApplicationStateMap();
		Assert.assertEquals(2, returnedMap2.size());
		Assert.assertEquals("value-3", returnedMap2.get("key-3"));
		Assert.assertEquals("value-4", returnedMap2.get("key-4"));

		dao.delete(newAppState1);
		dao.delete(newAppState2);
	}

	@Test
	public void testGetApplicationState4() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("key-1", "value-1");
		map.put("key-2", "value-2");
		ApplicationState newAppState1 = dao.createApplicationState("1", "2", "course", "tom", map);
		ApplicationState newAppState2 = dao.createApplicationState("1", "2", "course", "dick", map);
		ApplicationState newAppState3 = dao.createApplicationState("1", "2", "course", "harry", map);
		
		List<ApplicationState> appState = dao.getApplicationState("1", "2", "course");
		Assert.assertEquals(3, appState.size());
		Map<String, String> map2 = appState.get(0).getApplicationStateMap();

		Assert.assertEquals(2, map.size());
		Assert.assertEquals("value-1", map2.get("key-1"));
		Assert.assertEquals("value-2", map2.get("key-2"));

		dao.delete(newAppState1);
		dao.delete(newAppState2);
		dao.delete(newAppState3);
	}

	@Test
	public void testGetApplicationStateByAppIdRefIdRefTypeUserId() throws Exception {
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("key-1", "value-1");
		map1.put("key-2", "value-2");
		ApplicationState newAppState1 = dao.createApplicationState("1", "2", "course1", "tom", map1);

		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("key-3", "value-3");
		map2.put("key-4", "value-4");
		ApplicationState newAppState2 = dao.createApplicationState("11", "22", "course2", "jim", map2);
		
		ApplicationState appState1 = dao.getApplicationState("1", "2", "course1", "tom");
		Map<String, String> returnedMap1 = appState1.getApplicationStateMap();
		Assert.assertEquals(2, returnedMap1.size());
		Assert.assertEquals("value-1", returnedMap1.get("key-1"));
		Assert.assertEquals("value-2", returnedMap1.get("key-2"));

		ApplicationState appState2 = dao.getApplicationState("11", "22", "course2", "jim");
		Map<String, String> returnedMap2 = appState2.getApplicationStateMap();
		Assert.assertEquals(2, returnedMap2.size());
		Assert.assertEquals("value-3", returnedMap2.get("key-3"));
		Assert.assertEquals("value-4", returnedMap2.get("key-4"));

		dao.delete(newAppState1);
		dao.delete(newAppState2);
	}

	@Test
	public void testGetApplicationStateByApplicationId() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("key-1", "value-1");
		map.put("key-2", "value-2");
		map.put("key-3", "value-3");
		ApplicationState newAppState1 = dao.createApplicationState("1", "1", "course1", map);
		ApplicationState newAppState2 = dao.createApplicationState("1", "2", "course2", map);
		ApplicationState newAppState3 = dao.createApplicationState("2", "1", "course1", map);
		
		List<ApplicationState> appStateList = dao.getApplicationState("1");
		
		Assert.assertEquals(2, appStateList.size());
		Assert.assertEquals("1", appStateList.get(0).getApplicationId());
		Assert.assertEquals("1", appStateList.get(1).getApplicationId());
		Assert.assertEquals("1", appStateList.get(0).getReferenceKey());
		Assert.assertEquals("2", appStateList.get(1).getReferenceKey());
		Assert.assertEquals("course1", appStateList.get(0).getReferenceType());
		Assert.assertEquals("course2", appStateList.get(1).getReferenceType());

		Map<String, String> map1 = appStateList.get(0).getApplicationStateMap();
		Map<String, String> map2 = appStateList.get(1).getApplicationStateMap();

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
		Map<String, String> map = new HashMap<String, String>();
		map.put("key-1", "value-1");
		ApplicationState newAppState1 = dao.createApplicationState("1", "1", "course1", "wil", map);
		ApplicationState newAppState2 = dao.createApplicationState("1", "2", "course2", "wil", map);
		ApplicationState newAppState3 = dao.createApplicationState("1", "3", "course1", "tom", map);
		ApplicationState newAppState4 = dao.createApplicationState("2", "1", "course1", "wil", map);
		
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

		Map<String, String> map1 = appStateList.get(0).getApplicationStateMap();
		Map<String, String> map2 = appStateList.get(1).getApplicationStateMap();

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
		Map<String, String> map1 = new HashMap<String, String>();
		for(int i=0; i<100; i++) {
			map1.put("key"+i, "value"+i);
		}
		dao.saveApplicationState("1", "2", "course1", "tomp", map1);

		ApplicationState appState = dao.getApplicationState("1", "2", "course1", "tomp");
		Map<String, String> returnedMap1 = appState.getApplicationStateMap();
		Assert.assertEquals(100, returnedMap1.size());
		for(int i=0; i<100; i++) {
			Assert.assertEquals("value"+i, returnedMap1.get("key"+i));
		}
	}	

	@Test
	public void testGetApplicationState_User_Perf2() throws Exception {
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("key-1", "value-1");
		for(int i=0; i<1000; i++) {
			dao.saveApplicationState("appId"+i, "refId"+i, "refType"+i, "tom"+i, map1);
		}
		
		for(int i=0; i<1000; i++) {
			ApplicationState appState = dao.getApplicationState("appId"+i, "refId"+i, "refType"+i, "tom"+i);
			Map<String, String> returnedMap1 = appState.getApplicationStateMap();
			Assert.assertEquals(1, returnedMap1.size());
			Assert.assertEquals("value-1", returnedMap1.get("key-1"));
		}
	}	

	@Test
	public void testGetApplicationState_User_Perf3() throws Exception {
		Map<String, String> map1 = new HashMap<String, String>();
		for(int i=0; i<100; i++) {
			map1.put("key"+i, "value"+i);
		}
		for(int i=0; i<100; i++) {
			dao.saveApplicationState("appId"+i, "refId"+i, "refType"+i, "tom"+i, map1);
		}
		
		for(int i=0; i<100; i++) {
			ApplicationState appState = dao.getApplicationState("appId"+i, "refId"+i, "refType"+i, "tom"+i);
			Map<String, String> returnedMap1 = appState.getApplicationStateMap();
			Assert.assertEquals(100, returnedMap1.size());
			for(int j=0; j<100; j++) {
				Assert.assertEquals("value"+j, returnedMap1.get("key"+j));
			}
		}
	}*/	
}
