package org.kuali.student.common_test_tester;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.kuali.student.common_test_tester.support.MyBusinessClass;
import org.kuali.student.poc.common.test.spring.AbstractManagerTest;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.Daos;
import org.kuali.student.poc.common.test.spring.Manager;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;

@PersistenceFileLocation("classpath:META-INF/test-persistence.xml")
@Daos( {
		@Dao(value = "org.kuali.student.common_test_tester.support.MyDaoImpl", testDataFile = "classpath:META-INF/load-my-beans.xml"),
		@Dao("org.kuali.student.common_test_tester.support.OtherDaoImpl") })
public class ManagerCommonTest extends AbstractManagerTest{
	@Manager("org.kuali.student.common_test_tester.support.MyBusinessClassImpl")
	MyBusinessClass manager;
	
	@Test
	public void testManager(){
		int i = manager.doBusinessThing();
		assertEquals(1,i);
	}
	
}
