/*
 * Copyright 2013 The Kuali Foundation Licensed under the
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
package org.kuali.student.r2.core.ges.infc;

import org.kuali.student.r2.common.infc.IdNamelessEntity;

/**
 * A key to a possibly empty set of values.
*/
public interface Parameter extends IdNamelessEntity {

    /**
     * The user friendly key for this parameter
     * @name Key Id
     * @readOnly
     * @required
     */
    String getKey();

    /**
     * Restricts the types of values that may be associated with this parameter.
     * @name Value Type Key
     * @readOnly
     * @required
     */
    String getValueTypeKey();

    /**
     * Indicates if duplicate priorities are allowed for all values associated with this parameter.
     * @name Require Unique Priorities
     */
    Boolean getRequireUniquePriorities();
}
