package org.kuali.student.enrollment.class2.exam.service.impl;

import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.krms.api.repository.action.ActionDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.framework.engine.Action;
import org.kuali.rice.krms.framework.engine.Agenda;
import org.kuali.rice.krms.framework.engine.BasicAgenda;
import org.kuali.rice.krms.framework.type.ActionTypeService;
import org.kuali.rice.krms.framework.type.AgendaTypeService;
import org.kuali.rice.krms.impl.provider.repository.LazyAgendaTree;
import org.kuali.rice.krms.impl.provider.repository.RepositoryToEngineTranslator;
import org.kuali.rice.krms.impl.type.KrmsTypeServiceBase;
import org.kuali.rice.krms.impl.util.KRMSServiceLocatorInternal;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: SW Genis
 * Date: 2013/08/21
 * Time: 2:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class ActionTypeServiceBase extends KrmsTypeServiceBase implements ActionTypeService {

    private SchedulingService schedulingService;

    public static final ActionTypeServiceBase defaultActionTypeService = new ActionTypeServiceBase();
    private static final String NAME_ATTRIBUTE = "name";

    @Override
    public Action loadAction(ActionDefinition actionDefinition) {
        if(actionDefinition.getName().equals("createRDL")) {
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

    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }
}