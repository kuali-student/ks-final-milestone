/*
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.academicrecord.model;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.infc.StudentCourseRecord;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.constants.AcademicRecordServiceConstants;

@Entity(name = "StudentCourseRecordEntity")
@Table(name = "KSEN_AR_STU_CRS_REC")
@NamedQueries( {

        @NamedQuery(name = "StudentCourseRecordEntity.getForCourse",
                query = "SELECT scr FROM StudentCourseRecordEntity scr WHERE " +
                        "scr.personId =:personId " +
                        "and scr.courseId =:courseId"),
        @NamedQuery(name = "StudentCourseRecordEntity.getForCourses",
                query = "SELECT scr FROM StudentCourseRecordEntity scr WHERE " +
                        "scr.personId =:personId " +
                        "and scr.courseId in (:courseIds)"),
        @NamedQuery(name = "StudentCourseRecordEntity.getCompletedCourseRecordsForCourse",
                query = "SELECT scr FROM StudentCourseRecordEntity scr WHERE " +
                        "scr.stateKey = '" + AcademicRecordServiceConstants.STUDENTCOURSERECORD_STATE_KEY_COMPLETED + "' " +
                        "and scr.personId =:personId " +
                        "and scr.courseId =:courseId"),
        @NamedQuery(name = "StudentCourseRecordEntity.getCompletedCourseRecords",
                query = "SELECT scr FROM StudentCourseRecordEntity scr WHERE " +
                        "scr.stateKey = '" + AcademicRecordServiceConstants.STUDENTCOURSERECORD_STATE_KEY_COMPLETED + "' " +
                        "and scr.personId =:personId "),
        @NamedQuery(name = "StudentCourseRecordEntity.getAttemptedCourseRecordsForTerm",
                query = "SELECT scr FROM StudentCourseRecordEntity scr WHERE " +
                        "scr.creditsAttempted is not null and CAST(scr.creditsAttempted AS integer) > 0 " +
                        "and scr.personId =:personId " +
                        "and scr.termId =:termId"),
        @NamedQuery(name = "StudentCourseRecordEntity.getCompletedCourseRecordsForTerm",
                query = "SELECT scr FROM StudentCourseRecordEntity scr WHERE " +
                        "scr.stateKey = '" + AcademicRecordServiceConstants.STUDENTCOURSERECORD_STATE_KEY_COMPLETED + "' " +
                        "and scr.personId =:personId " +
                        "and scr.termId =:termId"),
        @NamedQuery(name = "StudentCourseRecordEntity.getIdsByType",
                query = "SELECT scr FROM StudentCourseRecordEntity scr WHERE " +
                        "scr.typeKey =:typeKey")
})
public class StudentCourseRecordEntity extends MetaEntity
    implements AttributeOwner<StudentCourseRecordAttributeEntity> {
	@Column(name= "AR_SCR_TYPE", nullable=false)
	private String typeKey;
	@Column(name= "AR_SCR_STATE", nullable=false)
	private String stateKey;
    @Column(name= "COURSE_ID", nullable=false)
    private String courseId;
    @Column(name= "COURSE_OFFERING_ID", nullable=true)
    private String courseOfferingId;
	@Column(name= "SOURCE_TYPE", nullable=true)
	private String sourceTypeKey;
	@Column(name= "COURSE_REG_ID", nullable=true)
	private String courseRegistrationId;
	@Column(name= "PERS_ID", nullable=false)
	private String personId;
	@Column(name= "COURSE_TITLE", nullable=true)
	private String courseTitle;
	@Column(name= "COURSE_CD", nullable=true)
	private String courseCode;
	@Column(name= "ACTIVITY_CD", nullable=true)
	private String activityCode;
	@Column(name= "TERM_ID", nullable=true)
	private String termId;
	@Column(name= "TERM_NAME", nullable=true)
	private String termName;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name= "COURSE_BEGIN_DT", nullable=true)
	private Date courseBeginDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name= "COURSE_END_DT", nullable=true)
	private Date courseEndDate;
	@Column(name= "ASSIGNED_GRD_VALUE", nullable=true)
	private String assignedGradeValue;
	@Column(name= "ASSIGNED_GRD_SCALE_ID", nullable=true)
	private String assignedGradeScaleKey;
	@Column(name= "ADMIN_GRD_VALUE", nullable=true)
	private String administrativeGradeValue;
	@Column(name= "ADMIN_GRD_SCALE_ID", nullable=true)
	private String administrativeGradeScaleKey;
	@Column(name= "CALC_GRD_VALUE", nullable=true)
	private String calculatedGradeValue;
	@Column(name= "CALC_GRD_SCALE_ID", nullable=true)
	private String calculatedGradeScaleKey;
	@Column(name= "CREDITS_ATTEMPTED", nullable=true)
	private String creditsAttempted;
	@Column(name= "CREDITS_EARNED", nullable=true)
	private String creditsEarned;
	@Column(name= "CREDITS_FOR_GPA", nullable=true)
	private String creditsForGPA;
	@Column(name= "CNT_TOWARD_CREDITS_IND", nullable=true)
	private Boolean countsTowardCredits;
	@Column(name= "REPEATED_IND", nullable=true)
	private Boolean isRepeated;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER, orphanRemoval = true)
	private final Set<StudentCourseRecordAttributeEntity> attributes = new HashSet<StudentCourseRecordAttributeEntity>();


	public StudentCourseRecordEntity() {
	}

	public StudentCourseRecordEntity(StudentCourseRecord dto) {
		super(dto);
		this.setId(dto.getId());
		this.setTypeKey(dto.getTypeKey());
		this.fromDto(dto);
	}

	public void fromDto(StudentCourseRecord dto) {
		super.fromDTO(dto);
		setStateKey(dto.getStateKey());
        setCourseOfferingId(dto.getCourseOfferingId());
        setCourseId(dto.getCourseId());
		setSourceTypeKey(dto.getSourceTypeKey());
		setCourseRegistrationId(dto.getCourseRegistrationId());
		setPersonId(dto.getPersonId());
		setCourseTitle(dto.getCourseTitle());
		setCourseCode(dto.getCourseCode());
		setActivityCode(dto.getActivityCode());
		setTermId(dto.getTermId());
		setTermName(dto.getTermName());
		setCourseBeginDate(dto.getCourseBeginDate());
		setCourseEndDate(dto.getCourseEndDate());
		setAssignedGradeValue(dto.getAssignedGradeValue());
		setAssignedGradeScaleKey(dto.getAssignedGradeScaleKey());
		setAdministrativeGradeValue(dto.getAdministrativeGradeValue());
		setAdministrativeGradeScaleKey(dto.getAdministrativeGradeScaleKey());
		setCalculatedGradeValue(dto.getCalculatedGradeValue());
		setCalculatedGradeScaleKey(dto.getCalculatedGradeScaleKey());
		setCreditsAttempted(dto.getCreditsAttempted());
		setCreditsEarned(dto.getCreditsEarned());
		setCreditsForGPA(dto.getCreditsForGPA());
		setCountsTowardCredits(dto.getCountsTowardCredits());
		setIsRepeated(dto.getIsRepeated());

		// dynamic attributes
		this.getAttributes().clear();
		for (Attribute att : dto.getAttributes()) {
		    StudentCourseRecordAttributeEntity attEntity = new StudentCourseRecordAttributeEntity(att, this);
		    this.getAttributes().add(attEntity);
		}
	}

	public StudentCourseRecordInfo toDto() {
		StudentCourseRecordInfo info = new StudentCourseRecordInfo();
		info.setId(getId());
		info.setTypeKey(this.getTypeKey());
		info.setStateKey(this.getStateKey());
        info.setCourseId(this.getCourseId());
        info.setCourseOfferingId(this.getCourseOfferingId());
		info.setSourceTypeKey(this.getSourceTypeKey());
		info.setCourseRegistrationId(this.getCourseRegistrationId());
		info.setPersonId(this.getPersonId());
		info.setCourseTitle(this.getCourseTitle());
		info.setCourseCode(this.getCourseCode());
		info.setActivityCode(this.getActivityCode());
		info.setTermId(this.getTermId());
		info.setTermName(this.getTermName());
		info.setCourseBeginDate(this.getCourseBeginDate());
		info.setCourseEndDate(this.getCourseEndDate());
		info.setAssignedGradeValue(this.getAssignedGradeValue());
		info.setAssignedGradeScaleKey(this.getAssignedGradeScaleKey());
		info.setAdministrativeGradeValue(this.getAdministrativeGradeValue());
		info.setAdministrativeGradeScaleKey(this.getAdministrativeGradeScaleKey());
		info.setCalculatedGradeValue(this.getCalculatedGradeValue());
		info.setCalculatedGradeScaleKey(this.getCalculatedGradeScaleKey());
		info.setCreditsAttempted(this.getCreditsAttempted());
		info.setCreditsEarned(this.getCreditsEarned());
		info.setCreditsForGPA(this.getCreditsForGPA());
		info.setCountsTowardCredits(this.getCountsTowardCredits());
		info.setIsRepeated(this.getIsRepeated());
		info.setMeta(super.toDTO());

		// dynamic attributes
		for (StudentCourseRecordAttributeEntity att : getAttributes()) {
		    AttributeInfo attInfo = att.toDto();
		    info.getAttributes().add(attInfo);
		}
		return info;
	}

	public String getTypeKey() {
	    return this.typeKey;
	}

	public void setTypeKey(String typeKey) {
	    this.typeKey = typeKey;
	}

	public String getStateKey() {
	    return this.stateKey;
	}

	public void setStateKey(String stateKey) {
	    this.stateKey = stateKey;
	}

    public String getCourseId() {
        return this.courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseOfferingId() {
        return this.courseOfferingId;
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

	public String getSourceTypeKey() {
	    return this.sourceTypeKey;
	}

	public void setSourceTypeKey(String sourceTypeKey) {
	    this.sourceTypeKey = sourceTypeKey;
	}

	public String getCourseRegistrationId() {
	    return this.courseRegistrationId;
	}

	public void setCourseRegistrationId(String courseRegistrationId) {
	    this.courseRegistrationId = courseRegistrationId;
	}

	public String getPersonId() {
	    return this.personId;
	}

	public void setPersonId(String personId) {
	    this.personId = personId;
	}

	public String getCourseTitle() {
	    return this.courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
	    this.courseTitle = courseTitle;
	}

	public String getCourseCode() {
	    return this.courseCode;
	}

	public void setCourseCode(String courseCode) {
	    this.courseCode = courseCode;
	}

	public String getActivityCode() {
	    return this.activityCode;
	}

	public void setActivityCode(String activityCode) {
	    this.activityCode = activityCode;
	}

	public String getTermId() {
	    return this.termId;
	}

	public void setTermId(String termId) {
	    this.termId = termId;
	}

	public String getTermName() {
	    return this.termName;
	}

	public void setTermName(String termName) {
	    this.termName = termName;
	}

	public Date getCourseBeginDate() {
	    return this.courseBeginDate;
	}

	public void setCourseBeginDate(Date courseBeginDate) {
	    this.courseBeginDate = courseBeginDate;
	}

	public Date getCourseEndDate() {
	    return this.courseEndDate;
	}

	public void setCourseEndDate(Date courseEndDate) {
	    this.courseEndDate = courseEndDate;
	}

	public String getAssignedGradeValue() {
	    return this.assignedGradeValue;
	}

	public void setAssignedGradeValue(String assignedGradeValue) {
	    this.assignedGradeValue = assignedGradeValue;
	}

	public String getAssignedGradeScaleKey() {
	    return this.assignedGradeScaleKey;
	}

	public void setAssignedGradeScaleKey(String assignedGradeScaleKey) {
	    this.assignedGradeScaleKey = assignedGradeScaleKey;
	}

	public String getAdministrativeGradeValue() {
	    return this.administrativeGradeValue;
	}

	public void setAdministrativeGradeValue(String administrativeGradeValue) {
	    this.administrativeGradeValue = administrativeGradeValue;
	}

	public String getAdministrativeGradeScaleKey() {
	    return this.administrativeGradeScaleKey;
	}

	public void setAdministrativeGradeScaleKey(String administrativeGradeScaleKey) {
	    this.administrativeGradeScaleKey = administrativeGradeScaleKey;
	}

	public String getCalculatedGradeValue() {
	    return this.calculatedGradeValue;
	}

	public void setCalculatedGradeValue(String calculatedGradeValue) {
	    this.calculatedGradeValue = calculatedGradeValue;
	}

	public String getCalculatedGradeScaleKey() {
	    return this.calculatedGradeScaleKey;
	}

	public void setCalculatedGradeScaleKey(String calculatedGradeScaleKey) {
	    this.calculatedGradeScaleKey = calculatedGradeScaleKey;
	}

	public String getCreditsAttempted() {
	    return this.creditsAttempted;
	}

	public void setCreditsAttempted(String creditsAttempted) {
	    this.creditsAttempted = creditsAttempted;
	}

	public String getCreditsEarned() {
	    return this.creditsEarned;
	}

	public void setCreditsEarned(String creditsEarned) {
	    this.creditsEarned = creditsEarned;
	}

	public String getCreditsForGPA() {
	    return this.creditsForGPA;
	}

	public void setCreditsForGPA(String creditsForGPA) {
	    this.creditsForGPA = creditsForGPA;
	}

	public Boolean getCountsTowardCredits() {
	    return this.countsTowardCredits;
	}

	public void setCountsTowardCredits(Boolean countsTowardCredits) {
	    this.countsTowardCredits = countsTowardCredits;
	}

	public Boolean getIsRepeated() {
	    return this.isRepeated;
	}

	public void setIsRepeated(Boolean isRepeated) {
	    this.isRepeated = isRepeated;
	}

	@Override
	public void setAttributes(Set<StudentCourseRecordAttributeEntity> attributes) {
		this.attributes.clear();
		if (attributes != null) {
		    this.attributes.addAll(attributes);
		}
	}

	@Override
	public Set<StudentCourseRecordAttributeEntity> getAttributes() {
	    return attributes;
	}


}

