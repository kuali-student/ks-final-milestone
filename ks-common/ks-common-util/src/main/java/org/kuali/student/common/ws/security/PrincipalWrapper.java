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
