/*
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.spring;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * Test using only the {@link WebServiceAwareSpringBeanPostProcessor} class.  
 * 
 * @author Kuali Student Team
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
		
		// this is not a proxy because @Autowired can find the service impl in the applicationContext.
		Assert.assertTrue(dataDictionaryClassName.equals("org.kuali.student.common.spring.FakeDictionaryServiceDecoratorImpl"));
		
		String messageServiceClassName = bean.getMessageService().getClass().getName();
		
		Assert.assertTrue("class name must contain $Proxy", messageServiceClassName.contains("$Proxy"));
		
	}
}
