package org.kuali.student.enrollment.courseoffering.infc;

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.IdNamelessEntity;
import org.kuali.student.r2.common.infc.RichText;

import java.util.List;


public interface CourseOfferingAdminDisplay extends IdNamelessEntity {



    /**
     * Custom Descr for the course Offering
     * @name Course Offering Description
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
     * @readOnly on updates
     * @impl Name of the type the CO Info is of, read from TypeInfo object
     */
    public String getTypeName();

    /**
     * The name of the State that the Course Offering or the underlying LUI
     * is in.
     *
     * @name State Name Owner
     * @readOnly on updates
     * @impl Name of the state the CO Info is in, read from StateInfo object
     */
    public String getStateName();

    /**
     * The display value of the credit, this is derived by fetching the result value groups for the credit option tied
     * to the course offering and concatenating the values of the options.
     *
     * @name Display Credit
     * @impl Get the credit option values and concatenate to a single field
     * @readOnly on updates
     */
    public String getDisplayCredit();

    /**
     * The display value of the grading options, this is derived by fetching the result value groups for the grading option tied
     * to the course offering and concatenating the values of the options.
     *
     * @name Display Grading
     * @impl Get the credit option values and concatenate to a single field
     * @readOnly on updates
     */
    public String getDisplayGrading();

    /**
     * Activty offering types for the course offering
     *
     * @name Actvity Offering Types
     * @impl Get the format offering for the course offering and then the activity types from the Format offering
     * @readOnly on updates
     */
    public List<String> getActivtyOfferingTypes();

    /**
     * Code for the term the course offering is in
     *
     * @name Term Code
     * @readOnly on updates
     */
    public String getTermCode();



    /**
     * Name of the Term the Course Offering is in
     *
     * @name Term Name
     * @readOnly on updates
     */
    public String getTermName();



}
