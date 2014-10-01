package org.kuali.student.enrollment.class2.registration.admin.service;

import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingContextBar;
import org.kuali.student.enrollment.class2.registration.admin.form.AdminRegistrationForm;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationActivity;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationCourse;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationResult;
import org.kuali.student.enrollment.class2.registration.admin.form.TermResult;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Blue Team (SA)
 * Date: 17 July 2014
 * Utility Class for common auto generated reg group functions
 */
public interface AdminRegistrationViewHelperService {

    /**
     * Retrieves the Student information for the Entity ID entered.
     *
     * @param studentId
     * @return
     * @throws Exception
     */
    public PersonInfo getStudentById(String studentId);

    /**
     * Retrieves the Term information for the term code entered on the screen.
     *
     * @param termCode
     * @return
     */
    public TermInfo getTermByCode(String termCode);

    /**
     * Retrieves the SOC information for the term code Id entered on the screen.
     *
     * @param termId
     * @return
     */
    public SocInfo getSocByTerm(String termId);

    /**
     * Returns whether or not the student is eligible for the term.
     *
     * @param studentId required
     * @param termId    required
     * @return Returns a list of validation result messages
     */
    public List<TermResult> checkStudentEligibilityForTermLocal(String studentId, String termId);

    /**
     * Using the Student ID and term entered to get the registered courses for that student
     * and also retrieves existing activities for that registered courses and adding it.
     *
     * @param studentId
     * @param termCode
     * @return List<RegistrationCourse> registeredCourses
     */
    public List<RegistrationCourse> getCourseRegForStudentAndTerm(String studentId, String termCode);

    /**
     * Using the Student ID and term entered to get the waitlisted courses for that student
     * and also retrieves existing activities for that waitlisted courses and adding it.
     *
     * @param studentId
     * @param termCode
     * @return List<RegistrationCourse> waitListCourse
     */
    public List<RegistrationCourse> getCourseWaitListForStudentAndTerm(String studentId, String termCode);

    /**
     * Validates for validate term, course code and section combimation
     *
     * @param form
     */
    public void validateForRegistration(AdminRegistrationForm form);

    /**
     * Validates if the registration request is ready for submission.
     *
     * @param form
     */
    public void validateForSubmission(AdminRegistrationForm form);

    /**
     * Validates the edited courses is ready for submission on save.
     *
     * @param form
     */
    public void validateCourseEdit(AdminRegistrationForm form);

    /**
     * @param registrationCourse
     * @param termCode
     * @return
     */
    public List<RegistrationActivity> getRegistrationActivitiesForRegistrationCourse(RegistrationCourse registrationCourse, String termCode);

    /**
     * Return a list of available credit options for the given credit option id.
     *
     * @param creditOptionId
     * @return
     */
    public List<String> getCourseOfferingCreditOptionValues(String creditOptionId);

    /**
     * Return a list of available grading options for the given course offering.
     *
     * @param courseOffering
     * @return
     */
    public List<String> getGradingOptionsForCourseOffering(CourseOfferingInfo courseOffering);

    /**
     * Submit the list of courses for registration.
     *
     * @param studentId
     * @param termId
     * @param registrationCourses
     * @param typeKey
     * @return
     */
    public String submitCourses(String studentId, String termId, List<RegistrationCourse> registrationCourses, String typeKey);

    /**
     * Submit a single course for registration.
     *
     * @param studentId
     * @param termId
     * @param registrationCourse
     * @param typeKey
     * @return
     */
    public String submitCourse(String studentId, String termId, RegistrationCourse registrationCourse, String typeKey);

    /**
     * Resubmit a single course without performing any eligibility validation checks.
     *
     * @param studentId
     * @param termId
     * @param registrationCourse
     * @param typeKey
     * @return
     */
    public String resubmitCourse(String studentId, String termId, RegistrationCourse registrationCourse, String typeKey);

    /**
     * Build registration results for the given course.
     *
     * @param course
     * @param messageKey
     * @param results
     * @return
     */
    public RegistrationResult buildRegistrationResult(RegistrationCourse course, TermInfo term, String messageKey, List<ValidationResultInfo> results);

    /**
     * Returns the RegistrationRequest for the given id.
     *
     * @param regRequestId
     * @return
     */
    public RegistrationRequestInfo getRegistrationRequest(String regRequestId);

    /**
     * Retrieve the contextBar info with the adminRegistrationForm
     *
     * @param term
     * @param soc
     * @return
     */
    public CourseOfferingContextBar getContextBarInfo(TermInfo term);

}
