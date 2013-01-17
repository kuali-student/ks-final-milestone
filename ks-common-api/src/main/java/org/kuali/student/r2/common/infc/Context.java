/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.infc;

import java.util.Date;

/*
 * This is a generic context container to be used by services to pass
 * user identity and preferences
 *
 * Note:
 *      1. ISO3 standard can now be interpreted by looking at the
 *         language and country codes
 *      2. Time zone is defined in GMT +/- hours and minutes format
 *      3. Should Locale contain currency?
 *
 * References:
 * ftp://ftp.rfc-editor.org/in-notes/bcp/bcp47.txt
 * http://download.oracle.com/javase/1.4.2/docs/api/java/util/TimeZone.html
 *
 * @author Kamal
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */

public interface Context 
    extends HasAttributes {

    /**
     * The Principal Id of the currently authenticated user.
     *
     * @name Authenticated Principal Id
     */
    public String getAuthenticatedPrincipalId();

    /**
     * The Principal Id of the principal on whose behalf the
     * authenticated principal is acting. If the authenticated
     * principal is not acting on behalf of a different user, then
     * this Id should be the same as the Authenticated Principal Id.
     *
     * (1) User is authorized to only act on behalf of
     * itself. Principal Id must equal the Authenticated Principal Id
     * and the authorization is performed on that Id. If the Principal
     * Id differs from the Authenticated Principal Id, then the user
     * is not authorized to perform the requested operation.
     *
     * (2) User is authorized to act on behalf of another user. The
     * Principal Id differs from the Authentication Principal
     * Id. Authorization is checked to see if Authenticated Principal
     * Id can perform the operation on behalf of Principal Id. Then,
     * authorization is checked to see if Principal Id can perform the
     * operation.
     *
     * @name Principal Id
     */
    public String getPrincipalId();

    /**
     * The current date in this context. This date is used to instruct
     * the provider to peform operations as if this date were the
     * current date.
     *
     * @name Current Date
     */
    public Date getCurrentDate();

    /**
     * The locale information requested by the user.
     *
     * @name Locale
     */
    public Locale getLocale();
   
    /**
     * The time zone requested by the user.
     *
     * @name Time Zone
     */
    public String getTimeZone();
}

