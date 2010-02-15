package org.kuali.student.common.util.security;

import org.kuali.rice.kim.KimAuthenticationProvider;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;

public class SecurityUtils {
	public static String getCurrentUserId() {
        String username=null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth!=null){
        	Object obj = auth.getPrincipal();
        	if(obj instanceof KimAuthenticationProvider.UserWithId){
        		//This is actually the user Id
        		username = ((KimAuthenticationProvider.UserWithId)obj).getUserId();
        	}else if (obj instanceof UserDetails) {
            	username = ((UserDetails)obj).getUsername();
            } else {
            	username = obj.toString();
            }
        }
		return username;
	}
}
