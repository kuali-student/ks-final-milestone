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
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.common.infc.ModelBuilder;
import org.kuali.student.common.infc.StatusInfc;
import org.w3c.dom.Element;

/**
 * Information about the state of an object
 * 
 * @author nwright
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatusInfo", propOrder = {"success", "message", "_futureElements"})
public class StatusInfo implements StatusInfc, Serializable {
	private static final long serialVersionUID = 1L;
	
	@XmlElement
	private final Boolean success;
	
	@XmlElement
	private final String message;
	
    @XmlAnyElement
    private final List<Element> _futureElements;	
	
	private StatusInfo() {
		success = true;
		message = "";
		_futureElements = null;
	}
	
	private StatusInfo(StatusInfc builder) {
		this.success = new Boolean(builder.isSuccess().booleanValue());
		this.message = builder.getMessage();
		this._futureElements = null;
	}

    @Override
	public Boolean isSuccess(){
		return success;
	}

    @Override
	public String getMessage() {
		return message;
	}
	
	public static class Builder implements ModelBuilder<StatusInfo>, StatusInfc {
		private Boolean success;
		private String message;

		public Builder() {}
		
		public Builder(StatusInfc status) {
			this.success = status.isSuccess();
			this.message = status.getMessage();
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

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public void setMessage(String message) {
            this.message = message;
        }	
	}
}
