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

import org.kuali.student.brms.factfinder.dto.FactResultColumnInfo;

public class FactResultColumnInfoMapAdapter extends XmlAdapter<FactResultColumnInfoList, Map<String, FactResultColumnInfo>> {

	@Override
	public Map<String, FactResultColumnInfo> unmarshal(FactResultColumnInfoList v) throws Exception {
		if (v == null) return null;
		Map<String, FactResultColumnInfo> map = new HashMap<String, FactResultColumnInfo>();
		for(FactResultColumnInfoMapEntry type : v.getEntry() ) {
			map.put(type.getKey(), type.getValue());
		}
		return map;
	}

	@Override
	public FactResultColumnInfoList marshal(Map<String, FactResultColumnInfo> v) throws Exception {
		if (v == null) return null;
		FactResultColumnInfoList type = new FactResultColumnInfoList();
		for(Map.Entry<String, FactResultColumnInfo> e : v.entrySet()) {
			type.getEntry().add(new FactResultColumnInfoMapEntry(e.getKey(), e.getValue()));
		}
		return type;
	}


}
