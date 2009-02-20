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
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.atp.dto.TimeAmountInfo;
import org.kuali.student.core.dto.HasAttributes;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
public class CluInfo implements Serializable, Idable, HasAttributes {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private CluIdentifierInfo officialIdentifier;
	
	@XmlElement
	private List<CluIdentifierInfo> alternateIdentifiers;

	@XmlElement
	private String studySubjectArea;

	@XmlElement
	private RichTextInfo desc;

	@XmlElement
	private RichTextInfo marketingDesc;

	@XmlElement
	private String accreditingOrg;

	@XmlElement
	private String adminOrg;

	@XmlElement
	private List<String> participatingOrgs;

	@XmlElement
	private CluInstructorInfo primaryInstructor;

	@XmlElement
	private List<CluInstructorInfo> instructors;

	@XmlElement
	private Date effectiveDate;

	@XmlElement
	private Date expirationDate;

	@XmlElement
	private TimeAmountInfo stdDuration;

	@XmlElement
	private boolean canCreateLui;

	@XmlElement
	private String referenceURL;

	@XmlElement
	private List<LuCodeInfo> luCodes;

	@XmlElement
	private CluCreditInfo creditInfo;

	@XmlElement
	private CluPublishingInfo publishingInfo;

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
	private CluFeeInfo feeInfo; 

	@XmlElement
	private CluAccountingInfo accountingInfo;

	@XmlElement
	@XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
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

	public CluIdentifierInfo getOfficialIdentifier() {
		return officialIdentifier;
	}

	public void setOfficialIdentifier(CluIdentifierInfo officialIdentifier) {
		this.officialIdentifier = officialIdentifier;
	}

	public List<CluIdentifierInfo> getAlternateIdentifiers() {
		if (alternateIdentifiers == null) {
			alternateIdentifiers = new ArrayList<CluIdentifierInfo>();
		}
		return alternateIdentifiers;
	}

	public void setAlternateIdentifiers(List<CluIdentifierInfo> alternateIdentifiers) {
		this.alternateIdentifiers = alternateIdentifiers;
	}

	public String getStudySubjectArea() {
		return studySubjectArea;
	}

	public void setStudySubjectArea(String studySubjectArea) {
		this.studySubjectArea = studySubjectArea;
	}

	public RichTextInfo getDesc() {
		return desc;
	}

	public void setDesc(RichTextInfo desc) {
		this.desc = desc;
	}

	public RichTextInfo getMarketingDesc() {
		return marketingDesc;
	}

	public void setMarketingDesc(RichTextInfo marketingDesc) {
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

	public CluInstructorInfo getPrimaryInstructor() {
		return primaryInstructor;
	}

	public void setPrimaryInstructor(CluInstructorInfo primaryInstructor) {
		this.primaryInstructor = primaryInstructor;
	}

	public List<CluInstructorInfo> getInstructors() {
		if (instructors == null) {
			instructors = new ArrayList<CluInstructorInfo>();
		}
		return instructors;
	}

	public void setInstructors(List<CluInstructorInfo> instructors) {
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

	public TimeAmountInfo getStdDuration() {
		return stdDuration;
	}

	public void setStdDuration(TimeAmountInfo stdDuration) {
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

	public CluCreditInfo getCreditInfo() {
		return creditInfo;
	}

	public void setCreditInfo(CluCreditInfo creditInfo) {
		this.creditInfo = creditInfo;
	}

	public CluPublishingInfo getPublishingInfo() {
		return publishingInfo;
	}

	public void setPublishingInfo(CluPublishingInfo publishingInfo) {
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

	public CluFeeInfo getFeeInfo() {
		return feeInfo;
	}

	public void setFeeInfo(CluFeeInfo feeInfo) {
		this.feeInfo = feeInfo;
	}

	public CluAccountingInfo getAccountingInfo() {
		return accountingInfo;
	}

	public void setAccountingInfo(CluAccountingInfo accountingInfo) {
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