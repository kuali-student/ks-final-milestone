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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.common.infc.ModelBuilder;
import org.kuali.student.enrollment.lui.infc.LuiLuiRelation;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;


/**
 * Detailed information about a LUI to LUI relationship.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LuiLuiRelationInfo", propOrder = {"id","typeKey","stateKey","luiId", "relatedLuiId", "effectiveDate", "expirationDate","metaInfo","attributes", "_futureElements"})
public class LuiLuiRelationInfo extends IdEntityInfo
  implements Serializable, LuiLuiRelation {

    private static final long serialVersionUID = 1L;

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
    	_futureElements = null;
    }
    
    private LuiLuiRelationInfo(LuiLuiRelation builder) {
    	super(builder);
    	this.luiId = builder.getLuiId();
    	this.relatedLuiId = builder.getRelatedLuiId();
    	this.effectiveDate = null != builder.getEffectiveDate() ? new Date(builder.getEffectiveDate().getTime()) : null;
    	this.expirationDate = null != builder.getExpirationDate() ? new Date(builder.getExpirationDate().getTime()) : null;
    	this._futureElements = null;
    }

    @Override
    public String getLuiId() {
        return luiId;
    }

    @Override
    public String getRelatedLuiId() {
        return relatedLuiId;
    }


    @Override
     public Date getEffectiveDate() {
        return effectiveDate;
    }


    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

  
    public static class Builder extends IdEntityInfo.Builder implements ModelBuilder<LuiLuiRelationInfo>, LuiLuiRelation {
    	
	    private String luiId;
	    private String relatedLuiId;
	    private Date effectiveDate;
	    private Date expirationDate;
    	
    	public Builder() {}
    	
    	public Builder(LuiLuiRelation llrInfo) {
    		super(llrInfo);
    		this.luiId = llrInfo.getLuiId();
    		this.relatedLuiId = llrInfo.getRelatedLuiId();
    		this.effectiveDate = llrInfo.getEffectiveDate();
    		this.expirationDate = llrInfo.getExpirationDate();
    	}
		
		public LuiLuiRelationInfo build() {
			return new LuiLuiRelationInfo(this);
		}

        @Override
        public String getLuiId() {
            return luiId;
        }

        public void setLuiId(String luiId) {
            this.luiId = luiId;
        }

        @Override
        public String getRelatedLuiId() {
            return relatedLuiId;
        }

        public void setRelatedLuiId(String relatedLuiId) {
            this.relatedLuiId = relatedLuiId;
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
