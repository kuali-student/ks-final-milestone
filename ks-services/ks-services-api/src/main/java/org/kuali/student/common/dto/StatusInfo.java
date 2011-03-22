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

import org.kuali.student.common.infc.StatusInfc;

@XmlAccessorType(XmlAccessType.FIELD)
public class StatusInfo implements StatusInfc, Serializable {
	private static final long serialVersionUID = 1L;
	
	@XmlElement
	private final Boolean success;
	
	@XmlElement
	private final String message;
	
	private StatusInfo() {
		success = true;
		message = "";
	}
	
	private StatusInfo(StatusInfc builder) {
		this.success = new Boolean(builder.isSuccess().booleanValue());
		this.message = builder.getMessage();
	}
	
	public Boolean isSuccess(){
		return success;
	}
	
	public String getMessage() {
		return message;
	}
	
	public static class Builder implements StatusInfc {
		private Boolean success;
		private String message;

		public Builder() {}
		
		public Builder(StatusInfc status) {
			this.success = status.isSuccess();
			this.message = status.getMessage();
		}

		public Builder setSuccess(Boolean bool) {
			this.success = bool;
			return this;
		}
		
		public StatusInfo build() {
			return new StatusInfo(this);
		}

		@Override
		public Boolean isSuccess() {
			return success;
		}

		@Override
		public String getMessage() {
			return message;
		}
	}
}
