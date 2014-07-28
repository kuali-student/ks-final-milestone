package org.kuali.student.enrollment.class2.registration.admin.service;

import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.enrollment.class2.registration.admin.form.AdminRegistrationForm;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationActivity;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationCourse;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationResultItem;
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
     * @throws Exception
     * @return
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
     * Returns whether or not the student is eligible for the term.
     *
     *
     * @param studentId      required
     * @param termId      required
     * @return Returns a list of validation results
     */
    public List<ValidationResultInfo> checkStudentEligibilityForTermLocal(String studentId,String termId);

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
     * @param form
     */
    public void validateForRegistration(AdminRegistrationForm form);

    /**
     *
     * @param registrationCourse
     * @param termCode
     * @return
     */
    public List<RegistrationActivity> getRegistrationActivitiesForRegistrationCourse(RegistrationCourse registrationCourse, String termCode);

    /**
     *
     */
    public String submitRegistrationRequest(String studentId, String termId, List<RegistrationCourse> registrationCourses);

    /**
     *
     */
    public RegistrationRequestInfo getRegistrationRequest(String regRequestId);

    /**
     *
     * @param results
     * @return
     */
    public List<RegistrationResultItem> createIssueItemsFromResults(List<ValidationResultInfo> results);

}
