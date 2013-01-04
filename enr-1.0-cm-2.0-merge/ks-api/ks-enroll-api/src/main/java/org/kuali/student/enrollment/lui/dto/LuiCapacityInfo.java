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
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.lui.infc.LuiCapacity;
import org.kuali.student.r2.common.dto.IdEntityInfo;

import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LuiCapacityInfo", propOrder = { "id", "typeKey",
                "stateKey", "name", "descr", "luiIds", "maximumSeatCount", 
                "processingOrder", "effectiveDate", "expirationDate", 
                "meta", "attributes", "_futureElements" })

public class LuiCapacityInfo 
    extends IdEntityInfo 
    implements Serializable, LuiCapacity {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<String> luiIds;
    
    @XmlElement
    private Integer maximumSeatCount;
    
    @XmlElement
    private Integer processingOrder;
    
    @XmlElement
    private Date effectiveDate;
    
    @XmlElement
    private Date expirationDate;
    
    @XmlAnyElement
    private List<Element> _futureElements;

    /**
     * Constructs a new LuiCapacityInfo.
     */    
    public LuiCapacityInfo() {
    }
    
    /**
     *  Constructs a new LuiCapacityInfo from another LuiCapacity.
     *
     *  @param lc the LioCapacity to copy
     */
    public LuiCapacityInfo(LuiCapacity lc) {
	super(lc);
	this.luiIds = new ArrayList(lc.getLuiIds());
	this.maximumSeatCount = lc.getMaximumSeatCount();
	this.processingOrder = lc.getProcessingOrder();
	this.effectiveDate = null != lc.getEffectiveDate() ? new Date(lc.getEffectiveDate().getTime()) : null;
	this.expirationDate = null != lc.getExpirationDate() ? new Date(lc.getExpirationDate().getTime()) : null;
    }
    
    @Override
    public List<String> getLuiIds() {
	return new ArrayList(luiIds);
    }

    public void setLuiIds(List<String> luiIds) {
	this.luiIds = new ArrayList(luiIds);
    }
    
    @Override
    public Integer getMaximumSeatCount() {
	return maximumSeatCount;
    }

    public void setMaximumSeatCount(Integer maximumSeatCount) {
	this.maximumSeatCount = maximumSeatCount;
    }

    @Override
    public Integer getProcessingOrder() {
	return processingOrder;
    }

    public void setProcessingOrder(Integer processingOrder) {
	this.processingOrder = processingOrder;
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
}
