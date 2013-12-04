package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.student.common.uif.service.KSMaintainable;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;

import java.util.List;

public interface ActivityOfferingMaintainable extends KSMaintainable{

    public boolean addScheduleRequestComponent(ActivityOfferingWrapper activityOfferingWrapper);

    public List<String> getEndTimes(String days,String startTime,String termTypeKey) throws Exception;

}
