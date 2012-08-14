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

import org.kuali.student.r2.common.infc.HasEffectiveDates;
import org.kuali.student.r2.common.infc.KeyEntity;

/**
 * The Result Scale defines a set of valid ResultValues. A ResultScale
 * may describe a set of ResultValue entities or describe a numeric
 * range, but not both. Numeric Ranges are specified using the
 * ResultValueRange. The valid ResultValues are available through the
 * service method getResultValuesForScale().
 */

public interface ResultScale extends KeyEntity, HasEffectiveDates {

    /**
     * The range contained within this result value group. This is
     * optional and might not be present for some Result Components
     * 
     * @name Result Value Range
     */
    public ResultValueRange getResultValueRange();
}
