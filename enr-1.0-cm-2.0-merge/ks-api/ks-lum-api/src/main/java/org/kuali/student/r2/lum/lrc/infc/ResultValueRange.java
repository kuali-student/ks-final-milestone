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

/**
 * Captures the legal value range for a numeric result.
 * 
 * @author Kuali Student Team (Kamal)
 */
public interface ResultValueRange {

    /**
     * Min Result Value string Lower end of the value range. Typically
     * corresponds with the short coded form of the result(ex. "1.0",
     * "25.0" etc.) Should the data resultTypeKey of values (min/max)
     * be numbers and not Strings in the value range?
     * 
     * @name Min Value
     * @return a floating point decimal as a string
     */
    public String getMinValue();

    /**
     * Max Result Value string Upper end of the value range. Typically
     * corresponds with the short coded form of the result(ex. "3.0",
     * "100.0" etc.). Upper end can be left empty to indicate
     * unbounded upper end.
     * 
     * @name Max Value
     * @return a floating point decimal as a string
     */
    public String getMaxValue();

    /**
     * Increment number Legal increments in the result values. This
     * has to be a decimal e.g 0.5)
     * 
     * @name Increment
     * @return a floating point decimal as a string
     */
    public String getIncrement();
}
