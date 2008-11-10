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
package edu.umd.ks.poc.util;

import org.springframework.beans.factory.FactoryBean;

/**
 * This class can be used to create a KSBProxy 
 *
 */
public class KSBClientProxyFactory implements FactoryBean {
    private Class<Object> serviceEndpointInterface;
    private String serviceQName;
    
    @Override
    public Object getObject() throws Exception {
        return KSBClientProxy.newInstance(serviceQName, serviceEndpointInterface);
    }

    @Override
    public Class<Object> getObjectType() {
        return serviceEndpointInterface;
    }

    @Override
    public boolean isSingleton() {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        return false;
    }

    public Class<Object> getServiceEndpointInterface() {
        return serviceEndpointInterface;
    }

    public void setServiceEndpointInterface(Class<Object> serviceInterface) {
        this.serviceEndpointInterface = serviceInterface;
    }

    public String getServiceQName() {
        return serviceQName;
    }

    public void setServiceQName(String serviceQName) {
        this.serviceQName = serviceQName;
    }

}
