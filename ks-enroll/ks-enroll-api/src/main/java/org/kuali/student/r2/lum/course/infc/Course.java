package org.kuali.student.r2.lum.course.infc;

import java.util.Date;
import java.util.List;

import org.kuali.student.r2.common.infc.Amount;
import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.common.infc.TimeAmount;
import org.kuali.student.r2.core.versionmanagement.infc.Version;

import org.kuali.student.r2.lum.lu.infc.CluInstructor;

/**
 * Detailed information about a single course. For specific usage, check the
 * specific service(s) implementation(s)
 **/

public interface Course extends IdEntity {

    /**
     * The composite string that is used to officially reference or publish the
     * CLU. Note it may have an internal structure that each Institution may
     * want to enforce. This structure may be composed from the other parts of
     * the structure such as Level amp; Division, but may include items such as
     * cluType.
     */
    public String getCode();

    /**
     * The "extra" portion of the code, which usually corresponds with the most
     * detailed part of the number. Ex. at MIT we might map Division to subject
     * area(Ex:6) but overall we need to say the code is 6.120. This field would
     * represent the 120 part.
     */
    public String getCourseNumberSuffix();

    /**
     * A code that indicates what level 100, 200 or upper division, lower
     * division etc
     * 
     * @return
     */
    public String getLevel();

    /**
     * Abbreviated name of the Course
     */
    public String getCourseTitle();

    /**
     * Information related to the official identification of the credit course,
     * typically in human readable form. Used to officially reference or
     * publish.
     */
    public String getTranscriptTitle();

    /**
     * 
     */
    public List<? extends Format> getFormats();

    /**
     * Terms in which this Course is typically offered.
     */
    public List<String> getTermsOffered();

    /**
     * The standard duration of the Course.
     */
    public TimeAmount getDuration();

    /**
     * 
     */
    public List<? extends CourseJoint> getJoints();

    /**
     * 
     */
    public List<? extends CourseCrossListing> getCrossListings();

    /**
     * 
     */
    public List<? extends CourseVariation> getVariations();

    /**
     * The Study Subject Area is used to identify the area of study associated
     * with the course. It may be a general study area (e.g. Chemistry) or very
     * specific (e.g. Naval Architecture).
     */
    public String getSubjectArea();

    /**
     * Places where this course might be offered
     */
    public List<String> getCampusLocations();

    /**
     * The expected level of out of class time commitment between the student
     * and the course.
     */
    public Amount getOutOfClassHours();

    /**
     * Primary potential instructor for the clu. This is primarily for use in
     * advertising the course and may not be the actual instructor.
     */
    public CluInstructor getPrimaryInstructor();

    /**
     * Instructors associated with this course.
     */
    public List<? extends CluInstructor> getInstructors();

    public List<String> getUnitsDeployment();

    /**
     * Narrative description of overall course fee justification.
     */
    public RichText getFeeJustification();

    public List<String> getUnitsContentOwner();

    /**
     * Fees information associated with this Course.
     */
    public List<? extends CourseFee> getFees();

    /**
     * Revenue information associated with this Course.
     */
    public List<? extends CourseRevenue> getRevenues();

    /**
     * Expenditure information associated with this Course.
     */
    public CourseExpenditure getExpenditure();

    /**
     * Learning Objectives associated with this Course.
     */
    public List<? extends LoDisplay> getCourseSpecificLOs();

    /**
     * Grading opitons available for the course
     */
    public List<String> getGradingOptionIds();

    /**
     * Credit outcomes from taking the course
     */
    public List<String> getCreditOptionKeys();

    /**
     * Flag to indicate the course as a special topics course
     */
    public boolean isSpecialTopicsCourse();

    /**
     * Flag to indicate a one-time or pilot course, which is likely to have
     * expedited approval process
     */
    public boolean isPilotCourse();

    /**
     * The first academic time period that this Course would be effective.
     */
    public String getStartTerm();

    /**
     * The last academic time period that this Course would be effective.
     */
    public String getEndTerm();

    /**
     * Date and time the Course became effective. This is a similar concept to
     * the effective date on enumerated values. When an expiration date has been
     * specified, this field must be less than or equal to the expiration date.
     */
    public Date getEffectiveDate();

    /**
     * Date and time that this Course expires. This is a similar concept to the
     * expiration date on enumerated values. If specified, this should be
     * greater than or equal to the effective date. If this field is not
     * specified, then no expiration date has been currently defined and should
     * automatically be considered greater than the effective date.
     */
    public Date getExpirationDate();

    /**
     * This method ...
     * 
     * @return
     */
    public Version getVersionInfo();

}
