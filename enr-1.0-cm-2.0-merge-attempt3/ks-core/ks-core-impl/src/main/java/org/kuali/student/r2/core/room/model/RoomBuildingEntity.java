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

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.infc.Building;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Gordon
 * Date: 11/1/12
 * Time: 3:41 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "KSEN_ROOM_BUILDING")
@NamedQueries(value={
        @NamedQuery(name = "Building.findBuildingsByIds", query = "FROM RoomBuildingEntity WHERE id in (:ids)"),
        @NamedQuery(name = "Building.findBuildingIdsByCampus", query = "SELECT id FROM RoomBuildingEntity WHERE campusKey = :campusKey"),
        @NamedQuery(name = "Building.findBuildingsByBuildingCode", query = "FROM RoomBuildingEntity WHERE buildingCode = :buildingCode"),
        @NamedQuery(name = "Building.findBuildingIdsByBuildingCode", query = "SELECT id FROM RoomBuildingEntity WHERE buildingCode = :buildingCode")
})
public class RoomBuildingEntity extends MetaEntity implements AttributeOwner<RoomBuildingAttributeEntity> {
    /*
   BaseEntity defines and handles the ID and OBJ_ID fields
    */

    @Column(name = "BUILDING_TYPE")
    private String buildingType;

    @Column(name = "BUILDING_STATE")
    private String buildingState;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCR_PLAIN")
    private String descrPlain;

    @Column(name = "DESCR_FORMATTED")
    private String descrFormatted;

    @Column(name = "BUILDING_CD")
    private String buildingCode;

    @Column(name = "CAMPUS_KEY")
    private String campusKey;

    /*
   BaseVersionEntity defines and handles the VER_NBR field
   MetaEntity defines and handles the CREATETIME, CREATEID, UPDATETIME, and UPDATEID fields
    */

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<RoomBuildingAttributeEntity> attributes = null;

    public RoomBuildingEntity() {
        super();
    }

    public RoomBuildingEntity(Building building) {
        super(building);

        this.fromDto(building);
    }

    public void fromDto(Building building) {
        this.setId(building.getId());

        //Meta is set during super constructor
        this.setName( building.getName() );
        this.setBuildingCode( building.getBuildingCode() );
        this.setBuildingState( building.getStateKey() );
        this.setBuildingType( building.getTypeKey() );
        if (building.getDescr() != null) {
            this.setDescrPlain( building.getDescr().getPlain() );
            this.setDescrFormatted( building.getDescr().getFormatted() );
        }

        this.setCampusKey(building.getCampusKey());

        this.setAttributes( new HashSet<RoomBuildingAttributeEntity>(building.getAttributes().size()) );
        for(Attribute attribute : building.getAttributes()) {
            this.addAttribute( new RoomBuildingAttributeEntity(attribute, this) );
        }
    }

    public BuildingInfo toDto() {
        BuildingInfo buildingInfo = new BuildingInfo();

        buildingInfo.setId( this.getId() );
        buildingInfo.setBuildingCode( this.getBuildingCode() );
        buildingInfo.setCampusKey(this.getCampusKey());
        buildingInfo.setDescr( new RichTextInfo(this.getDescrPlain(), this.getDescrFormatted()) );
        buildingInfo.setMeta( super.toDTO() );
        buildingInfo.setName( this.getName() );
        buildingInfo.setStateKey( this.getBuildingState() );
        buildingInfo.setTypeKey( this.getBuildingType() );

        buildingInfo.setAttributes( new ArrayList<AttributeInfo>(this.getAttributes().size()) );
        for(RoomBuildingAttributeEntity att : this.getAttributes()) {
            buildingInfo.getAttributes().add( att.toDto() );
        }

        return buildingInfo;
    }

    public String getDescrPlain() {
        return this.descrPlain;
    }

    public void setDescrPlain(String descrPlain) {
        this.descrPlain = descrPlain;
    }

    public String getDescrFormatted() {
        return this.descrFormatted;
    }

    public void setDescrFormatted(String descrFormatted) {
        this.descrFormatted = descrFormatted;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public String getBuildingState() {
        return buildingState;
    }

    public void setBuildingState(String buildingState) {
        this.buildingState = buildingState;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public String getCampusKey() {
        return campusKey;
    }

    public void setCampusKey(String campusKey) {
        this.campusKey = campusKey;
    }

    @Override
    public void setAttributes(Set<RoomBuildingAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(RoomBuildingAttributeEntity attributeEntity) {
        this.attributes.add(attributeEntity);
    }

    @Override
    public Set<RoomBuildingAttributeEntity> getAttributes() {
        return this.attributes;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
