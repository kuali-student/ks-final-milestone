/**
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.r2.lum.program.infc;


import org.kuali.student.r2.common.infc.IdNamelessEntity;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.common.infc.TimeAmount;
import org.kuali.student.r2.lum.clu.infc.Accreditation;
import org.kuali.student.r2.lum.clu.infc.CluInstructor;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;

import java.util.Date;
import java.util.List;

/**
 * @author Kuali Student Team
 */
public interface Track extends IdNamelessEntity {

    /**
     * Term when this minor is next slated to be reviewed
     *
     * @name Next Review Period
     */
    public String getNextReviewPeriod();

    /**
     * Instructors associated with this Minor.
     *
     * This may not be an exhaustive list, and instead may only be used to
     * indicate potential instructors in publication.
     *
     * @name Published Instructors
     */
    public List<? extends CluInstructor> getPublishedInstructors();

    /**
     * Identifier of the credential program under which the minor belongs
     *
     * @name Credential Program Id
     */
    public String getCredentialProgramId();

    /**
     * External Agencies that accredit this minor
     *
     * @name Accrediting Agencies
     */
    public List<? extends Accreditation> getAccreditingAgencies();

    /**
     * Indicates if the program is full time, part time, both etc
     *
     * @name Intensity
     */
    public String getIntensity();

    /**
     * Specifies if the Major is Selective Major, Limited Enrollment program or
     * Selective Admissions
     */
    public String getSelectiveEnrollmentCode();

    /**
     * Date this program became effective
     *
     * @name Effective Date
     */
    public Date getEffectiveDate();

    /**
     * Title to print on the diploma
     *
     * @name Diploma Title
     */
    public String getDiplomaTitle();

    /**
     * Places where this program might be offered
     *
     * @name Campus Locations
     */
    public List<String> getCampusLocations();

    /**
     * Result Option for the Program
     *
     * This indicates the degrees that can be awarded from completing this program.
     *
     * @name Result Options
     */
    public List<String> getResultOptions();

    /**
     * Standard Duration of the Program
     *
     * Typically expressed in years or semesters
     *
     * @name Standard Duration
     */
    public TimeAmount getStdDuration();

    /**
     * Division Deployment for the program variation
     *
     * @name Divisions Deployment
     */
    public List<String> getDivisionsDeployment();

    /**
     * @name Divisions Financial Resources
     */
    public List<String> getDivisionsFinancialResources();

    /**
     * @name Divisions Financial COntrol
     */
    public List<String> getDivisionsFinancialControl();

    /**
     * @name Units Deployment
     */
    public List<String> getUnitsDeployment();

    /**
     * @name Units Financial Resources
     */
    public List<String> getUnitsFinancialResources();

    /**
     * @name Units Financial Control
     */
    public List<String> getUnitsFinancialControl();

    /**
     * @name Program Requirements
     */
    public List<String> getProgramRequirements();

    /**
     * @name Code
     */
    public String getCode();

    /**
     * @name Short Title
     */
    public String getShortTitle();

    /**
     * @name Long Title
     */
    public String getLongTitle();

    /**
     * @name Transcript Title
     */
    public String getTranscriptTitle();

    /**
     * @name Catalog Descr
     */
    public RichText getCatalogDescr();

    /**
     * @name Catalog Publication Targets
     */
    public List<String> getCatalogPublicationTargets();

    /**
     * @name Reference URL
     */
    public String getReferenceURL();

    /**
     * @name  University Classification
     */
    public String getUniversityClassification();

    /**
     * @name  Start Term
     */
    public String getStartTerm();

    /**
     * @name  End Term
     */
    public String getEndTerm();

    /**
     * @name  End Program Entry Term
     */
    public String getEndProgramEntryTerm();

    /**
     * @name Divisions Content Owner
     */
    public List<String> getDivisionsContentOwner();

    /**
     * @name Divisions Student Oversight
     */
    public List<String> getDivisionsStudentOversight();

    /**
     * @name Units Content Owner
     */
    public List<String> getUnitsContentOwner();

    /**
     * @name Units Student Oversight
     */
    public List<String> getUnitsStudentOversight();

    /**
     * @name Learning Objectives
     */
    public List<LoDisplayInfo> getLearningObjectives();

    /**
     * @name Last Awarded Term
     */
    public String getLastAwardedTerm();

    /**
     *
     * @name Minor Discipline Id
     */
    public String getMinorDisciplineId();
}
