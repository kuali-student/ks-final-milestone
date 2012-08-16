/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.lum.lu.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.kuali.student.r1.common.entity.Amount;
import org.kuali.student.r1.common.entity.AttributeOwner;
import org.kuali.student.r1.common.entity.TimeAmount;
import org.kuali.student.r1.common.entity.VersionEntity;

@Entity
@Table(name = "KSLU_CLU", uniqueConstraints={@UniqueConstraint(columnNames={"VER_IND_ID", "SEQ_NUM"})} )
@NamedQueries( {
	//FIXME dates should be either set from the DB time as part of the insert statement, or set from the application.
	//DB timestamp (CURRENT_TIMESTAMP) is preferred
    @NamedQuery(name = "Clu.findCurrentVersionInfo", query = "SELECT " +
    		"NEW org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo(c.id, c.version.versionIndId, c.version.sequenceNumber, c.version.currentVersionStart, c.version.currentVersionEnd, c.version.versionComment, c.version.versionedFromId) " +
    		"FROM Clu c " +
    		"WHERE c.version.versionIndId = :versionIndId " +
    		"AND c.version.currentVersionStart <= :currentTime AND (c.version.currentVersionEnd > :currentTime OR c.version.currentVersionEnd IS NULL)"),
	@NamedQuery(name = "Clu.findCurrentVersionOnDate", query = "SELECT " +
    		"NEW org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo(c.id, c.version.versionIndId, c.version.sequenceNumber, c.version.currentVersionStart, c.version.currentVersionEnd, c.version.versionComment, c.version.versionedFromId) " +
    		"FROM Clu c " +
    		"WHERE c.version.versionIndId = :versionIndId " +
    		"AND c.version.currentVersionStart <= :date AND (c.version.currentVersionEnd > :date OR c.version.currentVersionEnd IS NULL)"),
	@NamedQuery(name = "Clu.findFirstVersion", query = "SELECT " +
    		"NEW org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo(c.id, c.version.versionIndId, c.version.sequenceNumber, c.version.currentVersionStart, c.version.currentVersionEnd, c.version.versionComment, c.version.versionedFromId) " +
    		"FROM Clu c " +
    		"WHERE c.version.versionIndId = :versionIndId " +
    		"AND c.version.sequenceNumber IN (SELECT MIN(nc.version.sequenceNumber) FROM Clu nc WHERE nc.version.versionIndId = :versionIndId)"),
    @NamedQuery(name = "Clu.findLatestVersion", query = "SELECT " +
 	    	"NEW org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo(c.id, c.version.versionIndId, c.version.sequenceNumber, c.version.currentVersionStart, c.version.currentVersionEnd, c.version.versionComment, c.version.versionedFromId) " +
    	    "FROM Clu c " +
    	    "WHERE c.version.versionIndId = :versionIndId " +
    	    "AND c.version.sequenceNumber IN (SELECT MAX(nc.version.sequenceNumber) FROM Clu nc WHERE nc.version.versionIndId = :versionIndId)"),
    @NamedQuery(name = "Clu.findVersionBySequence", query = "SELECT " +
    		"NEW org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo(c.id, c.version.versionIndId, c.version.sequenceNumber, c.version.currentVersionStart, c.version.currentVersionEnd, c.version.versionComment, c.version.versionedFromId) " +
    		"FROM Clu c " +
    		"WHERE c.version.versionIndId = :versionIndId " +
    		"AND c.version.sequenceNumber = :sequenceNumber"),
	@NamedQuery(name = "Clu.findVersions", query = "SELECT " +
    		"NEW org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo(c.id, c.version.versionIndId, c.version.sequenceNumber, c.version.currentVersionStart, c.version.currentVersionEnd, c.version.versionComment, c.version.versionedFromId) " +
    		"FROM Clu c " +
    		"WHERE c.version.versionIndId = :versionIndId"),
	@NamedQuery(name = "Clu.findVersionsInDateRange", query = "SELECT " +
    		"NEW org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo(c.id, c.version.versionIndId, c.version.sequenceNumber, c.version.currentVersionStart, c.version.currentVersionEnd, c.version.versionComment, c.version.versionedFromId) " +
    		"FROM Clu c " +
    		"WHERE c.version.versionIndId = :versionIndId " +
    		"AND ( (c.version.currentVersionStart >= :from AND c.version.currentVersionStart < :to)" +
    		"   OR (c.version.currentVersionStart < :from AND c.version.currentVersionEnd > :from) )"),
	@NamedQuery(name = "Clu.findVersionsBeforeDate", query = "SELECT " +
    		"NEW org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo(c.id, c.version.versionIndId, c.version.sequenceNumber, c.version.currentVersionStart, c.version.currentVersionEnd, c.version.versionComment, c.version.versionedFromId) " +
    		"FROM Clu c " +
    		"WHERE c.version.versionIndId = :versionIndId " +
    		"AND c.version.currentVersionStart <= :date"),
	@NamedQuery(name = "Clu.findVersionsAfterDate", query = "SELECT " +
    		"NEW org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo(c.id, c.version.versionIndId, c.version.sequenceNumber, c.version.currentVersionStart, c.version.currentVersionEnd, c.version.versionComment, c.version.versionedFromId) " +
    		"FROM Clu c " +
    		"WHERE c.version.versionIndId = :versionIndId " +
    		"AND c.version.currentVersionStart >= :date"),
    @NamedQuery(name = "Clu.findLatestClu", query = "SELECT c FROM Clu c WHERE c.version.versionIndId = :versionIndId AND c.version.sequenceNumber IN (SELECT MAX(nc.version.sequenceNumber) FROM Clu nc WHERE nc.version.versionIndId = :versionIndId)"),
    @NamedQuery(name = "Clu.findCurrentClu", query = "SELECT c FROM Clu c WHERE c.version.versionIndId = :versionIndId AND c.version.currentVersionStart <= :currentTime AND (c.version.currentVersionEnd > :currentTime OR c.version.currentVersionEnd IS NULL)"),
    @NamedQuery(name = "Clu.findClusByIdList", query = "SELECT c FROM Clu c WHERE c.id IN (:idList)"),
    @NamedQuery(name = "Clu.getClusByLuType", query = "SELECT c FROM Clu c WHERE c.state = :luState AND c.luType.id = :luTypeKey"),
    @NamedQuery(name = "Clu.getClusByRelation", query = "SELECT c FROM Clu c WHERE c.id IN (SELECT ccr.relatedClu.id FROM CluCluRelation ccr WHERE ccr.clu.id = :parentCluId AND ccr.luLuRelationType.id = :luLuRelationTypeKey)"),
    @NamedQuery(name = "Clu.getCrossListedClusByCodes", query = "SELECT c FROM Clu c WHERE c.state='Active' AND c.officialIdentifier.code IN (:crossListedCodes) ")
   
})
public class Clu extends VersionEntity implements AttributeOwner<CluAttribute> {

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "OFFIC_CLU_ID")
    private CluIdentifier officialIdentifier;
    
    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "KSLU_CLU_JN_CLU_IDENT", joinColumns = @JoinColumn(name = "CLU_ID"), inverseJoinColumns = @JoinColumn(name = "ALT_CLU_ID"))
    private List<CluIdentifier> alternateIdentifiers;

    @Column(name = "STDY_SUBJ_AREA")
    private String studySubjectArea;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private LuRichText descr;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "clu")
    private List<CluCampusLocation> campusLocations;
    
    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "KSLU_CLU_JN_ACCRED", joinColumns = @JoinColumn(name = "CLU_ID"), inverseJoinColumns = @JoinColumn(name = "CLU_ACCRED_ID"))
    private List<CluAccreditation> accreditations;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clu")
    private List<CluAdminOrg> adminOrgs;
    
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
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="unitType", column=@Column(name="CLU_INTSTY_TYPE")),
        @AttributeOverride(name="unitQuantity", column=@Column(name="CLU_INTSTY_QTY")
        )})
     private Amount intensity;

    @Embedded
    @Column(name = "STD_DUR")
    private TimeAmount stdDuration;
    
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

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "FEE_ID")
    private CluFee fee;
    
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "ACCT_ID")
    private CluAccounting accounting;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<CluAttribute> attributes;

    @ManyToOne
    @JoinColumn(name = "LUTYPE_ID")
    private LuType luType;
    
    @Column(name = "ST")
    private String state;
    
    public LuType getLuType() {
        return luType;
    }

    public void setLuType(LuType luType) {
        this.luType = luType;
    }

    @Override
    public List<CluAttribute> getAttributes() {
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

    public LuRichText getDescr() {
        return descr;
    }

    public void setDescr(LuRichText descr) {
        this.descr = descr;
    }

    public List<CluInstructor> getInstructors() {
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
        return luCodes;
    }

    public void setLuCodes(List<LuCode> luCodes) {
        this.luCodes = luCodes;
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

     public List<CluAtpTypeKey> getOfferedAtpTypes() {
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
         return hazardousForDisabledStudents;
     }

     public void setHazardousForDisabledStudents(
             boolean hazardousForDisabledStudents) {
         this.hazardousForDisabledStudents = hazardousForDisabledStudents;
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

     public String getState() {
         return state;
     }

     public void setState(String state) {
         this.state = state;
     }

      public CluInstructor getPrimaryInstructor() {
          return primaryInstructor;
      }

      public void setPrimaryInstructor(CluInstructor primaryInstructor) {
          this.primaryInstructor = primaryInstructor;
      }

      public List<CluCampusLocation> getCampusLocations() {
          return campusLocations;
      }

      public void setCampusLocations(List<CluCampusLocation> campusLocationList) {
          this.campusLocations = campusLocationList;
      }

      public Amount getIntensity() {
          return intensity;
      }

      public void setIntensity(Amount intensity) {
          this.intensity = intensity;
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
}
