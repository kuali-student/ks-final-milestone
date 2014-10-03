package org.kuali.student.enrollment.class2.courseoffering.krms.service;

import org.kuali.rice.krms.framework.engine.Rule;

/**
 * Created by SW Genis on 2014-09-18.
 */
public interface RequisitesService {

    public Rule getRuleForCourseOfferingIdAndType(String courseOfferingId, String agendaType, String ruleType);

    public Rule getRuleForActivityOfferingIdAndType(String courseOfferingId, String agendaType, String ruleType);

}
