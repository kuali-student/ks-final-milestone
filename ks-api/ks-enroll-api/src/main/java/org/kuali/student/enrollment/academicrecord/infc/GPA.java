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

package org.kuali.student.enrollment.academicrecord.infc;

import org.kuali.student.r2.common.infc.IdNamelessEntity;

/**
 * Information about a GPA.
 *
 * @author tom
 * @since Tue Sep 06 14:22:34 EDT 2011
 */ 

public interface GPA
    extends IdNamelessEntity {

    /**
     * The GPA value.
     *
     * @return a string representing a floating point decimal number
     * @name Value
     * @readOnly
     * @required
     */
    public String getValue();

    /**
     * The calculation Type key.
     *
     * @name Calculation Type Key
     * @readOnly
     * @required
     */
    public String getCalculationTypeKey();

    /**
     * The key of the scale.
     *
     * @name Scale Key
     * @readOnly
     */
    public String getScaleKey();
}
