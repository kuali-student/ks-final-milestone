package org.kuali.student.poc.common.test.spring;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
/**
 * This test will create a manager and load the Daos you define and inject them 
 * into your manager by type. It passes the &#064;Daos and
 * &#064;PersistenceFileLocation to system properties from the annotations.
 * <p>
 * Extend this class and set the
 * <ul>
 * <li>&#064;PersistenceFileLocation
 * <li>&#064;Daos
 * <li>&#064;Manager
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
 * &#064;Manager requires the name of the Manager implementation class. <B>This manager 
 * MUST be &#064;Transactional</B> in order for the tests to work correctly.
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
 * public class ManagerCommonTest extends AbstractManagerTest {
 * 
 * &#064;Manager(&quot;org.kuali.student.MyManagerImpl&quot;)
 * public MyManager manager;
 * 
 * &#064;Test
 * public void test1() {
 * 	manager.foo();
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
@ContextConfiguration(locations = { "classpath:META-INF/default-manager-context-test.xml" })
@TestExecutionListeners( { TransactionalTestExecutionListener.class,
		ManagerTestDependencyInjectorListener.class,
		DirtiesContextTestExecutionListener.class })
public abstract class AbstractManagerTest {
	@PersistenceContext
	protected EntityManager em;

	/**
	 * 
	 */
	public AbstractManagerTest() {
		super();

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

		// Grab the persistence context location or set a default value
		if (this.getClass().isAnnotationPresent(PersistenceFileLocation.class)) {
			PersistenceFileLocation a = this.getClass().getAnnotation(
					PersistenceFileLocation.class);
			System.setProperty("ks.test.persistenceLocation", a.value());
		} else {
			System.setProperty("ks.test.persistenceLocation",
					"classpath:META-INF/persistence.xml");
		}
	}
}
