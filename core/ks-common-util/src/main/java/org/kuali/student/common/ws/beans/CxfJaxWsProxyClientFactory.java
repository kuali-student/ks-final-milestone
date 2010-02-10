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

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.kuali.student.security.cxf.interceptors.SamlTokenCxfInInterceptor;

public class CxfJaxWsProxyClientFactory extends JaxWsProxyFactoryBean implements JaxWsClientFactory {

    private SamlTokenCxfInInterceptor samlTokenInInterceptor;
    
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
        
        //Map<String,Object> inProps= new HashMap<String,Object>();
        // If ignore actions is false (default) then the actions have to match the security elements processed
        // in the header. Must list the actions in reverse order of the elements appearance in the header. 
        
        // The problem is that SAML_TOKEN_SIGNED is not working when inserting a signed saml in the header, unless setIgnoreActions(true).
        // I've asked wss4j mailing list and they have confirmed that this is an issue.
        // We could set SAML_TOKEN_UNSIGNED. with default setIgnoreActions(false) but it implies the the signature is not verified.
        //inProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.SIGNATURE  + " " + WSHandlerConstants.SAML_TOKEN_SIGNED);
        //inProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.SIGNATURE  + " " + WSHandlerConstants.SAML_TOKEN_UNSIGNED);
        //inProps.put(WSHandlerConstants.SIG_PROP_FILE, "crypto.properties");

        //SamlTokenCxfInInterceptor samlTokenInInterceptor = new SamlTokenCxfInInterceptor();
        //samlTokenInInterceptor.setIgnoreActions(true);
        //samlTokenInInterceptor.setProperties(inProps);
        
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

    public SamlTokenCxfInInterceptor getSamlTokenInInterceptor() {
        return samlTokenInInterceptor;
    }

    public void setSamlTokenInInterceptor(SamlTokenCxfInInterceptor samlTokenInInterceptor) {
        this.samlTokenInInterceptor = samlTokenInInterceptor;
    }

}
