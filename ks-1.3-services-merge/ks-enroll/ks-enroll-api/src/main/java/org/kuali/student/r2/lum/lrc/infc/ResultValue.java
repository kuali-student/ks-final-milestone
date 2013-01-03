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
     * @name Result Scale Key
     * @required
     */
    public String getResultScaleKey();

    /**
     * A numeric value used to ranking or order this result value
     * within the scale.
     * 
     * For Grades it holds the quality points, i.e. A=4.0, B=3.0.  For
     * credits it holds the actual numeric credits.  For degrees it is
     * used to indicate perhaps how many years of study it typically
     * takes to achieve that degree.
     * 
     * @name Numeric Value
     * @impl this is a floating point decimal but encoded as a string to avoid rounding issues
     */
    public String getNumericValue();      

    /**
     * The Value of this Result. 
     * 
     * Typically corresponds with the short coded form of the result (ex. "A", 
     * "B", "C" or for "100.00", "97.0", "B.S" etc.)  
     *   
     * Should be unique within it's result scale.
     * 
     * @name value
     * @required
     */
    public String getValue();
}
