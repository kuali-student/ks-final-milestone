package org.kuali.student.core.search.dto;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class SearchRelationship implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private TypeAttribute lhs; 
	@XmlElement
	private TypeAttribute rhs; 
	public TypeAttribute getLhs(){
		return lhs;
	}
	public void setLhs(TypeAttribute lhs){
		this.lhs = lhs;
	}
	public TypeAttribute getRhs(){
		return rhs;
	}
	public void setRhs(TypeAttribute rhs){
		this.rhs = rhs;
	}
}