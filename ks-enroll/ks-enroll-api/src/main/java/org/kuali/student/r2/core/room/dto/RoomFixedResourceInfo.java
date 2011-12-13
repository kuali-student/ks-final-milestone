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

import org.kuali.student.r2.core.room.infc.RoomFixedResource;

import java.io.Serializable;

/**
 * Refer to interface javadoc
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public class RoomFixedResourceInfo implements RoomFixedResource, Serializable {
    
    private String id;

    private Integer quantity;

    private String resourceType;

    public RoomFixedResourceInfo() {

    }

    public RoomFixedResourceInfo(RoomFixedResource roomFixedResource) {
        if (null != roomFixedResource) {
            this.quantity = roomFixedResource.getQuantity();
            this.resourceType = roomFixedResource.getResourceType();
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
    public String getResourceType() {
        return this.resourceType;
    }
}
