/*
 * Copyright 2011 The Kuali Foundation Licensed under the
 *  Educational Community License, Version 2.0 (the "License"); you may
 *  not use this file except in compliance with the License. You may
 *  obtain a copy of the License at
 *
 *   http://www.osedu.org/licenses/ECL-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS"
 *  BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */

package org.kuali.student.r2.core.room.dto;

import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.kuali.student.r2.core.room.infc.RoomResponsibleOrg;
//import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoomResponsibleOrgInfo", propOrder = {"id", "typeKey", "stateKey", "roomId", "orgId", "effectiveDate", "expirationDate", "meta", "attributes", "_futureElements" }) 
public class RoomResponsibleOrgInfo extends RelationshipInfo implements RoomResponsibleOrg, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String roomId;
    @XmlElement
    private String orgId;

    @XmlAnyElement
    private List<Object> _futureElements;  

    public RoomResponsibleOrgInfo() {

    }

    public RoomResponsibleOrgInfo(RoomResponsibleOrg roomResponsibleOrg) {
        if (null != roomResponsibleOrg) {
            this.roomId = roomResponsibleOrg.getRoomId();
            this.orgId = roomResponsibleOrg.getOrgId();
        }
    }

    @Override
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    @Override
    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

}
