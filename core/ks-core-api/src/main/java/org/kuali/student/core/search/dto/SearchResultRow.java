package org.kuali.student.core.search.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchResultRow implements Serializable {
	private static final long serialVersionUID = 1L;
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
