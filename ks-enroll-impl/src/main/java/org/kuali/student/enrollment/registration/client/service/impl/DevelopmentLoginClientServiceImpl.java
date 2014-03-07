package org.kuali.student.enrollment.registration.client.service.impl;

import org.apache.log4j.Logger;
import org.kuali.student.enrollment.registration.client.service.DevelopmentLoginClientService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.context.ContextLoader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

public class DevelopmentLoginClientServiceImpl implements DevelopmentLoginClientService {

    private static final Logger LOGGER = Logger.getLogger(DevelopmentLoginClientServiceImpl.class);

    private AuthenticationManager authenticationManager;

    @Override
    public Response login(String userId, String password, HttpServletRequest request) {
        Response.ResponseBuilder response;

        try {
            HttpSession session = request.getSession(true);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userId, password);
            authRequest.setDetails(new WebAuthenticationDetails(request));
            Authentication authResult = getAuthenticationManager().authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(authResult);
            response = Response.ok("Logged in!");
            response.cookie(new NewCookie("JSESSIONID", session.getId()));
            response.header("Access-Control-Allow-Header", "Content-Type");
            response.header("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
            response.header("Access-Control-Allow-Origin", "http://127.0.0.1:9000");
        } catch (Exception e) {
            LOGGER.warn("Exception Thrown", e);
            response = Response.serverError().entity(e.getMessage());
        }
        return response.build();
    }

    @Override
    public Response logout(@Context HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        return Response.ok("Logged Out!").build();
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationManager getAuthenticationManager() {
        if (authenticationManager == null) {
            authenticationManager = (AuthenticationManager) ContextLoader.getCurrentWebApplicationContext().getBean("authenticationManager");
        }
        return authenticationManager;
    }
}
