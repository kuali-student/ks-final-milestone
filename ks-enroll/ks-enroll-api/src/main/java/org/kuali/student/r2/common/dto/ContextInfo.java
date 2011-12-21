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
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.r2.common.infc.Context;
import org.kuali.student.r2.common.infc.Locale;
import org.w3c.dom.Element;

/**
 * @author Kamal
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContextInfo", propOrder = {
                "authenticatedPrincipalId", "principalId", 
                "currentDate", "locale", "timeZone", 
                "attributes", "_futureElements"})

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
    private List<Element> _futureElements;

    public static ContextInfo newInstance() {
        ContextInfo contextInfo = new ContextInfo();
        UserSession userSession = GlobalVariables.getUserSession();
        if (userSession != null) {
            contextInfo.setPrincipalId(userSession.getPrincipalId());
        }

        return contextInfo;
    }

    public static ContextInfo getInstance(ContextInfo callContext) {
        return new ContextInfo(callContext);
    }
    
    public static ContextInfo getInstance(String principalId, Locale locale) {
        ContextInfo ctx = new ContextInfo();
        ctx.setAuthenticatedPrincipalId(principalId);
        ctx.setPrincipalId(principalId);
        ctx.locale = (null != locale) ? new LocaleInfo(locale) : null;
        return ctx;
    }

    public static ContextInfo getInstance(String principalId, String localeLanguage, String localeRegion) {
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(localeLanguage);
        localeInfo.setLocaleRegion(localeRegion);
        return ContextInfo.getInstance(principalId, localeInfo);
    }
    
    public ContextInfo() {
        this.locale = new LocaleInfo();
        this.currentDate = new Date();
    }

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
    public Locale getLocale() {
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
}
