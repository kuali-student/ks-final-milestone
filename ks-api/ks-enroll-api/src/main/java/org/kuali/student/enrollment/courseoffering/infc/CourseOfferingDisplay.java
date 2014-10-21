package org.kuali.student.enrollment.courseoffering.infc;

import org.kuali.student.r2.common.infc.IdNamelessEntity;
import org.kuali.student.r2.common.infc.KeyName;
import org.kuali.student.r2.common.infc.RichText;

import java.util.List;


public interface CourseOfferingDisplay extends IdNamelessEntity {



    /**
     * Custom Descr for the course Offering
     * @name Course Offering Description
     * @readOnly
     */
    public RichText getDescr();

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
     * @readOnly
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
     * @readOnly
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
     * @readOnly
     */
    public String getSubjectArea();

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
     * @readOnly
    \ */
    public String getCourseOfferingTitle();




    /**
     * The name of the Type that the Course Offering or the underlying LUI
     * is of.
     *
     * @name Type name
     * @readOnly
     * @impl Name of the type the CO Info is of, read from TypeInfo object
     */
    public String getTypeName();

    /**
     * The name of the State that the Course Offering or the underlying LUI
     * is in.
     *
     * @name State Name Owner
     * @readOnly
     * @impl Name of the state the CO Info is in, read from StateInfo object
     */
    public String getStateName();

    /**
     * The display value of the credit, this is derived by fetching the result value groups for the credit option tied
     * to the course offering and concatenating the values of the options.
     *
     * @name Credit Option
     * @impl copy from CO.creditOptionId and CO.creditOptionName
     * @readOnly
     */
    public KeyName getCreditOption();

    /**
     * The options/scales that indicate the allowable grades within a
     * grading scheme that can be awarded. Typically, the values here
     * are constrained by the values on the canonical course. If the
     * value is set here then the Clu must have a grading option set
     * on the canonical activity. For example: an id might point to
     * Pass/Fail or Letter Graded option.
     *
     * @name Grading Option
     * @impl copy from Co.gradingOptionId and Co.gradingOptionName
     * @readOnly
     */
    public KeyName getGradingOption();

    /**
     * Code for the term the course offering is in
     *
     * @name Term Code
     * @readOnly
     */
    public String getTermCode();


    /**
     * Name of the Term the Course Offering is in
     *
     * @name Term Name
     * @readOnly
     */
    public String getTermName();

    /**
     * The options/scales that indicate the allowable grades within a grading
     * scheme in which an eligible student can register. This list of options
     * includes the Grading Option Id (such as kuali.rvg.passfail) plus any
     * additional grading schemes, such as P/F or Audit.
     *
     * @name Student Registration Grading Options
     */
    public List<? extends KeyName> getStudentRegistrationGradingOptions();

    /**
     * Indicates that the Course Offering is an Honors option
     *
     * @name Is Honors Offering
     * @readOnly
     * @impl 'H' icon displayed if true
     */
    public Boolean getIsHonorsOffering();

}
