package org.kuali.student.ap.framework.config;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.xml.namespace.QName;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.config.ConfigurationException;
import org.kuali.rice.core.framework.resourceloader.BaseResourceLoader;
import org.kuali.rice.core.framework.resourceloader.SpringResourceLoader;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.AutowireCandidateResolver;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * @deprecated TODO: Integrate autowiring with Rice source, and remove this
 *             duplication.
 */
public class KsapSpringResourceLoader extends BaseResourceLoader {

	private SpringResourceLoader parentSpringResourceLoader;

	private ApplicationContext parentContext;
	private ConfigurableApplicationContext context;
	private ServletContext servletContextcontext;

	// BEGIN KSAP MOD
	private AutowireCandidateResolver autowireCandidateResolver;

	private static final ThreadLocal<PopulatingBean> POPULATING = new ThreadLocal<PopulatingBean>();

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
	 * This method may be used internally by {@link AutowireCandidateResolver},
	 * but should not be accessed externally.
	 * </p>
	 * 
	 * @return Meta-information related to the bean currently being populated.
	 */
	public static PopulatingBean getPopulatingBean() {
		return POPULATING.get();
	}

	private static class _BeanFactory extends DefaultListableBeanFactory {

		private _BeanFactory() {
		}

		private _BeanFactory(BeanFactory parentBeanFactory) {
			super(parentBeanFactory);
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
	}

	private _BeanFactory createBeanFactory() {
		_BeanFactory rv = new _BeanFactory();
		if (autowireCandidateResolver != null)
			rv.setAutowireCandidateResolver(autowireCandidateResolver);
		return rv;
	}

	private class _ApplicationContext extends GenericApplicationContext {

		private _ApplicationContext(ApplicationContext parent,
				String... resources) {
			super(createBeanFactory(), parent);
			setClassLoader(getClass().getClassLoader());
			XmlBeanDefinitionReader xr = new XmlBeanDefinitionReader(this);
			for (String rp : resources)
				xr.loadBeanDefinitions(rp);
			refresh();
		}

	}

	private void doCustomizeBeanFactory(_BeanFactory beanFactory) {
		if (autowireCandidateResolver != null) {
			if (beanFactory.getAutowireCandidateResolver() != null)
				try {
					BeanUtils.setProperty(autowireCandidateResolver,
							"delegate",
							beanFactory.getAutowireCandidateResolver());
				} catch (IllegalAccessException e) {
					throw new IllegalStateException(e);
				} catch (InvocationTargetException e) {
					if (e.getCause() instanceof RuntimeException)
						throw (RuntimeException) e.getCause();
					if (e.getCause() instanceof Error)
						throw (Error) e.getCause();
					throw new IllegalStateException(e);
				}
			beanFactory.setAutowireCandidateResolver(autowireCandidateResolver);
		}
	}

	// END KSAP MOD

	private final List<String> fileLocs;

	KsapSpringResourceLoader(QName name, List<String> fileLocs,
			ServletContext servletContextcontext,
			AutowireCandidateResolver autowireCandidateResolver) {
		super(name);
		this.fileLocs = fileLocs;
		this.servletContextcontext = servletContextcontext;
		// KSAP MOD
		this.autowireCandidateResolver = autowireCandidateResolver;
	}

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
						doCustomizeBeanFactory((_BeanFactory) beanFactory);
					}
				};
				// END KSAP MOD
				lContext.setServletContext(servletContextcontext);
				lContext.setParent(parentContext);
				lContext.setConfigLocations(this.fileLocs
						.toArray(new String[] {}));
				lContext.refresh();
				context = lContext;
			} else
				// BEGIN KSAP MOD
				this.context = new _ApplicationContext(parentContext,
						this.fileLocs.toArray(new String[this.fileLocs.size()]));

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
