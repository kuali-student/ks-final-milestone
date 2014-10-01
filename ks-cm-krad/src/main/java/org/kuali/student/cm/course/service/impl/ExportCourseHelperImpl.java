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
import org.kuali.student.cm.course.form.wrapper.LoDisplayInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.ResultValuesGroupInfoWrapper;
import org.kuali.student.cm.proposal.form.wrapper.ProposalElementsWrapper;
import org.kuali.student.common.ui.client.util.ExportElement;
import org.kuali.student.r2.lum.clu.dto.CluInstructorInfo;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.r2.lum.course.dto.CourseJointInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Kuali Student Team
 */
public class ExportCourseHelperImpl extends AbstractExportCourseHelperImpl {

    /**
     * Constructor. Sets headers to "save as".
     *
     * @param courseInfoWrapper The data object to export.
     * @param exportFileType The metadata for the type of file to output.
     */
    public ExportCourseHelperImpl(CourseInfoWrapper courseInfoWrapper, FileType exportFileType) {
        super(courseInfoWrapper, courseInfoWrapper.getCourseInfo().getCourseTitle(),exportFileType, true, false);
    }

    /**
     * Constructor.
     *
     * @param courseInfoWrapper The data object to export.
     * @param exportFileType The metadata for the type of file to output.
     * @param saveDocument If 'true' sets the document headers to values which encourage the web browers to display a
     *                     "save as" dialog. Otherwise, sets them values which encourage the browser to open the document..
     *  @param isProposal true, if it is a proposal or false
     */
    public ExportCourseHelperImpl(CourseInfoWrapper courseInfoWrapper, FileType exportFileType, boolean saveDocument, boolean isProposal) {
        super(courseInfoWrapper, courseInfoWrapper.getCourseInfo().getCourseTitle(),exportFileType, saveDocument, isProposal);
    }

    /**
     * This method constructs list of exportElement based on each field in courseInfoWrapper.
     *
     * @param wrapper
     * @return List of constructed exportElement based on course/proposal.
     */
    public List<ExportElement> constructExportElementBasedOnView(ProposalElementsWrapper wrapper, boolean isProposal ) {

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) wrapper;

        List<ExportElement> exportElements = new ArrayList<ExportElement>();

        populateCourseInformation(exportElements, courseInfoWrapper , isProposal);
        populateGovernance(exportElements, courseInfoWrapper);
        populateCourseLogistics(exportElements, courseInfoWrapper);
        populateLearningObjectives(exportElements, courseInfoWrapper);
        populateCourseRequisites(exportElements, courseInfoWrapper);
        populateActiveDates(exportElements, courseInfoWrapper);
        if (isProposal) {
            populateFinancials(exportElements, courseInfoWrapper);
            populateAuthorsCollaborators(exportElements, courseInfoWrapper);
            populateSupportingDocuments(exportElements, courseInfoWrapper);
        }
        return exportElements;
    }

    /**
     * This method creates exportElement for each field in "Course Information" section and adds them to the list.
     *
     * @param exportElements
     * @param courseInfoWrapper
     */
    protected void populateCourseInformation(List<ExportElement> exportElements, CourseInfoWrapper courseInfoWrapper, boolean isProposal) {

        if (courseInfoWrapper.getCourseInfo() == null) {
            return;
        }

        exportElements.add(populateExportElement(null, null, CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1));

        if (isProposal) {
            String proposalTitle = courseInfoWrapper.getProposalInfo().getName();
            exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.PROPOSAL_TITLE,
                    proposalTitle, CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1));
        }

        String courseTitle = courseInfoWrapper.getCourseInfo().getCourseTitle();
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.COURSE_TITLE,
                courseTitle, CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1));

        String transcriptCourseTitle = courseInfoWrapper.getCourseInfo().getTranscriptTitle();
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.TRANSCRIPT_COURSE_TITLE,
                transcriptCourseTitle, CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1));

        String subjectCode = courseInfoWrapper.getReviewProposalDisplay().getCourseSection().getSubjectArea();
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SUBJECT_CODE, subjectCode,
                 CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1));

        String courseNumber = courseInfoWrapper.getCourseInfo().getCourseNumberSuffix();
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.COURSE_NUMBER, courseNumber ,
                 CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1));

        populateCrossListCourses(exportElements,courseInfoWrapper);
        populateJointlyOfferedCourses(exportElements,courseInfoWrapper);
        populateVersionCodes(exportElements,courseInfoWrapper);

        StringBuilder instructors = new StringBuilder();
        for (CluInstructorInfo instructor : courseInfoWrapper.getInstructorWrappers()) {
            if (StringUtils.isNotBlank(((CluInstructorInfoWrapper)instructor).getDisplayName())) {
                instructors.append(((CluInstructorInfoWrapper)instructor).getDisplayName());
                instructors.append(CurriculumManagementConstants.COLLECTION_ITEMS_DELIMITER);
            }
        }

        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.INSTRUCTOR,
                removeEndDelimiter(instructors.toString()) , CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1));

        String description  = (courseInfoWrapper.getCourseInfo().getDescr() != null) ? courseInfoWrapper.getCourseInfo().getDescr().getPlain() : StringUtils.EMPTY;
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.DESCRIPTION, description,
                CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1));

        if (isProposal) {
            String proposalRationale = (courseInfoWrapper.getProposalInfo().getRationale() != null) ? courseInfoWrapper.getProposalInfo().getRationale().getPlain() : StringUtils.EMPTY;
            exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.PROPOSAL_RATIONALE,
                    proposalRationale, CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1));
        }
    }

    /**
     * This method creates exportElement for each field in "Governance" section and adds them to the list.
     *
     * @param exportElements
     * @param courseInfoWrapper
     */
    protected void populateGovernance(List<ExportElement> exportElements, CourseInfoWrapper courseInfoWrapper) {

        if (courseInfoWrapper.getCourseInfo() == null) {
            return;
        }

        StringBuilder campusLocations = new StringBuilder();
        for (String campusLocation : courseInfoWrapper.getReviewProposalDisplay().getGovernanceSection().getCampusLocations()) {
            campusLocations.append(campusLocation);
            campusLocations.append(CurriculumManagementConstants.COLLECTION_ITEMS_DELIMITER);
        }
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.Governance.CAMPUS_LOCATION,
                removeEndDelimiter(campusLocations.toString()), CurriculumManagementConstants.ProposalViewFieldLabels.Governance.SECTION_NAME, -1));

        String curriculumOversight = StringUtils.EMPTY;
        for (CourseCreateUnitsContentOwner courseCreateUnitsContentOwner : courseInfoWrapper.getUnitsContentOwner()) {
            if (StringUtils.isNotBlank(courseCreateUnitsContentOwner.getRenderHelper().getOrgLongName())) {
                curriculumOversight = curriculumOversight + courseCreateUnitsContentOwner.getRenderHelper().getOrgLongName() + CurriculumManagementConstants.COLLECTION_ITEMS_DELIMITER;
            }
        }
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.Governance.CURRICULUM_OVERSIGHT,
                removeEndDelimiter(curriculumOversight), CurriculumManagementConstants.ProposalViewFieldLabels.Governance.SECTION_NAME, -1));

        StringBuilder  administeringOrganizations = new StringBuilder();
        for (String administeringOrganization : courseInfoWrapper.getReviewProposalDisplay().getGovernanceSection().getAdministeringOrganization()) {
            administeringOrganizations.append(administeringOrganization);
            administeringOrganizations.append(CurriculumManagementConstants.COLLECTION_ITEMS_DELIMITER);
        }
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.Governance.ADMINISTERING_ORGANIZATIONS,
                removeEndDelimiter(administeringOrganizations.toString()), CurriculumManagementConstants.ProposalViewFieldLabels.Governance.SECTION_NAME, -1));
    }

    /**
     *
     * This method creates exportElement for each field in "Course Logistics" section and adds them to the list.
     *
     * @param exportElements
     * @param courseInfoWrapper
     */
    protected void populateCourseLogistics(List<ExportElement> exportElements, CourseInfoWrapper courseInfoWrapper) {

        if (courseInfoWrapper.getCourseInfo() == null) {
            return;
        }

        StringBuilder termOffered = new StringBuilder();
        for (String term :courseInfoWrapper.getCourseInfo().getTermsOffered()) {
            if (term != null) {
                termOffered.append(term.substring(term.lastIndexOf('.') + 1));
                termOffered.append(CurriculumManagementConstants.COLLECTION_ITEMS_DELIMITER);
            }
        }
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.TERM,
                removeEndDelimiter(termOffered.toString()), CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME, -1));

        String duration =  StringUtils.EMPTY;
        if (courseInfoWrapper.getCourseInfo().getDuration() != null) {
            if (courseInfoWrapper.getCourseInfo().getDuration().getTimeQuantity() != null &&
                    courseInfoWrapper.getCourseInfo().getDuration().getAtpDurationTypeKey() != null) {
                String durationCount = courseInfoWrapper.getCourseInfo().getDuration().getTimeQuantity().toString();
                String durationType = courseInfoWrapper.getCourseInfo().getDuration().getAtpDurationTypeKey();
                durationType = durationType.substring(durationType.lastIndexOf('.') + 1);
                duration = durationCount + " " + durationType + CurriculumManagementConstants.COLLECTION_ITEM_PLURAL_END;
            }
        }
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.DURATION,
                duration, CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME, -1));

        StringBuilder assessmentScale = new StringBuilder();
        for (String gradingOption : courseInfoWrapper.getReviewProposalDisplay().getCourseLogisticsSection().getGradingOptions()) {
            assessmentScale.append(gradingOption.substring(gradingOption.lastIndexOf('.') + 1));
            assessmentScale.append(CurriculumManagementConstants.COLLECTION_ITEMS_DELIMITER);
        }
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.ASSESSMENT_SCALE,
                removeEndDelimiter(assessmentScale.toString()), CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME, -1));

        String audit = (courseInfoWrapper.isAudit()) ? CurriculumManagementConstants.YES_LABEL : CurriculumManagementConstants.NO_LABEL;
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.AUDIT, audit,
                CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME, -1));

        String passFail = (courseInfoWrapper.isPassFail()) ? CurriculumManagementConstants.YES_LABEL : CurriculumManagementConstants.NO_LABEL;
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.PASS_FAIL_TRANSCRIPT_GRADE,
                passFail, CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME, -1));

        String finalExamStatus = courseInfoWrapper.getReviewProposalDisplay().getCourseLogisticsSection().getFinalExamStatus();
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.FINAL_EXAM_STATUS,
                finalExamStatus, CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME, -1));

        String finalExamStatusRationale = courseInfoWrapper.getReviewProposalDisplay().getCourseLogisticsSection().getFinalExamStatusRationale();
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.FINAL_EXAM_RATIONALE,
                finalExamStatusRationale, CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME, -1));

        populateOutcome(exportElements,courseInfoWrapper);
        populateCourseFormat(exportElements, courseInfoWrapper);
    }

    /**
     *  populate the Outcomes
     * @param exportElements
     * @param courseInfoWrapper
     */
    protected void populateOutcome(List<ExportElement> exportElements, CourseInfoWrapper courseInfoWrapper) {

        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.OUTCOME + 's', null,
                CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME, -1));

        int counter = 1;
        for (ResultValuesGroupInfoWrapper resultValuesGroupInfoWrapper : courseInfoWrapper.getCreditOptionWrappers()) {

            String typeKey = (resultValuesGroupInfoWrapper.getTypeKey() != null) ? resultValuesGroupInfoWrapper.getTypeKey() : StringUtils.EMPTY;
            if (typeKey.length() != 0) {
                typeKey = typeKey.substring(typeKey.lastIndexOf('.') + 1);
                typeKey = typeKey.substring(0,1).toUpperCase() + typeKey.substring(1);
            }
            String creditOption = (resultValuesGroupInfoWrapper.getUiHelper() != null) ? (resultValuesGroupInfoWrapper.getUiHelper().getResultValue()) : StringUtils.EMPTY;
            if(!StringUtils.isEmpty(typeKey) && !StringUtils.isEmpty(creditOption))  {
                exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.OUTCOME + counter, null,
                        CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME, -1));
                exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.TYPE, typeKey,
                        CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME, -1));
                exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.CREDITS, creditOption,
                        CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME, -1));
            }
            counter ++;
        }
    }

    /**
     *  Populate the Course Formats
     * @param exportElements
     * @param courseInfoWrapper
     */
    protected void populateCourseFormat(List<ExportElement> exportElements, CourseInfoWrapper courseInfoWrapper) {

        int counter = 1;
        for (FormatInfo formatInfo : courseInfoWrapper.getFormats()) {

            exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.COURSE_FORMAT + counter,
                    null, CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME, -1));

            int activityCounter = 1;
            for (ActivityInfo activityInfo : formatInfo.getActivities()) {

                exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.ACTIVITY + activityCounter,
                        null, CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME, -1));

                String activityType = activityInfo.getTypeKey();
                activityType = StringUtils.substringAfterLast(activityType, ".");
                exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.ACTIVITY_TYPE,
                        activityType, CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME, -1));

                String contactHours = StringUtils.EMPTY;
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
                exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.CONTACT_HOURS, contactHours,
                        CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME, -1));

                String duration = StringUtils.EMPTY;
                if (activityInfo.getDuration() != null) {
                    if (StringUtils.isNotBlank(activityInfo.getDuration().getAtpDurationTypeKey()) &&
                            activityInfo.getDuration().getTimeQuantity() != null) {
                        String activityDurationType =   activityInfo.getDuration().getAtpDurationTypeKey();
                        activityDurationType = activityDurationType.substring(activityDurationType.lastIndexOf('.') + 1);
                        String activityDurationCount = activityInfo.getDuration().getTimeQuantity().toString();
                        duration = activityDurationCount + " " + activityDurationType + CurriculumManagementConstants.COLLECTION_ITEM_PLURAL_END;
                    }
                }
                exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.DURATION, duration,
                        CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME, -1));

                String anticipatedClassSize = StringUtils.EMPTY;
                if (activityInfo.getDefaultEnrollmentEstimate() != null) {
                    anticipatedClassSize = activityInfo.getDefaultEnrollmentEstimate().toString();
                }
                exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.CLASS_SIZE,
                        anticipatedClassSize, CurriculumManagementConstants.ProposalViewFieldLabels.CourseLogistics.SECTION_NAME, -1));
                activityCounter++;
            }
            counter ++;
        }
    }

    /**
     * Here we construct the HTML List for Learning Objectives
     *
     * @param exportElements
     * @param courseInfoWrapper
     */
    protected void populateLearningObjectives(List<ExportElement> exportElements, CourseInfoWrapper courseInfoWrapper) {

        if(courseInfoWrapper.getCourseInfo() == null) {
            return;
        }
        StringBuilder loList = new StringBuilder();
        int firstLO = 0;
        int prevIndentLevel = 0;

        if (!courseInfoWrapper.getLoDisplayWrapperModel().getLoWrappers().isEmpty()) {
            loList.append("<ul>");
            for (LoDisplayInfoWrapper loDisplayInfo : courseInfoWrapper.getLoDisplayWrapperModel().getLoWrappers()) {
                String loDescription = loDisplayInfo.getTitleAndCategoriesAsString();
                if (loDisplayInfo.getIndentLevel() == 0) {
                    if (prevIndentLevel == 0 && firstLO == 0) {
                        loList.append("<li>").append(loDescription);
                        firstLO++;
                    } else if (prevIndentLevel == 0) {
                        loList.append("</li><li>").append(loDescription);
                    } else {
                        for (int k = 0;k < prevIndentLevel; k++) {
                            loList.append("</li></ul>");
                        }
                        loList.append("<li>").append(loDescription);
                    }
                } else {
                    if (prevIndentLevel < loDisplayInfo.getIndentLevel()) {
                        loList.append("<ul>");
                    } else if (prevIndentLevel > loDisplayInfo.getIndentLevel()) {
                        loList.append("</li></ul></li>");
                    } else {
                        loList.append("</li>");
                    }
                    loList.append("<li>").append(loDescription);
                }
                prevIndentLevel = loDisplayInfo.getIndentLevel();
            }
            loList.append("</li></ul>");
        }
        exportElements.add(populateExportElement(null, loList.toString(), CurriculumManagementConstants.ProposalViewFieldLabels.LearningObjectives.SECTION_NAME, -1));
    }

    /**
     * This method creates exportElement for each field in "Course Requisites" section and adds them to the list.
     *
     * @param exportElements
     * @param courseInfoWrapper
     */
    protected void populateCourseRequisites(List<ExportElement> exportElements, CourseInfoWrapper courseInfoWrapper) {

        for (AgendaEditor agendaEditor : courseInfoWrapper.getAgendas()) {
            String agendaDescription = agendaEditor.getAgendaTypeInfo().getDescription();
            exportElements.add(populateExportElement(null, agendaDescription , CurriculumManagementConstants.ProposalViewFieldLabels.CourseRequisites.SECTION_NAME, -1));
            Iterator iterator = agendaEditor.getRuleEditors().entrySet().iterator();

            while (iterator.hasNext()) {
                RuleEditor ruleEditor = (RuleEditor)(((Map.Entry)iterator.next()).getValue());

                if (ruleEditor.getProposition() != null) {
                    String typeId = ruleEditor.getTypeId();

                    for (RuleTypeInfo ruleTypeInfo : agendaEditor.getAgendaTypeInfo().getRuleTypes()) {

                        if (StringUtils.equals(ruleTypeInfo.getId(), typeId)) {
                            String description = ruleTypeInfo.getDescription();
                            exportElements.add(populateExportElement(null, description, CurriculumManagementConstants.ProposalViewFieldLabels.CourseRequisites.SECTION_NAME, -1));
                            Iterator naturalLanguageIterator = ((PropositionEditor)(ruleEditor.getProposition())).getNaturalLanguage().entrySet().iterator();

                            while (naturalLanguageIterator.hasNext()) {
                                String naturalLanguage = (String)(((Map.Entry)(naturalLanguageIterator.next())).getValue());
                                exportElements.add(populateExportElement(null, naturalLanguage, CurriculumManagementConstants.ProposalViewFieldLabels.CourseRequisites.SECTION_NAME, -1));

                                for (PropositionEditor luPropositionEditor : (((PropositionEditor) (ruleEditor.getProposition())).getCompoundEditors())) {
                                    populateEachRule(exportElements, luPropositionEditor);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     *  Here we Iterate through the Course Requisites rules.
     * @param exportElements
     * @param luPropositionEditor
     */
    protected  void populateEachRule(List<ExportElement> exportElements, PropositionEditor luPropositionEditor) {
        Iterator compoundEditorsNaturalLanguage = luPropositionEditor.getNaturalLanguage().entrySet().iterator();

        while (compoundEditorsNaturalLanguage.hasNext()) {
            String ceNaturalLanguage = (String)((Map.Entry)compoundEditorsNaturalLanguage.next()).getValue();
            exportElements.add(populateExportElement(null, ceNaturalLanguage, CurriculumManagementConstants.ProposalViewFieldLabels.CourseRequisites.SECTION_NAME, -1));
        }
        for (PropositionEditor subLUPropositionEditor : luPropositionEditor.getCompoundEditors()) {
            populateEachRule(exportElements,subLUPropositionEditor) ;
        }
    }

    /**
     * This method creates exportElement for each field in "Active Dates" section and adds them to the list.
     *
     * @param exportElements
     * @param courseInfoWrapper
     */
    protected void populateActiveDates(List<ExportElement> exportElements, CourseInfoWrapper courseInfoWrapper) {

        if (courseInfoWrapper.getCourseInfo() == null) {
            return;
        }

        String startTerm = courseInfoWrapper.getReviewProposalDisplay().getActiveDatesSection().getStartTerm();
        String endTerm  = courseInfoWrapper.getReviewProposalDisplay().getActiveDatesSection().getEndTerm();
        String isPilotCourse = (courseInfoWrapper.getCourseInfo().isPilotCourse()) ? CurriculumManagementConstants.YES_LABEL : CurriculumManagementConstants.NO_LABEL;
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.ActiveDates.START_TERM, startTerm,
                CurriculumManagementConstants.ProposalViewFieldLabels.ActiveDates.SECTION_NAME, -1));
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.ActiveDates.END_TERM, endTerm,
                CurriculumManagementConstants.ProposalViewFieldLabels.ActiveDates.SECTION_NAME, -1));
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.ActiveDates.PILOT_COURSE, isPilotCourse,
                CurriculumManagementConstants.ProposalViewFieldLabels.ActiveDates.SECTION_NAME, -1));
    }

    /**
     * Populates the Financials info
     *
     * @param exportElements
     * @param courseInfoWrapper
     */
    protected void populateFinancials(List<ExportElement> exportElements, CourseInfoWrapper courseInfoWrapper) {

        if (courseInfoWrapper.getCourseInfo() == null) {
            return;
        }
        String feeJustification = StringUtils.EMPTY;
        if (courseInfoWrapper.getCourseInfo().getFeeJustification() != null) {
            feeJustification = courseInfoWrapper.getCourseInfo().getFeeJustification().getPlain();
        }
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.Financials.JUSTIFICATION_OF_FEES,
                feeJustification, CurriculumManagementConstants.ProposalViewFieldLabels.Financials.SECTION_NAME, -1));
    }

    /**
     *  populate the Cross listed courses
     * @param exportElements
     * @param courseInfoWrapper
     */
    protected void populateCrossListCourses(List<ExportElement> exportElements, CourseInfoWrapper courseInfoWrapper) {
        StringBuilder crossListedCourses = new StringBuilder();
        for (CourseCrossListingInfo crossListing : courseInfoWrapper.getCourseInfo().getCrossListings()) {

            if (StringUtils.isNotBlank(crossListing.getCourseNumberSuffix()) && StringUtils.isNotBlank(crossListing.getSubjectArea())) {
                crossListedCourses.append(crossListing.getSubjectArea());
                crossListedCourses.append(crossListing.getCourseNumberSuffix());
                crossListedCourses.append(CurriculumManagementConstants.COLLECTION_ITEMS_DELIMITER);
            }
        }
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.CROSS_LISTED_COURSES,
                removeEndDelimiter(crossListedCourses.toString()) , CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1));
    }

    /**
     *  populate the Jointly Offered Courses
     * @param exportElements
     * @param courseInfoWrapper
     */
    protected void populateJointlyOfferedCourses(List<ExportElement> exportElements, CourseInfoWrapper courseInfoWrapper) {

        StringBuilder jointlyOfferedCourses = new StringBuilder();
        for (CourseJointInfo courseJointInfo : courseInfoWrapper.getCourseInfo().getJoints()) {

            if (StringUtils.isNotBlank(courseJointInfo.getCourseNumberSuffix()) && StringUtils.isNotBlank(courseJointInfo.getSubjectArea())) {
                jointlyOfferedCourses.append(courseJointInfo.getSubjectArea());
                jointlyOfferedCourses.append(courseJointInfo.getCourseNumberSuffix());
                jointlyOfferedCourses.append(CurriculumManagementConstants.COLLECTION_ITEMS_DELIMITER);
            }
        }
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.JOINTLY_OFFERED_COURSES,
                removeEndDelimiter(jointlyOfferedCourses.toString()) , CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1));
    }

    /**
     *  populate the version codes
     * @param exportElements
     * @param courseInfoWrapper
     */
    protected void populateVersionCodes(List<ExportElement> exportElements, CourseInfoWrapper courseInfoWrapper) {

        StringBuilder versionCodes = new StringBuilder();
        for (String courseVariation : courseInfoWrapper.getReviewProposalDisplay().getCourseSection().getVariations()) {

            if (StringUtils.isNotBlank(courseVariation)) {
                versionCodes.append(courseVariation);
                versionCodes.append(CurriculumManagementConstants.COLLECTION_ITEMS_DELIMITER);
            }
        }
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.VERSION_CODES,
                removeEndDelimiter(versionCodes.toString()), CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1));
    }
}
