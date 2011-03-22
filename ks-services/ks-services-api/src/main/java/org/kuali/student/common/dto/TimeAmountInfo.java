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

import org.kuali.student.common.infc.TimeAmountInfc;

@XmlAccessorType(XmlAccessType.FIELD)
public class TimeAmountInfo implements TimeAmountInfc, Serializable {
	private static final long serialVersionUID = 1L;
	
	@XmlElement
	private final String atpDurationTypeKey; 
	
	@XmlElement
	private final Integer timeQuantity; 
	
	private TimeAmountInfo() {
		atpDurationTypeKey = null; 
		timeQuantity = null;
	}
	
	private TimeAmountInfo(TimeAmountInfc builder) {
		this.atpDurationTypeKey = builder.getAtpDurationTypeKey();
		this.timeQuantity = builder.getTimeQuantity();
	}
	
	public String getAtpDurationTypeKey(){
		return atpDurationTypeKey;
	}
	
	public Integer getTimeQuantity(){
		return timeQuantity;
	}
	
	public static class Builder implements TimeAmountInfc {
		private String atpDurationTypeKey;
		private Integer timeQuantity;

		public Builder() {}
		
		public Builder(TimeAmountInfc taInfo) {
			this.atpDurationTypeKey = taInfo.getAtpDurationTypeKey();
			this.timeQuantity = taInfo.getTimeQuantity();
		}
		
		public TimeAmountInfo build() {
			return new TimeAmountInfo(this);
		}

		@Override
		public String getAtpDurationTypeKey() {
			return atpDurationTypeKey;
		}

		@Override
		public Integer getTimeQuantity() {
			return timeQuantity;
		}
	}
}
