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
package org.kuali.student.common.ws.beans;

import javax.xml.namespace.QName;

import org.springframework.beans.factory.FactoryBean;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 */
public interface JaxWsClientFactory extends FactoryBean{
    
    public Class<?> getServiceEndpointInterface();

    /**
     * @param serviceEndpointInterface
     *            the serviceEndpointInterface to set
     */
    public void setServiceEndpointInterface(
            Class<?> serviceEndpointInterface);

    /**
     * @return the serviceName
     */
    public QName getServiceName();

    /**
     * @param serviceName
     *            the serviceName to set
     */
    public void setServiceName(QName serviceName);

    /**
     * @return the wsdlDocumentLocation
     */
    public String getWsdlLocation();

    /**
     * @param wsdlDocumentLocation the wsdlDocumentLocation to set
     */
    public void setWsdlLocation(String wsdlLocation);

    /**
     * @return the service address
     */
    public String getAddress();

    /**
     * @param add the service address to set
     */
    public void setAddress(String add);
    
}
