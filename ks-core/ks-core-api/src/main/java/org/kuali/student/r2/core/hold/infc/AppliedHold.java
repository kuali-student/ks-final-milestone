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

package org.kuali.student.r2.core.hold.infc;

import java.util.Date;

import org.kuali.student.r2.common.infc.IdEntity;


/**
 * Information about a Hold.
 *
 * @author tom
 * @since Sun May 1 14:22:34 EDT 2011
 */ 

public interface AppliedHold 
    extends IdEntity {

    /**
     * The Hold Issue Id.
     * @name Hold Issue Id
     * @required
     * @readOnly
     */
    public String getHoldIssueId();
   

    /**
     * The Id of the Person.
     * @name Person Id
     * @required
     * @readOnly
     */
    public String getPersonId();
      
    /**
     * The date this hold becomes effective.
     * @name Effective Date
     * @required
     */
    public Date getEffectiveDate();
   
    /**
     * The date this hold was released, of null if not yet released.
     * @name Released Date
     */
    public Date getReleasedDate();
}
