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

import org.kuali.student.r2.core.search.infc.SearchResult;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)

public class SearchResultInfo 
    implements SearchResult, Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer startAt;
    
    private Integer totalResults;
    
    private List<SearchResultRowInfo> rows;
    
    private String sortColumn;
    
    private SortDirection sortDirection;

    @Override
    public Integer getStartAt() {
        return startAt;
    }
	
    public void setStartAt(Integer startAt) {
        this.startAt = startAt;
    }
	
    @Override
    public Integer getTotalResults() {
        return totalResults;
    }
    
    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }
	
    @Override
    public List<SearchResultRowInfo> getRows() {
        if (rows == null) {
            rows = new ArrayList<SearchResultRowInfo>(0);
        }
        return rows;
    }
    
    public void setRows(List<SearchResultRowInfo> rows) {
        this.rows = rows;
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
}
