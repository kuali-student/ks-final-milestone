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

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseCompleteInfoSearchResult", propOrder = {
        "courseOfferingId", "courseOfferingCode", "courseOfferingDesc", "courseOfferingCreditOptionDisplay",
        "courseOfferingGradingOptionDisplay", "studentSelectablePassFail", "auditCourse", "honorsCourse"})
public class CourseCompleteInfoSearchResult {
    private String courseOfferingId;
    private String courseOfferingCode;
    private String courseOfferingDesc;
    private String courseOfferingCreditOptionDisplay;
    private String courseOfferingGradingOptionDisplay;

    private boolean studentSelectablePassFail;
    private boolean auditCourse;
    private boolean honorsCourse;

    public boolean isAuditCourse() {
        return auditCourse;
    }

    public void setAuditCourse(boolean auditCourse) {
        this.auditCourse = auditCourse;
    }

    public String getCourseOfferingCode() {
        return courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
    }

    public String getCourseOfferingCreditOptionDisplay() {
        return courseOfferingCreditOptionDisplay;
    }

    public void setCourseOfferingCreditOptionDisplay(String courseOfferingCreditOptionDisplay) {
        this.courseOfferingCreditOptionDisplay = courseOfferingCreditOptionDisplay;
    }

    public String getCourseOfferingDesc() {
        return courseOfferingDesc;
    }

    public void setCourseOfferingDesc(String courseOfferingDesc) {
        this.courseOfferingDesc = courseOfferingDesc;
    }

    public String getCourseOfferingGradingOptionDisplay() {
        return courseOfferingGradingOptionDisplay;
    }

    public void setCourseOfferingGradingOptionDisplay(String courseOfferingGradingOptionDisplay) {
        this.courseOfferingGradingOptionDisplay = courseOfferingGradingOptionDisplay;
    }

    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public boolean isHonorsCourse() {
        return honorsCourse;
    }

    public void setHonorsCourse(boolean honorsCourse) {
        this.honorsCourse = honorsCourse;
    }

    public boolean isStudentSelectablePassFail() {
        return studentSelectablePassFail;
    }

    public void setStudentSelectablePassFail(boolean studentSelectablePassFail) {
        this.studentSelectablePassFail = studentSelectablePassFail;
    }
}