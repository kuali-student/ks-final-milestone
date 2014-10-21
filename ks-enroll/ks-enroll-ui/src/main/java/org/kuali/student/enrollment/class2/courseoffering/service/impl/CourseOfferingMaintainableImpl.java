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
import org.kuali.rice.krad.maintenance.Maintainable;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.component.BindingInfo;
import org.kuali.rice.krad.uif.control.SelectControl;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.common.uif.form.KSUifMaintenanceDocumentForm;
import org.kuali.student.common.uif.service.impl.KSMaintainableImpl;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.FormatOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingCrossListingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Base view helper service for both create and edit course offering presentations.
 *
 */
public abstract class CourseOfferingMaintainableImpl extends KSMaintainableImpl implements Maintainable {

    /**
     * Returns the format long name short name as a string array  by concatenation all the shortened activity names with / seperated
     *
     * @param foWrapper
     * @param course
     * @return   String[]
     */
    public String[] getFormatShortAndLongNames(FormatOfferingWrapper foWrapper,CourseInfo course){
        String[] formatNames = new String[2];
        for (FormatInfo format : course.getFormats()) {
            if (StringUtils.equals(format.getId(),foWrapper.getFormatId())){
                StringBuilder longName = new StringBuilder();
                StringBuilder shortName = new StringBuilder();
                for (ActivityInfo activityInfo : format.getActivities()) {
                    TypeInfo activityType = null;
                    try {
                        activityType = CourseOfferingManagementUtil.getTypeService().getType(activityInfo.getTypeKey(), ContextUtils.createDefaultContextInfo());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    longName.append(activityType.getName()+"/");
                    shortName.append(activityType.getName().toUpperCase().substring(0,3)+"/");
                }
                if (format.getActivities().size() == 1){
                    formatNames[0] = StringUtils.removeEnd(longName.toString(),"/") + " Only";
                } else {
                    formatNames[0] = StringUtils.removeEnd(longName.toString(),"/");
                }
                formatNames[1] = StringUtils.removeEnd(shortName.toString(),"/");
                return formatNames;
            }
        }
        formatNames[0] = StringUtils.EMPTY;
        formatNames[1] = StringUtils.EMPTY;
        return formatNames;
    }

    @Override
    public void processCollectionAddBlankLine(ViewModel model, String collectionId, String collectionPath) {

        if (StringUtils.endsWith(collectionPath,"formatOfferingList")){
            MaintenanceDocumentForm maintenanceForm = (MaintenanceDocumentForm) model;
            MaintenanceDocument document = maintenanceForm.getDocument();

            if (document.getNewMaintainableObject().getDataObject() instanceof CourseOfferingEditWrapper){
                CourseOfferingEditWrapper wrapper = (CourseOfferingEditWrapper)document.getNewMaintainableObject().getDataObject();

                populateFormatNames(wrapper);

                FormatOfferingWrapper newFoWrapper = new FormatOfferingWrapper();
                newFoWrapper.getRenderHelper().setNewRow(true);

                wrapper.getFormatOfferingList().add(newFoWrapper);

                return;
            }
        }

        super.processCollectionAddBlankLine(model, collectionId, collectionPath);
    }

    @Override
    public void processAfterDeleteLine(ViewModel model, String collectionId, String collectionPath, int lineIndex) {

        BindingInfo bindingInfo = (BindingInfo) model.getViewPostMetadata().getComponentPostData(collectionId,
                UifConstants.PostMetadata.BINDING_INFO);

        if (StringUtils.equals(bindingInfo.getBindingName(),"formatOfferingList")){
            MaintenanceDocumentForm maintenanceForm = (MaintenanceDocumentForm) model;
            MaintenanceDocument document = maintenanceForm.getDocument();

            if (document.getNewMaintainableObject().getDataObject() instanceof CourseOfferingEditWrapper){
                CourseOfferingEditWrapper wrapper = (CourseOfferingEditWrapper)document.getNewMaintainableObject().getDataObject();

                for (FormatOfferingWrapper foWrapper : wrapper.getFormatOfferingList()){
                    if (StringUtils.isNotBlank(foWrapper.getFormatId())){
                        foWrapper.getRenderHelper().setNewRow(false);
                    }
                }
            }
        }
    }

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
            formatOfferingInfo = ((FormatOfferingWrapper)field.getContext().get(UifConstants.ContextVariableNames.LINE)).getFormatOfferingInfo();
        }

        SelectControl control = (SelectControl)field.getControl();

        List<KeyValue> gradeKeyValues = new ArrayList<KeyValue>();

        if (StringUtils.isNotBlank(formatOfferingInfo.getFormatId())){
            gradeKeyValues.addAll(collectActivityTypeKeyValues(courseInfo, formatOfferingInfo.getFormatId(), CourseOfferingManagementUtil.getTypeService(), ContextUtils.createDefaultContextInfo()));
            // Always include an option for Course as last option
            gradeKeyValues.add(new ConcreteKeyValue(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, getTypeName(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY)));
            control.setDisabled(false);
        } else {
            control.setDisabled(true);
        }

        control.setOptions(gradeKeyValues);

    }

    @SuppressWarnings("unused")
    public  List<KeyValue> getGradeRosterLevelTypes(String formatId, MaintenanceDocumentForm form){
        CourseOfferingWrapper wrapper = (CourseOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        CourseInfo courseInfo = wrapper.getCourse();

        List<KeyValue> gradeKeyValues = new ArrayList<KeyValue>();

        if (StringUtils.isNotBlank(formatId) && courseInfo != null){
            gradeKeyValues.addAll(collectActivityTypeKeyValues(courseInfo, formatId, CourseOfferingManagementUtil.getTypeService(), ContextUtils.createDefaultContextInfo()));
            // Always include an option for Course as last option
            gradeKeyValues.add(new ConcreteKeyValue(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, getTypeName(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY)));
        }
        return gradeKeyValues;
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
    public void populateFinalExamDriverTypes(InputField field, MaintenanceDocumentForm form) throws Exception {

        if (field.isReadOnly()) {
            return;
        }

        FormatOfferingInfo formatOfferingInfo;
        CourseOfferingWrapper wrapper = (CourseOfferingWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
        CourseInfo courseInfo = wrapper.getCourse();

        if (wrapper instanceof CourseOfferingCreateWrapper) {
            /**
             * If the call is from create co, then there are two places from where this method is being called. From the 'Add format' section
             * and from the format offering collections. For the 'add format' section, we're checking the property name to get the FO Wrapper
             */
            if (StringUtils.equals(field.getPropertyName(), "addLineFormatWrapper.finalExamLevelTypeKey")) {
                formatOfferingInfo = ((CourseOfferingCreateWrapper) wrapper).getAddLineFormatWrapper().getFormatOfferingInfo();
            } else {
                //This else is for the format offering collection.
                FormatOfferingWrapper foWrapper = (FormatOfferingWrapper) field.getContext().get(UifConstants.ContextVariableNames.LINE);
                formatOfferingInfo = foWrapper.getFormatOfferingInfo();
                if (foWrapper.isJointOffering()) {
                    courseInfo = foWrapper.getJointCreateWrapper().getCourseInfo();
                }
            }
        } else {
            formatOfferingInfo = ((FormatOfferingWrapper) field.getContext().get(UifConstants.ContextVariableNames.LINE)).getFormatOfferingInfo();
        }

        SelectControl control = (SelectControl) field.getControl();

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        CourseOfferingEditWrapper courseOfferingEditWrapper;
        if (StringUtils.isNotBlank(formatOfferingInfo.getFormatId()) && courseInfo != null) {
            keyValues.addAll(collectActivityTypeKeyValues(courseInfo, formatOfferingInfo.getFormatId(), CourseOfferingManagementUtil.getTypeService(), ContextUtils.createDefaultContextInfo()));
            control.setDisabled(false);
        } else {
            control.setDisabled(true);
        }

        control.setOptions(keyValues);

    }

    public List<KeyValue> getFinalExamDriverTypes(String formatId, MaintenanceDocumentForm form) throws Exception {
        CourseOfferingWrapper wrapper = (CourseOfferingWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
        CourseInfo courseInfo = wrapper.getCourse();

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        CourseOfferingEditWrapper courseOfferingEditWrapper;
        if (StringUtils.isNotBlank(formatId) && courseInfo != null) {
            keyValues.addAll(collectActivityTypeKeyValues(courseInfo, formatId, CourseOfferingManagementUtil.getTypeService(), ContextUtils.createDefaultContextInfo()));
       }

        return keyValues;
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
                String alternateCourseCodesSuffixStripped = StringUtils.stripEnd(alternateCode,coInfo.getCourseNumberSuffix());
                for (CourseCrossListingInfo crossInfo : wrapper.getCourse().getCrossListings()) {
                    if (StringUtils.equals(crossInfo.getCode(),alternateCourseCodesSuffixStripped)) {
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

    public void populateFormatNames(CourseOfferingWrapper coWrapper){

        if (!(coWrapper instanceof CourseOfferingEditWrapper)){
            throw new RuntimeException("Invalid CourseOffering wrapper.");
        }

        CourseOfferingEditWrapper editWrapper = (CourseOfferingEditWrapper)coWrapper;
        for (FormatOfferingWrapper foWrapper : editWrapper.getFormatOfferingList()){
            if (StringUtils.isBlank(foWrapper.getFormatOfferingInfo().getName())){
                String[] foNames = getFormatShortAndLongNames(foWrapper, editWrapper.getCourse());
                foWrapper.getFormatOfferingInfo().setName(foNames[0]);
                foWrapper.getFormatOfferingInfo().setShortName(foNames[1]);
            }
            if (StringUtils.isNotBlank(foWrapper.getFormatId())){
                foWrapper.getRenderHelper().setNewRow(false);
            }
        }

    }
   /**
     * Returns the Name for a type key.
     *
     * @param typeKey
     * @return
     */
    protected String getTypeName(String typeKey){
        try{
            TypeInfo typeInfo = CourseOfferingManagementUtil.getTypeService().getType(typeKey,ContextUtils.createDefaultContextInfo());
            return typeInfo.getName();
        } catch (Exception e){
            //Throwing a runtime as we use this method to get the type name only for the ui purpose..
            throw new RuntimeException(e);
        }
    }


}
