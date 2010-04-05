package org.kuali.rice.student.ksb.messaging;

import org.springframework.beans.factory.FactoryBean;

public class KSBClientProxyFactoryBean implements FactoryBean {

    private Class<Object> serviceEndpointInterface;
    private String serviceQName;
    
    public Object getObject() throws Exception {
        return KSBClientProxy.newInstance(serviceQName, serviceEndpointInterface);
    }

    public Class<Object> getObjectType() {
        return serviceEndpointInterface;
    }

    public boolean isSingleton() {
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
