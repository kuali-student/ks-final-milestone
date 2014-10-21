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

package org.kuali.student.r1.core.proposal.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.r1.common.entity.Type;

/**
 * This is a description of what this class does - lindholm don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@Deprecated
@Entity
@Table(name = "KSPR_PROPOSAL_REFTYPE")
public class ProposalReferenceType extends Type<ProposalReferenceTypeAttribute> {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<ProposalReferenceTypeAttribute> attributes;


    @Override
    public List<ProposalReferenceTypeAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(List<ProposalReferenceTypeAttribute> attributes) {
        this.attributes = attributes;
    }

}
