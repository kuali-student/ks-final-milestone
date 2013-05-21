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
 * Created by Charles on 5/10/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a matrix to determine whether going from a fromState to a toState is valid.
 * If the value is null, then it wasn't tested.
 *
 * @author Kuali Student Team
 */
public class PseudoUnitTestStateTransitionGrid {
    List<String> stateKeys;
    List<List<Integer>> transitionAllowed;

    public PseudoUnitTestStateTransitionGrid(List<String> stateNames) {
        this.stateKeys = new ArrayList<String>();
        this.stateKeys.addAll(stateNames); // make a deep copy
        // Create the grid
        int size = stateNames.size();
        transitionAllowed = new ArrayList<List<Integer>>();  // Set size to 0
        for (int i = 0; i < size; i++) {
            List<Integer> row = new ArrayList<Integer>();
            for (int j = 0; j < size; j++) {
                row.add(-1);  // -1 acts as null
            }
            transitionAllowed.add(row);
        }
    }

    public void setTransition(String fromState, String toState, int value) {
        if (value < -1 || value > 1) {
            throw new IndexOutOfBoundsException();
        }
        int fromIndex = stateKeys.indexOf(fromState);
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("fromIndex out of bounds: " + fromIndex);
        }
        int toIndex = stateKeys.indexOf(toState);
        if (toIndex < 0) {
            throw new IndexOutOfBoundsException("toIndex out of bounds: " + toIndex);
        }
        transitionAllowed.get(fromIndex).set(toIndex, value);
    }

    public int getTransition(String fromState, String toState) {
        int fromIndex = stateKeys.indexOf(fromState);
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("fromIndex out of bounds: " + fromIndex);
        }
        int toIndex = stateKeys.indexOf(toState);
        if (toIndex < 0) {
            throw new IndexOutOfBoundsException("toIndex out of bounds: " + toIndex);
        }
        return transitionAllowed.get(fromIndex).get(toIndex);
    }

    public String getStateKeyAt(int index) {
        return stateKeys.get(index);
    }

    public int size() {
        return stateKeys.size();
    }

    public int getTransition(int row, int col) {
        return transitionAllowed.get(row).get(col);
    }

    public int setTransition(int row, int col, int val) {
        return transitionAllowed.get(row).set(col, val);
    }

    public static List<String> compareGrid(PseudoUnitTestStateTransitionGrid expected, PseudoUnitTestStateTransitionGrid actual) {
        List<String> results = new ArrayList<String>();
        int size = expected.size();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int expectedVal = expected.getTransition(row, col);
                int actualVal = actual.getTransition(row, col);
                if (expectedVal != actualVal) {
                    // Mismatch
                    String fromState = expected.getStateKeyAt(row);
                    String toState = expected.getStateKeyAt(col);
                    String message = "[" + fromState + " => " + toState + "] expected: " + expectedVal + " actual: " + actualVal;
                    results.add(message);
                }
            }
        }
        return results;
    }
}
