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

package org.kuali.student.r1.common.search.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
@Deprecated
@XmlAccessorType(XmlAccessType.FIELD)
public class SubSearchInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String key;
	private String searchkey;
	private List<SubSearchParamMappingInfo> subSearchParamMappings;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getSearchkey() {
		return searchkey;
	}
	public void setSearchkey(String searchkey) {
		this.searchkey = searchkey;
	}
	public List<SubSearchParamMappingInfo> getSubSearchParamMappings() {
		if(subSearchParamMappings == null){
			subSearchParamMappings = new ArrayList<SubSearchParamMappingInfo>();
		}
		return subSearchParamMappings;
	}
	public void setSubSearchParamMappings(
			List<SubSearchParamMappingInfo> subSearchParamMappings) {
		this.subSearchParamMappings = subSearchParamMappings;
	}
}
