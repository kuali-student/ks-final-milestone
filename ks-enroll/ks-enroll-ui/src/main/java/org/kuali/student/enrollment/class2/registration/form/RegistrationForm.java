package org.kuali.student.enrollment.class2.registration.form;

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

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.registration.dto.CourseOfferingWrapper;
import org.kuali.student.enrollment.class2.registration.dto.ScheduleDataWrapper;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestInfo;

import java.util.List;

public class RegistrationForm extends UifFormBase {

    private static final long serialVersionUID = 2554632701931313545L;

    private String termKey;
    private String subjectArea;
    private String courseNameOrNumber;
    private List<CourseOfferingWrapper> courseOfferingWrappers;
    private List<ScheduleDataWrapper> registeredCourses;
    private List<ScheduleDataWrapper> cartCourses;

    private RegRequestInfo regRequest;

    public RegistrationForm(){
        super();
    }

    public String getTermKey() {
        return termKey;
    }

    public void setTermKey(String termKey) {
        this.termKey = termKey;
    }

    public String getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    public String getCourseNameOrNumber() {
        return courseNameOrNumber;
    }

    public void setCourseNameOrNumber(String courseNameOrNumber) {
        this.courseNameOrNumber = courseNameOrNumber;
    }

    public List<CourseOfferingWrapper> getCourseOfferingWrappers() {
        return courseOfferingWrappers;
    }

    public void setCourseOfferingWrappers(List<CourseOfferingWrapper> courseOfferingWrappers) {
        this.courseOfferingWrappers = courseOfferingWrappers;
    }

    public RegRequestInfo getRegRequest() {
        return regRequest;
    }

    public void setRegRequest(RegRequestInfo regRequest) {
        this.regRequest = regRequest;
    }

    public List<ScheduleDataWrapper> getRegisteredCourses() {
        return registeredCourses;
    }

    public void setRegisteredCourses(List<ScheduleDataWrapper> registeredCourses) {
        this.registeredCourses = registeredCourses;
    }

    public List<ScheduleDataWrapper> getCartCourses() {
        return cartCourses;
    }

    public void setCartCourses(List<ScheduleDataWrapper> cartCourses) {
        this.cartCourses = cartCourses;
    }

    public String getRegisteredCoursesJsArray(){
        String courseArray = "[";
        for(ScheduleDataWrapper course: registeredCourses){
            courseArray = courseArray + course.getJsScheduleObject() + ",";
        }
        courseArray = StringUtils.removeEnd(courseArray, ",") + "]";
        return courseArray;
    }

    public String getCartCoursesJsArray(){
        String courseArray = "[";
        for(ScheduleDataWrapper course: cartCourses){
            courseArray = courseArray + course.getJsScheduleObject() + ",";
        }
        courseArray = StringUtils.removeEnd(courseArray, ",") + "]";
        return courseArray;
    }
}