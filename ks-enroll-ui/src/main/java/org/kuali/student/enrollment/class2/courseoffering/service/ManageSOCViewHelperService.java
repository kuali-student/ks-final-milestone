package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.form.ManageSOCForm;

import java.util.List;

public interface ManageSOCViewHelperService extends ViewHelperService{

    public List<TermInfo> getTermByCode(String termCode) throws Exception;

    public void buildModel(ManageSOCForm socForm);

    public void lockSOC(ManageSOCForm socForm);

    public void allowSOCFinalEdit(ManageSOCForm socForm);

    public void publishSOC(ManageSOCForm socForm);

    public void closeSOC(ManageSOCForm socForm);

    public void startMassScheduling(ManageSOCForm socForm);

    public String getSocStateDescription(String stateKey);
}
