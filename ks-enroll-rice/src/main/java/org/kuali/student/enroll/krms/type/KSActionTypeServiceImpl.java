package org.kuali.student.enroll.krms.type;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krms.api.repository.action.ActionDefinition;
import org.kuali.rice.krms.framework.engine.Action;
import org.kuali.rice.krms.framework.type.ActionTypeService;
import org.kuali.student.enroll.krms.examoffering.action.RDLAction;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SW Genis
 * Date: 2013/08/21
 * Time: 2:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class KSActionTypeServiceImpl extends ActionTypeServiceBase implements ActionTypeService {

    private SchedulingService schedulingService;

    @Override
    public Action loadAction(ActionDefinition actionDefinition) {
        if(actionDefinition.getTypeId().equals("KS-KRMS-TYP-55674")) {
            return buildRDLAction(actionDefinition);
        }
        return null;
    }

    protected Action buildRDLAction(ActionDefinition actionDefinition){
        RDLAction rdlAction = new RDLAction();
        rdlAction.setSchedulingService(this.getSchedulingService());
        String weekdays = actionDefinition.getAttributes().get("weekdays");
        rdlAction.setWeekdays(this.weekdaysString2WeekdaysList(weekdays));
        rdlAction.setStartTime(actionDefinition.getAttributes().get("startTime"));
        rdlAction.setEndTime(actionDefinition.getAttributes().get("endTime"));
        return rdlAction;
    }

    public static List<Integer> weekdaysString2WeekdaysList(String weekdaysString) {
        List<Integer> result = new ArrayList<Integer>();
        if (StringUtils.isNotBlank(weekdaysString)) {
            checkStringForDayCode(SchedulingServiceConstants.MONDAY_TIMESLOT_DAY_CODE, Calendar.MONDAY, result, weekdaysString);
            checkStringForDayCode(SchedulingServiceConstants.TUESDAY_TIMESLOT_DAY_CODE, Calendar.TUESDAY, result, weekdaysString);
            checkStringForDayCode(SchedulingServiceConstants.WEDNESDAY_TIMESLOT_DAY_CODE, Calendar.WEDNESDAY, result, weekdaysString);
            checkStringForDayCode(SchedulingServiceConstants.THURSDAY_TIMESLOT_DAY_CODE, Calendar.THURSDAY, result, weekdaysString);
            checkStringForDayCode(SchedulingServiceConstants.FRIDAY_TIMESLOT_DAY_CODE, Calendar.FRIDAY, result, weekdaysString);
            checkStringForDayCode(SchedulingServiceConstants.SATURDAY_TIMESLOT_DAY_CODE, Calendar.SATURDAY, result, weekdaysString);
            checkStringForDayCode(SchedulingServiceConstants.SUNDAY_TIMESLOT_DAY_CODE, Calendar.SUNDAY, result, weekdaysString);
        }
        return result;
    }

    private static void checkStringForDayCode(String codeInString, Integer integerDayCode, List<Integer> result, String testString) {
        if (testString.contains(codeInString)) {
            result.add(integerDayCode);
        }
    }

    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }
}