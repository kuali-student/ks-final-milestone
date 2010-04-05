/*
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
 
package org.kuali.student.core.assembly.util;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * Stores an ID and its translated value 
 * 
 * @author Kuali Student Team
 *
 */
public class IdTranslation {
	private String id;
	private String display;
	private Map<String, String> attributes = new HashMap<String, String>();
	public IdTranslation() {
		
	}
	public IdTranslation(String id, String display) {
		this.id = id;
		this.display = display;
	}
	
	public void addAttribute(String key, String value) {
		attributes.put(key, value);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	
}
