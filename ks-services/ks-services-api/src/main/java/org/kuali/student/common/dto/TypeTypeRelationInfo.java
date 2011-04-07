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

package org.kuali.student.common.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.common.infc.ModelBuilder;
import org.kuali.student.common.infc.TypeTypeRelationInfc;
import org.w3c.dom.Element;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KSTypeRelationType", propOrder = {"key", "name", "descr", "effectiveDate", "expirationDate", "ownerType", "relatedType", "rank", "attributes", "_futureElements"})
public class TypeTypeRelationInfo extends HasAttributesInfo implements TypeTypeRelationInfc, Serializable {
	
    @XmlAttribute
	private final String key;
	
    @XmlAttribute
    private final String type;

    @XmlElement
	private final String name;
	
	@XmlElement
	private final String descr;

	@XmlElement
	private final Date effectiveDate;
	
	@XmlElement
	private final Date expirationDate;
		
	@XmlElement
	private final String ownerType;
	
	@XmlElement
	private final String relatedType;

	@XmlElement
	private final Integer rank;
	
    @XmlAnyElement
    private final List<Element> _futureElements;    
	
	private TypeTypeRelationInfo() {
		key = null;
		name = null;
		descr = null;
		effectiveDate = null;
		expirationDate = null;
		type = null;
		ownerType = null;
		relatedType = null;
		rank = null;
		_futureElements = null;
	}
		
	private TypeTypeRelationInfo(TypeTypeRelationInfc builder) {
		super(builder);
		this.key = builder.getKey();
		this.name = builder.getName();
		this.descr = builder.getDescr();
    	this.effectiveDate = null != builder.getEffectiveDate() ? new Date(builder.getEffectiveDate().getTime()) : null;
    	this.expirationDate = null != builder.getExpirationDate() ? new Date(builder.getExpirationDate().getTime()) : null;
    	this.type = builder.getType();
    	this.ownerType = builder.getOwnerType();
    	this.relatedType = builder.getRelatedType();
    	this.rank = builder.getRank();
    	this._futureElements = null;
	}
	
    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the descr
     */
    public String getDescr() {
        return descr;
    }

    /**
     * @return the effectiveDate
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * @return the expirationDate
     */
    public Date getExpirationDate() {
        return expirationDate;
    }
    

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getOwnerType() {
        return ownerType;
    }

    @Override
    public String getRelatedType() {
        return relatedType;
    }

    @Override
    public Integer getRank() {
        return rank;
    }
    
    public static class Builder extends HasAttributesInfo.Builder implements ModelBuilder<TypeTypeRelationInfo>, TypeTypeRelationInfc {
    	private String key;
		private String name;
		private String descr;
		private Date effectiveDate;
		private Date expirationDate;
		private String type;
	    private String ownerType;	    
	    private String relatedType;
	    private Integer rank;

		public Builder() {}
    	
    	public Builder(TypeTypeRelationInfo typeTypeRelationInfo) {
    		super(typeTypeRelationInfo);
    		this.key = typeTypeRelationInfo.getKey();
    		this.name = typeTypeRelationInfo.getName();
    		this.descr = typeTypeRelationInfo.getDescr();
    		this.effectiveDate = typeTypeRelationInfo.getEffectiveDate();
    		this.expirationDate = typeTypeRelationInfo.getExpirationDate();
    		this.type = typeTypeRelationInfo.getType();
    		this.ownerType = typeTypeRelationInfo.getOwnerType();
    		this.relatedType = typeTypeRelationInfo.getRelatedType();
    		this.rank = typeTypeRelationInfo.getRank();
    	}

        public TypeTypeRelationInfo build() {
            return new TypeTypeRelationInfo(this);
        } 
        
		@Override
		public String getKey() {
			return key;
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
		public String getName() {
			return name;
		}

		@Override
		public String getDescr() {
			return descr;
		}

        @Override
        public String getType() {
            return type;
        }

        @Override
        public String getOwnerType() {
            return ownerType;
        }

        @Override
        public String getRelatedType() {
            return relatedType;
        }

        @Override
        public Integer getRank() {
            return rank;
        }
    }
}
