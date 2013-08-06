/*
 * Copyright 2010 The Kuali Foundation 
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

package org.kuali.student.enrollment.courseoffering.infc;

import org.kuali.student.r2.common.infc.IdEntity;

/**
 * Information about a single seat pool that can be associated with
 * either a course offering or registration group. Seat pools provides
 * a handle for statements that will be used to created enrollment
 * restrictions based on capacity. Seat pools are ordered using rank
 * to enable the most restrictive pools to be filled first.
 * 
 * @author Kamal
 */

public interface SeatPoolDefinition 
    extends IdEntity {

    /**
     * When this seat pool definition expires. A seat pool whose state
     * is active is active once the registration period for the
     * related ActivityOffering begins. The restriction may expire
     * before the end of the registration period on a Milestone. The
     * Milestone is determined by the Expiration Milestone Type Key on
     * the ATP for the registration period.
     *
     * @name Expiration Milestone Type Key
     */
    public String getExpirationMilestoneTypeKey();

    /**
     * Tests if the seat limit is an absolute number or a percentage
     * of MaximumEnrollment in the ActivityOffering. 
     *
     * @name Is Percentage
     * @required
     */
    public Boolean getIsPercentage();

    /** 
     * The limit on the number of seats in this pool. The number may
     * be expressed as an absolute number or as an integer
     * representing a percentage (0-100) based on the IsPercentage
     * flag.
     *
     * @name Seat Limit
     */
    public Integer getSeatLimit();

    /**
     * Indicates the order in which this seat pool will be processed
     * during registration. During registration students should be
     * assigned to a seat pool with the lowest possible rank to which
     * he or she meets the restriction requirements.
     *
     * @name Processing Priority
     */
    public Integer getProcessingPriority();

    /**
     * The Population to which this seat pool applies. 
     *
     * @name Population Id
     */
    public String getPopulationId();
}
