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

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.kuali.student.rules.factfinder.dto.FactResultColumnInfoDTO;

public class FactResultColumnInfoMapAdapter extends XmlAdapter<FactResultColumnInfoList, Map<String, FactResultColumnInfoDTO>> {

	@Override
	public Map<String, FactResultColumnInfoDTO> unmarshal(FactResultColumnInfoList v) throws Exception {
		if (v == null) return null;
		Map<String, FactResultColumnInfoDTO> map = new HashMap<String, FactResultColumnInfoDTO>();
		for(FactResultColumnInfoMapEntry type : v.getEntry() ) {
			map.put(type.getKey(), type.getValue());
		}
		return map;
	}

	@Override
	public FactResultColumnInfoList marshal(Map<String, FactResultColumnInfoDTO> v) throws Exception {
		if (v == null) return null;
		FactResultColumnInfoList type = new FactResultColumnInfoList();
		for(Map.Entry<String, FactResultColumnInfoDTO> e : v.entrySet()) {
			type.getEntry().add(new FactResultColumnInfoMapEntry(e.getKey(), e.getValue()));
		}
		return type;
	}


}
