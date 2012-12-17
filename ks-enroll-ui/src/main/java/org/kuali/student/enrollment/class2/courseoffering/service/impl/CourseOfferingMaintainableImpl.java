/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * @author Kuali Student Team
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;


import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.control.SelectControl;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingMaintainable;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.service.CourseService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class CourseOfferingMaintainableImpl extends MaintainableImpl implements CourseOfferingMaintainable {

    private transient CourseOfferingService courseOfferingService;
    private transient TypeService typeService;
    private transient StateService stateService;
    private transient CourseService courseService;

    /**
     * This method is being called by KRAD to populate grade roster level types drop down. There would be no reference
     * for this method in the code as it has it's reference at the xml
     *
     * @param field
     * @param form
     */
    @SuppressWarnings("unused")
    public void populateGradeRosterLevelTypes(InputField field, MaintenanceDocumentForm form){

        if (field.isReadOnly()){
            return;
        }

        FormatOfferingInfo formatOfferingInfo = (FormatOfferingInfo)field.getContext().get(UifConstants.ContextVariableNames.LINE);
        CourseOfferingWrapper wrapper = (CourseOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        SelectControl control = (SelectControl)field.getControl();

        List<KeyValue> gradeKeyValues = new ArrayList<KeyValue>();

        if (StringUtils.isNotBlank(formatOfferingInfo.getFormatId())){
            // Always include an option for Course
            gradeKeyValues.add(new ConcreteKeyValue(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, CourseOfferingConstants.FORMAT_OFFERING_GRADE_ROSTER_LEVEL_COURSE_DISPLAY));
            gradeKeyValues.addAll(collectActivityTypeKeyValues(wrapper.getCourse(), formatOfferingInfo.getFormatId(), getTypeService(), ContextUtils.createDefaultContextInfo()));
            control.setDisabled(false);
        } else {
            control.setDisabled(true);
        }

        control.setOptions(gradeKeyValues);

    }

    /**
     * This method is being called by KRAD to populate final exam driver types drop down. There would be no reference
     * for this method in the code as it has it's reference at the xml
     *
     * @param field
     * @param form
     */
    @SuppressWarnings("unused")
    public void populateFinalExamDriverTypes(InputField field, MaintenanceDocumentForm form){

        if (field.isReadOnly()){
            return;
        }

        FormatOfferingInfo formatOfferingInfo = (FormatOfferingInfo)field.getContext().get(UifConstants.ContextVariableNames.LINE);
        CourseOfferingWrapper wrapper = (CourseOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        SelectControl control = (SelectControl)field.getControl();

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        if (StringUtils.isNotBlank(formatOfferingInfo.getFormatId())){
            keyValues.addAll(collectActivityTypeKeyValues(wrapper.getCourse(), formatOfferingInfo.getFormatId(), getTypeService(), ContextUtils.createDefaultContextInfo()));
            control.setDisabled(false);
        } else {
            control.setDisabled(true);
        }

        control.setOptions(keyValues);

    }

    protected List<KeyValue> collectActivityTypeKeyValues(CourseInfo course, String formatId, TypeService typeService, ContextInfo contextInfo) {

        List<KeyValue> results = new ArrayList<KeyValue>();

        Set<String> activityTypes = new HashSet<String>();
        for(FormatInfo format : course.getFormats()) {
            if (StringUtils.isBlank(formatId) || (StringUtils.isNotBlank(formatId) && StringUtils.equals(format.getId(),formatId))){
                for (ActivityInfo activity : format.getActivities()) {
                    // if we haven't added a value for this activity type yet
                    if(activityTypes.add(activity.getTypeKey())) {
                        try {
                            TypeInfo type = typeService.getType(activity.getTypeKey(), contextInfo);
                            results.add(new ConcreteKeyValue(type.getKey(), type.getName()));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }

        return results;
    }

    protected TypeService getTypeService() {
        if(typeService == null) {
            typeService = CourseOfferingResourceLoader.loadTypeService();
        }
        return this.typeService;
    }

    protected StateService getStateService() {
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

    protected CourseService getCourseService() {
        if(courseService == null) {
            courseService = CourseOfferingResourceLoader.loadCourseService();
        }
        return this.courseService;
    }


}
