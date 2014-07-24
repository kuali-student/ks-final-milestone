/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by vgadiyak on 7/21/14
 */
package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseOfferingInfoSearchResult", propOrder = {
        "courseOfferingId", "courseOfferingCode", "courseOfferingNumber", "courseOfferingPrefix", "courseOfferingDesc",
        "courseOfferingLongName", "courseOfferingCreditOptions", "courseOfferingGradingOptions",
        "courseOfferingCrossListedCourses", "courseOfferingPrerequisites", "activityOfferingTypes"})
public class CourseOfferingInfoSearchResult {
    private String courseOfferingId;
    private String courseOfferingCode;
    private String courseOfferingDesc;
    private String courseOfferingNumber;
    private String courseOfferingPrefix;
    private String courseOfferingLongName;
    private List<String> courseOfferingCreditOptions;
    private List<String> courseOfferingGradingOptions;
    private List<CourseOfferingLimitedInfoSearchResult> courseOfferingCrossListedCourses;
    private List<String> courseOfferingPrerequisites;
    private List<ActivityOfferingTypesSearchResult> activityOfferingTypes;

    public String getCourseOfferingCode() {
        return courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
    }

    public String getCourseOfferingDesc() {
        return courseOfferingDesc;
    }

    public void setCourseOfferingDesc(String courseOfferingDesc) {
        this.courseOfferingDesc = courseOfferingDesc;
    }

    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public String getCourseOfferingNumber() {
        return courseOfferingNumber;
    }

    public void setCourseOfferingNumber(String courseOfferingNumber) {
        this.courseOfferingNumber = courseOfferingNumber;
    }

    public String getCourseOfferingPrefix() {
        return courseOfferingPrefix;
    }

    public void setCourseOfferingPrefix(String courseOfferingPrefix) {
        this.courseOfferingPrefix = courseOfferingPrefix;
    }

    public String getCourseOfferingLongName() {
        return courseOfferingLongName;
    }

    public void setCourseOfferingLongName(String courseOfferingLongName) {
        this.courseOfferingLongName = courseOfferingLongName;
    }

    public List<String> getCourseOfferingCreditOptions() {
        return courseOfferingCreditOptions;
    }

    public void setCourseOfferingCreditOptions(List<String> courseOfferingCreditOptions) {
        this.courseOfferingCreditOptions = courseOfferingCreditOptions;
    }

    public List<String> getCourseOfferingGradingOptions() {
        return courseOfferingGradingOptions;
    }

    public void setCourseOfferingGradingOptions(List<String> courseOfferingGradingOptions) {
        this.courseOfferingGradingOptions = courseOfferingGradingOptions;
    }

    public List<CourseOfferingLimitedInfoSearchResult> getCourseOfferingCrossListedCourses() {
        return courseOfferingCrossListedCourses;
    }

    public void setCourseOfferingCrossListedCourses(List<CourseOfferingLimitedInfoSearchResult> courseOfferingCrossListedCourses) {
        this.courseOfferingCrossListedCourses = courseOfferingCrossListedCourses;
    }

    public List<String> getCourseOfferingPrerequisites() {
        return courseOfferingPrerequisites;
    }

    public void setCourseOfferingPrerequisites(List<String> courseOfferingPrerequisites) {
        this.courseOfferingPrerequisites = courseOfferingPrerequisites;
    }
}