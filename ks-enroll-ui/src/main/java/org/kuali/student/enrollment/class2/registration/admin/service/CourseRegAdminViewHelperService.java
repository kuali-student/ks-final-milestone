package org.kuali.student.enrollment.class2.registration.admin.service;

import org.kuali.student.r2.core.acal.dto.TermInfo;

/**
 * Created by SW Genis on 2014/07/04.
 */
public interface CourseRegAdminViewHelperService {

    public void getRegistrationStatus();

    public void submitRegistrationRequest();

    public TermInfo getTermByCode(String termCode);

}
