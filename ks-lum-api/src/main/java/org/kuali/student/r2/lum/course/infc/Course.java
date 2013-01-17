package org.kuali.student.r2.lum.course.infc;

import java.util.Date;
import java.util.List;

import org.kuali.student.r2.common.infc.Amount;
import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.common.infc.TimeAmount;
import org.kuali.student.r2.core.versionmanagement.infc.Version;
import org.kuali.student.r2.lum.clu.infc.CluInstructor;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;


/**
 * Detailed information about a single course. For specific usage, check the
 * specific service(s) implementation(s)
 **/

public interface Course extends IdEntity {

    /**
     * Identifier used to officially reference or publish the
     * course.
     *
     * The code is typically unique within a specified context or time period but 
     * it is not intended as a database key or id.
     *
     * It may have an internal structure that each Institution may
     * want to enforce. This structure may be composed from the other parts of
     * the structure such as Level amp; Division, but may include items such as
     * the type of the course and whether it has a lab or not.
     *
     * @name code
     */
    public String getCode();

    /**
     * The "extra" portion of the code, which usually corresponds with the most
     * detailed part of the number. Ex. at MIT we might map Division to subject
     * area(Ex:6) but overall we need to say the code is 6.120. This field would
     * represent the 120 part.
     *
     * @name Course Number Suffix
     */
    public String getCourseNumberSuffix();

    /**
     * A code that indicates what level 100, 200 or upper division, lower
     * division etc
     *
     * @name Level
     */
    public String getLevel();

    /**
     * Abbreviated name of the Course
     *
     * @name Course Title
     */
    public String getCourseTitle();

    /**
     * Information related to the official identification of the credit course,
     * typically in human readable form. Used to officially reference or
     * publish.
     *
     * @name Transcript Title
     */
    public String getTranscriptTitle();

    /**
     * Course Formats
     *
     * @name Formats
     */
    public List<? extends Format> getFormats();

    /**
     * Terms in which this Course is typically offered.
     *
     * @name Terms Offered
     */
    public List<String> getTermsOffered();

    /**
     * The standard duration of the Course.
     *
     * @name Duration
     */
    public TimeAmount getDuration();

    /**
     * Joint Courses
     *
     * @name Joints
     */
    public List<? extends CourseJoint> getJoints();

    /**
     * Cross Listed Courses
     *
     * @name Cross Listings
     */
    public List<? extends CourseCrossListing> getCrossListings();

    /**
     * Variations
     *
     * @name Variations
     */
    public List<? extends CourseVariation> getVariations();

    /**
     * The Study Subject Area is used to identify the area of study associated
     * with the course. It may be a general study area (e.g. Chemistry) or very
     * specific (e.g. Naval Architecture).
     *
     * @name Subject Area
     */
    public String getSubjectArea();

    /**
     * Places where this course might be offered
     *
     * @name Campus Locations
     */
    public List<String> getCampusLocations();

    /**
     * The expected level of out of class time commitment between the student
     * and the course.
     *
     * @name Out of Class Hours
     */
    public Amount getOutOfClassHours();

    /**
     * Primary potential instructor for the course. This is primarily for use in
     * advertising the course and may not be the actual instructor.
     *
     * @name Primary Instructor
     */
    public CluInstructor getPrimaryInstructor();

    /**
     * Instructors associated with this course.
     *
     * @name Instructors
     */
    public List<? extends CluInstructor> getInstructors();

    /**
     * Units Responsible for Deploying this course
     * @name Units Deployment
     */
    public List<String> getUnitsDeployment();

    /**
     * Narrative description of overall course fee justification.
     *
     * @name Fee Justification
     */
    public RichText getFeeJustification();

    /**
     * Units Responsible for overseeing the content of this course
     * @name Units Content Owner
     */
    public List<String> getUnitsContentOwner();

    /**
     * Fees information associated with this Course.
     *
     * @name Fees
     */
    public List<? extends CourseFee> getFees();

    /**
     * Revenue information associated with this Course.
     *
     * @name Revenues
     */
    public List<? extends CourseRevenue> getRevenues();

    /**
     * Expenditure information associated with this Course.
     *
     * @name Expenditure
     */
    public CourseExpenditure getExpenditure();

    /**
     * Learning Objectives associated with this Course.
     *
     * @name Course Specific Learning Objectives
     */
    public List<? extends LoDisplay> getCourseSpecificLOs();

    /**
     * Grading options available for the course
     *
     * @name Grading Options
     */
    public List<String> getGradingOptions();

    /**
     * Credit outcomes from taking the course
     *
     * @name Credit Options
     */
    public List<ResultValuesGroupInfo> getCreditOptions();

    /**
     * Flag to indicate the course as a special topics course
     *
     * @name Special Topics Course?
     */
    public boolean isSpecialTopicsCourse();

    /**
     * Flag to indicate a one-time or pilot course, which is likely to have
     * expedited approval process
     *
     * @name Pilot Course?
     */
    public boolean isPilotCourse();

    /**
     * The first academic time period that this Course would be effective.
     *
     * @name Start Term
     */
    public String getStartTerm();

    /**
     * The last academic time period that this Course would be effective.
     *
     * @name End Term
     */
    public String getEndTerm();

    /**
     * Date and time the Course became effective. This is a similar concept to
     * the effective date on enumerated values. When an expiration date has been
     * specified, this field must be less than or equal to the expiration date.
     *
     * @name Effective Date
     */
    public Date getEffectiveDate();

    /**
     * Date and time that this Course expires. This is a similar concept to the
     * expiration date on enumerated values. If specified, this should be
     * greater than or equal to the effective date. If this field is not
     * specified, then no expiration date has been currently defined and should
     * automatically be considered greater than the effective date.
     *
     * @name Expiration Date
     */
    public Date getExpirationDate();

    /**
     * Version of this course
     *
     * @name Version
     */
    public Version getVersion();

}
