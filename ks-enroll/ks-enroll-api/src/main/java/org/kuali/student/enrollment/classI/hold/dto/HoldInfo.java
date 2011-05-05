/*
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
package org.kuali.student.enrollment.classI.hold.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;

import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.infc.ModelBuilder;
import org.kuali.student.enrollment.classI.hold.infc.Hold;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HoldInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr", "holdCategoryId", "personId", "isWarning", "isOverridable", "effectiveDate", "releasedDate", "metaInfo", "attributes", "_futureElements"})

public class HoldInfo extends IdEntityInfo implements Hold, Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement
    private final String personId;

    @XmlElement
    private final String holdCategoryId;

    @XmlElement
    private final Boolean isWarning;

    @XmlElement
    private final Boolean isOverridable;

    @XmlElement
    private final Date effectiveDate;

    @XmlElement
    private final Date releasedDate;

    @XmlAnyElement
    private final List<Element> _futureElements;

    private HoldInfo() {
	personId = null;
	isWarning = false;
	isOverridable = false;
	holdCategoryId = null;
        effectiveDate = null;
        releasedDate = null;
        _futureElements = null;
    }

    /**
     * Constructs a new HoldInfo from another Hold.
     *
     * @param hold the Hold to copy
     */
    public HoldInfo(Hold hold) {
        super(hold);

	this.personId = hold.getPersonId();
	this.holdCategoryId = hold.getHoldCategoryId();
	this.isWarning = hold.getIsWarning();
	this.isOverridable = hold.getIsOverridable();

        this.effectiveDate = null != hold.getEffectiveDate() ? new Date(hold.getEffectiveDate().getTime()) : null;
        this.releasedDate = null != hold.getReleasedDate() ? new Date(hold.getReleasedDate().getTime()) : null;

        _futureElements = null;
    }

    @Override
    public String getPersonId() {
        return personId;
    }

    @Override
    public String getHoldCategoryId() {
        return holdCategoryId;
    }

    @Override
    public Boolean getIsWarning() {
        return isWarning;
    }

    @Override
    public Boolean getIsOverridable() {
	return isOverridable;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    @Override
    public Date getReleasedDate() {
        return releasedDate;
    }

    /**
     * The builder class for this HoldInfo.
     */
    public static class Builder extends IdEntityInfo.Builder implements ModelBuilder<HoldInfo>, Hold {

        private String personId;
        private String holdCategoryId;
        private Boolean isWarning;
        private Boolean isOverridable;
        private Date effectiveDate;
        private Date releasedDate;

        /**
         * Constructs a new builder.
         */
        public Builder() {
        }

        /**
         * Constructs a new builder initialized from another 
	 * Hold.
         */
        public Builder(Hold hold) {
            super(hold);
	    this.personId = hold.getPersonId();
	    this.holdCategoryId = hold.getHoldCategoryId();
	    this.isWarning = hold.getIsWarning();
	    this.isOverridable = hold.getIsOverridable();
            this.effectiveDate = hold.getEffectiveDate();
            this.effectiveDate = hold.getReleasedDate();
        }

        @Override
        public HoldInfo build() {
            return new HoldInfo(this);
        }

        @Override
        public String getPersonId() {
            return personId;
        }

        public void setPersonId(String personId) {
            this.personId = personId;
        }

        @Override
        public String getHoldCategoryId() {
            return holdCategoryId;
        }

        public void setHoldCategoryId(String holdCategoryId) {
            this.holdCategoryId = holdCategoryId;
        }

        @Override
        public Boolean getIsWarning() {
            return isWarning;
        }

        public void setIsWarning(Boolean isWarning) {
            this.isWarning = isWarning;
        }

	@Override
	public Boolean getIsOverridable() {
	    return isOverridable;
	}

        public void setIsOverridable(Boolean isOverridable) {
            this.isOverridable = isOverridable;
        }

        @Override
        public Date getEffectiveDate() {
            return effectiveDate;
        }

        public void setEffectiveDate(Date effectiveDate) {
            this.effectiveDate = effectiveDate;
        }

        @Override
        public Date getReleasedDate() {
            return releasedDate;
        }

        public void setReleasedDate(Date releasedDate) {
            this.releasedDate = releasedDate;
        }
    }
}
