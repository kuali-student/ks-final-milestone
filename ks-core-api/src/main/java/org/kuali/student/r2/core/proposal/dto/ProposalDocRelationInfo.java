/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.proposal.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.r1.common.dto.HasAttributes;
import org.kuali.student.r1.common.dto.HasTypeState;
import org.kuali.student.r1.common.dto.Idable;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;
import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.kuali.student.r2.core.proposal.infc.ProposalDocRelation;
//import org.w3c.dom.Element;

/**
 * This is a description of what this class does - sambit don't forget to fill
 * this in.
 * 
 * @author KS Team(sambitpa@kuali.org)
 */
@XmlType(name = "ProposalDocRelationInfo", propOrder = {"id", "typeKey", "stateKey", "proposalId", "documentId", "title", "effectiveDate", "expirationDate", "meta", "attributes"})//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code})
@XmlAccessorType(XmlAccessType.FIELD)
public class ProposalDocRelationInfo extends RelationshipInfo implements ProposalDocRelation, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String proposalId;

    @XmlElement
    private String documentId;

    @XmlElement
    private String title;

//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;

    public ProposalDocRelationInfo() {

    }

    public ProposalDocRelationInfo(ProposalDocRelation proposalDocRelation) {
        super(proposalDocRelation);
        if (proposalDocRelation != null) {
            this.proposalId = proposalDocRelation.getProposalId();
            this.documentId = proposalDocRelation.getDocumentId();
            this.title = proposalDocRelation.getTitle();
        }
    }

    /**
     * Unique identifier for a Proposal.
     */
    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    /**
     * Unique identifier for a document.
     */
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    /**
     * The title of the document usage in the context of the Proposal.
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
