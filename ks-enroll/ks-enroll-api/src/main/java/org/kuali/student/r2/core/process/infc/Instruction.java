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

import java.util.List;

import org.kuali.student.r2.common.infc.Relationship;
import org.kuali.student.r2.common.infc.RichText;


/**
 * Information about a Relationship.
 *
 * @author tom
 * @since Thu Nov 21 14:22:34 EDT 2011
 */ 

public interface Instruction extends Relationship {

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
     *
     * @name Population Ids
     */
    public List<String> getPopulationIds();

    /**
     * The ATP Type keys to which the Check applies.
     *
     * @name Atp Type Keys
     */
    public List<String> getAtpTypeKeys();

    /**
     * The ATP keys to which the Check applies.
     *
     * @name Atp Keys
     */
    public List<String> getAtpKeys();

    /**
     * The text of a message to display to a user on fail or warning
     * for this Instruction.
     *
     * @name Message
     */
    public RichText getMessage();

    /**
     * The position in the Process.
     *
     * @name Position
     */
    public Integer getPosition();

    /**
     * Tests if a failure in this Check results in warning or failure
     * for this step. If true, the Check failure is interpreted as a
     * warning.
     *
     * @name 
     */
    public Boolean getIsWarning();

    /**
     * Tests if processing should continue if this Check fails.
     *
     * @name Continue On Fail
     */
    public Boolean getContinueOnFail();

    /**
     * Tests an Exemption can be applied to this Instruction.
     *
     * @name Is Exemptable
     */
    public Boolean getIsExemptable();
}
