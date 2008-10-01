package org.kuali.student.poc.common.ws.security.cxf;

import java.util.Map;
import java.util.TreeMap;

import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.kuali.student.poc.common.ws.beans.JaxWsClientFactory;

public class SecureJaxWsProxyFactoryBean extends JaxWsProxyFactoryBean implements JaxWsClientFactory{
    
    @Override
    public Class<?> getObjectType() {
        return super.getServiceClass();
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public Object getObject() throws Exception {
        Map<String, Object>     pMap    = new TreeMap<String,Object>();                 
        WSS4JOutInterceptor wss4jOut = new WSS4JOutInterceptor();
        
        pMap.put("action", "UsernameToken");
        pMap.put("passwordType", "PasswordDigest");
        pMap.put("passwordCallbackRef", new AcegiClientPasswordCallback());
        wss4jOut.setProperties(pMap);

        this.getOutInterceptors().add(new SAAJOutInterceptor());
        this.getOutInterceptors().add(new AcegiUsernameOutInterceptor());
        this.getOutInterceptors().add(wss4jOut);

        return super.create();
    }

    /**
     * @param serviceEndpointInterface
     *            the serviceEndpointInterface to set
     */
    @Override
    public void setServiceEndpointInterface(
            Class<?> serviceEndpointInterface) {
        super.setServiceClass(serviceEndpointInterface);
    }

    @Override
    public Class<?> getServiceEndpointInterface() {        
        return super.getServiceClass();
    }

}
