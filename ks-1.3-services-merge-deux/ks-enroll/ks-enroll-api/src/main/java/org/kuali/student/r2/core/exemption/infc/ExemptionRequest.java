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
 * Information about an ExemptionRequest. 
 *
 * The qualifier, if it exists, qualifies the restriction exemption to
 * a scope, such as for a particular Program.
 *
 * There can only be one override structure per exemption request. The
 * override structure available in this ExmeptionRequest is
 * constrained by the ExemptionRequest Type.  What about populations
 * such as all athletes, or some other group of people?
 *
 * @author tom
 * @since Tue Jun 14 14:22:34 EDT 2011
 */
public interface ExemptionRequest extends IdEntity {

    /**
     * The id of a Process that indicates to what Process in the
     * the exemption is requested, if any.
     *
     * @name Process id
     */
    public String getProcessKey();

    /**
     * The Id of a Check that indicates to what Check in the Process
     * the exemption is requested, if any.
     *
     * @name Check Id
     */
    public String getCheckId();

    /**
     * The Id of the Person for whom the request is requested
     *
     * @name Person Id
     * @required
     * @readOnly on update
     */
    public String getPersonId();

    /**
     * The Id of the Person making the request.
     * 
     * If not specified it should default to the person identified in the supplied
     * contextInfo.
     *
     * @name Requester Id
     */
    public String getRequesterId();

    /**
     * The date of this exemption request.
     * 
     * If not specified it should default to today's date.
     *
     * @name Request Date
     * @required
     */
    public Date getRequestDate();

    /**
     * The Id of the Person who approved this request.
     *
     * @name Approved By Person Id
     */
    public String getApprovedByPersonId();

    /**
     * The date this request was approved.
     *
     * @name Approved Date
     */
    public Date getApprovedDate();

    /**
     * The detailed data for a date override.
     *
     * @name Date Override
     */
    public DateOverride getDateOverride();

    /**
     * The data for a milestone override.
     *
     * TODO: understand the exact usage of this.
     * 
     * @name Milestone Override
     */
    public MilestoneOverride getMilestoneOverride();

    /**
     * The data for a learning result override.
     * 
     * This is deprecated because it is just a PLACEHOLDER for eventual overrides 
     * that are used in degree audit.
     * 
     * @name Learning Result Override
     */
    @Deprecated
    public LearningResultOverride getLearningResultOverride();
}
