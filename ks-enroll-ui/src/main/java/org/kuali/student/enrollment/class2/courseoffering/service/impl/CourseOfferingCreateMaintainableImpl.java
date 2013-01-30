package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseJointCreateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.FormatOfferingCreateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingMaintainable;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingCrossListingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.CourseJointInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CourseOfferingCreateMaintainableImpl extends CourseOfferingMaintainableImpl implements CourseOfferingMaintainable {

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(CourseOfferingCreateMaintainableImpl.class);

    @Override
    public void saveDataObject() {

        if(getMaintenanceAction().equals(KRADConstants.MAINTENANCE_NEW_ACTION) ||
                getMaintenanceAction().equals(KRADConstants.MAINTENANCE_COPY_ACTION)) {

            try {
                CourseOfferingCreateWrapper wrapper = (CourseOfferingCreateWrapper)getDataObject();

                CourseOfferingInfo coInfo = new CourseOfferingInfo();
                loadCrossListedCOs(wrapper,coInfo);

                CourseOfferingInfo createdCOInfo = createCourseOfferingInfo(wrapper.getTerm().getId(), wrapper.getCourse(), wrapper.getCourseOfferingSuffix(),coInfo);
                wrapper.setCoInfo(createdCOInfo);

                createFormatOfferings(wrapper);

                createJointOfferings(wrapper);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void loadCrossListedCOs(CourseOfferingCreateWrapper wrapper, CourseOfferingInfo coInfo){
        if (wrapper.getCoListedCOs().size() > 0) {
            for (String CoId : wrapper.getCoListedCOs()) {
                for (CourseCrossListingInfo crossInfo : wrapper.getCourse().getCrossListings()) {
                    if (crossInfo.getId().equalsIgnoreCase(CoId)) {
                        CourseOfferingCrossListingInfo crossListingInfo = new CourseOfferingCrossListingInfo();
                        crossListingInfo.setCode(crossInfo.getCode());
                        crossListingInfo.setCourseNumberSuffix(crossInfo.getCourseNumberSuffix());
                        crossListingInfo.setDepartmentOrgId (crossInfo.getDepartment());
                        crossListingInfo.setSubjectArea (crossInfo.getSubjectArea());
                        crossListingInfo.setId(crossInfo.getId());
                        crossListingInfo.setStateKey(crossInfo.getStateKey());
                        crossListingInfo.setTypeKey(crossInfo.getTypeKey());
                        coInfo.getCrossListings().add(crossListingInfo);
                    }
                }
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
     * @return
     * @throws Exception throws any of the services exception from the service call
     */
    protected CourseOfferingInfo createCourseOfferingInfo(String termId, CourseInfo courseInfo, String courseOfferingSuffix,CourseOfferingInfo courseOffering) throws Exception {

        List<String> optionKeys = new ArrayList<String>();

        courseOffering.setTermId(termId);
        courseOffering.setCourseOfferingTitle(courseInfo.getCourseTitle());
//                  courseOffering.setCreditOptionId();

        // if the course offering wrapper suffix is set, set the value in the CourseOfferingInfo
        if (!StringUtils.isEmpty(courseOfferingSuffix)) {
            courseOffering.setCourseOfferingCode(courseOfferingSuffix);
            courseOffering.setCourseNumberSuffix(courseOfferingSuffix);
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
            courseOffering.setGradingOptionId(courseGradingOptions.get(0));
        }

        CourseOfferingInfo info = getCourseOfferingService().createCourseOffering(courseInfo.getId(), termId, LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, courseOffering, optionKeys, ContextUtils.createDefaultContextInfo());
        return info;
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
        for (FormatOfferingCreateWrapper foWrapper : wrapper.getFormatOfferingWrappers()) {
            if (!foWrapper.isJointOffering()){
                foWrapper.getFormatOfferingInfo().setStateKey(LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY);
                foWrapper.getFormatOfferingInfo().setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
                foWrapper.getFormatOfferingInfo().setTermId(wrapper.getCoInfo().getTermId());
                foWrapper.getFormatOfferingInfo().setCourseOfferingId(wrapper.getCoInfo().getId());
                try {
                    FormatOfferingInfo createdFormatOffering = getCourseOfferingService().createFormatOffering(wrapper.getCoInfo().getId(), foWrapper.getFormatOfferingInfo().getFormatId(), foWrapper.getFormatOfferingInfo().getTypeKey(), foWrapper.getFormatOfferingInfo(), contextInfo);
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
     * @throws Exception RuntimeException with cause as service exception
     */
    protected void createJointOfferings(CourseOfferingCreateWrapper wrapper) throws Exception {
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
         for (CourseJointCreateWrapper jointWrapper : wrapper.getJointCourses()){
              CourseOfferingInfo coInfo = createCourseOfferingInfo(wrapper.getTerm().getId(), jointWrapper.getCourseInfo(), StringUtils.EMPTY, new CourseOfferingInfo());
              for (FormatOfferingCreateWrapper foWrapper : jointWrapper.getFormatOfferingWrappers()){
                  foWrapper.getFormatOfferingInfo().setStateKey(LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY);
                  foWrapper.getFormatOfferingInfo().setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
                  foWrapper.getFormatOfferingInfo().setTermId(wrapper.getCoInfo().getTermId());
                  foWrapper.getFormatOfferingInfo().setCourseOfferingId(coInfo.getId());
                  try {
                      FormatOfferingInfo createdFormatOffering = getCourseOfferingService().createFormatOffering(coInfo.getId(), foWrapper.getFormatOfferingInfo().getFormatId(), foWrapper.getFormatOfferingInfo().getTypeKey(), foWrapper.getFormatOfferingInfo(), contextInfo);
                      foWrapper.setFormatOfferingInfo(createdFormatOffering);
                  } catch (Exception e) {
                      throw new RuntimeException(e);
                  }
              }

         }
    }

    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        document.getDocumentHeader().setDocumentDescription("Course Offering");
        if (requestParameters.get("targetTermCode") != null && requestParameters.get("targetTermCode").length != 0){
            ((CourseOfferingCreateWrapper)document.getNewMaintainableObject().getDataObject()).setTargetTermCode(requestParameters.get("targetTermCode")[0]);
        }
    }


    public void loadCourseJointInfos(CourseOfferingCreateWrapper wrapper)
    throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        List<CourseJointInfo> joints = wrapper.getCourse().getJoints();
        wrapper.setShowJointOption(!joints.isEmpty());
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        wrapper.setJointCourseCodes(wrapper.getCourse().getCode());

        for (CourseJointInfo joint : joints) {
            CourseJointCreateWrapper jointCreateWrapper = new CourseJointCreateWrapper();
            CourseInfo jointCourse = getCourseService().getCourse(joint.getCourseId(),contextInfo);
            jointCreateWrapper.setCourseJointInfo(joint);
            jointCreateWrapper.setCourseInfo(jointCourse);
            List<CourseOfferingInfo> cos = getCourseOfferingService().getCourseOfferingsByCourseAndTerm(joint.getCourseId(),wrapper.getTerm().getId(),contextInfo);
            if (!cos.isEmpty()){
                jointCreateWrapper.setAlreadyOffered(true);
            }
            wrapper.getJointCourses().add(jointCreateWrapper);
        }

    }

    @Override
    public void processCollectionAddLine(View view, Object model, String collectionPath) {
        // get the collection group from the view
        CollectionGroup collectionGroup = view.getViewIndex().getCollectionGroupByPath(collectionPath);
        if (collectionGroup == null) {
            logAndThrowRuntime("Unable to get collection group component for path: " + collectionPath);
        }

        // get the collection instance for adding the new line
        Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(model, collectionPath);
        if (collection == null) {
            logAndThrowRuntime("Unable to get collection property from model for path: " + collectionPath);
        }

        // now get the new line we need to add
        String addLinePath = collectionGroup.getAddLineBindingInfo().getBindingPath();
        FormatOfferingCreateWrapper addLine = (FormatOfferingCreateWrapper)ObjectPropertyUtils.getPropertyValue(model, addLinePath);

        FormatOfferingCreateWrapper formatOfferingWrapper = (FormatOfferingCreateWrapper)ObjectPropertyUtils.getPropertyValue(model, addLinePath);
        if (formatOfferingWrapper == null) {
            logAndThrowRuntime("Add line instance not found for path: " + addLinePath);
        }

        CourseOfferingCreateWrapper wrapper = (CourseOfferingCreateWrapper)((MaintenanceDocumentForm)model).getDocument().getNewMaintainableObject().getDataObject();
        for (CourseJointCreateWrapper joint : wrapper.getJointCourses()){
            if (joint.isSelectedToJointlyOfferred()){
                FormatOfferingCreateWrapper foForJoint = new FormatOfferingCreateWrapper();
                foForJoint.setJointOffering(true);
                foForJoint.setJointCreateWrapper(joint);
                foForJoint.setCourseCode(joint.getCourseCode());
                foForJoint.setFinalExamLevelTypeKey(formatOfferingWrapper.getFinalExamLevelTypeKey());
                foForJoint.setGradeRosterLevelTypeKey(formatOfferingWrapper.getGradeRosterLevelTypeKey());
                foForJoint.setFormatId(formatOfferingWrapper.getFormatId());
                foForJoint.setActivitesUI(getActivityDisplayText(joint.getCourseInfo(),addLine.getFormatId()));
                wrapper.getFormatOfferingWrappers().add(foForJoint);
            }
        }

        addLine.setActivitesUI(getActivityDisplayText(wrapper.getCourse(),addLine.getFormatId()));
        addLine.setCourseCode(wrapper.getCourse().getCode());
        wrapper.getFormatOfferingWrappers().add(addLine);
    }

    protected String getActivityDisplayText(CourseInfo courseInfo, String formatId){

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        StringBuffer activities = new StringBuffer();

        try {
            for (FormatInfo formatInfo : courseInfo.getFormats()){
                if (StringUtils.equals(formatInfo.getId(),formatId)){
                    List<ActivityInfo> activityInfos = formatInfo.getActivities();
                    for (ActivityInfo activityInfo : activityInfos) {
                        TypeInfo activityType = getTypeService().getType(activityInfo.getTypeKey(), contextInfo);
                        activities.append(activityType.getName() + "/");
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return StringUtils.removeEnd(activities.toString(),"/");
    }

    @Override
    protected void processAfterDeleteLine(View view, CollectionGroup collectionGroup, Object model, int lineIndex) {

    }

}
