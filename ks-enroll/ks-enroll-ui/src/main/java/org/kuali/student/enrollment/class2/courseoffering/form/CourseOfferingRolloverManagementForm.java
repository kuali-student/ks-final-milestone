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
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;

import java.util.List;
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
    private String statusField;
    private TermInfo sourceTerm;
    private TermInfo targetTerm;
    
    // rollover details fields
    private String rolloverSourceTerm;
    private String rolloverTargetTerm;
    private String dateInitiated;
    private String dateCompleted;
    private String courseOfferingsAllowed;
    private String activityOfferingsAllowed;
    private List<SocRolloverResultItemInfo> socRolloverResultItemInfos;

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
        statusField = "";
        //rollover details fields
        rolloverSourceTerm = "";
        rolloverTargetTerm = "";
        dateInitiated = "";
        dateCompleted = "";

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

    public String getCourseOfferingsAllowed() {
        return courseOfferingsAllowed;
    }

    public void setCourseOfferingsAllowed(String courseOfferingsAllowed) {
        this.courseOfferingsAllowed = courseOfferingsAllowed;
    }

    public String getActivityOfferingsAllowed() {
        return activityOfferingsAllowed;
    }

    public void setActivityOfferingsNotAllowed(String activityOfferingsNotAllowed) {
        this.activityOfferingsAllowed = activityOfferingsNotAllowed;
    }
    
    public List<SocRolloverResultItemInfo> getSocRolloverResultItemInfos(){
        return socRolloverResultItemInfos;
    }
    
    public void setSocRolloverResultItemInfos(List<SocRolloverResultItemInfo> socRolloverResultItemInfos){
        this.socRolloverResultItemInfos = socRolloverResultItemInfos;
    }

    public String getStatusField() {
        return statusField;
    }

    public void setStatusField(String statusField) {
        this.statusField = statusField;
    }

    public TermInfo getSourceTerm() {
        return sourceTerm;
    }

    public void setSourceTerm(TermInfo sourceTerm) {
        this.sourceTerm = sourceTerm;
    }

    public TermInfo getTargetTerm() {
        return targetTerm;
    }

    public void setTargetTerm(TermInfo targetTerm) {
        this.targetTerm = targetTerm;
    }
 }
