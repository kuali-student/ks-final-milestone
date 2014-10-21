/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 */

package org.kuali.student.r2.core.class1.state.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * State propagation attribute entity
 *
 * @author Kuali Student Team
 */

@Entity
@Table(name = "KSEN_STATE_PROPAGT_ATTR")
public class StatePropagationAttributeEntity  extends BaseAttributeEntity<StatePropagationEntity> {

	public StatePropagationAttributeEntity() {
		super();
	}

	public StatePropagationAttributeEntity(Attribute att, StatePropagationEntity owner) {
		super(att, owner);
	}

}
