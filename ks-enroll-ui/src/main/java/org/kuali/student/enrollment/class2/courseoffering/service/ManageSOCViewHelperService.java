package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.form.ManageSOCForm;
import org.kuali.student.common.uif.service.KSViewHelperService;

import java.util.List;

public interface ManageSOCViewHelperService extends KSViewHelperService {

    public TermInfo getTermByCode(String termCode);

    public void buildModel(ManageSOCForm socForm);

    public void lockSOC(ManageSOCForm socForm);

    public void allowSOCFinalEdit(ManageSOCForm socForm);

    public void publishSOC(ManageSOCForm socForm);

    public void closeSOC(ManageSOCForm socForm);

    public void startMassScheduling(ManageSOCForm socForm);

}
