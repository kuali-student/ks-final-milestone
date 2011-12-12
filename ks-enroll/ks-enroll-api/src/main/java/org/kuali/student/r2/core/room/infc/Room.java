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

package org.kuali.student.r2.core.room.infc;

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.core.room.dto.RoomResourceInfo;
import org.kuali.student.r2.core.room.dto.RoomResponsibleOrgInfo;

import java.util.List;

/**
 * Room information
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface Room extends IdEntity {

    /**
     * Floor the room is in
     *
     * @name Floor Key
     * @readOnly
     * @required
     *
     */
    public String getFloorKey();

    /**
     *  Soft capacity of the room
     *
     * @name Soft Capacity
     * @required
     *
     */
    public Integer getSoftCapacity();

    /**
     *  Hard capacity of the room
     *
     * @name Hard Capacity
     * @required
     *
     */
    public Integer getHardCapacity();

    /**
     *  Exam capacity of the room
     *
     * @name Exam Capacity
     *
     */
    public Integer getExamCapacity();

    /**
     *  Resources in the room
     *
     * @name Room Resources
     *
     */
    public List<RoomResource> getRoomResources();

    /**
     *  Usage types for the room (class, exam, yoga, ...)
     *
     * @name Usage Type Keys
     * @readOnly
     *
     */
    public List<String> getUsageTypeKeys();

    /**
     *  Accessibility types for the room (wheelchair, ...)
     *
     * @name Accessibility Type Keys
     * @readOnly
     *
     */
    public List<String> getAccessibilityTypeKeys();

    /**
     *  Room Responsible Orgs (multiple orgs in different roles like
     *      maintenance, scheduling, ...)
     *
     * @name Room Responsible Orgs
     * @required
     *
     */
    public List<RoomResponsibleOrg> getRoomResponsibleOrgs();
    
}
