/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
