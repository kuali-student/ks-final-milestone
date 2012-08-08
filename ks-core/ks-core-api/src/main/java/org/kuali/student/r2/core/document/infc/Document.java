/*
 * Copyright 2011 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.document.infc;

import org.kuali.student.r2.core.document.dto.DocumentBinaryInfo;
import org.kuali.student.r2.common.infc.IdEntity;

import java.util.Date;

/**
 * Document object
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 *
 */
public interface Document extends IdEntity {

    /**
     * Name of the document file
     * @name File Name
     *
     */
    public String getFileName();

    /**
     * The encoded document. The expectation is that this could be a base64
     * encoding
     *
     * @name Document Binary Info
     * @required
     *
     */
    public DocumentBinaryInfo getDocumentBinary();

    /**
     * Date and time that this document became effective. This is a similar
     * concept to the effective date on enumerated values. When an expiration
     * date has been specified, this field must be less than or equal to the 
     * expiration date.
     *
     * @name Effective Date
     *
     */
    public Date getEffectiveDate();

    /**
     * Date and time that this document expires. This is a similar concept to
     * the expiration date on enumerated values. If specified, this should be
     * greater than or equal to the effective date. If this field is not
     * specified, then no expiration date has been currently defined and should
     * automatically be considered greater than the effective date.
     *
     * @name Expiration Date
     *
     */
    public Date getExpirationDate();

}
