package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.form.ManageSOCForm;

import java.util.List;

public interface ManageSOCViewHelperService extends ViewHelperService{

    public List<TermInfo> getTermByCode(String termCode) throws Exception;

    public void loadDataUI(ManageSOCForm socForm);

    public void lockSOC(ManageSOCForm socForm);
}
