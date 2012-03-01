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

package org.kuali.student.r1.lum.lu.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.r1.common.dto.TypeInfo;

/**
 *Detailed information about a single learning unit type.
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
public class LuTypeInfo extends TypeInfo{

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String instructionalFormat;

    @XmlElement
    private String deliveryMethod;
    
	public String getInstructionalFormat() {
		return instructionalFormat;
	}

	public void setInstructionalFormat(String instructionalFormat) {
		this.instructionalFormat = instructionalFormat;
	}

	public String getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}

	@Override
	public String toString() {
		return "LuTypeInfo[id=" + getId() + "]";
	}
}
