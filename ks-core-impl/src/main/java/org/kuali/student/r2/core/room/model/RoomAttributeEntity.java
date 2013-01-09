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
 * Created by Gordon on 11/01/12
 */
package org.kuali.student.r2.core.room.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This class contains information on attributes associated with RoomEntity records
 *
 * @author Kuali Student Team
 */
@Entity
@Table(name = "KSEN_ROOM_ATTR")
public class RoomAttributeEntity extends BaseAttributeEntity<RoomEntity> {

	public RoomAttributeEntity() {
		super();
	}

	public RoomAttributeEntity(Attribute att, RoomEntity owner) {
		super(att, owner);
	}
   
}
