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
import org.kuali.student.cm.common.util.CurriculumManagementConstants.Export.FileType;
import org.kuali.student.cm.course.form.wrapper.CluInstructorInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.CourseCreateUnitsContentOwner;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.ResultValuesGroupInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.SupportingDocumentInfoWrapper;
import org.kuali.student.cm.course.service.ExportCourseHelper;
import org.kuali.student.common.ui.client.util.ExportElement;
import org.kuali.student.common.ui.server.screenreport.ScreenReportProcessor;
import org.kuali.student.common.ui.server.screenreport.jasper.JasperScreenReportProcessorImpl;
import org.kuali.student.lum.lu.ui.krms.dto.LUPropositionEditor;
import org.kuali.student.r1.core.workflow.dto.CollaboratorWrapper;
import org.kuali.student.r2.lum.clu.dto.CluInstructorInfo;
import org.kuali.student.r2.lum.course.dto.*;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A helper class to export course. Constructs a list of ExportElement based on courseInfoWrapper.
 *
 * @author Kuali Student Team
 */
public class ExportCourseHelperImpl implements ExportCourseHelper {

    private CourseInfoWrapper courseInfoWrapper;
    private FileType exportFileType;
    private String fileName;
    private boolean saveDocument = true;

    private ScreenReportProcessor processor = new JasperScreenReportProcessorImpl();

    /**
     * Constructor. Sets headers to "save as".
     *
     * @param courseInfoWrapper The data object to export.
     * @param exportFileType The metadata for the type of file to output.
     */
    public ExportCourseHelperImpl(CourseInfoWrapper courseInfoWrapper, FileType exportFileType) {
        this(courseInfoWrapper, exportFileType, true);
    }

    /**
     * Constructor.
     *
     * @param courseInfoWrapper The data object to export.
     * @param exportFileType The metadata for the type of file to output.
     * @param saveDocument If 'true' sets the document headers to values which encourage the web browers to display a
     *                     "save as" dialog. Otherwise, sets them values which encourage the browser to open the document..
     */
    public ExportCourseHelperImpl(CourseInfoWrapper courseInfoWrapper, FileType exportFileType, boolean saveDocument) {
        this.courseInfoWrapper = courseInfoWrapper;
        this.exportFileType = exportFileType;

        String courseTitle = courseInfoWrapper.getCourseInfo().getCourseTitle();
        //  Replace anything that isn't a number or a letter with underscore.
        this.fileName = courseTitle.replaceAll("[^A-Za-z0-9]", "_") + "."  + exportFileType.getFileSuffix();

        this.saveDocument = saveDocument;
    }

    public CourseInfoWrapper getCourseInfoWrapper() {
        return this.courseInfoWrapper;
    }

    public String getFileName() {
        return fileName;
    }

    public FileType getExportFileType() {
        return exportFileType;
    }

    public boolean isSaveDocument() {
        return this.saveDocument;
    }

    /**
     * Generates a response for the report.
     * @return
     */
    public ResponseEntity<byte[]> getResponseEntity() {

        byte[] bytes = getBytes();

        HttpHeaders headers = new HttpHeaders();

        /*
         * Setup the header for the response.
         */
        if (isSaveDocument()) {
            // Try to persuade the agent to save the document (in accordance with http://tools.ietf.org/html/rfc2616#section-19.5.1)
            headers.setContentType(MediaType.parseMediaType("application/octet-stream"));
            String contentDisposition = String.format("attachment; filename=%s", getFileName());
            headers.set("Content-Disposition" , contentDisposition);
        } else {
            headers.setContentType(MediaType.parseMediaType(getExportFileType().getMimeType()));
            headers.setContentDispositionFormData(getFileName(), getFileName());
        }

        headers.setCacheControl(CurriculumManagementConstants.Export.DOCUMENT_DOWNLOAD_CACHE_CONTROL);

        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }

    public byte[] getBytes() {
        byte[] bytes = null;

        //  Create the data object.
        List<ExportElement> exportElements = constructExportElementBasedOnView(getCourseInfoWrapper(), false);

        switch (this.exportFileType) {
            case PDF:
                bytes = this.processor.createPdf(exportElements, "base.template",
                    CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME);
                break;
            case DOC:
                bytes = this.processor.createDoc(exportElements, "base.template",
                    CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME);
                break;
        }

        return bytes;
    }

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
            populateSupportingDocuments(exportElements, courseInfoWrapper);
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
        exportElement.setFieldValue(StringUtils.defaultString(fieldValue));
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

        if(courseInfoWrapper.getCourseInfo() == null) {
            return;
        }

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

        populateCrossListCourses(exportElements,courseInfoWrapper);
        populateJointlyOfferedCourses(exportElements,courseInfoWrapper);
        populateVersionCodes(exportElements,courseInfoWrapper);

        StringBuilder instructors = new StringBuilder("");
        for(CluInstructorInfo instructor : courseInfoWrapper.getInstructorWrappers()){
            if (StringUtils.isNotBlank(((CluInstructorInfoWrapper)instructor).getDisplayName())){
                instructors.append(((CluInstructorInfoWrapper)instructor).getDisplayName());
                instructors.append(";");
            }
        }

        ExportElement exportInstructors = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.INSTRUCTOR, instructors.toString() , CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1);
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

        if(courseInfoWrapper.getCourseInfo() == null) {
            return;
        }

        StringBuilder campusLocations = new StringBuilder();
        for(String campusLocation : courseInfoWrapper.getCourseInfo().getCampusLocations()){
            campusLocations.append(campusLocation);
            campusLocations.append(";");
        }
        ExportElement exportCampusLocation = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.Governance.CAMPUS_LOCATION, campusLocations.toString(),CurriculumManagementConstants.ProposalViewFieldLabels.Governance.SECTION_NAME, -1);
        exportElements.add(exportCampusLocation);

        String curriculumOversight = "";
        for(CourseCreateUnitsContentOwner courseCreateUnitsContentOwner  : courseInfoWrapper.getUnitsContentOwner()){
            if (StringUtils.isNotBlank(courseCreateUnitsContentOwner.getRenderHelper().getOrgLongName())){
                curriculumOversight = curriculumOversight  + courseCreateUnitsContentOwner.getRenderHelper().getOrgLongName() + ";";
            }
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

        if(courseInfoWrapper.getCourseInfo() == null) {
            return;
        }

        StringBuilder termOffered = new StringBuilder("");
        for(String term :courseInfoWrapper.getCourseInfo().getTermsOffered()){
            if(term != null) {
                termOffered.append(term.substring(term.lastIndexOf('.')+1));
                termOffered.append(";");
            }
        }
        ExportElement exportTermOffered = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.TERM, termOffered.toString() , CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME,-1 );
        exportElements.add(exportTermOffered);

        String durationCount = "";
        String durationType = "";
        if(courseInfoWrapper.getCourseInfo().getDuration()!= null) {
            if(courseInfoWrapper.getCourseInfo().getDuration().getTimeQuantity() != null){
                durationCount = courseInfoWrapper.getCourseInfo().getDuration().getTimeQuantity().toString();
            }
            if( courseInfoWrapper.getCourseInfo().getDuration().getAtpDurationTypeKey() != null){
                durationType = courseInfoWrapper.getCourseInfo().getDuration().getAtpDurationTypeKey();
                durationType = durationType.substring(durationType.lastIndexOf('.')+1);
            }
        }
        ExportElement exportDuration = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.DURATION_COUNT,durationCount, CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME,-1 );
        exportElements.add(exportDuration);

        ExportElement exportDurationType = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.DURATION_TYPE,durationType, CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME,-1 );
        exportElements.add(exportDurationType);

        StringBuilder assessmentScale = new StringBuilder("");
        for(String gradingOption :courseInfoWrapper.getReviewProposalDisplay().getCourseLogisticsSection().getGradingOptions()){
            assessmentScale.append(((gradingOption.substring(gradingOption.lastIndexOf('.')+1))));
            assessmentScale.append(";");
        }
        ExportElement exportAssessmentScale = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.ASSESSMENT_SCALE, assessmentScale.toString(), CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME,-1 );
        exportElements.add(exportAssessmentScale);

        String audit = (courseInfoWrapper.isAudit()) ? "Yes" : "No";
        ExportElement exportAudit = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.AUDIT, audit, CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME,-1 );
        exportElements.add(exportAudit);

        String passFail = (courseInfoWrapper.isPassFail()) ? "Yes" : "No";
        ExportElement exportPassFail = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.PASS_FAIL_TRANSCRIPT_GRADE, passFail, CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME,-1 );
        exportElements.add(exportPassFail);

        String finalExamStatus = courseInfoWrapper.getFinalExamStatus();
        ExportElement exportFinalExamStatus = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.FINAL_EXAM_STATUS, finalExamStatus, CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME,-1 );
        exportElements.add(exportFinalExamStatus);

        int counter = 1;
        for(ResultValuesGroupInfoWrapper resultValuesGroupInfoWrapper : courseInfoWrapper.getCreditOptionWrappers()){

            String typeKey = (resultValuesGroupInfoWrapper.getTypeKey()!= null) ? resultValuesGroupInfoWrapper.getTypeKey() :"" ;
            typeKey = typeKey.substring(typeKey.lastIndexOf('.') + 1);
            String creditOption = (resultValuesGroupInfoWrapper.getUiHelper() != null) ? (resultValuesGroupInfoWrapper.getUiHelper().getResultValue()) : " ";
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
               exportElements.add(exportActivityType);

               String activityDurationCount = "";
               String activityDurationType ="";
               if (activityInfo.getDuration() != null) {
                   if(StringUtils.isNotBlank(activityInfo.getDuration().getAtpDurationTypeKey())){
                       activityDurationType =   activityInfo.getDuration().getAtpDurationTypeKey();
                       activityDurationType = activityDurationType.substring(activityDurationType.lastIndexOf('.')+1);
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

        if(courseInfoWrapper.getCourseInfo() == null) {
            return;
        }

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
        StringBuilder loDescription = new StringBuilder("");
        for(LoDisplayInfo loDisplayInfo : courseSpecificLOs) {
            if (loDisplayInfo.getLoInfo() != null && loDisplayInfo.getLoInfo().getDescr() != null) {
                loDescription.append(loDisplayInfo.getLoInfo().getDescr().getPlain() + "(");
                for (LoCategoryInfo loCategoryInfo : loDisplayInfo.getLoCategoryInfoList()) {
                    if (loCategoryInfo.getDescr() != null) {
                        loDescription.append(loCategoryInfo.getDescr().getPlain());
                        loDescription.append(";");
                    }
                }
                loDescription.append(")");
                exportLOList.add(populateExportElement(null, loDescription.toString(), CurriculumManagementConstants.ProposalViewFieldLabels.LearningObjectives.SECTION_NAME, -1));
                traverseThroughLOs(exportLearningObjectives, loDisplayInfo.getLoDisplayInfoList());
                exportLearningObjectives.setSubset(exportLOList);
            }
        }
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
                                for(PropositionEditor luPropositionEditor : (((PropositionEditor) (ruleEditor.getProposition())).getCompoundEditors())) {
                                    Iterator compoundEditorsNaturalLanguage = luPropositionEditor.getNaturalLanguage().entrySet().iterator();
                                    while(compoundEditorsNaturalLanguage.hasNext()) {
                                        String ceNaturalLanguage = (String)((Map.Entry)compoundEditorsNaturalLanguage.next()).getValue();
                                        ExportElement exportCENaturalLanguage = populateExportElement(null, ceNaturalLanguage, CurriculumManagementConstants.ProposalViewFieldLabels.CourseRequisites.SECTION_NAME , -1 );
                                        exportElements.add(exportCENaturalLanguage);
                                    }
                                }
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

        if(courseInfoWrapper.getCourseInfo() == null) {
            return;
        }

        String startTerm = courseInfoWrapper.getReviewProposalDisplay().getActiveDatesSection().getStartTerm();
        String endTerm  = courseInfoWrapper.getReviewProposalDisplay().getActiveDatesSection().getEndTerm();
        String isPilotCourse = (courseInfoWrapper.getCourseInfo().isPilotCourse()) ? "Yes" : "No";
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.ActiveDates.START_TERM, startTerm , CurriculumManagementConstants.ProposalViewFieldLabels.ActiveDates.SECTION_NAME, -1  ));
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.ActiveDates.END_TERM, endTerm, CurriculumManagementConstants.ProposalViewFieldLabels.ActiveDates.SECTION_NAME, -1));
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.ActiveDates.PILOT_COURSE,isPilotCourse , CurriculumManagementConstants.ProposalViewFieldLabels.ActiveDates.SECTION_NAME, -1  ));
    }

    /**
     * This method creates exportElement for each field in "Financials" section and adds them to the list.
     *
     * @param exportElements
     * @param courseInfoWrapper
     */
    protected void populateFinancials(List<ExportElement> exportElements, CourseInfoWrapper courseInfoWrapper) {

        if(courseInfoWrapper.getCourseInfo() == null) {
            return;
        }
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
            exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.AuthorsCollaborators.SECTION_NAME, displayName, CurriculumManagementConstants.ProposalViewFieldLabels.AuthorsCollaborators.SECTION_NAME, -1));
            exportElements.add(populateExportElement(null, permission, CurriculumManagementConstants.ProposalViewFieldLabels.AuthorsCollaborators.SECTION_NAME, -1));
            exportElements.add(populateExportElement(null, actionRequest, CurriculumManagementConstants.ProposalViewFieldLabels.AuthorsCollaborators.SECTION_NAME, -1));
        }
   }

    protected void populateSupportingDocuments(List<ExportElement> exportElements, CourseInfoWrapper courseInfoWrapper) {

        for (SupportingDocumentInfoWrapper supportingDocumentInfoWrapper : courseInfoWrapper.getSupportingDocs()) {
            String description = supportingDocumentInfoWrapper.getDescription();
            String documentName = supportingDocumentInfoWrapper.getDocumentName();
            if (StringUtils.isNotBlank(description) || StringUtils.isNotBlank(documentName)){
                exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.SupportingDocument.SECTION_NAME, documentName + " " + description, CurriculumManagementConstants.ProposalViewFieldLabels.SupportingDocument.SECTION_NAME, -1));
            }
        }
    }

    protected void populateCrossListCourses(List<ExportElement> exportElements, CourseInfoWrapper courseInfoWrapper) {
        StringBuilder crossListedCourses = new StringBuilder("");
        for (CourseCrossListingInfo crossListing : courseInfoWrapper.getCourseInfo().getCrossListings()) {

            if (StringUtils.isNotBlank(crossListing.getCourseNumberSuffix()) && StringUtils.isNotBlank(crossListing.getSubjectArea())) {
                crossListedCourses.append(crossListing.getSubjectArea());
                crossListedCourses.append(crossListing.getCourseNumberSuffix());
                crossListedCourses.append(";");
            }
        }
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.CROSS_LISTED_COURSES, crossListedCourses.toString() , CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1));
    }

    protected void populateJointlyOfferedCourses(List<ExportElement> exportElements, CourseInfoWrapper courseInfoWrapper) {

        StringBuilder jointlyOfferedCourses = new StringBuilder("");
        for (CourseJointInfo courseJointInfo : courseInfoWrapper.getCourseInfo().getJoints()) {

            if (StringUtils.isNotBlank(courseJointInfo.getCourseNumberSuffix()) && StringUtils.isNotBlank(courseJointInfo.getSubjectArea())) {
                jointlyOfferedCourses.append(courseJointInfo.getSubjectArea());
                jointlyOfferedCourses.append(courseJointInfo.getCourseNumberSuffix());
                jointlyOfferedCourses.append(";");
            }
        }
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.JOINTLY_OFFERED_COURSES, jointlyOfferedCourses.toString() , CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1));
    }

    protected void populateVersionCodes(List<ExportElement> exportElements, CourseInfoWrapper courseInfoWrapper) {

        StringBuilder versionCodes = new StringBuilder("");
        for (CourseVariationInfo courseVariationInfo : courseInfoWrapper.getCourseInfo().getVariations()) {

            if (StringUtils.isNotBlank(courseVariationInfo.getCourseNumberSuffix()) && StringUtils.isNotBlank(courseVariationInfo.getSubjectArea())) {
                versionCodes.append(courseVariationInfo.getSubjectArea());
                versionCodes.append(courseVariationInfo.getCourseNumberSuffix());
                versionCodes.append(";");
            }
        }
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.VERSION_CODES, versionCodes.toString() , CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1));
    }
}
