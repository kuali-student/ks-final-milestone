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

package org.kuali.student.enrollment.exemption.infc;

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
     * Name: Exemption Request Id
     * The Id of the Exemption Request.
     */
    public String getExemptionRequestId();

    /**
     * Name: Exempted Person Id
     * The Id of the Person exempted.
     */
    public String getExemptedPersonId();

    /**
     * Name: Qualifier Type Key
     * The Type of a Qualifier to the Exemption.
     */
    public String getQualifierTypeKey();

    /**
     * Name: Qualifier Id
     * The Id of a Qualifier to the Exemption.
     */
    public String getQualifierId();

    /**
     * Name: Effective Date
     * The date this exemption becomes effective.
     */
    public Date getEffectiveDate();

    /**
     * Name: Expiration Date
     * The date this exemption expires.
     */
    public Date getExpirationDate(); 

    /**
     * Name: Restriction Override
     * The data for a restriction override.
     */
    public RestrictionOverride getRestrictionOverride(); 

    /**
     * Name: Date Override
     * The data for a date override.
     */
    public DateOverride getDateOverride(); 

    /**
     * Name: Milestone Override
     * The data for a milestone override.
     */
    public MilestoneOverride getMilestoneOverride(); 

    /**
     * Name: Statement Override
     * The data for a statement override.
     */
    public StatementOverride getStatementOverride(); 

    /**
     * Name: Hold Override
     * The data for a hold override.
     */
    public HoldOverride getHoldOverride(); 

    /**
     * Name: Learning Result Override
     * The data for a learning result override.
     */
    public LearningResultOverride getLearningResultOverride(); 
}
