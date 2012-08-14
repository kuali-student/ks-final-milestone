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

package org.kuali.student.common.test.spring;

import java.io.File;

import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.notification.RunNotifier;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.DefaultHandler;
import org.mortbay.jetty.handler.HandlerCollection;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class IntegrationServiceTestClassRunner extends JUnit4ClassRunner {
	final static Logger logger = LoggerFactory.getLogger(IntegrationServiceTestClassRunner.class);

	private Class<?> testClass;
	private Server server;
	private String webAppPath;
	private String contextPath;
	private int port = 9090;

	public IntegrationServiceTestClassRunner(Class<?> klass) throws InitializationError {
		super(klass);
		testClass = klass;
	}

	private void getAnnotations() {
		IntegrationServer webapp = this.testClass.getAnnotation(IntegrationServer.class);
		this.port = webapp.port();
		this.webAppPath = webapp.webappPath();
		this.contextPath = webapp.contextPath();

		if (logger.isDebugEnabled()) {
			logger.debug("port="+this.port);
			logger.debug("webAppPath="+this.webAppPath);
			logger.debug("contextPath="+this.contextPath);
		}
	}
	
	private void setProperties() {
		if (System.getProperty("catalina.base") == null) {
			System.setProperty("catalina.base", "./target");
		}

		SystemProperties systemProperties = this.testClass.getAnnotation(SystemProperties.class);
		if (systemProperties != null) {
			for(Property property : systemProperties.properties()) {
				System.setProperty(property.key(), property.value());
			}
		}
	}

	@Override
	public void run(RunNotifier notifier) {
		startServer();
		super.run(notifier);
		stopServer();
	}
	
	private void startServer() {
		getAnnotations();
		setProperties();
		
		this.server = new Server();
		Connector connector = new SelectChannelConnector();
		connector.setPort(this.port);
		this.server.setConnectors(new Connector[] { connector });

		// Metro: Additional debugging info to console
		// com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump=true;      

		String root = IntegrationServiceTestClassRunner.class.getResource("/").getPath();
		File webAppsPath = new File(root + this.webAppPath);

		try {
		    if(!webAppsPath.isDirectory()) {
		    	throw new RuntimeException("Webapps directory does not exist. " +
		    			"Webapps directory must be an exploded (war) directory: " + 
		    			webAppsPath.getCanonicalPath());
		    }
					
			if (logger.isDebugEnabled()) {
				logger.debug("WebApps Path="+webAppsPath.getCanonicalPath());
			}
	
			WebAppContext webAppcontext = new WebAppContext();
			webAppcontext.setParentLoaderPriority(true);
			webAppcontext.setContextPath(this.contextPath); // e.g. /brms-ws-0.1.0-SNAPSHOT
			webAppcontext.setWar(webAppsPath.getCanonicalPath());
			//webAppcontext.setTempDirectory(new File(root));
	
			HandlerCollection handlers = new HandlerCollection();
			handlers.setHandlers(new Handler[] { webAppcontext, new DefaultHandler() });
			this.server.setHandler(handlers);

			this.server.start();
		} catch (Exception e) {
			throw new RuntimeException("Starting Jetty server failed", e);
		}
	}

	private void stopServer() {
		try {
			this.server.stop();
		} catch (Exception e) {
			throw new RuntimeException("Stopping Jetty server failed", e);
		}
	}
}
