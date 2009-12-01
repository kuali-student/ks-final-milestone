package org.kuali.student.common.assembly.client.search;

import java.util.List;

public class SearchResult {
	private Integer startAt;
	private Integer totalResults;
	private List<SearchResultRow> rows;
	private String sortColumn;
	private SortDirection sortDirection;
	public Integer getStartAt() {
		return startAt;
	}
	public void setStartAt(Integer startAt) {
		this.startAt = startAt;
	}
	public Integer getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(Integer totalResults) {
		this.totalResults = totalResults;
	}
	public List<SearchResultRow> getRows() {
		return rows;
	}
	public void setRows(List<SearchResultRow> rows) {
		this.rows = rows;
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
}
