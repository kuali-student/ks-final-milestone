package org.kuali.student.poc.common.test.spring;


import java.lang.reflect.Field;

import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/jetty-context.xml" })
@TestExecutionListeners( { ServiceTestDependencyInjectorListener.class,
		DirtiesContextTestExecutionListener.class })
public abstract class AbstractServiceTest {

	/**
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws BeansException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	public AbstractServiceTest()  {
		super();
		System.setProperty("ks.test.port","8181");
		for(Field f:this.getClass().getFields()){
			if(f.isAnnotationPresent(Client.class)){
				Client a = f.getAnnotation(Client.class);
				System.setProperty("ks.test.serviceImplClass", a.value());
				System.setProperty("ks.test.port",a.port());
			}
		}

		if(this.getClass().isAnnotationPresent(PersistenceFileLocation.class)){
			PersistenceFileLocation a = this.getClass().getAnnotation(PersistenceFileLocation.class);
			System.setProperty("ks.test.persistenceLocation", a.value());
		}else{
			System.setProperty("ks.test.persistenceLocation", "classpath:META-INF/persistence.xml");
		}
		
		Daos daos = this.getClass().getAnnotation(Daos.class);
		
		String daoImpls="";
		if (daos != null) {
			int i = 1;
			for (Dao dao : daos.value()) {
				daoImpls+=dao.value()+"|"+dao.testDataFile();
				if(i<daos.value().length){
					daoImpls+=",";
				}
				i++;
			}
			System.setProperty("ks.test.daoImplClasses", daoImpls);
		}
	}

}
