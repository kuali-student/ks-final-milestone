package org.kuali.student.poc.common.ws.security.cxf;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.ws.security.WSPasswordCallback;

public class AcegiClientPasswordCallback implements CallbackHandler {

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

        for (int i = 0; i < callbacks.length; i++) {
            WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];

            int usage = pc.getUsage();

            if (usage == WSPasswordCallback.USERNAME_TOKEN) {
                Authentication acegiAuthentication = SecurityContextHolder.getContext().getAuthentication();
                String pwd = (String) acegiAuthentication.getCredentials();

                pc.setPassword(pwd);
            } else if (usage == WSPasswordCallback.SIGNATURE) {
                // set the password for client's keystore.keyPassword
                pc.setPassword("keyStorePassword");
            }
        }
    }

}
