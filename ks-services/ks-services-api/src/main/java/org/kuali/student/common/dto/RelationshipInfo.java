/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational
 * Community License, Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a
 * copy of the License at http://www.opensource.org/licenses/ecl1.php
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.kuali.student.common.infc.Relationship;

/**
 * Information about a Relationship.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlTransient
public abstract class RelationshipInfo extends IdEntityInfo implements Relationship, Serializable {

    @XmlElement
    private final Date effectiveDate;

    @XmlElement
    private final Date expirationDate;

    protected RelationshipInfo() {
        effectiveDate = null;
        expirationDate = null;
    }

    protected RelationshipInfo(Relationship builder) {
        super(builder);
        this.effectiveDate = null != builder.getEffectiveDate() ? new Date(builder.getEffectiveDate().getTime()) : null;
        this.expirationDate = null != builder.getExpirationDate() ? new Date(builder.getExpirationDate().getTime()) : null;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    public static class Builder extends IdEntityInfo.Builder implements Relationship {

        private Date effectiveDate;
        private Date expirationDate;

        public Builder() {}

        public Builder(Relationship amrInfo) {
            super(amrInfo);
            this.effectiveDate = amrInfo.getEffectiveDate();
            this.expirationDate = amrInfo.getExpirationDate();
        }

        public Date getEffectiveDate() {
            return effectiveDate;
        }

        public void setEffectiveDate(Date effectiveDate) {
            this.effectiveDate = effectiveDate;
        }

        public Date getExpirationDate() {
            return expirationDate;
        }

        public void setExpirationDate(Date expirationDate) {
            this.expirationDate = expirationDate;
        }
    }
}
