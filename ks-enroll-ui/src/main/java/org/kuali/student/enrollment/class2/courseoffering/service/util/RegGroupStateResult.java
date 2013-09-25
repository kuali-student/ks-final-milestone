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
 * Created by Charles on 5/30/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service.util;

import edu.emory.mathcs.backport.java.util.Arrays;
import org.kuali.student.enrollment.class2.courseoffering.service.exception.PseudoUnitTestException;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class RegGroupStateResult {
    private List<String> expected;
    private List<String> actual;
    private int numAos;
    private int numPermutations;
    private static List<String> RG_STATES = Arrays.asList(LuiServiceConstants.REGISTRATION_GROUP_LIFECYCLE_KEY_STATES);

    public RegGroupStateResult(int numAos) {
        // The index
        this.numAos = numAos;
        numPermutations = (int) Math.pow(2, numAos);
        expected = new ArrayList<String>();
        actual = new ArrayList<String>();
        while (expected.size() < numPermutations) {
            expected.add(LuiServiceConstants.REGISTRATION_GROUP_PENDING_STATE_KEY);
            actual.add("empty");
        }
        expected.set(expected.size() - 1, LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY);
    }

    public String getExpected(int index) {
        return expected.get(index);
    }

    public String getActual(int index) {
        return actual.get(index);
    }

    public void setActual(int index, String rgState) throws PseudoUnitTestException {
        if (!RG_STATES.contains(rgState)) {
            throw new PseudoUnitTestException("Invalid rgState: " + rgState);
        }
        actual.set(index, rgState);
    }

    public boolean isSame(int index) {
        return expected.get(index).equals(actual.get(index));
    }

    public int size() {
        return expected.size();
    }

    public int numAos() {
        return numAos;
    }
}
