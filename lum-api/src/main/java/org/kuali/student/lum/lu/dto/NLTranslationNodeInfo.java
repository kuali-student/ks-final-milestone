package org.kuali.student.lum.lu.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class NLTranslationNodeInfo {
    @XmlAttribute
	private String id;

    @XmlAttribute
    private String booleanId;

    @XmlAttribute
    private String operator;

	@XmlElement
	private String booleanExpr;

    @XmlElement
	private String nlTranslation;

    @XmlElement
    private NLTranslationNodeInfo parentNode;

	@XmlElement
    private List<NLTranslationNodeInfo> childNodeList = new ArrayList<NLTranslationNodeInfo>();

	public NLTranslationNodeInfo() {
	}

	public NLTranslationNodeInfo(String id, String booleanId, String operator) {
		this.id = id;
		this.booleanId = booleanId;
		this.operator = operator;
	}

	public String getId() {
		return id;
	}

	public String getBooleanExpression() {
		return (booleanExpr == null ? null : booleanExpr.replaceAll("\\*", "AND").replaceAll("\\+", "OR"));
	}

	public void setBooleanExpression(String booleanExpression) {
		this.booleanExpr = booleanExpression;
	}
	
	public String getOperator() {
		return operator;
	}

	public String getNLTranslation() {
		return nlTranslation;
	}
	
	public void setNLTranslation(String nlTranslation) {
		this.nlTranslation = nlTranslation;
	}
	
	public void addChildNode(NLTranslationNodeInfo child) {
		this.childNodeList.add(child);
	}
	
	public List<NLTranslationNodeInfo> getChildNodes() {
		return this.childNodeList;
	}
	
	public void setChildNodes(List<NLTranslationNodeInfo> children) {
		this.childNodeList = children;
	}

	public NLTranslationNodeInfo getParent() {
		return parentNode;
	}

	public void setParent(NLTranslationNodeInfo parent) {
		this.parentNode = parent;
	}

	public String getBooleanId() {
		return booleanId;
	}
	
	public String toString() {
		return "id="+this.id+", translation="+this.nlTranslation;
	}
}
