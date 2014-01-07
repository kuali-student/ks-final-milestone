package org.kuali.student.r2.common.krms.type;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.repository.action.ActionDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.framework.engine.Action;
import org.kuali.rice.krms.framework.type.ActionTypeService;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.kuali.student.enrollment.class2.examoffering.krms.action.RDLAction;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This is an implementation of the ActionTypeService to load actions for the execution of rules.
 *
 * @author Kuali Student Team
 */
public class KSActionTypeServiceImpl extends ActionTypeServiceBase implements ActionTypeService {

    private KrmsTypeRepositoryService krmsTypeRepositoryService;
    private SchedulingService schedulingService;

    @Override
    public Action loadAction(ActionDefinition actionDefinition) {

        KrmsTypeDefinition rdlTypeDefinition = this.getKrmsTypeRepositoryService().getTypeByName(
                PermissionServiceConstants.KS_SYS_NAMESPACE, KSKRMSServiceConstants.ACTION_TYPE_REQUESTED_DELIVERY_LOGISTIC);

        if(actionDefinition.getTypeId().equals(rdlTypeDefinition.getId())) {
            return buildRDLAction(actionDefinition);
        }
        return null;
    }

    protected Action buildRDLAction(ActionDefinition actionDefinition){
        RDLAction rdlAction = new RDLAction();

        List<Integer> dayList = new ArrayList<Integer>();
        String day = actionDefinition.getAttributes().get(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_DAY);
        dayList.add(Integer.valueOf(day));

        rdlAction.setWeekdays(dayList);
        rdlAction.setStartTime(actionDefinition.getAttributes().get(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_STARTTIME));
        rdlAction.setEndTime(actionDefinition.getAttributes().get(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_ENDTIME));

        rdlAction.setBuildingId(actionDefinition.getAttributes().get(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_FACILITY));
        rdlAction.setRoomId(actionDefinition.getAttributes().get(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_ROOM));

        if (actionDefinition.getAttributes().containsKey(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_TBA)) {
            rdlAction.setTba(Boolean.parseBoolean(actionDefinition.getAttributes().get(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_TBA)));
        }
        return rdlAction;
    }

    public KrmsTypeRepositoryService getKrmsTypeRepositoryService() {
        if (krmsTypeRepositoryService == null) {
            krmsTypeRepositoryService = GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "krmsTypeRepositoryService"));
        }
        return krmsTypeRepositoryService;
    }

    public void setKrmsTypeRepositoryService(KrmsTypeRepositoryService krmsTypeRepositoryService) {
        this.krmsTypeRepositoryService = krmsTypeRepositoryService;
    }

}