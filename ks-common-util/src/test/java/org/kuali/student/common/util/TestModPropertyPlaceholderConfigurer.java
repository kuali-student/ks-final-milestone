package org.kuali.student.common.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestModPropertyPlaceholderConfigurer {

	@Test
	public void testSystemPropNestedResolution() {
		System.setProperty("sys.username", "Bob ${sys.user.lastname}");
		System.setProperty("sys.obit", "${sys.username} is dead.");
		System.setProperty("otherproperties.filelocation", "test-modprop-otherproperties.properties");
		ApplicationContext context = new ClassPathXmlApplicationContext("test-modprop-context.xml");
		
		String obit = (String)context.getBean("obit");
		
		assertEquals("I read that Bob Smith is dead.", obit);

		String statement2 = (String)context.getBean("statement2");
		
		assertEquals("DOREMI", statement2);
	}
}
