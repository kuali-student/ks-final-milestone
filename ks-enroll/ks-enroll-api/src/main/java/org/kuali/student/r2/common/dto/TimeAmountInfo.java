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
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.TimeAmount;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeAmountInfo", propOrder = {"atpDurationTypeKey", "timeQuantity", "_futureElements"})
public class TimeAmountInfo implements TimeAmount, Serializable {
	private static final long serialVersionUID = 1L;
	
	@XmlElement
	private String atpDurationTypeKey; 
	
	@XmlElement
	private Integer timeQuantity; 
	
    @XmlAnyElement
    private final List<Element> _futureElements;    
		
    public void setAtpDurationTypeKey(String atpDurationTypeKey) {
		this.atpDurationTypeKey = atpDurationTypeKey;
	}

	public void setTimeQuantity(Integer timeQuantity) {
		this.timeQuantity = timeQuantity;
	}
	
    public TimeAmountInfo() {
		atpDurationTypeKey = null; 
		timeQuantity = null;
		_futureElements = null;
	}
	
    public TimeAmountInfo(TimeAmount builder) {           
        
        this._futureElements = null;
        
        if(null == builder) {
            return;
        }
        
        this.atpDurationTypeKey = builder.getAtpDurationTypeKey();
		this.timeQuantity = builder.getTimeQuantity();
	}
	
    @Override
	public String getAtpDurationTypeKey(){
		return atpDurationTypeKey;
	}
	
    @Override
	public Integer getTimeQuantity(){
		return timeQuantity;
	}
}
