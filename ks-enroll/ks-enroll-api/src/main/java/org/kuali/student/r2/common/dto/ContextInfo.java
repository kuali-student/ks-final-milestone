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
package org.kuali.student.r2.common.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.Context;
import org.w3c.dom.Element;

/**
 * @author Kamal
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContextInfo", propOrder = {"principalId", "localeLanguage", "localeVariant", "localeRegion", "localeScript", "timeZone", "attributes", "_futureElements"})
public class ContextInfo extends HasAttributesInfo implements Context, Serializable {

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
    @XmlAnyElement
    private List<Element> _futureElements;



    public static ContextInfo newInstance() {
        return new ContextInfo();
    }

    public static ContextInfo getInstance(ContextInfo callContext) {
        return new ContextInfo(callContext);
    }
    
    public static ContextInfo getInstance(String principalId, String localeLanguage, String localeRegion) {
        ContextInfo ctx = new ContextInfo();
        
        ctx.setPrincipalId(principalId);
        ctx.setLocaleLanguage(localeLanguage);
        ctx.setLocaleRegion(localeRegion);
        
        return ctx;
    }
    
    private ContextInfo() {
        principalId = null;
        localeLanguage = null;
        localeVariant = null;
        localeRegion = null;
        localeScript = null;
        timeZone = null;
        _futureElements = null;
    }

    private ContextInfo(Context context) {
        super(context);
        this.principalId = context.getPrincipalId();
        this.localeLanguage = context.getLocaleLanguage();
        this.localeVariant = context.getLocaleVariant();
        this.localeRegion = context.getLocaleRegion();
        this.localeScript = context.getLocaleScript();
        this.timeZone = context.getTimeZone();
        this._futureElements = null;
    }

    @Override
    public String getPrincipalId() {
        return principalId;
    }

    @Override
    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    @Override
    public String getLocaleLanguage() {
        return localeLanguage;
    }

    @Override
    public void setLocaleLanguage(String localeLanguage) {
        this.localeLanguage = localeLanguage;
    }

    @Override
    public String getLocaleVariant() {
        return localeVariant;
    }

    @Override
    public void setLocaleVariant(String localeVariant) {
        this.localeVariant = localeVariant;
    }

    @Override
    public String getLocaleRegion() {
        return localeRegion;
    }

    @Override
    public void setLocaleRegion(String localeRegion) {
        this.localeRegion = localeRegion;
    }

    @Override
    public String getLocaleScript() {
        return localeScript;
    }

    @Override
    public void setLocaleScript(String localeScript) {
        this.localeScript = localeScript;
    }

    @Override
    public String getTimeZone() {
        return timeZone;
    }

    @Override
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
