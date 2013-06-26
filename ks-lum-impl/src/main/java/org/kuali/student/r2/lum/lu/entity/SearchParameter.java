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

package org.kuali.student.r2.lum.lu.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.r1.common.entity.BaseEntity;

@Entity
@Table(name = "KSLU_SPARAM")
public class SearchParameter extends BaseEntity{

	@Column(name = "SEARCH_PARAM_KEY")
	private String key;

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private List<SearchParameterValue> values = new ArrayList<SearchParameterValue>();

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<SearchParameterValue> getValues() {
		return values;
	}

	public void setValues(List<SearchParameterValue> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "SearchParamInfo[key=" + key + ", values=" + values + "]";
	}
}
