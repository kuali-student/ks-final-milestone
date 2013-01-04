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
 * Created by Gordon on 11/30/2012
 */
package org.kuali.student.r2.core.room.model;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.room.dto.RoomResponsibleOrgInfo;
import org.kuali.student.r2.core.room.infc.RoomResponsibleOrg;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * This class contains information on RoomResponsibleOrg records
 *
 * @author Kuali Student Team
 */
@Entity
@Table(name = "KSEN_ROOM_RESP_ORG")
@NamedQueries( value={
        @NamedQuery(name="RoomRespOrg.findIdsByRoom", query="Select id From RoomResponsibleOrgEntity where roomId = :roomId")
})
public class RoomResponsibleOrgEntity extends MetaEntity implements AttributeOwner<RoomResponsibleOrgAttributeEntity> {
    /*
   BaseEntity defines and handles the ID and OBJ_ID fields
    */

    @Column(name = "RESP_ORG_TYPE")
    private String respOrgType;

    @Column(name = "RESP_ORG_STATE")
    private String respOrgState;

    @Column(name =  "ROOM_ID")
    private String roomId;

    @Column(name = "ORG_ID")
    private String orgId;

    @Column(name = "EFF_DT")
    private Date effDt;

    @Column(name = "EXPIR_DT")
    private Date expirDt;

    /*
    BaseVersionEntity defines and handles the VER_NBR field
    MetaEntity defines and handles the CREATETIME, CREATEID, UPDATETIME, and UPDATEID fields
     */

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<RoomResponsibleOrgAttributeEntity> attributes = null;

    public RoomResponsibleOrgEntity() {
        super();
    }

    public RoomResponsibleOrgEntity(RoomResponsibleOrg roomResponsibleOrg) {
        super(roomResponsibleOrg);
        this.fromDto(roomResponsibleOrg);
    }

    public void fromDto(RoomResponsibleOrg roomResponsibleOrg) {
        this.setId( roomResponsibleOrg.getId() );
        this.setRespOrgType( roomResponsibleOrg.getTypeKey() );
        this.setRespOrgState(roomResponsibleOrg.getStateKey());
        this.setRoomId(roomResponsibleOrg.getRoomId());
        this.setOrgId(roomResponsibleOrg.getOrgId());
        this.setEffDt(roomResponsibleOrg.getEffectiveDate());
        this.setExpirDt(roomResponsibleOrg.getExpirationDate());

        if (roomResponsibleOrg.getAttributes() != null) {
            this.setAttributes( new HashSet<RoomResponsibleOrgAttributeEntity>(roomResponsibleOrg.getAttributes().size()) );
            for (Attribute att : roomResponsibleOrg.getAttributes()) {
                this.getAttributes().add( new RoomResponsibleOrgAttributeEntity(att, this));
            }
        }
    }

    public RoomResponsibleOrgInfo toDto() {
        RoomResponsibleOrgInfo roomResponsibleOrgInfo = new RoomResponsibleOrgInfo();

        roomResponsibleOrgInfo.setId( this.getId() );
        roomResponsibleOrgInfo.setOrgId( this.getOrgId() );
        roomResponsibleOrgInfo.setRoomId( this.getRoomId() );
        roomResponsibleOrgInfo.setEffectiveDate( this.getEffDt() );
        roomResponsibleOrgInfo.setExpirationDate( this.getExpirDt() );
        roomResponsibleOrgInfo.setMeta( super.toDTO() );
        roomResponsibleOrgInfo.setStateKey( this.getRespOrgState() );
        roomResponsibleOrgInfo.setTypeKey( this.getRespOrgType() );

        if (attributes != null) {
            roomResponsibleOrgInfo.setAttributes(new ArrayList<AttributeInfo>(attributes.size()));
            for (RoomResponsibleOrgAttributeEntity att : getAttributes()) {
                roomResponsibleOrgInfo.getAttributes().add( att.toDto() );
            }
        }

        return roomResponsibleOrgInfo;
    }

    public String getRespOrgType() {
        return respOrgType;
    }

    public void setRespOrgType(String respOrgType) {
        this.respOrgType = respOrgType;
    }

    public String getRespOrgState() {
        return respOrgState;
    }

    public void setRespOrgState(String respOrgState) {
        this.respOrgState = respOrgState;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Date getEffDt() {
        return effDt;
    }

    public void setEffDt(Date effDt) {
        this.effDt = effDt;
    }

    public Date getExpirDt() {
        return expirDt;
    }

    public void setExpirDt(Date expirDt) {
        this.expirDt = expirDt;
    }

    @Override
    public void setAttributes(Set<RoomResponsibleOrgAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Set<RoomResponsibleOrgAttributeEntity> getAttributes() {
        return this.attributes;
    }
}
