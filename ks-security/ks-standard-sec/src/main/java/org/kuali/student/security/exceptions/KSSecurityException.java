/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.security.exceptions;

public class KSSecurityException extends Exception {

    private static final long serialVersionUID = 2941300199692497512L;

    /**
     * This constructs a ...
     * 
     */
    public KSSecurityException() {
        super();
    }

    /**
     * This constructs a ...
     * 
     * @param message
     * @param cause
     */
    public KSSecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * This constructs a ...
     * 
     * @param message
     */
    public KSSecurityException(String message) {
        super(message);
    }

    /**
     * This constructs a ...
     * 
     * @param cause
     */
    public KSSecurityException(Throwable cause) {
        super(cause);
    }

}
