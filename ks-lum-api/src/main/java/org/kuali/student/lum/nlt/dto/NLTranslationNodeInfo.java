package org.kuali.student.lum.nlt.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NLTranslationNodeInfo {
    @XmlAttribute
	private String id;

    @XmlAttribute
    private String referenceId;

    @XmlAttribute
    private String operator;

	@XmlElement
	private String booleanExpression;

    @XmlElement
	private String nlTranslation;

    @XmlElement
    private NLTranslationNodeInfo parentNode;

	@XmlElement
    private List<NLTranslationNodeInfo> childNodeList = new ArrayList<NLTranslationNodeInfo>();

	public NLTranslationNodeInfo() {
	}

	public NLTranslationNodeInfo(String id, String referenceId, String operator) {
		this.id = id;
		this.referenceId = referenceId;
		this.operator = operator;
	}

	public String getId() {
		return this.id;
	}

	public String getProperBooleanExpression() {
		return (this.booleanExpression == null ? null : this.booleanExpression.replaceAll("\\*", "and").replaceAll("\\+", "or"));
	}

	public String getBooleanExpression() {
		return this.booleanExpression;
	}

	public void setBooleanExpression(String booleanExpression) {
		this.booleanExpression = booleanExpression;
	}
	
	public String getOperator() {
		return this.operator;
	}

	public String getNLTranslation() {
		return this.nlTranslation;
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

	public NLTranslationNodeInfo getParentNode() {
		return this.parentNode;
	}

	public void setParent(NLTranslationNodeInfo parentNode) {
		this.parentNode = parentNode;
	}

	public String getReferenceId() {
		return this.referenceId;
	}
	
	public String toString() {
		return "[id="+this.id+", referenceId="+this.referenceId+", translation="+this.nlTranslation+"]";
	}
}
