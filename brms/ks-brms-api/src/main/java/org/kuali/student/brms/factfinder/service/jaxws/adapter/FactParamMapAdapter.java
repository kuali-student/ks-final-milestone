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

package org.kuali.student.brms.factfinder.service.jaxws.adapter;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.kuali.student.brms.factfinder.dto.FactParamInfo;

public class FactParamMapAdapter extends XmlAdapter<FactParamList, Map<String, FactParamInfo>> {

	@Override
	public Map<String, FactParamInfo> unmarshal(FactParamList v) throws Exception {
		if (v == null) return null;
		Map<String, FactParamInfo> map = new HashMap<String, FactParamInfo>();
		for(FactParamMapEntry type : v.getEntry() ) {
			map.put(type.getKey(), type.getValue());
		}
		return map;
	}

	@Override
	public FactParamList marshal(Map<String, FactParamInfo> v) throws Exception {
		if (v == null) return null;
		FactParamList type = new FactParamList();
		for(Map.Entry<String, FactParamInfo> e : v.entrySet()) {
			type.getEntry().add(new FactParamMapEntry(e.getKey(), e.getValue()));
		}
		return type;
	}


}
