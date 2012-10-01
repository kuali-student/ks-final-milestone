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
     * Minimum Result Value allowed for this range.
     *
     * 
     * @name Min Value
     * @return a floating point decimal as a string
     * @required
     */
    public String getMinValue();

    /**
     * Maximum Result Value allowed for this range.
     * 
     * @name Max Value
     * @return a floating point decimal as a string
     * @required
     */
    public String getMaxValue();

    /**
     * Legal increment of the value within the range
     *  
     * @name Increment
     * @return a floating point decimal as a string
     * @required
     */
    public String getIncrement();
}
