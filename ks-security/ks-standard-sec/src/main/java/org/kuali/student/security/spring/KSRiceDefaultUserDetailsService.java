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

package org.kuali.student.security.spring;

import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.student.common.util.security.UserWithId;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * This is a description of what this class does. 
 * 
 * @author Kuali Student Team
 *
 */
public class KSRiceDefaultUserDetailsService extends KSDefaultUserDetailsService{
	
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	return this.getUserDetails(username, null);
    }
    
    public UserDetails loadUserByUsernameAndToken(String username, UsernamePasswordAuthenticationToken authentication) throws UsernameNotFoundException {
        return this.getUserDetails(username, authentication);
    }
    
    protected UserDetails getUserDetails(String username, UsernamePasswordAuthenticationToken authentication) throws UsernameNotFoundException {
        if(username==null || username.equals("")){
            throw new UsernameNotFoundException("Username cannot be null or empty");
        }                
                               
        Principal kimPrincipalInfo = null;
        kimPrincipalInfo = getIdentityService().getPrincipalByPrincipalName(username);

        String userId;
        String password; 
        if (null != kimPrincipalInfo) {
            username = kimPrincipalInfo.getPrincipalName();
            userId = kimPrincipalInfo.getPrincipalId();
            if(authentication == null){
            	//password = kimPrincipalInfo.getPassword();
                password = null;
            }else{
            	// How will this affect CAS?
            	//password = kimPrincipalInfo.getPassword();
            	password = (String)authentication.getCredentials();
            }
        } else {
        // When a UsernameNotFoundException is thrown, spring security will proceed to the next AuthenticationProvider on the list.
        // When Rice is running and username is not found in KIM, we want authentication to stop and allow the user to enter the correct username.
        // to do this we need to throw a AccountStatusException and not UsernameNotFoundException.
            throw new KimUserNotFoundException("Invalid username or password");  
        }
        
        password = (password == null ? "":password);
        UserWithId ksuser = new UserWithId(username, password, super.enabled, true, true, super.nonlocked, getGrantedAuthority(userId));
        ksuser.setUserId(userId);
        return ksuser;
    }
          
    
}
