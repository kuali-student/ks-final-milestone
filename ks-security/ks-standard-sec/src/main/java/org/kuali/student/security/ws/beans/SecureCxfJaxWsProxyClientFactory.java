package org.kuali.student.security.ws.beans;

import org.kuali.student.common.ws.beans.CxfJaxWsProxyClientFactory;
import org.kuali.student.security.cxf.interceptors.SamlTokenCxfInInterceptor;

public class SecureCxfJaxWsProxyClientFactory extends CxfJaxWsProxyClientFactory {
    private SamlTokenCxfInInterceptor samlTokenInInterceptor;
    
    @Override
    public Object getObject() throws Exception {
        
        if(samlTokenInInterceptor != null){
            this.getInInterceptors().add(samlTokenInInterceptor);
        }
        
        return super.create();
    }

    public SamlTokenCxfInInterceptor getSamlTokenInInterceptor() {
        return samlTokenInInterceptor;
    }

    public void setSamlTokenInInterceptor(SamlTokenCxfInInterceptor samlTokenInInterceptor) {
        this.samlTokenInInterceptor = samlTokenInInterceptor;
    }
	
}
