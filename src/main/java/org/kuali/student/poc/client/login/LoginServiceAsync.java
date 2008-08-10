package org.kuali.student.poc.client.login;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {
    public void getCurrentLogin(AsyncCallback<LoginCredentials> callback);
    public void login(LoginCredentials credentials, AsyncCallback<Boolean> callback);
    public void logout(AsyncCallback<Boolean> callback);
}
