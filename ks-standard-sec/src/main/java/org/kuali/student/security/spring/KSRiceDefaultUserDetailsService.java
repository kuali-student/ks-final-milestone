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

import javax.xml.namespace.QName;

import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.kuali.rice.kim.bo.entity.dto.KimPrincipalInfo;
import org.kuali.rice.kim.service.IdentityService;
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

    private User ksuser = null;
    private String password = "";
   
    private boolean enabled = true;
    private boolean nonlocked = true;
    
    private IdentityService identityService = null;
    private String identityServiceAddress;
    // Spring Security requires roles to have a prefix of ROLE_ , 
    // look in org.springframework.security.vote.RoleVoter to change.
    private GrantedAuthority[] authorities = 
        AuthorityUtils.commaSeparatedStringToAuthorityArray("ROLE_KS_ADMIN, ROLE_KS_USER");
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        if(username==null || username.equals("")){
            throw new UsernameNotFoundException("Username cannot be null or empty");
        }
        password = username;
        
        aquireIdentityService();
        KimPrincipalInfo kimPrincipalInfo = identityService.getPrincipalByPrincipalName(username);
        if(kimPrincipalInfo!=null){
            username = kimPrincipalInfo.getPrincipalId();
        } else {
            throw new UsernameNotFoundException("Kim Identity Service could not find a principal, KimPrincipalInfo is null"); 
        }
        
        ksuser = new User(username, password, enabled, true, true, nonlocked, authorities);
        
        return ksuser;
    }
    
    public void setAuthorities(String[] roles) {
        this.authorities =  AuthorityUtils.stringArrayToAuthorityArray(roles);
    }
    
    private void aquireIdentityService() {
        if(identityService==null){
            ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
            factory.setServiceClass(IdentityService.class);
            factory.setAddress(identityServiceAddress);
            factory.setServiceName(new QName("KIM", "kimIdentityServiceSOAPunsecure"));
            factory.getServiceFactory().setDataBinding(new AegisDatabinding());
            identityService = (IdentityService) factory.create();
        }
     }

    public String getIdentityServiceAddress() {
        return identityServiceAddress;
    }

    public void setIdentityServiceAddress(String identityServiceAddress) {
        this.identityServiceAddress = identityServiceAddress;
    }
}
