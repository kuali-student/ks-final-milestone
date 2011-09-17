package org.kuali.student.enrollment.class2.grading.form;

/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.grading.dataobject.GradeStudent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

public class GradingForm extends UifFormBase{

    private static final long serialVersionUID = -1054046347823986329L;

    private String selectedCourse;
    private List<GradeStudent> students;

    private List<CourseOfferingInfo> courseOfferingInfoList;

    private AcademicCalendarService acalService;
    private CourseOfferingService coService;

    public GradingForm(){
        super();
    }

    @Override
    public void postBind(HttpServletRequest request) {
        super.postBind(request);
        loadCourseOfferings();
    }

    public String getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(String selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    public List<GradeStudent> getStudents() {
        return students;
    }

    public void setStudents(List<GradeStudent> students) {
        this.students = students;
    }

    public List<CourseOfferingInfo> getCourseOfferingInfoList() {
        return courseOfferingInfoList;
    }

    public void setCourseOfferingInfoList(List<CourseOfferingInfo> courseOfferingInfoList) {
        this.courseOfferingInfoList = courseOfferingInfoList;
    }

    private TermInfo getCurrentACal(){
        ContextInfo context = ContextInfo.newInstance();

        try{
                return getAcalService().getCurrentTerms(null,context).get(0);
        } catch (DoesNotExistException e) {
            throw new RuntimeException("No Terms found for current AcademicCalendar(s)! There should be some in the database.", e);
        } catch (InvalidParameterException e) {
            throw new RuntimeException(e);
        } catch (MissingParameterException e) {
            throw new RuntimeException(e);
        } catch (OperationFailedException e) {
            throw new RuntimeException(e);
        } catch (PermissionDeniedException e) {
            throw new RuntimeException(e);
        }

    }

    protected void loadCourseOfferings(){
        TermInfo term = getCurrentACal();
        if (term == null){
            throw new RuntimeException("No current Term found");
        }

        String currentUser = GlobalVariables.getUserSession().getPrincipalId();
        ContextInfo context = ContextInfo.newInstance();
        courseOfferingInfoList = new ArrayList();

        try{
            List<String> coIds = getCOService().getCourseOfferingIdsByTermAndInstructorId(term.getKey(), currentUser, context);
            if (!coIds.isEmpty()){
                courseOfferingInfoList = getCOService().getCourseOfferingsByIdList(coIds, context);
            }
        }catch(Exception e){
            //FIXME: Change it to use proper error handling
            throw new RuntimeException(e);
        }

    }

    protected AcademicCalendarService getAcalService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/acal", "AcademicCalendarService"));
        }
        return this.acalService;
    }

    protected CourseOfferingService getCOService(){
        if (coService == null){
            coService = (CourseOfferingService)GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/courseOffering", "coService"));
        }
        return coService;
    }

}
