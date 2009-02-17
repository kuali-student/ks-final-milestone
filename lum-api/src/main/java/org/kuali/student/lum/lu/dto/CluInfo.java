package org.kuali.student.lum.lu.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichText;
import org.kuali.student.core.entity.TimeAmount;

@XmlAccessorType(XmlAccessType.FIELD)
public class CluInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private CluIdentifier officialIdentifier;
	
	@XmlElement
	private List<CluIdentifier> alternateIdentifiers;

	@XmlElement
	private String studySubjectArea;

	@XmlElement
	private RichText desc;

	@XmlElement
	private RichText marketingDesc;

	@XmlElement
	private String accreditingOrg;

	@XmlElement
	private String adminOrg;

	@XmlElement
	private List<String> participatingOrgs;

	@XmlElement
	private CluInstructor primaryInstructor;

	@XmlElement
	private List<CluInstructor> instructors;

	@XmlElement
	private Date effectiveDate;

	@XmlElement
	private Date expirationDate;

	@XmlElement
	private TimeAmount stdDuration;

	@XmlElement
	private boolean canCreateLui;

	@XmlElement
	private String referenceURL;

	@XmlElement
	private List<LuCodeInfo> luCodes;

	@XmlElement
	private CluCredit creditInfo;

	@XmlElement
	private CluPublishing publishingInfo;

	@XmlElement
	private String nextReviewPeriod;

	@XmlElement
	private boolean isEnrollable;

	@XmlElement
	private List<String> offeredAtpTypes;

	@XmlElement
	private boolean hasEarlyDropDeadline;

	@XmlElement
	private int defaultEnrollmentEstimate;

	@XmlElement
	private int defaultMaximumEnrollment;

	@XmlElement
	private boolean isHazardousForDisabledStudents;

	@XmlElement
	private CluFee feeInfo; 

	@XmlElement
	private CluAccounting accountingInfo;

	@XmlElement
	private Map<String, String> attributes;

	@XmlElement
	private MetaInfo metaInfo;

	@XmlAttribute
	private String type;

	@XmlAttribute
	private String state;

	@XmlAttribute
	private String id;

	public Map<String, String> getAttributes() {
		if (attributes == null) {
			attributes = new HashMap<String, String>();
		}
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public CluIdentifier getOfficialIdentifier() {
		return officialIdentifier;
	}

	public void setOfficialIdentifier(CluIdentifier officialIdentifier) {
		this.officialIdentifier = officialIdentifier;
	}

	public List<CluIdentifier> getAlternateIdentifiers() {
		if (alternateIdentifiers == null) {
			alternateIdentifiers = new ArrayList<CluIdentifier>();
		}
		return alternateIdentifiers;
	}

	public void setAlternateIdentifiers(List<CluIdentifier> alternateIdentifiers) {
		this.alternateIdentifiers = alternateIdentifiers;
	}

	public String getStudySubjectArea() {
		return studySubjectArea;
	}

	public void setStudySubjectArea(String studySubjectArea) {
		this.studySubjectArea = studySubjectArea;
	}

	public RichText getDesc() {
		return desc;
	}

	public void setDesc(RichText desc) {
		this.desc = desc;
	}

	public RichText getMarketingDesc() {
		return marketingDesc;
	}

	public void setMarketingDesc(RichText marketingDesc) {
		this.marketingDesc = marketingDesc;
	}

	public String getAccreditingOrg() {
		return accreditingOrg;
	}

	public void setAccreditingOrg(String accreditingOrg) {
		this.accreditingOrg = accreditingOrg;
	}

	public String getAdminOrg() {
		return adminOrg;
	}

	public void setAdminOrg(String adminOrg) {
		this.adminOrg = adminOrg;
	}

	public List<String> getParticipatingOrgs() {
		if (participatingOrgs == null) {
			participatingOrgs = new ArrayList<String>();
		}
		return participatingOrgs;
	}

	public void setParticipatingOrgs(List<String> participatingOrgs) {
		this.participatingOrgs = participatingOrgs;
	}

	public CluInstructor getPrimaryInstructor() {
		return primaryInstructor;
	}

	public void setPrimaryInstructor(CluInstructor primaryInstructor) {
		this.primaryInstructor = primaryInstructor;
	}

	public List<CluInstructor> getInstructors() {
		if (instructors == null) {
			instructors = new ArrayList<CluInstructor>();
		}
		return instructors;
	}

	public void setInstructors(List<CluInstructor> instructors) {
		this.instructors = instructors;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public TimeAmount getStdDuration() {
		return stdDuration;
	}

	public void setStdDuration(TimeAmount stdDuration) {
		this.stdDuration = stdDuration;
	}

	public boolean isCanCreateLui() {
		return canCreateLui;
	}

	public void setCanCreateLui(boolean canCreateLui) {
		this.canCreateLui = canCreateLui;
	}

	public String getReferenceURL() {
		return referenceURL;
	}

	public void setReferenceURL(String referenceURL) {
		this.referenceURL = referenceURL;
	}

	public List<LuCodeInfo> getLuCodes() {
		if (luCodes == null) {
			luCodes = new ArrayList<LuCodeInfo>();
		}
		return luCodes;
	}

	public void setLuCodes(List<LuCodeInfo> luCodes) {
		this.luCodes = luCodes;
	}

	public CluCredit getCreditInfo() {
		return creditInfo;
	}

	public void setCreditInfo(CluCredit creditInfo) {
		this.creditInfo = creditInfo;
	}

	public CluPublishing getPublishingInfo() {
		return publishingInfo;
	}

	public void setPublishingInfo(CluPublishing publishingInfo) {
		this.publishingInfo = publishingInfo;
	}

	public String getNextReviewPeriod() {
		return nextReviewPeriod;
	}

	public void setNextReviewPeriod(String nextReviewPeriod) {
		this.nextReviewPeriod = nextReviewPeriod;
	}

	public boolean isEnrollable() {
		return isEnrollable;
	}

	public void setEnrollable(boolean isEnrollable) {
		this.isEnrollable = isEnrollable;
	}

	public List<String> getOfferedAtpTypes() {
		if (offeredAtpTypes == null) {
			offeredAtpTypes = new ArrayList<String>();
		}
		return offeredAtpTypes;
	}

	public void setOfferedAtpTypes(List<String> offeredAtpTypes) {
		this.offeredAtpTypes = offeredAtpTypes;
	}

	public boolean isHasEarlyDropDeadline() {
		return hasEarlyDropDeadline;
	}

	public void setHasEarlyDropDeadline(boolean hasEarlyDropDeadline) {
		this.hasEarlyDropDeadline = hasEarlyDropDeadline;
	}

	public int getDefaultEnrollmentEstimate() {
		return defaultEnrollmentEstimate;
	}

	public void setDefaultEnrollmentEstimate(int defaultEnrollmentEstimate) {
		this.defaultEnrollmentEstimate = defaultEnrollmentEstimate;
	}

	public int getDefaultMaximumEnrollment() {
		return defaultMaximumEnrollment;
	}

	public void setDefaultMaximumEnrollment(int defaultMaximumEnrollment) {
		this.defaultMaximumEnrollment = defaultMaximumEnrollment;
	}

	public boolean isHazardousForDisabledStudents() {
		return isHazardousForDisabledStudents;
	}

	public void setHazardousForDisabledStudents(
			boolean isHazardousForDisabledStudents) {
		this.isHazardousForDisabledStudents = isHazardousForDisabledStudents;
	}

	public CluFee getFeeInfo() {
		return feeInfo;
	}

	public void setFeeInfo(CluFee feeInfo) {
		this.feeInfo = feeInfo;
	}

	public CluAccounting getAccountingInfo() {
		return accountingInfo;
	}

	public void setAccountingInfo(CluAccounting accountingInfo) {
		this.accountingInfo = accountingInfo;
	}

	public MetaInfo getMetaInfo() {
		return metaInfo;
	}

	public void setMetaInfo(MetaInfo metaInfo) {
		this.metaInfo = metaInfo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}