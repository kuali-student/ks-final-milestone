/*
 * Copyright 2011 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.infc;

/**
 * A common data structure for holding locale information.
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */

public interface Locale {

    /**
     * The language portion of the locale information used in this
     * context.
     *
     * @name Locale Language
     */
    public String getLocaleLanguage();

    /**
     * The language variant portion of the locale information to be
     * used in this context .
     *
     * @name Locale Language Variant
     */
    public String getLocaleVariant();

    /**
     * The language Region portion of the locale information to be
     * used in this context.
     *
     * @name Locale Region
     */
    public String getLocaleRegion();

    /**
     * The language Script to be used in this context.
     *
     * @name Locale Script
     */
    public String getLocaleScript();
}
