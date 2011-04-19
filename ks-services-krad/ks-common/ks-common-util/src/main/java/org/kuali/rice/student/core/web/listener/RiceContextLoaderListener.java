/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.rice.student.core.web.listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.kuali.rice.core.config.ConfigContext;
import org.kuali.rice.core.config.SimpleConfig;
import org.kuali.rice.core.exception.RiceRuntimeException;
import org.kuali.rice.core.resourceloader.RiceResourceLoaderFactory;
import org.kuali.rice.core.resourceloader.SpringResourceLoader;
import org.kuali.rice.core.util.JSTLConstants;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.ksb.messaging.MessageFetcher;
import org.kuali.rice.ksb.service.KSBServiceLocator;
import org.kuali.rice.student.core.web.context.RiceWebApplicationContextProxy;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class RiceContextLoaderListener implements ServletContextListener {

	public static final String CATALINA_BASE = "catalina.base";
	public static final String RICE_BASE = "rice.base";
	public static final String RICE_STANDALONE_EXECUTE_MESSAGE_FETCHER = "rice.standalone.execute.messageFetcher";

	private static final String DEFAULT_LOG4J_CONFIG = "org/kuali/rice/core/logging/default-log4j.properties";
	private static final String LOG4J_CONFIG_PARAM = "log4j.config.file.location";
	private static final String DEFAULT_SPRING_BEANS = "classpath:org/kuali/rice/standalone/config/StandaloneSpringBeans.xml";
	private static final String DEFAULT_SPRING_BEANS_REPLACEMENT_VALUE = "${bootstrap.spring.file}";

	private static final Logger LOG = Logger.getLogger(RiceContextLoaderListener.class);

	private ConfigurableApplicationContext context = null;

	/**
	 * ServletContextListener interface implementation that schedules the start of the lifecycle
	 */
	public void contextInitialized(ServletContextEvent sce) {
		long startInit = System.currentTimeMillis();

		initLog4j(sce);

		LOG.info("Initializing Kuali Rice Standalone...");

		sce.getServletContext().setAttribute("Constants", new JSTLConstants(KEWConstants.class));

		try {
			initConfig(sce.getServletContext());

			context = new ClassPathXmlApplicationContext(lookupSpringContextFile(sce.getServletContext()));
			context.start();

			initWebContext(sce.getServletContext());

			if (shouldExecuteMessageFetcher()) {
				// execute the MessageFetcher to grab any messages that were being processed
				// when the system shut down originally
				MessageFetcher messageFetcher = new MessageFetcher((Integer) null);
				KSBServiceLocator.getThreadPool().execute(messageFetcher);
			}
		} catch (Exception e) {
			LOG.fatal("Kuali Rice Standalone startup failed!", e);
			throw new RuntimeException("Startup failed.  Exiting.", e);
		}
		long endInit = System.currentTimeMillis();
		LOG.info("...Kuali Rice Standalone successfully initialized, startup took " + (endInit - startInit) + " ms.");
	}

	public void contextDestroyed(ServletContextEvent sce) {
		if (context != null) {
			context.close();
		}

		sce.getServletContext().removeAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

	}

	protected void initLog4j(ServletContextEvent sce) {
		try {
			Properties p = new Properties();
			InputStream log4jProperties = null;
			String log4jConfigFile = sce.getServletContext().getInitParameter(LOG4J_CONFIG_PARAM);
			
			if(log4jConfigFile != null){
				try{
					log4jProperties = new FileInputStream(log4jConfigFile);
				}catch(FileNotFoundException e){
					System.out.println("Context Parameter "+LOG4J_CONFIG_PARAM+" was set to '"+log4jConfigFile+"', but not file was found. Using default logging properties instead.");
				}
			}

			if(log4jProperties == null){
				log4jProperties = getClass().getClassLoader().getResourceAsStream(DEFAULT_LOG4J_CONFIG);
			}
			
			p.load(log4jProperties);
			PropertyConfigurator.configure(p);
		} catch (Exception e) {
			// if there is an issue initializing logging system, let's be sure to print the stack trace so we can debug!
			e.printStackTrace();
			throw new RiceRuntimeException(e);
		}
	}

	protected void initConfig(ServletContext servletContext) throws IOException {
		String basePath = findBasePath(servletContext);
		Properties baseProps = new Properties();
		baseProps.putAll(getContextParameters(servletContext));
		baseProps.putAll(System.getProperties());
		baseProps.setProperty(RICE_BASE, basePath);

		// HACK: need to determine best way to do this...
		// if the additional config locations property is empty then we need
		// to explicitly set it so that if we use it in a root config
		// a value (an empty value) can be found, and the config parser
		// won't blow up because "additional.config.locations" property
		// cannot be resolved
		// An alternative to doing this at the application/module level would
		// be to push this functionality down into the Rice ConfigFactoryBean
		// e.g., by writing a simple ResourceFactoryBean that would conditionally
		// expose the resource, and then plugging the Resource into the ConfigFactoryBean
		// However, currently, the ConfigFactoryBean operates on String locations, not
		// Resources. Spring can coerce string <value>s into Resources, but not vice-versa
		String additionalConfigLocations = baseProps.getProperty(KEWConstants.ADDITIONAL_CONFIG_LOCATIONS_PARAM);
		if (StringUtils.isEmpty(additionalConfigLocations)) {
			baseProps.setProperty(KEWConstants.ADDITIONAL_CONFIG_LOCATIONS_PARAM, "");
		}
		SimpleConfig config = new SimpleConfig(baseProps);
		config.parseConfig();
		ConfigContext.init(config);
	}

	/**
	 * Translates context parameters from the web.xml (and container) into entries in a Properties file.
	 */
	protected Properties getContextParameters(ServletContext context) {
		Properties properties = new Properties();
		Enumeration<?> paramNames = context.getInitParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			properties.put(paramName, context.getInitParameter(paramName));
		}
		return properties;
	}

	/**
	 * Checks for spring context file location parameters. Checks for kew settings, then standard spring context init
	 * param, then defaults to StandaloneSpringBeans.xml.
	 * 
	 * @param servletContext
	 * @return file location to use for root spring context.
	 */
	protected String lookupSpringContextFile(ServletContext servletContext) {
		String bootstrapSpringBeans = null;

		if (!StringUtils.isBlank(System.getProperty(KEWConstants.BOOTSTRAP_SPRING_FILE))) {
			bootstrapSpringBeans = System.getProperty(KEWConstants.BOOTSTRAP_SPRING_FILE);
		} else {
			String bootstrapSpringInitParam = servletContext.getInitParameter(KEWConstants.BOOTSTRAP_SPRING_FILE);

			if (!StringUtils.isBlank(bootstrapSpringInitParam) && !DEFAULT_SPRING_BEANS_REPLACEMENT_VALUE.equals(bootstrapSpringInitParam)) {
				bootstrapSpringBeans = bootstrapSpringInitParam;
				LOG.info("Found bootstrap Spring Beans file defined in servlet context: " + bootstrapSpringBeans);
			} else if (!StringUtils.isBlank(servletContext.getInitParameter(ContextLoader.CONFIG_LOCATION_PARAM))) {
				bootstrapSpringBeans = servletContext.getInitParameter(ContextLoader.CONFIG_LOCATION_PARAM);
			} else {
				bootstrapSpringBeans = DEFAULT_SPRING_BEANS;
			}
		}

		return bootstrapSpringBeans;
	}

	protected void initWebContext(ServletContext servletContext) {

		if (servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE) != null) {
			throw new IllegalStateException("Cannot set Rice Root WebApplication context because one is already present - " + "check whether you have other Spring ContextLoaderListeners in your web.xml!");
		}

		SpringResourceLoader springResourceLoader = RiceResourceLoaderFactory.getSpringResourceLoaders().iterator().next();
		ConfigurableApplicationContext rootContext = springResourceLoader.getContext();

		RiceWebApplicationContextProxy webContext = new RiceWebApplicationContextProxy();
		webContext.setApplicationContext(rootContext);
		webContext.setServletContext(servletContext);

		// -----------------------------------------------------------------------------
		// this is the initial commit, so leaving this untested way commented out
		// -----------------------------------------------------------------------------
		// GenericWebApplicationContext webContext;
		// webContext = new GenericWebApplicationContext((DefaultListableBeanFactory)rootContext.getBeanFactory());
		// webContext.setServletContext(servletContext);
		//
		// // this doesn't seem to actually recreate any beans created by the rootContext, though
		// // it does log an instantiating singletons message.
		// webContext.refresh();
		// -----------------------------------------------------------------------------

		servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, webContext);

	}

	/**
	 * Determines whether or not the Message Fetcher should be executed.
	 */
	protected boolean shouldExecuteMessageFetcher() {
		String executeMessageFetcher = ConfigContext.getCurrentContextConfig().getProperty(RICE_STANDALONE_EXECUTE_MESSAGE_FETCHER);
		return StringUtils.isBlank(executeMessageFetcher) ? true : Boolean.valueOf((executeMessageFetcher));
	}

	protected String findBasePath(ServletContext servletContext) {
		String realPath = servletContext.getRealPath("/");
		// if cannot obtain real path (because, e.g., deployed as WAR
		// try a reasonable guess
		if (realPath == null) {
			if (System.getProperty(CATALINA_BASE) != null) {
				realPath = System.getProperty(CATALINA_BASE);
			} else {
				realPath = ".";
			}
		}
		String basePath = new File(realPath).getAbsolutePath();
		// append a trailing path separator to make relatives paths work in conjunction
		// with empty ("current working directory") basePath
		if (basePath.length() > 0 && !basePath.endsWith(File.separator)) {
			basePath += File.separator;
		}
		return basePath;
	}

}
