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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.common.infc.ModelBuilder;
import org.kuali.student.common.infc.TypeTypeRelation;
import org.w3c.dom.Element;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypeTypeRelationInfo", propOrder = {"key", "typeKey", "stateKey", "name", "descr", "effectiveDate", "expirationDate", "ownerTypeKey", "relatedTypeKey", "rank", "attributes", "metaInfo", "_futureElements"})
public class TypeTypeRelationInfo extends KeyEntityInfo implements TypeTypeRelation, Serializable {
	
  	@XmlElement
	private final Date effectiveDate;
	
	@XmlElement
	private final Date expirationDate;
		
	@XmlElement
	private final String ownerTypeKey;
	
	@XmlElement
	private final String relatedTypeKey;

	@XmlElement
	private final Integer rank;
	
    @XmlAnyElement
    private final List<Element> _futureElements;    
	
	private TypeTypeRelationInfo() {
		effectiveDate = null;
		expirationDate = null;
		ownerTypeKey = null;
		relatedTypeKey = null;
		rank = null;
		_futureElements = null;
	}
		
	private TypeTypeRelationInfo(TypeTypeRelation builder) {
		super(builder);
    	this.effectiveDate = null != builder.getEffectiveDate() ? new Date(builder.getEffectiveDate().getTime()) : null;
    	this.expirationDate = null != builder.getExpirationDate() ? new Date(builder.getExpirationDate().getTime()) : null;
    	this.ownerTypeKey = builder.getOwnerTypeKey();
    	this.relatedTypeKey = builder.getRelatedTypeKey();
    	this.rank = builder.getRank();
    	this._futureElements = null;
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
    public String getOwnerTypeKey() {
        return ownerTypeKey;
    }

    @Override
    public String getRelatedTypeKey() {
        return relatedTypeKey;
    }

    @Override
    public Integer getRank() {
        return rank;
    }
    
    public static class Builder extends KeyEntityInfo.Builder implements ModelBuilder<TypeTypeRelationInfo>, TypeTypeRelation {
    
		private Date effectiveDate;
		private Date expirationDate;
	    private String ownerTypeKey;
	    private String relatedTypeKey;
	    private Integer rank;

		public Builder() {}
    	
    	public Builder(TypeTypeRelation typeTypeRelationInfo) {
    		super(typeTypeRelationInfo);
    		this.effectiveDate = typeTypeRelationInfo.getEffectiveDate();
    		this.expirationDate = typeTypeRelationInfo.getExpirationDate();
    		this.ownerTypeKey = typeTypeRelationInfo.getOwnerTypeKey();
    		this.relatedTypeKey = typeTypeRelationInfo.getRelatedTypeKey();
    		this.rank = typeTypeRelationInfo.getRank();
    	}

        public TypeTypeRelationInfo build() {
            return new TypeTypeRelationInfo(this);
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

        @Override
        public String getOwnerTypeKey() {
            return ownerTypeKey;
        }

        public void setOwnerTypeKey(String ownerTypeKey) {
            this.ownerTypeKey = ownerTypeKey;
        }

        @Override
        public String getRelatedTypeKey() {
            return relatedTypeKey;
        }

        public void setRelatedTypeKey(String relatedTypeKey) {
            this.relatedTypeKey = relatedTypeKey;
        }

        public Integer getRank() {
            return rank;
        }

        public void setRank(Integer rank) {
            this.rank = rank;
        }         
    }
}
