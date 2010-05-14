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

import org.kuali.student.rules.factfinder.dto.FactParamDTO;

public class FactParamMapAdapter extends XmlAdapter<FactParamList, Map<String, FactParamDTO>> {

	@Override
	public Map<String, FactParamDTO> unmarshal(FactParamList v) throws Exception {
		if (v == null) return null;
		Map<String, FactParamDTO> map = new HashMap<String, FactParamDTO>();
		for(FactParamMapEntry type : v.getEntry() ) {
			map.put(type.getKey(), type.getValue());
		}
		return map;
	}

	@Override
	public FactParamList marshal(Map<String, FactParamDTO> v) throws Exception {
		if (v == null) return null;
		FactParamList type = new FactParamList();
		for(Map.Entry<String, FactParamDTO> e : v.entrySet()) {
			type.getEntry().add(new FactParamMapEntry(e.getKey(), e.getValue()));
		}
		return type;
	}


}
