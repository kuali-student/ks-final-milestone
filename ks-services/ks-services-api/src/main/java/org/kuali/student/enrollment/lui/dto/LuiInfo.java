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
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.common.dto.HasAttributesAndMetaInfo;
import org.kuali.student.common.infc.ModelBuilder;
import org.kuali.student.enrollment.lui.infc.Lui;
import org.w3c.dom.Element;


/**
 * Detailed information about a single LUI.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LuiInfo", propOrder = {"id","type","state","luiCode", "cluId", "atpKey", "maxSeats", "effectiveDate", "expriationDate","metaInfo","attributes", "_futureElements"})
public class LuiInfo extends HasAttributesAndMetaInfo
  implements Serializable, Lui {

	private static final long serialVersionUID = 1L;

    @XmlAttribute
    private final String id;

    @XmlAttribute 
    private final String type;
    
    @XmlAttribute
    private final String state;
    
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
    	luiCode = null;
    	cluId = null;
    	atpKey = null;
    	maxSeats = null;
    	effectiveDate = null;
    	expirationDate = null;
    	state = null;
    	id = null;
    	type = null;
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
		this.id = builder.getId();
		this.state = builder.getState();
		this.type = builder.getType();
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
    
    @Override
    public String getType() {
        return type;
    }


    public static class Builder extends HasAttributesAndMetaInfo.Builder implements ModelBuilder<LuiInfo>, Lui {

		private String luiCode;
		private String cluId;
		private String atpKey;
		private Integer maxSeats;
		private Date effectiveDate;
		private Date expirationDate;
		private String id;
		private String state;
		private String type;
		
		public Builder() {}
		
		public Builder(Lui luiInfo) {
			super(luiInfo);
			this.luiCode = luiInfo.getLuiCode();
			this.cluId = luiInfo.getCluId();
			this.atpKey = luiInfo.getAtpKey();
			this.maxSeats = luiInfo.getMaxSeats();
			this.effectiveDate = luiInfo.getEffectiveDate();
			this.expirationDate = luiInfo.getExpirationDate();
			this.id = luiInfo.getId();
			this.state= luiInfo.getState();
			this.type = luiInfo.getType();
		}
		
		public LuiInfo build() {
			return new LuiInfo(this);
		}

        public String getLuiCode() {
            return luiCode;
        }

        public void setLuiCode(String luiCode) {
            this.luiCode = luiCode;
        }

        public String getCluId() {
            return cluId;
        }

        public void setCluId(String cluId) {
            this.cluId = cluId;
        }

        public String getAtpKey() {
            return atpKey;
        }

        public void setAtpKey(String atpKey) {
            this.atpKey = atpKey;
        }

        public Integer getMaxSeats() {
            return maxSeats;
        }

        public void setMaxSeats(Integer maxSeats) {
            this.maxSeats = maxSeats;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }		        
   }
}