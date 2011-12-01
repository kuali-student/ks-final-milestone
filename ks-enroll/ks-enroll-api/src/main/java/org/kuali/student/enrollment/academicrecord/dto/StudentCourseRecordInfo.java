/*
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

package org.kuali.student.enrollment.academicrecord.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.academicrecord.infc.StudentCourseRecord;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudentCourseRecordInfo", propOrder = {
        "id", "typeKey", "stateKey", 
        "sourceTypeKey", "courseRegistrationId",
	"personId", "courseTitle", "courseCode", "activityCode", 
        "termName", "courseBeginDate", "courseEndDate", 
        "assignedGradeValue", "assignedGradeScaleKey", 
        "administrativeGradeValue", "administrativeGradeScaleKey", 
        "calculatedGradeValue", "calculatedGradeScaleKey", 
        "creditsAttempted", "creditsEarned", "creditsForGPA",
        "countsTowardCredits", "isRepeated",
        "meta", "attributes", "_futureElements"})

public class StudentCourseRecordInfo     
    extends IdNamelessEntityInfo
    implements StudentCourseRecord, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String sourceTypeKey;

    @XmlElement
    private String courseRegistrationId;

    @XmlElement
    private String personId;

    @XmlElement
    private String courseTitle;

    @XmlElement
    private String courseCode;

    @XmlElement
    private String activityCode;

    @XmlElement
    private String termName;

    @XmlElement
    private Date courseBeginDate;

    @XmlElement
    private Date courseEndDate;

    @XmlElement
    private String assignedGradeValue;

    @XmlElement
    private String assignedGradeScaleKey;

    @XmlElement
    private String administrativeGradeValue;

    @XmlElement
    private String administrativeGradeScaleKey;

    @XmlElement
    private String calculatedGradeValue;

    @XmlElement
    private String calculatedGradeScaleKey;

    @XmlElement
    private String creditsAttempted;

    @XmlElement
    private String creditsEarned;

    @XmlElement
    private String creditsForGPA;

    @XmlElement
    private Boolean countsTowardCredits;

    @XmlElement
    private Boolean isRepeated;

    @XmlAnyElement    
    List<Element> _futureElements;

    public StudentCourseRecordInfo() {
        sourceTypeKey = null;
        courseRegistrationId = null;
        personId = null;
        courseTitle = null;
        courseCode = null;
        activityCode = null;
        termName = null;
        courseBeginDate = null;
        courseEndDate = null;
        assignedGradeValue = null;
        assignedGradeScaleKey = null;
        administrativeGradeValue = null;
        administrativeGradeScaleKey = null;
        calculatedGradeValue = null;
        calculatedGradeScaleKey = null;
        creditsAttempted = null;
        creditsEarned = null;
        creditsForGPA = null;
        countsTowardCredits = new Boolean(false);
        isRepeated = new Boolean(false);

        _futureElements = null;
    }

    /**
     * Constructs a new StudentCOurseRecordInfo from another
     * StudentCourseRecord.
     *
     * @param scr the StudentCourseRecord to copy
     */
    public StudentCourseRecordInfo(StudentCourseRecord scr) {
        super(scr);

        sourceTypeKey = scr.getSourceTypeKey();
        courseRegistrationId = scr.getCourseRegistrationId();
        personId = scr.getPersonId();;
        courseTitle = scr.getCourseTitle();
        courseCode = scr.getCourseCode();
        activityCode = scr.getActivityCode();
        termName = scr.getTermName();
        courseBeginDate = scr.getCourseBeginDate();
        courseEndDate = scr.getCourseEndDate();
        assignedGradeValue = scr.getAssignedGradeValue();
        assignedGradeScaleKey = scr.getAssignedGradeScaleKey();
        administrativeGradeValue = scr.getAdministrativeGradeValue();
        administrativeGradeScaleKey = scr.getAdministrativeGradeScaleKey();
        calculatedGradeValue = scr.getCalculatedGradeValue();
        calculatedGradeScaleKey = scr.getCalculatedGradeScaleKey();
        creditsAttempted = scr.getCreditsAttempted();
        creditsEarned = scr.getCreditsEarned();
        creditsForGPA = scr.getCreditsForGPA();
        countsTowardCredits = scr.getCountsTowardCredits();
        isRepeated = scr.getIsRepeated();

        _futureElements = null;
    }

    @Override
    public String getSourceTypeKey() {
        return sourceTypeKey;
    }

    public void setSourceTypeKey(String sourceTypeKey) {
        this.sourceTypeKey = sourceTypeKey;
    }

    @Override
    public String getCourseRegistrationId() {
        return courseRegistrationId;
    }

    public void setCourseRegistrationId(String courseRegistrationId) {
        this.courseRegistrationId = courseRegistrationId;
    }

    @Override
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Override
    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    @Override
    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    @Override
    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    @Override
    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    @Override
    public Date getCourseBeginDate() {
        return courseBeginDate;
    }

    public void setCourseBeginDate(Date courseBeginDate) {
        this.courseBeginDate = courseBeginDate;
    }

    @Override
    public Date getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(Date courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    @Override
    public String getAssignedGradeValue() {
        return assignedGradeValue;
    }

    public void setAssignedGradeValue(String assignedGradeValue) {
        this.assignedGradeValue = assignedGradeValue;
    }

    @Override
    public String getAssignedGradeScaleKey() {
        return assignedGradeScaleKey;
    }

    public void setAssignedGradeScaleKey(String assignedGradeScaleKey) {
        this.assignedGradeScaleKey = assignedGradeScaleKey;
    }

    @Override
    public String getAdministrativeGradeValue() {
        return administrativeGradeValue;
    }

    public void setAdministrativeGradeValue(String administrativeGradeValue) {
        this.administrativeGradeValue = administrativeGradeValue;
    }

    @Override
    public String getAdministrativeGradeScaleKey() {
        return administrativeGradeScaleKey;
    }

    public void setAdministrativeGradeScaleKey(String administrativeGradeScaleKey) {
        this.administrativeGradeScaleKey = administrativeGradeScaleKey;
    }

    @Override
    public String getCalculatedGradeValue() {
        return calculatedGradeValue;
    }

    public void setCalculatedGradeValue(String calculatedGradeValue) {
        this.calculatedGradeValue = calculatedGradeValue;
    }

    @Override
    public String getCalculatedGradeScaleKey() {
        return calculatedGradeScaleKey;
    }

    public void setCalculatedGradeScaleKey(String calculatedGradeScaleKey) {
        this.calculatedGradeScaleKey = calculatedGradeScaleKey;
    }

    @Override
    public String getCreditsAttempted() {
        return creditsAttempted;
    }

    public void setCreditsAttempted(String creditsAttempted) {
        this.creditsAttempted = creditsAttempted;
    }

    @Override
    public String getCreditsEarned() {
        return creditsEarned;
    }

    public void setCreditsEarned(String creditsEarned) {
        this.creditsEarned = creditsEarned;
    }

    @Override
    public String getCreditsForGPA() {
        return creditsForGPA;
    }

    public void setCreditsForGPA(String creditsForGPA) {
        this.creditsForGPA = creditsForGPA;
    }

    @Override
    public Boolean getCountsTowardCredits() {
        return countsTowardCredits;
    }

    public void setCountsTowardCredits(Boolean countsTowardCredits) {
        this.countsTowardCredits = countsTowardCredits;
    }

    @Override
    public Boolean getIsRepeated() {
        return isRepeated;
    }

    public void setIsRepeated(Boolean isRepeated) {
        this.isRepeated = isRepeated;
    }
}
