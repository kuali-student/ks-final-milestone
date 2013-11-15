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

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
public class SupportingDocumentInfoWrapper implements java.io.Serializable {

	private static final long serialVersionUID = -1L;
    

    protected MultipartFile documentUpload;
    protected String description;

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
}
