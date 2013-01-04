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

import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.grading.dataobject.GradeStudent;

import java.util.List;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.grading.dto.GradeRosterInfo;

//Core slice classes, just still around for reference.. needs cleanup
@Deprecated
public class GradingForm extends UifFormBase{

    private static final long serialVersionUID = -1054046347823986329L;

    private String title = "Grading";
    private String gradeDueDate = " ";
    private String selectedCourse;
    private String selectedTerm;
    private boolean readOnly;

    private List<GradeStudent> students;
    private List<CourseOfferingInfo> courseOfferingInfoList;
    private CourseOfferingInfo selectedCourseOffering;
    private List<GradeRosterInfo> rosterInfos;

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

    public String getSelectedTerm() {
        return selectedTerm;
    }

    public void setSelectedTerm(String selectedTerm) {
        this.selectedTerm = selectedTerm;
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

    public List<GradeRosterInfo> getRosterInfos() {
        return rosterInfos;
    }

    public void setRosterInfos(List<GradeRosterInfo> rosterInfos) {
        this.rosterInfos = rosterInfos;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }
}
