package org.kuali.student.ap.framework.config;

import javax.servlet.ServletContext;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.AutowireCandidateResolver;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * Application context for loading beans from one or more XML resources.
 * 
 * <p>
 * This context configures injected auto-wiring and top-down bean factory
 * delegation.
 * </p>
 */
public class RiceWebApplicationContext extends XmlWebApplicationContext {

	private final AutowireCandidateResolver autowireCandidateResolver;
	private final BeanFactory delegatedBeanFactory;

	public RiceWebApplicationContext() {
		this.autowireCandidateResolver = GlobalResourceLoader
				.getService("autowireCandidateResolver");
		this.delegatedBeanFactory = GlobalResourceLoader
				.getService("delegatedBeanFactory");
		assert this.autowireCandidateResolver != null;
		assert this.delegatedBeanFactory != null;
	}

	public RiceWebApplicationContext(ServletContext ctx, String[] resources,
			ApplicationContext parent,
			AutowireCandidateResolver autowireCandidateResolver,
			BeanFactory delegatedBeanFactory) {
		super();
		assert autowireCandidateResolver != null;
		assert delegatedBeanFactory != null;
		this.autowireCandidateResolver = autowireCandidateResolver;
		this.delegatedBeanFactory = delegatedBeanFactory;
		setServletContext(ctx);
		setParent(parent);
		setConfigLocations(resources);
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
