package org.kuali.student.core.search.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ComparisonParamInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String subSearchKey;
	private String param;
	public String getSubSearchKey() {
		return subSearchKey;
	}
	public void setSubSearchKey(String subSearchKey) {
		this.subSearchKey = subSearchKey;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
}
