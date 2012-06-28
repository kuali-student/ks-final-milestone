package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.kuali.student.enrollment.class2.acal.util.CalendarConstants;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.OfferingInstructorWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ScheduleComponentWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.ActivityOfferingMaintainable;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.ViewHelperUtil;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.state.dto.StateInfo;
import org.kuali.student.r2.core.state.service.StateService;
import org.kuali.student.r2.core.type.service.TypeService;
import org.kuali.student.r2.core.type.dto.TypeInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ActivityOfferingMaintainableImpl extends MaintainableImpl implements ActivityOfferingMaintainable {

    private transient CourseOfferingService courseOfferingService;
    private ContextInfo contextInfo;
    private TypeService typeService;
    private StateService stateService;
    private CourseService courseService;



    @Override
    public void saveDataObject() {
        if(getMaintenanceAction().equals(KRADConstants.MAINTENANCE_EDIT_ACTION)) {
            ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper) getDataObject();
            disassembleInstructorWrapper(activityOfferingWrapper.getInstructors(), activityOfferingWrapper.getAoInfo());
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
            ActivityOfferingInfo info = getCourseOfferingService().getActivityOffering(dataObjectKeys.get(ActivityOfferingConstants.ACTIVITY_OFFERING_WRAPPER_ID),getContextInfo());
            ViewHelperUtil.getInstructorNames(info.getInstructors());
            ActivityOfferingWrapper wrapper = new ActivityOfferingWrapper(info);

            //get the course offering
            CourseOfferingInfo courseOfferingInfo = getCourseOfferingService().getCourseOffering(dataObjectKeys.get(ActivityOfferingConstants.ACTIVITYOFFERING_COURSE_OFFERING_ID), getContextInfo());
            wrapper.setCoInfo(courseOfferingInfo);

            //process instructor effort
            assembleInstructorWrapper(info.getInstructors(), wrapper);

            boolean readOnlyView = Boolean.parseBoolean(dataObjectKeys.get("readOnlyView"));
            wrapper.setReadOnlyView(readOnlyView);

            document.getNewMaintainableObject().setDataObject(wrapper);
            document.getOldMaintainableObject().setDataObject(wrapper);
            document.getDocumentHeader().setDocumentDescription("Edit AO - " + info.getActivityCode());
            StateInfo state = getStateService().getState(wrapper.getAoInfo().getStateKey(), getContextInfo());
            wrapper.setStateName(state.getName());
            TypeInfo typeInfo = getTypeService().getType(wrapper.getAoInfo().getTypeKey(), getContextInfo());
            wrapper.setTypeName(typeInfo.getName());

            return wrapper;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void assembleInstructorWrapper(List<OfferingInstructorInfo> instructors, ActivityOfferingWrapper wrapper){
        if(instructors!= null && !instructors.isEmpty()){
            for(OfferingInstructorInfo instructor : instructors){
                OfferingInstructorWrapper instructorWrapper = new OfferingInstructorWrapper(instructor);
                instructorWrapper.setIntEffort((instructor.getPercentageEffort().intValue()));
                wrapper.getInstructors().add(instructorWrapper);
            }
        }
    }

   private void disassembleInstructorWrapper(List<OfferingInstructorWrapper> instructors, ActivityOfferingInfo aoInfo){
        if(instructors!= null && !instructors.isEmpty()){
            for(OfferingInstructorWrapper instructor : instructors){
                OfferingInstructorInfo instructorInfo = new OfferingInstructorInfo(instructor.getOfferingInstructorInfo());
                instructorInfo.setPercentageEffort(instructor.getIntEffort().floatValue());
                aoInfo.getInstructors().add(instructorInfo);
            }
        }
    }

    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        ActivityOfferingWrapper wrapper = (ActivityOfferingWrapper)document.getNewMaintainableObject().getDataObject();
        document.getDocumentHeader().setDocumentDescription("Activity Offering");
        try {
            StateInfo state = getStateService().getState(wrapper.getAoInfo().getStateKey(), getContextInfo());
            wrapper.setStateName(state.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void processAfterAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
        super.processAfterAddLine(view, collectionGroup, model, addLine);

        if (addLine instanceof ScheduleComponentWrapper) {
            ScheduleComponentWrapper scheduleComponentWrapper = (ScheduleComponentWrapper)addLine;
            if ("1".equals(scheduleComponentWrapper.getAddDaysSpecifiedBoolean())) {
                if (null != scheduleComponentWrapper.getAddWeekDayOptions()) {
                    List<String> weekDayLabels = Arrays.asList("Su ","M ","T ","W ","Th ","F ","Sa ");
                    StringBuilder weekDays = new StringBuilder();
                    for (Integer day : scheduleComponentWrapper.getAddWeekDayOptions()) {
                        weekDays.append(weekDayLabels.get(day));
                    }
                    scheduleComponentWrapper.setWeekDays(weekDays.toString());
                }
            }
            else {
                scheduleComponentWrapper.setWeekDays("To Be Announced");
            }
            if (null != scheduleComponentWrapper.getAddRoomResources()) {
                StringBuilder resources = new StringBuilder();
                for (String resource : scheduleComponentWrapper.getAddRoomResources()) {
                    if (resources.length() > 0) {
                        resources.append(", ");
                    }
                    resources.append(resource);
                }
                scheduleComponentWrapper.setRoomFeatures(resources.toString());
            }
        }
    }

     protected boolean performAddLineValidation(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
        if (addLine instanceof OfferingInstructorWrapper){
            OfferingInstructorWrapper instuctor = (OfferingInstructorWrapper) addLine;

            //validate ID
            List<Person> lstPerson = ViewHelperUtil.getInstructorByPersonId(instuctor.getOfferingInstructorInfo().getPersonId());
            if(lstPerson == null || lstPerson.isEmpty()){
                GlobalVariables.getMessageMap().putErrorForSectionId("ao-personnelgroup", ActivityOfferingConstants.MSG_ERROR_INSTRUCTOR_NOTFOUND, instuctor.getOfferingInstructorInfo().getPersonId());
                return false;
            }
        }

       return super.performAddLineValidation(view, collectionGroup, model, addLine);
     }

    public ContextInfo getContextInfo() {
        if (null == contextInfo) {
            contextInfo = ContextBuilder.loadContextInfo();
        }

        return contextInfo;
    }

    public TypeService getTypeService() {
           if(typeService == null) {
             typeService = CourseOfferingResourceLoader.loadTypeService();
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


    /**
     * Mock data that was being used in the Delivery Logistics section of Edit Activity Offering
     *
     * @param userEnteredCode
     * @return
     */
    public List<String> getBuildingsCodesForSuggest(String userEnteredCode) {
        //TODO - make this an actual search based on user-entered text
        List<String> buildingCodes = new ArrayList<String>();
        buildingCodes.add(userEnteredCode+"Dog");
        buildingCodes.add(userEnteredCode+"Emu");
        buildingCodes.add(userEnteredCode+"Fox");
        return buildingCodes;
    }

    /**
     * Mock data that was being used in the Delivery Logistics section of Edit Activity Offering
     *
     * @param buildingCode
     * @return
     */
    public List<String> getRoomNumbersForSuggest(String buildingCode) {
        //TODO - make this an actual search based on the building & user-entered text
        List<String> roomNumbers = new ArrayList<String>();
        roomNumbers.add("101");
        roomNumbers.add("202");
        roomNumbers.add("303");
        return roomNumbers;
    }

}
