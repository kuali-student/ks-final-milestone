/**
 * 
 */
package org.kuali.student.poc.context;

import java.io.IOException;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.context.support.GenericApplicationContext;
import org.junit.Test;
/**
 * Test that properties can be read from classpath:application.properties and from
 * an external properties file defined in command line variable
 * ks.application.properties
 * this variable is optional
 * 
 * Add tests for specific properties an a specific external properties file
 * 
 * @author garystruthers
 *
 */
@ContextConfiguration(locations={"/application-context.xml"})
public class TestApplicationContext extends AbstractJUnit4SpringContextTests{
	
	@Test
	public void testApplicationContext() throws IOException {
		SpringContextDebugHelper debugHelper = new SpringContextDebugHelper();
		ApplicationContext applicationContext = this.applicationContext;
		StringBuilder sb = new StringBuilder();
		debugHelper.appendApplicationContextFields(applicationContext, sb);
		System.out.println(sb.toString());
		
		if(applicationContext.getClass().isAssignableFrom(GenericApplicationContext.class)) {
			sb = new StringBuilder();
			debugHelper.appendGenericApplicationContextFields(applicationContext, sb);
			System.out.println(sb.toString());

		}
		sb = new StringBuilder();
		debugHelper.appendBeanNameAndClasses(applicationContext, sb);
		System.out.println(sb.toString());

		sb = new StringBuilder();
		debugHelper.appendPropertyPlaceholderConfigurerFields(applicationContext, sb);
		System.out.println(sb.toString());

		sb = new StringBuilder();
		debugHelper.appendClasspathApplicationPropertiesURL(applicationContext, sb);
		System.out.println(sb.toString());

		sb = new StringBuilder();
		debugHelper.appendAtomikosDataSourceBeanFields(applicationContext, sb);
		System.out.println(sb.toString());

		sb = new StringBuilder();
		debugHelper.appendHibernateJpaVendorAdapterFields(applicationContext, sb);
		System.out.println(sb.toString());

	}
}
