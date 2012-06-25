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
import org.kuali.student.enrollment.class2.courseoffering.dto.SocRolloverResultItemWrapper;

import java.util.ArrayList;
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
    private boolean isGoSourceButtonDisabled = true;
    private boolean isRolloverButtonDisabled = true;

    // rollover details fields
    private String rolloverSourceTermDesc;
    private String rolloverTargetTermCode;
    private String rolloverTargetTermDesc;
    private String dateInitiated;
    private String dateCompleted;
    private String courseOfferingsAllowed;
    private String activityOfferingsAllowed;
    private List<SocRolloverResultItemWrapper> socRolloverResultItems;
    private boolean isConfigurationOptionsDisabled = true;
    private boolean acceptIndicator = false;

    // release to depts fields
    private boolean releaseToDeptsDisabled = false;
    private boolean releaseToDeptsInvalidTerm = false;
    private boolean releaseToDeptsAlreadyReleased = false;
    private boolean isReleasedToDepts = false;

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
        rolloverSourceTermDesc = "";
        rolloverTargetTermCode = "";
        rolloverTargetTermDesc = "";
        dateInitiated = "";
        dateCompleted = "";
        socRolloverResultItems = new ArrayList<SocRolloverResultItemWrapper>();
        // release to depts fields
        releaseToDeptsDisabled = false;
        releaseToDeptsAlreadyReleased = false;
        releaseToDeptsInvalidTerm = false;
        
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

    public String getRolloverSourceTermDesc() {
        return rolloverSourceTermDesc;
    }

    public void setRolloverSourceTermDesc(String rolloverSourceTermDesc) {
        this.rolloverSourceTermDesc = rolloverSourceTermDesc;
    }

    public String getRolloverTargetTermDesc() {
        return rolloverTargetTermDesc;
    }

    public void setRolloverTargetTermDesc(String rolloverTargetTermDesc) {
        this.rolloverTargetTermDesc = rolloverTargetTermDesc;
    }

    public String getDateInitiated() {
        return dateInitiated;
    }

    public void setDateInitiated(String dateInitiated) {
        this.dateInitiated = dateInitiated;
    }

    public String getRolloverTargetTermCode() {
        return rolloverTargetTermCode;
    }

    public void setRolloverTargetTermCode(String rolloverTargetTermCode) {
        this.rolloverTargetTermCode = rolloverTargetTermCode;
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
    
    public List<SocRolloverResultItemWrapper> getSocRolloverResultItems(){
        return socRolloverResultItems;
    }
    
    public void setSocRolloverResultItems(List<SocRolloverResultItemWrapper> socRolloverResultItems){
        this.socRolloverResultItems = socRolloverResultItems;
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

    public boolean getIsGoSourceButtonDisabled() {
        return isGoSourceButtonDisabled;
    }

    public void setIsGoSourceButtonDisabled(boolean goSourceButtonDisabled) {
        isGoSourceButtonDisabled = goSourceButtonDisabled;
    }

    public boolean getIsRolloverButtonDisabled() {
        return isRolloverButtonDisabled;
    }

    public void setIsRolloverButtonDisabled(boolean rolloverButtonDisabled) {
        isRolloverButtonDisabled = rolloverButtonDisabled;
    }

    public boolean getIsConfigurationOptionsDisabled() {
        return isConfigurationOptionsDisabled;
    }

    public void setIsConfigurationOptionsDisabled(boolean isConfigurationOptionsDisabled) {
        this.isConfigurationOptionsDisabled = isConfigurationOptionsDisabled;
    }

    public boolean getAcceptIndicator() {
        return acceptIndicator;
    }

    public void setAcceptIndicator(boolean acceptIndicator) {
        this.acceptIndicator = acceptIndicator;
    }

    public boolean getReleaseToDeptsDisabled() {
        return releaseToDeptsDisabled;
    }

    public void setReleaseToDeptsDisabled(boolean releaseToDeptsDisabled) {
        this.releaseToDeptsDisabled = releaseToDeptsDisabled;
    }

    public boolean getReleaseToDeptsInvalidTerm() {
        return releaseToDeptsInvalidTerm;
    }

    public void setReleaseToDeptsInvalidTerm(boolean releaseToDeptsInvalidTerm) {
        this.releaseToDeptsInvalidTerm = releaseToDeptsInvalidTerm;
    }

    public boolean getReleaseToDeptsAlreadyReleased() {
        return releaseToDeptsAlreadyReleased;
    }

    public void setReleaseToDeptsAlreadyReleased(boolean releaseToDeptsAlreadyReleased) {
        this.releaseToDeptsAlreadyReleased = releaseToDeptsAlreadyReleased;
    }

    public boolean getIsReleasedToDepts() {
        return isReleasedToDepts;
    }

    public void setIsReleasedToDepts(boolean releasedToDepts) {
        isReleasedToDepts = releasedToDepts;
    }

    public void resetForm(){
        displayedTargetTermCode = "";
        targetTermStartDate = "";
        targetTermEndDate = "";
        targetLastRolloverDate = "";
        displayedSourceTermCode = "";
        sourceTermStartDate = "";
        sourceTermEndDate = "";
        statusField = "";
        //rollover details fields
        rolloverSourceTermDesc = "";
        rolloverTargetTermCode = "";
        rolloverTargetTermDesc = "";
        dateInitiated = "";
        dateCompleted = "";
        socRolloverResultItems = new ArrayList<SocRolloverResultItemWrapper>();
        isGoSourceButtonDisabled = true;
        isRolloverButtonDisabled = true;
        isConfigurationOptionsDisabled = true;
        // release to depts fields
        releaseToDeptsDisabled = false;
        releaseToDeptsAlreadyReleased = false;
        releaseToDeptsInvalidTerm = false;
        isReleasedToDepts = false;
    }
 }
