package org.kuali.student.r2.common.krms.type;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krms.api.repository.action.ActionDefinition;
import org.kuali.rice.krms.framework.engine.Action;
import org.kuali.rice.krms.framework.type.ActionTypeService;
import org.kuali.student.enrollment.class2.examoffering.krms.action.RDLAction;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;

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
        rdlAction.setWeekdays(SchedulingServiceUtil.weekdaysString2WeekdaysList(weekdays));
        rdlAction.setStartTime(actionDefinition.getAttributes().get("startTime"));
        rdlAction.setEndTime(actionDefinition.getAttributes().get("endTime"));
        return rdlAction;
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