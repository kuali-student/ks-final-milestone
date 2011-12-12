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
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.core.room.infc.RoomResource;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * Refer to interface javadoc
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoomResourceInfo", propOrder = {"id", "typeKey", "stateKey",
        "name", "descr", "quantity", "size", "meta", "attributes", "_futureElements"})
public class RoomResourceInfo extends IdEntityInfo implements RoomResource, Serializable {

    @XmlElement
    private Integer quantity;

    @XmlElement
    private String size;

    @XmlAnyElement
    private List<Element> _futureElements;

    public RoomResourceInfo() {

    }

    public RoomResourceInfo(RoomResource roomResource) {
        super(roomResource);
        if (null != roomResource) {
            this.quantity = roomResource.getQuantity();
            this.size = roomResource.getSize();
        }
    }

    @Override
    public Integer getQuantity() {
        return this.quantity;
    }

    @Override
    public String getSize() {
        return this.size;
    }
}
