package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.KRADConstants;

import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ScheduleComponentWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.ActivityOfferingMaintainable;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.ViewHelperUtil;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.core.state.dto.StateInfo;
import org.kuali.student.r2.core.state.service.StateService;
import org.kuali.student.r2.core.type.service.TypeService;
import org.kuali.student.r2.core.type.dto.TypeInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

public class ActivityOfferingMaintainableImpl extends MaintainableImpl implements ActivityOfferingMaintainable {

    private transient CourseOfferingService courseOfferingService;
    private ContextInfo contextInfo;
    private TypeService typeService;
    private StateService stateService;
    private CourseService courseService;



    @Override
    public void saveDataObject() {
        if(getMaintenanceAction().equals(KRADConstants.MAINTENANCE_NEW_ACTION) ||
                getMaintenanceAction().equals(KRADConstants.MAINTENANCE_COPY_ACTION)) {
            try {
                ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper) getDataObject();
                ActivityOfferingInfo toSave = activityOfferingWrapper.getAoInfo();

                // **** BEGIN HARD-CODED DEFAULTS FOR TESTING ****
                // TODO REMOVE THESE
                toSave.setFormatOfferingId("447823da-7d70-406e-9736-a1087e27be20");

                FormatOfferingInfo foInfo = getCourseOfferingService().getFormatOffering(toSave.getFormatOfferingId(), getContextInfo());

                toSave.setFormatOfferingName(foInfo.getName());
                toSave.setTermId(foInfo.getTermId());
                toSave.setTermCode(foInfo.getTermId());

                CourseOfferingInfo coInfo = getCourseOfferingService().getCourseOffering(foInfo.getCourseOfferingId(), getContextInfo());

                toSave.setCourseOfferingId(coInfo.getId());
                toSave.setCourseOfferingCode(coInfo.getCourseOfferingCode());
                toSave.setCourseOfferingTitle(coInfo.getCourseOfferingTitle());

                CourseInfo course = getCourseService().getCourse(coInfo.getCourseId());

                // find the format that matches the offering we have
                List<FormatInfo> formats = course.getFormats();
                FormatInfo foundFormat = null;
                for(FormatInfo format : formats) {
                    if(format.getId().equals(foInfo.getFormatId())) {
                        foundFormat = format;
                        break;
                    }
                }

                //
                if (!foundFormat.getActivities().isEmpty()) {
                    toSave.setActivityId(foundFormat.getActivities().get(0).getId());
                }

                // **** END HARD-CODED DEFAULTS FOR TESTING ****
                // TODO REMOVE THESE

                ActivityOfferingInfo activityOfferingInfo = getCourseOfferingService().createActivityOffering(activityOfferingWrapper.getAoInfo().getFormatOfferingId(), activityOfferingWrapper.getAoInfo().getActivityId(), LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, activityOfferingWrapper.getAoInfo(),getContextInfo());
                setDataObject(new ActivityOfferingWrapper(activityOfferingInfo));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else {   //should be edit action
            ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper) getDataObject();
            try {
                ActivityOfferingInfo activityOfferingInfo = getCourseOfferingService().updateActivityOffering(activityOfferingWrapper.getAoInfo().getId(), activityOfferingWrapper.getAoInfo(), getContextInfo());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
        try {
            ActivityOfferingInfo info = getCourseOfferingService().getActivityOffering(dataObjectKeys.get("aoInfo.id"),getContextInfo());
            ViewHelperUtil.getInstructorNames(info.getInstructors());
            ActivityOfferingWrapper wrapper = new ActivityOfferingWrapper(info);

            boolean readOnlyView = Boolean.parseBoolean(dataObjectKeys.get("readOnlyView"));
            wrapper.setReadOnlyView(readOnlyView);

            document.getNewMaintainableObject().setDataObject(wrapper);
            document.getOldMaintainableObject().setDataObject(wrapper);
            document.getDocumentHeader().setDocumentDescription("Edit AO - " + info.getActivityCode());
            /* Tanveer 06/14/2012 Uncomment these two lines once LUI state data is in the db. Li Pan has created the Jira 1464
            StateInfo state = getStateService().getState(wrapper.getAoInfo().getStateKey(), getContextInfo());
            wrapper.setStateName(state.getName());   */
            TypeInfo typeInfo = getTypeService().getType(wrapper.getAoInfo().getTypeKey(), getContextInfo());
            wrapper.setTypeName(typeInfo.getName());

            return wrapper;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        ActivityOfferingWrapper wrapper = (ActivityOfferingWrapper)document.getNewMaintainableObject().getDataObject();
        document.getDocumentHeader().setDocumentDescription("Activity Offering");
        try {
//            StateInfo state = getStateService().getState(wrapper.getDto().getStateKey(), getContextInfo());
//            wrapper.setStateName(state.getName());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    protected void processAfterAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
        super.processAfterAddLine(view, collectionGroup, model, addLine);

        if (addLine instanceof ScheduleComponentWrapper) {
            ScheduleComponentWrapper scheduleComponentWrapper = (ScheduleComponentWrapper)addLine;
            //scheduleComponentWrapper.setEndTime("TESTING");
        }
    }

    public ContextInfo getContextInfo() {
        if (null == contextInfo) {
            contextInfo = ContextBuilder.loadContextInfo();
        }

        return contextInfo;
    }

/*    public TypeService getTypeService() {
           if(typeService == null) {
             typeService = CourseOfferingResourceLoader.loadTypeService();
        }
        return this.typeService;
    } */

    // Tanveer 06/14/2012
    public TypeService getTypeService() {
        if(typeService == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.typeService;
    }

    public StateService getStateService() {
           if(stateService == null) {
             stateService = CourseOfferingResourceLoader.loadStateService();
        }
        return stateService;
    }

    protected CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = CourseOfferingResourceLoader.loadCourseOfferingService();
        }
        return courseOfferingService;
    }

    private CourseService getCourseService() {
        if(courseService == null) {
            courseService = CourseOfferingResourceLoader.loadCourseService();
        }

        return courseService;
    }

    public List<String> getBuildingsCodesForSuggest(String userEnteredCode) {
        List<String> buildingCodes = new ArrayList<String>();
        buildingCodes.add(userEnteredCode+"Dog");
        buildingCodes.add(userEnteredCode+"Emu");
        buildingCodes.add(userEnteredCode+"Fox");
        return buildingCodes;
    }

    public List<String> getRoomNumbersForSuggest(String buildingCode) {
        List<String> roomNumbers = new ArrayList<String>();
        roomNumbers.add("101");
        roomNumbers.add("202");
        roomNumbers.add("303");
        return roomNumbers;
    }

}
