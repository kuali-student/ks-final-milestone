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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Marker interface for having arbitrary course data in a collection displayed in stacks.
 *
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
public class ReviewProposalDisplay implements java.io.Serializable {

    private CourseSectionWrapper courseSection;
    private GovernanceSectionWrapper governanceSection;
    private CourseLogisticsSectionWrapper courseLogisticsSection;
    private LearningObjectivesSectionWrapper learningObjectivesSection;
    private CourseRequisitesSectionWrapper courseRequisitesSection;
    private ActiveDatesSectionWrapper activeDatesSection;
    private FinancialsSectionWrapper financialsSection;
    private CollaboratorSectionWrapper collaboratorSection;
    private SupportingDocumentsSectionWrapper supportingDocumentsSection;

    public CourseSectionWrapper getcourseSection() {
        if (this.courseSection == null) {
            courseSection = new CourseSectionWrapper();
        }
        return courseSection;
    }

    public GovernanceSectionWrapper getgovernanceSection() {
        if (this.governanceSection == null) {
            governanceSection = new GovernanceSectionWrapper();
        }
        return governanceSection;
    }

    public CourseLogisticsSectionWrapper getcourseLogisticsSection() {
        if (this.courseLogisticsSection == null) {
            courseLogisticsSection = new CourseLogisticsSectionWrapper();
        }
        return courseLogisticsSection;
    }


    public LearningObjectivesSectionWrapper getlearningObjectivesSection() {
        if (this.learningObjectivesSection == null) {
            learningObjectivesSection = new LearningObjectivesSectionWrapper();
        }
        return learningObjectivesSection;
    }

    public ActiveDatesSectionWrapper getactiveDatesSection() {
        if (this.activeDatesSection == null) {
            activeDatesSection = new ActiveDatesSectionWrapper();
        }
        return activeDatesSection;
    }

    public CourseRequisitesSectionWrapper getcourseRequisitesSection() {
        if (this.courseRequisitesSection == null) {
            courseRequisitesSection = new CourseRequisitesSectionWrapper();
        }
        return courseRequisitesSection;
    
    }

    public FinancialsSectionWrapper getfinancialsSection() {
        if (this.financialsSection == null) {
            financialsSection = new FinancialsSectionWrapper();
        }
        return financialsSection;
    
    }

    public CollaboratorSectionWrapper getcollaboratorSection() {
        if(this.collaboratorSection == null) {
            collaboratorSection = new CollaboratorSectionWrapper();
        }
        return collaboratorSection;
    }

    public SupportingDocumentsSectionWrapper getsupportingDocumentsSection() {
        if (this.supportingDocumentsSection == null) {
            supportingDocumentsSection = new SupportingDocumentsSectionWrapper();
        }
        return supportingDocumentsSection;
    
    }

    public class CourseSectionWrapper implements java.io.Serializable {
        private String proposalName;
        private String courseTitle;
        private String transcriptTitle;
        private String subjectArea;
        private String courseNumberSuffix;
        private List<String> instructors;

        private String description;
        private String rationale;
        private List<String> crossListings;
        private List<String> variations;

        public String getProposalName() {
            return proposalName;
        }

        public void setProposalName(final String proposalName) {
            this.proposalName = proposalName;
        }

        public String getCourseTitle() {
            return courseTitle;
        }

        public void setCourseTitle(final String courseTitle) {
            this.courseTitle = courseTitle;
        }

        public String getTranscriptTitle() {
            return transcriptTitle;
        }

        public void setTranscriptTitle(final String transcriptTitle) {
            this.transcriptTitle = transcriptTitle;
        }

        public String getSubjectArea() {
            return subjectArea;
        }

        public void setSubjectArea(final String subjectArea) {
            this.subjectArea = subjectArea;
        }

        public String getCourseNumberSuffix() {
            return courseNumberSuffix;
        }

        public void setCourseNumberSuffix(final String courseNumberSuffix) {
            this.courseNumberSuffix = courseNumberSuffix;
        }

        public List<String> getInstructors() {
            if (this.instructors == null) {
                instructors = new LinkedList<String>();
            }
            return instructors;
        }

        public List<String> getCrossListings() {
            if (this.crossListings == null) {
                crossListings = new LinkedList<String>();
            }
            return crossListings;
        }

        public List<String> getVariations() {
            if (this.variations == null) {
                variations = new LinkedList<String>();
            }
            return variations;
        }

        public String getDescription() {
            return description;
        }

        public String getRationale() {
            return rationale;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setRationale(String rationale) {
            this.rationale = rationale;
        }
    }

    public class GovernanceSectionWrapper implements java.io.Serializable {
        private List<String> campusLocations;
        private List<String> curriculumOversight;
        private List<String> administeringOrganization;

        public List<String> getCampusLocations() {
            if (this.campusLocations == null) {
                campusLocations = new LinkedList<String>();
            }
            return campusLocations;
        }

        public List<String> getCurriculumOversight() {
            if (this.curriculumOversight == null) {
                curriculumOversight = new LinkedList<String>();
            }
            return curriculumOversight;
        }

        public List<String> getAdministeringOrganization() {
            if (this.administeringOrganization == null) {
                administeringOrganization = new LinkedList<String>();
            }
            return administeringOrganization;
        }
    }
    

    public class CourseLogisticsSectionWrapper implements java.io.Serializable {
        private List<String> terms;
        private String gradingOptions;
        private String atpDurationType;
        private Integer timeQuantity;
        private String passFail;
        private String audit;
        private String finalExamStatus;

        public List<String> getTerms() {
            if(terms == null) {
                terms = new ArrayList<String>();
            }
            return terms;
        }

        public String getGradingOptions() {
            return gradingOptions;
        }

        public String getAtpDurationType() {
            return atpDurationType;
        }


        public String getPassFail() {
            return passFail;
        }

        public void setAudit(final String audit) {
            this.audit = audit;
        }

        public String getAudit() {
            return audit;
        }

        public void setGradingOptions(String gradingOptions) {
            this.gradingOptions = gradingOptions;
        }

        public void setAtpDurationType(String atpDurationType) {
            this.atpDurationType = atpDurationType;
        }

        public Integer getTimeQuantity() {
            return timeQuantity;
        }

        public void setTimeQuantity(Integer timeQuantity) {
            this.timeQuantity = timeQuantity;
        }

        public void setPassFail(String passFail) {
            this.passFail = passFail;
        }

        public String getFinalExamStatus() {
            return finalExamStatus;
        }

        public void setFinalExamStatus(String finalExamStatus) {
            this.finalExamStatus = finalExamStatus;
        }
    }

    public class LearningObjectivesSectionWrapper implements java.io.Serializable {
        private List<String> learningObjectives;

        public List<String> getLearningObjectives() {
            if(learningObjectives == null) {
                learningObjectives = new ArrayList<String>();
            }
            return learningObjectives;
        }

    }

    public class CourseRequisitesSectionWrapper implements java.io.Serializable {

    }

    public class ActiveDatesSectionWrapper implements java.io.Serializable {
        private String startTerm;
        private String endTerm;
        private String pilotCourse;

        public void setStartTerm(final String startTerm) {
            this.startTerm = startTerm;
        }

        public String getStartTerm() {
            return startTerm;
        }
 
        public void setEndTerm(final String endTerm) {
            this.endTerm = endTerm;
        }

        public String getEndTerm() {
            return endTerm;
        }
        
        public void setPilotCourse(final String pilotCourse) {
            this.pilotCourse = pilotCourse;
        }

        public String getPilotCourse() {
            return pilotCourse;
        }
        
    }

    public class FinancialsSectionWrapper implements java.io.Serializable {
        private String justificationOfFees;
        private String fee;
        private String revenueSource;
        private String expendingOrganization;

        
        public void setJustificationOfFees(final String justificationOfFees) {
            this.justificationOfFees = justificationOfFees;
        }

        public String getJustificationOfFees() {
            return justificationOfFees;
        }

        public void setFee(final String fee) {
            this.fee = fee;
        }

        public String getFee() {
            return fee;
        }

        public void setRevenueSource(final String revenueSource) {
            this.revenueSource = revenueSource;
        }

        public String getRevenueSource() {
            return revenueSource;
        }

        public void setExpendingOrganization(final String expendingOrganization) {
            this.expendingOrganization = expendingOrganization;
        }

        public String getExpendingOrganization() {
            return expendingOrganization;
        }
    }

    public class SupportingDocumentsSectionWrapper implements java.io.Serializable {
        private String supportingDoc;

        public String getSupportingDoc() {
            return supportingDoc;
        }

        public void setSupportingDoc(String supportingDoc) {
            this.supportingDoc = supportingDoc;
        }
    }

    public class CollaboratorSectionWrapper implements Serializable {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
