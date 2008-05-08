package org.kuali.student.poc.common.ws.security;

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
                
        System.err.print("Getting principal.");
        try {
            principalWrapperImpl =  
                (Class.forName("org.kuali.student.poc.common.cxf.security.PrincipalWrapperImpl"));
        } catch (ClassNotFoundException cnfe){
            System.err.print("CXF Principal failed.");
            try{
                principalWrapperImpl = 
                    Class.forName("org.kuali.student.poc.common.metro.security.PrincipalWrapperImpl");
            } catch (ClassNotFoundException cnfe2){
                System.err.print("PrincpalWraper impl could not be obtained.");
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
