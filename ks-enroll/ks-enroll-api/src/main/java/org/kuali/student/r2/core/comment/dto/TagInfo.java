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
 * Detailed information about a tag.
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

    public static TagInfo newInstance() {
        return new TagInfo();
    }

    public static TagInfo getInstance(TagInfo tag) {
        return new TagInfo(tag);
    }

    /**
     * Default constructor needs to be provided as we have an explicit parameterized constructor
     */
    public TagInfo() {
    }


    /**
     * Constructs a new TagInfo from another Tag.
     *
     * @param tag the TAG to copy
     */
    public TagInfo(Tag tag) {
        super(tag);
        if (null != tag) {
            this.namespace = tag.getNamespace();
            this.predicate = tag.getPredicate();
            this.value = tag.getValue();
            this.referenceTypeKey = tag.getReferenceTypeKey();
            this.referenceId = tag.getReferenceId();
            this.effectiveDate = new Date(tag.getEffectiveDate().getTime());
            this.expirationDate = new Date(tag.getExpirationDate().getTime());
            this._futureElements = null;
        }
    }


    /**
     * Namespace of the tag.
     */
    @Override
    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    /**
     * Predicate of the tag.
     */
    @Override
    public String getPredicate() {
        return predicate;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

    /**
     * Value of the tag.
     */
    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Unique identifier for a reference type.
     */
    @Override
    public String getReferenceTypeKey() {
        return referenceTypeKey;
    }

    public void setReferenceTypeKey(String referenceTypeKey) {
        this.referenceTypeKey = referenceTypeKey;
    }

    /**
     * Identifier component for a reference. This is an external identifier and such may not uniquely identify a particular reference unless combined with the type. A referenceId could be a cluId, a luiId, an orgId, a documentId, etc.
     */
    @Override
    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    /**
     * Date and time that this tag became effective. This is a similar concept to the effective date on enumerated values. When an expiration date has been specified, this field must be less than or equal to the expiration date.
     */
    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Date and time that this tag expires. This is a similar concept to the expiration date on enumerated values. If specified, this should be greater than or equal to the effective date. If this field is not specified, then no expiration date has been currently defined and should automatically be considered greater than the effective date.
     */
    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

}
