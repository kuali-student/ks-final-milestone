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

package org.kuali.student.common.ws.beans;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;

import org.springframework.core.io.ClassPathResource;

public class JaxWsClientFactoryBean implements JaxWsClientFactory {
    private Class<?> serviceEndpointInterface = null;
    private String wsdlDocumentLocation = "";
    private QName serviceQName = null;
    private String serviceUrl = "";
    private static final String CLASSPATH_PREFIX = "classpath:";
    private Object client;
    
    @Override
    public synchronized Object getObject() throws Exception {
        if(client==null){
	    	URL url;
	        if (wsdlDocumentLocation.startsWith(CLASSPATH_PREFIX)) {
	            ClassPathResource cpr = new ClassPathResource(wsdlDocumentLocation.substring(CLASSPATH_PREFIX.length()));
	            url = cpr.getURL();
	        } else {
	            url = new URL(wsdlDocumentLocation);
	        }
	        Service service = Service.create(url, serviceQName);
	        client = service.getPort(serviceEndpointInterface);
	        // Override the service URL
	        if (this.serviceUrl != null && !"".equals(this.serviceUrl)) {
	            ((BindingProvider) client).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.serviceUrl);
	        }
	    }

        return client;
    }

    @Override
    public Class<?> getObjectType() {
        return serviceEndpointInterface;
    }

    /**
     * @return the serviceEndpointInterface
     */
    public Class<?> getServiceEndpointInterface() {
        return serviceEndpointInterface;
    }

    /**
     * @param serviceEndpointInterface
     *            the serviceEndpointInterface to set
     */
    public void setServiceEndpointInterface(Class<?> serviceEndpointInterface) {
        this.serviceEndpointInterface = serviceEndpointInterface;
    }

 
    /**
     * @param serviceName
     *            the serviceName to set
     */
    public void setServiceQNameString(String serviceName) {
        this.serviceQName = QName.valueOf(serviceName);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * @return the wsdlDocumentLocation
     */
    public String getWsdlLocation() {
        return wsdlDocumentLocation;
    }

    /**
     * @param wsdlDocumentLocation
     *            the wsdlDocumentLocation to set
     */
    public void setWsdlLocation(String wsdlDocumentLocation) {
        this.wsdlDocumentLocation = wsdlDocumentLocation;
    }

    /**
     * @return the serviceUrl
     */
    public String getAddress() {
        return serviceUrl;
    }

    /**
     * @param serviceUrl
     *            the serviceUrl to set
     */
    public void setAddress(String add) {
        this.serviceUrl = add;
    }

	/**
	 * @return the serviceQName
	 */
	public QName getServiceQName() {
		return serviceQName;
	}

	/**
	 * @param serviceQName the serviceQName to set
	 */
	public void setServiceQName(QName serviceQName) {
		this.serviceQName = serviceQName;
	}

	@Override
	public QName getServiceName() {
		return serviceQName;
	}

	@Override
	public void setServiceName(QName serviceName) {
		this.serviceQName = serviceName;
	}

}
