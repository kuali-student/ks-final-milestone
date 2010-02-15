package org.kuali.student.core.search.newdto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class SearchRequest {
	private String searchKey;//TODO How to figure out which service to call? maybe we just look through each service the assembler has and find the key
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
	
}
