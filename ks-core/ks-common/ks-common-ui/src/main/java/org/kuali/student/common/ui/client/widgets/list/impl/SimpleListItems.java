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

	public void clear(){
		items.clear();
		attributes.clear();
	}
}
