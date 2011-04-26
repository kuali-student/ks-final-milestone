/**
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.classI.lui.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.classI.lui.infc.Lui;
import org.kuali.student.r2.common.dto.HasAttributesAndMetaInfo;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.infc.ModelBuilder;
import org.w3c.dom.Element;


/**
 * Detailed information about a single LUI.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LuiInfo", propOrder = {"id","typeKey","stateKey","name", "descr", "luiCode", "cluId", "atpKey", "maxSeats", "effectiveDate", "expirationDate","metaInfo","attributes", "_futureElements"})
public class LuiInfo extends IdEntityInfo
  implements Serializable, Lui {

	private static final long serialVersionUID = 1L;
 
    @XmlElement
    private final String luiCode;

    @XmlElement
    private final String cluId;

    @XmlElement
    private final String atpKey;

    @XmlElement
    private final Integer maxSeats;

    @XmlElement
    private final Date effectiveDate;

    @XmlElement
    private final Date expirationDate;

    @XmlAnyElement
    private final List<Element> _futureElements;    
    
    private LuiInfo() {
        super ();
    	luiCode = null;
    	cluId = null;
    	atpKey = null;
    	maxSeats = null;
    	effectiveDate = null;
    	expirationDate = null;
    	_futureElements = null;
    }
    
    private LuiInfo(Lui builder) {
		super(builder);
		this.luiCode = builder.getLuiCode();
		this.cluId = builder.getCluId();
		this.atpKey = builder.getAtpKey();
		this.maxSeats =builder.getMaxSeats();
    	this.effectiveDate = null != builder.getEffectiveDate()? new Date(builder.getEffectiveDate().getTime()) : null;
    	this.expirationDate = null != builder.getExpirationDate()? new Date(builder.getExpirationDate().getTime()) : null;
		this._futureElements = null;
	}

    /**
     * Code identifier/name for the LUI. This is typically used in
     * human readable form (e.g. ENGL 100 section 123).
     */
    @Override
    public String getLuiCode() {
        return luiCode;
    }

    /**
     * Unique identifier for a Canonical Learning Unit (CLU).
     */
    @Override
    public String getCluId() {
        return cluId;
    }

    /**
     * Unique identifier for an Academic Time Period (ATP).
     */
    @Override
    public String getAtpKey() {
        return atpKey;
    }

    /**
     * Maximum number of "seats" that the LUI will hold for registration.
     */
    @Override
    public Integer getMaxSeats() {
        return maxSeats;
    }

    /**
     * Date and time that this LUI became effective. This is a similar
     * concept to the effective date on enumerated values. When an
     * expiration date has been specified, this field must be less
     * than or equal to the expiration date.
     */
    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Date and time that this LUI expires. This is a similar concept
     * to the expiration date on enumerated values. If specified, this
     * should be greater than or equal to the effective date. If this
     * field is not specified, then no expiration date has been
     * currently defined and should automatically be considered
     * greater than the effective date.
     */
    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

   

    public static class Builder extends IdEntityInfo.Builder implements ModelBuilder<LuiInfo>, Lui {

		private String luiCode;
		private String cluId;
		private String atpKey;
		private Integer maxSeats;
		private Date effectiveDate;
		private Date expirationDate;
		
		public Builder() {}
		
		public Builder(Lui luiInfo) {
			super(luiInfo);
			this.luiCode = luiInfo.getLuiCode();
			this.cluId = luiInfo.getCluId();
			this.atpKey = luiInfo.getAtpKey();
			this.maxSeats = luiInfo.getMaxSeats();
			this.effectiveDate = luiInfo.getEffectiveDate();
			this.expirationDate = luiInfo.getExpirationDate();
		}

        @Override
		public LuiInfo build() {
			return new LuiInfo(this);
		}

        @Override
        public String getLuiCode() {
            return luiCode;
        }

        public void setLuiCode(String luiCode) {
            this.luiCode = luiCode;
        }

        @Override
        public String getCluId() {
            return cluId;
        }

        public void setCluId(String cluId) {
            this.cluId = cluId;
        }

        @Override
        public String getAtpKey() {
            return atpKey;
        }

        public void setAtpKey(String atpKey) {
            this.atpKey = atpKey;
        }

        @Override
        public Integer getMaxSeats() {
            return maxSeats;
        }

        public void setMaxSeats(Integer maxSeats) {
            this.maxSeats = maxSeats;
        }

        @Override
        public Date getEffectiveDate() {
            return effectiveDate;
        }

        public void setEffectiveDate(Date effectiveDate) {
            this.effectiveDate = effectiveDate;
        }

        @Override
        public Date getExpirationDate() {
            return expirationDate;
        }

        public void setExpirationDate(Date expirationDate) {
            this.expirationDate = expirationDate;
        }
    }
}