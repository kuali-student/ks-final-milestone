package org.kuali.student.lum.lu.naturallanguage;

import java.util.List;

public class NLTranslationNode {
	private String id;
	private Boolean isRootNode = Boolean.FALSE;
	private String booleanExpression;
	private String nlTranslation;
	private NLTranslationNode parent;
	private List<NLTranslationNode> children;

	public NLTranslationNode(String id, String nlTranslation) {
		this.id = id;
		this.nlTranslation = nlTranslation;
	}

	public NLTranslationNode(Boolean isRootNode, String booleanExpression) {
		this.isRootNode = isRootNode;
		this.booleanExpression = booleanExpression;
	}

	public Boolean isRootNode() {
		return this.isRootNode;
	}

	public String getId() {
		return id;
	}

	public String getBooleanExpression() {
		return booleanExpression;
	}

	public String getNLTranslation() {
		return nlTranslation;
	}
	
	public void addChildNode(NLTranslationNode child) {
		this.children.add(child);
	}
	
	public List<NLTranslationNode> getChildNodes() {
		return this.children;
	}
	
	public void setChildNodes(List<NLTranslationNode> children) {
		this.children = children;
	}

	public NLTranslationNode getParent() {
		return parent;
	}

	public void setParent(NLTranslationNode parent) {
		this.parent = parent;
	}
}
