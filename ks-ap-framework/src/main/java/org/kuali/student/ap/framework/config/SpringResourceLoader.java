package org.kuali.student.ap.framework.config;

import java.util.List;

import javax.servlet.ServletContext;
import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.config.ConfigurationException;
import org.kuali.rice.core.framework.resourceloader.BaseResourceLoader;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.AutowireCandidateResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

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
	private final AutowireCandidateResolver autowireCandidateResolver;
	private final BeanFactory delegatedBeanFactory;

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

			// BEGIN KSAP MOD
			if (servletContextcontext != null) {
				RiceWebApplicationContext lContext = new RiceWebApplicationContext(
						servletContextcontext,
						this.fileLocs.toArray(new String[this.fileLocs.size()]),
						parentContext, autowireCandidateResolver,
						delegatedBeanFactory);
				lContext.refresh();
				context = lContext;
			} else {
				RiceApplicationContext lContext = new RiceApplicationContext(
						this.fileLocs.toArray(new String[this.fileLocs.size()]),
						parentContext, autowireCandidateResolver,
						delegatedBeanFactory);
				lContext.refresh();
				this.context = lContext;
			}
			// END KSAP MOD
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
