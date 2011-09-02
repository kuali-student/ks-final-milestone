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
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseregistration.dto.*;
import org.kuali.student.r2.common.dto.MeetingScheduleInfo;

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

    protected List<MeetingScheduleWrapper> getRegisteredCourses() {
        List<MeetingScheduleWrapper> meetingScheduleWrappers = new ArrayList<MeetingScheduleWrapper>();
        // first loop all the items in the course registration list
        for (CourseRegistrationInfo courseRegistrationInfo : getCourseRegistrations()) {
            // TODO - remove this cast below if CourseRegistrationInfo.getCourseOffering() method is fixed
            CourseOfferingInfo courseOfferingInfo = (CourseOfferingInfo) courseRegistrationInfo.getCourseOffering();
            RegGroupRegistrationInfo regGroupRegistrationInfo = courseRegistrationInfo.getRegGroupRegistration();
            for (ActivityRegistrationInfo activityRegistrationInfo : regGroupRegistrationInfo.getActivityRegistrations()) {
                ActivityOfferingInfo activityOfferingInfo = activityRegistrationInfo.getActivityOffering();
                for (MeetingScheduleInfo meetingScheduleInfo : activityOfferingInfo.getMeetingSchedules()) {
                    MeetingScheduleWrapper meetingScheduleWrapper = new MeetingScheduleWrapper(meetingScheduleInfo);
                    meetingScheduleWrapper.setCourseOfferingCode(courseOfferingInfo.getCourseOfferingCode());
                    meetingScheduleWrapper.setCourseTitle(courseOfferingInfo.getCourseTitle());
                    meetingScheduleWrappers.add(meetingScheduleWrapper);
                }
            }
        }
        return meetingScheduleWrappers;
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

    protected String getJsArray(List<MeetingScheduleWrapper> meetingScheduleInfos) {
        StringBuilder builder = new StringBuilder();
        for(MeetingScheduleWrapper course: meetingScheduleInfos){
            if (StringUtils.isNotBlank(builder.toString())) {
                builder.append(",");
            }
            builder.append(course.getJsScheduleObject());
        }
        return "[" + builder.toString() + "]";
    }

    public void setRegisteredCoursesJsArray(String temp){}
    public String getRegisteredCoursesJsArray(){
        return getJsArray(getRegisteredCourses());
    }

    public String getCartCoursesJsArray() {
        return getJsArray(getCartCourses());
    }

}