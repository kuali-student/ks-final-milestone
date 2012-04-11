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

package org.kuali.student.r2.core.class1.enumerationmanagement.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_ENUM_ATTR")
public class EnumerationAttributeEntity extends BaseAttributeEntity<EnumerationEntity> {

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private EnumerationEntity owner;

    public EnumerationAttributeEntity () {
    }
    
    public EnumerationAttributeEntity(Attribute att, EnumerationEntity owner) {
        super(att);
        setOwner(owner);
    }

    @Override
    public void setOwner(EnumerationEntity owner) {
        this.owner = owner;
        
    }

    @Override
    public EnumerationEntity getOwner() {
        return owner;
    }

}
