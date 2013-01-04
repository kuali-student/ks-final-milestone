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

import org.kuali.student.r2.common.infc.Relationship;
import org.kuali.student.r2.common.infc.RichText;

import java.util.List;


/**
 * Information about an Instruction. An Instruction is a Relationship
 * between a Process and a Check. Instructions contain a set of
 * applied data that determines if the Check applies to a Process.
 *
 * To determine if a Check applies to a Process:
 *      1. the person is a member of any applied Populations
 *      2. and the current ATP type is any of the applied ATP Types
 *
 * If any of the above applied elements is empty, then the applied
 * element evaluates to true. If all applied elements are empty, then
 * the Check is globally applied.
 *
 * @author tom
 * @since Thu Nov 21 14:22:34 EDT 2011
 */ 

public interface Instruction 
    extends Relationship {

    /**
     * The process key.
     *
     * @name Process Key
     * @required
     * @readOnly
     */
    public String getProcessKey();

    /**
     * The Check Id.
     *
     * @name Check Id
     * @required
     * @readOnly
     */
    public String getCheckId();

    /**
     * The Population Id to which the Check applies.
     * Check notes on https://wiki.kuali.org/display/STUDENT/SVCS+20120605
     *
     * @name Applied Population Id
     */
    public String getAppliedPopulationId();

    /**
     * The ATP Type keys to which the Check applies.
     * TODO: revisit rules that evaluate to ATPs.
     *
     * @name Applied Atp Type Keys
     */
    public List<String> getAppliedAtpTypeKeys();

    /**
     * The text of a message to display to a user on fail or warning
     * for this Instruction. (todo: substituting variables to create
     * contextual message and handling internationalization)
     *
     * @name Message
     */
    public RichText getMessage();

    /**
     * The position in the Process.
     *
     * @name Position
     * @readOnly
     */
    public Integer getPosition();

    /**
     * Tests if a failure in this Check results in warning or failure
     * for this step. If true, the Check failure is interpreted as a
     * warning. If false, the Check failure is interpreted as an
     * error.
     *
     * @name Is Warning 
     */
    public Boolean getIsWarning();

    /**
     * Tests if processing should continue if this Check fails.
     *
     * @name Continue On Fail
     */
    public Boolean getContinueOnFail();

    /**
     * Tests an Exemption can be requested to this Instruction.
     *
     * @name Is Exemptable
     */
    public Boolean getIsExemptible();
}
