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

import java.util.Date;
import org.kuali.student.r2.common.infc.IdEntity;


/**
 * Information about an ExemptionRequest. 
 *
 * The qualifier, if it exists, qualifies the restriction exemption to
 * a scope, such as for a particular Program.
 *
 * There can only be one override structure per exemption request. The
 * override structure available in this ExmeptionRequest is
 * constrained by the ExemptionRequest Type.
 * What about populations such as all athletes, or some other group of
 * people? 
 *
 * @author tom
 * @since Tue Jun 14 14:22:34 EDT 2011
 */ 
public interface ExemptionRequest extends IdEntity {

    /**
     * Name: Person Id
     * The Id of the Person making the request.
     */
    public String getPersonId();

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
     * Name: Date
     * The date this exemption request.
     */
    public Date getDate();

    /**
     * Name: Restriction Override
     * The data for a restriction override request.
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
     * Name: Hold Override
     * The data for a hold override.
     */
    public HoldOverride getHoldOverride(); 

    /**
     * Name: Statement Override
     * The data for a statement override.
     */
    public StatementOverride getStatementOverride(); 

    /**
     * Name: Learning Result Override
     * The data for a learning result override.
     */
    public LearningResultOverride getLearningResultOverride(); 
}
