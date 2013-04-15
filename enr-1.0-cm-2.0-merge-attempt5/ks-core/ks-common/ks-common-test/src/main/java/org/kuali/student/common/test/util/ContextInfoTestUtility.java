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
package org.kuali.student.common.test.util;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.util.ContextUtils;

/**
 * This is a library of utility methods that can be used when working with the Context Info in unit tests.  
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class ContextInfoTestUtility {
    
    /**
     * Create and return a new ContextInfo object and set the locale language on it to 'English'.
     */
    public static ContextInfo getEnglishContextInfo() {
        ContextInfo contextInfo = ContextUtils.getContextInfo();
        LocaleInfo localeInfo = contextInfo.getLocale();
        localeInfo.setLocaleLanguage("en");
        contextInfo.setPrincipalId("TEST");
        return contextInfo;
    }

}
