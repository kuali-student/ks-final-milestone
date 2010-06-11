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

package org.kuali.student.core.search.dto;

import java.io.Serializable;
import java.util.List;

public class SearchParam implements Serializable {
	private static final long serialVersionUID = 1L;
	private String value;
	private List<String> listValue;
	private String key;

	public Object getValue() {
		if (value != null) {
			return value;
		} else {
			return listValue;
		}
	}

	public void setValue(String value) {
		this.value = value;
		listValue = null;
	}

	public void setValue(List<String> listValue) {
		this.listValue = listValue;
		value = null;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "SearchParam[key=" + key + ", value=" + value + ", listValue="
				+ listValue + "]";
	}
}
