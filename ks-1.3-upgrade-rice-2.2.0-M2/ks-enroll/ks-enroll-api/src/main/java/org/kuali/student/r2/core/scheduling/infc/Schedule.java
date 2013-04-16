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

import org.kuali.student.r2.common.infc.DateRange;
import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;

import java.util.List;


/**
 * Information about a Schedule.
 *
 * @author tom
 * @since Thu Nov 3 14:22:34 EDT 2011
 */ 

public interface Schedule extends IdEntity {

    /**
     * The ATP Id. Schedule Components are applied to this ATP.
     *
     * @name Atp Id
     * @required
     */
    public String getAtpId();

    /**
     * The Schedule Components. These provide a list of Time Slots
     * coupled with Room locations.
     *
     * @name Schedule Components
     */
    public List<ScheduleComponentInfo> getScheduleComponents();

    /**
     * A list of blackout dates where a meeting time implied by the
     * ScheduleComponent is skipped.
     *
     * @name Blackout Dates
     * @deprecated Should this point to a shared set (not really
     *              deprecated, but ignorable for now.
     */
    public List<? extends DateRange> getBlackoutDates();

    /**
     * A list of Milestone Ids where a meeting time that occurs
     * within a Milestone is considered a blackout date. Milestones of
     * any of the Milestone Types that exist in the related ATP are
     * used to determine the blackout dates.
     *
     * @name Blackout Milestone Ids
     * @deprecated  Should this point to a shared set (think about the common case of lectures usually having the same non-instructional days)?
     */
    public List<String> getBlackoutMilestoneIds();

    /**
     * A list of one-time MeetingTimes to add to this Schedule.
     *
     * @name Meeting Times
     */
    public List<? extends MeetingTime> getAdditionalMeetingTimes();
}
