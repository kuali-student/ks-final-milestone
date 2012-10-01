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

package org.kuali.student.common.test.spring;

import org.junit.runner.RunWith;

/**
 * This test will start a jetty server and deploy the service define in the
 * &#064;Client. It will also initialize a client based on the annotations in
 * the &#064;Client Service implementation class. Also passes the &#064;Daos and
 * &#064;PersistenceFileLocation to system properties from the annotations.
 * <p>
 * Extend this class and set the
 * <ul>
 * <li>&#064;PersistenceFileLocation
 * <li>&#064;Daos
 * <li>&#064;Client
 * </ul>
 * <p>
 * &#064;PersistenceFileLocation defines the persistence.xml location if it is
 * named something else.
 * <p>
 * &#064;Daos is a list of &#064;Dao annotations. These define the Dao
 * implementation classes, and an optional application context that contains a
 * list of beans that should be persisted. The list bean should be called
 * "persistList". A sql file that should be loaded can also be defined here with the
 * testSqlFile parameter.  This should be a sql file.
 * <p>
 * &#064;Client requires the name of the service implementation class which
 * should be annotated with the &#064;WebService and have the targetNamespace
 * and serviceName set.
 * <p>
 * &#064;Client can also take the following additional settings
 * <ul>
 * <li>port - use to set port if the default port 9191 is not available. The default port can be changed by the system property "ks.test.default.port"
 * <li>secure - set to true to return a secure client (default is false)
 * </ul>
 * 
 * 
 * <p>
 * Example:
 * <p>
 * 
 * <pre>
 *  @Daos( {   @Dao(value = &quot;org.kuali.student.MyDaoImpl&quot;, 
 *               testDataFile = &quot;classpath:META-INF/pretest-data-beans.xml&quot;),
 *            @Dao(&quot;org.kuali.student.OtherDaoImpl&quot;) })
 *  @PersistenceFileLocation(&quot;classpath:META-INF/custom-persistence.xml&quot;)
 * public class ServiceCommonTest extends AbstractServiceTest {
 * 
 *  @Client(&quot;org.kuali.student.MyServiceImpl&quot;)
 * public MyService client;
 * 
 *  @Test
 * public void test1() {
 * 	client.foo();
 * }
 * </pre>
 * 
 * Example of application context for preloading data:
 * 
 * <pre>
 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 * &lt;beans xmlns=&quot;http://www.springframework.org/schema/beans&quot;
 *  xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot;
 *  xsi:schemaLocation=&quot;http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd&quot;&gt;
 *  
 *  &lt;bean id=&quot;persistList&quot;
 *  class=&quot;org.springframework.beans.factory.config.ListFactoryBean&quot;&gt;
 * &lt;property name=&quot;sourceList&quot;&gt;
 * 	&lt;list&gt;
 * 		&lt;ref bean=&quot;value1&quot; /&gt;
 * 		&lt;ref bean=&quot;value2&quot; /&gt;
 * 	&lt;/list&gt;
 * &lt;/property&gt;
 *  &lt;/bean&gt;
 *  
 *  &lt;bean id=&quot;value1&quot;
 *  class=&quot;org.kuali.student.Value&quot;&gt;
 * &lt;property name=&quot;value&quot; value=&quot;Value Number One&quot; /&gt;
 *  &lt;/bean&gt;
 *  
 *  &lt;bean id=&quot;value2&quot;
 *  class=&quot;org.kuali.student.Value&quot;&gt;
 * &lt;property name=&quot;value&quot; value=&quot;Value Number Two&quot; /&gt;
 *  &lt;/bean&gt;
 * 
 * &lt;/beans&gt;
 * </pre>
 * 
 */
@RunWith(ServiceTestClassRunner.class)
public abstract class AbstractServiceTest {

}
