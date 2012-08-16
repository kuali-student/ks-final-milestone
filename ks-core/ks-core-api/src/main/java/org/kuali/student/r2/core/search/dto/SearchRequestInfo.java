/*
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.search.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.core.search.infc.SearchRequest;
import org.kuali.student.r2.core.search.infc.SearchParam;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchRequestInfo", propOrder = {
                "searchKey", "params", "sortColumn", "sortDirection",
                "startAt", "maxResults", "neededTotalResults", 
                "_futureElements" })

public class SearchRequestInfo 
    implements SearchRequest, Serializable {
    
    private static final long serialVersionUID = 1L;

    @XmlElement    
    private String searchKey;
    
    @XmlElement
    private List<SearchParamInfo> params;
    
    @XmlElement
    private String sortColumn;

    @XmlElement
    private SortDirection sortDirection;

    @XmlElement
    private Integer startAt;

    @XmlElement
    private Integer maxResults;

    @XmlElement
    private Boolean neededTotalResults;
    
    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new SearchRequestInfo.
     */
    public SearchRequestInfo() {
    }

    /**
     * Constructs a new SearchRequestInfo from another SearchRequest.
     *
     * @param requst the SearchRequest to copy
     */
    public SearchRequestInfo(SearchRequest request) {
        if (request != null) {
            this.searchKey = request.getSearchKey();

            this.params = new ArrayList<SearchParamInfo>();
            for (SearchParam param : request.getParams()) {
                this.params.add(new SearchParamInfo(param));
            }
             
            this.sortColumn = request.getSortColumn();
            this.sortDirection = request.getSortDirection();
            this.startAt = request.getStartAt();
            this.maxResults = request.getMaxResults();
            this.neededTotalResults = request.getNeededTotalResults();
        }
    }
                
    /**
     * Constructs a new SearchRequestInfo.
     *
     * @param searchKey a search key
     */    
    public SearchRequestInfo(String searchKey) {
        this.searchKey = searchKey;
    }
    
    public void addParam(String key, String value) {
        getParams().add(new SearchParamInfo(key, value));
    }
    
    public void addParam(String key, List<String> value) {
        getParams().add(new SearchParamInfo(key, value));
    }

    @Override
    public String getSearchKey() {
        return searchKey;
    }
    
    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
	
    @Override
    public List<SearchParamInfo> getParams() {
        if(params == null) {
            params = new ArrayList<SearchParamInfo>();
        }

        return params;
    }

    public void setParams(List<SearchParamInfo> params) {
        this.params = params;
    }

    @Override
    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    @Override
    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }

    @Override
    public Integer getStartAt() {
        return startAt;
    }

    public void setStartAt(Integer startAt) {
        this.startAt = startAt;
    }

    @Override
    public Integer getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    @Override
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
        
        SearchRequestInfo that = (SearchRequestInfo) o;
        
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
