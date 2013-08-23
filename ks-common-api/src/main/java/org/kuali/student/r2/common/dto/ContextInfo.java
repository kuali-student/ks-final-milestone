/*
 * Copyright 2011 The Kuali Foundation
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
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.Context;

/**
 * The DTO for a Context.
 *
 * @author Kamal
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContextInfo", propOrder = {
                "authenticatedPrincipalId", "principalId",
                "currentDate", "locale", "timeZone",
                "attributes", "_futureElements" }) 

public class ContextInfo
        extends HasAttributesInfo
        implements Context, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String authenticatedPrincipalId;

    @XmlElement
    private String principalId;

    @XmlElement
    private Date currentDate;

    @XmlElement
    private LocaleInfo locale;

    @XmlElement
    private String timeZone;

    @XmlAnyElement
    private List<Object> _futureElements;


    /**
     * Constructs a new ContextInfo.
     */
    public ContextInfo() {
        this.locale = new LocaleInfo();
        this.currentDate = new Date();
    }

    /**
     * Constructs a new ContextInfo from another Context.
     *
     * @param context the context to copy
     */
    public ContextInfo(Context context) {
        super(context);

        this.authenticatedPrincipalId = context.getAuthenticatedPrincipalId();
        this.principalId = context.getPrincipalId();

        if (context.getLocale() != null) {
            this.locale = new LocaleInfo(context.getLocale());
        }

        if (context.getCurrentDate() != null) {
            this.currentDate = new Date(context.getCurrentDate().getTime());
        } else {
            this.currentDate = new Date();
        }

        this.timeZone = context.getTimeZone();
    }

    @Override
    public String getAuthenticatedPrincipalId() {
        return authenticatedPrincipalId;
    }

    public void setAuthenticatedPrincipalId(String authenticatedPrincipalId) {
        this.authenticatedPrincipalId = authenticatedPrincipalId;
    }

    @Override
    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    @Override
    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    public LocaleInfo getLocale() {
        return this.locale;
    }

    public void setLocale(LocaleInfo locale) {
        this.locale = locale;
    }

    @Override
    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    // Compatibility methods

    @Deprecated
    public static ContextInfo getInstance(String principalId, String localeLanguage, String localeRegion) {
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(localeLanguage);
        localeInfo.setLocaleRegion(localeRegion);

        ContextInfo ctx = new ContextInfo();
        ctx.setAuthenticatedPrincipalId(principalId);
        ctx.setPrincipalId(principalId);
        ctx.setLocale(localeInfo);

        return ctx;
    }
}
