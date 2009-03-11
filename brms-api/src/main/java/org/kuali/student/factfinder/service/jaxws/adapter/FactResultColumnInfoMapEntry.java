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
package org.kuali.student.rules.factfinder.service.jaxws.adapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.rules.factfinder.dto.FactResultColumnInfoDTO;

@XmlAccessorType(XmlAccessType.FIELD)
public class FactResultColumnInfoMapEntry {
	@XmlAttribute
	private String key;
	
    @XmlElement
	private FactResultColumnInfoDTO value;

    public FactResultColumnInfoMapEntry() {}

	public FactResultColumnInfoMapEntry(String key, FactResultColumnInfoDTO value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public FactResultColumnInfoDTO getValue() {
		return this.value;
	}
}
