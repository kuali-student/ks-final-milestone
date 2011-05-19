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

package org.kuali.student.r2.common.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.TypeTypeRelation;
import org.w3c.dom.Element;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypeTypeRelationInfo", propOrder = {"key", "typeKey", "stateKey", "name", "descr", "effectiveDate", "expirationDate", "ownerTypeKey", "relatedTypeKey", "rank", "attributes", "metaInfo", "_futureElements"})
public class TypeTypeRelationInfo extends KeyEntityInfo implements TypeTypeRelation, Serializable {
	
  	@XmlElement
	private Date effectiveDate;
	
	@XmlElement
	private Date expirationDate;
		
	@XmlElement
	private String ownerTypeKey;
	
	@XmlElement
	private String relatedTypeKey;

	@XmlElement
	private Integer rank;
	
    @XmlAnyElement
    private List<Element> _futureElements;    
	
    public TypeTypeRelationInfo() {
		effectiveDate = null;
		expirationDate = null;
		ownerTypeKey = null;
		relatedTypeKey = null;
		rank = null;
		_futureElements = null;
	}
		
	public TypeTypeRelationInfo(TypeTypeRelation builder) {
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


    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = new Date(effectiveDate.getTime());
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }
    

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = new Date(expirationDate.getTime());
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

    @Override
    public Integer getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
