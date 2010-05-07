/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common_test_tester;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;

import org.aopalliance.aop.Advice;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.IdToObjectEhcacheAdvice;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.common_test_tester.support.MyService;
import org.springframework.aop.aspectj.AspectInstanceFactory;
import org.springframework.aop.aspectj.AspectJAroundAdvice;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.AspectJPointcutAdvisor;
import org.springframework.aop.aspectj.SingletonAspectInstanceFactory;
import org.springframework.aop.framework.ProxyFactory;

@Daos( {
		@Dao(value = "org.kuali.student.common_test_tester.support.MyDaoImpl", testDataFile = "classpath:META-INF/load-my-beans.xml",testSqlFile="classpath:test.sql"),
		@Dao("org.kuali.student.common_test_tester.support.OtherDaoImpl") })
@PersistenceFileLocation("classpath:META-INF/test-persistence.xml")
public class ServiceCommonTest extends AbstractServiceTest {

	@Client(value="org.kuali.student.common_test_tester.support.MyServiceImpl",additionalContextFile="classpath:test-my-additional-context.xml")
	private MyService client;

	@Test
	public void test1() {
		System.out.println(System.getProperty("ks.test.daoImplClasses"));
		assertNotNull(client.saveString("la la la"));
	}

	@Test
	public void testDaoLoader(){
	    String value = "loaded-value";
	    assertEquals(value, client.findValueFromValue(value));
	}
	
	@Test
	public void testClientCaching() {
		// Create a proxy for aop caching
		MyService cachedClient = client;
		try {
			//Create the proxy
			ProxyFactory factory = new ProxyFactory(client);
			factory.addInterface(MyService.class);

			//Create the advice (caching) and a factory for that advice
			Advice cacheAdvice = new IdToObjectEhcacheAdvice("cachename");
			AspectInstanceFactory aif = new SingletonAspectInstanceFactory(
					cacheAdvice);
			
			//Look up the method object
			Method method = IdToObjectEhcacheAdvice.class.getMethod(
					"getFromCache", ProceedingJoinPoint.class);
			//Create a new pointcut
			AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
			pointcut
					.setExpression("execution(* org.kuali.student.common_test_tester.support.MyService.find*(..))");
			//Create the around advice using our caching aspect and the point cut
			AspectJAroundAdvice advice = new AspectJAroundAdvice(method,
					pointcut, aif);
			//Create an advisor to wrap our advice
			AspectJPointcutAdvisor advisor = new AspectJPointcutAdvisor(advice);
			//Add the advisor to the proxy factory
			factory.addAdvisor(advisor);

			//Do the same with a different Method/Expression
			method = IdToObjectEhcacheAdvice.class.getMethod("invalidateCache",
					ProceedingJoinPoint.class);
			pointcut = new AspectJExpressionPointcut();
			pointcut
					.setExpression("execution(* org.kuali.student.common_test_tester.support.MyService.update*(..))");
			advice = new AspectJAroundAdvice(method, pointcut, aif);
			advisor = new AspectJPointcutAdvisor(advice);
			factory.addAdvisor(advisor);
			
			cachedClient = (MyService) factory.getProxy();
		} catch (SecurityException e) {

			e.printStackTrace();
		} catch (NoSuchMethodException e) {

			e.printStackTrace();
		}

		String id = cachedClient.saveString("Cache me!");
		cachedClient.findStringId(id);
		// find again but it should be cached by now
		assertNotNull(cachedClient.findStringId(id));
		assertTrue(cachedClient.updateValue(id, "Updated!!!"));
		// now should not be in caches
		assertNotNull(cachedClient.findStringId(id));
	}
}
