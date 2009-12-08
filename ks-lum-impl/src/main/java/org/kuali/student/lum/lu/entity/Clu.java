/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.lum.lu.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;
import org.kuali.student.core.entity.RichText;
import org.kuali.student.core.entity.TimeAmount;

@Entity
@Table(name = "KSLU_CLU")
@NamedQueries( {
    @NamedQuery(name = "Clu.findClusByIdList", query = "SELECT c FROM Clu c WHERE c.id IN (:idList)"),
    @NamedQuery(name = "Clu.getClusByLuType", query = "SELECT c FROM Clu c WHERE c.state = :luState AND c.luType.id = :luTypeKey"),
    @NamedQuery(name = "Clu.getCluIdsByLoId", query = "SELECT c.id FROM Clu c join c.learningObjectives lo WHERE lo.learningObjectiveId = :loId"),
    @NamedQuery(name = "Clu.getClusByRelation", query = "SELECT c FROM Clu c WHERE c.id IN (SELECT ccr.relatedClu.id FROM CluCluRelation ccr WHERE ccr.clu.id = :parentCluId AND ccr.luLuRelationType.id = :luLuRelationTypeKey)")
})
public class Clu extends MetaEntity implements AttributeOwner<CluAttribute> {
    @Id
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "LUTYPE_ID")
    private LuType luType;

    @OneToMany(mappedBy = "clu",cascade=CascadeType.ALL)
    private List<LearningObjective> learningObjectives;

    @OneToMany(mappedBy = "clu",cascade=CascadeType.ALL)
    private List<Resource> resourceTypes;

    @ManyToMany(mappedBy = "clus")
    private List<CluSet> cluSets;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<CluAttribute> attributes;

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
    private RichText desc;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "RT_MKTG_DESCR_ID")
    private RichText marketingDesc;

    // Deprecated in v  1.0-rc2
    @Column(name = "ACCRED_ORG_ID")
    private String accreditingOrg;

    // Deprecated in v  1.0-rc2 Replaced by primaryAdminOrg
    @Column(name = "ADMIN_ORG_ID")
    private String adminOrg;

    // Deprecated in v  1.0-rc2 Replaced by alternateAdminOrgs
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clu")
    private List<CluOrg> participatingOrgs;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="PRI_ADMIN_ORG_ID")
    private CluAdminOrg primaryAdminOrg;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "KSLU_CLU_JN_ALT_ADMIN_ORG", joinColumns = @JoinColumn(name = "CLU_ID"), inverseJoinColumns = @JoinColumn(name = "ALT_ORG_ID"))
    private List<CluAdminOrg> alternateAdminOrgs;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="PRI_INSTR_ID")
    private CluInstructor primaryInstructor;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "KSLU_CLU_JN_CLU_INSTR", joinColumns = @JoinColumn(name = "CLU_ID"), inverseJoinColumns = @JoinColumn(name = "CLU_INSTR_ID"))
    private List<CluInstructor> instructors;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;

    @Embedded
    @Column(name = "STD_DUR")
    private TimeAmount stdDuration;

    @Column(name = "CAN_CREATE_LUI")
    private boolean canCreateLui;

    @Column(name = "REF_URL")
    private String referenceURL;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="clu")
    private List<LuCode> luCodes;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "CR_ID")
    private CluCredit credit;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "PUBL_ID")
    private CluPublishing publishing;

    @Column(name = "NEXT_REVIEW_PRD")
    private String nextReviewPeriod;

    @Column(name = "IS_ENRL")
    private boolean isEnrollable;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="clu")
    private List<CluAtpTypeKey> offeredAtpTypes;

    @Column(name = "HAS_EARLY_DROP_DEDLN")
    private boolean hasEarlyDropDeadline;

    @Column(name = "DEF_ENRL_EST")
    private int defaultEnrollmentEstimate;

    @Column(name = "DEF_MAX_ENRL")
    private int defaultMaximumEnrollment;

    @Column(name = "IS_HAZR_DISBLD_STU")
    private boolean isHazardousForDisabledStudents;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "FEE_ID")
    private CluFee fee;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "ACCT_ID")
    private CluAccounting accounting;

    @Column(name = "ST")
    private String state;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clu")
    private List<CluAcademicSubjectOrg> academicSubjectOrgs;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clu")
    private List<CluCampusLocation> campusLocationList;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="atpDurationTypeKey", column=@Column(name="CLU_INTSTY_TYPE")),
        @AttributeOverride(name="timeQuantity", column=@Column(name="CLU_INTSTY_QTY")
        )})
        private TimeAmount intensity;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "KSLU_CLU_JN_CLU_ACCRED", joinColumns = @JoinColumn(name = "CLU_ID"), inverseJoinColumns = @JoinColumn(name = "ACCRED_ORG_ID"))
    private List<CluAccreditation> accreditationList;

    @Override
    protected void onPrePersist() {
        this.id = UUIDHelper.genStringUUID(this.id);
    }

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

    public List<Resource> getResourceTypes() {
        return resourceTypes;
    }

    public void setResourceTypes(List<Resource> resourceTypes) {
        this.resourceTypes = resourceTypes;
    }

    @Override
    public List<CluAttribute> getAttributes() {
//        if (attributes == null) {
//            attributes = new ArrayList<CluAttribute>();
//        }
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
//        if (alternateIdentifiers == null) {
//            alternateIdentifiers = new ArrayList<CluIdentifier>();
//        }
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

    public List<CluInstructor> getInstructors() {
//        if (instructors == null) {
//            instructors = new ArrayList<CluInstructor>();
//        }
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
//        if (luCodes == null) {
//            luCodes = new ArrayList<LuCode>();
//        }
        return luCodes;
    }

    public void setLuCodes(List<LuCode> luCodes) {
        this.luCodes = luCodes;
    }

    /**
     * 
     * @deprecated
     * 
     * @return
     */
    public CluCredit getCredit() {
        return credit;
    }

    /**
     * 
     * @deprecated
     * @param credit
     */   
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
//         if (offeredAtpTypes == null) {
//             offeredAtpTypes = new ArrayList<CluAtpTypeKey>();
//         }
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

     public String getState() {
         return state;
     }

     public void setState(String state) {
         this.state = state;
     }

     /**
      * 
      * @deprecated  Replaced by getAccreditationList
      * 
      *  @return
      */
     public String getAccreditingOrg() {
         return accreditingOrg;
     }

     /**
      * 
      * @deprecated  Replaced by setAccreditationList
      * 
      *  @return
      */
     public void setAccreditingOrg(String accreditingOrg) {
         this.accreditingOrg = accreditingOrg;
     }

     /**
      * 
      * @deprecated  Replaced by getPrimaryAdminOrg
      * 
      *  @return
      */
     public String getAdminOrg() {
         return adminOrg;
     }

     /**
      * 
      * @deprecated   Replaced by setPrimaryAdminOrg
      * 
      *  @return
      */
     public void setAdminOrg(String adminOrg) {
         this.adminOrg = adminOrg;
     }

     /**
      * 
      * @deprecated   Replaced by getAlternateAdminOrgs
      * 
      * @return
      */public List<CluOrg> getParticipatingOrgs() {
//          if (participatingOrgs == null) {
//              participatingOrgs = new ArrayList<CluOrg>();
//          }
          return participatingOrgs;
      }

      /**
       * 
       * @deprecated   Replaced by setAlternateAdminOrgs
       * @param participatingOrgs
       */
      public void setParticipatingOrgs(List<CluOrg> participatingOrgs) {
          this.participatingOrgs = participatingOrgs;
      }

      public List<CluSet> getCluSets() {
          return cluSets;
      }

      public void setCluSets(List<CluSet> cluSets) {
          this.cluSets = cluSets;
      }

      public CluInstructor getPrimaryInstructor() {
          return primaryInstructor;
      }

      public void setPrimaryInstructor(CluInstructor primaryInstructor) {
          this.primaryInstructor = primaryInstructor;
      }

      public List<CluAcademicSubjectOrg> getAcademicSubjectOrgs() {
//          if (academicSubjectOrgs == null) {
//              academicSubjectOrgs = new ArrayList<CluAcademicSubjectOrg>();
//          }
          return academicSubjectOrgs;
      }

      public void setAcademicSubjectOrgs(List<CluAcademicSubjectOrg> academicSubjectOrgs) {
          this.academicSubjectOrgs = academicSubjectOrgs;
      }

      public List<CluCampusLocation> getCampusLocationList() {
//          if (campusLocationList == null) {
//              campusLocationList = new ArrayList<CluCampusLocation>();
//          }
          return campusLocationList;
      }

      public void setCampusLocationList(List<CluCampusLocation> campusLocationList) {
          this.campusLocationList = campusLocationList;
      }

      public TimeAmount getIntensity() {
          return intensity;
      }

      public void setIntensity(TimeAmount intensity) {
          this.intensity = intensity;
      }

      public List<CluAccreditation> getAccreditationList() {
//          if (accreditationList == null) {
//              accreditationList = new ArrayList<CluAccreditation>();
//          }
          return accreditationList;
      }

      public void setAccreditationList(List<CluAccreditation> accreditationList) {
          this.accreditationList = accreditationList;
      }

      public CluAdminOrg getPrimaryAdminOrg() {
          return primaryAdminOrg;
      }

      public void setPrimaryAdminOrg(CluAdminOrg primaryAdminOrg) {
          this.primaryAdminOrg = primaryAdminOrg;
      }

      public List<CluAdminOrg> getAlternateAdminOrgs() {
//          if (alternateAdminOrgs == null) {
//              alternateAdminOrgs = new ArrayList<CluAdminOrg>();
//          }
          return alternateAdminOrgs;
      }

      public void setAlternateAdminOrgs(List<CluAdminOrg> alternateAdminOrgs) {
          this.alternateAdminOrgs = alternateAdminOrgs;
      }


}
