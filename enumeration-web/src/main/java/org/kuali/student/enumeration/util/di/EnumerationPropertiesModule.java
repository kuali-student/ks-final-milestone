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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.kuali.student.enumeration.service.EnumerationService;
import org.kuali.student.enumeration.service.impl.EnumerationServiceImpl;

import com.google.inject.Binder;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * This is a description of what this class does - kuali don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kuali-rice@googlegroups.com)
 *
 */
public class EnumerationPropertiesModule extends AbstractModule
 {

	/**
	 * Read properties file, bind to Guice by key values in file
	 * 
	 * 
	 */
	public void configure() {
		  InputStream stream =
			  EnumerationPropertiesModule.class.getResourceAsStream("/application.properties");
		  Properties appProperties = new Properties();
		  try {
		    appProperties.load(stream);
		    Names.bindProperties(binder(), appProperties);
		  } catch (IOException e) {
		    // This is the preferred way to tell Guice something went wrong
		    binder().addError("Could not load EnumerationService application properties", e);
		  }
	}
}
