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

import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.core.document.infc.DocumentCategory;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * Refer to interface javadoc
 *
 * @Version 2.0
 * @Author tom
 * @Author Sri komandur@uw.edu
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentCategoryInfo", propOrder = { "key", "typeKey", "stateKey",
        "name", "descr", "effectiveDate", "expirationDate", "meta", "attributes", "_futureElements" })
public class DocumentCategoryInfo extends KeyEntityInfo implements DocumentCategory, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlAnyElement
    private List<Element> _futureElements;

    public DocumentCategoryInfo() {
    }

    public DocumentCategoryInfo(DocumentCategory documentCategory) {
        super(documentCategory);
        if (null != documentCategory) {
            this.effectiveDate = (null != documentCategory.getEffectiveDate()) ? new Date(documentCategory.getEffectiveDate().getTime()) : null;
            this.expirationDate = (null != documentCategory.getExpirationDate()) ? new Date(documentCategory.getExpirationDate().getTime()) : null;
        }
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
