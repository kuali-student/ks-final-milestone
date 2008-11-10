package edu.umd.ks.poc.lum.util.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class JaxbAttributeListMapListAdapter extends XmlAdapter<JaxbAttributesList, Map<String, List<String>>> {
	public Map<String, List<String>> unmarshal(JaxbAttributesList value) {
		Map<String, List<String>> resultMap = new HashMap<String, List<String>>();
		for (JaxbAttributes a : value.getAttribute()) {
			resultMap.put(a.type, a.values);
		}
		return resultMap;
	}

	public JaxbAttributesList marshal(Map<String, List<String>> value) {
		JaxbAttributesList attributes = new JaxbAttributesList();
		if(value!=null){
			for (Map.Entry<String, List<String>> e : value.entrySet()) {
				attributes.getAttribute().add(
						new JaxbAttributes(e.getKey(), e.getValue()));
			}
		}
		return attributes;
	}
}
