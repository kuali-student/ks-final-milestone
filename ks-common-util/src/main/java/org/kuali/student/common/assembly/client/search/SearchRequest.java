package org.kuali.student.common.assembly.client.search;

import java.util.List;


public class SearchRequest {
	private String searchKey;//TODO How to figure out which service to call? maybe we just look through each service the assembler has and find the key
	private List<SearchParam> params;
	private String sortColumn;
	private SortDirection sortDirection;
	private Integer startAt;
	private Integer maxResults;
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public List<SearchParam> getParams() {
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
	
}
