package org.kuali.student.ap.framework.config;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.xml.namespace.QName;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.config.ConfigurationException;
import org.kuali.rice.core.framework.resourceloader.BaseResourceLoader;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.AutowireCandidateResolver;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * Short-term alternate for
 * org.kuali.rice.core.framework.resourceloader.SpringResourceLoader
 * 
 * @deprecated TODO: Move KSAP auto-wiring functionality to Rice.
 */
public class SpringResourceLoader extends BaseResourceLoader {

	private static final Logger LOG = Logger
			.getLogger(SpringResourceLoader.class);

	private SpringResourceLoader parentSpringResourceLoader;

	private ApplicationContext parentContext;
	private ConfigurableApplicationContext context;
	private ServletContext servletContextcontext;

	// BEGIN KSAP MOD
	/**
	 * The injected AutowireCandidateResolver to use for injecting resources
	 * into this module.
	 */
	private AutowireCandidateResolver autowireCandidateResolver;

	/**
	 * The injected BeanFactory to use for top-down bean delegation for this
	 * module.
	 */
	private BeanFactory delegatedBeanFactory;

	/**
	 * Thread-local used to track
	 */
	private static final ThreadLocal<PopulatingBean> POPULATING = new ThreadLocal<PopulatingBean>();

	/**
	 * Get a proxy instance for delaying initialization errors for dependencies
	 * marked as optional. This allows the container to return a real value for
	 * missing resources, delaying the failure until the first time the service
	 * is used.
	 * 
	 * <p>
	 * The proxy returned by this method will always throw
	 * UnsupportedOperationException with the provided message and cause.
	 * </p>
	 * 
	 * @param message
	 *            The message to include with the exception.
	 * @param cause
	 *            The initialization failure cause.
	 * @param type
	 *            The interface to use for the proxy.
	 * @return A proxy to use for modeling an unsupported feature.
	 */
	static <T> T missingOptionalDependency(final String message,
			final Throwable cause, final Class<T> type) {
		return type.cast(Proxy.newProxyInstance(type.getClassLoader(),
				new Class<?>[] { type, }, new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method,
							Object[] args) throws Throwable {
						throw new UnsupportedOperationException(message, cause);
					}
				}));
	}

	/**
	 * Tracks meta-information related to the bean currently being populated for
	 * auto-wiring support.
	 * 
	 * @see DefaultListableBeanFactory#populateBean(String,
	 *      AbstractBeanDefinition, BeanWrapper)
	 */
	public static class PopulatingBean {
		final String beanName;
		final AbstractBeanDefinition definition;
		final BeanWrapper wrapper;

		private PopulatingBean(String beanName, AbstractBeanDefinition mbd,
				BeanWrapper bw) {
			this.beanName = beanName;
			this.definition = mbd;
			this.wrapper = bw;
		}

		public String getBeanName() {
			return beanName;
		}

		public AbstractBeanDefinition getDefinition() {
			return definition;
		}

		public BeanWrapper getWrapper() {
			return wrapper;
		}
	}

	/**
	 * Get meta-information related to the bean currently being populated.
	 * 
	 * <p>
	 * Rice-aware {@link AutowireCandidateResolver} and {@link BeanFactory}
	 * implementations can use this utility method to get meta-information about
	 * the bean currently being populated. This will be the bean definition
	 * (either XML or annotation driven) that refers to the bean or autowire
	 * resource within the Rice module that is actively being refresh on the
	 * current thread.
	 * </p>
	 * 
	 * @return Meta-information related to the bean currently being populated.
	 */
	public static PopulatingBean getPopulatingBean() {
		return POPULATING.get();
	}

	/**
	 * Internal bean factory for achieving top-down delegation to the injected
	 * bean factory resource.
	 */
	private class _BeanFactory extends DefaultListableBeanFactory {

		private _BeanFactory() {
		}

		private _BeanFactory(BeanFactory parentBeanFactory) {
			super(parentBeanFactory);
		}

		/**
		 * Call after attaching to the context, such as from
		 * customizeBeanFactory().
		 */
		private void customize() {
			if (autowireCandidateResolver != null)
				try {
					BeanUtils.setProperty(autowireCandidateResolver,
							"delegate", getAutowireCandidateResolver());
				} catch (IllegalAccessException e) {
					throw new IllegalStateException(e);
				} catch (InvocationTargetException e) {
					if (e.getCause() instanceof RuntimeException)
						throw (RuntimeException) e.getCause();
					if (e.getCause() instanceof Error)
						throw (Error) e.getCause();
					throw new IllegalStateException(e);
				}
			setAutowireCandidateResolver(autowireCandidateResolver);
		}

		@Override
		protected void populateBean(String beanName,
				AbstractBeanDefinition mbd, BeanWrapper bw) {
			PopulatingBean obn = POPULATING.get();
			try {
				POPULATING.set(new PopulatingBean(beanName, mbd, bw));
				super.populateBean(beanName, mbd, bw);
			} finally {
				if (obn == null)
					POPULATING.remove();
				else
					POPULATING.set(obn);
			}
		}

		@Override
		public Object resolveDependency(DependencyDescriptor descriptor,
				String beanName, Set<String> autowiredBeanNames,
				TypeConverter typeConverter) throws BeansException {
			try {
				return super.resolveDependency(descriptor, beanName,
						autowiredBeanNames, typeConverter);
			} catch (NoSuchBeanDefinitionException e) {
				boolean optional = false;
				for (Annotation a : descriptor.getAnnotations())
					optional = optional || (a instanceof OptionalResource);
				if (optional)
					return missingOptionalDependency(
							"Missing optional dependency for "
									+ descriptor.getDependencyName() + " "
									+ descriptor.getDependencyType(), e,
							descriptor.getDependencyType());
				else
					throw e;
			}
		}

		@Override
		public Object getBean(String name) throws BeansException {
			Object rv = null;
			if (delegatedBeanFactory != null)
				try {
					rv = delegatedBeanFactory.getBean(name);
				} catch (NoSuchBeanDefinitionException e) {
					LOG.debug("Delegate bean factory did not supply a bean", e);
				}
			if (rv == null)
				rv = super.getBean(name);
			return rv;
		}

		@Override
		public <T> T getBean(String name, Class<T> requiredType)
				throws BeansException {
			T rv = null;
			if (delegatedBeanFactory != null)
				try {
					rv = delegatedBeanFactory.getBean(name, requiredType);
				} catch (NoSuchBeanDefinitionException e) {
					LOG.debug("Delegate bean factory did not supply a bean", e);
				}
			if (rv == null)
				rv = super.getBean(name, requiredType);
			return rv;
		}

		@Override
		public <T> T getBean(Class<T> requiredType) throws BeansException {
			T rv = null;
			if (delegatedBeanFactory != null)
				try {
					rv = delegatedBeanFactory.getBean(requiredType);
				} catch (NoSuchBeanDefinitionException e) {
					LOG.debug("Delegate bean factory did not supply a bean", e);
				}
			if (rv == null)
				rv = super.getBean(requiredType);
			return rv;
		}

		@Override
		public Object getBean(String name, Object... args)
				throws BeansException {
			Object rv = null;
			if (delegatedBeanFactory != null)
				try {
					rv = delegatedBeanFactory.getBean(name, args);
				} catch (NoSuchBeanDefinitionException e) {
					LOG.debug("Delegate bean factory did not supply a bean", e);
				}
			if (rv == null)
				rv = super.getBean(name, args);
			return rv;
		}

		@Override
		public boolean containsBean(String name) {
			return (delegatedBeanFactory != null && delegatedBeanFactory
					.containsBean(name)) || super.containsBean(name);
		}

		@Override
		public boolean isSingleton(String name)
				throws NoSuchBeanDefinitionException {
			if (delegatedBeanFactory != null)
				try {
					return delegatedBeanFactory.isSingleton(name);
				} catch (NoSuchBeanDefinitionException e) {
					LOG.debug("Delegate bean factory did not supply a bean", e);
				}
			return super.isSingleton(name);
		}

		@Override
		public boolean isPrototype(String name)
				throws NoSuchBeanDefinitionException {
			if (delegatedBeanFactory != null)
				try {
					return delegatedBeanFactory.isPrototype(name);
				} catch (NoSuchBeanDefinitionException e) {
					LOG.debug("Delegate bean factory did not supply a bean", e);
				}
			return super.isPrototype(name);
		}

		@Override
		public boolean isTypeMatch(String name, Class<?> targetType)
				throws NoSuchBeanDefinitionException {
			if (delegatedBeanFactory != null)
				try {
					return delegatedBeanFactory.isTypeMatch(name, targetType);
				} catch (NoSuchBeanDefinitionException e) {
					LOG.debug("Delegate bean factory did not supply a bean", e);
				}
			return super.isTypeMatch(name, targetType);
		}

		@Override
		public Class<?> getType(String name)
				throws NoSuchBeanDefinitionException {
			Class<?> rv = null;
			if (delegatedBeanFactory != null)
				try {
					rv = delegatedBeanFactory.getType(name);
				} catch (NoSuchBeanDefinitionException e) {
					LOG.debug("Delegate bean factory did not supply a bean", e);
				}
			if (rv == null)
				rv = super.getType(name);
			return rv;
		}

		@Override
		public String[] getAliases(String name) {
			List<String> aliases = new java.util.LinkedList<String>();
			if (delegatedBeanFactory != null
					&& delegatedBeanFactory.getAliases(name) != null)
				aliases.addAll(Arrays.asList(delegatedBeanFactory
						.getAliases(name)));
			if (super.getAliases(name) != null)
				aliases.addAll(Arrays.asList(super.getAliases(name)));
			return aliases.toArray(new String[aliases.size()]);
		}

	}

	/**
	 * Internal application context for loading beans from one or more XML
	 * resources.
	 * 
	 * <p>
	 * This context configures the inject auto-wiring and top-down bean factory
	 * delegation.
	 * </p>
	 */
	private class _ApplicationContext extends ClassPathXmlApplicationContext {

		private _ApplicationContext(String[] resources,
				ApplicationContext parent) {
			super(resources, parent);
		}

		@Override
		protected DefaultListableBeanFactory createBeanFactory() {
			return new _BeanFactory(getInternalParentBeanFactory());
		}

		@Override
		protected void customizeBeanFactory(
				DefaultListableBeanFactory beanFactory) {
			super.customizeBeanFactory(beanFactory);
			((_BeanFactory) beanFactory).customize();
		}
	}

	SpringResourceLoader(QName name, List<String> fileLocs,
			ServletContext servletContextcontext,
			AutowireCandidateResolver autowireCandidateResolver,
			BeanFactory delegatedBeanFactory) {
		super(name);
		this.fileLocs = fileLocs;
		this.servletContextcontext = servletContextcontext;
		this.autowireCandidateResolver = autowireCandidateResolver;
		this.delegatedBeanFactory = delegatedBeanFactory;
	}

	// END KSAP MOD

	private final List<String> fileLocs;

	@Override
	public void start() throws Exception {
		if (!isStarted()) {
			LOG.info("Creating Spring context "
					+ StringUtils.join(this.fileLocs, ","));
			if (parentSpringResourceLoader != null && parentContext != null) {
				throw new ConfigurationException(
						"Both a parentSpringResourceLoader and parentContext were defined.  Only one can be defined!");
			}
			if (parentSpringResourceLoader != null) {
				parentContext = parentSpringResourceLoader.getContext();
			}

			if (servletContextcontext != null) {
				// BEGIN KSAP MOD
				XmlWebApplicationContext lContext = new XmlWebApplicationContext() {
					@Override
					protected DefaultListableBeanFactory createBeanFactory() {
						return new _BeanFactory(getInternalParentBeanFactory());
					}

					@Override
					protected void customizeBeanFactory(
							DefaultListableBeanFactory beanFactory) {
						super.customizeBeanFactory(beanFactory);
						((_BeanFactory) beanFactory).customize();
					}
				};
				// END KSAP MOD
				lContext.setServletContext(servletContextcontext);
				lContext.setParent(parentContext);
				lContext.setConfigLocations(this.fileLocs
						.toArray(new String[] {}));
				lContext.refresh();
				context = lContext;
			} else {
				// BEGIN KSAP MOD
				_ApplicationContext lContext = new _ApplicationContext(
						this.fileLocs.toArray(new String[this.fileLocs.size()]),
						parentContext);
				lContext.refresh();
				this.context = lContext;
			}
			super.start();
		}
	}

	// UNMODIFIED BELOW
	@Override
	public Object getService(QName serviceName) {
		if (!isStarted()) {
			return null;
		}
		if (this.getContext().containsBean(serviceName.toString())) {
			Object service = this.getContext().getBean(serviceName.toString());
			return postProcessService(serviceName, service);
		}
		return super.getService(serviceName);
	}

	@Override
	public void stop() throws Exception {
		LOG.info("Stopping Spring context "
				+ StringUtils.join(this.fileLocs, ","));
		this.context.close();
		super.stop();
	}

	private ConfigurableApplicationContext getContext() {
		return this.context;
	}

	public void setParentContext(ApplicationContext parentContext) {
		this.parentContext = parentContext;
	}

	public void setParentSpringResourceLoader(
			SpringResourceLoader parentSpringResourceLoader) {
		this.parentSpringResourceLoader = parentSpringResourceLoader;
	}

	@Override
	public String getContents(String indent, boolean servicePerLine) {
		String contents = "";
		for (String name : context.getBeanDefinitionNames()) {
			if (servicePerLine) {
				contents += indent + "+++" + name + "\n";
			} else {
				contents += name + ", ";
			}
		}
		return contents;
	}

}
