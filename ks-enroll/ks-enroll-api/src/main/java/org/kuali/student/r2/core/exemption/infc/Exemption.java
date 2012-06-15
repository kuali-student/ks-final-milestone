/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.r2.core.exemption.infc;

import java.util.Date;
import org.kuali.student.r2.common.infc.IdEntity;


/**
 * Information about an Exemption. 
 * 
 * Extra data qualifies the exemption to a particular scope, such as for a
 * particular Course or Program.
 *
 * There can only be one override structure per exemption. The
 * override structure available in this Exemption is constrained by
 * the Exemption Type.
 *
 * @author tom
 * @since Tue Jun 14 14:22:34 EDT 2011
 */ 
public interface Exemption extends IdEntity {

    /**
     * The Id of the Exemption Request.
     *
     * @name Exemption Request Id
     * @required
     * @readOnly
     */
    public String getExemptionRequestId();

     /**
     * The id of a Process that is being exempted in the
     * the exemption is applied.
     * 
     * Together with the check this identifies which instruction(s) to skip or 
     * or data to be overridden when the process is being evaluated.
     * 
     * @name Process Id
     */
    public String getProcessKey();

    /**
     * The Id of a Check that indicates to what Check in the Process
     * the exemption is applied.
     *
     * @name Check Id
     */
    public String getCheckId();

    
    /**
     * The Id of the Person who was exempted.
     * 
     * This is the same as the person id on the request.
     *
     * @name Person Id
     * @required
     * @readOnly
     * @impl on creates this should be copied from the request and stored on the exemption 
     * @impl on updates this field should not be updated
     */
    public String getPersonId();

    /**
     * The date this exemption becomes effective.
     *
     * If not supplied it should default to today's date.
     * 
     * @name Effective Date
     * @requred
     */
    public Date getEffectiveDate();

    /**
     * The date this exemption expires.
     *
     * @name Expiration Date
     */
    public Date getExpirationDate(); 

    /**
     * The number of times this Exemption may be used.
     * 
     * Should be a positive integer or left null to indicate there is no limit.
     * 
     * @name Use Limit
     */
    public Integer getUseLimit();

    /**
     * The number of times this Exemption was marked as used.
     *
     * Null means that it has not been used or is not being tracked because there is 
     * no corresponding limit.
     * 
     * @name Use Count
     */
    public Integer getUseCount();

    /**
     * The data for a date override.
     *
     * @name Date Override
     */
    public DateOverride getDateOverride(); 

    /**
     * The data for a milestone override.
     *
     * @name Milestone Override
     */
    public MilestoneOverride getMilestoneOverride(); 

    /**
     * The data for a learning result override.
     *
     * @name Learning Result Override
     */
    public LearningResultOverride getLearningResultOverride(); 
}
