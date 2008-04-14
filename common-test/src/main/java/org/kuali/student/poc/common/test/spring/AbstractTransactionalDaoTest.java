package org.kuali.student.poc.common.test.spring;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:META-INF/default-dao-context-test.xml"})
@TestExecutionListeners( { TransactionalTestExecutionListener.class,
		DaoTestDependencyInjectorListener.class,
		DirtiesContextTestExecutionListener.class })
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager")
public abstract class AbstractTransactionalDaoTest {
	@PersistenceContext
	protected EntityManager em;

	@Before
	public void preLoadBeans(){
		for(Field f:this.getClass().getDeclaredFields()){
			if(f.isAnnotationPresent(Dao.class)){
				Dao dao = f.getAnnotation(Dao.class);
				if(dao.testDataFile().length()>0){
					ApplicationContext ac = new FileSystemXmlApplicationContext(dao.testDataFile());
					for(Object o:(List<?>)ac.getBean("persistList")){
						em.persist(o);
					}
				}
			}
		}
	}
	
	/**
	 * 
	 */
	public AbstractTransactionalDaoTest() {
		super();
		if(this.getClass().isAnnotationPresent(PersistenceFileLocation.class)){
			PersistenceFileLocation a = this.getClass().getAnnotation(PersistenceFileLocation.class);
			System.setProperty("ks.test.persistenceLocation", a.value());
		}else{
			System.setProperty("ks.test.persistenceLocation", "classpath:META-INF/persistence.xml");
		}
	}
}
