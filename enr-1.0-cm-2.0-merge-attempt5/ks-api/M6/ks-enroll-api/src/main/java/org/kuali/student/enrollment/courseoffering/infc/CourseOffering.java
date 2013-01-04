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

import org.kuali.student.r2.common.infc.IdNamelessEntity;
import org.kuali.student.r2.common.infc.RichText;

import java.util.List;

/**
 * @author Kamal
 */

public interface CourseOffering 
    extends IdNamelessEntity {

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
     * Custom Description for the course Offering.
     *
     * @name Course Offering Description
     */
    public RichText getDescr();

    /**
     * Identifies the number of a course as reflected in the course
     * catalog.  This typically must be unique across all courses
     * offered during that term. If the user wants to create two
     * separate offerings for the same course they must modify this
     * code to make it unique. For example: An on-line offering of the
     * course might have an "O" appended to it to distinguish it from
     * the face to face offering, i.e. ENG101 and ENG101O Initially
     * copied from the course catalog but then, depending on the
     * configuration it may be updatable. Often this field is
     * configured so that it is not not directly updatable but rather
     * is calculated from it's two constituent parts, the subject area
     * and the course number suffix. For example: Subject Area = "ENG"
     * and Suffix = "101" then code = "ENG101"
     *
     * This field is differentiated from the Couurse Offering Code in
     * that the Course Offering Code can updated while the Course Code
     * always indicates the official canonical code.
     *
     * @name Course Code
     * @readOnly
     * @impl this is a read only copy of the official canonical course code
     */
    public String getCourseCode();

    /**
     * Identifies the number of a course as reflected in the course
     * catalog.  This typically must be unique across all courses
     * offered during that term. If the user wants to create two
     * separate offerings for the same course they must modify this
     * code to make it unique. For example: An on-line offering of the
     * course might have an "O" appended to it to distinguish it from
     * the face to face offering, i.e. ENG101 and ENG101O Initially
     * copied from the course catalog but then, depending on the
     * configuration it may be updatable. Often this field is
     * configured so that it is not not directly updatable but rather
     * is calculated from it's two constituent parts, the subject area
     * and the course number suffix. For example: Subject Area = "ENG"
     * and Suffix = "101" then code = "ENG101"
     *
     * @name Course Offering Code
     * @impl initialially this is copied from the course catalog code but then
     *       is subsequently stored in the lui as lui.officialIdentifier.code
     */
    public String getCourseOfferingCode();

    /**
     * Identifies the department and/subject code of the course as
     * reflected in the course catalog. Initially copied from the
     * course catalog but then, depending on the configuration it may
     * be updatable. In most configurations this should not be
     * updatable. Often used in the calculation of the courseCode
     * 
     * @name Subject Area
     * @impl initially copied from the canonical course but then stored in the
     *       Lui as lui.officialIdentifier.division
     */
    public String getSubjectArea();

    /**
     * A suffix of the course number as reflected in the college
     * catalog. This is the "number" portion of the course
     * code. Initially copied from the course catalog but then,
     * depending on the configuration it may be updatable. This field
     * is often configured to be updatable but the updates typically
     * simply append something like an "O" for on-line to it to
     * differentiate multiple course offerings for the same
     * course. Often used in the calculation of the courseCode.
     * 
     * @name Course Number Suffix
     * @impl initially copied from the canonical course but then stored in the
     *       Lui as lui.officialIdentifier.suffixCode
     */
    public String getCourseNumberSuffix();

    /**
     * Name of the course used in the college catalog. Initially
     * copied from the course catalog but then, depending on the
     * configuration it may be updatable. For regular courses this is
     * not generally allowed to be updated on the offering, but for
     * special topics courses this is often overridden to capture the
     * particular topic being taught offered this term. Note: the
     * configuration of the validation for titles is typically
     * restricted to exclude line breaks. This may have to be loosened
     * as some schools may want the particular topic to appear on a
     * 2nd line. For example: SPECIAL TOPICS: AN EXPLORATION OF DEEP
     * SPACE ARTIFACTS l
     *
     * @name Course Title
     * @impl initially copied from the canonical course but then stored in the
     *       Lui as lui.officialIdentifier.longName
     */
    public String getCourseOfferingTitle();

    /**
     * Indicates that the entire course offering is an Honors Course
     * ??? Is this an enrollment restriction rule or a flag or both?
     * 
     * @name Is Honors Offering
     * @impl store in a generic lui luCodes type of field?
     */
    public Boolean getIsHonorsOffering();

    /**
     * Total maximum number of "seats" or enrollment slots that can be
     * filled for the offering. Calculated based on sum of all the
     * maximum seats of primary activity type offerings.
     * 
     * @name Maximum Enrollment
     * @readOnly
     * @impl maps to Lui.maximumEnrollment
     */
    public Integer getMaximumEnrollment();

    /**
     * Total minimum number of seats that must be filled for the
     * offering not to be canceled. Calculated based on sum of all the
     * minimum seats of primary activity type offerings
     * 
     * @name Minimum Enrollment
     * @impl maps to Lui.minimumEnrollment
     */
    public Integer getMinimumEnrollment();

    /**
     * The cross listings which this CourseOffering can be offered under.
     */
    public List<? extends CourseOfferingCrossListing> getCrossListings();


    /******** Assessment Information ***************/

    /**
     * The options/scales that indicate the allowable grades within a
     * grading scheme that can be awarded. Typically, the values here
     * are constrained by the values on the canonical course. If the
     * value is set here then the Clu must have a grading option set
     * on the canonical activity. For example: an id might point to
     * Pass/Fail or Letter Graded option.
     * 
     * @name: Grading Option Id
     * @impl Lui.resultOptionIds of type ???
     */
    public String getGradingOptionId();

    /**
     * The options/scales that indicate the allowable grades within a
     * grading scheme in which an eligible student can register. This
     * list of options includes the Grading Option Id plus any
     * additional grading schemes, such as P/F or Audit.
     * 
     * @name: registration Grading Option Ids
     * @impl Lui.resultOptionIds of type ???
     */
    public List<String> getStudentRegistrationGradingOptions();

    /**
     * The name of the credit option
     * 
     * @name Credit Option Name
     * @readOnly
     * @impl the name of the RVG for the creditOptionId
     */
    public String getCreditOptionName();

    /**
     * Type of credit of course offering. This field is initially
     * copied from the canonical course but then, depending on
     * configuration, it may be updated. TODO: figure out which of the
     * credit options will be copied down because the canonical has
     * more than one! Often it is just a fixed single value but a
     * ResultValuesGroup could contain a range (with increments) or
     * even a discrete list of possible credit values.
     *
     * @name Credit Option Id
     * @impl Lui.resultOptionIds returns a list of
     *       resultOptions. Filter option with credit type and that
     *       should give the resultValueGroup.
     */
    public String getCreditOptionId();


    /******** Personnel Information *****************/

    /**
     * Instructors for this course offering TODO: find out if the
     * canonical instructors should be copied down
     * 
     * @name Instructors
     * @impl These are derived from Lui Person relations with instructor type
     */
    public List<? extends OfferingInstructor> getInstructors();

    /********* Organization Information **************/

    /**
     * Organization(s) that is responsible for administering the
     * course delivery - and all associated logistics - of the course
     * Initially copied from the canonical course then, depending on
     * the configuration, updated This is typically an academic
     * department but could be for example the extended studies office
     * that is responsible for delivering the course even though it's
     * content is managed by an academic department.
     * 
     * @name Units Deployment
     * @impl initalized from canonical course units deployment but then stored
     *       in lui.unitsDeployment
     */
    public List<String> getUnitsDeploymentOrgIds();

    /**
     * Organization(s) that is responsible for the academic content of
     * the course as approved in its canonical form. This is the
     * organization that has oversight of the curriculum. This is
     * typically an academic department.
     * 
     * @name Units Content Owner
     * @readOnly on updates
     * @impl this is never updatable so it should just be grabbed from
     *       the canonical course and then stored in
     *       lui.unitsContentOwner
     */
    public List<String> getUnitsContentOwnerOrgIds();



    /*********** Waitlist *****************************/

    /*
     * TODO: Skip this section for core slice development
     */

    /**
     * Indicates whether a RegistrationGroup has a waitlist TODO:
     * figure out how to store this TODO: make sure we are consistent
     * on how we spell Waitlist, should be spelled Waitlist, or Wait
     * List or Wait-List (as the merriam-webster has it)
     * 
     * @name Has Waitlist
     * @impl not sure how to store this, it depends on how we end up
     *       implementing waitlists.
     */
    public Boolean getHasWaitlist();

    /**
     * Indicates the type of waitlist as it relates to processing
     * students on and off The three types predefined in kuali are
     * Automatic, Semi-Automatic and Manual TODO: Right not Waitlist
     * types are not tied to any Waitlist object so we need to define
     * an "other key" to get the list of valid values from the type
     * service. TODO: Decide if we need a separate getHasWaitList,
     * perhaps no value in this field means no waitlist. TODO: Cross
     * validate with hasWaitlist
     * 
     * @name Waitlist Type Key
     * @impl TODO: decide if this this should be stored on the Lui or on a
     *       waitlist object?
     */
    public String getWaitlistTypeKey();

    /**
     *  Indicates the waitlist level, i.e., CourseOffering or
     *  ActivityOffering.  Values are in WaitListLevel enum.
     *
     * @name Waitlist Level Type Key
     */
    public String  getWaitlistLevelTypeKey();

    /**
     * Maximum number of students to be allowed on the wait list.
     *
     * @name Waitlist Maximum
     * @impl maps to Lui.waitlistMaximum
     */
    public Integer getWaitlistMaximum();    


  /************* Finances ***************************/

    /**
     * The primary source of funding for the offering.
     * 
     * @name Funding Source
     * @impl fyi "funding source" is an enumeration values are state support,
     *       self-support, contract funding
     */
    public String getFundingSource();

    /**
     * Flag indicating whether a course is eligible for Financial
     * Aid. Derived from course catalog (canonical) TODO: find a place
     * to store this on the canonical course because it does not
     * currently exist there TODO: Decide if this is really a rule or
     * a CluSet or what?
     * 
     * @name Is Financial Aid Eligible
     * @impl TODO: decide where to store
     */
    public Boolean getIsFinancialAidEligible();

    /**
     * Places where this Course offering is offered.
     *
     * @name Campus Locations
     */
    public List<String> getCampusLocations();

    /**
     * Indicates whether a final exam is to be given for this format
     * Offering and if its true, the level at which it exists
     * Indicates the type of final exam ('STANDARD', 'ALTERNATE',
     * 'NONE') to be given for this format Offering, if any
     *
     * @name Final Exam Type
     */
    public String getFinalExamType();

    /**
     * Indicates whether the fee is at activity offering level or
     * course offering level. If true its at Activity Offering level,
     * if false its at CourseOffering level.
     *
     * @name Is Fee at Activity Offering
     */
    public Boolean getIsFeeAtActivityOffering();

    /**
     * Indicates whether the course offering has an evaluation.
     *
     * @name Is Evaluated
     */
    public Boolean getIsEvaluated();

    /**
     * Gets the Course Offering URL.
     *
     * @name Course Offering URL
     */
    public String getCourseOfferingURL();

    /**
     * Gets the Credit Count
     *
     * @name Credit Count
     * @readOnly
     * @impl this is the number of credits
     */
    public String getCreditCnt();

    /**
     * Gets the Grading Option Name
     *
     * @name Grading Option Name
     * @readOnly
     * @impl this is the name of the RVG matching the gradingOptionId
     */
    public String getGradingOptionName();

   /**
     * A suffix is the system generated code that is hidden from the
     * user that parallels with courseOfferingNumberSuffix.
     *
     * @name Course Number Internal Suffix
     * @impl initially copied from the canonical course but then
     *       stored in the Lui as lui.officialIdentifier.suffixCode
     */
    public String getCourseNumberInternalSuffix();
}
