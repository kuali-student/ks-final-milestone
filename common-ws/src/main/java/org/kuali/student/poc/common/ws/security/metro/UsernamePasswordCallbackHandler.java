package org.kuali.student.poc.common.ws.security.metro;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;

/**
 * This is handles name and passord callbacks for WSIT client
 */
public class UsernamePasswordCallbackHandler implements CallbackHandler {

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof NameCallback) {
                //Get username from Acegi
                Authentication acegiAuth = SecurityContextHolder.getContext().getAuthentication();               
                String uid = acegiAuth.getName();

                NameCallback nc = (NameCallback)callbacks[i];
                nc.setName(uid);

            } else if (callbacks[i] instanceof PasswordCallback) {
                //Get password from Acegi
                Authentication acegiAuthentication = SecurityContextHolder.getContext().getAuthentication();
                String pwd = (String) acegiAuthentication.getCredentials();
                
                PasswordCallback pc = (PasswordCallback)callbacks[i];
                pc.setPassword(pwd.toCharArray());

            } else {
                throw new UnsupportedCallbackException
                (callbacks[i], "Unrecognized Callback");
            }
        }
    }         
}
