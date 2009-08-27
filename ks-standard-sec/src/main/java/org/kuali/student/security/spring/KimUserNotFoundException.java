package org.kuali.student.security.spring;

import org.springframework.security.AccountStatusException;


/**
 * 
 * This is a custom exception designed to be trwon for developement purposes when only Kim is used for 
 * authentication. In production system this exception has be replaced with UsernameNotFoundException.
 * This exception is thrown so that when rice kim fails to authenticate the user we donot want the control to pass
 * onto the default user service.  
 * AccountStatusException is extended because spring security has the desired behaviour only for AccountStatusException.
 * @author Kuali Student Team: NeeravA
 *
 */
public class KimUserNotFoundException extends AccountStatusException{

    private static final long serialVersionUID = 1L;

    public KimUserNotFoundException(String msg) {
        super(msg);
    }

    public KimUserNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    protected KimUserNotFoundException(String msg, Object extraInformation) {
        super(msg, extraInformation);
    }
}
