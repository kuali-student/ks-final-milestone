package org.kuali.student.core.search.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Result implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private List<ResultCell> resultCells;

	public List<ResultCell> getResultCells() {
		if (resultCells == null) {
			resultCells = new ArrayList<ResultCell>(0);
		}
		return resultCells;
	}

	public void setResultCells(List<ResultCell> resultCells) {
		this.resultCells = resultCells;
	}
}
