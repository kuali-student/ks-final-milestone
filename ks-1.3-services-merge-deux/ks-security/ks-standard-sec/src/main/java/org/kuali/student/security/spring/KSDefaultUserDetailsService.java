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

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * This is a description of what this class does - Rich don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class KSDefaultUserDetailsService implements UserDetailsService{

    private User ksuser = null;
    private String password = "";
   
    private boolean enabled = true;
    private boolean nonlocked = true;
    
    // Spring Security requires roles to have a prefix of ROLE_ , 
    // look in org.springframework.security.vote.RoleVoter to change.
    private List<GrantedAuthority> authorities = 
        AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_KS_ADMIN, ROLE_KS_USER");
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        if(username==null || username.equals("")){
            throw new UsernameNotFoundException("Username cannot be null or empty");
        }
        password = username;
        
        ksuser = new User(username, password, enabled, true, true, nonlocked, authorities);
        
        return ksuser;
    }
    
    public void setAuthorities(String[] roles) {
        this.authorities =  AuthorityUtils.createAuthorityList(roles);
    }
}
