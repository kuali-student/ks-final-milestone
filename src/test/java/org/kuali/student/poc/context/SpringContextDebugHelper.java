/**
 * 
 */
package org.kuali.student.poc.context;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.sql.XADataSource;
import javax.persistence.spi.PersistenceProvider;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.context.support.GenericApplicationContext;
import com.atomikos.jdbc.AtomikosDataSourceBean;

/**
 * Used in JUnit Test classes to show actual Spring Application Context Configuration
 * @see org.springframework.context.ApplicationContext
 * 
 * Helper methods take an ApplicationContext and a StringBuilder and append appropriate data to
 * the StringBuilder. Then the StringBuilder can be output to the console or log file.
 * @see org.kuali.student.poc.context.TestApplicationContext for usage
 * 
 * @author garystruthers
 * 
 *
 */
public class SpringContextDebugHelper {
	private String eol = System.getProperty("line.separator");

	/**
	 * Append top level ApplicationContext fields asserts fields exist
	 * @param applicationContext
	 * @param sb
	 */
	public void appendApplicationContextFields(ApplicationContext applicationContext, StringBuilder sb) {

		String displayName = applicationContext.getDisplayName();
		assertNotNull(displayName);
		String classpathUrlPrefix = applicationContext.CLASSPATH_URL_PREFIX;
		assertNotNull(classpathUrlPrefix);
		String classpathAllUrlPrefix = applicationContext.CLASSPATH_ALL_URL_PREFIX;
		assertNotNull(classpathAllUrlPrefix);
		String factoryBeanPrefix = applicationContext.FACTORY_BEAN_PREFIX;
		assertNotNull(factoryBeanPrefix);
		int beanDefinitionCount = applicationContext.getBeanDefinitionCount();
		String contextId = applicationContext.getId();
		sb.append("APPLICATION CONTEXT - DISPLAY NAME: ").append(displayName).append(", ID: ").append(contextId).append(eol);
		sb.append("CLASSPATH_URL_PREFIX: ").append(classpathUrlPrefix).append(" CLASSPATH_ALL_URL_PREFIX: ").append(classpathAllUrlPrefix);
		sb.append(" FACTORY_BEAN_PREFIX: ").append(factoryBeanPrefix).append(",BEAN DEFINITION COUNT: ").append(beanDefinitionCount).append(eol);
	}
	
	/**
	 * Append top level GenericApplicationContext fields asserts fields exist
	 * @param applicationContext to be cast to GenericApplicationContext
	 * @param sb
	 * @throws IOException
	 */
	public void appendGenericApplicationContextFields(ApplicationContext applicationContext, StringBuilder sb) throws IOException {
	
		GenericApplicationContext genericApplicationContext = (GenericApplicationContext)applicationContext;
		String locDelimiters = genericApplicationContext.CONFIG_LOCATION_DELIMITERS;
		String weaverBean = genericApplicationContext.LOAD_TIME_WEAVER_BEAN_NAME;
		Resource[] classpathResources = genericApplicationContext.getResources("classpath*:"); 
		sb = new StringBuilder();
		sb.append("GENERIC APPLICATION CONTEXT - CONFIG_LOCATION_DELIMITERS: ").append(locDelimiters).append(eol);
		sb.append("LOAD_TIME_WEAVER_BEAN_NAME: ").append(weaverBean).append(eol);
		sb.append("CLASSPATH RESOURCES: ").append(classpathResources.length).append(eol);
		for(Resource res : classpathResources) {
			sb.append("CLASSPATH RESOURCE FILENAME: ").append(res.getFilename()).append(eol);
			sb.append("CLASSPATH RESOURCE URL: ").append(res.getURL().toString()).append(eol);
		}
		Resource[] propertiesResources = genericApplicationContext.getResources("*.properties"); 
		sb.append("PROPERTIES FILE RESOURCES: ").append(propertiesResources.length).append(eol);
		for(Resource res : propertiesResources) {
			sb.append("PROPERTIES FILE RESOURCE FILENAME: ").append(res.getFilename()).append(eol);
			sb.append("PROPERTIES FILE RESOURCE URL: ").append(res.getURL().toString()).append(eol);
		}
	}

	/**
	 * Append list of bean names and their class names in the context
	 * @param applicationContext
	 * @param sb
	 */
	public void appendBeanNameAndClasses(ApplicationContext applicationContext, StringBuilder sb) {

		String[] beanNames = applicationContext.getBeanDefinitionNames();
		for(String beanName : beanNames) {
			sb.append("BEAN NAME: ").append(beanName).append(" BEAN CLASS: ").append(applicationContext.getType(beanName)).append(eol);
		}
	}
	
	/**
	 * Append PlaceholderConfigurer fields asserts bean exists
	 * @param applicationContext
	 * @param sb
	 */
	public void appendPropertyPlaceholderConfigurerFields(ApplicationContext applicationContext, StringBuilder sb) {

		Map springConfigurers = applicationContext.getBeansOfType(org.springframework.beans.factory.config.PropertyPlaceholderConfigurer.class);
		assertNotNull(springConfigurers);
		assertFalse(springConfigurers.isEmpty());
		Collection<PropertyPlaceholderConfigurer> values = springConfigurers.values();
		for (PropertyPlaceholderConfigurer key : values) {
			sb.append("SYSTEM_PROPERTIES_MODE_OVERRIDE ").append(key.SYSTEM_PROPERTIES_MODE_OVERRIDE).append(eol);
			sb.append("ORDER ").append(key.getOrder()).append(eol);
			sb.append("CLASS ").append(key.getClass()).append(eol);
			sb.append("XML_FILE_EXTENSION ").append(key.XML_FILE_EXTENSION).append(eol);
			sb.append("OBJECT ").append(key.toString()).append(eol);
		}
	}
	
	/**
	 * Append Classpath Application Properties URL asserts URL exists
	 * @param applicationContext
	 * @param sb
	 * @throws IOException
	 */
	public void appendClasspathApplicationPropertiesURL(ApplicationContext applicationContext, StringBuilder sb) throws IOException {

		Resource res = applicationContext.getResource("classpath:application.properties");
		assertNotNull(res);
		sb.append("applicationContext Resource URL: " + res.getURL());
	}

	/**
	 * Append Atomikos Data Source Bean Fields asserts datasource bean exists asserts it contains an XADataSource
	 * @param applicationContext
	 * @param sb
	 */
	public void appendAtomikosDataSourceBeanFields(ApplicationContext applicationContext, StringBuilder sb) {

		Object bean = applicationContext.getBean("dataSource");
		assertNotNull(bean);
		if(bean.getClass().isAssignableFrom(AtomikosDataSourceBean.class)) {
			AtomikosDataSourceBean atomikosDataSourceBean = (AtomikosDataSourceBean)bean;
			XADataSource xaDataSource = atomikosDataSourceBean.getXaDataSource();
			assertNotNull(xaDataSource);
			sb.append("UniqueResourceName: ").append(atomikosDataSourceBean.getUniqueResourceName());
			sb.append("XaDataSourceClassName: ").append(atomikosDataSourceBean.getXaDataSourceClassName()).append(eol);
			Properties p = atomikosDataSourceBean.getXaProperties();
			sb.append("XaProperties: " + p.toString());
		}
	}
	
	/** 
	 * Appends HibernateJpaVendor Adapter Fields asserts jpaVendorAdapter bean exists
	 * @param applicationContext
	 * @param sb
	 */
	public void appendHibernateJpaVendorAdapterFields(ApplicationContext applicationContext, StringBuilder sb) {

		Object bean = applicationContext.getBean("jpaVendorAdapter");
		assertNotNull(bean);
		if(bean.getClass().isAssignableFrom(HibernateJpaVendorAdapter.class)) {
			HibernateJpaVendorAdapter jpaVendorAdapter = (HibernateJpaVendorAdapter)bean;
			Map jpaPropertyMap = jpaVendorAdapter.getJpaPropertyMap();
			assertNotNull(jpaPropertyMap);
			PersistenceProvider persistenceProvider = jpaVendorAdapter.getPersistenceProvider();
			sb.append("HibernateJpaVendorAdapter - PersistenceProvider ").append(persistenceProvider.toString()).append(eol);
			Set<String> jpaPropertyKeys = jpaPropertyMap.keySet(); 
			sb.append("jpaPropertyKeys size ").append(jpaPropertyKeys.size()).append(eol);
			for ( String key : jpaPropertyKeys) {
				sb.append(key).append(": ").append(jpaPropertyMap.get(key)).append(eol);
			}	
		}
	}
}
