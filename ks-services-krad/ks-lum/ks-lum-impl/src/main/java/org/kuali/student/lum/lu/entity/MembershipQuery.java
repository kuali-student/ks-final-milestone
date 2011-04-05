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

package org.kuali.student.lum.lu.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.BaseEntity;

@Entity
@Table(name = "KSLU_MEMSHIP")
public class MembershipQuery extends BaseEntity{

	@Column(name = "SEARCH_TYPE_KEY")
	private String searchTypeKey;

	@OneToMany(cascade = CascadeType.ALL)
	private List<SearchParameter> searchParameters = new ArrayList<SearchParameter>();

	public String getSearchTypeKey() {
		return searchTypeKey;
	}

	public void setSearchTypeKey(String searchTypeKey) {
		this.searchTypeKey = searchTypeKey;
	}

	public List<SearchParameter> getSearchParameters() {
		return searchParameters;
	}

	public void setSearchParameters(List<SearchParameter> searchParameters) {
		this.searchParameters = searchParameters;
	}

	@Override
	public String toString() {
		return "MembershipQuery[searchTypeKey=" + searchTypeKey + "]";
	}

}
