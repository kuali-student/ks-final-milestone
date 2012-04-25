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
import java.util.List;

import org.kuali.rice.core.api.config.property.Config;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.role.RoleService;
import org.kuali.student.common.util.security.UserWithId;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        
        Principal kimPrincipalInfo = null;
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
    
    protected List<GrantedAuthority> getGrantedAuthority(String principalId){
    	
    	String springRoles = "";
    	
    	// KS Administrator
    	ArrayList<String> adminRoleIdList = new ArrayList<String>();
     	Role adminRole = roleService.getRoleByNamespaceCodeAndName(StudentIdentityConstants.KS_NAMESPACE_CD, StudentIdentityConstants.KSCM_ADMIN_ROLE_NAME);
    	if(adminRole != null) {
    		adminRoleIdList.add(adminRole.getId());
    	}

    	// KS User
        ArrayList<String> ksUserRoleIdList = new ArrayList<String>();
        Role ksUserRole = roleService.getRoleByNamespaceCodeAndName(StudentIdentityConstants.KS_NAMESPACE_CD, StudentIdentityConstants.KSCM_USER_ROLE_NAME);
        if(ksUserRole != null) {
        	ksUserRoleIdList.add(ksUserRole.getId());
        }            
        
        ArrayList<String> ksSpringRolesList = new ArrayList<String>();
       
        //TODO: Uncomment these lines when R2 upgrade is done.
        //if(roleService.principalHasRole(principalId, adminRoleIdList, null)){
        	ksSpringRolesList.add("ROLE_KS_ADMIN");
        //}
        //if(roleService.principalHasRole(principalId, ksUserRoleIdList, null)){
        	ksSpringRolesList.add("ROLE_KS_USER");
        //}  
         
        // Enable backdoor login. The LUMMain.jsp has will actually display the login. 
        if (enableBackdoorLogin()) {
        	ksSpringRolesList.add("ROLE_KS_BACKDOOR");
        }
        
        springRoles = StringUtils.collectionToCommaDelimitedString(ksSpringRolesList);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(springRoles);
        
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
