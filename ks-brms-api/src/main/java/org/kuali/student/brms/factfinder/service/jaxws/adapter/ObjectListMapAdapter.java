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
package org.kuali.student.brms.factfinder.service.jaxws.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ObjectListMapAdapter extends XmlAdapter<ObjectListMap, List<Map<String, Object>>> {

	@Override
	public List<Map<String, Object>> unmarshal(ObjectListMap v) throws Exception {
		if (v == null) return null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(ObjectList objectList : v.getEntry()) {
			Map<String, Object> map = new HashMap<String, Object>(objectList.getEntry().size());
			for(ObjectMapEntry type : objectList.getEntry() ) {
				map.put(type.getKey(), type.getValue());
			}
			list.add(map);
		}
		return list;
	}

	@Override
	public ObjectListMap marshal(List<Map<String, Object>> v) throws Exception {
		if (v == null) return null;
		ObjectListMap list = new ObjectListMap();
		for(Map<String, Object> map : v) {
			ObjectList type = new ObjectList();
			for(Map.Entry<String, Object> e : map.entrySet()) {
				type.getEntry().add(new ObjectMapEntry(e.getKey(), e.getValue()));
			}
			list.getEntry().add(type);
		}
		return list;
	}


}
