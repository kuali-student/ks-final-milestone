package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.rice.krad.maintenance.Maintainable;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ScheduleWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.ActivityOfferingForm;

public interface ActivityOfferingMaintainable extends Maintainable{

    public void addOrUpdateScheduleRequestComponent(ActivityOfferingWrapper wrapper);

    public ScheduleWrapper getMatchingActualForRequestedSchedule(ActivityOfferingWrapper activityOfferingWrapper,ScheduleWrapper request);

    public void saveAndProcessScheduleRequest(ActivityOfferingWrapper activityOfferingWrapper,ActivityOfferingForm form);
}
