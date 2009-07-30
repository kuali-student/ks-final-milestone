package org.kuali.student.common.ws.beans;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.kuali.student.security.cxf.interceptors.SamlTokenCxfInInterceptor;

public class CxfJaxWsProxyClientFactory extends JaxWsProxyFactoryBean implements JaxWsClientFactory {

    @Override
    public Class<?> getServiceEndpointInterface() {
        return super.getServiceClass();
    }

    @Override
    public void setServiceEndpointInterface(Class<?> serviceEndpointInterface) {
        super.setServiceClass(serviceEndpointInterface);  
    }

    @Override
    public Object getObject() throws Exception {
        
        Map<String,Object> inProps= new HashMap<String,Object>();
        
        //inProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.SAML_TOKEN_SIGNED);

        SamlTokenCxfInInterceptor samlTokenInInterceptor = new SamlTokenCxfInInterceptor();
        //stih.setProperties(inProps);
        
        this.getInInterceptors().add(samlTokenInInterceptor);
        
        return super.create();
    }

    @Override
    public Class<?> getObjectType() {
        return super.getServiceClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
    
    public void setServiceQNameString(String serviceName) {
        super.setServiceName(QName.valueOf(serviceName));
    }

}
