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

//import org.kuali.student.r2.common.util.date.DateFormatters;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.core.search.infc.SearchResultRow;
//import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchResultInfo", propOrder = {
                "startAt", "totalResults", "rows", "sortColumn",
                "sortDirection", "_futureElements" }) 

public class SearchResultInfo 
    implements SearchResult, Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElement        
    private Integer startAt;
    
    @XmlElement    
    private Integer totalResults;
    
    @XmlElement    
    private List<SearchResultRowInfo> rows;
    
    @XmlElement    
    private String sortColumn;
    
    @XmlElement    
    private SortDirection sortDirection;

    @XmlAnyElement
    private List<Object> _futureElements;  


    /**
     * Constructs a new SearchResultInfo.
     */
    public SearchResultInfo() {
    }

    /**
     * Constructs a new SearchResultInfo from another
     * SearchResult.
     *
     * @param result the SearchResult to copy
     */
    public SearchResultInfo(SearchResult result) {
        if (result != null) {
            this.startAt = result.getStartAt();
            this.totalResults = result.getTotalResults();

            this.rows = new ArrayList<SearchResultRowInfo>();
            for (SearchResultRow row : result.getRows()) {
                this.rows.add(new SearchResultRowInfo(row));
            }

            this.sortColumn = result.getSortColumn();
            this.sortDirection = result.getSortDirection();
        }
    }
    
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

    public void sortRows() {
        if (sortColumn != null) {
            Collections.sort(getRows(), new SearchResultRowComparator(sortColumn, sortDirection));
        }
    }

    /**
     * Compares two SearchResultRow rows with a given sort direction and column
     *
     */
    private static class SearchResultRowComparator implements Comparator<SearchResultRowInfo> {
        private String sortColumn;
        private SortDirection sortDirection;

        public SearchResultRowComparator(String sortColumn, SortDirection sortDirection) {
            super();
            this.sortColumn = sortColumn;
            this.sortDirection = sortDirection;

        }

        @Override
        public int compare(SearchResultRowInfo r1, SearchResultRowInfo r2) {
            int compareResult = 0;

            //Pares out the cell values to compare
            String v1 = null;
            String v2 = null;
            for (SearchResultCellInfo c : r1.getCells()) {
                if (sortColumn.equals(c.getKey())) {
                    v1 = c.getValue();
                    break;
                }
            }
            for (SearchResultCellInfo c : r2.getCells()) {
                if (sortColumn.equals(c.getKey())) {
                    v2 = c.getValue();
                    break;
                }
            }

            //Compare the values wiuth the right type (SHould be done more efficiently
            try {
                Integer v1Integer = Integer.parseInt(v1);
                Integer v2Integer = Integer.parseInt(v2);
                compareResult = v1Integer.compareTo(v2Integer);
            } catch (Exception e1) {
                if (v1 != null && v2 != null && ("true".equals(v1.toLowerCase()) || "false".equals(v1.toLowerCase())) &&
                        ("true".equals(v2.toLowerCase()) || "false".equals(v2.toLowerCase()))) {
                    Boolean v1Boolean = Boolean.parseBoolean(v1);
                    Boolean v2Boolean = Boolean.parseBoolean(v2);
                    compareResult = v1Boolean.compareTo(v2Boolean);
                } else {
                    try {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        Date v1Date = df.parse(v1);
                        Date v2Date = df.parse(v2);
                        compareResult = v1Date.compareTo(v2Date);
                    } catch (Exception e) {
                        if (v1 != null && v2 != null) {
                            compareResult = v1.compareTo(v2);
                        } else if (v2 == null) {
                            compareResult = 0;
                        } else {
                            compareResult = -1;
                        }
                    }
                }
            }

            //Sort reverse if order is descending
            if (SortDirection.DESC.equals(sortDirection)) {
                return -1 * compareResult;
            }
            return compareResult;
        }
    }
}
