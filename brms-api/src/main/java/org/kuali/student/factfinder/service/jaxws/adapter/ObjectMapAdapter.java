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

public class ObjectMapAdapter extends XmlAdapter<ObjectList, Map<String, Object>> {

	@Override
	public Map<String, Object> unmarshal(ObjectList v) throws Exception {
		if (v == null) return null;
		Map<String, Object> map = new HashMap<String, Object>();
		for(ObjectMapEntry type : v.getEntry() ) {
			map.put(type.getKey(), type.getValue());
		}
		return map;
	}

	@Override
	public ObjectList marshal(Map<String, Object> v) throws Exception {
		if (v == null) return null;
		ObjectList type = new ObjectList();
		for(Map.Entry<String, Object> e : v.entrySet()) {
			type.getEntry().add(new ObjectMapEntry(e.getKey(), e.getValue()));
		}
		return type;
	}


}
