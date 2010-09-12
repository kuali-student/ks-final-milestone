/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.ws;

import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Spring Controller implementation that mimics standard
 * ServletWrappingController behaviour (see its documentation), but with the
 * important difference that it doesn't instantiate the Servlet instance
 * directly but delegate for this the BeanContext, so that we can also use IoC.*
 */
public class ServletWrappingController extends AbstractController implements
		BeanNameAware, InitializingBean, DisposableBean {
	private Class<? extends Servlet> servletClass;
	private String servletName;
	private Properties initParameters = new Properties();
	private String beanName;
	private Servlet servletInstance;

	private static org.apache.log4j.Logger log = Logger.getLogger(ServletWrappingController.class);

	
	public void setServletClass(Class<? extends Servlet> servletClass) {
		log.info("setServletClass : " + servletClass);
		this.servletClass = servletClass;
	}

	public void setServletName(String servletName) {
		log.info("setServletName : " + servletName);
		this.servletName = servletName;
	}

	public void setInitParameters(Properties initParameters) {
		log.info("setInitParameters : " + initParameters);
		this.initParameters = initParameters;
	}

	public void setBeanName(String name) {
		log.info("setBeanName : " + name);
		this.beanName = name;
	}

	public void setServletInstance(Servlet servletInstance) {
		log.info("setServletInstance : " + servletInstance);
		this.servletInstance = servletInstance;
	}

	public void afterPropertiesSet() throws Exception {
		log.info("afterPropertiesSet");
		if (this.servletInstance == null) {
			throw new IllegalArgumentException("servletInstance is required");
		}
		if (!Servlet.class.isAssignableFrom(servletInstance.getClass())) {
			throw new IllegalArgumentException("servletInstance ["
					+ this.servletClass.getName()
					+ "] needs to implement interface [javax.servlet.Servlet]");
		}
		if (this.servletName == null) {
			this.servletName = this.beanName;
		}
		this.servletInstance.init(new DelegatingServletConfig());
	}

	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("handleRequestInternal");
		try{
			this.servletInstance.service(request, response);
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}
		
		return null;
	}

	public void destroy() {
		log.info("destroy");
		this.servletInstance.destroy();
	}

	private class DelegatingServletConfig implements ServletConfig {
		public String getServletName() {
			return servletName;
		}

		public ServletContext getServletContext() {
			return getWebApplicationContext().getServletContext();
		}

		public String getInitParameter(String paramName) {
			return initParameters.getProperty(paramName);

		}

		public Enumeration<?> getInitParameterNames() {
			return initParameters.keys();
		}
	}
}
