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
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseOfferingDetailsSearchResult", propOrder = {
        "courseOfferingId", "courseOfferingCode", "courseOfferingNumber", "courseOfferingSubjectArea", "courseOfferingDesc",
        "courseOfferingLongName", "cluId", "regGroupsOffered", "creditOptions", "gradingOptions",
        "crossListedCourses", "prerequisites", "activityOfferingTypes"})
public class CourseOfferingDetailsSearchResult implements Serializable {
    private String courseOfferingId;
    private String courseOfferingCode;
    private String courseOfferingDesc;
    private String courseOfferingNumber;
    private String courseOfferingSubjectArea;
    private String courseOfferingLongName;
    private String cluId;  // this allows us to tie our course offering back to the clu (KSAP Integration Point)
    private boolean regGroupsOffered;
    private List<String> creditOptions;
    private Map<String, String> gradingOptions;
    private List<CourseOfferingLimitedInfoSearchResult> crossListedCourses;
    private List<String> prerequisites;
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

    public String getCourseOfferingSubjectArea() {
        return courseOfferingSubjectArea;
    }

    public void setCourseOfferingSubjectArea(String courseOfferingSubjectArea) {
        this.courseOfferingSubjectArea = courseOfferingSubjectArea;
    }

    public String getCourseOfferingLongName() {
        return courseOfferingLongName;
    }

    public void setCourseOfferingLongName(String courseOfferingLongName) {
        this.courseOfferingLongName = courseOfferingLongName;
    }

    public boolean isRegGroupsOffered() { return regGroupsOffered; }

    public void setRegGroupsOffered(boolean regGroupsOffered) { this.regGroupsOffered = regGroupsOffered; }

    public List<String> getCreditOptions() {
        return creditOptions;
    }

    public void setCreditOptions(List<String> creditOptions) {
        this.creditOptions = creditOptions;
    }

    public Map<String, String> getGradingOptions() {
        if(gradingOptions == null){
            gradingOptions = new HashMap<String, String>();
        }
        return gradingOptions;
    }

    public void setGradingOptions(Map<String, String> gradingOptions) { this.gradingOptions = gradingOptions; }

    public List<CourseOfferingLimitedInfoSearchResult> getCrossListedCourses() {
        return crossListedCourses;
    }

    public void setCrossListedCourses(List<CourseOfferingLimitedInfoSearchResult> crossListedCourses) {
        this.crossListedCourses = crossListedCourses;
    }

    public List<ActivityOfferingTypesSearchResult> getActivityOfferingTypes() {
        return activityOfferingTypes;
    }

    public void setActivityOfferingTypes(List<ActivityOfferingTypesSearchResult> activityOfferingTypes) {
        this.activityOfferingTypes = activityOfferingTypes;
    }

    public List<String> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<String> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public String getCluId() {
        return cluId;
    }

    public void setCluId(String cluId) {
        this.cluId = cluId;
    }
}