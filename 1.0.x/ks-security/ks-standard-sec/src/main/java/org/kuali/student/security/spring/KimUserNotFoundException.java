/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.security.spring;

import org.springframework.security.AccountStatusException;


/**
 * 
 * This is a custom exception designed to be thrown when only Kim is used for authentication
 * This exception is thrown so that when rice-kim fails to authenticate the user, we do not want
 * the next authentication provider on the list to be used.  
 * AccountStatusException is extended because spring security has the desired behavior only for AccountStatusException.
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
