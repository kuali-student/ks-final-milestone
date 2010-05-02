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

import java.lang.reflect.Field;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import javax.jws.WebService;
import javax.servlet.Servlet;
import javax.xml.namespace.QName;

import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.notification.RunNotifier;
import org.kuali.student.common.ws.beans.JaxWsClientFactory;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.jetty.servlet.ServletMapping;
import org.springframework.web.context.ContextLoaderListener;

public class ServiceTestClassRunner extends JUnit4ClassRunner {
	private final Class<?> testImplClass;
	private Server server;

	public ServiceTestClassRunner(Class<?> klass) throws InitializationError {
		super(klass);
		testImplClass = klass;

	}

	/**
	 * Creates a client and injects it into the test class based on the
	 * &#064;Client annotations
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.junit.internal.runners.JUnit4ClassRunner#createTest()
	 */
	@Override
	protected Object createTest() throws Exception {
		Object instance = super.createTest();
		try {
			for (Field f : instance.getClass().getDeclaredFields()) {
				if (f.isAnnotationPresent(Client.class)) {
					Client a = f.getAnnotation(Client.class);
					String port = System.getProperty("ks.test.port");
					
					Class<?> serviceImplClass = Class.forName(a.value());
					String serviceName = "";
					String serviceNamespaceURI = "";
					String serviceWsdlLocation = "http://localhost:" + port
							+ "/Service/Service" + (a.secure() ? "Secure" : "")
							+ "?wsdl";
					String serviceAddress = "http://localhost:" + port
							+ "/Service/Service" + (a.secure() ? "Secure" : "");

					if (serviceImplClass.isAnnotationPresent(WebService.class)) {
						WebService wsAnnotation = serviceImplClass
								.getAnnotation(WebService.class);
						serviceName = wsAnnotation.serviceName();
						serviceNamespaceURI = wsAnnotation.targetNamespace();
					}

					Class<?> clientImplClass = Class
							.forName("org.kuali.student.common.ws.beans.JaxWsClientFactoryBean");

					if (a.secure()) {
						try {
							clientImplClass = Class
									.forName("org.kuali.student.common.cxf.security.SecureJaxWsProxyFactoryBean");
						} catch (ClassNotFoundException cnfe) {
						}
					}

					JaxWsClientFactory clientFactory = (JaxWsClientFactory) clientImplClass
							.newInstance();

					clientFactory.setServiceEndpointInterface(f.getType());
					clientFactory.setServiceName(new QName(serviceNamespaceURI,
							serviceName));
					clientFactory.setWsdlLocation(serviceWsdlLocation);
					clientFactory.setAddress(serviceAddress);

					f.setAccessible(true);
					f.set(instance, clientFactory.getObject());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return instance;
	}

	/**
	 * Start a server based on the service Impl &#064;WebService annotaions and
	 * the test class &#064;
	 */
	private void startServer() {
		try {
			// Set the default Port
			if (System.getProperty("ks.test.default.port")==null||System.getProperty("ks.test.default.port").isEmpty()){
				System.setProperty("ks.test.port", "9191");
			} else {
				System.setProperty("ks.test.port", System.getProperty("ks.test.default.port"));
			}

			// Grab the client annotation and set the service implementation and
			// port as system properties
			for (Field f : testImplClass.getDeclaredFields()) {
				if (f.isAnnotationPresent(Client.class)) {
					Client a = f.getAnnotation(Client.class);
					if (a.secure()) {
						System.setProperty("ks.test.serviceImplSecure", a
								.value());
					} else {
						System.setProperty("ks.test.serviceImplClass", a
								.value());
					}
					if(a.port()!=null&&!a.port().isEmpty()){
						System.setProperty("ks.test.port", a.port());
					}
					if(!"".equals(a.additionalContextFile())){
						System.setProperty("ks.test.additionalContextFile", a.additionalContextFile());
					}else{
						System.setProperty("ks.test.additionalContextFile", "classpath:*noSuchContextFile.xml");
					}
				}
			}

			// If no secure client defined, set secure service endpoint impl
			// to be same as non-secure endpoint impl
			if (System.getProperty("ks.test.serviceImplSecure") == null) {
				System.setProperty("ks.test.serviceImplSecure", System
						.getProperty("ks.test.serviceImplClass"));
			}

			// Grab the persistence context loacation or set a default value
			if (testImplClass
					.isAnnotationPresent(PersistenceFileLocation.class)) {
				PersistenceFileLocation a = testImplClass
						.getAnnotation(PersistenceFileLocation.class);
				System.setProperty("ks.test.persistenceLocation", a.value());
			} else {
				System.setProperty("ks.test.persistenceLocation",
						"classpath:META-INF/persistence.xml");
			}

			// Grab the Dao information and pass it to a System variable
			Daos daos = testImplClass.getAnnotation(Daos.class);

			String daoImpls = "";
			if (daos != null) {
				int i = 1;
				for (Dao dao : daos.value()) {
					daoImpls += dao.value() + "|" + dao.testDataFile() +"|" + dao.testSqlFile()  ;
					if (i < daos.value().length) {
						daoImpls += ",";
					}
					i++;
				}
			}
			System.setProperty("ks.test.daoImplClasses", daoImpls);
			
			server = new Server(Integer.valueOf(System
					.getProperty("ks.test.port")));

			Context context = new Context();
			ServletHandler servletHandler = new ServletHandler();
			ServletHolder servletHolder = new ServletHolder();
			ServletMapping servletMapping = new ServletMapping();
			ContextLoaderListener contextLoaderListener = new ContextLoaderListener();

			
            Class<?> servletClass;
			String wsEngine;
			try{
			    servletClass = Class.forName("org.apache.cxf.transport.servlet.CXFServlet");		    
			    wsEngine = "cxf";
			} catch (ClassNotFoundException e) {
	            servletClass = Class.forName("com.sun.xml.ws.transport.http.servlet.WSSpringServlet");
				wsEngine = "jaxws";
			}

			Servlet servlet = (Servlet) servletClass.newInstance();

			servletHolder.setName("Service");
			servletHolder.setServlet(servlet);
			servletHandler.setServlets(new ServletHolder[] { servletHolder });

			servletMapping.setPathSpec("/*");
			servletMapping.setServletName("Service");
			servletHandler
					.setServletMappings(new ServletMapping[] { servletMapping });

			context.setContextPath("/Service");
			context.setResourceBase("src/test/resources");

			Map<String, String> initParams = new HashMap<String, String>();
			
			//Set the context config location
			String contextConfigLocation = "classpath:META-INF/" + wsEngine + "-context.xml";
			if(!"".equals(daoImpls)){
				//If there are Daos defined, add the dao context file
				contextConfigLocation +="\nclasspath:META-INF/default-dao-context-test.xml";
			}
			
			initParams.put("contextConfigLocation", contextConfigLocation);
			initParams.put("log4jConfigLocation", "log4j.properties");
			context.setInitParams(initParams);
			context
					.setEventListeners(new EventListener[] { contextLoaderListener });
			context.setServletHandler(servletHandler);

			server.setHandler(context);

			server.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.junit.internal.runners.JUnit4ClassRunner#run(org.junit.runner.notification.RunNotifier)
	 */
	@Override
	public void run(RunNotifier notifier) {
		startServer();
		super.run(notifier);
		stopServer();
	}

	private void stopServer() {
		try {
			server.stop();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
