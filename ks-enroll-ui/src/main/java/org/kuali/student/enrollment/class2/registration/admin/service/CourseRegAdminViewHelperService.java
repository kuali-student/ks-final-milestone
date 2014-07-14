package org.kuali.student.enrollment.class2.registration.admin.service;

import org.kuali.student.enrollment.class2.registration.admin.form.AdminRegistrationForm;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationCourse;
import org.kuali.student.r2.core.acal.dto.TermInfo;

import java.util.List;

/**
 * Created by SW Genis on 2014/07/04.
 */
public interface CourseRegAdminViewHelperService {

    public void getRegistrationStatus();

    public void submitRegistrationRequest();

    public TermInfo getTermByCode(String termCode);

    public void populateStudentInfo(AdminRegistrationForm form) throws Exception;

    public List<RegistrationCourse> getCourseRegStudentAndTerm(String studentId, String termCode);

    public List<RegistrationCourse> getCourseWaitListStudentAndTerm(String studentId, String termCode);

}
