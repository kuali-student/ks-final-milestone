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

import javax.xml.namespace.QName;

import org.kuali.student.enumeration.service.EnumerationService;
import org.kuali.student.poc.common.ws.beans.JaxWsClientFactoryBean;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

/**
 * Properties have been loaded in EnumerationPropertiesModule, which must be installed before running this.
 * They are passed to JaxWsClientFactoryBean which returns a configured EnumerationService client object
 * 
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 */
public class EnumerationServiceProvider implements Provider<EnumerationService> {
	@Inject @Named("ks.servicelocation.EnumerationService") 
	private String address;
	@Inject @Named("wsdllocation.EnumerationService")
	private String wsdllocation;
	@Inject @Named("serviceQNameString.EnumerationService") 
	private String serviceQNameString;
	public EnumerationService get() {
		 JaxWsClientFactoryBean factory = new JaxWsClientFactoryBean();
		 factory.setServiceEndpointInterface(EnumerationService.class);
		 factory.setWsdlLocation(wsdllocation);
		 factory.setServiceName(new QName(serviceQNameString,"EnumerationService"));
		 factory.setAddress(address);
		 try {
		 return (EnumerationService) factory.getObject();
		 } catch(Exception e) {
			 throw new RuntimeException(e);
		 }
	}
}
