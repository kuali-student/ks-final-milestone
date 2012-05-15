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
 * Created by Charles on 5/7/12
 */
package org.kuali.student.enrollment.class2.courseoffering.form;

import org.kuali.rice.krad.web.form.UifFormBase;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CourseOfferingRolloverManagementForm extends UifFormBase {
    // Properties of the form
    private String targetTermCode;
    private String sourceTermCode;
    private String displayedTargetTermCode;
    private String targetTermStartDate;
    private String targetTermEndDate;
    private String targetLastRolloverDate;
    private String displayedSourceTermCode;
    private String sourceTermStartDate;
    private String sourceTermEndDate;
    // rollover details fields
    private String rolloverSourceTerm;
    private String rolloverTargetTerm;
    private String dateInitiated;
    private String dateCompleted;
    private String courseOfferingsNotAllowed;
    private String activityOfferingsNotAllowed;

    public CourseOfferingRolloverManagementForm(){
        targetTermCode = "";
        sourceTermCode = "";
        displayedTargetTermCode = "";
        targetTermStartDate = "";
        targetTermEndDate = "";
        targetLastRolloverDate = "";
        displayedSourceTermCode = "";
        sourceTermStartDate = "";
        sourceTermEndDate = "";
    }

    public String getTargetTermCode() {
        return targetTermCode;
    }

    public void setTargetTermCode(String targetTermCode) {
        this.targetTermCode = targetTermCode;
    }

    public String getSourceTermCode() {
        return sourceTermCode;
    }

    public void setSourceTermCode(String sourceTermCode) {
        this.sourceTermCode = sourceTermCode;
    }

    public String getDisplayedTargetTermCode() {
        return displayedTargetTermCode;
    }

    public void setDisplayedTargetTermCode(String displayedTargetTermCode) {
        this.displayedTargetTermCode = displayedTargetTermCode;
    }

    public String getTargetTermStartDate() {
        return targetTermStartDate;
    }

    public void setTargetTermStartDate(String targetTermStartDate) {
        this.targetTermStartDate = targetTermStartDate;
    }

    public String getTargetTermEndDate() {
        return targetTermEndDate;
    }

    public void setTargetTermEndDate(String targetTermEndDate) {
        this.targetTermEndDate = targetTermEndDate;
    }

    public String getTargetLastRolloverDate() {
        return targetLastRolloverDate;
    }

    public void setTargetLastRolloverDate(String targetLastRolloverDate) {
        this.targetLastRolloverDate = targetLastRolloverDate;
    }

    public String getDisplayedSourceTermCode() {
        return displayedSourceTermCode;
    }

    public void setDisplayedSourceTermCode(String displayedSourceTermCode) {
        this.displayedSourceTermCode = displayedSourceTermCode;
    }

    public String getSourceTermStartDate() {
        return sourceTermStartDate;
    }

    public void setSourceTermStartDate(String sourceTermStartDate) {
        this.sourceTermStartDate = sourceTermStartDate;
    }

    public String getSourceTermEndDate() {
        return sourceTermEndDate;
    }

    public void setSourceTermEndDate(String sourceTermEndDate) {
        this.sourceTermEndDate = sourceTermEndDate;
    }

    public String getRolloverSourceTerm() {
        return rolloverSourceTerm;
    }

    public void setRolloverSourceTerm(String rolloverSourceTerm) {
        this.rolloverSourceTerm = rolloverSourceTerm;
    }
    public String getDateInitiated() {
        return dateInitiated;
    }

    public void setDateInitiated(String dateInitiated) {
        this.dateInitiated = dateInitiated;
    }

    public String getRolloverTargetTerm() {
        return rolloverTargetTerm;
    }

    public void setRolloverTargetTerm(String rolloverTargetTerm) {
        this.rolloverTargetTerm = rolloverTargetTerm;
    }

    public String getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public String getCourseOfferingsNotAllowed() {
        return courseOfferingsNotAllowed;
    }

    public void setCourseOfferingsNotAllowed(String courseOfferingsNotAllowed) {
        this.courseOfferingsNotAllowed = courseOfferingsNotAllowed;
    }

    public String getActivityOfferingsNotAllowed() {
        return activityOfferingsNotAllowed;
    }

    public void setActivityOfferingsNotAllowed(String activityOfferingsNotAllowed) {
        this.activityOfferingsNotAllowed = activityOfferingsNotAllowed;
    }
}
