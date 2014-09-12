package org.kuali.student.cm.course.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.RuleTypeInfo;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.common.util.CurriculumManagementConstants.Export.FileType;
import org.kuali.student.cm.course.form.wrapper.*;
import org.kuali.student.cm.proposal.form.wrapper.ProposalElementsWrapper;
import org.kuali.student.cm.proposal.form.wrapper.SupportingDocumentInfoWrapper;
import org.kuali.student.common.ui.client.util.ExportElement;
import org.kuali.student.r1.core.workflow.dto.CollaboratorWrapper;
import org.kuali.student.r2.lum.clu.dto.CluInstructorInfo;
import org.kuali.student.r2.lum.course.dto.*;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Kuali Student Team
 */
public class ExportRetireCourseHelperImpl extends AbstractExportCourseHelperImpl {

    /**
     * Constructor. Sets headers to "save as".
     *
     * @param retireCourseWrapper The data object to export.
     * @param exportFileType The metadata for the type of file to output.
     */
    public ExportRetireCourseHelperImpl(RetireCourseWrapper retireCourseWrapper, FileType exportFileType) {
        super(retireCourseWrapper, retireCourseWrapper.getProposalInfo().getName(), exportFileType, true);
    }

    /**
     * Constructor.
     *
     * @param retireCourseWrapper The data object to export.
     * @param exportFileType The metadata for the type of file to output.
     * @param saveDocument If 'true' sets the document headers to values which encourage the web browers to display a
     *                     "save as" dialog. Otherwise, sets them values which encourage the browser to open the document..
     */
    public ExportRetireCourseHelperImpl(RetireCourseWrapper retireCourseWrapper, FileType exportFileType, boolean saveDocument) {
        super(retireCourseWrapper, retireCourseWrapper.getProposalInfo().getName(), exportFileType, saveDocument);
    }

    /**
     * This method constructs list of exportElement based on each field in courseInfoWrapper.
     *
     * @param wrapper
     * @return List of constructed exportElement based on course/proposal.
     */
    public List<ExportElement> constructExportElementBasedOnView(ProposalElementsWrapper wrapper, boolean isProposal ) {
        RetireCourseWrapper retireCourseWrapper = (RetireCourseWrapper) wrapper;

        List<ExportElement> exportElements = new ArrayList<>();

        populateCourseInformation(exportElements, retireCourseWrapper);
        populateGovernance(exportElements, retireCourseWrapper);
        populateActiveDates(exportElements,retireCourseWrapper);
        populateAuthorsCollaborators(exportElements, retireCourseWrapper);
        populateSupportingDocuments(exportElements, retireCourseWrapper);

        return exportElements;
    }

    /**
     * This method creates exportElement for each field in "Course Information" section and adds them to the list.
     *
     * @param exportElements
     * @param retireCourseWrapper
     */
    protected void populateCourseInformation(List<ExportElement> exportElements, RetireCourseWrapper retireCourseWrapper) {

        if(retireCourseWrapper.getCourseInfo() == null) {
            return;
        }

        String courseTitle = retireCourseWrapper.getCourseInfo().getCourseTitle();
        ExportElement exportCourseTitle = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.COURSE_TITLE, courseTitle, CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1);
        exportElements.add(exportCourseTitle);

        String proposalTitle = retireCourseWrapper.getProposalInfo().getName();
        ExportElement exportProposalTitle = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.COURSE_TITLE, proposalTitle, CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1);
        exportElements.add(exportProposalTitle);

        String transcriptCourseTitle = retireCourseWrapper.getCourseInfo().getTranscriptTitle();
        ExportElement exportTranscriptCourseTitle = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.TRANSCRIPT_COURSE_TITLE, transcriptCourseTitle, CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1);
        exportElements.add(exportTranscriptCourseTitle);

        String subjectCode = retireCourseWrapper.getCourseInfo().getSubjectArea();
        ExportElement exportSubjectCode = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SUBJECT_CODE, subjectCode, CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1);
        exportElements.add(exportSubjectCode);

        String courseNumber = retireCourseWrapper.getCourseInfo().getCourseNumberSuffix();
        ExportElement exportCourseNumber = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.COURSE_NUMBER, courseNumber , CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1);
        exportElements.add(exportCourseNumber);

        String description  = (retireCourseWrapper.getCourseInfo().getDescr() != null) ? retireCourseWrapper.getCourseInfo().getDescr().getPlain() : "";
        ExportElement exportDescription = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.DESCRIPTION_AND_RATIONALE, description , CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1);
        exportElements.add(exportDescription);

        String proposalRationale = (retireCourseWrapper.getProposalInfo().getRationale() != null) ? retireCourseWrapper.getProposalInfo().getRationale().getPlain() : "";
        ExportElement exportProposalRationale = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.PROPOSAL_RATIONALE, proposalRationale, CurriculumManagementConstants.ProposalViewFieldLabels.CourseInformation.SECTION_NAME, -1);
        exportElements.add(exportProposalRationale);
    }

    /**
     * This method creates exportElement for each field in "Governance" section and adds them to the list.
     *
     * @param exportElements
     * @param retireCourseWrapper
     */
    protected void populateGovernance(List<ExportElement> exportElements, RetireCourseWrapper retireCourseWrapper) {

        if(retireCourseWrapper.getCourseInfo() == null) {
            return;
        }

        StringBuilder campusLocations = new StringBuilder();
        for(String campusLocation : retireCourseWrapper.getCourseInfo().getCampusLocations()){
            campusLocations.append(campusLocation);
            campusLocations.append(";");
        }
        ExportElement exportCampusLocation = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.Governance.CAMPUS_LOCATION, campusLocations.toString(),CurriculumManagementConstants.ProposalViewFieldLabels.Governance.SECTION_NAME, -1);
        exportElements.add(exportCampusLocation);

        String curriculumOversight = "";
        for(CourseCreateUnitsContentOwner courseCreateUnitsContentOwner  : retireCourseWrapper.getUnitsContentOwner()){
            if (StringUtils.isNotBlank(courseCreateUnitsContentOwner.getRenderHelper().getOrgLongName())){
                curriculumOversight = curriculumOversight  + courseCreateUnitsContentOwner.getRenderHelper().getOrgLongName() + ";";
            }
        }
        ExportElement exportCurriculumOversight = populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.Governance.CURRICULUM_OVERSIGHT, curriculumOversight,CurriculumManagementConstants.ProposalViewFieldLabels.Governance.SECTION_NAME, -1);
        exportElements.add(exportCurriculumOversight);
    }

    /**
     * This method creates exportElement for each field in "Active Dates" section and adds them to the list.
     *
     * @param exportElements
     * @param retireCourseWrapper
     */
    protected void populateActiveDates(List<ExportElement> exportElements, RetireCourseWrapper retireCourseWrapper) {
        String startTerm = retireCourseWrapper.getRetireStartTerm();
        String endTerm = ((RetireCourseReviewProposalDisplay)retireCourseWrapper.getReviewProposalDisplay()).getRetireCourseSection().getEndTerm();
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.ActiveDates.START_TERM, startTerm , CurriculumManagementConstants.ProposalViewFieldLabels.ActiveDates.SECTION_NAME, -1  ));
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.ActiveDates.END_TERM, endTerm, CurriculumManagementConstants.ProposalViewFieldLabels.ActiveDates.SECTION_NAME, -1));
    }

}
