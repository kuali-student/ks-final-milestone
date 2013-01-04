/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.lum.clu.dto;

import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.lum.clu.infc.LuDocRelation;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LuDocRelationInfo", propOrder = {"id", "descr", "stateKey", "typeKey", "cluId", "documentId", "title",
        "effectiveDate", "expirationDate", "meta", "attributes" })//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code})
public class LuDocRelationInfo extends RelationshipInfo implements Serializable, LuDocRelation {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String cluId;

    @XmlElement
    private String documentId;

    @XmlElement
    private String title;

    @XmlElement
    private RichTextInfo descr;

//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;

    public LuDocRelationInfo() {

    }

    public LuDocRelationInfo(LuDocRelation luDocRelation) {
        super(luDocRelation);
        if (null != luDocRelation) {
            this.cluId = luDocRelation.getCluId();
            this.documentId = luDocRelation.getDocumentId();
            this.title = luDocRelation.getTitle();
            this.descr = (null != luDocRelation.getDescr()) ? new RichTextInfo(luDocRelation.getDescr()) : null;
        }
    }

    @Override
    public String getCluId() {
        return cluId;
    }

    public void setCluId(String cluId) {
        this.cluId = cluId;
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
