/*
 * Copyright 2010 The Kuali Foundation 
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

package org.kuali.student.enrollment.courseoffering.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.common.infc.TimeAmount;
import org.kuali.student.r2.lum.lrc.infc.ResultValuesGroup;
import org.kuali.student.r2.lum.clu.dto.ExpenditureInfo;
import org.kuali.student.r2.lum.clu.dto.RevenueInfo;
import org.kuali.student.r2.lum.clu.infc.Fee;

/**
 * @author Kamal
 */

public interface CourseOffering 
    extends IdEntity {

    /**
     * A unique identifier assigned to all approved courses that exist
     * in the catalog This is not updatable once initialized.
     * 
     * @name Course Id
     * @readOnly
     * @required
     * @impl this maps the the version dependent id as the offering
     *       must point to one and only one version of the
     *       course. Maps to cluId in Lui.
     */
    public String getCourseId();

    /**
     * Academic term of course offering
     * 
     * @name Term Id
     * @readOnly on updates
     * @required
     * @impl set during the #createCourseOffering and then maps to
     *       the aptKey on the Lui
     */
    public String getTermId();


    /**
     * Identifies the number of a course as reflected in the course catalog.
     * This typically must be unique across all courses offered during that
     * term. If the user wants to create two separate offerings for the same
     * course they must modify this code to make it unique. For example: An
     * on-line offering of the course might have an "O" appended to it to
     * distinguish it from the face to face offering, i.e. ENG101 and ENG101O
     * Initially copied from the course catalog but then, depending on the
     * configuration it may be updatable. Often this field is configured so that
     * it is not not directly updatable but rather is calculated from it's two
     * constituent parts, the subject area and the course number suffix. For
     * example: Subject Area = "ENG" and Suffix = "101" then code = "ENG101"
     * 
     * @name Course Offering Code
     * @impl initialially this is copied from the course catalog code but then
     *       is subsequently stored in the lui as lui.officialIdentifier.code
     */
    public String getCourseOfferingCode();

    /**
     * Identifies the department and/subject code of the course as reflected in
     * the course catalog. Initially copied from the course catalog but then,
     * depending on the configuration it may be updatable. In most
     * configurations this should not be updatable. Often used in the
     * calculation of the courseCode
     * 
     * @name Subject Area
     * @impl initially copied from the canonical course but then stored in the
     *       Lui as lui.officialIdentifier.division
     */
    public String getSubjectArea();

    /**
     * A suffix of the course number as reflected in the college catalog. This
     * is the "number" portion of the course code. Initially copied from the
     * course catalog but then, depending on the configuration it may be
     * updatable. This field is often configured to be updatable but the updates
     * typically simply append something like an "O" for on-line to it to
     * differentiate multiple course offerings for the same course. Often used
     * in the calculation of the courseCode.
     * 
     * @name Course Number Suffix
     * @impl initially copied from the canonical course but then stored in the
     *       Lui as lui.officialIdentifier.suffixCode
     */
    public String getCourseNumberSuffix();

    /**
     * Name of the course used in the college catalog. Initially copied from the
     * course catalog but then, depending on the configuration it may be
     * updatable. For regular courses this is not generally allowed to be
     * updated on the offering, but for special topics courses this is often
     * overridden to capture the particular topic being taught offered this
     * term. Note: the configuration of the validation for titles is typically
     * restricted to exclude line breaks. This may have to be loosened as some
     * schools may want the particular topic to appear on a 2nd line. For
     * example: SPECIAL TOPICS: AN EXPLORATION OF DEEP SPACE ARTIFACTS
     * 
     * @name Course Title
     * @impl initially copied from the canonical course but then stored in the
     *       Lui as lui.officialIdentifier.longName
     */
    public String getCourseTitle();

    /**
     * Indicates that the entire course offering is an Honors Course ??? Is this
     * an enrollment restriction rule or a flag or both?
     * 
     * @name Is Honors Offering
     * @impl store in a generic lui luCodes type of field?
     */
    public Boolean getIsHonorsOffering();

    /**
     * Total maximum number of "seats" or enrollment slots that can be filled
     * for the offering. Calculated based on sum of all the maximum seats of
     * primary activity type offerings.
     * 
     * @name Maximum Enrollment
     * @impl maps to Lui.maximumEnrollment
     */
    public Integer getMaximumEnrollment();

    /**
     * Total minimum number of seats that must be filled for the offering not to
     * be canceled. Calculated based on sum of all the minimum seats of primary
     * activity type offerings
     * 
     * @name Minimum Enrollment
     * @impl maps to Lui.minimumEnrollment
     */
    public Integer getMinimumEnrollment();

    /**
     * The unique identifier of the other course offerings with which this
     * offering is joint-listed
     * 
     * @name Joint Offering Ids
     * @readOnly I think?
     * @impl Canonical might suggest offerings that can be jointly offered. This
     *       is stored as a luiluirelation of joint type
     */
    public List<String> getJointOfferingIds();

    /******** Assessment Information ***************/
    /**
     * The options/scales that indicate the allowable grades that can be
     * awarded. Typically the values here are constrained by the values on the
     * canonical course. If the value is set here then the Clu must have a
     * grading option set on the canonical activity. For example: an id might
     * point to Pass/Fail or Letter Graded option.
     * 
     * @name: Grading Option Ids
     * @impl these are actually Ids to ResultValuesGroup. Lui.resultOptionIds
     *       returns a list of resultOptions. Filter options with grading type
     *       and those should give the resultValueGroupIds
     */
    public List<String> getGradingOptionKeys();

    /**
     * Type of credit of course offering. This field is initially copied from
     * the canonical course but then, depending on configuration, it may be
     * updated. TODO: figure out which of the credit options will be copied down
     * because the canonical has more than one! Often it is just a fixed single
     * value but a ResultValuesGroup could contain a range (with increments) or
     * even a discrete list of possible credit values.
     * 
     * @name Credit Options
     * @impl Lui.resultOptionIds returns a list of resultOptions. Filter option
     *       with credit type and that should give the resultValueGroup
     */
    public ResultValuesGroup getCreditOptions();

    /**
     * Key indicating the level at which grade rosters should be generated -
     * activity, format or course. TODO: define these types. TODO: add a service
     * method to get the list of types that can be put in this field.
     * 
     * @name Grade Roster Level Key
     * @impl this should be a constrained the a list types generated from the
     *       roster types from the generic type system.
     */
    public String getGradeRosterLevelTypeKey();

    /******** Personnel Information *****************/

    /**
     * Instructors for this course offering TODO: find out if the canonical
     * instructors should be copied down
     * 
     * @name Instructors
     * @impl These are derived from Lui Person relations with instructor type
     */
    public List<? extends OfferingInstructor> getInstructors();

    /********* Organization Information **************/

    /**
     * Organization(s) that is responsible for administering the course delivery
     * - and all associated logistics - of the course Initially copied from the
     * canonical course then, depending on the configuration, updated This is
     * typically an academic department but could be for example the extended
     * studies office that is responsible for delivering the course even though
     * it's content is managed by an academic department.
     * 
     * @name Units Deployment
     * @impl initalized from canonical course units deployment but then stored
     *       in lui.unitsDeployment
     */
    public List<String> getUnitsDeployment();

    /**
     * Organization(s) that is responsible for the academic content of the
     * course as approved in its canonical form. This is the organization that
     * has oversight of the curriculum. This is typically an academic
     * department.
     * 
     * @name Units Content Owner
     * @readOnly on updates
     * @impl this is never updatable so it should just be grabbed from the
     *       canonical course and then stored in lui.unitsContentOwner
     */
    public List<String> getUnitsContentOwner();

    /********** Final Exam Information *****************/

    /**
     * Indicates whether a final exam is to be given Initially copied from the
     * canonical course and then, depending on configuration, updated.
     * 
     * @name Has Final Exam
     * @impl If set to true, create a lui of type final exam and a lui lui
     *       relation to the course offering
     */
    public Boolean getHasFinalExam();

    /*********** Waitlist *****************************/

    /*
     * TODO: Skip this section for core slice development
     */

    /**
     * Indicates whether a RegistrationGroup has a waitlist TODO: figure out how
     * to store this TODO: make sure we are consistent on how we spell Waitlist,
     * should be spelled Waitlist, or Wait List or Wait-List (as the
     * merriam-webster has it)
     * 
     * @name Has Waitlist
     * @impl not sure how to store this, it depends on how we end up
     *       implementing waitlists.
     */
    public Boolean getHasWaitlist();

    /**
     * Indicates the type of waitlist as it relates to processing students on
     * and off The three types predefined in kuali are Automatic, Semi-Automatic
     * and Manual TODO: Right not Waitlist types are not tied to any Waitlist
     * object so we need to define an "other key" to get the list of valid
     * values from the type service. TODO: Decide if we need a separate
     * getHasWaitList, perhaps no value in this field means no waitlist. TODO:
     * Cross validate with hasWaitlist
     * 
     * @name Waitlist Type Key
     * @impl TODO: decide if this this should be stored on the Lui or on a
     *       waitlist object?
     */
    public String getWaitlistTypeKey();

    /**
     * Maximum number of students to be allowed on the wait list
     * 
     * @name Waitlist Maximum
     * @impl TODO: decide if this this should be stored on the Lui or on a
     *       waitlist object?
     */
    public Integer getWaitlistMaximum();

    /**
     * Indicates if the waitlist requires checkin
     * 
     * @name Is Waitlist Checkin Required
     * @impl TODO: decide if this this should be stored on the Lui or on a
     *       waitlist object?
     */
    public Boolean getIsWaitlistCheckinRequired();

    /**
     * Frequency for the waitlist checkin
     * 
     * @name Waitlist Checkin Frequency
     * @impl TODO: decide if this this should be stored on the Lui or on a
     *       waitlist object?
     */
    public TimeAmount getWaitlistCheckinFrequency();

    /************* Finances ***************************/

    /**
     * The primary source of funding for the offering.
     * 
     * @name Funding Source
     * @impl fyi "funding source" is an enumeration values are state support,
     *       self-support, contract funding
     */
    public String getFundingSource();

    /*
     * TODO: Change CourseFeeInfo, CourseRevenueInfo and CourseExpenditureInfo
     * to interfaces after course service is migrated to 1.3 Evaluate creating
     * parallel financial structures in enrollment so that there is no
     * referential dependencies from enr to curriculum module
     */

    /**
     * Fees associated with the course offering. Initially copied from the
     * course catalog but then, depending on the configuration it may be
     * updatable.
     * 
     * @name Fees
     * @impl initially copied from canonical CourseFeeInfo but subsequently
     *       stored on the lui
     */
    public List<? extends Fee> getFees();

    /**
     * Organization(s) that receives the revenue from fees associated with the
     * course offering Initially copied from the course catalog but then,
     * depending on the configuration it may be updatable.
     * 
     * @name Revenues
     * @impl initially copied from cannonical CourseRevenueInfo but then
     *       subsequently stored on the Lui
     */
    public List<RevenueInfo> getRevenues();

    /**
     * Organization(s) that incurs the cost associated with the course offering
     * Initially copied from the course catalog but then, depending on the
     * configuration it may be updatable.
     * 
     * @name Expenditure
     * @impl initially copied from cannonical CourseRevenueInfo but then
     *       subsequently stored on the Lui
     */
    public ExpenditureInfo getExpenditure();

    /**
     * Flag indicating whether a course is eligible for Financial Aid. Derived
     * from course catalog (canonical) TODO: find a place to store this on the
     * canonical course because it does not currently exist there TODO: Decide
     * if this is really a rule or a CluSet or what?
     * 
     * @name Is Financial Aid Eligible
     * @impl TODO: decide where to store
     */
    public Boolean getIsFinancialAidEligible();
}
