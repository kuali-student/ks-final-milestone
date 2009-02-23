package org.kuali.student.lum.lu.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;
import org.kuali.student.core.entity.RichText;
import org.kuali.student.core.entity.TimeAmount;

@Entity
@Table(name = "KS_CLU_T")
public class Clu extends MetaEntity implements AttributeOwner<CluAttribute> {
	@Id
	@Column(name = "ID")
	private String id;

    @ManyToOne
    @JoinColumn(name="LU_TYPE_ID")
    private LuType luType;
    
    @OneToMany(mappedBy="clu")
    private List<LearningObjective> learningObjectives;

    @OneToMany(mappedBy="clu")
    private List<ResourceType> resourceTypes;
    
    @ManyToMany(mappedBy="clus")
    List<CluSet> cluSets;
    
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "OWNER")
    private List<CluAttribute> attributes;
    
    @OneToOne
    @JoinColumn(name="OFF_CLU_IDFY_ID")
    private CluIdentifier officialIdentifier;
    
    @OneToMany
    @JoinTable(name="KS_ALT_CLU_IDFY_JOIN_T",joinColumns=@JoinColumn(name="ALT_CLU_IDFY_ID"), inverseJoinColumns=@JoinColumn(name="CLU_ID"))
    private List<CluIdentifier> alternateIdentifiers;
    
	@Column(name = "STUDY_SUBJECT_AREA")
    private String studySubjectArea;
	
	@ManyToOne
	@JoinColumn(name = "CLU_DESC")
	private RichText desc;
	
	@ManyToOne
	@JoinColumn(name = "MARKETING_DESC")
    private RichText marketingDesc;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name = "CLU_ID")
	private List<CluOrg> orgs;

	@OneToMany
	@JoinTable(name = "KS_CLU_CLU_INSTRUCTOR_T", joinColumns = @JoinColumn(name = "CLU_ID"), inverseJoinColumns = @JoinColumn(name = "CLU_INSTRUCTOR_ID"))
    private List<CluInstructor> instructors;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFFECTIVE_DT")
    private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIRATION_DT")
    private Date expirationDate;

	@Embedded
	@Column(name = "STD_DURATION")
	private TimeAmount stdDuration;

	@Column(name = "CAN_CREATE_LUI")
    private boolean canCreateLui;

	@Column(name = "REF_URL")
    private String referenceURL;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "CLU_ID")
    private List<LuCode> luCodes;

    @OneToOne
    private CluCredit credit;

	@OneToOne
    private CluPublishing publishing;

	@Column(name = "NEXT_REVIEW_PERIOD")
    private String nextReviewPeriod;

	@Column(name = "IS_ENROLLABLE")
    private boolean isEnrollable;

	@OneToMany
	@JoinColumn(name = "CLU_ID")
    private List<CluAtpTypeKey> offeredAtpTypes;

	@Column(name = "HAS_EARLY_DROP_DEADLINE")
    private boolean hasEarlyDropDeadline;

	@Column(name = "DEFAULT_ENROLLMENT_ESTIMATE")
    private int defaultEnrollmentEstimate;

	@Column(name = "DEFAULT_MAX_ENROLLMENT")
    private int defaultMaximumEnrollment;

	@Column(name = "IS_HAZARDAOUS_DISABLED_STUDENTS")
    private boolean isHazardousForDisabledStudents;

	@OneToOne
    private CluFee fee;

	@OneToOne
    private CluAccounting accounting;

	@Column(name = "CLU_TYPE")
    private String type;

	@Column(name = "CLU_STATE")
    private String state;
    

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public LuType getLuType() {
        return luType;
    }

    public void setLuType(LuType luType) {
        this.luType = luType;
    }
    
    public List<LearningObjective> getLearningObjectives() {
        return learningObjectives;
    }

    public void setLearningObjectives(List<LearningObjective> learningObjectives) {
        this.learningObjectives = learningObjectives;
    }

    public List<ResourceType> getResourceTypes() {
        return resourceTypes;
    }

    public void setResourceTypes(List<ResourceType> resourceTypes) {
        this.resourceTypes = resourceTypes;
    }    
    
    @Override
    public List<CluAttribute> getAttributes() {
        if (attributes == null) {
            attributes = new ArrayList<CluAttribute>();
        }
        return attributes;
    }

    @Override
    public void setAttributes(List<CluAttribute> attributes) {
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
    
    public List<CluOrg> getOrgs() {
        if (orgs == null) {
            orgs = new ArrayList<CluOrg>();
        }
        return orgs;
    }

    public void setOrgs(List<CluOrg> orgs) {
        this.orgs = orgs;
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

    public List<LuCode> getLuCodes() {
        if (luCodes == null) {
            luCodes = new ArrayList<LuCode>();
        }
        return luCodes;
    }

    public void setLuCodes(List<LuCode> luCodes) {
        this.luCodes = luCodes;
    }
    
    public CluCredit getCredit() {
        return credit;
    }

    public void setCredit(CluCredit credit) {
        this.credit = credit;
    }

    public CluPublishing getPublishing() {
        return publishing;
    }

    public void setPublishing(CluPublishing publishing) {
        this.publishing = publishing;
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

    public List<CluAtpTypeKey> getOfferedAtpTypes() {
        if (offeredAtpTypes == null) {
            offeredAtpTypes = new ArrayList<CluAtpTypeKey>();
        }
        return offeredAtpTypes;
    }

    public void setOfferedAtpTypes(List<CluAtpTypeKey> offeredAtpTypes) {
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

    public CluFee getFee() {
        return fee;
    }

    public void setFee(CluFee fee) {
        this.fee = fee;
    }

    public CluAccounting getAccounting() {
        return accounting;
    }

    public void setAccounting(CluAccounting accounting) {
        this.accounting = accounting;
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
}
