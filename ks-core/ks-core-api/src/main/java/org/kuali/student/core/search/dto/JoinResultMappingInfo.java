package org.kuali.student.core.search.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class JoinResultMappingInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String subSearchKey;
	private String subSearchResultParam;
	private String resultParam;
	public String getSubSearchKey() {
		return subSearchKey;
	}
	public void setSubSearchKey(String subSearchKey) {
		this.subSearchKey = subSearchKey;
	}
	public String getSubSearchResultParam() {
		return subSearchResultParam;
	}
	public void setSubSearchResultParam(String subSearchResultParam) {
		this.subSearchResultParam = subSearchResultParam;
	}
	public String getResultParam() {
		return resultParam;
	}
	public void setResultParam(String resultParam) {
		this.resultParam = resultParam;
	}
}
