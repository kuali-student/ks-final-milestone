package org.kuali.student.enrollment.courseoffering.infc;

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.IdNamelessEntity;

import java.util.List;


public interface CourseOfferingAdminDisplay extends IdNamelessEntity {

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
     * @readOnly
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
     * @readOnly
     * @impl initially copied from the canonical course but then stored in the
     *       Lui as lui.officialIdentifier.division
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
     * @impl initially copied from the canonical course but then stored in the
     *       Lui as lui.officialIdentifier.longName
     */
    public String getCourseOfferingTitle();



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
     * @readOnly
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


}
