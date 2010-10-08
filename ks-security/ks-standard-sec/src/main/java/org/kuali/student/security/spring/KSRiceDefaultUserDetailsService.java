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

import org.kuali.rice.core.config.Config;
import org.kuali.rice.core.config.ConfigContext;
import org.kuali.rice.kim.bo.entity.dto.KimPrincipalInfo;
import org.kuali.rice.kim.service.IdentityService;
import org.kuali.student.common.util.security.UserWithId;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.User;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.security.util.AuthorityUtils;

/**
 * This is a description of what this class does. 
 * 
 * @author Kuali Student Team
 *
 */
public class KSRiceDefaultUserDetailsService implements UserDetailsService{

    private UserWithId ksuser = null;
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
        
        Config config = ConfigContext.getCurrentContextConfig();
        String ksIgnoreRiceLogin = config.getProperty("ks.ignore.rice.login");
        
        // if property was not set in a config file then 
        // it will be null and it falls through to the identityService code.
        if(Boolean.valueOf(ksIgnoreRiceLogin) == true){
            return new User(username, password, enabled, true, true, nonlocked, authorities);
        }
        
        KimPrincipalInfo kimPrincipalInfo = null;
        kimPrincipalInfo = identityService.getPrincipalByPrincipalName(username);
        
        String userId;
        if (null != kimPrincipalInfo) {
            username = kimPrincipalInfo.getPrincipalName();
            userId = kimPrincipalInfo.getPrincipalId();
        } else {
        // When a UsernameNotFoundException is thrown, spring security will proceed to the next AuthenticationProvider on the list.
        // When Rice is running and username is not found in KIM, we want authentication to stop and allow the user to enter the correct username.
        // to do this we need to throw a AccountStatusException and not UsernameNotFoundException.
            throw new KimUserNotFoundException("Invalid username or password");  
        }
        ksuser = new UserWithId(username, password, enabled, true, true, nonlocked, authorities);
        ksuser.setUserId(userId);
        return ksuser;
    }
    
    public UserDetails loadUserByUsernameAndToken(String username, UsernamePasswordAuthenticationToken authentication) throws UsernameNotFoundException {
        if(username==null || username.equals("")){
            throw new UsernameNotFoundException("Username cannot be null or empty");
        }
        
        Config config = ConfigContext.getCurrentContextConfig();
        String ksIgnoreRiceLogin = config.getProperty("ks.ignore.rice.login");
        
        // if property was not set in a config file then 
        // it will be null and it falls through to the identityService code.
        if(Boolean.valueOf(ksIgnoreRiceLogin) == true){
            return null;
        }
        
        password = (String)authentication.getCredentials();
        
        KimPrincipalInfo kimPrincipalInfo = null;
        
        kimPrincipalInfo = identityService.getPrincipalByPrincipalNameAndPassword(username, password);
        String userId;
        if (null != kimPrincipalInfo) {
            username = kimPrincipalInfo.getPrincipalName();
            userId = kimPrincipalInfo.getPrincipalId();
        } else {
        // When a UsernameNotFoundException is thrown, spring security will proceed to the next AuthenticationProvider on the list.
        // When Rice is running and username is not found in KIM, we want authentication to stop and allow the user to enter the correct username.
        // to do this we need to throw a AccountStatusException and not UsernameNotFoundException.
            //System.out.println("kimPrincipalInfo is null ");
            throw new KimUserNotFoundException("Invalid username or password");  
        }
        ksuser = new UserWithId(username, password, enabled, true, true, nonlocked, authorities);
        ksuser.setUserId(userId);
        return ksuser;
    }
    
    public void setAuthorities(String[] roles) {
        this.authorities =  AuthorityUtils.stringArrayToAuthorityArray(roles);
    }

    public void setIdentityService(IdentityService identityService) {
        this.identityService = identityService;
    }
}
