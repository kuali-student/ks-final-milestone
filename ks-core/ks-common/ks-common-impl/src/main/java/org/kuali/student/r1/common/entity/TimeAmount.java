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

package org.kuali.student.r1.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Deprecated
@Embeddable
public class TimeAmount {

	@Column(name="ATP_DUR_TYP_KEY")
	private String atpDurationTypeKey;
	
	@Column(name="TM_QUANTITY")
	private Integer timeQuantity;
	
	public String getAtpDurationTypeKey() {
		return atpDurationTypeKey;
	}
	public void setAtpDurationTypeKey(String atpDurationTypeKey) {
		this.atpDurationTypeKey = atpDurationTypeKey;
	}
	public Integer getTimeQuantity() {
		return timeQuantity;
	}
	public void setTimeQuantity(Integer timeQuantity) {
		this.timeQuantity = timeQuantity;
	}
}
