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
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

/**
 * Refer to interface javadoc
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoomResponsibleOrgInfo", propOrder = {"id", "typeKey",
        "stateKey", "orgKey", "effectiveDate", "expirationDate", "meta", "attributes", "_futureElements"})
public class RoomResponsibleOrgInfo extends RelationshipInfo implements RoomResponsibleOrg, Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement
    private String orgKey;

    @XmlAnyElement
    private List<Element> _futureElements;

    public RoomResponsibleOrgInfo() {

    }

    public RoomResponsibleOrgInfo(RoomResponsibleOrg roomResponsibleOrg) {
        super(roomResponsibleOrg);
        if (null != roomResponsibleOrg) {
            this.orgKey = roomResponsibleOrg.getOrgKey();
        }
    }

    @Override
    public String getOrgKey() {
        return this.orgKey;
    }

    public void setOrgKey(String orgKey) {
        this.orgKey = orgKey;
    }
}
