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

import org.kuali.student.r2.common.dto.HasAttributesAndMetaInfo;
import org.kuali.student.r2.core.room.infc.RoomFixedResource;
//import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoomFixedResourceInfo", propOrder = {"id", "quantity", "resourceTypeKey", "meta", "attributes", "_futureElements" }) 

public class RoomFixedResourceInfo 
    extends HasAttributesAndMetaInfo 
    implements RoomFixedResource, Serializable {

    @XmlElement
    private String id;

    @XmlElement
    private Integer quantity;

    @XmlElement
    private String resourceTypeKey;

    @XmlAnyElement
    private List<Object> _futureElements;  

    public RoomFixedResourceInfo() {
    }

    public RoomFixedResourceInfo(RoomFixedResource roomFixedResource) {
        if (null != roomFixedResource) {
            this.id = roomFixedResource.getId();
            this.quantity = roomFixedResource.getQuantity();
            this.resourceTypeKey = roomFixedResource.getResourceTypeKey();
        }
    }
    
    @Override
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Integer getQuantity() {
        return this.quantity;
    }

    @Override
    public String getResourceTypeKey() {
        return this.resourceTypeKey;
    }
}
