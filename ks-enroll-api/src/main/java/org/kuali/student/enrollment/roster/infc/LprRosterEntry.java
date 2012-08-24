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

import org.kuali.student.r2.common.infc.Relationship;


/**
 * An Lpr Roster Entry relates an LPR to an LprRoster.
 */

public interface LprRosterEntry 
    extends Relationship {

    /**
     * The LPR Roster Id to which this LPR roster entry belongs.
     * 
     * @name LprRoster Id
     * @readOnly
     * @required
     */
    public String getLprRosterId();

    /**
     * The LPR Id of this entry.
     * 
     * @name Lpr Id
     * @readOnly
     * @required
     */
    public String getLprId();

    /**
     * The position of this entry within an ordered LPR Roster.
     * 
     * If not supplied when the entry is created the service should
     * set it to the next sequential number available if needed for an
     * ordered roster.
     * 
     * The service does not have to guarantee uniqueness of this field
     * within a roster.
     * 
     * This field cannot be relied upon to determine exactly how many
     * people are ahead of a person in the roster because the sequence
     * may contain gaps (because of deletes) or duplicates.  Moreover
     * a particular roster may order entries using different
     * algorithms, for example it may be FIFO or LIFO or it may take
     * other factors into account, such as the state, to determine
     * which entry process next.
     * 
     * @name Position
     */
    public Integer getPosition();
}
