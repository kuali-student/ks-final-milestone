package org.kuali.student.security;

import javax.servlet.http.HttpServletRequest;

import org.kuali.rice.kim.service.AuthenticationService;
import org.springframework.security.context.SecurityContextHolder;

public class KSRiceAuthenticationServiceImpl implements AuthenticationService {

    @Override
    public String getPrincipalName(HttpServletRequest request) {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
