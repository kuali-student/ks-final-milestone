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

package org.kuali.student.r2.core.document.dto;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.document.infc.DocumentHeaderDisplay;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentHeaderDisplayInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr",
        "documentId", "documentTypeKey", "fileName", "description",
        "meta", "attributes", "_futureElements"})
public class DocumentHeaderDisplayInfo extends IdEntityInfo implements DocumentHeaderDisplay, Serializable {

    @XmlElement
    private String documentId;

    @XmlElement
    private String documentTypeKey;

    @XmlElement
    private String fileName;

    @XmlElement
    private String description;

    @XmlAnyElement
    private List<Object> _futureElements;

    public DocumentHeaderDisplayInfo() {
    }

    public DocumentHeaderDisplayInfo(DocumentHeaderDisplay documentHeaderDisplay) {
        super(documentHeaderDisplay);
        if (null != documentHeaderDisplay) {
            this.documentId = documentHeaderDisplay.getDocumentId();
            this.documentTypeKey = documentHeaderDisplay.getDocumentTypeKey();
            this.fileName = documentHeaderDisplay.getFileName();
            this.description = documentHeaderDisplay.getDescription();
        }
    }

    @Override
    public String getDocumentId() {
        return this.documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }


    @Override
    public String getDocumentTypeKey() {
        return this.documentTypeKey;
    }

    public void setDocumentTypeKey(String documentTypeKey) {
        this.documentTypeKey = documentTypeKey;
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}