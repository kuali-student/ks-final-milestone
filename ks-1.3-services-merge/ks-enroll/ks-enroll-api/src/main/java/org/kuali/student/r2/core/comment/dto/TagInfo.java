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

package org.kuali.student.r2.core.comment.dto;

import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.core.comment.infc.Tag;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Refer to interface javadoc
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TagInfo", propOrder = {"id", "typeKey", "stateKey",
        "namespace", "predicate", "value", "referenceTypeKey", "referenceId",
        "effectiveDate", "expirationDate", "meta", "attributes", "_futureElements"})
public class TagInfo extends IdNamelessEntityInfo implements Tag, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String namespace;

    @XmlElement
    private String predicate;

    @XmlElement
    private String value;

    @XmlElement
    private String referenceTypeKey;

    @XmlElement
    private String referenceId;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlAnyElement
    private List<Element> _futureElements;

    public TagInfo() {
    }

    public TagInfo(Tag tag) {
        super(tag);
        if (null != tag) {
            this.namespace = tag.getNamespace();
            this.predicate = tag.getPredicate();
            this.value = tag.getValue();
            this.referenceTypeKey = tag.getReferenceTypeKey();
            this.referenceId = tag.getReferenceId();
            this.effectiveDate = (null != tag.getEffectiveDate()) ? new Date(tag.getEffectiveDate().getTime()) : null;
            this.expirationDate = (null != tag.getExpirationDate()) ? new Date(tag.getExpirationDate().getTime()) : null;
            this._futureElements = null;
        }
    }

    @Override
    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @Override
    public String getPredicate() {
        return predicate;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getReferenceTypeKey() {
        return referenceTypeKey;
    }

    public void setReferenceTypeKey(String referenceTypeKey) {
        this.referenceTypeKey = referenceTypeKey;
    }

    @Override
    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
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
