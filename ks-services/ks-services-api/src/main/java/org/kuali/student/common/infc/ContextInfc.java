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

public interface ContextInfc extends HasAttributesInfc {

    /**
     * Set ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    public void setPrincipalId(String principalId);

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    public String getPrincipalId();

    /**
     * Set ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    public void setLocaleLanguage(String localeLanguage);

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    public String getLocaleLanguage();

    /**
     * Set ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    public void setLocaleVariant(String localeVariant);

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    public String getLocaleVariant();

    /**
     * Set ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    public void setLocaleRegion(String localeRegion);

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    public String getLocaleRegion();

    /**
     * Set ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    public void setLocaleScript(String localeScript);

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    public String getLocaleScript();

    /**
     * Set ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    public void setTimeZone(String timeZone);

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    public String getTimeZone();
}

