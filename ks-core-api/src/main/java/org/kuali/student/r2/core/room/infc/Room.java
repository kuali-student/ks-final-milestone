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
import java.util.List;

/**
 * Room information
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface Room extends IdEntity {

    /**
     * Code assigned to room (for example, "239")
     *
     * Must be unique within the building.
     *
     * @name Room Code
     * @required
     */
    public String getRoomCode();

    /**
     * Building the room is in
     *
     * @name Building Id
     * @readOnly
     * @required
     */
    public String getBuildingId();

    /**
     * Floor the room is in
     *
     * @name Floor
     * @required
     */
    public String getFloor();

    /**
     * Resources in the room
     *
     * @name Room Resources
     */
    public List<? extends RoomFixedResource> getRoomFixedResources();

    /**
     * Various usage information for the room
     *
     * @name Room Usages
     */
    public List<? extends RoomUsage> getRoomUsages();

    /**
     * Accessibility types for the room (wheelchair, ...)
     *
     * @name Accessibility Type Keys
     */
    public List<String> getAccessibilityTypeKeys();
}
