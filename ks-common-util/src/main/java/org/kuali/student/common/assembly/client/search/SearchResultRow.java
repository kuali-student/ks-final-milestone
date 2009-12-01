package org.kuali.student.common.assembly.client.search;

import java.util.ArrayList;
import java.util.List;

public class SearchResultRow {
	private List<SearchResultColumn> columns;

	public List<SearchResultColumn> getColumns() {
		if (columns == null) {
			columns = new ArrayList<SearchResultColumn>(0);
		}
		return columns;
	}

	public void setColumns(List<SearchResultColumn> columns) {
		this.columns = columns;
	}
}
