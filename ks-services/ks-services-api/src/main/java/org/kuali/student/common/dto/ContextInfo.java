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

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.common.infc.ContextInfc;

/**
 * @author Kamal
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ContextInfo extends HasAttributesInfo implements ContextInfc, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private final String principalId;
    @XmlElement
    private final String localeLanguage;
    @XmlElement
    private final String localeVariant;
    @XmlElement
    private final String localeRegion;
    @XmlElement
    private final String localeScript;
    @XmlElement
    private final String timeZone;

    private ContextInfo() {
        principalId = null;
        localeLanguage = null;
        localeVariant = null;
        localeRegion = null;
        localeScript = null;
        timeZone = null;
    }

    private ContextInfo(ContextInfc builder) {
        super(builder);
        this.principalId = builder.getPrincipalId();
        this.localeLanguage = builder.getLocaleLanguage();
        this.localeVariant = builder.getLocaleVariant();
        this.localeRegion = builder.getLocaleRegion();
        this.localeScript = builder.getLocaleScript();
        this.timeZone = builder.getTimeZone();
    }

    @Override
    public String getPrincipalId() {
        return principalId;
    }

    @Override
    public String getLocaleLanguage() {
        return localeLanguage;
    }

    @Override
    public String getLocaleVariant() {
        return localeVariant;
    }

    @Override
    public String getLocaleRegion() {
        return localeRegion;
    }

    @Override
    public String getLocaleScript() {
        return localeScript;
    }

    @Override
    public String getTimeZone() {
        return timeZone;
    }

    public static class Builder extends HasAttributesInfo.Builder implements ContextInfc {

        private String principalId;
        private String localeLanguage;
        private String localeVariant;
        private String localeRegion;
        private String localeScript;
        private String timeZone;

        public Builder() {
        }

        public Builder(ContextInfc ctxInfo) {
            super(ctxInfo);
        }

        public ContextInfo build() {
            return new ContextInfo(this);
        }

        @Override
        public String getPrincipalId() {
            return principalId;
        }

        @Override
        public String getLocaleLanguage() {
            return localeLanguage;
        }

        @Override
        public String getLocaleVariant() {
            return localeVariant;
        }

        @Override
        public String getLocaleRegion() {
            return localeRegion;
        }

        @Override
        public String getLocaleScript() {
            return localeScript;
        }

        @Override
        public String getTimeZone() {
            return timeZone;
        }

        public Builder principalId(String principalId) {
            this.principalId = principalId;
            return this;
        }

        public Builder localeLanguage(String localeLanguage) {
            this.localeLanguage = localeLanguage;
            return this;
        }

        public Builder localeRegion(String localeRegion) {
            this.localeRegion = localeRegion;
            return this;
        }
    }
}
