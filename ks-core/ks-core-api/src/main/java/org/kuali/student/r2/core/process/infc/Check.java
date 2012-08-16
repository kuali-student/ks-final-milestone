/**
 * Copyright 2011 The Kuali Foundation
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

package org.kuali.student.r2.core.process.infc;

import org.kuali.student.r2.common.infc.IdEntity;


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
    extends IdEntity {

    /**
     * The Hold Issue if this Check to check if the student has an applied hold.
     *
     * @name Hold Issue Id
     */
    public String getHoldIssueId();

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
     * The Process Key if this Check is to evaluate a all the instructions
     * already defined in another child process.
     *
     * @name Child Process Key
     */
    public String getChildProcessKey();

    /**
     *  A hard coded value for comparison Check Types of
     *  min/max/equals. The left comparison value may be determined
     *  from the context of the process or from an agenda Id.
     *
     *  For example, this value may indicate a hard-coded credit limit
     *  per term for all students. The Check may be that the current
     *  load does not exceed this hard coded value.
     *
     *  @name Right Comparison Value
     */
    public String getRightComparisonValue();

    /**
     * The Left Comparison Agenda Id is for comparison Check Types of
     * min/max/equals for determining the left-hand side of the
     * comparison.
     *
     * For example, this rule evaluation may determine the current
     * credit load for a particular student in a Term to compare it
     * with a credit limit as determined by getRightComparisonValue()
     * or an evaluation resulting from getRightComparisonAgendaId().
     *
     * @name Left Comparison Agenda Id
     */
    public String getLeftComparisonAgendaId();

    /**
     * The Right Comparison Agenda Id is for comparison Check Types of
     * min/max/equals for determining the right-hand side of the
     * comparison.
     *
     *  For example, the result of this rule evaluation may calculate
     *  the credit limit for a particular student if it fluctuates by
     *  other criteria such as Term or Population.
     *
     * @name Right Comparison Agenda Id
     */
    public String getRightComparisonAgendaId();
}
