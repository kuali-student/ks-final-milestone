package org.kuali.student.enrollment.class2.acal.service;

import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.acal.form.AcademicTermForm;
import org.kuali.student.r2.common.dto.ContextInfo;

public interface AcademicTermEditViewHelperService {

    public void saveTerm(AcademicTermForm academicTermForm,ContextInfo context) throws Exception;

    public void buildTerm(String termId,AcademicTermForm academicTermForm,ContextInfo context) throws Exception;
}
