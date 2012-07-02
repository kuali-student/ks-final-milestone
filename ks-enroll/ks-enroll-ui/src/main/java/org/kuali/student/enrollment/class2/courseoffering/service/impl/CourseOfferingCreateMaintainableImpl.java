package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.FormatOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.state.service.StateService;
import org.kuali.student.r2.core.type.service.TypeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CourseOfferingCreateMaintainableImpl extends MaintainableImpl {

    private transient CourseOfferingService courseOfferingService;
    private ContextInfo contextInfo;
    private transient TypeService typeService;
    private transient StateService stateService;
    private transient CourseService courseService;

    @Override
    public void saveDataObject() {
        if(getMaintenanceAction().equals(KRADConstants.MAINTENANCE_NEW_ACTION) ||
                getMaintenanceAction().equals(KRADConstants.MAINTENANCE_COPY_ACTION)) {
            try {
                CourseOfferingCreateWrapper wrapper = (CourseOfferingCreateWrapper)getDataObject();
                CourseOfferingInfo courseOffering = new CourseOfferingInfo();
                CourseInfo courseInfo = wrapper.getCourse();
                courseOffering.setTermId(wrapper.getTerm().getId());
                courseOffering.setCourseOfferingTitle(courseInfo.getCourseTitle());
//                  courseOffering.setCreditOptionId();
                courseOffering.setCourseNumberSuffix(wrapper.getCourseCodeSuffix());
                courseOffering.setCourseId(courseInfo.getId());
                courseOffering.setCourseCode(courseInfo.getCode());
                courseOffering.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
                courseOffering.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
                CourseOfferingInfo info = getCourseOfferingService().createCourseOffering(courseInfo.getId(),wrapper.getTerm().getId(),LuiServiceConstants.COURSE_OFFERING_TYPE_KEY,courseOffering,new ArrayList<String>(),getContextInfo());
                wrapper.setCoInfo(info);
                createFormatOffering(wrapper);
                //FIXEM:create formatoffering relation
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void createFormatOffering(CourseOfferingCreateWrapper wrapper){
        List<FormatOfferingWrapper> updatedFOs = new ArrayList();
        for (FormatOfferingWrapper foWrapper : wrapper.getFormatOfferingList()) {
            foWrapper.getFormatOfferingInfo().setStateKey(LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY);
            foWrapper.getFormatOfferingInfo().setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
            foWrapper.getFormatOfferingInfo().setTermId(wrapper.getCoInfo().getTermId());
            foWrapper.getFormatOfferingInfo().setCourseOfferingId(wrapper.getCoInfo().getId());
            try {
                FormatOfferingInfo createdFormatOffering = getCourseOfferingService().createFormatOffering(wrapper.getCoInfo().getId(), foWrapper.getFormatOfferingInfo().getFormatId(), foWrapper.getFormatOfferingInfo().getTypeKey(), foWrapper.getFormatOfferingInfo(), getContextInfo());
                updatedFOs.add(new FormatOfferingWrapper(createdFormatOffering));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        wrapper.setFormatOfferingList(updatedFOs);
    }

    @Override
    protected void processBeforeAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
        if (addLine instanceof FormatOfferingWrapper){
            FormatOfferingWrapper formatOffering = (FormatOfferingWrapper)addLine;
            CourseOfferingCreateWrapper coCreateWrapper = (CourseOfferingCreateWrapper)((MaintenanceForm)model).getDocument().getNewMaintainableObject().getDataObject();
            for( FormatInfo formatInfo : coCreateWrapper.getCourse().getFormats()){
                if (StringUtils.equals(formatInfo.getId(),formatOffering.getFormatOfferingInfo().getFormatId())){
                    formatOffering.setFormatType(formatInfo.getType());
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

    protected CourseService getCourseService() {
        if(courseService == null) {
            courseService = CourseOfferingResourceLoader.loadCourseService();
        }
        return this.courseService;
    }

}
