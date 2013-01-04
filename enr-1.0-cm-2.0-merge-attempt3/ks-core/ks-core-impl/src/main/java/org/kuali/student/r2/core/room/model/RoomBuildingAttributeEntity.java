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
 * Created with IntelliJ IDEA.
 * User: Gordon
 * Date: 11/1/12
 * Time: 4:00 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "KSEN_ROOM_ATTR")
public class RoomBuildingAttributeEntity extends BaseAttributeEntity<RoomBuildingEntity> {

    public RoomBuildingAttributeEntity() {
        super();
    }

    public RoomBuildingAttributeEntity(Attribute att, RoomBuildingEntity owner) {
        super(att, owner);
    }

}
