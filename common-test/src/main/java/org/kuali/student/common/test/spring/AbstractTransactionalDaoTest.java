package org.kuali.student.common.test.spring;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

/**
 * This test class will load your dao and gives you access to the shared
 * entityManager em. Also passes the &#064;Dao and &#064;PersistenceFileLocation
 * to system properties from the annotations.
 * <p>
 * Extend this class and set the
 * <ul>
 * <li>&#064;PersistenceFileLocation
 * <li>&#064;Dao
 * </ul>
 * <p>
 * &#064;PersistenceFileLocation defines the persistence.xml location if it is
 * named something else.
 * <p>
 * &#064;Dao defines the Dao implementation class, and an optional application
 * context that contains a list of beans that should be persisted. The list bean
 * should be called "persistList"
 * <p>
 * This test class is &#064;Transactional, so all tests will be rolled back.
 * That means the data you load will be in the same state for each test.
 * <p>
 * Example:
 * 
 * <pre>
 * &#064;PersistenceFileLocation(&quot;classpath:META-INF/custom-persistence.xml&quot;)
 * public class DaoCommonTest extends AbstractTransactionalDaoTest {
 * 
 * &#064;Dao(value = &quot;org.kuali.student.MyDaoImpl&quot;, 
 *      testDataFile = &quot;classpath:META-INF/pretest-data-beans.xml&quot;)
 * public MyDao myDao;
 * 
 * &#064;Test
 * public void test1() {
 *	MyObject a = myDao.foo();
 *	MyObject b = em.find(MyObject.class,1);
 *	assertEquals(a.id,b.id);
 * }
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
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/default-dao-context-test.xml" })
@TestExecutionListeners( { TransactionalTestExecutionListener.class,
		DaoTestDependencyInjectorListener.class,
		DirtiesContextTestExecutionListener.class })
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager")
public abstract class AbstractTransactionalDaoTest {
	@PersistenceContext
	protected EntityManager em;

	/**
	 * Loads the application context defined in the &#064;Dao testDataFile
	 * attribute. Then uses the EntityManager em to persist the beans in
	 * persistList
	 */
	@Before
	public void preLoadBeans() {
		for (Field f : this.getClass().getDeclaredFields()) {
			if (f.isAnnotationPresent(Dao.class)) {
				Dao dao = f.getAnnotation(Dao.class);
				if (dao.testDataFile().length() > 0) {
					ApplicationContext ac = new FileSystemXmlApplicationContext(
							dao.testDataFile());
					for (Object o : (List<?>) ac.getBean("persistList")) {
						em.persist(o);
					}
					em.flush();
				}
			}
		}
	}

	/**
	 * Passes some variables so they can be used in the application context
	 */
	public AbstractTransactionalDaoTest() {
		super();
		// Grab annotations and pass them as System properties
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
