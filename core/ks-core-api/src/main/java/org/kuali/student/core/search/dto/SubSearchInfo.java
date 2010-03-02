package org.kuali.student.core.search.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
@XmlAccessorType(XmlAccessType.FIELD)
public class SubSearchInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String key;
	private String searchkey;
	private List<SubSearchParamMappingInfo> subSearchParamMappings;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getSearchkey() {
		return searchkey;
	}
	public void setSearchkey(String searchkey) {
		this.searchkey = searchkey;
	}
	public List<SubSearchParamMappingInfo> getSubSearchParamMappings() {
		if(subSearchParamMappings == null){
			subSearchParamMappings = new ArrayList<SubSearchParamMappingInfo>();
		}
		return subSearchParamMappings;
	}
	public void setSubSearchParamMappings(
			List<SubSearchParamMappingInfo> subSearchParamMappings) {
		this.subSearchParamMappings = subSearchParamMappings;
	}
}
