package org.kuali.student.common.ws.security;

import javax.xml.ws.WebServiceContext;

/**
 * 
 * @author Will Gomes
 *
 */
public class PrincipalAccessor {
    public static PrincipalWrapper getPrincipalFromWebServiceContext(WebServiceContext wsContext){
        Class<?> principalWrapperImpl = null;
        PrincipalWrapper principal = null;
                
        try {
            principalWrapperImpl =  
                (Class.forName("org.kuali.student.common.cxf.security.PrincipalWrapperImpl"));
        } catch (ClassNotFoundException cnfe){
            try{
                principalWrapperImpl = 
                    Class.forName("org.kuali.student.common.metro.security.PrincipalWrapperImpl");
            } catch (ClassNotFoundException cnfe2){
                System.err.println("PrincpalWraper implementaion could not be found.");
            }
        } 
        
        try{
            principal = (PrincipalWrapper)principalWrapperImpl.newInstance();
            principal.setPrincipal(wsContext);
        } catch (Exception e){
            e.printStackTrace();            
        }
            
        return principal;
    }
}
