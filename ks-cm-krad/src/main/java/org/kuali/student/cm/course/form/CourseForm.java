/**
 * Copyright 2005-2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.cm.course.form;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.dto.DecisionInfo;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

import org.kuali.student.cm.course.form.OrganizationInfoWrapper;

/**
 * This is the helper class for CourseView
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
public class CourseForm extends UifFormBase {

    private static final long serialVersionUID = -988885314122936950L;
    
    private CourseInfo courseInfo;
    
    private ProposalInfo proposalInfo;
    
    private boolean audit;
    
    private boolean passFail;
    
    private List<CluInstructorInfoWrapper> instructorDisplays;
    
    private List<CourseJointInfoWrapper> courseJointDisplays;
    
    private List<ResultValuesGroupInfoWrapper> creditOptionsDisplay;
    
    private String finalExamStatus;
    
    private String finalExamRationale;
    
    private List<CommentInfo> commentInfos;
    
    private LearningObjectiveDialogWrapper loDialogWrapper;
    
    private Boolean showAll;
    
    private String userId;
    
    private List<DecisionInfo> decisions;

    private List<OrganizationInfoWrapper> administeringOrganizations;
    
    public CourseForm() {
        this.courseInfo = new CourseInfo();
        this.proposalInfo = new ProposalInfo();
        this.instructorDisplays = new ArrayList<CluInstructorInfoWrapper>();
        
        this.creditOptionsDisplay = new ArrayList<ResultValuesGroupInfoWrapper>();
        this.courseJointDisplays = new ArrayList<CourseJointInfoWrapper>();
        this.commentInfos = new ArrayList<CommentInfo>();
        this.decisions = new ArrayList<DecisionInfo>();
        this.administeringOrganizations = new ArrayList<OrganizationInfoWrapper>();
    }

    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }

    public ProposalInfo getProposalInfo() {
        return proposalInfo;
    }

    public void setProposalInfo(ProposalInfo proposalInfo) {
        this.proposalInfo = proposalInfo;
    }

    public List<CluInstructorInfoWrapper> getInstructorDisplays() {
        return instructorDisplays;
    }

    public void setInstructorDisplays(
        List<CluInstructorInfoWrapper> instructorDisplays) {
        this.instructorDisplays = instructorDisplays;
    }
    
    public List<OrganizationInfoWrapper> getAdministeringOrganizations() {
        return administeringOrganizations;
    }

    public void setAdministeringOrganizations(final List<OrganizationInfoWrapper> administeringOrganizations) {
        this.administeringOrganizations = administeringOrganizations;
    }

    public boolean isAudit() {
        return audit;
    }

    public void setAudit(boolean audit) {
        this.audit = audit;
    }

    public boolean isPassFail() {
        return passFail;
    }

    public void setPassFail(boolean passFail) {
        this.passFail = passFail;
    }

    public List<ResultValuesGroupInfoWrapper> getCreditOptionsDisplay() {
        return creditOptionsDisplay;
    }

    public void setCreditOptionsDisplay(List<ResultValuesGroupInfoWrapper> creditOptionsDisplay) {
        this.creditOptionsDisplay = creditOptionsDisplay;
    }
    
    public List<CourseJointInfoWrapper> getCourseJointDisplays() {
        return courseJointDisplays;
    }

    public void setCourseJointDisplays(List<CourseJointInfoWrapper> courseJointDisplays) {
        this.courseJointDisplays = courseJointDisplays;
    }

    public String getFinalExamStatus() {
        return finalExamStatus;
    }
    
    public void setFinalExamStatus(String finalExamStatus) {
        this.finalExamStatus = finalExamStatus;
    }

    public String getFinalExamRationale() {
        return finalExamRationale;
    }

    public void setFinalExamRationale(String finalExamRationale) {
        this.finalExamRationale = finalExamRationale;
    }

    public LearningObjectiveDialogWrapper getLoDialogWrapper() {
        return loDialogWrapper;
    }

    public void setLoDialogWrapper(LearningObjectiveDialogWrapper loDialogWrapper) {
        this.loDialogWrapper = loDialogWrapper;
    }

    public void setShowAll(final Boolean showAll) {
        this.showAll = showAll;
    }

    public Boolean getShowAll() {
        return this.showAll;
    }

    public List<CommentInfo> getCommentInfos() {
        return commentInfos;
    }

    public void setCommentInfos(List<CommentInfo> commentInfos) {
        this.commentInfos = commentInfos;
    }

    public List<DecisionInfo> getDecisions() {
        return decisions;
    }

    public void setDecisions(List<DecisionInfo> decisionInfos) {
        this.decisions = decisionInfos;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
