package org.kuali.student.poc.common.ws.security.metro;

import java.security.Principal;
import java.util.Set;

import javax.security.auth.Subject;
import javax.xml.ws.WebServiceContext;

import org.kuali.student.poc.common.ws.security.PrincipalWrapper;

import com.sun.xml.wss.XWSSecurityException;

/**
 * 
 * @author Will Gomes
 *
 */
public class PrincipalWrapperImpl extends PrincipalWrapper {
    private String name;
    
    /**
     * 
     * @see org.kuali.student.poc.common.ws.security.PrincipalWrapper#setPrincipal(javax.xml.ws.WebServiceContext)
     */
    @Override
    public void setPrincipal(WebServiceContext wsContext) {
        try {
            if (wsContext != null){
                Subject subj = com.sun.xml.wss.SubjectAccessor.getRequesterSubject(wsContext);
                Set<Principal> principals = subj.getPrincipals();
                
                if (principals != null && !principals.isEmpty()){
                    principal = principals.iterator().next();
                    System.err.println("Metro Wraper: " + principal);
                    //This is because metro returns principal name in form CN=name
                    name = principal.getName().substring(3);
                }
            } else {
                System.err.println("Web service context is null, principal not obtained.");
            }
        } catch (XWSSecurityException xwsse){
            //FIXME: What to do about this exception
        }
    }
    
    @Override
    public String getName() {
        return name;
    }
}
