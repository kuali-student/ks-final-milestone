/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import net.sf.ehcache.Element;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.krad.maintenance.Maintainable;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.common.uif.util.GrowlIcon;
import org.kuali.student.common.uif.util.KSUifUtils;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.FormatOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.JointCourseWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingViewHelperUtil;
import org.kuali.student.enrollment.class2.examoffering.service.facade.ExamOfferingResult;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.infc.SearchResultCell;
import org.kuali.student.r2.core.search.infc.SearchResultRow;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.CourseJointInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * View helper service to deal with all the create course offering presentation.
 *
 * @see org.kuali.student.enrollment.class2.courseoffering.controller.CourseOfferingCreateController
 */
public class CourseOfferingCreateMaintainableImpl extends CourseOfferingMaintainableImpl implements Maintainable {

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(CourseOfferingCreateMaintainableImpl.class);
    private final static String CACHE_NAME = "CourseOfferingMaintainableImplCache";

    /**
     * Sets a default maintenace document description and if term code exists in the request parameter, set it to the wrapper.
     * When 'Create CO' is called from manage co screen, we pass in the term code.
     *
     */
    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        document.getDocumentHeader().setDocumentDescription("Course Offering");
        if (requestParameters.get("targetTermCode") != null && requestParameters.get("targetTermCode").length != 0){
            ((CourseOfferingCreateWrapper)document.getNewMaintainableObject().getDataObject()).setTargetTermCode(requestParameters.get("targetTermCode")[0]);
        }
    }

    /**
     * Overrides from KRAD to handle saving the DTOs
     *
     */
    @Override
    public void saveDataObject() {

        if(getMaintenanceAction().equals(KRADConstants.MAINTENANCE_NEW_ACTION) ||
                getMaintenanceAction().equals(KRADConstants.MAINTENANCE_COPY_ACTION)) {

            try {
                CourseOfferingCreateWrapper wrapper = (CourseOfferingCreateWrapper)getDataObject();

                CourseOfferingInfo coInfo = new CourseOfferingInfo();
                loadCrossListedCOs(wrapper,coInfo);

                CourseOfferingInfo createdCOInfo = createCourseOfferingInfo(wrapper.getTerm().getId(), wrapper.getCourse(), wrapper.getCourseOfferingSuffix(),coInfo);
                wrapper.setCourseOfferingInfo(createdCOInfo);

                createFormatOfferings(wrapper);

                createJointCOs(wrapper);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     *
     * This method creates the CourseOffering for a course and for a specific term. This is being called
     * for the regular CO as well as for the Joint offerings
     *
     * @param termId courseing offering created for this term
     * @param courseInfo this is the course info for which this mehtod is going to create course offering
     * @param courseOfferingSuffix the suffix entered by the user
     * @return created course offering
     * @throws Exception throws any of the services exception from the service call
     */
    protected CourseOfferingInfo createCourseOfferingInfo(String termId, CourseInfo courseInfo, String courseOfferingSuffix,CourseOfferingInfo courseOffering) throws Exception {

        List<String> optionKeys = CourseOfferingManagementUtil.getDefaultOptionKeysService().getDefaultOptionKeysForCreateCourseOfferingFromCanonical();

        courseOffering.setTermId(termId);
        courseOffering.setCourseOfferingTitle(courseInfo.getCourseTitle());
//                  courseOffering.setCreditOptionId();

        // if the course offering wrapper suffix is set, set the value in the CourseOfferingInfo
        if (!StringUtils.isEmpty(courseOfferingSuffix)) {
            courseOffering.setCourseOfferingCode(StringUtils.upperCase(courseOfferingSuffix));
            courseOffering.setCourseNumberSuffix(StringUtils.upperCase(courseOfferingSuffix));
            optionKeys.add(CourseOfferingServiceConstants.APPEND_COURSE_OFFERING_CODE_SUFFIX_OPTION_KEY);
        }
        courseOffering.setCourseId(courseInfo.getId());
        courseOffering.setCourseCode(courseInfo.getCode());
        courseOffering.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        courseOffering.setStateKey(LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY);

        //Copy grading and credit options
        if(!courseInfo.getCreditOptions().isEmpty()){
            courseOffering.setCreditOptionId(courseInfo.getCreditOptions().get(0).getKey());
        }
        //Remove these two special student registration options and set them on the CO
        List<String> courseGradingOptions = new ArrayList<String>(courseInfo.getGradingOptions());
        if(courseGradingOptions.remove(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL) ){
            courseOffering.getStudentRegistrationGradingOptions().add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL);
        }
        if(courseGradingOptions.remove(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT) ){
            courseOffering.getStudentRegistrationGradingOptions().add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT);
        }
        //set the first remaining grading option on the CO
        if(!courseGradingOptions.isEmpty()){
            int firstGradingOption = 0;
            courseOffering.setGradingOptionId(courseGradingOptions.get(firstGradingOption));
        }

        // make sure we set attribute information from the course
        if(!courseInfo.getAttributes().isEmpty()){
            for(AttributeInfo info: courseInfo.getAttributes()){
                // Default the CourseOffering Final Exam Type to the Final Exam type in the Course
                if(info.getKey().equals("finalExamStatus")){
                    courseOffering.setFinalExamType(convertCourseFinalExamTypeToCourseOfferingFinalExamType(info.getValue()));
                }
            }
        }

        CourseOfferingInfo info = CourseOfferingManagementUtil.getCourseOfferingService().createCourseOffering(courseInfo.getId(), termId, LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, courseOffering, optionKeys, ContextUtils.createDefaultContextInfo());

        try {
            String examPeriodID = CourseOfferingManagementUtil.getExamOfferingServiceFacade().getExamPeriodId(info.getTermId(), ContextUtils.createDefaultContextInfo());
            CourseOfferingManagementUtil.getExamOfferingServiceFacade().generateFinalExamOffering(info, info.getTermId(), examPeriodID, new ArrayList<String>(), ContextUtils.createDefaultContextInfo());
        }  catch (Exception e){
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.ERROR, CourseOfferingConstants.COURSEOFFERING_EXAMPERIOD_MISSING);
        }

        return info;
    }

    /**
     * Services needs to come up with a standard way to represent final exams.
     * @param courseFinalExamType course final exam type
     * @return CO final exam type
     */
    protected static String convertCourseFinalExamTypeToCourseOfferingFinalExamType(String courseFinalExamType){
        String sRet = null;
        if("STD".equals(courseFinalExamType))   {
            sRet = CourseOfferingConstants.COURSEOFFERING_FINAL_EXAM_TYPE_STANDARD;
        } else if("ALT".equals(courseFinalExamType)) {
            sRet = CourseOfferingConstants.COURSEOFFERING_FINAL_EXAM_TYPE_ALTERNATE;
        }
        return sRet;
    }

    /**
     * This method creates format offerings for all the format selected by the user.
     * As the <code>CourseOfferingCreateWrapper.formatOfferingWrappers</code> list holds all the formats for the joint courses
     * as well (just to display the list at the ui), this method skips those formats here.
     *
     * @param wrapper CourseOfferingCreateWrapper
     */
    protected void createFormatOfferings(CourseOfferingCreateWrapper wrapper){
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        for (FormatOfferingWrapper foWrapper : wrapper.getFormatOfferingWrappers()) {
            if (!foWrapper.isJointOffering()){
                foWrapper.getFormatOfferingInfo().setStateKey(LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY);
                foWrapper.getFormatOfferingInfo().setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
                foWrapper.getFormatOfferingInfo().setTermId(wrapper.getCourseOfferingInfo().getTermId());
                foWrapper.getFormatOfferingInfo().setCourseOfferingId(wrapper.getCourseOfferingInfo().getId());
                try {
                    // KSENROLL-6071
                    CourseOfferingViewHelperUtil.addActivityOfferingTypesToFormatOffering(foWrapper.getFormatOfferingInfo(), wrapper.getCourse(), CourseOfferingManagementUtil.getTypeService(), contextInfo);
                    FormatOfferingInfo createdFormatOffering = CourseOfferingManagementUtil.getCourseOfferingService().createFormatOffering(wrapper.getCourseOfferingInfo().getId(), foWrapper.getFormatId(), foWrapper.getFormatOfferingInfo().getTypeKey(), foWrapper.getFormatOfferingInfo(), contextInfo);

                    foWrapper.setFormatOfferingInfo(createdFormatOffering);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * This method creates all the Course Offerings for joint courses.
     *
     * @param wrapper CourseOfferingCreateWrapper
     * @throws Exception
     */
    protected void createJointCOs(CourseOfferingCreateWrapper wrapper) throws Exception {
        LOG.debug("Creating Offerings for the joint courses.");
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
         for (JointCourseWrapper jointWrapper : wrapper.getJointCourses()){
             if (jointWrapper.isSelectedToJointlyOfferred()){
                 LOG.debug("Creating offerings for the joint course " + jointWrapper.getCourseCode());
                  CourseOfferingInfo coInfo = createCourseOfferingInfo(wrapper.getTerm().getId(), jointWrapper.getCourseInfo(), StringUtils.EMPTY, new CourseOfferingInfo());
                  for (FormatOfferingWrapper foWrapper : jointWrapper.getFormatOfferingWrappers()){
                      foWrapper.getFormatOfferingInfo().setStateKey(LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY);
                      foWrapper.getFormatOfferingInfo().setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
                      foWrapper.getFormatOfferingInfo().setTermId(wrapper.getCourseOfferingInfo().getTermId());
                      foWrapper.getFormatOfferingInfo().setCourseOfferingId(coInfo.getId());
                      try {
                          CourseOfferingViewHelperUtil.addActivityOfferingTypesToFormatOffering(foWrapper.getFormatOfferingInfo(), jointWrapper.getCourseInfo(), CourseOfferingManagementUtil.getTypeService(), contextInfo);
                          FormatOfferingInfo createdFormatOffering = CourseOfferingManagementUtil.getCourseOfferingService().createFormatOffering(coInfo.getId(), foWrapper.getFormatOfferingInfo().getFormatId(), foWrapper.getFormatOfferingInfo().getTypeKey(), foWrapper.getFormatOfferingInfo(), contextInfo);
                          foWrapper.setFormatOfferingInfo(createdFormatOffering);
                      } catch (Exception e) {
                          throw new RuntimeException(e);
                      }
                  }
             }
         }
    }


    /**
     * This method loads the wrapper details for the joint courses
     *
     * @param wrapper CourseOfferingCreateWrapper
     * @throws Exception throws one of the services exceptions
     */
    public void loadCourseJointInfos(CourseOfferingCreateWrapper wrapper, String viewId)
    throws Exception {

        List<CourseJointInfo> joints = wrapper.getCourse().getJoints();
        wrapper.setShowJointOption(!joints.isEmpty());
        wrapper.setJointCourseCodes(wrapper.getCourse().getCode());

        Map<String,String> permissionDetails = new HashMap<String,String>();
        Map<String,String> roleQualifications = new HashMap<String,String>();
        String principalId = GlobalVariables.getUserSession().getPerson().getPrincipalId();

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        for (CourseJointInfo joint : joints) {

            JointCourseWrapper jointCourseWrapper = new JointCourseWrapper();
            CourseInfo jointCourse = CourseOfferingManagementUtil.getCourseService().getCourse(joint.getCourseId(),contextInfo);
            jointCourseWrapper.setCourseJointInfo(joint);
            jointCourseWrapper.setCourseInfo(jointCourse);

            List<CourseOfferingInfo> cos = CourseOfferingManagementUtil.getCourseOfferingService().getCourseOfferingsByCourseAndTerm(joint.getCourseId(),wrapper.getTerm().getId(),contextInfo);

            if (!cos.isEmpty()){
                LOG.debug("For the joint course " + jointCourse.getCode() + ", it already has the offerings created.");
                jointCourseWrapper.setAlreadyOffered(true);
            }

            for (CourseOfferingInfo co : cos) {
                List<FormatOfferingInfo> formatOfferings = CourseOfferingManagementUtil.getCourseOfferingService().getFormatOfferingsByCourseOffering(co.getId(),contextInfo);
                for (FormatOfferingInfo formatOffering : formatOfferings) {
                    FormatOfferingWrapper foWrapper = new FormatOfferingWrapper();
                    foWrapper.setCourseCode(co.getCourseOfferingCode());
                    foWrapper.setFormatOfferingInfo(formatOffering);
                    CourseInfo courseInfo = CourseOfferingManagementUtil.getCourseService().getCourse(co.getCourseId(),contextInfo);
                    foWrapper.setFormatInfo(getFormatInfo(courseInfo,formatOffering.getFormatId()));
                    foWrapper.setActivitesUI(getActivityTypeNames(foWrapper.getFormatInfo()));
                    foWrapper.setGradeRosterUI(super.getTypeName(formatOffering.getGradeRosterLevelTypeKey()));
                    foWrapper.setFinalExamUI(super.getTypeName(formatOffering.getFinalExamLevelTypeKey()));
                    wrapper.getCopyFromFormats().add(foWrapper);
                }
            }

            // Check authz
            List<String> orgIds = jointCourse.getUnitsContentOwner();
            if(orgIds != null && !orgIds.isEmpty()){
                StringBuffer orgIDBuffer = new StringBuffer("");
                for (String orgId : orgIds) {
                    orgIDBuffer.append(orgId);
                    orgIDBuffer.append(",");
                }
                String orgIDs = orgIDBuffer.toString();

                if (orgIDs.length() > 0) {
                    roleQualifications.put("offeringAdminOrgId", orgIDs.substring(0, orgIDs.length() - 1));
                }
            }
            roleQualifications.put(PermissionServiceConstants.SUBJECT_AREA_ATTR_DEFINITION, jointCourse.getSubjectArea());
            permissionDetails.put(KimConstants.AttributeConstants.VIEW_ID, viewId);
            permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "createNewCO");
            jointCourseWrapper.setEnableCreateNewCOActionLink(false);
            if(CourseOfferingManagementUtil.getPermissionService().isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)){
                jointCourseWrapper.setEnableCreateNewCOActionLink(true);
            }

            wrapper.getJointCourses().add(jointCourseWrapper);
        }

    }

    /**
     * Adds a format offering. This handles creating format offerings for all the selected joint courses as well.
     */
    public void addFormatOffering(CourseOfferingCreateWrapper wrapper){

        FormatOfferingWrapper addLine = wrapper.getAddLineFormatWrapper();

        FormatInfo formatToBeAdded = getFormatInfo(wrapper.getCourse(), addLine.getFormatId());

        for (JointCourseWrapper joint : wrapper.getJointCourses()){
            if (joint.isSelectedToJointlyOfferred()){
                FormatOfferingWrapper foForJoint = new FormatOfferingWrapper();
                foForJoint.setJointOffering(true);
                foForJoint.setJointCreateWrapper(joint);
                foForJoint.setCourseCode(joint.getCourseCode());
                foForJoint.setFinalExamLevelTypeKey(addLine.getFinalExamLevelTypeKey());
                foForJoint.setGradeRosterLevelTypeKey(addLine.getGradeRosterLevelTypeKey());
                foForJoint.setGradeRosterUI(getTypeName(addLine.getGradeRosterLevelTypeKey()));
                foForJoint.setFinalExamUI(getTypeName(addLine.getFinalExamLevelTypeKey()));

                //Look for a matching format at the joint course
                FormatInfo formatInfo = getMatchingFormatInfo(joint.getCourseInfo(), formatToBeAdded);
                if (formatInfo == null){
                    GlobalVariables.getMessageMap().putInfo("KS-Catalog-FormatOfferingSubSection-New", RiceKeyConstants.ERROR_CUSTOM,"There is no matching format to be added for joint course " + joint.getCourseCode());
                } else {
                    foForJoint.setFormatInfo(formatInfo);
                    foForJoint.setFormatId(formatInfo.getId());
                    foForJoint.setActivitesUI(getActivityTypeNames(formatInfo));

                    wrapper.getFormatOfferingWrappers().add(0,foForJoint);
                    joint.getFormatOfferingWrappers().add(foForJoint);
                }
            }
        }

        FormatInfo formatInfo = getFormatInfo(wrapper.getCourse(), addLine.getFormatId());
        addLine.setActivitesUI(getActivityTypeNames(formatInfo));
        addLine.setFormatInfo(formatInfo);
        addLine.setCourseCode(wrapper.getCourse().getCode());
        wrapper.getFormatOfferingWrappers().add(0,addLine);
        wrapper.setAddLineFormatWrapper(new FormatOfferingWrapper());
    }

    /**
     * This method copies all the selected joint format offerings and creates format offering for the
     * main course as well as for the selected joint courses.
     *
     * @param wrapper course offering wrapper
     */
    public void copyJointFormatOfferings(CourseOfferingCreateWrapper wrapper){

        //Iterate all the joint formats and look for the selected format to copy
         for(FormatOfferingWrapper foWrapper : wrapper.getCopyFromFormats()){

             if (foWrapper.isSelectedToCopy()){
                 //For a joint format, find a matching format from the course
                 FormatInfo matchedFormat = getMatchingFormatInfo(wrapper.getCourse(),foWrapper.getFormatInfo());
                 boolean shouldCreateFO = true;

                 if (matchedFormat != null){
                     //If match found, make sure FOs doesnt exists already for that format
                     for (FormatOfferingWrapper existingFormat : wrapper.getFormatOfferingWrappers()){
                         if (StringUtils.equals(existingFormat.getFormatId(),matchedFormat.getId())){
                             shouldCreateFO = false;
                             GlobalVariables.getMessageMap().putError("KS-Catalog-FormatOfferingSubSection-New", RiceKeyConstants.ERROR_CUSTOM,"Already selected format exists for the course " + wrapper.getCourse().getCode());
                             break;
                         }
                     }
                 }

                 if (shouldCreateFO){
                      FormatOfferingWrapper newFormatOffering = new FormatOfferingWrapper(matchedFormat,wrapper.getCourse().getCode(),null);
                      newFormatOffering.setGradeRosterLevelTypeKey(foWrapper.getGradeRosterLevelTypeKey());
                      newFormatOffering.setFinalExamLevelTypeKey(foWrapper.getFinalExamLevelTypeKey());
                      //As the formats are same, activities must be same.. To avoid service calls, just copy the activity types from joint format
                      newFormatOffering.setActivitesUI(foWrapper.getActivitesUI());
                      wrapper.getFormatOfferingWrappers().add(0,newFormatOffering);
                 }

                 //Iterate all the selected joint course and create a format for that as well.
                 for (JointCourseWrapper jointWrapper : wrapper.getJointCourses()){
                     if (jointWrapper.isSelectedToJointlyOfferred()){
                        //For a joint format, find a matching format for the course
                         matchedFormat = getMatchingFormatInfo(jointWrapper.getCourseInfo(),foWrapper.getFormatInfo());

                         shouldCreateFO = true;

                         if (matchedFormat != null){
                             for (FormatOfferingWrapper existingFormat : wrapper.getFormatOfferingWrappers()){
                                  if (StringUtils.equals(existingFormat.getFormatId(),matchedFormat.getId())){
                                      shouldCreateFO = false;
                                      GlobalVariables.getMessageMap().putError("KS-Catalog-FormatOfferingSubSection-New", RiceKeyConstants.ERROR_CUSTOM,"Already selected format exists for the joint course " + jointWrapper.getCourseInfo().getCode());
                                      break;
                                  }
                              }
                             if (shouldCreateFO){
                                 FormatOfferingWrapper newFormatOffering = new FormatOfferingWrapper(matchedFormat,jointWrapper.getCourseCode(),jointWrapper);
                                   newFormatOffering.setGradeRosterLevelTypeKey(foWrapper.getGradeRosterLevelTypeKey());
                                   newFormatOffering.setFinalExamLevelTypeKey(foWrapper.getFinalExamLevelTypeKey());
                                   //As the formats are same, activities must be same.. To avoid service calls, just copy the activity types from joint format
                                   newFormatOffering.setActivitesUI(foWrapper.getActivitesUI());
                                   jointWrapper.getFormatOfferingWrappers().add(newFormatOffering);
                                   wrapper.getFormatOfferingWrappers().add(0,newFormatOffering);
                             }
                         }
                     }
                 }
             }
         }
    }

    /**
     * Helper method to display a list of Activity type names at the UI
     *
     * @param formatInfo format
     * @return activity type names
     */
    private String getActivityTypeNames(FormatInfo formatInfo){

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        //JIRA FIX : KSENROLL-8731 - Replaced StringBuffer with StringBuilder
        StringBuilder activities = new StringBuilder();

        try {
            List<ActivityInfo> activityInfos = formatInfo.getActivities();
            for (ActivityInfo activityInfo : activityInfos) {
                TypeInfo activityType = CourseOfferingManagementUtil.getTypeService().getType(activityInfo.getTypeKey(), contextInfo);
                activities.append(activityType.getName() + "/");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return StringUtils.removeEnd(activities.toString(),"/");
    }

    /**
     * This method returns a matching FormatInfo for a given id from a course info.
     *
     * @param courseInfo course info to look for a matching format
     * @param formatId format id to match
     * @return formatInfo
     */
    private FormatInfo getFormatInfo(CourseInfo courseInfo, String formatId){
        for (FormatInfo formatInfo : courseInfo.getFormats()){
            if (StringUtils.equals(formatInfo.getId(),formatId)){
                return formatInfo;
            }
        }
        return null;
    }

    /**
     * This is overridden from KRAD to implement deleting the format from the joint courses. It should not be a problem
     * overriding this method completely as we dont need any validation or pre/post event for this action
     */
    @Override
    public void processCollectionDeleteLine(View view, Object model, String collectionPath, int lineIndex){
        Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(model, collectionPath);
        FormatOfferingWrapper deleteLine = (FormatOfferingWrapper)((List<Object>) collection).get(lineIndex);
        if (deleteLine.isJointOffering()){
            deleteLine.getJointCreateWrapper().getFormatOfferingWrappers().remove(deleteLine);
        }
        ((List<Object>) collection).remove(lineIndex);
    }

    /**
     * This method iterates all the <code>FormatInfo</code> in a <code>CourseInfo</code> to match with the passed it <code>FormatInfo</code>.
     *
     * <p>
     *     There is no id match here. It simply iterates all the formats and match the activities count and type. If all the
     *     activity types matches, it returns that <code>FormatInfo</code>
     * </p>
     *
     * @param courseInfoToSearch course info to search for a matching format
     * @param formatInfoToMatchWith format info to match with
     *
     * @return formatInfo from joint course which matches the format being added by the user at the ui.
     */
    public FormatInfo getMatchingFormatInfo(CourseInfo courseInfoToSearch, FormatInfo formatInfoToMatchWith){
        for (FormatInfo searchFormat : courseInfoToSearch.getFormats()){
            if (StringUtils.equalsIgnoreCase(formatInfoToMatchWith.getTypeKey(),searchFormat.getTypeKey())){
                if (!formatInfoToMatchWith.getActivities().isEmpty() && formatInfoToMatchWith.getActivities().size() == searchFormat.getActivities().size()){
                    boolean isMatchFound = true;
                    for (ActivityInfo activityInfo : formatInfoToMatchWith.getActivities()){
                        if (!isMatchingJointActivityFound(searchFormat,activityInfo)){
                            isMatchFound = false;
                            break;
                        }
                    }
                    if (isMatchFound){
                        return searchFormat;
                    }
                }
            }
        }
        return null;
    }

    /**
     * This method checks whether any one of the activities in a format matches with the passed in
     * activity by comparing its type.
     *
     */
    private boolean isMatchingJointActivityFound(FormatInfo jointCourseFormat, ActivityInfo activityInfo){
        for (ActivityInfo activityFromJointCourse : jointCourseFormat.getActivities()){
            if (!StringUtils.equals(activityInfo.getTypeKey(),activityFromJointCourse.getTypeKey())){
                return false;
            }
        }
        return true;
    }

    /**
     * The premise of this is rather simple. Return a distinct list of course code. At a minimum there needs to
     * be one character. It then does a char% search. so E% will return all ENGL or any E* codes.
     *
     * This implementation is a little special. It's both cached and recursive.
     *
     * Because this is a structured search and course codes don't update often we can cache this pretty heavily and make
     * some assumptions that allow us to make this very efficient.
     *
     * So a user wants to type and see the type ahead results very quickly. The server wants as few db calls as possible.
     * The "bad" way to do this is to search on Every character entered. If we cache the searches then we'll get much
     * better performance. But we can go one step further because ths is a structured search. The first letter E in
     * ENGL will return EVERY course that starts with an E. So when you search for EN... why would you call the DB if
     * you have already called a search for E. So this uses recursion to build the searches. So, in the average case
     * you will only have to call a db search Once for Every first letter of the course codes.
     *
     * @return List of distinct course codes or an empty list
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    public List<String> retrieveCourseCodes(String targetTermCode, String catalogCourseCode) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {

        List<String> results = new ArrayList<String>();

        if(catalogCourseCode == null || catalogCourseCode.isEmpty())   return results;   // if nothing passed in, return empty list

        catalogCourseCode = catalogCourseCode.toUpperCase(); // force toUpper

        MultiKey cacheKey = new MultiKey(targetTermCode+"retrieveCourseCodes", catalogCourseCode);

        // only one character. This is the base search.
        if(catalogCourseCode.length() == 1){
            Element cachedResult = CourseOfferingManagementUtil.getCacheManager().getCache(CACHE_NAME).get(cacheKey);

            Object result;
            if (cachedResult == null) {
                result = _retrieveCourseCodes(targetTermCode, catalogCourseCode);
                CourseOfferingManagementUtil.getCacheManager().getCache(CACHE_NAME).put(new Element(cacheKey, result));
                results = (List<String>)result;
            } else {
                results = (List<String>)cachedResult.getValue();
            }
        }else{
            Element cachedResult = CourseOfferingManagementUtil.getCacheManager().getCache(CACHE_NAME).get(cacheKey);

            if (cachedResult == null) {
                // This is where the recursion happens. If you entered CHEM and it didn't find anything it will
                // recurse and search for CHE -> CH -> C (C is the base). Each time building up the cache.
                // This for loop is the worst part of this method. I'd love to use some logic to remove the for loop.
                for(String courseCode : retrieveCourseCodes(targetTermCode, catalogCourseCode.substring(0,catalogCourseCode.length()-1))){
                    // for every course code, see if it's part of the Match.
                    if(courseCode.startsWith(catalogCourseCode)){
                        results.add(courseCode);
                    }
                }

                CourseOfferingManagementUtil.getCacheManager().getCache(CACHE_NAME).put(new Element(cacheKey, results));
            } else {
                results = (List<String>)cachedResult.getValue();
            }
        }

        return results;
    }

    /**
     * Does a search Query for course codes used for auto suggest
     * @param catalogCourseCode the starting characters of a course code
     * @return a list of CourseCodeSuggestResults containing matching course codes
     */
    private List<String> _retrieveCourseCodes(String targetTermCode, String catalogCourseCode) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {

        List<String> rList = new ArrayList<String>();
        Set<String> rSet = new LinkedHashSet<String>(rList);
        ContextInfo context = ContextUtils.createDefaultContextInfo();

        //First get ATP information
        List<AtpInfo> atps = CourseOfferingManagementUtil.getAtpService().getAtpsByCode(targetTermCode, context);
        if(atps == null || atps.size() != 1){
            return rList;
        }

        //Then do the search
        SearchRequestInfo request = new SearchRequestInfo("lu.search.courseCodes");
        request.addParam(CourseInfoByTermLookupableImpl.QueryParamEnum.CODE.getQueryKey(), catalogCourseCode);
        request.addParam("lu.queryParam.luOptionalType", CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY);
        request.addParam("lu.queryParam.luOptionalState", Arrays.asList(new String[]{
                DtoConstants.STATE_ACTIVE, DtoConstants.STATE_RETIRED, DtoConstants.STATE_SUPERSEDED}));
        request.addParam(CourseInfoByTermLookupableImpl.QueryParamEnum.TERM_START.getQueryKey(), DateFormatters.QUERY_SERVICE_TIMESTAMP_FORMATTER.format(atps.get(0).getStartDate()));
        request.addParam(CourseInfoByTermLookupableImpl.QueryParamEnum.TERM_END.getQueryKey(), DateFormatters.QUERY_SERVICE_TIMESTAMP_FORMATTER.format(atps.get(0).getEndDate()));
        request.setSortColumn("lu.resultColumn.cluOfficialIdentifier.cluCode");

        SearchResultInfo results = CourseOfferingManagementUtil.getCluService().search(request, context);
        for(SearchResultRow row:results.getRows()){
            for(SearchResultCell cell:row.getCells()){
                if("lu.resultColumn.cluOfficialIdentifier.cluCode".equals(cell.getKey())){
                    rSet.add(cell.getValue());
                }
            }
        }
        return new ArrayList<String>(rSet);
    }
}
