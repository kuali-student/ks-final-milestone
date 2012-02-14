/*
 * Copyright 2011 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.lum.lu.infc;

import org.kuali.student.r2.common.infc.Amount;
import org.kuali.student.r2.common.infc.HasEffectiveDates;
import org.kuali.student.r2.common.infc.IdNamelessEntity;
import org.kuali.student.r2.common.infc.TimeAmount;
import org.kuali.student.r2.core.versionmanagement.infc.Version;

import java.util.List;

/**
 * Detailed information about a single CLU.
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface Clu extends IdNamelessEntity, HasEffectiveDates {
    /**
     * Information related to the official identification of the clu, typically
     * in human readable form. Used to officially reference or publish.
     *
     * @name Official Identifier
     * @readOnly
     * @required
     */
    CluIdentifier getOfficialIdentifier();

    /**
     * Information related to alternate identifications of the clu, typically in
     * human readable form. Used to reference or publish.
     *
     * @name Alternate Identifiers
     * @readOnly
     */
    List<? extends CluIdentifier> getAlternateIdentifiers();

    /**
     * The Study Subject Area is used to identify the area of study associated
     * with the clu. It may be a general study area (e.g. Chemistry) or very
     * specific (e.g. Naval Architecture) depending on the level of specificity
     * of the clu.
     *
     * @name Study Subject Area
     */
    String getStudySubjectArea();

    /**
     * Information around the accreditation of the clu.
     *
     * @name Accreditations
     */
    List<? extends Accreditation> getAccreditations();

    /**
     * Places where this clu might be offered
     *
     * @name Campus Locations
     */
    List<String> getCampusLocations();

    /**
     * Admin Orgs associated with this clu
     *
     * @name Admin Orgs
     */
    List<? extends AdminOrg> getAdminOrgs();

    /**
     * Primary potential instructor for the clu. This is primarily for use in
     * advertising the clu and may not be the actual instructor.
     *
     * @name Primary Instructor
     */
    CluInstructor getPrimaryInstructor();

    /**
     * Instructors associated with this clu. This may not be an exhaustive list,
     * and instead may only be used to indicate potential instructors in
     * publication.
     *
     * @name Instructors
     */
    List<? extends CluInstructor> getInstructors();

    /*
    * The expected level of time commitment between the student and the CLU meetings.
    * @name Intensity
    */
    Amount getIntensity();

    /**
     * The standard duration of the learning unit.
     *
     * @name Std Duration
     */
    TimeAmount getStdDuration();

    /**
     * Indicates if the CLU can be used to instantiate LUIs (offerings).
     *
     * @name is Can Create Lui
     */
    boolean isCanCreateLui();

    /**
     * An URL for additional information about the CLU. This could be a
     * reference to a document which might in turn have references to other
     * documents (e.g. course syllabus provided by the faculty or department,
     * standard schedule of classes, etc.).
     *
     * @name Reference URL
     */
    String getReferenceURL();

    /**
     * List of LU code info structures. These are structures so that many
     * different types of codes can be associated with the clu. This allows them
     * to be put into categories.
     *
     * @name Lu Codes
     */
    List<? extends LuCode> getLuCodes();

    /**
     * When the next review should be
     *
     * @name Next Review Period
     */
    String getNextReviewPeriod();

    /**
     * Indicates if Luis generated from this Clu are intended to be enrolled in
     * by Students directly
     *
     * @name is Enrollable
     */
    boolean isEnrollable();

    /**
     * The academic time period types in which this CLU is typically offered.
     * Standard usage would equate to terms.
     *
     * @name Offered Atp Types
     */
    List<String> getOfferedAtpTypes();

    /**
     * Indicates if the CLU has an Early Drop Deadline (EDD). Certain courses
     * are designated as such to maximize access to courses that have
     * historically experienced high demand and high attrition. Default is
     * "false".
     *
     * @name is Has Early Drop Deadline
     */
    boolean isHasEarlyDropDeadline();

    /**
     * Default enrollment estimate for this CLU.
     *
     * @name Default Enrollment Estimate
     */
    int getDefaultEnrollmentEstimate();

    /**
     * Default maximum enrollment for this CLU.
     *
     * @name Default Maximum Enrollment
     */
    int getDefaultMaximumEnrollment();

    /**
     * Indicates if the CLU may be hazardous for students with disabilities.
     * Would default to "false".
     *
     * @name is Hazardous For Disabled Students
     */
    boolean isHazardousForDisabledStudents();

    /**
     * Fee information associated with this CLU.
     *
     * @name Fee Info
     */
    CluFee getFeeInfo();

    /**
     * Accounting information associated with this CLU.
     *
     * @name Account Info
     */
    CluAccounting getAccountingInfo();

    /**
     * Version information associated with this CLU
     *
     * @name Version Info
     */

    Version getVersionInfo();

    /**
     * The expected first academic time period that this clu would be effective.
     * This may not reflect the first "real" academic time period for this clu.
     *
     * @name Expected First Atp
     */
    String getExpectedFirstAtp();

    /**
     * The last academic time period that this clu would be effective.
     *
     * @name Last Atp
     */
    String getLastAtp();

    /**
     * The last academic time period that this clu would be available for
     * enrollment. This may not reflect the last "real" academic time period for
     * this clu.
     *
     * @name Last Admit Atp
     */
    String getLastAdmitAtp();

}
