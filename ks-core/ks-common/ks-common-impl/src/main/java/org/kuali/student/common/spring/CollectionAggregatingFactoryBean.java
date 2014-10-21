/**
 * 
 */
package org.kuali.student.common.spring;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

/**
 * Create a List<String> using a set of static resources and also from resolving several bean references.
 * 
 * The named beans do not need to exist.  The aggregation is from those beans of List<String> that are resolvable from the list of named bean references.
 * 
 * This allows this factory bean to be configured in a global scope defining all possible modules and work with what ever combination of spring profiles are active.
 * 
 * 
 * 
 * @author Kuali Student Team
 *
 */
public class CollectionAggregatingFactoryBean implements FactoryBean<List<String>>, BeanFactoryAware {

	private static final Logger log = LoggerFactory.getLogger(CollectionAggregatingFactoryBean.class);
	
	/*
	 * static resources
	 */
	private List<String>staticResources = new ArrayList<String>();
	
	/*
	 * bean names for List<String> collections that should be aggregated
	 */
	private List<String>collectionBeanReferences = new ArrayList<String>();

	private BeanFactory beanFactory;
	
	
	/**
	 * 
	 */
	public CollectionAggregatingFactoryBean() {
	}

	
	/**
	 * @param staticResources the staticResources to set
	 */
	public void setStaticResources(List<String> staticResources) {
		this.staticResources = staticResources;
	}


	/**
	 * @param collectionBeanReferences the collectionBeanReferences to set
	 */
	public void setCollectionBeanReferences(List<String> collectionBeanReferences) {
		this.collectionBeanReferences = collectionBeanReferences;
	}

	

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.BeanFactoryAware#setBeanFactory(org.springframework.beans.factory.BeanFactory)
	 */
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<String> getObject() throws Exception {
		
		List<String>aggregationList = new ArrayList<String>();

		aggregationList.addAll(staticResources);
		
		for (String beanName : this.collectionBeanReferences) {
			
			try {
				List<String>collectionBean = beanFactory.getBean(beanName, List.class);
				
				if (collectionBean != null)
					aggregationList.addAll(collectionBean);
			} catch (NoSuchBeanDefinitionException e) {
				// allow the named bean to not exist.  
				// continue to the next named bean reference.
				// this can occur if the named bean is defined in a spring profile
				// that is not active right now.
				log.info("skipping non-existent bean reference = " + beanName);
			}
		}
		
		return aggregationList;
	}

	@Override
	public Class<?> getObjectType() {
		return List.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
