package org.kuali.student.common_test_tester;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.common_test_tester.support.MyDao;
import org.kuali.student.common_test_tester.support.Value;

@PersistenceFileLocation("classpath:META-INF/test-persistence.xml")
public class DaoCommonTest extends AbstractTransactionalDaoTest {

	@Dao(value = "org.kuali.student.common_test_tester.support.MyDaoImpl", testDataFile = "classpath:META-INF/load-my-beans.xml", testSqlFile="classpath:test.sql")
	public MyDao myDao;

	public Value value1;
	
	@Test
	public void test1() {
		Value value = new Value("Cheerios");

		String id = myDao.createValue(value);
		//em.flush();//This is needed for eclipselink for some reason
		String result = myDao.findValue(id);
		assertEquals("Cheerios", result);
		
		assertNotNull(myDao.findValueFromValue("Value Number One"));
		assertNotNull(myDao.findValue("11223344-1122-1122-1111-000000000000"));
	}
	
	@Test
	public void test2() {
		Value value = new Value("Cheerios");

		String id = myDao.createValue(value);
		//em.flush();//This is needed for eclipselink for some reason
		String result = myDao.findValue(id);
		assertEquals("Cheerios", result);
		
		assertNotNull(myDao.findValueFromValue("Value Number One"));
		assertNotNull(myDao.findValue("11223344-1122-1122-1111-000000000000"));
	}
}
