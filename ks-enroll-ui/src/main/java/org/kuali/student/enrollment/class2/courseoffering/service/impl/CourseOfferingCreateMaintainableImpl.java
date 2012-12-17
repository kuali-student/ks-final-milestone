package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingMaintainable;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import java.util.ArrayList;
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
                CourseOfferingInfo courseOffering = new CourseOfferingInfo();
                List<String> optionKeys = new ArrayList<String>();

                CourseInfo courseInfo = wrapper.getCourse();
                courseOffering.setTermId(wrapper.getTerm().getId());
                courseOffering.setCourseOfferingTitle(courseInfo.getCourseTitle());
//                  courseOffering.setCreditOptionId();

                // if the course offering wrapper suffix is set, set the value in the CourseOfferingInfo
                if (!StringUtils.isEmpty(wrapper.getCourseOfferingSuffix())) {
                    courseOffering.setCourseOfferingCode(wrapper.getCourseOfferingSuffix());
                    courseOffering.setCourseNumberSuffix(wrapper.getCourseOfferingSuffix());
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

                CourseOfferingInfo info = getCourseOfferingService().createCourseOffering(courseInfo.getId(), wrapper.getTerm().getId(), LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, courseOffering, optionKeys, ContextUtils.createDefaultContextInfo());
                wrapper.setCoInfo(info);
                createFormatOffering(wrapper);
                //FIXEM:create formatoffering relation
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void createFormatOffering(CourseOfferingCreateWrapper wrapper){
        List<FormatOfferingInfo> updatedFOs = new ArrayList<FormatOfferingInfo>();
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        for (FormatOfferingInfo formatOfferingInfo : wrapper.getFormatOfferingList()) {
            formatOfferingInfo.setStateKey(LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY);
            formatOfferingInfo.setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
            formatOfferingInfo.setTermId(wrapper.getCoInfo().getTermId());
            formatOfferingInfo.setCourseOfferingId(wrapper.getCoInfo().getId());
            try {
                FormatOfferingInfo createdFormatOffering = getCourseOfferingService().createFormatOffering(wrapper.getCoInfo().getId(), formatOfferingInfo.getFormatId(), formatOfferingInfo.getTypeKey(), formatOfferingInfo, contextInfo);
                updatedFOs.add(createdFormatOffering);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        wrapper.setFormatOfferingList(updatedFOs);
    }

    @Override
    protected void processBeforeAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
        if (addLine instanceof FormatOfferingInfo) {
            FormatOfferingInfo formatOfferingInfo = (FormatOfferingInfo) addLine;
            CourseOfferingCreateWrapper coCreateWrapper = (CourseOfferingCreateWrapper) ((MaintenanceDocumentForm) model).getDocument().getNewMaintainableObject().getDataObject();
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
            for (FormatInfo formatInfo : coCreateWrapper.getCourse().getFormats()) {
                if (StringUtils.equals(formatInfo.getId(), formatOfferingInfo.getFormatId())) {
                    // TODO: fix R2 Format to include name and short name
//                    formatOfferingInfo.setName("FIX ME!");
//                    formatOfferingInfo.setShortName("FIX ME!");
                    //Bonnie: this is only a temporary walk-around solution.
                    //Still need to address the issue that FormatInfo does not include name and short name
                    try {
                        List<ActivityInfo> activityInfos = formatInfo.getActivities();
                        StringBuffer st = new StringBuffer();
                        for (ActivityInfo activityInfo : activityInfos) {
                            TypeInfo activityType = getTypeService().getType(activityInfo.getTypeKey(), contextInfo);
                            st.append(activityType.getName() + "/");
                        }
                        String name = st.toString();
                        name = name.substring(0, name.length() - 1);
                        formatOfferingInfo.setName(name);
                        formatOfferingInfo.setShortName(name);
                    } catch (Exception e) {
                        //This was just swallowing the exception so a log entry was added
                        LOG.error("An exception was thrown!", e);
                    }

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



}
