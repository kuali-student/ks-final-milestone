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

package org.kuali.student.r2.core.process.infc;

import org.kuali.student.r2.common.infc.KeyEntity;


/**
 * Information about a Check. A Check can be one of a:
 *
 *     1. Hold Check: the Issue Id is not null
 *     2. Deadline Check: the Milestone Type Key and Atp Type Key are
 *        not null
 *     3. Not Before Check: the Milestone Type Key and Atp Type Key are
 *        not null
 *     4. In Time Period Check: the Milestone Type Key and Atp Type
 *        Key are not null
 *     5. Outside Date Range Check: the Milestone Type Key and Atp
 *        Type Key are not null
 *     6. Statement Check: the Statement Id is not null
 *     7. Process Check: the Process key is not null
 *
 * @author tom
 * @since Thu Nov 21 14:22:34 EDT 2011
 */ 

public interface Check 
    extends KeyEntity {

    /**
     * The Issue Key if this Check is for a Hold.
     *
     * @name Issue Key
     */
    public String getIssueKey();

    /**
     * The Milestone Type Key if this Check is for a deadline, "not
     * before," "in time period" or "outside date range" checks.
     *
     * @name Milestone Type Key
     */
    public String getMilestoneTypeKey();

    /**
     * The Agenda Id if this Check is based on evaluating
     * an Agenda. (do we need a reference for the eval?)
     *
     * @name Agenda Id
     */
    public String getAgendaId();

    /**
     * The Process Key if this Check is based on evaluating
     * a Process.
     *
     * @name Process Key
     */
    public String getProcessKey();
}
