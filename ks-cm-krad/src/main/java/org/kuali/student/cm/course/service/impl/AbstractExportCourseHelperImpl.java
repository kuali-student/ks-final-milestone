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
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.common.util.CurriculumManagementConstants.Export.FileType;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.cm.course.service.ExportCourseHelper;
import org.kuali.student.cm.proposal.form.wrapper.ProposalElementsWrapper;
import org.kuali.student.cm.proposal.form.wrapper.SupportingDocumentInfoWrapper;
import org.kuali.student.common.ui.client.util.ExportElement;
import org.kuali.student.common.ui.server.screenreport.ScreenReportProcessor;
import org.kuali.student.common.ui.server.screenreport.jasper.JasperScreenReportProcessorImpl;
import org.kuali.student.r1.core.workflow.dto.CollaboratorWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * A helper class to export course. Constructs a list of ExportElement based on courseInfoWrapper.
 *
 * @author Kuali Student Team
 */
public abstract class AbstractExportCourseHelperImpl implements ExportCourseHelper {

    private ProposalElementsWrapper wrapper;
    private FileType exportFileType;
    private String fileName;
    private boolean saveDocument = true;
    private boolean isProposal = false;

    private ScreenReportProcessor processor = new JasperScreenReportProcessorImpl();

    /**
     * Constructor.
     *
     * @param wrapper The data object to export.
     * @param exportFileType The metadata for the type of file to output.
     * @param saveDocument If 'true' sets the document headers to values which encourage the web browers to display a
     *                     "save as" dialog. Otherwise, sets them values which encourage the browser to open the document..
     * @param isProposal true, if it is a proposal or false
     */
    public AbstractExportCourseHelperImpl(ProposalElementsWrapper wrapper, String fileName, FileType exportFileType, boolean saveDocument, boolean isProposal) {
        this.wrapper = wrapper;
        this.exportFileType = exportFileType;

        //  Replace anything that isn't a number or a letter with underscore.
        this.fileName = fileName.replaceAll("[^A-Za-z0-9]", "_") + "."  + exportFileType.getFileSuffix();

        this.saveDocument = saveDocument;
        this.isProposal = isProposal;
    }

    public CourseInfoWrapper getCourseInfoWrapper() {
        return (CourseInfoWrapper) this.wrapper;
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

    public boolean isProposal() {
        return isProposal;
    }

    public void setProposal(boolean isProposal) {
        this.isProposal = isProposal;
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
        List<ExportElement> exportElements = constructExportElementBasedOnView(wrapper, isProposal);

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
     * This method creates exportElement for each field in "Authors and Collaborators" section and adds them to the list.
     *
     * @param exportElements
     * @param proposalElementsWrapper
     */
    protected void populateAuthorsCollaborators(List<ExportElement> exportElements, ProposalElementsWrapper proposalElementsWrapper) {

        String sectionTitle = CurriculumManagementConstants.ProposalViewFieldLabels.AuthorsCollaborators.SECTION_NAME;

        exportElements.add(populateExportElement(sectionTitle,
                null, CurriculumManagementConstants.ProposalViewFieldLabels.AuthorsCollaborators.SECTION_NAME, -1));
        for(CollaboratorWrapper collaboratorWrapper : proposalElementsWrapper.getReviewProposalDisplay().getCollaboratorSection().getCollaboratorWrappers())    {

            String displayName = collaboratorWrapper.getLastName() + ", " + collaboratorWrapper.getFirstName();
            displayName = (collaboratorWrapper.isAuthor()) ? displayName + " (Author)" : displayName;
            String actionRequest = (new StringBuilder("<b>")
                                        .append(CurriculumManagementConstants.ProposalViewFieldLabels.AuthorsCollaborators.ACTION_REQUEST)
                                        .append("</b>    ").append(collaboratorWrapper.getAction())).toString();
            String permission = (new StringBuilder("<b>")
                                    .append(CurriculumManagementConstants.ProposalViewFieldLabels.AuthorsCollaborators.PERMISSION)
                                    .append("</b>       ").append(collaboratorWrapper.getPermission())).toString();
            exportElements.add(populateExportElement(null,
                    displayName, CurriculumManagementConstants.ProposalViewFieldLabels.AuthorsCollaborators.SECTION_NAME, -1));
            exportElements.add(populateExportElement(null, permission, CurriculumManagementConstants.ProposalViewFieldLabels.AuthorsCollaborators.SECTION_NAME, -1));
            exportElements.add(populateExportElement(null, actionRequest, CurriculumManagementConstants.ProposalViewFieldLabels.AuthorsCollaborators.SECTION_NAME, -1));
        }
    }

    /**
     * This method creates exportElement for each field in "Supporting Documents" section and adds them to the list.
     *
     * @param exportElements
     * @param proposalElementsWrapper
     */
    protected void populateSupportingDocuments(List<ExportElement> exportElements, ProposalElementsWrapper proposalElementsWrapper) {

        String sectionTitle = CurriculumManagementConstants.ProposalViewFieldLabels.SupportingDocument.SECTION_NAME;
        exportElements.add(populateExportElement(sectionTitle, null, CurriculumManagementConstants.ProposalViewFieldLabels.SupportingDocument.SECTION_NAME, -1));

        for (SupportingDocumentInfoWrapper supportingDocumentInfoWrapper : proposalElementsWrapper.getSupportingDocs()) {
            String description = supportingDocumentInfoWrapper.getDescription();
            String documentName = supportingDocumentInfoWrapper.getDocumentName();
            if (StringUtils.isNotBlank(description) || StringUtils.isNotBlank(documentName)){
                exportElements.add(populateExportElement(null, documentName + " " + description, CurriculumManagementConstants.ProposalViewFieldLabels.SupportingDocument.SECTION_NAME, -1));
            }
        }
    }

    /**
     *  Here we remove the trailing delimiter in a String object.
     * @param value
     * @return String
     */
    protected String removeEndDelimiter(String value) {
        if (StringUtils.isNotBlank(value) && value.endsWith(CurriculumManagementConstants.COLLECTION_ITEMS_DELIMITER)) {
            value = value.substring(0, value.length() - 2);
        }
        return value;
    }

}
