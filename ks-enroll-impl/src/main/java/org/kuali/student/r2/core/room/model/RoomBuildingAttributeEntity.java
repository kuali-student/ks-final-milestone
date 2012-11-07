package org.kuali.student.r2.core.room.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.room.model.RoomBuildingEntity;

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
