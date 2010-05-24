package org.kuali.student.core.dictionary.poc.dto;

import java.util.List;

public class Constraint extends BaseConstraint {
	public static final String UNBOUNDED = "unbounded";
	public static final String SINGLE = "1";
	// Constraints
	protected boolean serverSide;
	protected String locale; //What is locale for?
	protected String exclusiveMin;
	protected String inclusiveMax;
	protected Integer minLength;
	protected String maxLength;
	protected ValidCharsConstraint validChars;
	protected Integer minOccurs;
	protected String maxOccurs;
	protected List<RequiredConstraint> requireConstraint;
	protected List<CaseConstraint> caseConstraint;
	protected List<MustOccurConstraint> occursConstraint;

	// LookupConstraints
	protected LookupConstraint lookupDefinition;// If the user wants to match
												// against two searches, that
												// search must be defined as
												// well
	protected String lookupContextPath;// The idea here is to reuse a
										// lookupConstraint with fields relative
										// to the contextPath. We might not need
										// this
	public boolean isServerSide() {
		return serverSide;
	}
	public void setServerSide(boolean serverSide) {
		this.serverSide = serverSide;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getExclusiveMin() {
		return exclusiveMin;
	}
	public void setExclusiveMin(String exclusiveMin) {
		this.exclusiveMin = exclusiveMin;
	}
	public String getInclusiveMax() {
		return inclusiveMax;
	}
	public void setInclusiveMax(String inclusiveMax) {
		this.inclusiveMax = inclusiveMax;
	}
	public Integer getMinLength() {
		return minLength;
	}
	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}
	public String getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}
	public ValidCharsConstraint getValidChars() {
		return validChars;
	}
	public void setValidChars(ValidCharsConstraint validChars) {
		this.validChars = validChars;
	}
	public Integer getMinOccurs() {
		return minOccurs;
	}
	public void setMinOccurs(Integer minOccurs) {
		this.minOccurs = minOccurs;
	}
	public String getMaxOccurs() {
		return maxOccurs;
	}
	public void setMaxOccurs(String maxOccurs) {
		this.maxOccurs = maxOccurs;
	}
	public List<RequiredConstraint> getRequireConstraint() {
		return requireConstraint;
	}
	public void setRequireConstraint(List<RequiredConstraint> requireConstraint) {
		this.requireConstraint = requireConstraint;
	}
	public List<CaseConstraint> getCaseConstraint() {
		return caseConstraint;
	}
	public void setCaseConstraint(List<CaseConstraint> caseConstraint) {
		this.caseConstraint = caseConstraint;
	}
	public List<MustOccurConstraint> getOccursConstraint() {
		return occursConstraint;
	}
	public void setOccursConstraint(List<MustOccurConstraint> occursConstraint) {
		this.occursConstraint = occursConstraint;
	}
	public LookupConstraint getLookupDefinition() {
		return lookupDefinition;
	}
	public void setLookupDefinition(LookupConstraint lookupDefinition) {
		this.lookupDefinition = lookupDefinition;
	}
	public String getLookupContextPath() {
		return lookupContextPath;
	}
	public void setLookupContextPath(String lookupContextPath) {
		this.lookupContextPath = lookupContextPath;
	}
}
