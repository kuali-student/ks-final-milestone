/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.core.proposal.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.dto.HasAttributes;
import org.kuali.student.core.dto.HasTypeState;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

/**
 * Detailed information about a proposal.
 *
 * @Author KSContractMojo
 * @Author Neerav Agrawal
 * @Since Thu May 28 10:25:28 EDT 2009
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/proposalInfo+Structure">ProposalInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ProposalInfo implements Serializable, Idable, HasTypeState, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String name;

    @XmlElement
    private List<String> proposerPerson;

    @XmlElement
    private List<String> proposerOrg;

    @XmlElement
    private String proposalReferenceType;

    @XmlElement
    private List<String> proposalReference;

    @XmlElement
    private String rationale;

    @XmlElement
    private String detailDesc;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String, String> attributes;

    @XmlElement
    private MetaInfo metaInfo;

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String state;

    @XmlAttribute
    private String id;

    /**
     * The name or title of the proposal. Any finite sequence of characters with letters, numerals, symbols and punctuation marks. The length can be any natural number between zero or any positive integer.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * List of person identifiers. Structure should contain a proposerPerson OR a proposerOrg.
     */
    public List<String> getProposerPerson() {
        if (proposerPerson == null) {
            proposerPerson = new ArrayList<String>(0);
        }
        return proposerPerson;
    }

    public void setProposerPerson(List<String> proposerPerson) {
        this.proposerPerson = proposerPerson;
    }

    /**
     * List of organization identifiers. Structure should contain a proposerPerson OR a proposerOrg
     */
    public List<String> getProposerOrg() {
        if (proposerOrg == null) {
            proposerOrg = new ArrayList<String>(0);
        }
        return proposerOrg;
    }

    public void setProposerOrg(List<String> proposerOrg) {
        this.proposerOrg = proposerOrg;
    }

    /**
     * Unique identifier for a reference type.
     */
    public String getProposalReferenceType() {
        return proposalReferenceType;
    }

    public void setProposalReferenceType(String proposalReferenceType) {
        this.proposalReferenceType = proposalReferenceType;
    }

    /**
     * List of reference identifiers.
     */
    public List<String> getProposalReference() {
        if (proposalReference == null) {
            proposalReference = new ArrayList<String>(0);
        }
        return proposalReference;
    }

    public void setProposalReference(List<String> proposalReference) {
        this.proposalReference = proposalReference;
    }

    /**
     * Brief explanation of the reason for the proposal
     */
    public String getRationale() {
        return rationale;
    }

    public void setRationale(String rationale) {
        this.rationale = rationale;
    }

    /**
     * Detailed description of the proposed changes.
     */
    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    /**
     * Date and time that this proposal became effective. This is a similar concept to the effective date on enumerated values. When an expiration date has been specified, this field must be less than or equal to the expiration date.
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Date and time that this proposal expires. This is a similar concept to the expiration date on enumerated values. If specified, this should be greater than or equal to the effective date. If this field is not specified, then no expiration date has been currently defined and should automatically be considered greater than the effective date.
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * List of key/value pairs, typically used for dynamic attributes.
     */
    public Map<String, String> getAttributes() {
        if (attributes == null) {
            attributes = new HashMap<String, String>();
        }
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    /**
     * Create and last update info for the structure. This is optional and treated as read only since the data is set by the internals of the service during maintenance operations.
     */
    public MetaInfo getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }

    /**
     * Unique identifier for a proposal type.
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * The current status of the proposal. The values for this field are constrained to those in the proposalState enumeration. A separate setup operation does not exist for retrieval of the meta data around this value.
     */
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * Unique identifier for a Proposal. This is optional, due to the identifier being set at the time of creation. Once the proposal has been created, this should be seen as required.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
