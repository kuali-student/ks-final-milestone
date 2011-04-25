/*
 * Copyright 2008-2009 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl2.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.kim.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.kuali.rice.kim.service.AuthenticationService;
import org.kuali.student.common.util.security.UserWithId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticationServiceImpl implements AuthenticationService {
    
	public String getPrincipalName(HttpServletRequest request) {
	    String username=null;
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    
	    if(auth!=null){
            Object obj = auth.getPrincipal();
            if(obj instanceof UserWithId){
                //This is actually the user Id
                username = ((UserWithId)obj).getUserId();
            }else if (obj instanceof UserDetails) {
                username = ((UserDetails)obj).getUsername();
            } else {
                username = request.getRemoteUser();
            }
        }
	    return username;
	}
}
