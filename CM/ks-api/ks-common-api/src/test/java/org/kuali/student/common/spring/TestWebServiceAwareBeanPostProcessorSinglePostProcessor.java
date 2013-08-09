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
import org.junit.runners.BlockJUnit4ClassRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * Test using only the {@link WebServiceAwareSpringBeanPostProcessor} class.  
 * 
 * The <em>DictionaryService</em> will be resolved using the normal @Autowired annotation.
 * 
 * The MessageService will be resolved using the KSB lookup from the QName defined on its @WebService annotation.
 * 
 * @author Kuali Student Team
 *
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class TestWebServiceAwareBeanPostProcessorSinglePostProcessor  {

	private static final Logger log = LoggerFactory.getLogger(TestWebServiceAwareBeanPostProcessorSinglePostProcessor.class);
	private ClassPathXmlApplicationContext context;
	
	/**
	 * 
	 */
	public TestWebServiceAwareBeanPostProcessorSinglePostProcessor() {
	}
	
	@Before
	public void onBefore() {
		
		/*
		 * In this case the WebServiceAwareSpringBeanPostProcessor is the only spring bean post processor being used.
		 */
		
		context = new ClassPathXmlApplicationContext("classpath:test-spring-post-processor.xml");
		
		context.registerShutdownHook();
		
	}


	@Test
	public void testDirectDictionaryServiceWithMessageServiceProxy() {
		TestBean bean = context.getBean(TestBean.class);
		
		String dataDictionaryClassName = bean.getDictionaryService().getClass().getName();
		
		Assert.assertTrue(dataDictionaryClassName.equals("org.kuali.student.common.spring.FakeDictionaryServiceDecoratorImpl"));
		
		String messageServiceClassName = bean.getMessageService().getClass().getName();
		
		Assert.assertTrue("class name must contain $Proxy", messageServiceClassName.contains("$Proxy"));
		
	}
}
