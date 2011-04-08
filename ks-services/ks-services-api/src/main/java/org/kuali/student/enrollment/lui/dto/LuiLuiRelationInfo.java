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
import org.kuali.student.common.infc.MetaInfc;
import org.kuali.student.common.infc.ModelBuilder;
import org.kuali.student.enrollment.lui.infc.LuiLuiRelationInfc;
import org.w3c.dom.Element;


/**
 * Detailed information about a LUI to LUI relationship.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LuiLuiRelationInfo", propOrder = {"id","type","state","luiId", "relatedLuiId", "effectiveDate", "expriationDate","metaInfo","attributes", "_futureElements"})
public class LuiLuiRelationInfo extends HasAttributesAndMetaInfo 
  implements Serializable, LuiLuiRelationInfc {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    private final String id;
    
    @XmlAttribute
    private final String type;

    @XmlAttribute
    private final String state;

    @XmlElement
    private final String luiId;

    @XmlElement
    private final String relatedLuiId;

    @XmlElement
    private final Date effectiveDate;

    @XmlElement
    private final Date expirationDate;

    @XmlAnyElement
    private final List<Element> _futureElements;    

    private LuiLuiRelationInfo() {
    	luiId = null;
    	relatedLuiId = null;
    	effectiveDate = null;
    	expirationDate = null;
    	type = null;
    	state = null;
    	id = null;
    	_futureElements = null;
    }
    
    private LuiLuiRelationInfo(LuiLuiRelationInfc builder) {
    	super(builder);
    	this.luiId = builder.getLuiId();
    	this.relatedLuiId = builder.getRelatedLuiId();
    	this.effectiveDate = null != builder.getEffectiveDate() ? new Date(builder.getEffectiveDate().getTime()) : null;
    	this.expirationDate = null != builder.getExpirationDate() ? new Date(builder.getExpirationDate().getTime()) : null;
    	this.type = builder.getType();
    	this.state = builder.getState();
    	this.id = builder.getId();
    	this._futureElements = null;
    }

    /**
     * Unique identifier for a Learning Unit Instance (LUI).
     *
     * @return a LUI identifier
     */

    public String getLuiId() {
        return luiId;
    }

    /**
     * Unique identifier for a Learning Unit Instance (LUI).
     *
     * @return a LUI identifier
     */

    public String getRelatedLuiId() {
        return relatedLuiId;
    }

    /**
     * Date and time that this LUI to LUI relationship type became
     * effective. This is a similar concept to the effective date on
     * enumerated values. When an expiration date has been specified,
     * this field must be less than or equal to the expiration date.
     *
     * @return the effective date
     */

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Date and time that this LUI to LUI relationship type
     * expires. This is a similar concept to the expiration date on
     * enumerated values. If specified, this should be greater than or
     * equal to the effective date. If this field is not specified,
     * then no expiration date has been currently defined and should
     * automatically be considered greater than the effective date.
     *
     * @return the expiration date
     */

    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * Unique identifier for the LU to LU relation type.
     *
     * @return the relation type key
     */

    public String getType() {
        return type;
    }

    /**
     * The current status of the LUI to LUI relationship. The values
     * for this field are constrained to those in the
     * luLuRelationState enumeration. A separate setup operation does
     * not exist for retrieval of the meta data around this value.
     *
     * @return the state
     */

    public String getState() {
        return state;
    }

    /**
     * Unique identifier for a LUI to LUI relation. This is optional,
     * due to the identifier being set at the time of creation. Once
     * the relation has been created, this should be seen as required.
     *
     * @return the identifier for this relationship
     */

    public String getId() {
        return id;
    }
    
    public static class Builder extends HasAttributesAndMetaInfo.Builder implements ModelBuilder<LuiLuiRelationInfo>, LuiLuiRelationInfc {
    	
	    private String luiId;
	    private String relatedLuiId;
	    private Date effectiveDate;
	    private Date expirationDate;
	    private String type;
	    private String state;
	    private String id;
    	
    	public Builder() {}
    	
    	public Builder(LuiLuiRelationInfc llrInfo) {
    		super(llrInfo);
    		this.luiId = llrInfo.getLuiId();
    		this.relatedLuiId = llrInfo.getRelatedLuiId();
    		this.effectiveDate = llrInfo.getEffectiveDate();
    		this.expirationDate = llrInfo.getExpirationDate();
    		this.type = llrInfo.getType();
    		this.state = llrInfo.getState();
    		this.id = llrInfo.getId();
    	}
		
		public LuiLuiRelationInfo build() {
			return new LuiLuiRelationInfo(this);
		}

        public String getLuiId() {
            return luiId;
        }

        public void setLuiId(String luiId) {
            this.luiId = luiId;
        }

        public String getRelatedLuiId() {
            return relatedLuiId;
        }

        public void setRelatedLuiId(String relatedLuiId) {
            this.relatedLuiId = relatedLuiId;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }		
    }
}
