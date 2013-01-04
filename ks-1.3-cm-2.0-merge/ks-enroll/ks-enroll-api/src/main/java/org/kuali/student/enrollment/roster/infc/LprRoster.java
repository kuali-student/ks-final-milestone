/**
 * Copyright 2012 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.roster.infc;

import java.util.List;

import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.infc.IdEntity;

/**
 * LprRoster is a collection or group of LPR Roster entries which can
 * be used to model any collection of LPRs at the class I level,
 * e.g. a garde sheet or a waitlist for a course. The LPR roster
 * entries in an LPR Roster will always belong to of a particular
 * course or to a section(s) within the course. The associated LUIs
 * are a way to show this relation.
 * 
 * @author Kuali Student Team (sambit)
 */

public interface LprRoster 
    extends IdEntity {

    /**
     * This method gets the Ids of the associated LUIs for the LPR
     * Roster. A roster may be serve one or more Luis.
     * 
     * @name Associated Lui Ids
     */
    public List<String> getAssociatedLuiIds();

    /**
     * The maximum capacity of the roster. This number contraints the
     * maximum number of roster entries in this roster. If null, there
     * is no roster size constraint. Size constraints are used with
     * rosters that implement waitlists.
     * 
     * @Maximum Capacity
     */
    public Integer getMaximumCapacity();

    /**
     * Is check in required to maintain an active status in this
     * roster. This is an optional flag for use with rosters that
     * implement waitlist checkins.
     * 
     * @name CheckIn Required
     */
    public Boolean getCheckInRequired();

    /**
     * The frequency in terms of time period that an entry has to
     * check in to maintain active status in this roster. This is only
     * populated if check in is required for the roster.
     *
     * This is an optional value for use with rosters that implement
     * waitlist checkins.
     * 
     * @name CheckIn  Frequency
     */
    public TimeAmountInfo getCheckInFrequency();
}
