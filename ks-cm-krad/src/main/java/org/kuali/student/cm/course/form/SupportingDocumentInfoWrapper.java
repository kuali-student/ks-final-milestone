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

/**
 *
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
public class SupportingDocumentInfoWrapper implements java.io.Serializable {

	private static final long serialVersionUID = -1L;
    

    protected String documentUpload;

    /**
     * Gets the value of documentUpload
     *
     * @return the value of documentUpload
     */
    public final String getDocumentUpload() {
        return this.documentUpload;
    }

    /**
     * Sets the value of documentUpload
     *
     * @param argDocumentUpload Value to assign to this.documentUpload
     */
    public final void setDocumentUpload(final String argDocumentUpload) {
        this.documentUpload = argDocumentUpload;
    }

}
