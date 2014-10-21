package org.kuali.student.cm.course.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.common.util.CurriculumManagementConstants.Export.FileType;
import org.kuali.student.cm.course.form.wrapper.*;
import org.kuali.student.cm.proposal.form.wrapper.ProposalElementsWrapper;
import org.kuali.student.common.ui.client.util.ExportElement;

import java.util.ArrayList;
import java.util.List;

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
        super(retireCourseWrapper, retireCourseWrapper.getProposalInfo().getName(), exportFileType, true, false);
    }

    /**
     * Constructor.
     *
     * @param retireCourseWrapper The data object to export.
     * @param exportFileType The metadata for the type of file to output.
     * @param saveDocument If 'true' sets the document headers to values which encourage the web browers to display a
     *                     "save as" dialog. Otherwise, sets them values which encourage the browser to open the document..
     * @param isProposal true, if it is a proposal or false
     */
    public ExportRetireCourseHelperImpl(RetireCourseWrapper retireCourseWrapper, FileType exportFileType, boolean saveDocument, boolean isProposal) {
        super(retireCourseWrapper, retireCourseWrapper.getProposalInfo().getName(), exportFileType, saveDocument, isProposal);
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

        RetireCourseProposalInformation(exportElements, retireCourseWrapper);
        populateAuthorsCollaborators(exportElements, retireCourseWrapper);
        populateSupportingDocuments(exportElements, retireCourseWrapper);

        return exportElements;
    }

    /**
     * This method creates exportElement for each field in "Retire Proposal Information" section and adds them to the list.
     *
     * @param exportElements
     * @param retireCourseWrapper
     */
    protected void RetireCourseProposalInformation(List<ExportElement> exportElements, RetireCourseWrapper retireCourseWrapper) {

        if(retireCourseWrapper.getCourseInfo() == null) {
            return;
        }

        RetireCourseReviewProposalDisplay retireCourseReviewProposalDisplay = (RetireCourseReviewProposalDisplay)retireCourseWrapper.getReviewProposalDisplay();

        exportElements.add(populateExportElement(null, null, CurriculumManagementConstants.ProposalViewFieldLabels.RetireProposalInformation.SECTION_NAME, -1));

        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.RetireProposalInformation.PROPOSAL_TITLE,
                retireCourseWrapper.getProposalInfo().getName(), CurriculumManagementConstants.ProposalViewFieldLabels.RetireProposalInformation.SECTION_NAME, -1));

        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.RetireProposalInformation.COURSE_TITLE,
                retireCourseWrapper.getCourseInfo().getCourseTitle(), CurriculumManagementConstants.ProposalViewFieldLabels.RetireProposalInformation.SECTION_NAME, -1));

        String courseNumber = retireCourseWrapper.getCourseInfo().getSubjectArea() + retireCourseWrapper.getCourseInfo().getCourseNumberSuffix();
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.RetireProposalInformation.COURSE_NUMBER,
                courseNumber, CurriculumManagementConstants.ProposalViewFieldLabels.RetireProposalInformation.SECTION_NAME, -1));

        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.RetireProposalInformation.CURRICULUM_OVERSIGHT,
                retireCourseReviewProposalDisplay.getReferenceDataSection().getCurriculumOversightAsString(), CurriculumManagementConstants.ProposalViewFieldLabels.RetireProposalInformation.SECTION_NAME, -1));

        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.RetireProposalInformation.CROSS_LISTED_JOINTLY_OFFERED_COURSE,
                retireCourseReviewProposalDisplay.getReferenceDataSection().getJointlyOfferedAndCrossListedCoursesAsString(), CurriculumManagementConstants.ProposalViewFieldLabels.RetireProposalInformation.SECTION_NAME, -1));

        String proposalRationale = (retireCourseWrapper.getProposalInfo().getRationale() != null) ? retireCourseWrapper.getProposalInfo().getRationale().getPlain() : StringUtils.EMPTY;
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.RetireProposalInformation.PROPOSAL_RATIONALE,
                proposalRationale, CurriculumManagementConstants.ProposalViewFieldLabels.RetireProposalInformation.SECTION_NAME, -1));

        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.RetireProposalInformation.START_TERM,
                retireCourseWrapper.getRetireStartTerm(), CurriculumManagementConstants.ProposalViewFieldLabels.RetireProposalInformation.SECTION_NAME, -1  ));
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.RetireProposalInformation.END_TERM,
                retireCourseReviewProposalDisplay.getRetireCourseSection().getEndTerm(), CurriculumManagementConstants.ProposalViewFieldLabels.RetireProposalInformation.SECTION_NAME, -1));
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.RetireProposalInformation.LAST_TERM,
                retireCourseReviewProposalDisplay.getRetireCourseSection().getLastTerm(), CurriculumManagementConstants.ProposalViewFieldLabels.RetireProposalInformation.SECTION_NAME, -1));
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.RetireProposalInformation.PUBLICATION_YEAR,
                retireCourseReviewProposalDisplay.getRetireCourseSection().getPublicationYear(), CurriculumManagementConstants.ProposalViewFieldLabels.RetireProposalInformation.SECTION_NAME, -1));
        exportElements.add(populateExportElement(CurriculumManagementConstants.ProposalViewFieldLabels.RetireProposalInformation.OTHER_COMMENTS,
                retireCourseWrapper.getRetirementComment().getPlain(), CurriculumManagementConstants.ProposalViewFieldLabels.RetireProposalInformation.SECTION_NAME, -1));
    }

}
