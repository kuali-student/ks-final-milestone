package org.kuali.student.core.search.newdto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchResultRow {
	private List<SearchResultCell> cells;

	public List<SearchResultCell> getCells() {
		if (cells == null) {
			cells = new ArrayList<SearchResultCell>(0);
		}
		return cells;
	}

	public void setCells(List<SearchResultCell> cells) {
		this.cells = cells;
	}
}
