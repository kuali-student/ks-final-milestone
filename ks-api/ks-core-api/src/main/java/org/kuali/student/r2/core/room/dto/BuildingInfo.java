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

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.room.infc.Building;
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
@XmlType(name = "BuildingInfo", propOrder = {"id", "typeKey", "stateKey",
        "name", "descr", "buildingCode", "campusId", "meta", "attributes", "_futureElements" })
public class BuildingInfo extends IdEntityInfo implements Building, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String buildingCode;
    
    @XmlElement
    private String campusId;

    @XmlAnyElement
    private List<Object> _futureElements;  

    public BuildingInfo() {
    }

    public BuildingInfo(Building building) {
        super(building);
        if (null != building) {
            this.buildingCode = building.getBuildingCode();
            this.campusId = building.getCampusId();
        }
    }

    @Override
    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    @Override
    public String getCampusId() {
        return this.campusId;
    }

    public void setCampusId(String campusId) {
        this.campusId = campusId;
    }
}
