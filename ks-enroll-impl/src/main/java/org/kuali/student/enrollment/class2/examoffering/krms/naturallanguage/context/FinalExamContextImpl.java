package org.kuali.student.enrollment.class2.examoffering.krms.naturallanguage.context;

import org.kuali.student.enrollment.class2.courseoffering.krms.naturallanguage.context.BasicContextImpl;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: SW Genis
 * Date: 2013/09/04
 * Time: 9:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class FinalExamContextImpl extends BasicContextImpl {

    public final static String WEEKDAY_TOKEN = "weekdays";
    public final static String STARTTIME_TOKEN = "startTime";
    public final static String ENDTIME_TOKEN = "endTime";

    /**
     * Creates the context map (template data) for the requirement component.
     *
     * @param parameters Requirement component
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          Creating context map fails
     */
    @Override
    public Map<String, Object> createContextMap(Map<String, Object> parameters) {
        Map<String, Object> contextMap = super.createContextMap(parameters);

        if(parameters.containsKey(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_WEEKDAY_STRING)) {
            contextMap.put(WEEKDAY_TOKEN, parameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_WEEKDAY_STRING));
        }

        if(parameters.containsKey(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_START)) {
            String timeInMillis = (String) parameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_START);
            TimeOfDayInfo start = TimeOfDayHelper.setMillis(Long.parseLong(timeInMillis));
            contextMap.put(STARTTIME_TOKEN, TimeOfDayHelper.makeFormattedTimeForAOSchedules(start));
        }

        if(parameters.containsKey(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_END)) {
            String timeInMillis = (String) parameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_END);
            TimeOfDayInfo end = TimeOfDayHelper.setMillis(Long.parseLong(timeInMillis));
            contextMap.put(ENDTIME_TOKEN, TimeOfDayHelper.makeFormattedTimeForAOSchedules(end));
        }

        return contextMap;
    }
}
