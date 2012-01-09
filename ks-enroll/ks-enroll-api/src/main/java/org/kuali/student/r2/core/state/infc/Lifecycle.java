/*
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

package org.kuali.student.r2.core.state.infc;

import org.kuali.student.r2.common.infc.KeyEntity;
import org.kuali.student.r2.common.infc.HasEffectiveDates;

/**
 * Detailed Information about a State Process
 *
 * State Process are used in Kuali Student to define various process
 * keys that might be associated with objectTypes For example: A
 * course might have a lifecycle process or workflow process with
 * different states in each process
 *
 * @author Kuali Student Team (Kamal)
 */

public interface Lifecycle 
    extends KeyEntity, HasEffectiveDates {

    /**
     * Gets the refObjectUri to which this lifecycle is associated.
     *
     * @name Ref Object URI
     */
    public String getRefObjectUri();
}
