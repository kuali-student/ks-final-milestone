package org.kuali.student.core.ws.binding;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class JaxbAttributeMapListAdapter extends
		XmlAdapter<JaxbAttributeList, Map<String, String>> {
	public Map<String, String> unmarshal(JaxbAttributeList value) {
		if(value == null) return null;
		Map<String, String> resultMap = new HashMap<String, String>();
		for (JaxbAttribute a : value.getAttribute()) {
			resultMap.put(a.key, a.value);
		}
		return resultMap;
	}

	public JaxbAttributeList marshal(Map<String, String> value) {
		if(value == null) return null;
		JaxbAttributeList attributes = new JaxbAttributeList();
		for (Map.Entry<String, String> e : value.entrySet()) {
			attributes.getAttribute().add(
					new JaxbAttribute(e.getKey(), e.getValue()));
		}
		return attributes;
	}

}