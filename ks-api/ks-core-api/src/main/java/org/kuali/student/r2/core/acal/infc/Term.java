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

package org.kuali.student.r2.core.acal.infc;

import java.util.Date;

import org.kuali.student.r2.common.infc.IdEntity;


/**
 * Information about a Term.
 *
 * A term is the time period in which a  course may be offered.
 *
 * @author tom
 * @since Tue Apr 05 14:22:34 EDT 2011
 */ 

public interface Term 
    extends IdEntity {

    /**
     * Gets a display code for this Term.
     *
     * @name Code
     */
    public String getCode();

    /**
     * Date and time the term became effective. This
     * does not provide a bound on date ranges or milestones
     * associated with this time period, but instead indicates the
     * time period proper. This is a similar concept to the effective
     * date on enumerated values. When an expiration date has been
     * specified, this field must be less than or equal to the
     * expiration date.
     *
     * @name Start Date
     */
    public Date getStartDate();
  
    /**
     * Date and time the term expires. This does not
     * provide a bound on date ranges or milestones associated with
     * this time period, but instead indicates the time period
     * proper. If specified, this must be greater than or equal to the
     * effective date. If this field is not specified, then no
     * expiration date has been currently defined and should
     * automatically be considered greater than the effective date.
     *
     * @name End Date
     */
    public Date getEndDate();  
}
