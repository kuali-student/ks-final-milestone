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
import org.kuali.rice.krad.uif.control.CheckboxGroupControl;
import org.kuali.rice.krad.uif.control.SelectControl;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.FormatOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingMaintainable;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingCrossListingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.service.CourseService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Base view helper service for both create and edit course offering presentations.
 *
 */
public abstract class CourseOfferingMaintainableImpl extends MaintainableImpl implements CourseOfferingMaintainable {

    private transient CourseOfferingService courseOfferingService;
    private transient TypeService typeService;
    private transient StateService stateService;
    private transient CourseService courseService;

    /**
     * This method is being called by KRAD to populate grade roster level types drop down.
     *
     * <p>
     * In 'Create CO', we use a wrapper around <code>FormatOfferingInfo</code> to handle joint formats.
     * In 'Edit CO', it's just <code>FormatOfferingInfo</code> at the collection
     * </p>
     *
     * <p>There would be no reference for this method in the code as it has it's references at the following view xmls</p>
     *      <ul>
     *          <li>CourseOfferingCreateMaintenanceView.xml</li>
     *          <li>CourseOfferingEditMaintenanceView.xml</li>
     *      </ul>
     *
     * @param field grade roster level field
     * @param form maintenace form
     * @see #populateFinalExamDriverTypes
     */
    @SuppressWarnings("unused")
    public void populateGradeRosterLevelTypes(InputField field, MaintenanceDocumentForm form){

        if (field.isReadOnly()){
            return;
        }

        FormatOfferingInfo formatOfferingInfo;
        CourseOfferingWrapper wrapper = (CourseOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        CourseInfo courseInfo = wrapper.getCourse();

        if (wrapper instanceof CourseOfferingCreateWrapper) {
            /**
             * If the call is from create co, then there are two places from where this method is being called. From the 'Add format' section
             * and from the format offering collections. For the 'add format' section, we're checking the property name to get the FO Wrapper
             */
            if (StringUtils.equals(field.getPropertyName(),"addLineFormatWrapper.gradeRosterLevelTypeKey")){
                formatOfferingInfo = ((CourseOfferingCreateWrapper)wrapper).getAddLineFormatWrapper().getFormatOfferingInfo();
            } else {
                //This else is for the format offering collection.
                FormatOfferingWrapper foWrapper = (FormatOfferingWrapper)field.getContext().get(UifConstants.ContextVariableNames.LINE);
                formatOfferingInfo = foWrapper.getFormatOfferingInfo();
                if (foWrapper.isJointOffering()){
                    courseInfo = foWrapper.getJointCreateWrapper().getCourseInfo();
                }
            }
        } else {
            formatOfferingInfo = (FormatOfferingInfo)field.getContext().get(UifConstants.ContextVariableNames.LINE);
        }

        SelectControl control = (SelectControl)field.getControl();

        List<KeyValue> gradeKeyValues = new ArrayList<KeyValue>();

        if (StringUtils.isNotBlank(formatOfferingInfo.getFormatId())){
            // Always include an option for Course
            gradeKeyValues.add(new ConcreteKeyValue(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, getTypeName(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY)));
            gradeKeyValues.addAll(collectActivityTypeKeyValues(courseInfo, formatOfferingInfo.getFormatId(), getTypeService(), ContextUtils.createDefaultContextInfo()));
            control.setDisabled(false);
        } else {
            control.setDisabled(true);
        }

        control.setOptions(gradeKeyValues);

    }

    /**
     * This method is being called by KRAD to populate final exam driver types drop down.
     *
     * <p>
     * In 'Create CO', we use a wrapper around <code>FormatOfferingInfo</code> to handle joint formats.
     * In 'Edit CO', it's just <code>FormatOfferingInfo</code> at the collection
     * </p>
     *
     * <p>There would be no reference for this method in the code as it has it's references at the following view xmls</p>
     *      <ul>
     *          <li>CourseOfferingCreateMaintenanceView.xml</li>
     *          <li>CourseOfferingEditMaintenanceView.xml</li>
     *      </ul>
     *
     * @param field grade roster level field
     * @param form maintenace form
     * @see #populateGradeRosterLevelTypes
     */
    @SuppressWarnings("unused")
    public void populateFinalExamDriverTypes(InputField field, MaintenanceDocumentForm form){

        if (field.isReadOnly()){
            return;
        }

        FormatOfferingInfo formatOfferingInfo;
        CourseOfferingWrapper wrapper = (CourseOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        CourseInfo courseInfo = wrapper.getCourse();

        if (wrapper instanceof CourseOfferingCreateWrapper) {
            /**
             * If the call is from create co, then there are two places from where this method is being called. From the 'Add format' section
             * and from the format offering collections. For the 'add format' section, we're checking the property name to get the FO Wrapper
             */
            if (StringUtils.equals(field.getPropertyName(),"addLineFormatWrapper.finalExamLevelTypeKey")){
                formatOfferingInfo = ((CourseOfferingCreateWrapper)wrapper).getAddLineFormatWrapper().getFormatOfferingInfo();
            } else {
                //This else is for the format offering collection.
                FormatOfferingWrapper foWrapper = (FormatOfferingWrapper)field.getContext().get(UifConstants.ContextVariableNames.LINE);
                formatOfferingInfo = foWrapper.getFormatOfferingInfo();
                if (foWrapper.isJointOffering()){
                    courseInfo = foWrapper.getJointCreateWrapper().getCourseInfo();
                }
            }
        } else {
            formatOfferingInfo = (FormatOfferingInfo)field.getContext().get(UifConstants.ContextVariableNames.LINE);
        }

        SelectControl control = (SelectControl)field.getControl();

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        if (StringUtils.isNotBlank(formatOfferingInfo.getFormatId()) && courseInfo != null){
            keyValues.addAll(collectActivityTypeKeyValues(courseInfo, formatOfferingInfo.getFormatId(), getTypeService(), ContextUtils.createDefaultContextInfo()));
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

    public void populateCrossCourseList(InputField field, MaintenanceDocumentForm form){

        CourseOfferingCreateWrapper wrapper = (CourseOfferingCreateWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        CheckboxGroupControl control = (CheckboxGroupControl)field.getControl();

        List<KeyValue> crossListedCos = new ArrayList<KeyValue>();

        if (wrapper.getCourseOfferingInfo().getCrossListings() != null && wrapper.getCourseOfferingInfo().getCrossListings().size() > 0){
            // Always include an option for Course
            for(CourseOfferingCrossListingInfo courseInfo : wrapper.getCourseOfferingInfo().getCrossListings())  {
                crossListedCos.add(new ConcreteKeyValue(courseInfo.getId(), courseInfo.getCode()));
            }
         }

        crossListedCos.add(new ConcreteKeyValue("1", "A"));
        control.setOptions(crossListedCos);

    }

    /**
     * This method populates {@link CourseCrossListingInfo} in to the {@link CourseOfferingInfo}
     * based on the user selection at create or edit CO. Note: If the <code>kuali.ks.enrollment.options.selective-crosslisting-allowed</code>
     * property is enabled, this method creates cross listing dtos for all the alternate course codes.
     *
     * @param wrapper either {@link CourseOfferingCreateWrapper} or {@link org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper}
     * @param coInfo course offering dto
     */
    protected void loadCrossListedCOs(CourseOfferingWrapper wrapper, CourseOfferingInfo coInfo) {
        coInfo.getCrossListings().clear();
        if (wrapper.isSelectCrossListingAllowed()) {
            List<String> alternateCodes = null;
            if (wrapper instanceof CourseOfferingCreateWrapper){
                alternateCodes = wrapper.getAlternateCOCodes();
            } else if (wrapper instanceof CourseOfferingEditWrapper){
                alternateCodes = ((CourseOfferingEditWrapper)wrapper).getAlternateCourseCodesSuffixStripped();
            }
            for (String alternateCode : alternateCodes) {
                for (CourseCrossListingInfo crossInfo : wrapper.getCourse().getCrossListings()) {
                    if (StringUtils.equals(crossInfo.getCode(),alternateCode)) {
                        CourseOfferingCrossListingInfo crossListingInfo = new CourseOfferingCrossListingInfo();
                        crossListingInfo.setCode(crossInfo.getCode());
                        crossListingInfo.setCourseNumberSuffix(crossInfo.getCourseNumberSuffix());
                        crossListingInfo.setSubjectOrgId(crossInfo.getSubjectOrgId());
                        crossListingInfo.setSubjectArea(crossInfo.getSubjectArea());
                        crossListingInfo.setStateKey(LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY);
                        crossListingInfo.setTypeKey(LuiServiceConstants.LUI_IDENTIFIER_CROSSLISTED_TYPE_KEY);
                        coInfo.getCrossListings().add(crossListingInfo);
                    }
                }
            }
        } else {
            // get all the crosslisted COs
            CourseInfo courseInfo = wrapper.getCourse();
            for (CourseCrossListingInfo crossInfo : courseInfo.getCrossListings()) {
                CourseOfferingCrossListingInfo crossListingInfo = new CourseOfferingCrossListingInfo();
                crossListingInfo.setCode(crossInfo.getCode());
                crossListingInfo.setCourseNumberSuffix(crossInfo.getCourseNumberSuffix());
                crossListingInfo.setSubjectOrgId(crossInfo.getSubjectOrgId());
                crossListingInfo.setSubjectArea(crossInfo.getSubjectArea());
                crossListingInfo.setStateKey(LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY);
                crossListingInfo.setTypeKey(LuiServiceConstants.LUI_IDENTIFIER_CROSSLISTED_TYPE_KEY);
                coInfo.getCrossListings().add(crossListingInfo);
            }
        }
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

    /**
     * Returns the Name for a type key.
     *
     * @param typeKey
     * @return
     */
    protected String getTypeName(String typeKey){
        try{
            TypeInfo typeInfo = getTypeService().getType(typeKey,ContextUtils.createDefaultContextInfo());
            return typeInfo.getName();
        } catch (Exception e){
            //Throwing a runtime as we use this method to get the type name only for the ui purpose..
            throw new RuntimeException(e);
        }
    }


}
