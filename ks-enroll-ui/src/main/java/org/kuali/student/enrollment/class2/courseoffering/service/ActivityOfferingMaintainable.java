package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.rice.krad.maintenance.Maintainable;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;

public interface ActivityOfferingMaintainable extends Maintainable{

    public void addOrUpdateScheduleRequestComponent(ActivityOfferingWrapper wrapper);
}
