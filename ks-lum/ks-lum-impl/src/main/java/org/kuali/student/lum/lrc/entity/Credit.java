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

package org.kuali.student.lum.lrc.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.AttributeOwner;
@Entity
@Table(name="KSLU_LRC_CREDIT")
@NamedQueries( {
    @NamedQuery(name = "Credit.getCreditsByIdList", query = "SELECT c FROM Credit c WHERE c.id IN (:creditIdList)"),
    @NamedQuery(name = "Credit.getCreditIdsByCreditType", query = "SELECT c.id FROM Credit c JOIN c.type type WHERE type.id = :creditTypeId")
})
@DiscriminatorValue("Credit")
public class Credit extends ResultValue implements AttributeOwner<CreditAttribute> {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<CreditAttribute> attributes;

    @ManyToOne
    @JoinColumn(name = "TYPE")
    private CreditType type;

    /**
     * @return the type
     */
    public CreditType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(CreditType type) {
        this.type = type;
    }

    @Override
    public List<CreditAttribute> getAttributes() {
        if (attributes == null) {
            attributes = new ArrayList<CreditAttribute>(0);
        }
        return attributes;
    }

    @Override
    public void setAttributes(List<CreditAttribute> attributes) {
        this.attributes = attributes;
    }

}
