/**
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.lpr.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_LPR_TRANS_ATTR")
public class LprTransactionAttributeEntity extends BaseAttributeEntity<LprTransactionEntity> {

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private LprTransactionEntity owner;

    public LprTransactionAttributeEntity() {}

    public LprTransactionAttributeEntity(Attribute att) {
        super(att);
    }

    public LprTransactionAttributeEntity(String key, String value) {
        super(key, value);
    }

    @Override
    public void setOwner(LprTransactionEntity owner) {
        this.owner = owner;
    }

    @Override
    public LprTransactionEntity getOwner() {
        return owner;
    }

	public void fromDto(Attribute info) {
		
		setKey(info.getKey());
		setValue(info.getValue());
	}

	

}
