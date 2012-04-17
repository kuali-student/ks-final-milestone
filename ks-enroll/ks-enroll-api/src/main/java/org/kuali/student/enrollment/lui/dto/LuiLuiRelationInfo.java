/*
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

import org.kuali.student.enrollment.lui.infc.LuiLuiRelation;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LuiLuiRelationInfo", propOrder = { "id", "typeKey",
		"stateKey", "name", "descr", "luiId", "relatedLuiId", "effectiveDate",
		"expirationDate", "meta", "attributes", "_futureElements" })
public class LuiLuiRelationInfo 
    extends IdEntityInfo 
    implements Serializable, LuiLuiRelation {

    private static final long serialVersionUID = 1L;
    
    @XmlElement
    private String luiId;
    
    @XmlElement
    private String relatedLuiId;
    
    @XmlElement
    private Date effectiveDate;
    
    @XmlElement
    private Date expirationDate;
    
    @XmlAnyElement
    private List<Element> _futureElements;
    

    /**
     * Constructs a new LuiLuiRelationInfo.
     */
    public LuiLuiRelationInfo() {
    }

    /**
     * Constructs a new LuiLuiRelationInfo from another LuiLuiRelation.
     *
     * @param llr the LuiLuiRelation to copy.
     */
    public LuiLuiRelationInfo(LuiLuiRelation llr) {
        super(llr);
        this.luiId = llr.getLuiId();
        this.relatedLuiId = llr.getRelatedLuiId();
        this.effectiveDate = null != llr.getEffectiveDate() ? new Date(llr.getEffectiveDate().getTime()) : null;
        this.expirationDate = null != llr.getExpirationDate() ? new Date(llr.getExpirationDate().getTime()) : null;
        this._futureElements = null;
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
        return effectiveDate != null ? new Date(effectiveDate.getTime()) : null;
    }
    
    public void setEffectiveDate(Date effectiveDate) {
        if (effectiveDate != null)
            this.effectiveDate = new Date(effectiveDate.getTime());
    }
    
    @Override
    public Date getExpirationDate() {
        return expirationDate != null ? new Date(expirationDate.getTime()) : null;
    }
    
    public void setExpirationDate(Date expirationDate) {
        if (expirationDate != null)
            this.expirationDate = new Date(expirationDate.getTime());
    }
}
