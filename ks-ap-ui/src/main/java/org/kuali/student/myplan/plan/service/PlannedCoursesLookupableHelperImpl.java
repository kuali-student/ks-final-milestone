package org.kuali.student.myplan.plan.service;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
//import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.myplan.course.util.CourseSearchConstants;
import org.kuali.student.myplan.plan.PlanConstants;
import org.kuali.student.myplan.plan.dataobject.PlannedCourseDataObject;
import org.kuali.student.myplan.plan.dataobject.PlannedTerm;
import org.kuali.student.myplan.plan.util.AtpHelper;
import org.kuali.student.myplan.utils.UserSessionHelper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import java.util.*;
import java.util.List;

/**
 * Produce a list of planned course items.
 */
public class PlannedCoursesLookupableHelperImpl extends PlanItemLookupableHelperBase {

    private final Logger logger = Logger.getLogger(PlannedCoursesLookupableHelperImpl.class);

    private transient AcademicRecordService academicRecordService;

    public AcademicRecordService getAcademicRecordService() {
        if (this.academicRecordService == null) {
            //   TODO: Use constants for namespace.
            this.academicRecordService = (AcademicRecordService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/academicrecord", "arService"));
        }
        return this.academicRecordService;
    }

    public void setAcademicRecordService(AcademicRecordService academicRecordService) {
        this.academicRecordService = academicRecordService;
    }

    /**
     * Skip the validation so that we use the criteriaFields param to pass in args to the getSearchResults method.
     *
     * @param form
     * @param searchCriteria
     * @return
     */
    @Override
    public boolean validateSearchParameters(LookupForm form, Map<String, String> searchCriteria) {
        return true;
    }

    @Override
    protected List<PlannedTerm> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String focusAtpId =request.getParameter(PlanConstants.FOCUS_ATP_ID_KEY);
        String studentId = UserSessionHelper.getStudentId();
        boolean isServiceStatusOK=true;
        /*Setting the Warning message if isServiceStatusOK is false*/
        if (!Boolean.valueOf(request.getAttribute(CourseSearchConstants.IS_ACADEMIC_CALENDER_SERVICE_UP).toString())
                || !Boolean.valueOf(request.getAttribute(CourseSearchConstants.IS_ACADEMIC_RECORD_SERVICE_UP).toString())) {
            isServiceStatusOK=false;
           AtpHelper.addServiceError("qtrYear");
        }
        String[] params = {};
        if(!isServiceStatusOK){
        GlobalVariables.getMessageMap().putWarningForSectionId(PlanConstants.PLAN_ITEM_RESPONSE_PAGE_ID, PlanConstants.ERROR_TECHNICAL_PROBLEMS, params);
        }


        /*************PlannedCourseList**************/
        List<PlannedCourseDataObject> plannedCoursesList = new ArrayList<PlannedCourseDataObject>();
        if(isServiceStatusOK){
        try {
            plannedCoursesList = getPlanItems(PlanConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED, true, studentId);
        } catch (Exception e) {
            logger.error("Could not load plannedCourseslist", e);

        }
        }
        /****academic record SWS call to get the studentCourseRecordInfo list *****/
        List<StudentCourseRecordInfo> studentCourseRecordInfos = new ArrayList<StudentCourseRecordInfo>();
        if(isServiceStatusOK){
        try {
            studentCourseRecordInfos = getAcademicRecordService().getCompletedCourseRecords(studentId, PlanConstants.CONTEXT_INFO);
        } catch (Exception e) {
            GlobalVariables.getMessageMap().putWarningForSectionId(PlanConstants.PLAN_ITEM_RESPONSE_PAGE_ID, PlanConstants.ERROR_TECHNICAL_PROBLEMS, params);
            logger.error("Could not retrieve StudentCourseRecordInfo from the SWS.", e);
        }
        }

        /*************BackupCourseList**************/
        List<PlannedCourseDataObject> backupCoursesList = new ArrayList<PlannedCourseDataObject>();
        if(isServiceStatusOK){
        try {
            backupCoursesList=getPlanItems(PlanConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP, true, studentId);
        } catch (Exception e) {
            logger.error("Could not load backupCourseList", e);

        }
        }

        List<PlannedTerm> perfectPlannedTerms = PlannedTermsHelperBase.populatePlannedTerms(plannedCoursesList, backupCoursesList, studentCourseRecordInfos, focusAtpId,isServiceStatusOK);
        return perfectPlannedTerms;
    }
}
