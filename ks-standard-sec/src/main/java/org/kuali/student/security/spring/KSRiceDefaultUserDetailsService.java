/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.security.spring;

import org.kuali.rice.core.exception.RiceRuntimeException;
import org.kuali.rice.kim.bo.entity.dto.KimPrincipalInfo;
import org.kuali.rice.kim.service.IdentityService;
import org.springframework.security.AccountExpiredException;
import org.springframework.security.AccountStatusException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.User;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.security.util.AuthorityUtils;

/**
 * This is a description of what this class does - Rich don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class KSRiceDefaultUserDetailsService implements UserDetailsService{

    private UserDetails ksuser = null;
    private String password = "";
   
    private boolean enabled = true;
    private boolean nonlocked = true;
    
    private IdentityService identityService = null;
    
    // Spring Security requires roles to have a prefix of ROLE_ , 
    // look in org.springframework.security.vote.RoleVoter to change.
    private GrantedAuthority[] authorities = 
        AuthorityUtils.commaSeparatedStringToAuthorityArray("ROLE_KS_ADMIN, ROLE_KS_USER");
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username==null || username.equals("")){
            throw new UsernameNotFoundException("Username cannot be null or empty");
        }
        password = username;
        
        KimPrincipalInfo kimPrincipalInfo = null;
        //try {
	        kimPrincipalInfo = identityService.getPrincipalByPrincipalName(username);
        if (null != kimPrincipalInfo) {
            username = kimPrincipalInfo.getPrincipalId();
        } else {
            //KimUserNotFoundException is thrown because the spring security doesnot stop at UsernameNotFoundException and continue to 
            // proceed to the next available userdetailservice.
            //When rice is up and user name is not found in it then authentication should stop there and allow the user to enter the correct username.
            //For production release please replace this with UsernameNotFoundException.
            throw new KimUserNotFoundException("Invalid username");  
        }
        ksuser = new User(username, password, enabled, true, true, nonlocked, authorities);
        
        return ksuser;
/*        }
        catch(Exception rre){
            //Added to handle the exception in case Rice service is not available. 
            // This is implemented because in developement environment if rice service is not up then 
            // we deflect the authentication to DefaultUserDetailService.
              return null;
        }
*/
    }
    
    public void setAuthorities(String[] roles) {
        this.authorities =  AuthorityUtils.stringArrayToAuthorityArray(roles);
    }

    public void setIdentityService(IdentityService identityService) {
        this.identityService = identityService;
    }
}
