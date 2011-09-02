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
import org.kuali.student.enrollment.class2.registration.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.registration.dto.CourseOfferingWrapper;
import org.kuali.student.enrollment.class2.registration.dto.MeetingScheduleWrapper;
import org.kuali.student.enrollment.class2.registration.dto.RegistrationGroupWrapper;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestItemInfo;

import java.util.*;

public class RegistrationForm extends UifFormBase {

    private static final long serialVersionUID = 2554632701931313545L;

    private String termKey;
    private String subjectArea;
    private String courseNameOrNumber;
    private List<CourseOfferingWrapper> courseOfferingWrappers;

    private List<CourseRegistrationInfo> courseRegistrations;
    private Map<String,RegistrationGroupWrapper> registrationGroupWrappersById;

    private RegRequestInfo regRequest;

    public RegistrationForm(){
        super();
        this.courseRegistrations = new ArrayList<CourseRegistrationInfo>();
        this.registrationGroupWrappersById = new HashMap<String,RegistrationGroupWrapper>();
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

    public List<CourseRegistrationInfo> getCourseRegistrations() {
        return courseRegistrations;
    }

    public void setCourseRegistrations(List<CourseRegistrationInfo> courseRegistrations) {
        this.courseRegistrations = courseRegistrations;
    }

    public Map<String, RegistrationGroupWrapper> getRegistrationGroupWrappersById() {
        return registrationGroupWrappersById;
    }

    public void setRegistrationGroupWrappersById(Map<String, RegistrationGroupWrapper> registrationGroupWrappersById) {
        this.registrationGroupWrappersById = registrationGroupWrappersById;
    }

    public RegRequestInfo getRegRequest() {
        return regRequest;
    }

    public void setRegRequest(RegRequestInfo regRequest) {
        this.regRequest = regRequest;
    }

    public String getRegisteredCoursesJsArray(){
        String courseArray = "[";
//        for(MeetingScheduleWrapper course: registeredCourses){
//            courseArray = courseArray + course.getJsScheduleObject() + ",";
//        }
        courseArray = StringUtils.removeEnd(courseArray, ",") + "]";
        return courseArray;
    }

    protected List<MeetingScheduleWrapper> getCartCourses() {
        List<MeetingScheduleWrapper> meetingScheduleWrappers = new ArrayList<MeetingScheduleWrapper>();
        // first loop all the items in the reg request
        for (RegRequestItemInfo regRequestItemInfo : getRegRequest().getRegRequestItems()) {
            // find the regGroupId of the current item
            String regGroupId = (StringUtils.isNotBlank(regRequestItemInfo.getNewRegGroupId())) ? regRequestItemInfo.getNewRegGroupId() : regRequestItemInfo.getExistingRegGroupId();
            // find the regGroupWrapper that matches the id from the supplemental list
            RegistrationGroupWrapper regGroupWrapper = getRegistrationGroupWrappersById().get(regGroupId);
            // if no valid regGroupWrapper object can be found something is wrong with the RegistrationContoller method that adds courses to the cart
            if (regGroupWrapper == null) {
                throw new RuntimeException("Cannot find RegistrationGroup in RegistrationForm for registrationGroupId: " + regGroupId);
            }
            // look at the activityOfferingInfos from the regGroup to get all the Schedule information into one single list
            for (ActivityOfferingWrapper activityOfferingWrapper : regGroupWrapper.getActivityOfferingWrappers()) {
                meetingScheduleWrappers.addAll(activityOfferingWrapper.getMeetingScheduleWrappers());
            }
        }
        return meetingScheduleWrappers;
    }

    public void setCartCoursesJsArray(String string) {
        StringBuilder builder = new StringBuilder();
    }

    public String getCartCoursesJsArray() {
        StringBuilder builder = new StringBuilder();
        for(MeetingScheduleWrapper course: getCartCourses()){
            if (StringUtils.isNotBlank(builder.toString())) {
                builder.append(",");
            }
            builder.append(course.getJsScheduleObject());
        }
        return "[" + builder.toString() + "]";
//        String courseArray = "[";
//        for(MeetingScheduleWrapper course: getCartCourses()){
//            courseArray = courseArray + course.getJsScheduleObject() + ",";
//        }
//        courseArray = StringUtils.removeEnd(courseArray, ",") + "]";
//        return courseArray;
    }

}