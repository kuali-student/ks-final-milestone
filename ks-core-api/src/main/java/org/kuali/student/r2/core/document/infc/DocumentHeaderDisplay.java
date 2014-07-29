/**
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.document.infc;

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.DisplayObject;
/**
 * @Author Sri komandur@uw.edu
 *
 * Display object to avoid the overhead of bringing in the entire doc bin via DocInfo object
 *  -- discussed revisiting DocumentService design at a later time (see 07/09/2014 svcs standup notes)
 */
public interface DocumentHeaderDisplay extends IdEntity, DisplayObject {
    /**
     * Document identifier
     * @name Document Identifier
     * @readOnly
     * @required
     */
    public String getDocumentId();

    /**
     * Document content type (e.g., pdf, png, mp3 etc)
     * @Name Document Type Key
     * @readOnly
     * @required
     */
    public String getDocumentTypeKey();
    /**
     * Name of the document file
     * @Name File Name
     * @readOnly
     * @required
     */
    public String getFileName();
    /**
     * Description of the document
     * @Name Description
     * @readOnly
     */
    public String getDescription();
}

