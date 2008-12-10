package org.kuali.student.poc.common.test.spring;

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

public class IntegrationServiceTestClassRunner extends JUnit4ClassRunner {
	final static Logger logger = LoggerFactory.getLogger(IntegrationServiceTestClassRunner.class);

	private Class<?> testClass;
	private Server server;
	private String warPath;
	private String contextPath;
	private int port = 9000;

	public IntegrationServiceTestClassRunner(Class<?> klass) throws InitializationError {
		super(klass);
		testClass = klass;
	}

	private void getAnnotations() {
		IntegrationServer warClient = this.testClass.getAnnotation(IntegrationServer.class);
		this.port = warClient.port();
		this.warPath = warClient.warPath();
		this.contextPath = warClient.contextPath();

		if (logger.isDebugEnabled()) {
			logger.debug("port="+this.port);
			logger.debug("warPath="+this.warPath);
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
		File webAppsPath = new File(root + this.warPath);

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
