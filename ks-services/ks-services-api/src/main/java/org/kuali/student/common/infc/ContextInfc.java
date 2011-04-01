/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.infc;

/**
 * This is a generic context container to be used by services to pass
 * user identity and preferences
 *
 * Note:
 *      1. ISO3 standard can now be interpreted by looking at the
 *         language and country codes
 *      2. Time zone is defined in GMT +/- hours and minutes format
 *      3. Should Locale contain currency
 *
 * References:
 * ftp://ftp.rfc-editor.org/in-notes/bcp/bcp47.txt
 * http://download.oracle.com/javase/1.4.2/docs/api/java/util/TimeZone.html
 *
 * @author Kamal
 */
public interface ContextInfc extends HasAttributes {

    /**
* Name: Principal Id
     *
     * Principal Id of the currently authenticated user or the user on whom's behalf this method is being invoked.
     *
     * Used for authorization checking.
     */
    public String getPrincipalId();

    /**
     * Name: Locale Language
     *
     * Laguage portion of the locale information used in this context
     */
    public String getLocaleLanguage();

    /**
     * Name: Locale Language Variant
     *
     * Laguage variant portion of the locale information to be used in this context
     */
    public String getLocaleVariant();

    /**
     * Name: Locale Region
     *
     * Laguage Region portion of the locale information to be used in this context
     */
    public String getLocaleRegion();

    /**
     * Name: Locale Script
     *
     * Laguage Script to be used in this context
     */
    public String getLocaleScript();

    /**
     * Name: Tim Zone
     *
     * The time zone to be used in this context
     */
    public String getTimeZone();
}

