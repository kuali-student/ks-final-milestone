package org.kuali.student.common_test_tester;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.kuali.student.common_test_tester.support.MyService;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.Daos;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;

@Daos({ @Dao("org.kuali.student.common_test_tester.support.MyDaoImpl"),
		@Dao("org.kuali.student.common_test_tester.support.OtherDaoImpl") })
@PersistenceFileLocation("classpath:META-INF/test-persistence.xml")		
public class ServiceCommonTest extends AbstractServiceTest {

	@Client("org.kuali.student.common_test_tester.support.MyServiceImpl")
	public MyService client;

	@Test
	public void test1()  {
		System.out.println(System.getProperty("ks.test.daoImplClasses"));
		assertNotNull(client.saveString("la la la"));
	}
}
