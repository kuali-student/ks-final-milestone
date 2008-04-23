package org.kuali.student.poc.common.test.spring;

import java.lang.reflect.Field;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

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
 * "persistList"
 * <p>
 * &#064;Client requires the name of the service implementation class which
 * should be annotated with the &#064;WebService and have the targetNamespace
 * and serviceName set.
 * <p> 
 * &#064;Client can also take the following additional settings
 * <ul>
 * <li>port - use to set port if the default port 8181 is not available.
 * <li>secure - set to true to return a secure client (default is false)
 * </ul>
 * 
 * 
 * <p>
 * Example:
 * <p>
 * 
 * <pre>
 * &#064;Daos( {  &#064;Dao(value = &quot;org.kuali.student.MyDaoImpl&quot;, 
 *               testDataFile = &quot;classpath:META-INF/pretest-data-beans.xml&quot;),
 *           &#064;Dao(&quot;org.kuali.student.OtherDaoImpl&quot;) })
 * &#064;PersistenceFileLocation(&quot;classpath:META-INF/custom-persistence.xml&quot;)
 * public class ServiceCommonTest extends AbstractServiceTest {
 * 
 * &#064;Client(&quot;org.kuali.student.MyServiceImpl&quot;)
 * public MyService client;
 * 
 * &#064;Test
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
 *	&lt;property name=&quot;sourceList&quot;&gt;
 *		&lt;list&gt;
 *			&lt;ref bean=&quot;value1&quot; /&gt;
 *			&lt;ref bean=&quot;value2&quot; /&gt;
 *		&lt;/list&gt;
 *	&lt;/property&gt;
 *  &lt;/bean&gt;
 *  
 *  &lt;bean id=&quot;value1&quot;
 *  class=&quot;org.kuali.student.Value&quot;&gt;
 *	&lt;property name=&quot;value&quot; value=&quot;Value Number One&quot; /&gt;
 *  &lt;/bean&gt;
 *  
 *  &lt;bean id=&quot;value2&quot;
 *  class=&quot;org.kuali.student.Value&quot;&gt;
 *	&lt;property name=&quot;value&quot; value=&quot;Value Number Two&quot; /&gt;
 *  &lt;/bean&gt;
 * 
 * &lt;/beans&gt;
 * </pre>
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/jetty-context.xml" })
@TestExecutionListeners( { ServiceTestDependencyInjectorListener.class,
		DirtiesContextTestExecutionListener.class })
public abstract class AbstractServiceTest {

	public AbstractServiceTest() {

		super();

		// Set the default Port
		System.setProperty("ks.test.port", "8181");

		// Grab the client annotation and set the service implementation and
		// port as system properties
		for (Field f : this.getClass().getFields()) {
			if (f.isAnnotationPresent(Client.class)) {
				Client a = f.getAnnotation(Client.class);
				if (a.secure()){
				    System.setProperty("ks.test.serviceImplSecure", a.value());
				} else {
				    System.setProperty("ks.test.serviceImplClass", a.value());
				}
				System.setProperty("ks.test.port", a.port());
			}
		}
		
		//If no secure client defined, set secure service endpoint impl
		//to be same as non-secure endpoint impl
		if (System.getProperty("ks.test.serviceImplSecure") == null){
		    System.setProperty("ks.test.serviceImplSecure", System.getProperty("ks.test.serviceImplClass"));
		}

		// Grab the persistence context loacation or set a default value
		if (this.getClass().isAnnotationPresent(PersistenceFileLocation.class)) {
			PersistenceFileLocation a = this.getClass().getAnnotation(
					PersistenceFileLocation.class);
			System.setProperty("ks.test.persistenceLocation", a.value());
		} else {
			System.setProperty("ks.test.persistenceLocation",
					"classpath:META-INF/persistence.xml");
		}

		// Grab the Dao information and pass it to a System variable
		Daos daos = this.getClass().getAnnotation(Daos.class);

		String daoImpls = "";
		if (daos != null) {
			int i = 1;
			for (Dao dao : daos.value()) {
				daoImpls += dao.value() + "|" + dao.testDataFile();
				if (i < daos.value().length) {
					daoImpls += ",";
				}
				i++;
			}
			System.setProperty("ks.test.daoImplClasses", daoImpls);
		}
	}

}
