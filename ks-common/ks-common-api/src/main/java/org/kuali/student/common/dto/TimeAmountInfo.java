/**
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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TimeAmountInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String atpDurationTypeKey; 
	@XmlElement
	private Integer timeQuantity; 
	public String getAtpDurationTypeKey(){
		return atpDurationTypeKey;
	}
	public void setAtpDurationTypeKey(String atpDurationTypeKey){
		this.atpDurationTypeKey = atpDurationTypeKey;
	}
	public Integer getTimeQuantity(){
		return timeQuantity;
	}
	public void setTimeQuantity(Integer timeQuantity){
		this.timeQuantity = timeQuantity;
	}
}
