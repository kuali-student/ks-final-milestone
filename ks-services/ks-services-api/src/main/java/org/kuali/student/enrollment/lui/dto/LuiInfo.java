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

package org.kuali.student.enrollment.lui.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.common.dto.HasAttributesAndMetaInfo;
import org.kuali.student.common.infc.MetaInfc;
import org.kuali.student.enrollment.lui.infc.LuiInfc;


/**
 * Detailed information about a single LUI.
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class LuiInfo extends HasAttributesAndMetaInfo
  implements Serializable, LuiInfc {

	private static final long serialVersionUID = 1L;

    @XmlElement
    private final String luiCode;

    @XmlElement
    private final String cluId;

    @XmlElement(name = "atpKey")
    private final String atpKey;

    @XmlElement
    private final Integer maxSeats;

    @XmlElement
    private final Date effectiveDate;

    @XmlElement
    private final Date expirationDate;

    @XmlAttribute
    private final String state;

    @XmlAttribute
    private final String id;

    private LuiInfo() {
    	luiCode = null;
    	cluId = null;
    	atpKey = null;
    	maxSeats = null;
    	effectiveDate = null;
    	expirationDate = null;
    	state = null;
    	id = null;
    }
    
    private LuiInfo(LuiInfc builder) {
		super(builder);
		this.luiCode = new String(builder.getLuiCode());
		this.cluId = new String(builder.getCluId());
		this.atpKey = new String(builder.getAtpKey());
		this.maxSeats = new Integer(builder.getMaxSeats());
    	this.effectiveDate = null != builder.getEffectiveDate()? new Date(builder.getEffectiveDate().getTime()) : null;
    	this.expirationDate = null != builder.getExpirationDate()? new Date(builder.getExpirationDate().getTime()) : null;
		this.id = new String(builder.getId());
		this.state = new String(builder.getState());
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

    /**
     * The current status of the LUI. The values for this field are
     * constrained to those in the luState enumeration. A separate
     * setup operation does not exist for retrieval of the meta data
     * around this value.
     */
    @Override
    public String getState() {
        return state;
    }

    /**
     * Unique identifier for a Learning Unit Instance (LUI). This is
     * optional, due to the identifier being set at the time of
     * creation. Once the LUI has been created, this should be seen as
     * required.
     */
    @Override
    public String getId() {
        return id;
    }
    
    public static class Builder extends HasAttributesAndMetaInfo.Builder implements LuiInfc {

		private String luiCode;
		private String cluId;
		private String atpKey;
		private Integer maxSeats;
		private Date effectiveDate;
		private Date expirationDate;
		private String id;
		private String state;
		
		public Builder() {}
		
		public Builder(LuiInfc luiInfo) {
			super(luiInfo);
			this.luiCode = luiInfo.getLuiCode();
			this.cluId = luiInfo.getCluId();
			this.atpKey = luiInfo.getAtpKey();
			this.maxSeats = luiInfo.getMaxSeats();
			this.effectiveDate = luiInfo.getEffectiveDate();
			this.expirationDate = luiInfo.getExpirationDate();
			this.id = luiInfo.getId();
			this.state= luiInfo.getState();
		}
		
		public LuiInfo build() {
			return new LuiInfo(this);
		}
		
		public Builder setCluId(String cluId) {
			this.cluId = cluId;
			return this;
		}

		public Builder setAtpKey(String atpKey) {
			this.atpKey = atpKey;
			return this;
		}

		public Builder setId(String id) {
			this.id = id;
			return this;
		}

        public Builder setState (String state) {
            this.state = state;
            return this;
        }
		// passthru so right Builder is returned
        @Override
		public Builder setMetaInfo(MetaInfc metaInfo) {
			super.setMetaInfo(metaInfo);
			return this;
		}

		@Override
		public String getId() {
			return id;
		}

		@Override
		public String getState() {
			return state;
		}

		@Override
		public Date getEffectiveDate() {
			return effectiveDate;
		}

		@Override
		public Date getExpirationDate() {
			return expirationDate;
		}

		@Override
		public String getLuiCode() {
			return luiCode;
		}

		@Override
		public String getCluId() {
			return cluId;
		}

		@Override
		public String getAtpKey() {
			return atpKey;
		}

		@Override
		public Integer getMaxSeats() {
			return maxSeats;
		}
    }
}