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

package org.kuali.student.r2.common.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.Locale;
import org.w3c.dom.Element;


/**
 * Information about Locale for Context.
 *
 * @author Sri komandur@uw.edu
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocaleInfo", propOrder = {
                "localeLanguage", "localeVariant",
                "localeRegion", "localeScript", 
                "_futureElements"})

public class LocaleInfo 
    implements Locale, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String localeLanguage;

    @XmlElement
    private String localeVariant;

    @XmlElement
    private String localeRegion;

    @XmlElement
    private String localeScript;

    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new LocaleInfo.
     */
    public LocaleInfo() {
    }

    /**
     * Constructs a new LocaleInfo from another Locale.
     *
     * @param locale the locale to copy
     */
    public LocaleInfo(Locale locale) {
        if (null != locale) {
            this.localeLanguage = locale.getLocaleLanguage();
            this.localeVariant = locale.getLocaleVariant();
            this.localeRegion = locale.getLocaleRegion();
            this.localeScript = locale.getLocaleScript();
        }
    }

    @Override
    public String getLocaleLanguage() {
        return this.localeLanguage;
    }

    public void setLocaleLanguage(String localeLanguage) {
        this.localeLanguage = localeLanguage;
    }

    @Override
    public String getLocaleVariant() {
        return this.localeVariant;
    }

    public void setLocaleVariant(String localeVariant) {
        this.localeVariant = localeVariant;
    }

    @Override
    public String getLocaleRegion() {
        return this.localeRegion;
    }

    public void setLocaleRegion(String localeRegion) {
        this.localeRegion = localeRegion;
    }

    @Override
    public String getLocaleScript() {
        return this.localeScript;
    }

    public void setLocaleScript(String localeScript) {
        this.localeScript = localeScript;
    }
}
