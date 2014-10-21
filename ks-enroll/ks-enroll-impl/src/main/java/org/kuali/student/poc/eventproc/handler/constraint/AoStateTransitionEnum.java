/**
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
 *
 * Created by Charles on 10/7/13
 */
package org.kuali.student.poc.eventproc.handler.constraint;

/**
 * Used to fill in a Map<SocAoFromStateToStateElement, AoStateTransitionEnum>
 *
 * @author Kuali Student Team
 */
public enum AoStateTransitionEnum {
    // YES means a valid state change
    // NO means an invalid state change
    // SAME means no state change
    // INVALID means the start state is invalid within the soc state
    YES, NO, SAME, INVALID;
}
