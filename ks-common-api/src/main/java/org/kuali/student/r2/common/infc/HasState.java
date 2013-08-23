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

/**
 * A common service pattern for entities. This interface is applied to
 * entities that have a State.
 *
 * @author nwright
 */

public interface HasState  {

    /**
     * A unique identifier for the state of this object.
     * 
     * @name State Key
     * @required
     */
    public String getStateKey();
  
    /**
     * Alternate method to get the stateKey to maintain R1
     * compatibility. Consumers should use getStateKey() instead.
     * 
     * @name State
     */
    @Deprecated
    public String getState();
}
