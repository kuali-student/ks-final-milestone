package org.kuali.student.enrollment.class2.registration.admin.service;

import org.kuali.student.enrollment.class2.registration.admin.form.AdminRegistrationForm;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationActivity;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationCourse;
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
     *
     * @param form
     * @throws Exception
     */
    public void populateStudentInfo(AdminRegistrationForm form) throws Exception;

    /**
     *
     * @param termCode
     * @return
     */
    public TermInfo getTermByCode(String termCode);

    /**
     *
     * @param studentId
     * @param termCode
     * @return
     */
    public List<RegistrationCourse> getCourseRegForStudentAndTerm(String studentId, String termCode);

    /**
     *
     * @param studentId
     * @param termCode
     * @return
     */
    public List<RegistrationCourse> getCourseWaitListForStudentAndTerm(String studentId, String termCode);

    /**
     *
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
    public void submitRegistrationRequest();

    /**
     *
     */
    public void getRegistrationStatus();

}
