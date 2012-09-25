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
 * Created by vgadiyak on 9/10/12
 */
package org.kuali.student.enrollment.class2.scheduleofclasses.controller;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.scheduleofclasses.dto.CourseOfferingDisplayWrapper;
import org.kuali.student.enrollment.class2.scheduleofclasses.form.ScheduleOfClassesSearchForm;
import org.kuali.student.enrollment.class2.scheduleofclasses.service.ScheduleOfClassesViewHelperService;
import org.kuali.student.enrollment.class2.scheduleofclasses.util.ScheduleOfClassesConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.AcademicCalendarServiceConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping(value = "/scheduleOfClassesSearch")
public class ScheduleOfClassesSearchController extends UifControllerBase {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ScheduleOfClassesSearchController.class);
    private ScheduleOfClassesViewHelperService viewHelperService;
    private AcademicCalendarService acalService;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new ScheduleOfClassesSearchForm();
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        ScheduleOfClassesSearchForm scheduleOfClassesSearchForm = (ScheduleOfClassesSearchForm)form;
        scheduleOfClassesSearchForm.setSearchType("course");

        return super.start(form, result, request, response);
    }

    /**
     * Method used to
     *  Search for course offerings based on search parameters: term and courseCode/Title&Desc/Instructor/Department
     */
    @RequestMapping(params = "methodToCall=show")
    public ModelAndView show(@ModelAttribute("KualiForm") ScheduleOfClassesSearchForm theForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        //First, find termName based on termCode
        String termCode = theForm.getTermCode();
        if (termCode != null && termCode.length() > 0) {
            String termName = getAcademicCalendarService().getTerm(termCode, ContextUtils.createDefaultContextInfo()).getName();
            theForm.setTermName(termName);
        } else{
            LOG.error("Error: term can't be empty");
            GlobalVariables.getMessageMap().putError("termCode", ScheduleOfClassesConstants.SOC_MSG_ERROR_TERM_IS_EMPTY);
            return getUIFModelAndView(theForm);
        }

        //Second, handle searchType
        if (theForm.getSearchType().equals("course")){
            String course = theForm.getCourse();
            if (course != null && course.length() > 0) {
                getViewHelperService(theForm).loadCourseOfferingsByTermAndCourseCode(termCode, course, theForm);
            } else {
                LOG.error("Error: search field can't be empty");
                GlobalVariables.getMessageMap().putError("course", ScheduleOfClassesConstants.SOC_MSG_ERROR_COURSE_IS_EMPTY);
                return getUIFModelAndView(theForm);
            }
        } else if (theForm.getSearchType().equals("instructor")){
            String instructorId = theForm.getInstructor();
            if (instructorId != null && !instructorId.isEmpty()) {
                getViewHelperService(theForm).loadCourseOfferingsByTermAndInstructor(termCode, instructorId, theForm);
            } else {
                LOG.error("Error: search field can't be empty");
                GlobalVariables.getMessageMap().putError("course", ScheduleOfClassesConstants.SOC_MSG_ERROR_COURSE_IS_EMPTY);
                return getUIFModelAndView(theForm);
            }
        } else if (theForm.getSearchType().equals("department")){
           String departmentId = theForm.getDepartment();
           String departmentName = theForm.getDepartmentName();
           if ((departmentId != null && !departmentId.isEmpty()) || (departmentName != null && !departmentName.isEmpty())){
               getViewHelperService(theForm).loadCourseOfferingsByTermAndDepartment(termCode, departmentId, departmentName, theForm);
           } else {
                LOG.error("Error: search field can't be empty");
                GlobalVariables.getMessageMap().putError("course", ScheduleOfClassesConstants.SOC_MSG_ERROR_COURSE_IS_EMPTY);
                return getUIFModelAndView(theForm);
           }

        }

        return getUIFModelAndView(theForm, ScheduleOfClassesConstants.SOC_RESULT_PAGE);
    }

    @RequestMapping(params = "methodToCall=populateAOs")
    public ModelAndView populateAOs(@ModelAttribute("KualiForm") ScheduleOfClassesSearchForm theForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        String courseOfferingId = theForm.getCourseOfferingId();

        if (courseOfferingId != null && courseOfferingId.length() > 0) {
            getViewHelperService(theForm).loadActivityOfferingsByCourseOfferingId(courseOfferingId, theForm);
        } else {
            LOG.error("Error: search field can't be empty");
            GlobalVariables.getMessageMap().putError("course", ScheduleOfClassesConstants.SOC_MSG_ERROR_COURSE_IS_EMPTY);
            return getUIFModelAndView(theForm);
        }

        return getUIFModelAndView(theForm, ScheduleOfClassesConstants.SOC_RESULT_PAGE);
    }

    public ScheduleOfClassesViewHelperService getViewHelperService(ScheduleOfClassesSearchForm theForm){
        if (viewHelperService == null) {
            if (theForm.getView().getViewHelperServiceClass() != null){
                viewHelperService = (ScheduleOfClassesViewHelperService) theForm.getView().getViewHelperService();
            }else{
                viewHelperService= (ScheduleOfClassesViewHelperService) theForm.getPostedView().getViewHelperService();
            }
        }
        return viewHelperService;
    }

    protected AcademicCalendarService getAcademicCalendarService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.acalService;
    }

}
