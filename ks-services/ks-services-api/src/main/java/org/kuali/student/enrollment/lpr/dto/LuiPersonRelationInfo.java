/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.lpr.dto;



import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.common.dto.HasAttributesAndMetaInfo;
import org.kuali.student.common.infc.AttributeInfc;
import org.kuali.student.common.infc.MetaInfc;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationInfc;

/**
 * Detailed information about a single LUI to Person Relation.
 *
 * @Author KSContractMojo
 * @Author Kamal
 * @Since Tue Mar 01 15:53:57 PST 2011
 * @See <a href="https://wiki.kuali.org/display/KULSTU/luiPersonRelationInfo+Structure">LuiPersonRelationInfo</a>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class LuiPersonRelationInfo extends HasAttributesAndMetaInfo
  implements LuiPersonRelationInfc, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private final String luiId;

    @XmlElement
    private final String personId;

    @XmlElement
    private final Date effectiveDate;

    @XmlElement
    private final Date expirationDate;

    @XmlAttribute
    private final String type;

    @XmlAttribute
    private final String state;

    @XmlAttribute
    private final String id;

    private LuiPersonRelationInfo() {
    	luiId = null;
    	personId = null;
    	effectiveDate = null;
    	expirationDate = null;
    	type = null;
    	state = null;
    	id = null;
    }
    
    private LuiPersonRelationInfo(LuiPersonRelationInfc builder) {
    	super(builder);
    	this.luiId = builder.getLuiId();
    	this.personId = builder.getPersonId();
    	this.effectiveDate = null != builder.getEffectiveDate()? new Date(builder.getEffectiveDate().getTime()) : null;
    	this.expirationDate = null != builder.getExpirationDate()? new Date(builder.getExpirationDate().getTime()) : null;
    	this.type = builder.getType();
    	this.state = builder.getState();
    	this.id = builder.getId();
    }
    
    /**
     * Name: LUI identifier
     * <p/>
     * Unique identifier for a Learning Unit Instance (LUI).
     */
    public String getLuiId() {
        return luiId;
    }

    /**
     * Name: Person Identifier
     * <p/>
     * Unique identifier for a person record.
     */
    public String getPersonId() {
        return personId;
    }

    /**
     * Name:Effective Date
     * <p/>
     * Date/time this relationship became effective. Must be less than or equal to the expirationDate specified.
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Name: Expiration Date
     * <p/>
     * Date/time this relationship is no longer effective. Must be greater than or equal to the effectiveDate specified.
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * Name: LUI Person Relation Type
     * <p/>
     * Unique identifier for the type of LUI to Person relation.
     */
    public String getType() {
        return type;
    }

    /**
     * Name: Relation State
     * <p/>
     * Unique identifier for the state of the relationship between a LUI and person.
     */
    public String getState() {
        return state;
    }

    /**
     * Name: LUI Person Relation identifier
     * <p/>
     * Unique identifier for the LUI to Person relation. This is optional, due to the identifier being set at the time of creation. Once the relation has been created, this should be seen as required.
     */
    public String getId() {
        return id;
    }

    public static class Builder extends HasAttributesAndMetaInfo.Builder implements LuiPersonRelationInfc {
    	
		private String luiId;
		private String personId;
		private Date effectiveDate;
		private Date expirationDate;
		private String type;
		private String state;
		private String id;

		public Builder() {}
    	
    	public Builder(LuiPersonRelationInfc lprInfo) {
    		super(lprInfo);
    		this.luiId = lprInfo.getLuiId();
    		this.personId = lprInfo.getPersonId();
    		this.effectiveDate = lprInfo.getEffectiveDate();
    		this.expirationDate = lprInfo.getExpirationDate();
    		this.type = lprInfo.getType();
    		this.state = lprInfo.getState();
    		this.id = lprInfo.getId();
    	}

    	public LuiPersonRelationInfo build() {
    		return new LuiPersonRelationInfo(this);
    	}
    	
		public Builder luiId(String luiId) {
			this.luiId = luiId;
			return this;
		}

		public Builder personId(String personId) {
			this.personId = personId;
			return this;
		}

		public Builder effectiveDate(Date effDate) {
			this.effectiveDate = new Date(effDate.getTime());
			return this;
		}

		public Builder expirationDate(Date expDate) {
			this.expirationDate = new Date(expDate.getTime());
			return this;
		}

		public Builder type(String luiPersonRelationType) {
			this.type = luiPersonRelationType;
			return this;
		}

		public Builder state(String state) {
			this.state = state;
			return this;
		}

		public Builder id(String id) {
			this.id = id;
			return this;
		}
		
		public Builder metaInfo(MetaInfc metaInfo) {
			super.metaInfo(metaInfo);
			return this;
		}
		
		// passthru to return correct Builder
        public Builder attributes(List<? extends AttributeInfc> attributes) {
        	super.attributes(attributes);
            return this;
        }

        @Override
		public String getLuiId() {
			return luiId;
		}

        @Override
		public String getPersonId() {
			return personId;
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
		public String getType() {
			return type;
		}

        @Override
		public String getState() {
			return state;
		}

        @Override
		public String getId() {
			return id;
		}
    }
}