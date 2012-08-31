/**
 * Copyright 2011 The Kuali Foundation Licensed under the Educational
 * Community License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.scheduling.infc;

import org.kuali.student.r2.common.infc.HasId;

import java.util.List;


/**
 * Information about a Schedule Request Item. 
 *
 * @author tom
 * @since Thu Nov 3 14:22:34 EDT 2011
 */ 

public interface ScheduleRequestComponent extends HasId {

    /**
     * The Campus Id to specify a Campus.
     *
     * @name Campus Ids
     */
    public List<String> getCampusIds();

    /**
     * The Building Id to specify a Building.
     *
     * @name Building Ids
     */
    public List<String> getBuildingIds();

    /**
     * The Room Id to specify a Room.
     *
     * @name Room Ids
     */
    public List<String> getRoomIds();

    /**
     * The Org Id to specify a Room or Building "owned" by an
     * Organization.
     *
     * @name Org Ids
     */
    public List<String> getOrgIds();

    /**
     * The Resource Types to specify a Room with types of fixed
     * Resources.
     *
     * @name Resource Type Keys
     */
    public List<String> getResourceTypeKeys();

    /**
     * The Time Slot Ids to specify a time slot.
     *
     * @name Time Slot Ids
     */
    public List<String> getTimeSlotIds();

    /**
     * The flag that holds whether this is a TBA schedule request.
     * A ScheduleRequestComponent is TBA if the Room is TBA, or timeslot is completely TBA (not there), or is of type TBA (weekday or start/end time TBA).
     */
    public Boolean getIsTBA();
}
