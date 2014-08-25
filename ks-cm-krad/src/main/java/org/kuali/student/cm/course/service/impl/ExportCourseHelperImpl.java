/*
 * Copyright 2011 The Kuali Foundation
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

package org.kuali.student.cm.course.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.RuleTypeInfo;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.course.form.wrapper.CluInstructorInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.CourseCreateUnitsContentOwner;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.ResultValuesGroupInfoWrapper;
import org.kuali.student.cm.course.service.ExportCourseHelper;
import org.kuali.student.common.ui.client.util.ExportElement;
import org.kuali.student.core.rule.infc.Rule;
import org.kuali.student.r1.core.workflow.dto.CollaboratorWrapper;
import org.kuali.student.r2.lum.clu.dto.CluInstructorInfo;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * A helper class to export course. this helper class is used to construct list of ExportElement based on courseInfoWrapper.
 *
 * @author Kuali Student Team
 */
public class ExportCourseHelperImpl implements ExportCourseHelper {

    /**
     * This method constructs list of exportElement based on each field in courseInfoWrapper.
     *
     * @param courseInfoWrapper
     * @return List of constructed exportElement based on course/proposal.
     */
    public List<ExportElement> constructExportElementBasedOnView(CourseInfoWrapper courseInfoWrapper, boolean isProposal ) {

        List<ExportElement> exportElements = new ArrayList<ExportElement>();

        populateCourseInformation(exportElements, courseInfoWrapper , isProposal);
        populateGovernance(exportElements, courseInfoWrapper);
        populateCourseLogistics(exportElements, courseInfoWrapper);
        populateLearningObjectives(exportElements,courseInfoWrapper);
        populateCourseRequisites(exportElements,courseInfoWrapper);
        populateActiveDates(exportElements,courseInfoWrapper);
        if(!isProposal) {
            populateFinancials(exportElements, courseInfoWrapper);
            populateAuthorsCollaborators(exportElements, courseInfoWrapper);
        }
        return exportElements;
    }

    /**
     * Creates exportElement with given fieldLabel, fieldValue, sectionName and print Type.
     *
     * @param fieldLabel
     * @param fieldValue
     * @param sectionName
     * @param printType
     * @return  returns exportElement based on given value.
     */
    protected ExportElement populateExportElement(String fieldLabel, String fieldValue, String sectionName, int printType) {

        ExportElement exportElement = new ExportElement();
        exportElement.setFieldLabel(fieldLabel);
        exportElement.setFieldValue(fieldValue);
        exportElement.setViewName(sectionName);
        exportElement.setSectionName(sectionName);
        exportElement.setPrintType(printType);
        return exportElement;
    }

    /**
     * This method creates exportElement for each field in "Course Information" section and adds them to the list.
     *
     * @param exportElements
     * @param courseInfoWrapper
     */
    protected void populateCourseInformation(List<ExportElement> exportElements, CourseInfoWrapper courseInfoWrapper ,boolean isProposal) {

        String courseTitle = courseInfoWrapper.getCourseInfo().getCourseTitle();
        ExportElement exportCourseTitle = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.COURSE_TITLE, courseTitle, CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1);
        exportElements.add(exportCourseTitle);

        if(isProposal) {
            String proposalTitle = courseInfoWrapper.getProposalInfo().getName();
            ExportElement exportProposalTitle = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.COURSE_TITLE, proposalTitle, CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1);
            exportElements.add(exportProposalTitle);
        }

        String transcriptCourseTitle = courseInfoWrapper.getCourseInfo().getTranscriptTitle();
        ExportElement exportTranscriptCourseTitle = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.TRANSCRIPT_COURSE_TITLE, transcriptCourseTitle, CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1);
        exportElements.add(exportTranscriptCourseTitle);

        String subjectCode = courseInfoWrapper.getCourseInfo().getCode();
        ExportElement exportSubjectCode = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SUBJECT_CODE, subjectCode, CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1);
        exportElements.add(exportSubjectCode);

        String courseNumber = courseInfoWrapper.getCourseInfo().getCourseNumberSuffix();
        ExportElement exportCourseNumber = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.COURSE_NUMBER, courseNumber , CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1);
        exportElements.add(exportCourseNumber);

        //String instructors = courseInfoWrapper.getCourseInfo().getInstructors();
        //ExportElement exportInstructors = populateExportElement(
        String instructors = "";
        for(CluInstructorInfo instructor : courseInfoWrapper.getInstructorWrappers()){
           instructors = instructors +  ((CluInstructorInfoWrapper)instructor).getDisplayName() + ";";
        }

        ExportElement exportInstructors = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.INSTRUCTOR, instructors , CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1);
        exportElements.add(exportInstructors);

        String description  = (courseInfoWrapper.getCourseInfo().getDescr() != null) ? courseInfoWrapper.getCourseInfo().getDescr().getPlain() : "";
        ExportElement exportDescription = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.DESCRIPTION_AND_RATIONALE, description , CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1);
        exportElements.add(exportDescription);

        if(isProposal) {
            String proposalRationale = (courseInfoWrapper.getProposalInfo().getRationale() != null) ? courseInfoWrapper.getProposalInfo().getRationale().getPlain() : "";
            ExportElement exportProposalRationale = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.PROPOSAL_RATIONALE, proposalRationale, CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1);
            exportElements.add(exportProposalRationale);
        }
    }

    /**
     * This method creates exportElement for each field in "Governance" section and adds them to the list.
     *
     * @param exportElements
     * @param courseInfoWrapper
     */
    protected void populateGovernance(List<ExportElement> exportElements, CourseInfoWrapper courseInfoWrapper) {

        String campusLocations = "";
        for(String campusLocation : courseInfoWrapper.getCourseInfo().getCampusLocations()){
            campusLocations = campusLocations + campusLocation + ";";
        }
        ExportElement exportCampusLocation = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.Governance.CAMPUS_LOCATION, campusLocations,CurriculumManagementConstants.ProposalViewFieldLabels.Governance.SECTION_NAME, -1);
        exportElements.add(exportCampusLocation);

        String curriculumOversight = "";
        for(String courseCreateUnitsContentOwner  : courseInfoWrapper.getCourseInfo().getUnitsContentOwner()){
            curriculumOversight = curriculumOversight  + courseCreateUnitsContentOwner + ";";
        }
        ExportElement exportCurriculumOversight = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.Governance.CURRICULUM_OVERSIGHT, curriculumOversight,CurriculumManagementConstants.ProposalViewFieldLabels.Governance.SECTION_NAME, -1);
        exportElements.add(exportCurriculumOversight);

       // String  administeringOrganization = "";
    }

    /**
     *
     * This method creates exportElement for each field in "Course Logistics" section and adds them to the list.
     *
     * @param exportElements
     * @param courseInfoWrapper
     */
    protected void populateCourseLogistics(List<ExportElement> exportElements, CourseInfoWrapper courseInfoWrapper) {

        String termOffered = "";
        for(String term :courseInfoWrapper.getCourseInfo().getTermsOffered()){
            termOffered = termOffered + term + ";";
        }
        ExportElement exportTermOffered = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.TERM, termOffered , CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME,-1 );
        exportElements.add(exportTermOffered);

        String durationCount = "";
        String durationType = "";
        if(courseInfoWrapper.getCourseInfo().getDuration()!= null) {
            if(courseInfoWrapper.getCourseInfo().getDuration().getTimeQuantity() != null){
                durationCount = courseInfoWrapper.getCourseInfo().getDuration().getTimeQuantity().toString();
            }
            durationType = courseInfoWrapper.getCourseInfo().getDuration().getAtpDurationTypeKey();
        }
        ExportElement exportDuration = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.DURATION_COUNT,durationCount, CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME,-1 );
        exportElements.add(exportDuration);

        ExportElement exportDurationType = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.DURATION_TYPE,durationType, CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME,-1 );
        exportElements.add(exportDurationType);

        String assessmentScale = "";
        for(String gradingOption :courseInfoWrapper.getCourseInfo().getGradingOptions()) {
            assessmentScale = assessmentScale +  gradingOption + ";";
        }
        ExportElement exportAssessmentScale = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.ASSESSMENT_SCALE, assessmentScale, CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME,-1 );
        exportElements.add(exportAssessmentScale);

        String audit = new Boolean(courseInfoWrapper.isAudit()).toString();
        ExportElement exportAudit = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.AUDIT, audit, CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME,-1 );
        exportElements.add(exportAudit);

        String passFail = new Boolean(courseInfoWrapper.isPassFail()).toString();
        ExportElement exportPassFail = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.PASS_FAIL_TRANSCRIPT_GRADE, passFail, CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME,-1 );
        exportElements.add(exportPassFail);

        String finalExamStatus = courseInfoWrapper.getFinalExamStatus();
        ExportElement exportFinalExamStatus = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.FINAL_EXAM_STATUS, finalExamStatus, CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME,-1 );
        exportElements.add(exportFinalExamStatus);

        int counter = 1;
        for(ResultValuesGroupInfoWrapper resultValuesGroupInfoWrapper : courseInfoWrapper.getCreditOptionWrappers()){

            String typeKey = resultValuesGroupInfoWrapper.getTypeKey();
            typeKey = typeKey.substring(typeKey.lastIndexOf(".") +1);
            String creditOption = resultValuesGroupInfoWrapper.getUiHelper().getResultValue();
            ExportElement exportOutcome = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.OUTCOME + counter, null,CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME,-1 );
            ExportElement exportOutcomeType = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.TYPE, typeKey,CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME,-1 );
            ExportElement exportCredit = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.CREDITS, creditOption,CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME,-1 );

            exportElements.add(exportOutcome);
            exportElements.add(exportOutcomeType);
            exportElements.add(exportCredit);
            counter ++;
        }
        counter = 1;
        for(FormatInfo formatInfo : courseInfoWrapper.getFormats()) {

           ExportElement exportCourseFormat = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.COURSE_FORMAT + counter , null,CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME,-1 );
            exportElements.add(exportCourseFormat);
            int activityCounter = 1;
           for(ActivityInfo activityInfo : formatInfo.getActivities()) {

               ExportElement exportFormatActivity = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.ACTIVITY + activityCounter , null,CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME,-1 );
               exportElements.add(exportFormatActivity);

               String activityType = activityInfo.getTypeKey();
               ExportElement exportActivityType = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.ACTIVITY_TYPE, activityType, CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME,-1 );

               String activityDurationCount = "";
               String activityDurationType ="";
               if (activityInfo.getDuration() != null) {
                   if(StringUtils.isNotBlank(activityInfo.getDuration().getAtpDurationTypeKey())){
                       activityDurationType =   activityInfo.getDuration().getAtpDurationTypeKey();
                   }

                   if(activityInfo.getDuration().getTimeQuantity() != null){
                       activityDurationCount = activityInfo.getDuration().getTimeQuantity().toString();
                   }
                   exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.DURATION_TYPE, activityDurationType, CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME, -1));
                   exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.DURATION_COUNT, activityDurationCount, CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME, -1));
               }

               String contactHours = "";
               if (activityInfo.getContactHours() != null) {
                   String contactType = activityInfo.getContactHours().getUnitTypeKey();
                   contactType = StringUtils.substringAfterLast(contactType, ".");

                   if (activityInfo.getContactHours().getUnitQuantity() != null) {
                       contactHours = activityInfo.getContactHours().getUnitQuantity();
                   }
                   if (StringUtils.isNotBlank(contactType)) {
                       contactHours = contactHours + " per " + StringUtils.lowerCase(contactType);
                   }
               }
               exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.CONTACT_HOURS, contactHours , CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME,-1 ));
               String anticipatedClassSize = "";

               if(activityInfo.getDefaultEnrollmentEstimate() != null) {
                   anticipatedClassSize = activityInfo.getDefaultEnrollmentEstimate().toString();
               }
               exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.CLASS_SIZE, anticipatedClassSize,CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME,-1 ));
               activityCounter++;
           }
            counter ++;
        }
    }

    /**
     * This method creates exportElement for each field in "Learning Objectives" section and adds them to the list.
     *
     * @param exportElements
     * @param courseInfoWrapper
     */
    protected void populateLearningObjectives(List<ExportElement> exportElements, CourseInfoWrapper courseInfoWrapper) {
        ExportElement exportLearningObjectives = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.LearningObjectives.LEARNING_OBJECTIVES, null,CurriculumManagementConstants.ProposalViewFieldLabels.LearningObjectives.SECTION_NAME, -1 );
        traverseThroughLOs(exportLearningObjectives,courseInfoWrapper.getCourseInfo().getCourseSpecificLOs());
        exportElements.add(exportLearningObjectives);
    }

    /**
     *
     * @param exportLearningObjectives
     * @param courseSpecificLOs
     */
    protected void traverseThroughLOs( ExportElement exportLearningObjectives , List<LoDisplayInfo> courseSpecificLOs){
        List<ExportElement> exportLOList = new ArrayList<ExportElement>();
        String loDescription = "";
        for(LoDisplayInfo loDisplayInfo : courseSpecificLOs) {
            loDescription = loDisplayInfo.getLoInfo().getDescr().getPlain() + "(";
            for( LoCategoryInfo loCategoryInfo : loDisplayInfo.getLoCategoryInfoList()){
                if(loCategoryInfo.getDescr() != null){
                    loDescription = loDescription + loCategoryInfo.getDescr().getPlain() + ";" ;
                }
            }
            loDescription = loDescription + ")";
            exportLOList.add(populateExportElement(null, loDescription, CurriculumManagementConstants.ProposalViewFieldLabels.LearningObjectives.SECTION_NAME, -1));
            traverseThroughLOs(exportLearningObjectives, loDisplayInfo.getLoDisplayInfoList());
        }
        exportLearningObjectives.setSubset(exportLOList);
    }

    /**
     * This method creates exportElement for each field in "Course Requisites" section and adds them to the list.
     *
     * @param exportElements
     * @param courseInfoWrapper
     */
    protected void populateCourseRequisites(List<ExportElement> exportElements, CourseInfoWrapper courseInfoWrapper) {

        for(AgendaEditor agendaEditor : courseInfoWrapper.getAgendas()){
            String agendaDescription = agendaEditor.getAgendaTypeInfo().getDescription();
            ExportElement exportAgendaEditor = populateExportElement(null, agendaDescription , CurriculumManagementConstants.ProposalViewFieldLabels.CourseRequisites.SECTION_NAME , 1 );
            exportElements.add(exportAgendaEditor);
            Iterator iterator = agendaEditor.getRuleEditors().entrySet().iterator();
            while(iterator.hasNext()) {
                RuleEditor ruleEditor = (RuleEditor)(((Map.Entry)iterator.next()).getValue());
                if(ruleEditor.getProposition() !=null) {
                    String typeId = ruleEditor.getTypeId();
                    for(RuleTypeInfo ruleTypeInfo : agendaEditor.getAgendaTypeInfo().getRuleTypes()) {
                        if(StringUtils.equals(ruleTypeInfo.getId(), typeId)){
                            String description = ruleTypeInfo.getDescription();
                            ExportElement exportNaturalLanguage = populateExportElement(null, description, CurriculumManagementConstants.ProposalViewFieldLabels.CourseRequisites.SECTION_NAME , -1 );
                            exportElements.add(exportNaturalLanguage);
                            Iterator naturalLanguageIterator  = ((PropositionEditor)(ruleEditor.getProposition())).getNaturalLanguage().entrySet().iterator();
                            while(naturalLanguageIterator.hasNext()){
                                String naturalLanguage = (String)(((Map.Entry)(naturalLanguageIterator.next())).getValue());
                                exportNaturalLanguage = populateExportElement(null, naturalLanguage, CurriculumManagementConstants.ProposalViewFieldLabels.CourseRequisites.SECTION_NAME , -1 );
                                exportElements.add(exportNaturalLanguage);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * This method creates exportElement for each field in "Active Dates" section and adds them to the list.
     *
     * @param exportElements
     * @param courseInfoWrapper
     */
    protected void populateActiveDates(List<ExportElement> exportElements, CourseInfoWrapper courseInfoWrapper) {

        String startTerm = courseInfoWrapper.getCourseInfo().getStartTerm();
        String endTerm  = courseInfoWrapper.getCourseInfo().getEndTerm();
        String isPilotCourse = new Boolean(courseInfoWrapper.getCourseInfo().isPilotCourse()).toString();

        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.ActiveDates.START_TERM, startTerm , CurriculumManagementConstants.ProposalViewFieldLabels.ActiveDates.SECTION_NAME, -1  ));
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.ActiveDates.END_TERM, endTerm , CurriculumManagementConstants.ProposalViewFieldLabels.ActiveDates.SECTION_NAME, -1  ));
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.ActiveDates.PILOT_COURSE,isPilotCourse , CurriculumManagementConstants.ProposalViewFieldLabels.ActiveDates.SECTION_NAME, -1  ));
    }

    /**
     * This method creates exportElement for each field in "Financials" section and adds them to the list.
     *
     * @param exportElements
     * @param courseInfoWrapper
     */
    protected void populateFinancials(List<ExportElement> exportElements, CourseInfoWrapper courseInfoWrapper) {

        String feeJustification = "" ;
        if(courseInfoWrapper.getCourseInfo().getFeeJustification() != null) {
            feeJustification = courseInfoWrapper.getCourseInfo().getFeeJustification().getPlain();
        }
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.Financials.JUSTIFICATION_OF_FEES, feeJustification, CurriculumManagementConstants.ProposalViewFieldLabels.Financials.SECTION_NAME, -1));
    }

    /**
     * This method creates exportElement for each field in "Authors and Collaborators" section and adds them to the list.
     *
     * @param exportElements
     * @param courseInfoWrapper
     */
    protected void populateAuthorsCollaborators(List<ExportElement> exportElements, CourseInfoWrapper courseInfoWrapper) {

        for(CollaboratorWrapper collabaratorWrapper : courseInfoWrapper.getCollaboratorWrappers())    {

            String displayName = collabaratorWrapper.getDisplayName();
            String actionRequest = collabaratorWrapper.getAction();
            String permission = collabaratorWrapper.getPermission();
            exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.AuthorsCollaborators.SECTION_NAME, displayName, CurriculumManagementConstants.ProposalViewFieldLabels.AuthorsCollaborators.SECTION_NAME, 1));
            exportElements.add(populateExportElement(null, permission, CurriculumManagementConstants.ProposalViewFieldLabels.AuthorsCollaborators.SECTION_NAME, -1));
            exportElements.add(populateExportElement(null, actionRequest, CurriculumManagementConstants.ProposalViewFieldLabels.AuthorsCollaborators.SECTION_NAME, -1));
        }
   }

    protected void populateSupportingDocuments() {

    }
}
