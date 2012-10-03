/**
 * 
 */
package org.kuali.student.common.spring;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * Test using only the {@link WebServiceAwareSpringBeanPostProcessor} class.  In this case both services in the test bean should be proxies.
 * 
 * @author Kuali Student
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations="classpath:test-spring-post-processor.xml")
public class TestWebServiceAwareBeanPostProcessorMultiProcessors {

	private static final Logger log = LoggerFactory.getLogger(TestWebServiceAwareBeanPostProcessorMultiProcessors.class);
	
	@Autowired
	private AbstractBeanFactory context;
	
	/**
	 * 
	 */
	public TestWebServiceAwareBeanPostProcessorMultiProcessors() {
	}
	
	@Before
	public void before() {
		
		/*
		 * In this case the WebServiceAwareSpringBeanPostProcessor is the only spring bean post processor being used.
		 * 
		 * Normally when running from the SpringJUnit4ClassRunner a whole bunch of other bean post processors are configured.
		 * 
		 * This causes only our post processor to be used.
		 * 
		 */
		
		context.getBeanPostProcessors().clear();
		context.addBeanPostProcessor(new WebServiceAwareSpringBeanPostProcessor());
		
		
	}
	
	@Test
	public void testDirectDictionaryServiceWithMessageServiceProxy() {
	
		TestBean bean = context.getBean(TestBean.class);
		
		String dataDictionaryClassName = bean.getDictionaryService().getClass().getName();
		
		Assert.assertTrue(dataDictionaryClassName.equals("org.kuali.student.common.spring.FakeDictionaryServiceImpl"));
		
		String messageServiceClassName = bean.getMessageService().getClass().getName();
		
		Assert.assertTrue(messageServiceClassName.startsWith("$Proxy"));
		
	}
}
