package org.kuali.student.core.search.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class SearchResult implements Serializable {
	private static final long serialVersionUID = 1L;
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
		if (rows == null) {
			rows = new ArrayList<SearchResultRow>(0);
		}
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
