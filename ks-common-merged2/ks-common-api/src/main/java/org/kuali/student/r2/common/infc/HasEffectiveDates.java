/**
 * Copyright 2010 The Kuali Foundation 
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

package org.kuali.student.r2.common.infc;

import java.util.Date;

/**
 * A common service pattern for entities. This interface is applied to
 * temporal entities with effective dates.
 *
 * @author nwright
 */
public interface HasEffectiveDates {

    /**
     * Date/time this object became effective. Must be less than or
     * equal to the expirationDate specified.
     * 
     * @name Effective Date
     */
    public Date getEffectiveDate();
    
    /**
     * Date/time this relationship is no longer effective. Must be
     * greater than or equal to the effectiveDate specified.
     * 
     * @name Expiration Date
     */
    public Date getExpirationDate();
}
