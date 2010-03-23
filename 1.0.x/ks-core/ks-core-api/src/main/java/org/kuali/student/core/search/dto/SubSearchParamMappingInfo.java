package org.kuali.student.core.search.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class SubSearchParamMappingInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String crossSearchParam;
	private String subSearchParam;
	public String getCrossSearchParam() {
		return crossSearchParam;
	}
	public void setCrossSearchParam(String crossSearchParam) {
		this.crossSearchParam = crossSearchParam;
	}
	public String getSubSearchParam() {
		return subSearchParam;
	}
	public void setSubSearchParam(String subSearchParam) {
		this.subSearchParam = subSearchParam;
	}
}
