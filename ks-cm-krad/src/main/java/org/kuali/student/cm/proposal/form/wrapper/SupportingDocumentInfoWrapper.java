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
package org.kuali.student.cm.proposal.form.wrapper;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.cm.uif.util.DTOWrapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

/**
 *
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
public class SupportingDocumentInfoWrapper implements DTOWrapper {

	private static final long serialVersionUID = -1L;
    
    private MultipartFile documentUpload;
    private String description;
    private String documentId;
    private String documentName;
    private byte[] uploadedDoc;

    /**
     * Gets the value of documentUpload
     *
     * @return the value of documentUpload
     */
    public MultipartFile getDocumentUpload() {
        return this.documentUpload;
    }

    /**
     * Sets the value of documentUpload
     *
     * @param argDocumentUpload Value to assign to this.documentUpload
     */
    public void setDocumentUpload(final MultipartFile argDocumentUpload) {
        this.documentUpload = argDocumentUpload;
    }

    /**
     * Gets the value of description
     *
     * @return the value of description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the value of description
     *
     * @param argDescription Value to assign to this.description
     */
    public void setDescription(final String argDescription) {
        this.description = argDescription;
    }

    /**
     * Gets the value of documentId
     * @return  value of documentId
     */
    public String getDocumentId() {
        return documentId;
    }

    /**
     * Sets the value of documentId
     * @param argDocumentId
     */
    public void setDocumentId(String argDocumentId) {
        this.documentId = argDocumentId;
    }

    /**
     * Gets the value of documentName
     * @return value of documentName
     */
    public String getDocumentName() {
        return documentName;
    }

    /**
     * Sets the value of documentName
     * @param argDocumentName
     */
    public void setDocumentName(String argDocumentName) {
        this.documentName = argDocumentName;
    }

    @Override
    public boolean isNewDto() {
        return StringUtils.isBlank(documentId);
    }

    public byte[] getUploadedDoc() {
        return uploadedDoc;
    }

    /**
     * Once user uploads a file, we copy the bytes from MultipartFile to persist during save operation.
     * MultipartFile will be empty once it hits the server. We need to retain the uploaded doc until
     * the user clicks on save and then we persist all the uploaded files.
     *
     * @param uploadedDoc
     */
    public void setUploadedDoc(byte[] uploadedDoc) {
        if(uploadedDoc != null) {
           this.uploadedDoc = Arrays.copyOf(uploadedDoc, uploadedDoc.length);
        }
    }
}
