/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enumeration.util.di;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javax.servlet.ServletContext;//Guice 1.0
import javax.servlet.ServletContextEvent;//Guice 1.0
import javax.servlet.ServletContextListener;//Guice 1.0
//Guice 2.0 import com.google.inject.servlet.GuiceServletContextListener; 


/**
 * This is a ServletContextListener to create a Guice injector for EnumerationService
 * In Guice 2.0 there will be a GuiceServletContextListener abstract class which this class 
 * should extend and implement just the getInjector method when Guice 2 is released.
 * 
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 */
//Guice 2.0 public class EnumerationWebServletContextListener extends GuiceServletContextListener  {
public class EnumerationWebServletContextListener implements ServletContextListener  {

	public static final String KEY = Injector.class.getName();
	/**
	 * This constructs a ServletContextListener that creates a Guice injector
	 * 
	 */
	public EnumerationWebServletContextListener() {}
	//Guice 1.0
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();
		servletContext.setAttribute(KEY, getInjector());
	}
	//Guice 1.0
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();
		servletContext.removeAttribute(KEY);
	}

	/**
	 * ServletContextListener creates an Injector at Servlet init() 
	 * 
	 * @see com.google.inject.servlet.GuiceServletContextListener#getInjector()
	 */
	//Guice 2.0	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new EnumerationServiceModule());

	}

}
