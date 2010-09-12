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

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

/**
 * This is a description of what this class does - lindholm don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@Entity
@Table(name = "KSPR_PROPOSAL_ATTR")
public class ProposalAttribute extends Attribute<Proposal> {
    @ManyToOne
    @JoinColumn(name = "OWNER")
    private Proposal owner;

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.entity.Attribute#getOwner()
     */
    @Override
    public Proposal getOwner() {
        return owner;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.entity.Attribute#setOwner(org.kuali.student.core.entity.AttributeOwner)
     */
    @Override
    public void setOwner(Proposal owner) {
        this.owner = owner;
    }

}
