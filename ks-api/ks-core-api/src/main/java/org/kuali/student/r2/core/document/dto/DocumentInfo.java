/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.core.document.dto;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.document.infc.Document;
//import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/*
 * Refer to interface javadoc
 *
 * @version 2.0
 * @Author tom
 * @Author Sri komandur@uw.edu
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentInfo", propOrder = { "id", "typeKey", "stateKey",
        "name", "descr", "fileName", "documentBinary", "effectiveDate", "expirationDate",
        "meta", "attributes", "_futureElements" }) 
public class DocumentInfo extends IdEntityInfo implements Document, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String fileName;

    @XmlElement
    private DocumentBinaryInfo documentBinary;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlAnyElement
    private List<Object> _futureElements;  

    @Override
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public DocumentInfo() {
    }

    public DocumentInfo(Document document) {
        super(document);
        if (null != document) {
            this.fileName = document.getFileName();
            this.documentBinary = (null != document.getDocumentBinary()) ? new DocumentBinaryInfo(document.getDocumentBinary()) : null;
            this.effectiveDate = (null != document.getEffectiveDate()) ? new Date(document.getEffectiveDate().getTime()) : null;
            this.expirationDate = (null != document.getExpirationDate()) ? new Date(document.getExpirationDate().getTime()) : null;
        }
    }

    @Override
    public DocumentBinaryInfo getDocumentBinary() {
        return documentBinary;
    }

    public void setDocumentBinary(DocumentBinaryInfo documentBinary) {
        this.documentBinary = documentBinary;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

}
