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
package org.kuali.student.rules.repository.service.jaxws.adapter;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.kuali.student.rules.repository.dto.RuleDTO;

public class RuleMapAdapter extends XmlAdapter<RuleMapType, Map<String, RuleDTO>> {

	@Override
	public Map<String, RuleDTO> unmarshal(RuleMapType v) throws Exception {
		if (v == null) return null;
		Map<String, RuleDTO> map = new LinkedHashMap<String, RuleDTO>();
		for(RuleMapEntryType type : v.getEntry() ) {
			map.put(type.getName(), type.getRule());
		}
		return map;
	}

	@Override
	public RuleMapType marshal(Map<String, RuleDTO> v) throws Exception {
		if (v == null) return null;
		RuleMapType type = new RuleMapType();
		for(Map.Entry<String, RuleDTO> e : v.entrySet()) {
			type.getEntry().add(new RuleMapEntryType(e.getKey(), e.getValue()));
		}
		return type;
	}


}
