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

import java.util.ArrayList;

import org.kuali.rice.core.config.Config;
import org.kuali.rice.core.config.ConfigContext;
import org.kuali.rice.kim.bo.entity.dto.KimPrincipalInfo;
import org.kuali.rice.kim.bo.role.dto.KimRoleInfo;
import org.kuali.rice.kim.service.IdentityService;
import org.kuali.rice.kim.service.RoleService;
import org.kuali.student.common.rice.StudentIdentityConstants;
import org.kuali.student.common.util.security.UserWithId;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.security.util.AuthorityUtils;
import org.springframework.util.StringUtils;


/**
 * This is a description of what this class does - Rich don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class KSDefaultUserDetailsService implements UserDetailsService{

   
    protected boolean enabled = true;
    protected boolean nonlocked = true;
    
    protected Config config = null;
    
    protected IdentityService identityService = null; // This is added so we can get the correct principal ID
    protected RoleService roleService = null;  // needed for future client overrides.
    
    
   

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password;
        
        if(username==null || username.equals("")){
            throw new UsernameNotFoundException("Username cannot be null or empty");
        }
        
        // This is for the dummy KS Login
        password = username;        
        
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
            //System.out.println("kimPrincipalInfo is null ");
            throw new KimUserNotFoundException("Invalid username or password");  
        }
        UserWithId ksuser = new UserWithId(username, password, enabled, true, true, nonlocked, getGrantedAuthority(userId));
        ksuser.setUserId(userId);                     
        
        return ksuser;
    }
    
    protected GrantedAuthority[] getGrantedAuthority(String principalId){
    	
    	String springRoles = "";
    	
    	// KS Administrator
    	ArrayList<String> adminRoleIdList = new ArrayList<String>();
     	KimRoleInfo adminRole = roleService.getRoleByName(StudentIdentityConstants.KS_NAMESPACE_CD, StudentIdentityConstants.KSCM_ADMIN_ROLE_NAME);
    	if(adminRole != null) {
    		adminRoleIdList.add(adminRole.getRoleId());
    	}

    	// KS User
        ArrayList<String> ksUserRoleIdList = new ArrayList<String>();
        KimRoleInfo ksUserRole = roleService.getRoleByName(StudentIdentityConstants.KS_NAMESPACE_CD, StudentIdentityConstants.KSCM_USER_ROLE_NAME);
        if(ksUserRole != null) {
        	ksUserRoleIdList.add(ksUserRole.getRoleId());
        }            
        
        ArrayList<String> ksSpringRolesList = new ArrayList<String>();
       
        if(roleService.principalHasRole(principalId, adminRoleIdList, null)){
        	ksSpringRolesList.add("ROLE_KS_ADMIN");
        }
        if(roleService.principalHasRole(principalId, ksUserRoleIdList, null)){
        	ksSpringRolesList.add("ROLE_KS_USER");
        }  
         
        // Enable backdoor login. The LUMMain.jsp has will actually display the login. 
        if (enableBackdoorLogin()) {
        	ksSpringRolesList.add("ROLE_KS_BACKDOOR");
        }
        
        springRoles = StringUtils.collectionToCommaDelimitedString(ksSpringRolesList);
        return AuthorityUtils.commaSeparatedStringToAuthorityArray(springRoles);                
        
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
	
	 public void setIdentityService(IdentityService identityService) {
	        this.identityService = identityService;
	 }
	    
	 protected boolean enableBackdoorLogin() {
	     return "true".equalsIgnoreCase(getConfig().getProperty("enableKSBackdoorLogin"));
	 }
	        
	 public RoleService getRoleService() {    	
		 return roleService;
	 }

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	public IdentityService getIdentityService() {
		return identityService;
	}
}