/**
 * Copyright 2013 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by Charles on 11/6/13
 */
package org.kuali.student.r2.core.scheduling.util.container;

import java.util.List;

/**
 * A subclass that represents the list of time slots from ADLs (i.e., from scheduleIds).
 * Stores the AO associated with the time slots.
 *
 * @author Kuali Student Team
 */
public class ADLTimeSlotContainer extends TimeSlotContainer {
    private String aoId; // The AO where the time slots come from

    public ADLTimeSlotContainer(String aoId, List<String> timeSlotIds) {
        super(timeSlotIds);
        this.aoId = aoId;
    }

    @Override
    public String getRefObjectId() {
        return aoId;
    }
}
