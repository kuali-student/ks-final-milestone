package org.kuali.student.common_test_tester;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common_test_tester.support.MyService;


public class ServiceNoDaoTest extends AbstractServiceTest {

	@Client(value="org.kuali.student.common_test_tester.support.MyServiceImpl",additionalContextFile="classpath:test-my-additional-context.xml")
	public MyService client;

	@Test
	public void test1() {
		assertEquals("Echo: test",client.echo("test"));
	}

}