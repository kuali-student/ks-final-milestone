package org.kuali.student.common_test_tester;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.common_test_tester.support.MyService;

@Daos( {
		@Dao(value = "org.kuali.student.common_test_tester.support.MyDaoImpl", testDataFile = "classpath:META-INF/load-my-beans.xml"),
		@Dao("org.kuali.student.common_test_tester.support.OtherDaoImpl") })
@PersistenceFileLocation("classpath:META-INF/test-persistence.xml")
public class ServiceCommonCopyTest extends AbstractServiceTest {

	@Client(value="org.kuali.student.common_test_tester.support.MyServiceImpl",port="9191",additionalContextFile="classpath:test-my-additional-context.xml")
	public MyService client;

	@Test
	public void test1() {
		System.out.println(System.getProperty("ks.test.daoImplClasses"));
		assertNotNull(client.saveString("la la la"));
	}
	
	@Test
	public void test2() {
		String id = client.saveString("Value Number One");
		assertNotNull(client.findStringId(id));
	}
}
