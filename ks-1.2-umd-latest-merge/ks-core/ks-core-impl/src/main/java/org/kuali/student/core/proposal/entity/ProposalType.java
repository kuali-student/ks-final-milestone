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

package org.kuali.student.core.proposal.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.common.entity.Type;

/**
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@Entity
@Table(name = "KSPR_PROPOSAL_TYPE")
public class ProposalType extends Type<ProposalTypeAttribute> {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<ProposalTypeAttribute> attributes;

    /**
     * @see org.kuali.student.common.entity.y.AttributeOwner#getAttributes()
     */
    @Override
    public List<ProposalTypeAttribute> getAttributes() {
        return attributes;
    }

    /**
     * @seorg.kuali.student.common.entity.ity.AttributeOwner#setAttributes(java.util.List)
     */
    @Override
    public void setAttributes(List<ProposalTypeAttribute> attributes) {
        this.attributes = attributes;
    }

}
