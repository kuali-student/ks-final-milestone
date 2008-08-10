package org.kuali.student.poc.client.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface LoginService extends RemoteService {
    public static final String SERVICE_URI = "/LoginService";

    public static class Util {

        public static LoginServiceAsync getInstance() {

            LoginServiceAsync instance = (LoginServiceAsync) GWT
                    .create(LoginService.class);
            ServiceDefTarget target = (ServiceDefTarget) instance;
            target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
            return instance;
        }
    }
    
    public LoginCredentials getCurrentLogin();
    public Boolean login(LoginCredentials credentials);
    public Boolean logout();
    
}
