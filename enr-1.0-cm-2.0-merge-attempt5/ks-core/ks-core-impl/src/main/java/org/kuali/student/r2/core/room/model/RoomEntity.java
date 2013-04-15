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
 * Created by Gordon on 11/01/2012
 */
package org.kuali.student.r2.core.room.model;

import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.infc.Room;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * This class contains information on Room records
 *
 * @author Kuali Student Team
 */
@Entity
@Table(name = "KSEN_ROOM")
@NamedQueries( value={
        @NamedQuery(name="Room.findRoomsByIds", query="from RoomEntity where id in (:ids)"),
        @NamedQuery(name="Room.findRoomIdsByBuilding", query="SELECT id FROM RoomEntity WHERE buildingId = :id"),
        @NamedQuery(name="Room.findRoomIdsByBuildingAndFloor", query="SELECT id FROM RoomEntity WHERE buildingId = :buildingId AND floor = :floor"),
        @NamedQuery(name="Room.findRoomIdsByType", query="SELECT id FROM RoomEntity WHERE roomType = :roomType"),
        @NamedQuery(name="Room.findRoomIdsByBuildingAndRoomType", query="SELECT id FROM RoomEntity WHERE buildingId = :buildingId AND roomType = :roomType"),
        @NamedQuery(name="Room.findRoomIdsByBuildingAndRoomTypes", query="SELECT id FROM RoomEntity WHERE buildingId = :buildingId AND roomType in (:roomTypes)"),
        @NamedQuery(name="Room.findRoomsByCodeAndBuilding", query="FROM RoomEntity WHERE roomCode = :roomCode AND buildingId in (:buildingIds)")
})
public class RoomEntity extends MetaEntity implements AttributeOwner<RoomAttributeEntity> {
    /*
    BaseEntity defines and handles the ID and OBJ_ID fields
     */

    @Column(name = "ROOM_TYPE")
    private String roomType;

    @Column(name = "ROOM_STATE")
    private String roomState;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCR_PLAIN")
    private String descrPlain;

    @Column(name = "DESCR_FORMATTED")
    private String descrFormatted;

    @Column(name = "ROOM_CD")
    private String roomCode;

    @Column(name = "BUILDING_ID")
    private String buildingId;

    @Column(name = "FLOOR")
    private String floor;

    /*
    BaseVersionEntity defines and handles the VER_NBR field
    MetaEntity defines and handles the CREATETIME, CREATEID, UPDATETIME, and UPDATEID fields
     */

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<RoomAttributeEntity> attributes = null;

    public RoomEntity() { // no-arg constructor expected in entity
        super();
    }

    public RoomEntity(Room room) {
        super(room);
        this.fromDto(room);
    }

    public void fromDto(Room room) {
        this.setId( room.getId() ); // read-only field
        this.setRoomType( room.getTypeKey() );
        this.setRoomState( room.getStateKey() );
        this.setName( room.getName() );
        if (room.getDescr() != null) {
            this.setDescrPlain( room.getDescr().getPlain() );
            this.setDescrFormatted( room.getDescr().getFormatted() );
        }
        this.setRoomCode( room.getRoomCode() );
        this.setBuildingId( room.getBuildingId() );
        this.setFloor( room.getFloor() );

        // This part usually boiler-plate since many entities have these fields
        this.setAttributes( new HashSet<RoomAttributeEntity>() );
        for (Attribute att : room.getAttributes()) {
            this.addAttribute( new RoomAttributeEntity(att, this) );
        }
    }

    public RoomInfo toDto() {
        RoomInfo roomInfo = new RoomInfo();
        // Set the instance variables that are common to most entities
        roomInfo.setId( getId() );
        roomInfo.setTypeKey(this.getRoomType());
        roomInfo.setStateKey(this.getRoomState());
        roomInfo.setName( this.getName() );

        String plain = this.getDescrPlain();
        String formatted = this.getDescrFormatted();
        if((plain != null && !"".equals(plain)) || (formatted != null && !"".equals(formatted))  ){
            RichTextInfo descr = new RichTextInfo(plain, formatted);
            roomInfo.setDescr(descr);
        }

        roomInfo.setRoomCode( this.getRoomCode() );
        roomInfo.setBuildingId( this.getBuildingId() );
        roomInfo.setFloor(this.getFloor());

        // Then, the meta fields
        roomInfo.setMeta( super.toDTO() );

        // Finally, attributes
        if (getAttributes() != null) {
            for (RoomAttributeEntity att: getAttributes()) {
                roomInfo.getAttributes().add( att.toDto() );
            }
        }

        return roomInfo;
    }

    public Set<RoomAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<RoomAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(RoomAttributeEntity attributeEntity) {
        this.attributes.add(attributeEntity);
    }

    public String getDescrPlain() {
        return descrPlain;
    }

    public void setDescrPlain(String descrPlain) {
        this.descrPlain = descrPlain;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomState() {
        return roomState;
    }

    public void setRoomState(String roomState) {
        this.roomState = roomState;
    }

    public String getDescrFormatted() {
        return descrFormatted;
    }

    public void setDescrFormatted(String descrFormatted) {
        this.descrFormatted = descrFormatted;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getName() {
        return this.name;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setName(String name) {
        this.name = name;
    }

}
