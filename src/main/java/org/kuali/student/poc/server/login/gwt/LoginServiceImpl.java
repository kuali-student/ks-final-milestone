package org.kuali.student.poc.server.login.gwt;

import org.kuali.student.poc.client.login.LoginCredentials;
import org.kuali.student.poc.client.login.LoginService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {
    public static final String CURRENT_LOGIN_SESSION_KEY = "CURRENT_LOGIN_SESSION_KEY";
    public static final String ATTEMPTS_COUNT_KEY = "ATTEMPTS_COUNT_KEY";
    
    private static final long serialVersionUID = 1L;
    
    public LoginCredentials getCurrentLogin() {
        return (LoginCredentials) getThreadLocalRequest().getSession(true).getAttribute(CURRENT_LOGIN_SESSION_KEY);
    }

    public Boolean login(LoginCredentials credentials) {
        String userId = credentials.getUserId();
        userId = (userId == null) ? "" : userId.trim().toLowerCase();
        
        String password = credentials.getPassword();
        password = (password == null) ? "" : password.trim().toLowerCase();
        
        boolean result =  userId.equals("demo") && password.equals("demo");
        if (result) {
            getThreadLocalRequest().getSession(true).setAttribute(CURRENT_LOGIN_SESSION_KEY, credentials);
        }
        return result;
    }

    public Boolean logout() {
        getThreadLocalRequest().getSession(true).removeAttribute(CURRENT_LOGIN_SESSION_KEY);
        return true;
    }

}
