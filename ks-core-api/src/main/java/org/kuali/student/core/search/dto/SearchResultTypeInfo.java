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
public class SearchResultTypeInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String name; 
	@XmlElement
	private String desc; 
	@XmlElement(name="resultColumn")
	@XmlElementWrapper(name="resultColumns")
	private List<ResultColumnInfo> resultColumns; 
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
	public List<ResultColumnInfo> getResultColumns(){
		if(resultColumns == null){
			resultColumns = new ArrayList<ResultColumnInfo>();
		}
		return resultColumns;
	}
	public void setResultColumns(List<ResultColumnInfo> resultColumns){
		this.resultColumns = resultColumns;
	}
	public String getKey(){
		return key;
	}
	public void setKey(String key){
		this.key = key;
	}
}