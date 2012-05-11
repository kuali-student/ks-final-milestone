package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingFormObject;
import org.kuali.student.enrollment.class2.courseoffering.service.ActivityOfferingMaintainable;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.constants.StateServiceConstants;
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.core.state.service.StateService;
import org.kuali.student.r2.core.type.service.TypeService;

import javax.xml.namespace.QName;
import java.util.Locale;
import java.util.Map;

public class ActivityOfferingMaintainableImpl extends MaintainableImpl implements ActivityOfferingMaintainable {

    private CourseOfferingService courseOfferingService;
    private ContextInfo contextInfo;
    private TypeService typeService;
    private StateService stateService;

    @Override
    public void saveDataObject() {
        if(getMaintenanceAction().equals(KRADConstants.MAINTENANCE_NEW_ACTION) ||
                getMaintenanceAction().equals(KRADConstants.MAINTENANCE_COPY_ACTION)) {
            try {
                ActivityOfferingFormObject activityOfferingFormObject = (ActivityOfferingFormObject) getDataObject();
                ActivityOfferingInfo activityOfferingInfo = getCourseOfferingService().createActivityOffering(activityOfferingFormObject.getAoInfo().getFormatOfferingId(),activityOfferingFormObject.getAoInfo().getActivityId(), LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY,activityOfferingFormObject.getAoInfo(),getContextInfo());
                setDataObject(new ActivityOfferingFormObject(activityOfferingInfo));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else {   //should be edit action
            ActivityOfferingFormObject activityOfferingFormObject = (ActivityOfferingFormObject) getDataObject();
            try {
                ActivityOfferingInfo activityOfferingInfo = getCourseOfferingService().updateActivityOffering(activityOfferingFormObject.getAoInfo().getId(), activityOfferingFormObject.getAoInfo(), getContextInfo());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
        try {
            ActivityOfferingInfo info = getCourseOfferingService().getActivityOffering(dataObjectKeys.get("aoInfo.id"),getContextInfo());
            ActivityOfferingFormObject formObject = new ActivityOfferingFormObject(info);
            document.getNewMaintainableObject().setDataObject(formObject);
            document.getOldMaintainableObject().setDataObject(formObject);
//            StateInfo state = getStateService().getState(formObject.getDto().getStateKey(), getContextInfo());
//            formObject.setStateName(state.getName());
            return formObject;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        ActivityOfferingFormObject formObject = (ActivityOfferingFormObject)document.getNewMaintainableObject().getDataObject();
        document.getDocumentHeader().setDocumentDescription("Activity Offering");
        try {
//            StateInfo state = getStateService().getState(formObject.getDto().getStateKey(), getContextInfo());
//            formObject.setStateName(state.getName());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public ContextInfo getContextInfo() {
        if (null == contextInfo) {
            contextInfo = new ContextInfo();
            contextInfo.setAuthenticatedPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
            contextInfo.setPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
            LocaleInfo localeInfo = new LocaleInfo();
            localeInfo.setLocaleLanguage(Locale.getDefault().getLanguage());
            localeInfo.setLocaleRegion(Locale.getDefault().getCountry());
            contextInfo.setLocale(localeInfo);
        }
        return contextInfo;
    }

    public TypeService getTypeService() {
           if(typeService == null) {
             typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, "TypeService"));
        }
        return this.typeService;
    }

    public StateService getStateService() {
           if(stateService == null) {
             stateService = (StateService) GlobalResourceLoader.getService(new QName(StateServiceConstants.NAMESPACE, "StateService"));
        }
        return stateService;
    }

    protected CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE, "CourseOfferingService"));
        }
        return courseOfferingService;
    }
}
