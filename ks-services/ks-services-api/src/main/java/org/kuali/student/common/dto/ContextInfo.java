/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.student.common.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;


/**
 * This is a generic context container to be used by services to pass
 * user identity and preferences
 * <p/>
 * Note:
 * 1. ISO3 standard can now be interpreted by looking at the
 * language and country codes
 * 2. Time zone is defined in GMT +/- hours and minutes format
 * 3. Should Locale contain currency
 * <p/>
 * References:
 * ftp://ftp.rfc-editor.org/in-notes/bcp/bcp47.txt
 * http://download.oracle.com/javase/1.4.2/docs/api/java/util/TimeZone.html
 *
 * @author Kamal
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class ContextInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String principalId;

    @XmlElement
    private String localeLanguage;

    @XmlElement
    private String localeVariant;

    @XmlElement
    private String localeRegion;

    @XmlElement
    private String localeScript;

    @XmlElement
    private String timeZone;


    /**
     * @return the principalId
     */

    public String getPrincipalId() {
        return principalId;
    }


    /**
     * @param principalId the principalId to set
     */

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }


    /**
     * @return the localeLanguage
     */

    public String getLocaleLanguage() {
        return localeLanguage;
    }


    /**
     * @param localeLanguage the localeLanguage to set
     */

    public void setLocaleLanguage(String localeLanguage) {
        this.localeLanguage = localeLanguage;
    }


    /**
     * @return the localeVariant
     */

    public String getLocaleVariant() {
        return localeVariant;
    }


    /**
     * @param localeVariant the localeVariant to set
     */

    public void setLocaleVariant(String localeVariant) {
        this.localeVariant = localeVariant;
    }


    /**
     * @return the localeRegion
     */

    public String getLocaleRegion() {
        return localeRegion;
    }


    /**
     * @param localeRegion the localeRegion to set
     */

    public void setLocaleRegion(String localeRegion) {
        this.localeRegion = localeRegion;
    }


    /**
     * @return the localeScript
     */

    public String getLocaleScript() {
        return localeScript;
    }


    /**
     * @param localeScript the localeScript to set
     */

    public void setLocaleScript(String localeScript) {
        this.localeScript = localeScript;
    }


    /**
     * @return the timeZone
     */

    public String getTimeZone() {
        return timeZone;
    }


    /**
     * @param timeZone the timeZone to set
     */

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
