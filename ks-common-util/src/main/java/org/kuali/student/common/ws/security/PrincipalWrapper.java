package org.kuali.student.common.ws.security;

import java.security.Principal;

import javax.xml.ws.WebServiceContext;

/**
 * This can be used to get the principal from a WebServiceContext based on the 
 * jaxws implementation used 
 * 
 * @author Will Gomes
 *
 */
public abstract class PrincipalWrapper implements Principal{

    protected Principal principal;
        
    public String getName(){
        if (principal != null){
            return principal.getName(); 
        }
        return null;
    }
    
    public String getPersonId(){
        return AuthenticationService.getPersonIdForUsername(getName());
    }

    public Principal getPrincipal() {
        return principal;
    }

    //Use this to set principal from web service context
    public abstract void setPrincipal(WebServiceContext wsContext);    
    
}
