package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.rice.krad.maintenance.Maintainable;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.ActivityOfferingForm;

public interface ActivityOfferingMaintainable extends Maintainable{

    public boolean addScheduleRequestComponent(ActivityOfferingForm form);

    public void prepareForScheduleRevise(ActivityOfferingWrapper wrapper);

    public void processRevisedSchedules(ActivityOfferingWrapper activityOfferingWrapper);
}
