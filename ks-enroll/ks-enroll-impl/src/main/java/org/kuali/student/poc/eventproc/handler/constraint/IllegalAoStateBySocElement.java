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
 * Represent an illegal AO state within a SOC state.
 *
 * @author Kuali Student Team
 */
public class IllegalAoStateBySocElement {
    private String socState;
    private String aoState;

    public IllegalAoStateBySocElement(String socState, String aoState) {
        this.socState = socState;
        this.aoState = aoState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IllegalAoStateBySocElement)) {
            return false;
        }

        IllegalAoStateBySocElement that = (IllegalAoStateBySocElement) o;

        if (!aoState.equals(that.aoState)) {
            return false;
        }
        if (!socState.equals(that.socState)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = socState.hashCode();
        result = 31 * result + aoState.hashCode();
        return result;
    }

    public String getSocState() {
        return socState;
    }

    public String getAoState() {
        return aoState;
    }
}
