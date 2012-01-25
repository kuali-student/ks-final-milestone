package org.kuali.student.enrollment.class2.acal.service;

import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.kuali.student.enrollment.class2.acal.form.HolidayCalendarForm;

/**
 * Created by IntelliJ IDEA.
 * Date: 1/24/12
 * Time: 11:56 AM
 * To change this template use File | Settings | File Templates.
 */
public interface AcademicCalendarViewHelperService extends ViewHelperService {
    public void createHolidayCalendar(HolidayCalendarForm hcForm) throws Exception;
}
