package org.kuali.student.common.ws.security;

import javax.xml.ws.WebServiceContext;

import org.apache.log4j.Logger;

/**
 * 
 * @author Will Gomes
 *
 */
public class PrincipalAccessor {
    
    final static Logger logger = Logger.getLogger(PrincipalAccessor.class);
    
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
                logger.error("PrincpalWraper implementaion could not be found.");
            }
        } 
        
        try{
            principal = (PrincipalWrapper)principalWrapperImpl.newInstance();
            principal.setPrincipal(wsContext);
        } catch (Exception e){
            logger.error("Exception occured: ", e);            
        }
            
        return principal;
    }
}
