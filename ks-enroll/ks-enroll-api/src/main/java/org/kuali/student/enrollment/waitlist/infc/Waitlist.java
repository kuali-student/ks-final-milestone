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

package org.kuali.student.enrollment.waitlist.infc;

import org.kuali.student.r2.common.infc.IdEntity;


/**
 * Information about a Waitlist.
 *
 * @Author tom
 * @Since Tue May 10 14:22:34 EDT 2011
 */ 

public interface Waitlist extends IdEntity {

    /**
     * Name: Offering Id
     * The Id of the related offering. Note: don't yet know
     * what kind of offering this is, but it does map
     * to a LUI Id. 
     */
    public String getOfferingId();

    /**
     * Name: Registration Type
     * The type of the registration for this waitlist. This maps to
     * the LPR type.
     */
    public String getRegistrationType();
}
