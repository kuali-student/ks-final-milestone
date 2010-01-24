/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.brms.repository.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class RuleSetContainerInfo implements java.io.Serializable {

	/** Class serial version uid */
    private static final long serialVersionUID = 1L;

    @XmlElement
	private List<RuleSetInfo> ruleSetList = new ArrayList<RuleSetInfo>();

	public List<RuleSetInfo> getRuleSetList() {
		return this.ruleSetList;
	}

	public void addRuleSet(RuleSetInfo ruleSet) {
		this.ruleSetList.add(ruleSet);
	}

	public void setRuleSetList(List<RuleSetInfo> ruleSetList) {
		this.ruleSetList = ruleSetList;
	}
}
