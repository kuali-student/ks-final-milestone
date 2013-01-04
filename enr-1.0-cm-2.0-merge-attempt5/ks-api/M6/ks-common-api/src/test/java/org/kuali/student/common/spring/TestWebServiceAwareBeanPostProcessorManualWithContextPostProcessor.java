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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.kuali.student.r2.common.messages.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * Test using only the {@link WebServiceAwareSpringBeanPostProcessor} class.
 * 
 * This tests with an application context using the <context:annotation-config/>
 * element.
 * 
 * It should result in only the course offering service being KSB proxied.
 * 
 * @author Kuali Student Team
 * 
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class TestWebServiceAwareBeanPostProcessorManualWithContextPostProcessor {

	private static final Logger log = LoggerFactory
	        .getLogger(TestWebServiceAwareBeanPostProcessorManualWithContextPostProcessor.class);
	private ClassPathXmlApplicationContext context;

	/**
	 * 
	 */
	public TestWebServiceAwareBeanPostProcessorManualWithContextPostProcessor() {
	}

	@Before
	public void onBefore() {

		/*
		 * In this case the <context:annotation-config/> is defined which brings in the default spring bean post processors.
		 * 
		 * The WebServiceAwareSpringBeanPostProcessor should sit in front of them and resolve the beans using the @WebService annotation where not automatically processed 
		 * using the normal @Autowired behaviour.
		 */
		context = new ClassPathXmlApplicationContext(
		        "classpath:test-spring-post-processor-with-annotation-config.xml");

		context.registerShutdownHook();

	}

	@Test
	public void testDirectDictionaryServiceWithMessageServiceProxy() {

		TestBean bean = context.getBean(TestBean.class);

		String dataDictionaryClassName = bean.getDictionaryService().getClass().getName();
		
		Assert.assertTrue(dataDictionaryClassName.equals("org.kuali.student.common.spring.FakeDictionaryServiceDecoratorImpl"));

		String messageServiceClassName = bean.getMessageService().getClass()
		        .getName();

		Assert.assertTrue(messageServiceClassName.startsWith("$Proxy"));

	}
	
	@Test
	public void testSerializeProxy() throws IOException, ClassNotFoundException {
		
		TestBean bean = context.getBean(TestBean.class);
		
		/*
		 * Right now there is no way to get the proxt from the container itself we have to get it off of an 
		 * already @Autowired property.
		 */
		MessageService messageService = bean.getMessageService();
	
		// check that the serializable proxy takes only 394 bytes to serialize
		testBeanSerialization(messageService, "org.kuali.student.common.spring.SerializableProxyInvokationHandler (serviceName={http://student.kuali.org/wsdl/message}MessageService)", 408);
		
		// check that the non serializable service impl would take 2,018,492 bytes to save
		testBeanSerialization(new FakeMessageServiceImpl(), FakeMessageServiceImpl.class.getName(), 2018492);
		
	}
	
	private void testBeanSerialization(Object bean, String expectedToStringValue, long expectedSize) throws IOException, ClassNotFoundException {
		
		File tempFile = File.createTempFile("proxy", "dat");

		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tempFile));
		
		oos.writeObject(bean);
		oos.flush();
		oos.close();
		
		// check the size of that file
		
		long serializedSizeInBytes = FileUtils.sizeOf(tempFile);
		
		Assert.assertEquals(expectedSize, serializedSizeInBytes);
		
		ObjectInputStream iis = new ObjectInputStream(new FileInputStream(tempFile));
		
		MessageService generated = (MessageService) iis.readObject();
		
		iis.close();
		
		Assert.assertEquals(expectedToStringValue, generated.toString());
	}
}
