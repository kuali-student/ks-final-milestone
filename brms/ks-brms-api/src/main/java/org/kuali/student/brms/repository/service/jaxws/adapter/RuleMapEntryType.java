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

package org.kuali.student.brms.repository.service.jaxws.adapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.brms.repository.dto.RuleInfo;

@XmlAccessorType(XmlAccessType.FIELD)
public class RuleMapEntryType {
	@XmlAttribute
	private String name;
	
    @XmlElement
	private RuleInfo rule;
	
    public RuleMapEntryType() {}

	public RuleMapEntryType(String name, RuleInfo rule) {
		this.name = name;
		this.rule = rule;
	}
	
	public String getName() {
		return this.name;
	}
	
	public RuleInfo getRule() {
		return this.rule;
	}
}
