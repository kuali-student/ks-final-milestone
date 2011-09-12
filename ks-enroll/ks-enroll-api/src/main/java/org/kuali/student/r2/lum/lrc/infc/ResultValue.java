/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.lum.lrc.infc;

import org.kuali.student.r2.common.infc.KeyEntity;
import org.kuali.student.r2.common.infc.HasEffectiveDates;

/**
 * A Result Value.
 */

public interface ResultValue extends KeyEntity, HasEffectiveDates {

    /**
     * Identifier of the scale for this result value.
     * 
     * @name Scale Key
     */
    public String getResultScaleKey();

    /**
     * Rank of the result value within the scale. Standards around
     * uniqueness and meaning of value are described in the
     * information about the scale.
     * 
     * @name Rank
     * @return a floating point decimal as a string
     */
    public String getNumericValue();      

    /**
     * Result Value string Value of the result. Typically corresponds with the
     * short coded form of the result(ex. "A", "4.0", "97.0", "B.S" etc.)
     * scaleKey Scale Identifier scaleKey.
     *
     * @name Result value
     */
    public String getValue();
}
