package org.kuali.student.ap.framework.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.AutowireCandidateResolver;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Application context for loading beans from one or more XML resources.
 * 
 * <p>
 * This context configures injected auto-wiring and top-down bean factory
 * delegation.
 * </p>
 */
public class RiceApplicationContext extends ClassPathXmlApplicationContext {

	private final AutowireCandidateResolver autowireCandidateResolver;
	private final BeanFactory delegatedBeanFactory;

	public RiceApplicationContext(String[] resources,
			ApplicationContext parent,
			AutowireCandidateResolver autowireCandidateResolver,
			BeanFactory delegatedBeanFactory) {
		super(resources, false, parent);
		this.autowireCandidateResolver = autowireCandidateResolver;
		this.delegatedBeanFactory = delegatedBeanFactory;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected DefaultListableBeanFactory createBeanFactory() {
		return new RiceBeanFactory(autowireCandidateResolver,
				delegatedBeanFactory, getInternalParentBeanFactory());
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void customizeBeanFactory(DefaultListableBeanFactory beanFactory) {
		super.customizeBeanFactory(beanFactory);
		((RiceBeanFactory) beanFactory).customize();
	}

}
