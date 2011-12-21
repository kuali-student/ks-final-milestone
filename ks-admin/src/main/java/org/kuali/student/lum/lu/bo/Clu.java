package org.kuali.student.lum.lu.bo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.kuali.rice.kns.bo.InactivateableFromTo;
import org.kuali.student.core.bo.KsVersionBusinessObjectBase;
import org.kuali.student.core.bo.util.InactivatableFromToHelper;

public class Clu extends KsVersionBusinessObjectBase implements InactivateableFromTo {

	private static final long serialVersionUID = -403153977773213333L;
	
	private String officialCluId;
	
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "OFFIC_CLU_ID")
    private CluIdentifier officialIdentifier;
    
    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "KSLU_CLU_JN_CLU_IDENT", joinColumns = @JoinColumn(name = "CLU_ID"), inverseJoinColumns = @JoinColumn(name = "ALT_CLU_ID"))
    private List<CluIdentifier> alternateIdentifiers;

    @Column(name = "STDY_SUBJ_AREA")
    private String studySubjectArea;
    
    private String descriptionId;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private LuRichText description;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "clu")
    private List<CluCampusLocation> campusLocations;
    
    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "KSLU_CLU_JN_ACCRED", joinColumns = @JoinColumn(name = "CLU_ID"), inverseJoinColumns = @JoinColumn(name = "CLU_ACCRED_ID"))
    private List<CluAccreditation> accreditations;
    
    private List<CluAdminOrg> adminOrgs;
    
    private String primaryInstructorId;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="PRI_INSTR_ID")
    private CluInstructor primaryInstructor;
    
    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "KSLU_CLU_JN_CLU_INSTR", joinColumns = @JoinColumn(name = "CLU_ID"), inverseJoinColumns = @JoinColumn(name = "CLU_INSTR_ID"))
    private List<CluInstructor> instructors;
        
    @Column(name = "EXP_FIRST_ATP")
    private String expectedFirstAtp;

    @Column(name = "LAST_ATP")
    private String lastAtp;

    @Column(name = "LAST_ADMIT_ATP")
    private String lastAdmitAtp;
    
    // from clu embedded Amount, intensity.unitType
    private String intensityTypeId;
    
    // from clu embedded Amount, intensity.unitQuanitity
    private Integer intensityQuantity;
    
    // from clu embedded TimeAmout, stdDuration.atpDurationTypeKey
    private String atpDurationTypeId;
    
    // from clu embedded TimeAmout, stdDuration.timeQuantity
    private Integer timeQuantity;
    
    @Column(name = "CAN_CREATE_LUI")
    private boolean canCreateLui;

    @Column(name = "REF_URL")
    private String referenceURL;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="clu")
    private List<LuCode> luCodes;
        
    @Column(name = "NEXT_REVIEW_PRD")
    private String nextReviewPeriod;

    @Column(name = "IS_ENRL")
    private boolean enrollable;
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="clu")
    private List<CluAtpTypeKey> offeredAtpTypes;
    
    @Column(name = "HAS_EARLY_DROP_DEDLN")
    private boolean hasEarlyDropDeadline;

    @Column(name = "DEF_ENRL_EST")
    private int defaultEnrollmentEstimate;

    @Column(name = "DEF_MAX_ENRL")
    private int defaultMaximumEnrollment;

    @Column(name = "IS_HAZR_DISBLD_STU")
    private boolean hazardousForDisabledStudents;

    private String feeId;
    
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "FEE_ID")
    private CluFee fee;
    
    private String cluAccountingId;
    
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "ACCT_ID")
    private CluAccounting accounting;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<CluAttribute> attributes;

    private String typeId;
    
    @ManyToOne
    @JoinColumn(name = "LUTYPE_ID")
    private LuType luType;
    
    @Column(name = "ST")
    private String state;
    
    @Column(name = "EFF_DT")
    protected Timestamp activeFromDate;
    
    @Column(name = "EXPIR_DT")
    protected Timestamp activeToDate;
    
    @Transient
    protected Timestamp activeAsOfDate;
    
    //-------- not in ks --------
    private List<CluCluRelation> cluRelations;

    
    //-------- getters/setters with behavior -----
    public boolean isActive() {
        return InactivatableFromToHelper.isActive(this);
    }
    
    public void setActive(boolean active) {
        // do nothing, this is to comply wiht InactivatableFromTo interface
    }
    
    public CluAccounting getAccounting() {
        // getting errors if this is null
        if(accounting == null) {
            accounting = new CluAccounting();
        }
        return accounting;
    }

    public void setAccounting(CluAccounting accounting) {
        this.accounting = accounting;
    }
    
    //--------- standard getters/setters ----------
	public String getOfficialCluId() {
		return officialCluId;
	}

	public void setOfficialCluId(String officialCluId) {
		this.officialCluId = officialCluId;
	}

	public CluIdentifier getOfficialIdentifier() {
		return officialIdentifier;
	}

	public void setOfficialIdentifier(CluIdentifier officialIdentifier) {
		this.officialIdentifier = officialIdentifier;
	}

	public List<CluIdentifier> getAlternateIdentifiers() {
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

	public String getDescriptionId() {
		return descriptionId;
	}

	public void setDescriptionId(String descriptionId) {
		this.descriptionId = descriptionId;
	}

	public LuRichText getDescription() {
		return description;
	}

	public void setDescription(LuRichText description) {
		this.description = description;
	}

	public List<CluCampusLocation> getCampusLocations() {
		return campusLocations;
	}

	public void setCampusLocations(List<CluCampusLocation> campusLocations) {
		this.campusLocations = campusLocations;
	}

	public List<CluAccreditation> getAccreditations() {
		return accreditations;
	}

	public void setAccreditations(List<CluAccreditation> accreditations) {
		this.accreditations = accreditations;
	}

	public List<CluAdminOrg> getAdminOrgs() {
		return adminOrgs;
	}

	public void setAdminOrgs(List<CluAdminOrg> adminOrgs) {
		this.adminOrgs = adminOrgs;
	}

	public String getPrimaryInstructorId() {
		return primaryInstructorId;
	}

	public void setPrimaryInstructorId(String primaryInstructorId) {
		this.primaryInstructorId = primaryInstructorId;
	}

	public CluInstructor getPrimaryInstructor() {
		return primaryInstructor;
	}

	public void setPrimaryInstructor(CluInstructor primaryInstructor) {
		this.primaryInstructor = primaryInstructor;
	}

	public List<CluInstructor> getInstructors() {
		return instructors;
	}

	public void setInstructors(List<CluInstructor> instructors) {
		this.instructors = instructors;
	}

	public String getExpectedFirstAtp() {
		return expectedFirstAtp;
	}

	public void setExpectedFirstAtp(String expectedFirstAtp) {
		this.expectedFirstAtp = expectedFirstAtp;
	}

	public String getLastAtp() {
		return lastAtp;
	}

	public void setLastAtp(String lastAtp) {
		this.lastAtp = lastAtp;
	}

	public String getLastAdmitAtp() {
		return lastAdmitAtp;
	}

	public void setLastAdmitAtp(String lastAdmitAtp) {
		this.lastAdmitAtp = lastAdmitAtp;
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

	public String getNextReviewPeriod() {
		return nextReviewPeriod;
	}

	public void setNextReviewPeriod(String nextReviewPeriod) {
		this.nextReviewPeriod = nextReviewPeriod;
	}

	public boolean isEnrollable() {
		return enrollable;
	}

	public void setEnrollable(boolean enrollable) {
		this.enrollable = enrollable;
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
		return hazardousForDisabledStudents;
	}

	public void setHazardousForDisabledStudents(boolean hazardousForDisabledStudents) {
		this.hazardousForDisabledStudents = hazardousForDisabledStudents;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public LuType getLuType() {
		return luType;
	}

	public void setLuType(LuType luType) {
		this.luType = luType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

    public String getCluAccountingId() {
        return cluAccountingId;
    }

    public void setCluAccountingId(String cluAccountingId) {
        this.cluAccountingId = cluAccountingId;
    }

    public String getFeeId() {
        return feeId;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }

    public CluFee getFee() {
        if(fee == null) {
            fee = new CluFee();
        }
        return fee;
    }

    public void setFee(CluFee fee) {
        this.fee = fee;
    }

    public String getIntensityTypeId() {
        return intensityTypeId;
    }

    public void setIntensityTypeId(String intensityTypeId) {
        this.intensityTypeId = intensityTypeId;
    }

    public Integer getIntensityQuantity() {
        return intensityQuantity;
    }

    public void setIntensityQuantity(Integer intensityQuantity) {
        this.intensityQuantity = intensityQuantity;
    }

    public String getAtpDurationTypeId() {
        return atpDurationTypeId;
    }

    public void setAtpDurationTypeId(String atpDurationTypeId) {
        this.atpDurationTypeId = atpDurationTypeId;
    }

    public Integer getTimeQuantity() {
        return timeQuantity;
    }

    public void setTimeQuantity(Integer timeQuantity) {
        this.timeQuantity = timeQuantity;
    }

    public List<LuCode> getLuCodes() {
        return luCodes;
    }

    public void setLuCodes(List<LuCode> luCodes) {
        this.luCodes = luCodes;
    }

    public List<CluAtpTypeKey> getOfferedAtpTypes() {
        return offeredAtpTypes;
    }

    public void setOfferedAtpTypes(List<CluAtpTypeKey> offeredAtpTypes) {
        this.offeredAtpTypes = offeredAtpTypes;
    }

    public List<CluCluRelation> getCluRelations() {
        return cluRelations;
    }

    public void setCluRelations(List<CluCluRelation> cluRelations) {
        this.cluRelations = cluRelations;
    }

    public Timestamp getActiveFromDate() {
        return activeFromDate;
    }

    public void setActiveFromDate(Timestamp activeFromDate) {
        this.activeFromDate = activeFromDate;
    }

    public Timestamp getActiveToDate() {
        return activeToDate;
    }

    public void setActiveToDate(Timestamp activeToDate) {
        this.activeToDate = activeToDate;
    }

    public Timestamp getActiveAsOfDate() {
        return activeAsOfDate;
    }

    public void setActiveAsOfDate(Timestamp activeAsOfDate) {
        this.activeAsOfDate = activeAsOfDate;
    }

    public List<CluAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<CluAttribute> attributes) {
        this.attributes = attributes;
    }

}
