package org.kuali.student.common_test_tester;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.kuali.student.common_test_tester.support.Base;
import org.kuali.student.common_test_tester.support.BaseExtendImpl;
import org.kuali.student.common_test_tester.support.BaseImpl;
import org.kuali.student.common_test_tester.support.BaseExtend;

import org.kuali.student.common_test_tester.support.MyService;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.Daos;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;

@Daos( {
		@Dao(value = "org.kuali.student.common_test_tester.support.MyDaoImpl", testDataFile = "classpath:META-INF/load-my-beans.xml"),
		@Dao("org.kuali.student.common_test_tester.support.OtherDaoImpl") })
@PersistenceFileLocation("classpath:META-INF/test-persistence.xml")
public class ServiceCommonCopyTest extends AbstractServiceTest {

	@Client(value="org.kuali.student.common_test_tester.support.MyServiceImpl",port="9191")
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
	
	@Test
	public void test3() {
		client.setBase(null);
		Base base = client.getBase();
		assertNull(base);
	}
	
	@Test
	public void test4() {
		Base base = new BaseImpl();
		client.setBase(base);
		base = client.getBase();
		assertNotNull(base);
	}
	
	@Test
	public void test5() {
		BaseExtend base = null;
		client.setBase(base);//client passes BaseImpl
		base = (BaseExtend) client.getBase();
		assertNull(base);
	}
	
	@Test
	public void test6() {
		BaseExtend baseExtend = new BaseExtendImpl();
		client.setBase(baseExtend);
		Base base = client.getBase();//Can't cast to BaseExtend		
		assertNotNull(base);
		System.out.println("test6 " + base.getClass().getName());
	}

}
