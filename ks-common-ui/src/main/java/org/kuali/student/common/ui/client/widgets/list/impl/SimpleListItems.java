package org.kuali.student.common.ui.client.widgets.list.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.widgets.list.ListItems;

public class SimpleListItems implements ListItems {
	HashMap<String,String> items = new HashMap<String,String>();
	HashMap<String,HashMap<String,String>> attributes = new HashMap<String,HashMap<String,String>>();
	
	public void addItem(String id, String value){
		items.put(id, value);
	}

	public void addAttribute(String itemId, String attrKey, String value){
		HashMap<String,String> attributeMap = attributes.get(itemId);
		if(attributeMap==null){
			attributeMap = new HashMap<String,String>();
			attributes.put(itemId, attributeMap);
		}
		attributeMap.put(attrKey, value);
	}

	
	@Override
	public List<String> getAttrKeys() {
		ArrayList<String> attrKeys = new ArrayList<String>();
		for(Map.Entry<String,HashMap<String,String>> attributeEntry:attributes.entrySet()){
			attrKeys.addAll(attributeEntry.getValue().keySet());
		}
		return attrKeys;
	}

	@Override
	public String getItemAttribute(String id, String attrkey) {
		return attributes.get("id").get("attrkey");
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	@Override
	public List<String> getItemIds() {
		return new ArrayList<String>(items.keySet());
	}

	@Override
	public String getItemText(String id) {
		return items.get(id);
	}

}
