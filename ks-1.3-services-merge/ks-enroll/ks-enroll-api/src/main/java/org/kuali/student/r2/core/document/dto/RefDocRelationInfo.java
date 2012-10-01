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

import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.kuali.student.r2.core.document.infc.RefDocRelation;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;
import org.kuali.student.r2.common.dto.RichTextInfo;


/**
 * Refer to interface javadoc
 *
 * @Version 2.0
 * @Author tom
 * @Author Sri komandur@uw.edu
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RefDocRelationInfo", propOrder = { "id", "typeKey",
		"stateKey", "refObjectTypeKey", "refObjectId", "documentId", "title", "descr", 
        "effectiveDate", "expirationDate", 
        "meta", "attributes", "_futureElements" })
public class RefDocRelationInfo extends RelationshipInfo implements RefDocRelation, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String refObjectTypeKey;

    @XmlElement
    private String refObjectId;

    @XmlElement
    private String documentId;

    @XmlElement
    private String title;
    
    @XmlElement
    private RichTextInfo descr;
    
    @XmlAnyElement
    private List<Element> _futureElements;

    public RefDocRelationInfo() {
    }

    public RefDocRelationInfo(RefDocRelation refDocRelation) {
        super(refDocRelation);
        if(null != refDocRelation) {
            this.refObjectTypeKey = refDocRelation.getRefObjectTypeKey();
            this.refObjectId = refDocRelation.getRefObjectId();
            this.documentId = refDocRelation.getDocumentId();
            this.title = refDocRelation.getTitle();
            if (this.descr != null) {
                this.descr = new RichTextInfo (refDocRelation.getDescr());
            }
        }
    }

    @Override
    public String getRefObjectTypeKey() {
        return refObjectTypeKey;
    }

    public void setRefObjectTypeKey(String refObjectTypeKey) {
        this.refObjectTypeKey = refObjectTypeKey;
    }

    @Override
    public String getRefObjectId() {
        return refObjectId;
    }

    public void setRefObjectId(String refObjectId) {
        this.refObjectId = refObjectId;
    }

    @Override
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }
 
}
