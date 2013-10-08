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
 * Contains a soc state plus a from state/to state
 *
 * @author Kuali Student Team
 */
public class SocAoFromStateToStateElement {
    private String socState;
    private String fromAoState;
    private String toAoState;

    public SocAoFromStateToStateElement(String socState, String fromAoState, String toAoState) {
        this.socState = socState;
        this.fromAoState = fromAoState;
        this.toAoState = toAoState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SocAoFromStateToStateElement)) {
            return false;
        }

        SocAoFromStateToStateElement that = (SocAoFromStateToStateElement) o;

        if (!fromAoState.equals(that.fromAoState)) {
            return false;
        }
        if (!socState.equals(that.socState)) {
            return false;
        }
        if (!toAoState.equals(that.toAoState)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = socState.hashCode();
        result = 31 * result + fromAoState.hashCode();
        result = 31 * result + toAoState.hashCode();
        return result;
    }
}
