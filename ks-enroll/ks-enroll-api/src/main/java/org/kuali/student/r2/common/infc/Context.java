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

package org.kuali.student.r2.common.infc;

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

public interface Context extends HasAttributes {

    /**
     * Principal Id of the currently authenticated user or the user on
     * whom's behalf this method is being invoked.
     *
     * Used for authorization checking.
     * @name Principal Id
     */
    public String getPrincipalId();
   

    /**
     * Language portion of the locale information used in this context
     * @name Locale Language
     */
    public String getLocaleLanguage();
   
    /**
     * Language variant portion of the locale information to be used in this context
     * @name Locale Language Variant
     */
    public String getLocaleVariant();
   
    /**
     * Language Region portion of the locale information to be used in this context
     * @name Locale Region
     */
    public String getLocaleRegion();
   
    /**
     * Language Script to be used in this context
     * @name Locale Script
     */
    public String getLocaleScript();
   
    /**
     * The time zone to be used in this context
     * @name Time Zone
     */
    public String getTimeZone();

}

