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

	static private String BASE_ROLES = "ROLE_KS_ADMIN, ROLE_KS_USER";
	
	
    private UserWithId ksuser = null;
    private String password = "";
   
    private boolean enabled = true;
    private boolean nonlocked = true;
    
    private IdentityService identityService = null;
    
    // Spring Security requires roles to have a prefix of ROLE_ , 
    // look in org.springframework.security.vote.RoleVoter to change.
    private GrantedAuthority[] authorities = 
        AuthorityUtils.commaSeparatedStringToAuthorityArray(BASE_ROLES);
    
    private Config config = null;
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username==null || username.equals("")){
            throw new UsernameNotFoundException("Username cannot be null or empty");
        }
        
        // Enable backdoor login. The LUMMain.jsp has will actually display the login. 
        if (enableBackdoorLogin()) {
            authorities = AuthorityUtils.commaSeparatedStringToAuthorityArray(BASE_ROLES + ", ROLE_KS_BACKDOOR");
        }
        
                
        String ksIgnoreRiceLogin = getConfig().getProperty("ks.ignore.rice.login");
        
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
                
        String ksIgnoreRiceLogin = getConfig().getProperty("ks.ignore.rice.login");
        
        if (enableBackdoorLogin()) {
            authorities = AuthorityUtils.commaSeparatedStringToAuthorityArray(BASE_ROLES + ", ROLE_KS_BACKDOOR");
        }
        
        // if property was not set in a config file then 
        // it will be null and it falls through to the identityService code.
        // -----------------
        // Here starts a new comment
        // ------------------
        // J.Jacobus Roos -- I changed this since the return of a valid principal ID is crucial 4 workflow
        // thus I use the getPrincipalByPrincipalName which doesn't require me 2 know the password. 
        // by changing the  ks.ignore.rice.login 2 false this step will be skipped and the proper soap method
        // will be called which include the username and password.
        // PS: the previous comment is not true anymore since I do not let it fall thru. I populate it with
        // all the values from Rice. The fact that it fell thru in the past was a quick way to allowing people
        // to login without knowing the password. This was good for testing, but that service did not include
        // the functionality 2 load the correct details(principalId) of the logged in person... thus that service return
        // principalId and principalName as the same value... which breaks workflow. 
        
        // So it is funny since now the people had 2 change the principalIds in the db to the same as the principalname 
        // What a crude workaround... please communicate people... 

       
        if(Boolean.valueOf(ksIgnoreRiceLogin) == true){
        	KimPrincipalInfo kimPrincipalInfo;
        	kimPrincipalInfo = identityService.getPrincipalByPrincipalName(username);
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
    
    public Config getConfig() {
    	if(this.config == null){
    		this.config = ConfigContext.getCurrentContextConfig();
    	}
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public void setAuthorities(String[] roles) {
        this.authorities =  AuthorityUtils.stringArrayToAuthorityArray(roles);
    }

    public void setIdentityService(IdentityService identityService) {
        this.identityService = identityService;
    }
    
    protected boolean enableBackdoorLogin() {
        return "true".equalsIgnoreCase(getConfig().getProperty("enableKSBackdoorLogin"));
    }
    
}
