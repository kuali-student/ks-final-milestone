package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.uif.service.KSMaintainable;

public interface ActivityOfferingMaintainable extends KSMaintainable{

    public boolean addScheduleRequestComponent(ActivityOfferingWrapper activityOfferingWrapper);

//    public void prepareForScheduleRevise(ActivityOfferingWrapper wrapper);

//    public void processRevisedSchedules(ActivityOfferingWrapper activityOfferingWrapper);
}
