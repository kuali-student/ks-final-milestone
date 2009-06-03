package org.kuali.student.core.search.dto;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SearchTypeInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String name; 
	@XmlElement
	private String desc; 
	@XmlElement
	private SearchResultTypeInfo searchResultTypeInfo; 
	@XmlElement
	private SearchCriteriaTypeInfo searchCriteriaTypeInfo; 
	@XmlAttribute
	private String key; 
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getDesc(){
		return desc;
	}
	public void setDesc(String desc){
		this.desc = desc;
	}
	public SearchResultTypeInfo getSearchResultTypeInfo(){
		return searchResultTypeInfo;
	}
	public void setSearchResultTypeInfo(SearchResultTypeInfo searchResultTypeInfo){
		this.searchResultTypeInfo = searchResultTypeInfo;
	}
	public SearchCriteriaTypeInfo getSearchCriteriaTypeInfo(){
		return searchCriteriaTypeInfo;
	}
	public void setSearchCriteriaTypeInfo(SearchCriteriaTypeInfo searchCriteriaTypeInfo){
		this.searchCriteriaTypeInfo = searchCriteriaTypeInfo;
	}
	public String getKey(){
		return key;
	}
	public void setKey(String key){
		this.key = key;
	}
}