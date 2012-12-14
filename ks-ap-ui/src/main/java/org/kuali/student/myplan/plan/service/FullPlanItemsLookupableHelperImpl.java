package org.kuali.student.myplan.plan.service;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
//import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.myplan.course.util.CourseSearchConstants;
import org.kuali.student.myplan.plan.PlanConstants;
import org.kuali.student.myplan.plan.dataobject.*;
import org.kuali.student.myplan.plan.util.AtpHelper;
import org.kuali.student.myplan.utils.UserSessionHelper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: hemanthg
 * Date: 4/13/12
 * Time: 1:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class FullPlanItemsLookupableHelperImpl extends PlanItemLookupableHelperBase {

    private final Logger logger = Logger.getLogger(FullPlanItemsLookupableHelperImpl.class);

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

    @Override
    protected List<FullPlanItemsDataObject> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {

        /*Setting the Warning message if isServiceStatusOK is false*/
        boolean isServiceStatusOK=true;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (!Boolean.valueOf(request.getAttribute(CourseSearchConstants.IS_ACADEMIC_CALENDER_SERVICE_UP).toString())
                || !Boolean.valueOf(request.getAttribute(CourseSearchConstants.IS_ACADEMIC_RECORD_SERVICE_UP).toString())) {
            isServiceStatusOK=false;
            AtpHelper.addServiceError("qtrYear");
        }
        String studentId = UserSessionHelper.getStudentId();
        /*************PlannedCourseList**************/
        List<PlannedCourseDataObject> plannedCoursesList = new ArrayList<PlannedCourseDataObject>();
        try {
            plannedCoursesList = getPlanItems(PlanConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED, true, studentId);
        } catch (Exception e) {
            logger.error("Could not load plannedCourseslist", e);

        }
        /****academic record SWS call to get the studentCourseRecordInfo list *****/

        List<StudentCourseRecordInfo> studentCourseRecordInfos = new ArrayList<StudentCourseRecordInfo>();
        try {
            studentCourseRecordInfos = getAcademicRecordService().getCompletedCourseRecords(studentId, PlanConstants.CONTEXT_INFO);
        } catch (Exception e) {
            logger.error("Could not retrieve StudentCourseRecordInfo from the SWS.", e);
        }


        List<PlannedTerm> perfectPlannedTerms = PlannedTermsHelperBase.populatePlannedTerms(plannedCoursesList, null, studentCourseRecordInfos, null,isServiceStatusOK);

        List<FullPlanItemsDataObject> fullPlanItemsDataObjectList = new ArrayList<FullPlanItemsDataObject>();
        int size = perfectPlannedTerms.size();
        for (int i = 0; size>0 ; i++) {
            FullPlanItemsDataObject fullPlanItemsDataObject = new FullPlanItemsDataObject();
            List<PlannedTerm> plannedTermList = new ArrayList<PlannedTerm>();


            for (int j = 0; j < AtpHelper.TERM_COUNT; j++) {
                plannedTermList.add(perfectPlannedTerms.get(0));
                perfectPlannedTerms.remove(perfectPlannedTerms.get(0));
                size--;
            }

            String[] minYear = AtpHelper.atpIdToTermNameAndYear(plannedTermList.get(0).getAtpId());
            String[] maxYear = AtpHelper.atpIdToTermNameAndYear(plannedTermList.get(plannedTermList.size() - 1).getAtpId());
            StringBuffer yearRange = new StringBuffer();
            yearRange = yearRange.append(minYear[1]).append("-").append(maxYear[1]);
            fullPlanItemsDataObject.setYearRange(yearRange.toString());
            fullPlanItemsDataObject.setTerms(plannedTermList);
            fullPlanItemsDataObjectList.add(fullPlanItemsDataObject);
        }
        return fullPlanItemsDataObjectList;
    }


}
