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
import org.kuali.student.enrollment.class2.grading.service.GradingViewHelperService;
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

    private boolean saveEnabled;
    private boolean submitEnabled;

    private String currentTerm;

    private CourseOfferingInfo selectedCourseOffering;

    private String title = "Grading";

    private String gradeDueDate = " ";

    private AcademicCalendarService acalService;
    private CourseOfferingService coService;

    public GradingForm(){
        super();
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

    public boolean isSaveEnabled() {
        return saveEnabled;
    }

    public void setSaveEnabled(boolean saveEnabled) {
        this.saveEnabled = saveEnabled;
    }

    public boolean isSubmitEnabled() {
        return submitEnabled;
    }

    public void setSubmitEnabled(boolean submitEnabled) {
        this.submitEnabled = submitEnabled;
    }

    public String getCurrentTerm() {
        return currentTerm;
    }

    public void setCurrentTerm(String currentTerm) {
        this.currentTerm = currentTerm;
    }

    public CourseOfferingInfo getSelectedCourseOffering() {
        return selectedCourseOffering;
    }

    public void setSelectedCourseOffering(CourseOfferingInfo selectedCourseOffering) {
        this.selectedCourseOffering = selectedCourseOffering;
    }

    public String getTitle() {
       return title;
    }

    public void setTitle(String title) {
       this.title = title;
    }

    public String getGradeDueDate() {
        return gradeDueDate;
    }

    public void setGradeDueDate(String gradeDueDate) {
        this.gradeDueDate = gradeDueDate;
    }
}
