/*
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.spring;

import java.util.Set;

import javax.jws.WebService;

import org.kuali.rice.ksb.api.bus.support.ServiceBusExporter;
import org.kuali.rice.ksb.api.bus.support.SoapServiceDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;

/**
 * Kuali Web Service interfaces have an @WebService annotation present.
 * 
 * This class extends the Rice {@link ServiceBusExporter} class to let the @WebService details provide the {@link SoapServiceDefinition} automatically.
 * 
 * The original class behavior still works and the annotation based functionality is only activated if no definition is provided in the xml.
 * 
 * 
 * <pre>{@code
 * 	<bean id="ks.AtpServiceExport" class="org.kuali.student.commone.spring.KualiWebServiceExporter"> 
 *       <property name="webService" ref="AtpService"/>
 *       <property name="busSecurity" value="true" />
 *</bean>}
 * </pre>
 * 
 * versus the default:
 * 
 * <pre>
 * {@code
 * <bean id="ks.AtpServiceExport" class="org.kuali.rice.ksb.api.bus.support.ServiceBusExporter">
        <property name="serviceDefinition">
            <bean class="org.kuali.rice.ksb.api.bus.support.SoapServiceDefinition">
                <property name="jaxWsService" value="true" />
                <property name="service" ref="atpEnrService" />
                <property name="serviceInterface" value="org.kuali.student.r2.core.atp.service.AtpService" />
                <property name="localServiceName" value="AtpService" />
                <property name="serviceNameSpaceURI" value="http://student.kuali.org/wsdl/atp" />
                <property name="busSecurity" value="true" />
            </bean>
        </property>
    </bean>
    }
 * </pre>
 * 
 * 
 * @author Kuali Student Team
 * 
 */
public class KualiWebServiceExporter extends ServiceBusExporter implements
        InitializingBean {

	private static Logger log = LoggerFactory
	        .getLogger(KualiWebServiceExporter.class);

	private Object webService;
	
	private boolean busSecurity = true;
	

	/**
	 */
	public KualiWebServiceExporter() {
		super();

	}

	/**
	 * @param webService
	 *            the webService to set
	 */
	public void setWebService(Object webService) {
		this.webService = webService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.rice.ksb.api.bus.support.ServiceBusExporter#afterPropertiesSet
	 * ()
	 */
	@Override
	public void afterPropertiesSet() {

		if (getServiceDefinition() == null) {

			if (webService == null)
				throw new BeanCreationException("webService object must be set.");
			
			// user did not set a definition via spring xml
			// so try and read it from the @WebService annotation
			SoapServiceDefinition def = new SoapServiceDefinition();

			def.setJaxWsService(false);
			def.setBusSecurity(busSecurity);

			Set<Class<?>> webServiceInterfaceSet = AnnotationUtils
			        .extractInterfaceContainingAnnotation(webService,
			                WebService.class);

			if (webServiceInterfaceSet.size() > 1)
				throw new BeanCreationException(
				        "multiple interfaces exist with an @WebService annotation.");
			else if (webServiceInterfaceSet.size() == 0)
				throw new BeanCreationException(
				        "no interfaces exist with an @WebService annotation");

			Class<?> webServiceInterfaceClass = webServiceInterfaceSet
			        .iterator().next();

			WebService serviceAnnotation = webServiceInterfaceClass
			        .getAnnotation(WebService.class);

			def.setService(webService);
			def.setServiceInterface(webServiceInterfaceClass.getName());

			String serviceName = serviceAnnotation.name();

			if (serviceName == null)
				throw new BeanCreationException("KualiWebServiceExporter: serviceName is not available for service: "
				        + webServiceInterfaceClass.getName());

			String namespaceUri = serviceAnnotation.targetNamespace();

			if (namespaceUri == null)
				throw new BeanCreationException("KualiWebServiceExporter: namespaceUri is not availavble for service: "
				        + webServiceInterfaceClass.getName());

			def.setLocalServiceName(serviceName);
			def.setServiceNameSpaceURI(namespaceUri);

			setServiceDefinition(def);

		}
		
		// intentionally call super after we build the ServiceDefinition from
		// the @WebService annotation
		super.afterPropertiesSet();
	}

	/**
	 * Set to false to disable security on the exported web service.
	 * 
	 * Defaults to true.
	 * 
	 * @param busSecurity the busSecurity to set
	 */
	public void setBusSecurity(boolean busSecurity) {
		this.busSecurity = busSecurity;
	}
	
	

}
