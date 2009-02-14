package org.kuali.student.core.search.dto;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Result implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private ResultCell resultCell; 
	public ResultCell getResultCell(){
		return resultCell;
	}
	public void setResultCell(ResultCell resultCell){
		this.resultCell = resultCell;
	}
}