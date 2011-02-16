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

package org.kuali.student.common.search.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class SearchRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String searchKey;
	private List<SearchParam> params;
	private String sortColumn;
	private SortDirection sortDirection;
	private Integer startAt;
	private Integer maxResults;
	private Boolean neededTotalResults;
	
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public List<SearchParam> getParams() {
		if(params == null){
			params = new ArrayList<SearchParam>();
		}
		return params;
	}
	public void setParams(List<SearchParam> params) {
		this.params = params;
	}
	public String getSortColumn() {
		return sortColumn;
	}
	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
	public SortDirection getSortDirection() {
		return sortDirection;
	}
	public void setSortDirection(SortDirection sortDirection) {
		this.sortDirection = sortDirection;
	}
	public Integer getStartAt() {
		return startAt;
	}
	public void setStartAt(Integer startAt) {
		this.startAt = startAt;
	}
	public Integer getMaxResults() {
		return maxResults;
	}
	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}
	public Boolean getNeededTotalResults() {
		return neededTotalResults;
	}
	public void setNeededTotalResults(Boolean neededTotalResults) {
		this.neededTotalResults = neededTotalResults;
	}
	@Override
	public String toString() {
		return "SearchRequest [searchKey=" + searchKey + ", params=" + params
				+ ", sortColumn=" + sortColumn + ", sortDirection="
				+ sortDirection + ", startAt=" + startAt + ", maxResults="
				+ maxResults + ", neededTotalResults=" + neededTotalResults
				+ "]";
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchRequest that = (SearchRequest) o;

        if (maxResults != null ? !maxResults.equals(that.maxResults) : that.maxResults != null) return false;
        if (neededTotalResults != null ? !neededTotalResults.equals(that.neededTotalResults) : that.neededTotalResults != null)
            return false;
        if (params != null ? !params.equals(that.params) : that.params != null) return false;
        if (searchKey != null ? !searchKey.equals(that.searchKey) : that.searchKey != null) return false;
        if (sortColumn != null ? !sortColumn.equals(that.sortColumn) : that.sortColumn != null) return false;
        if (sortDirection != that.sortDirection) return false;
        if (startAt != null ? !startAt.equals(that.startAt) : that.startAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = searchKey != null ? searchKey.hashCode() : 0;
        result = 31 * result + (params != null ? params.hashCode() : 0);
        result = 31 * result + (sortColumn != null ? sortColumn.hashCode() : 0);
        result = 31 * result + (sortDirection != null ? sortDirection.hashCode() : 0);
        result = 31 * result + (startAt != null ? startAt.hashCode() : 0);
        result = 31 * result + (maxResults != null ? maxResults.hashCode() : 0);
        result = 31 * result + (neededTotalResults != null ? neededTotalResults.hashCode() : 0);
        return result;
    }
}
