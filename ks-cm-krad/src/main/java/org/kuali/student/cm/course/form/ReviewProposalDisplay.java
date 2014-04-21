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

import org.apache.commons.lang.StringUtils;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Display data for review course proposal.
 */
public class ReviewProposalDisplay {

    private CourseSectionWrapper courseSection;
    private GovernanceSectionWrapper governanceSection;
    private CourseLogisticsSectionWrapper courseLogisticsSection;
    private LearningObjectivesSectionWrapper learningObjectivesSection;
    private CourseRequisitesSectionWrapper courseRequisitesSection;
    private ActiveDatesSectionWrapper activeDatesSection;
    private FinancialsSectionWrapper financialsSection;
    private CollaboratorSectionWrapper collaboratorSection;
    private SupportingDocumentsSectionWrapper supportingDocumentsSection;

    public CourseSectionWrapper getCourseSection() {
        if (this.courseSection == null) {
            courseSection = new CourseSectionWrapper();
        }
        return courseSection;
    }

    public GovernanceSectionWrapper getGovernanceSection() {
        if (this.governanceSection == null) {
            governanceSection = new GovernanceSectionWrapper();
        }
        return governanceSection;
    }

    public CourseLogisticsSectionWrapper getCourseLogisticsSection() {
        if (this.courseLogisticsSection == null) {
            courseLogisticsSection = new CourseLogisticsSectionWrapper();
        }
        return courseLogisticsSection;
    }


    public LearningObjectivesSectionWrapper getLearningObjectivesSection() {
        if (this.learningObjectivesSection == null) {
            learningObjectivesSection = new LearningObjectivesSectionWrapper();
        }
        return learningObjectivesSection;
    }

    public ActiveDatesSectionWrapper getActiveDatesSection() {
        if (this.activeDatesSection == null) {
            activeDatesSection = new ActiveDatesSectionWrapper();
        }
        return activeDatesSection;
    }

    public CourseRequisitesSectionWrapper getCourseRequisitesSection() {
        if (this.courseRequisitesSection == null) {
            courseRequisitesSection = new CourseRequisitesSectionWrapper();
        }
        return courseRequisitesSection;

    }

    public FinancialsSectionWrapper getFinancialsSection() {
        if (this.financialsSection == null) {
            financialsSection = new FinancialsSectionWrapper();
        }
        return financialsSection;

    }

    public CollaboratorSectionWrapper getCollaboratorSection() {
        if (this.collaboratorSection == null) {
            collaboratorSection = new CollaboratorSectionWrapper();
        }
        return collaboratorSection;
    }

    public SupportingDocumentsSectionWrapper getSupportingDocumentsSection() {
        if (this.supportingDocumentsSection == null) {
            supportingDocumentsSection = new SupportingDocumentsSectionWrapper();
        }
        return supportingDocumentsSection;

    }

    public class CourseSectionWrapper {
        private String proposalName;
        private String courseTitle;
        private String transcriptTitle;
        private String subjectArea;
        private String courseNumberSuffix;
        private List<String> instructors;

        private String description;
        private String rationale;
        private List<String> crossListings;
        private List<String> jointlyOfferedCourses;
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

        public String getInstructorsAsString() {
            return StringUtils.join(getInstructors(), CurriculumManagementConstants.COLLECTION_ITEMS_DELIMITER);
        }

        public List<String> getCrossListings() {
            if (this.crossListings == null) {
                crossListings = new LinkedList<String>();
            }
            return crossListings;
        }

        public String getCrossListingsAsString() {
            return StringUtils.join(getCrossListings(), CurriculumManagementConstants.COLLECTION_ITEMS_DELIMITER);
        }

        public List<String> getVariations() {
            if (this.variations == null) {
                variations = new LinkedList<String>();
            }
            return variations;
        }

        public String getVariationsAsString() {
            return StringUtils.join(getVariations(), CurriculumManagementConstants.COLLECTION_ITEMS_NEWLINE_DELIMITER);
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

        public List<String> getJointlyOfferedCourses() {
            if (this.jointlyOfferedCourses == null) {
                jointlyOfferedCourses = new LinkedList<String>();
            }
            return jointlyOfferedCourses;
        }

        public String getJointlyOfferedCoursesAsString() {
            return StringUtils.join(getJointlyOfferedCourses(), CurriculumManagementConstants.COLLECTION_ITEMS_DELIMITER);
        }
    }

    public class GovernanceSectionWrapper {
        private List<String> campusLocations;
        private List<String> curriculumOversight;
        private List<String> administeringOrganization;

        public List<String> getCampusLocations() {
            if (this.campusLocations == null) {
                campusLocations = new LinkedList<String>();
            }
            return campusLocations;
        }

        public String getCampusLocationsAsString() {
            return StringUtils.join(getCampusLocations(), CurriculumManagementConstants.COLLECTION_ITEMS_DELIMITER);
        }

        public void setCurriculumOversight(List<String> curriculumOversight) {
            this.curriculumOversight = curriculumOversight;
        }

        public List<String> getCurriculumOversight() {
            return curriculumOversight;
        }

        public String getCurriculumOversightAsString() {
            return StringUtils.join(getCurriculumOversight(), CurriculumManagementConstants.COLLECTION_ITEMS_DELIMITER);
        }

        public List<String> getAdministeringOrganization() {
            if (this.administeringOrganization == null) {
                administeringOrganization = new LinkedList<String>();
            }
            return administeringOrganization;
        }

        public String getAdministeringOrganizationAsString() {
            return StringUtils.join(getAdministeringOrganization(), CurriculumManagementConstants.COLLECTION_ITEMS_DELIMITER);
        }
    }

    public class CourseLogisticsSectionWrapper {
        private List<String> terms;
        private List<String> gradingOptions;
        private String atpDurationType;
        private Integer timeQuantity;
        private String passFail;
        private String audit;
        private String finalExamStatus;
        private String finalExamStatusRationale;
        private List<OutcomeReviewSection> outcomes;
        private List<FormatInfoWrapper> formatInfoWrappers;

        public CourseLogisticsSectionWrapper() {
        }

        public List<FormatInfoWrapper> getFormatInfoWrappers() {
            if (formatInfoWrappers == null) {
                formatInfoWrappers = new ArrayList<FormatInfoWrapper>();
            }
            return formatInfoWrappers;
        }

        public void setFormatInfoWrappers(List<FormatInfoWrapper> formatInfoWrapper) {
            this.formatInfoWrappers = formatInfoWrapper;
        }

        /**
         * This is for the formats "empty collection" input field. KRAD needs a property name to bind the constraints.
         * Using the same property name for two input fields causes the constraints to be overwritten. Simply setting a
         * KRAD component render=false doesn't prevent it from being considered during validation, so if formats are
         * defined there needs to be some text in the input field.
         *
         * @return An empty String if no formats have been defined. Otherwise, returns some text.
         */
        public String getEmptyStringFormats() {
          return formatInfoWrappers.isEmpty() ? "" : "Has Formats";
        }

        public List<String> getTerms() {
            if (terms == null) {
                terms = new ArrayList<String>();
            }
            return terms;
        }

        public String getTermsAsString() {
            return StringUtils.join(getTerms(), CurriculumManagementConstants.COLLECTION_ITEMS_DELIMITER);
        }

        public List<String> getGradingOptions() {
            return gradingOptions;
        }

        public String getGradingOptionsAsString() {
            return StringUtils.join(getGradingOptions(), CurriculumManagementConstants.COLLECTION_ITEMS_DELIMITER);
        }

        public void setGradingOptions(List<String> gradingOptions) {
            this.gradingOptions = gradingOptions;
        }

        public String getAtpDurationType() {
            return atpDurationType;
        }

        public void setAtpDurationType(String atpDurationType) {
            this.atpDurationType = atpDurationType;
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

        public Integer getTimeQuantity() {
            return timeQuantity;
        }

        public void setTimeQuantity(Integer timeQuantity) {
            this.timeQuantity = timeQuantity;
        }

        public String getDurationAsString() {
            String quatity = StringUtils.EMPTY;
            if(timeQuantity != null && timeQuantity.intValue() > 0 ) {
                quatity = timeQuantity.toString() + CurriculumManagementConstants.COLLECTION_ITEMS_WHITESPACE_DELIMITER;
            }  else {
                return null;
            }

            return quatity + atpDurationType + CurriculumManagementConstants.COLLECTION_ITEM_PLURAL_END;
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

        public String getFinalExamStatusRationale() {
            return finalExamStatusRationale;
        }

        public void setFinalExamStatusRationale(String finalExamStatusRationale) {
            this.finalExamStatusRationale = finalExamStatusRationale;
        }

        public List<OutcomeReviewSection> getOutcomes() {
            if (outcomes == null) {
                outcomes = new ArrayList<OutcomeReviewSection>();
            }
            return outcomes;
        }

        public void setOutcomes(List<OutcomeReviewSection> outComes) {
            this.outcomes = outComes;
        }

        /**
         * This is for the outcomes "empty collection" input field. KRAD needs a property name to bind the constraints.
         * Using the same property name for two input fields causes the constraints to be overwritten. Simply setting a
         * KRAD component render=false doens't prevent it from being considered during validation, so if outcomes are
         * defined there needs to be some text in the input field.
         *
         * @return An empty String if no outcomes have been defined. Otherwise, returns some text.
         */
        public String getEmptyStringOutcomes() {
            return outcomes.isEmpty() ? "" : "Has Outcomes";
        }
    }

    public class LearningObjectivesSectionWrapper {
        private List<String> learningObjectives;

        public List<String> getLearningObjectives() {
            if (learningObjectives == null) {
                learningObjectives = new ArrayList<String>();
            }
            return learningObjectives;
        }

        public String getLearningObjectivesAsString() {
            return StringUtils.join(getLearningObjectives(), CurriculumManagementConstants.COLLECTION_ITEMS_DELIMITER);
        }
    }

    public class CourseRequisitesSectionWrapper {

        //This method can be removed once the course requisites section is implemented.

        public void setDummyString(String dummyString) {
            this.dummyString = dummyString;
        }

        public String dummyString;

        public String getDummyString() {
            return null;
        }
    }

    public class ActiveDatesSectionWrapper {
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

    public class FinancialsSectionWrapper {
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

    public class SupportingDocumentsSectionWrapper {
        private String supportingDoc;

        public String getSupportingDoc() {
            return supportingDoc;
        }

        public void setSupportingDoc(String supportingDoc) {
            this.supportingDoc = supportingDoc;
        }
    }

    public class CollaboratorSectionWrapper {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
