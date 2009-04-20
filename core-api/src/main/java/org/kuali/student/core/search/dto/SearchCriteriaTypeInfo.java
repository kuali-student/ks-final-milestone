package org.kuali.student.core.search.dto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;


@XmlAccessorType(XmlAccessType.FIELD)
public class SearchCriteriaTypeInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String name; 
	@XmlElement
	private String desc; 
	@XmlElement(name="queryParam")
	@XmlElementWrapper(name="queryParams")
	private List<QueryParamInfo> queryParams; 
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
	public List<QueryParamInfo> getQueryParams(){
		if(queryParams == null){
			queryParams = new ArrayList<QueryParamInfo>();
		}
		return queryParams;
	}
	public void setQueryParams(List<QueryParamInfo> queryParams){
		this.queryParams = queryParams;
	}
	public String getKey(){
		return key;
	}
	public void setKey(String key){
		this.key = key;
	}
}