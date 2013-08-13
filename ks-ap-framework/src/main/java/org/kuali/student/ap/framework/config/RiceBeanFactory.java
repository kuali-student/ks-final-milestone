package org.kuali.student.ap.framework.config;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.AutowireCandidateResolver;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Short-term alternate for
 * org.kuali.rice.core.framework.resourceloader.SpringResourceLoader
 * 
 * @deprecated TODO: Move KSAP auto-wiring functionality to Rice.
 */
public class RiceBeanFactory extends DefaultListableBeanFactory {

	private static final Logger LOG = Logger.getLogger(RiceBeanFactory.class);

	/**
	 * The injected AutowireCandidateResolver to use for injecting resources
	 * into this module.
	 */
	private final AutowireCandidateResolver autowireCandidateResolver;

	/**
	 * The injected BeanFactory to use for top-down bean delegation for this
	 * module.
	 */
	private final BeanFactory delegatedBeanFactory;

	/**
	 * Thread-local used to track the populating bean.
	 */
	private static final ThreadLocal<PopulatingBean> POPULATING = new ThreadLocal<PopulatingBean>();

	/**
	 * Thread-local used to track the delegating bean.
	 */
	private static final ThreadLocal<DelegatingBean> DELEGATING = new ThreadLocal<DelegatingBean>();

	/**
	 * Tracks reference data back to the highest-level factory requesting
	 * delegation.
	 */
	private static class DelegatingBean {
		private final String beanName;
		private final Callable<Object> continueGetBean;

		private DelegatingBean(String beanName, Callable<Object> continueGetBean) {
			this.beanName = beanName;
			this.continueGetBean = continueGetBean;
		}
	}

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
		final RiceBeanFactory beanFactory;

		private PopulatingBean(String beanName, AbstractBeanDefinition mbd,
				BeanWrapper bw, RiceBeanFactory beanFactory) {
			this.beanName = beanName;
			this.definition = mbd;
			this.wrapper = bw;
			this.beanFactory = beanFactory;
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

		public RiceBeanFactory getBeanFactory() {
			return beanFactory;
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
	 * Get the non-delegated bean name. When invoked from within a delegated
	 * bean factory operation, this method will return the original name of the
	 * non-delegated result bean. This does not trigger actual creation of the
	 * non-delegated bean like {@link ()} does.
	 */
	public static String getNonDelegateBeanName() {
		return DELEGATING.get() != null ? DELEGATING.get().beanName : null;
	}

	/**
	 * Continue retrieving the non-delegated bean. When invoked from within a
	 * delegated bean factory operation, this method will return the
	 * non-delegated result for the same operation, allowing the delegated
	 * service to act as a proxy for the original service without knowing any
	 * actual details beyond the interface type.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getNonDelegateBean() {
		try {
			return (T) (DELEGATING.get() != null ? DELEGATING.get().continueGetBean
					.call() : null);
		} catch (NoSuchBeanDefinitionException e) {
			// This can happen when a bean defined in the parent factory depends
			// on a reference to a bean defined in this factory. In such a case,
			// containsBean() will be true, but getBean() will throw the
			// exception when attempting to resolve the reference.
			// Enable DEBUG logs to distinguish a null return value and inspect
			// the error
			if (LOG.isDebugEnabled())
				LOG.debug(
						"Non-delegate bean is not available from this bean factory",
						e);
			return null;
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public RiceBeanFactory(AutowireCandidateResolver autowireCandidateResolver,
			BeanFactory delegatedBeanFactory) {
		this.autowireCandidateResolver = autowireCandidateResolver;
		this.delegatedBeanFactory = delegatedBeanFactory;
	}

	public RiceBeanFactory(AutowireCandidateResolver autowireCandidateResolver,
			BeanFactory delegatedBeanFactory, BeanFactory parentBeanFactory) {
		super(parentBeanFactory);
		this.autowireCandidateResolver = autowireCandidateResolver;
		this.delegatedBeanFactory = delegatedBeanFactory;
	}

	/**
	 * Call after attaching to the context, such as from customizeBeanFactory(),
	 * to complete auto-wiring customization.
	 */
	public void customize() {
		if (autowireCandidateResolver != null)
			try {
				BeanUtils.setProperty(autowireCandidateResolver, "delegate",
						getAutowireCandidateResolver());
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
	protected void populateBean(String beanName, RootBeanDefinition mbd,
			BeanWrapper bw) {
		PopulatingBean obn = POPULATING.get();
		try {
			POPULATING.set(new PopulatingBean(beanName, mbd, bw, this));
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

	private Object super$getBean(String name) throws BeansException {
		return super.getBean(name);
	}

	@Override
	public Object getBean(final String name) throws BeansException {
		Object rv = null;
		if (delegatedBeanFactory != null) {
			DelegatingBean odop = DELEGATING.get();
			try {
				DELEGATING.set(new DelegatingBean(name, new Callable<Object>() {
					@Override
					public Object call() throws Exception {
						return super$getBean(name);
					}
				}));
				rv = delegatedBeanFactory.getBean(name);
			} catch (NoSuchBeanDefinitionException e) {
				LOG.debug("Delegate bean factory did not supply a bean", e);
			} finally {
				if (odop == null)
					DELEGATING.remove();
				else
					DELEGATING.set(odop);
			}
		}
		if (rv == null)
			rv = super$getBean(name);
		return rv;
	}

	private <T> T super$getBean(String name, Class<T> requiredType)
			throws BeansException {
		return super.getBean(name, requiredType);
	}

	@Override
	public <T> T getBean(final String name, final Class<T> requiredType)
			throws BeansException {
		T rv = null;
		if (delegatedBeanFactory != null) {
			DelegatingBean odop = DELEGATING.get();
			try {
				DELEGATING.set(new DelegatingBean(name, new Callable<Object>() {
					@Override
					public Object call() throws Exception {
						return super$getBean(name, requiredType);
					}
				}));
				rv = delegatedBeanFactory.getBean(name, requiredType);
			} catch (NoSuchBeanDefinitionException e) {
				LOG.debug("Delegate bean factory did not supply a bean", e);
			} finally {
				if (odop == null)
					DELEGATING.remove();
				else
					DELEGATING.set(odop);
			}
		}
		if (rv == null)
			rv = super$getBean(name, requiredType);
		return rv;
	}

	private <T> T super$getBean(Class<T> requiredType) throws BeansException {
		return super.getBean(requiredType);
	}

	@Override
	public <T> T getBean(final Class<T> requiredType) throws BeansException {
		T rv = null;
		if (delegatedBeanFactory != null) {
			DelegatingBean odop = DELEGATING.get();
			try {
				String rsn = Character.toLowerCase((rsn = requiredType
						.getSimpleName()).charAt(0)) + rsn.substring(1);
				DELEGATING.set(new DelegatingBean(rsn, new Callable<Object>() {
					@Override
					public Object call() throws Exception {
						return super$getBean(requiredType);
					}
				}));
				rv = delegatedBeanFactory.getBean(requiredType);
			} catch (NoSuchBeanDefinitionException e) {
				LOG.debug("Delegate bean factory did not supply a bean", e);
			} finally {
				if (odop == null)
					DELEGATING.remove();
				else
					DELEGATING.set(odop);
			}
		}
		if (rv == null)
			rv = super$getBean(requiredType);
		return rv;
	}

	private Object super$getBean(String name, Object... args) {
		return super.getBean(name, args);
	}

	@Override
	public Object getBean(final String name, final Object... args)
			throws BeansException {
		Object rv = null;
		if (delegatedBeanFactory != null) {
			DelegatingBean odop = DELEGATING.get();
			try {
				DELEGATING.set(new DelegatingBean(name, new Callable<Object>() {
					@Override
					public Object call() throws Exception {
						return super$getBean(name, args);
					}
				}));
				rv = delegatedBeanFactory.getBean(name, args);
			} catch (NoSuchBeanDefinitionException e) {
				LOG.debug("Delegate bean factory did not supply a bean", e);
			} finally {
				if (odop == null)
					DELEGATING.remove();
				else
					DELEGATING.set(odop);
			}
		}
		if (rv == null)
			rv = super$getBean(name, args);
		return rv;
	}

	@Override
	public boolean containsBean(final String name) {
		DelegatingBean odop = DELEGATING.get();
		try {
			DELEGATING.set(new DelegatingBean(name, new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					return super$getBean(name);
				}
			}));
			return (delegatedBeanFactory != null && delegatedBeanFactory
					.containsBean(name)) || super.containsBean(name);
		} finally {
			if (odop == null)
				DELEGATING.remove();
			else
				DELEGATING.set(odop);
		}
	}

	@Override
	public boolean isSingleton(final String name)
			throws NoSuchBeanDefinitionException {
		DelegatingBean odop = DELEGATING.get();
		try {
			DELEGATING.set(new DelegatingBean(name, new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					return super$getBean(name);
				}
			}));
			return (delegatedBeanFactory != null
					&& delegatedBeanFactory.containsBean(name) && delegatedBeanFactory
						.isSingleton(name)) || super.isSingleton(name);
		} finally {
			if (odop == null)
				DELEGATING.remove();
			else
				DELEGATING.set(odop);
		}
	}

	@Override
	public boolean isPrototype(final String name)
			throws NoSuchBeanDefinitionException {
		DelegatingBean odop = DELEGATING.get();
		try {
			DELEGATING.set(new DelegatingBean(name, new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					return super$getBean(name);
				}
			}));
			return (delegatedBeanFactory != null
					&& delegatedBeanFactory.containsBean(name) && delegatedBeanFactory
						.isPrototype(name)) || super.isPrototype(name);
		} finally {
			if (odop == null)
				DELEGATING.remove();
			else
				DELEGATING.set(odop);
		}
	}

	@Override
	public boolean isTypeMatch(final String name, Class<?> targetType)
			throws NoSuchBeanDefinitionException {
		DelegatingBean odop = DELEGATING.get();
		try {
			DELEGATING.set(new DelegatingBean(name, new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					return super$getBean(name);
				}
			}));
			return (delegatedBeanFactory != null
					&& delegatedBeanFactory.containsBean(name) && delegatedBeanFactory
						.isTypeMatch(name, targetType))
					|| super.isTypeMatch(name, targetType);
		} finally {
			if (odop == null)
				DELEGATING.remove();
			else
				DELEGATING.set(odop);
		}
	}

	@Override
	public Class<?> getType(final String name)
			throws NoSuchBeanDefinitionException {
		DelegatingBean odop = DELEGATING.get();
		try {
			DELEGATING.set(new DelegatingBean(name, new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					return super$getBean(name);
				}
			}));
			Class<?> rv = null;
			if (delegatedBeanFactory != null
					&& delegatedBeanFactory.containsBean(name))
				rv = delegatedBeanFactory.getType(name);
			if (rv == null)
				rv = super.getType(name);
			return rv;
		} finally {
			if (odop == null)
				DELEGATING.remove();
			else
				DELEGATING.set(odop);
		}
	}

	@Override
	public String[] getAliases(final String name) {
		DelegatingBean odop = DELEGATING.get();
		try {
			DELEGATING.set(new DelegatingBean(name, new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					return super$getBean(name);
				}
			}));
			List<String> aliases = new java.util.LinkedList<String>();
			if (delegatedBeanFactory != null
					&& delegatedBeanFactory.containsBean(name)
					&& delegatedBeanFactory.getAliases(name) != null)
				aliases.addAll(Arrays.asList(delegatedBeanFactory
						.getAliases(name)));
			if (super.getAliases(name) != null)
				aliases.addAll(Arrays.asList(super.getAliases(name)));
			return aliases.toArray(new String[aliases.size()]);
		} finally {
			if (odop == null)
				DELEGATING.remove();
			else
				DELEGATING.set(odop);
		}
	}

}
