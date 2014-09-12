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
import org.kuali.student.common.ui.client.util.ExportElement;
import org.kuali.student.common.ui.server.screenreport.ScreenReportProcessor;
import org.kuali.student.common.ui.server.screenreport.jasper.JasperScreenReportProcessorImpl;
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

    private ScreenReportProcessor processor = new JasperScreenReportProcessorImpl();

    /**
     * Constructor.
     *
     * @param wrapper The data object to export.
     * @param exportFileType The metadata for the type of file to output.
     * @param saveDocument If 'true' sets the document headers to values which encourage the web browers to display a
     *                     "save as" dialog. Otherwise, sets them values which encourage the browser to open the document..
     */
    public AbstractExportCourseHelperImpl(ProposalElementsWrapper wrapper, String fileName, FileType exportFileType, boolean saveDocument) {
        this.wrapper = wrapper;
        this.exportFileType = exportFileType;

        //  Replace anything that isn't a number or a letter with underscore.
        this.fileName = fileName.replaceAll("[^A-Za-z0-9]", "_") + "."  + exportFileType.getFileSuffix();

        this.saveDocument = saveDocument;
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
        List<ExportElement> exportElements = constructExportElementBasedOnView(wrapper, false);

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

}
