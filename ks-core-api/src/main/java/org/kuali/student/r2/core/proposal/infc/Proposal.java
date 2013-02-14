/*
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.proposal.infc;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.RichText;

/**
 * Detailed information about a proposal.
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public interface Proposal extends IdEntity {

    /**
     * List of person identifiers. Structure should contain a proposerPerson OR
     * a proposerOrg.
     */
    public List<String> getProposerPerson();

    /**
     * List of organization identifiers. Structure should contain a
     * proposerPerson OR a proposerOrg
     */
    public List<String> getProposerOrg();

    /**
     * Unique identifier for a reference type.
     */
    public String getProposalReferenceType();

    /**
     * List of reference identifiers.
     */
    public List<String> getProposalReference();

    /**
     * Brief explanation of the reason for the proposal
     */
    public RichText getRationale();

    /**
     * Date and time that this proposal became effective. This is a similar
     * concept to the effective date on enumerated values. When an expiration
     * date has been specified, this field must be less than or equal to the
     * expiration date.
     */
    public Date getEffectiveDate();

    /**
     * Date and time that this proposal expires. This is a similar concept to
     * the expiration date on enumerated values. If specified, this should be
     * greater than or equal to the effective date. If this field is not
     * specified, then no expiration date has been currently defined and should
     * automatically be considered greater than the effective date.
     */
    public Date getExpirationDate();

    /**
     * The workflow document associated with this proposal.
     * 
     * @return
     */
    public String getWorkflowId();

}
