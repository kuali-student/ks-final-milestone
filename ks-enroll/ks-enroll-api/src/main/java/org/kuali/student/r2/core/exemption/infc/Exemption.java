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

import org.kuali.student.r2.common.infc.IdEntity;
import java.util.Date;


/**
 * Information about an Exemption. The qualifier, if it exists,
 * qualifies the restriction exemption to a scope, such as for a
 * particular Course or Program.
 *
 * There can only be one override structure per exemption. The
 * override structure available in this Exmeption is constrained by
 * the Exemption Type.
 *
 * org?
 *
 * @author tom
 * @since Tue Jun 14 14:22:34 EDT 2011
 */ 
public interface Exemption extends IdEntity {

    /**
     * The Id of the Exemption Request.
     *
     * @name Exemption Request Id
     */
    public String getExemptionRequestId();

     /**
     * The key of a Process that indicates to what Process in the
     * the exemption is applied.
     *
     * @name Process Key
     * @required
     */
    public String getProcessKey();

    /**
     * The Id of a Check that indicates to what Check in the Process
     * the exemption is applied.
     *
     * @name Check Id
     */
    public String getCheckKey();

    
    /**
     * The Id of the Person who was exempted.
     *
     * @name Person Id
     */
    public String getPersonId();

    /**
     * The date this exemption becomes effective.
     *
     * @name Effective Date
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
     * @name Use Limit
     */
    public Integer getUseLimit();

    /**
     * The number of times this Exemption was marked as used.
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
