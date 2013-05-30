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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates a matrix to determine whether going from a fromState to a toState is valid.
 * Both states are valid
 * If the value is null, then it wasn't tested.
 *
 * @author Kuali Student Team
 */
public class PseudoUnitTestStateTransitionGrid {
    List<String> stateKeys;
    List<String> allowedValues;
    List<List<String>> expectedTransitions;
    List<List<String>> actualTransitions;
    String socStateKey;
    String entityType;  // ao, fo, co

    public PseudoUnitTestStateTransitionGrid(List<String> stateNames, List<String> allowedValues, String entityType) {
        this.entityType = entityType;
        this.stateKeys = new ArrayList<String>();
        this.stateKeys.addAll(stateNames); // make a deep copy
        this.allowedValues = new ArrayList<String>();
        this.allowedValues.addAll(allowedValues);
        // Create the grid
        expectedTransitions = _createTransitionsGrid();
        actualTransitions = _createTransitionsGrid();
    }

    private List<List<String>> _createTransitionsGrid() {
        List<List<String>> grid = new ArrayList<List<String>>();  // Set size to 0
        int size = stateKeys.size();
        for (int i = 0; i < size; i++) {
            List<String> row = new ArrayList<String>();
            for (int j = 0; j < size; j++) {
                row.add(TransitionGridYesNoEnum.INVALID.getName());  // -1 acts as null
            }
            grid.add(row);
        }
        return grid;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setSocStateKey(String socStateKey) {
        this.socStateKey = socStateKey;
    }

    public String getSocStateKey() {
        return socStateKey;
    }

    public void setTransition(AFUTTypeEnum actualOrExpected, String fromState, String toState, String value) {
        if (!allowedValues.contains(value)) {
            throw new RuntimeException(value + " is in invalid value for grid: " + entityType);
        }
        if (!stateKeys.contains(fromState) || !stateKeys.contains(toState)) {
            throw new RuntimeException("Invalid fromState or toState");
        }
        int fromIndex = stateKeys.indexOf(fromState);
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("fromIndex out of bounds: " + fromIndex);
        }
        int toIndex = stateKeys.indexOf(toState);
        if (toIndex < 0) {
            throw new IndexOutOfBoundsException("toIndex out of bounds: " + toIndex);
        }
        if (actualOrExpected == AFUTTypeEnum.EXPECTED) {
            expectedTransitions.get(fromIndex).set(toIndex, value);
        } else {
            actualTransitions.get(fromIndex).set(toIndex, value);
        }
    }

    public String getTransition(AFUTTypeEnum actualOrExpected, String fromState, String toState) {
        int fromIndex = stateKeys.indexOf(fromState);
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("fromIndex out of bounds: " + fromIndex);
        }
        int toIndex = stateKeys.indexOf(toState);
        if (toIndex < 0) {
            throw new IndexOutOfBoundsException("toIndex out of bounds: " + toIndex);
        }
        if (actualOrExpected == AFUTTypeEnum.EXPECTED) {
            return expectedTransitions.get(fromIndex).get(toIndex);
        } else {
            return actualTransitions.get(fromIndex).get(toIndex);
        }
    }

    public String getStateKeyAt(int index) {
        return stateKeys.get(index);
    }

    public int size() {
        return stateKeys.size();
    }

    public String getTransition(AFUTTypeEnum actualOrExpected, int row, int col) {
        if (actualOrExpected == AFUTTypeEnum.EXPECTED) {
            return expectedTransitions.get(row).get(col);
        } else {
            return actualTransitions.get(row).get(col);
        }
    }

    public String setTransition(AFUTTypeEnum actualOrExpected, int row, int col, String val) {
        if (actualOrExpected == AFUTTypeEnum.EXPECTED) {
            return expectedTransitions.get(row).set(col, val);
        } else {
            return actualTransitions.get(row).get(col);
        }
    }

    // Keys
    public static final String SOC_STATE = "socState";
    public static final String AO_STATE_FROM = "aoStateFrom";
    public static final String AO_STATE_TO = "aoStateTo";
    public static final String EXPECTED = "expected";
    public static final String ACTUAL = "actual";
    public static final String PASS_FAIL = "passFail";
    // Values
    public static final String PASS_VAL = "pass";
    public static final String FAIL_VAL = "fail";

    public List<Map<String, String>> compare() {
        List<Map<String, String>> results = new ArrayList<Map<String, String>>();
        for (int row = 0; row < size(); row++) {
            for (int col = 0; col < size(); col++) {
                String expectedVal = getTransition(AFUTTypeEnum.EXPECTED, row, col);
                String actualVal = getTransition(AFUTTypeEnum.ACTUAL, row, col);
                Map<String, String> keyValue = new HashMap<String, String>();
                String fromState = getStateKeyAt(row);
                String toState = getStateKeyAt(col);
                keyValue.put(AO_STATE_FROM, fromState);
                keyValue.put(AO_STATE_TO, toState);
                keyValue.put(EXPECTED, expectedVal);
                keyValue.put(ACTUAL, actualVal);
                keyValue.put(SOC_STATE, getSocStateKey());
                keyValue.put(PASS_FAIL, expectedVal.equals(actualVal) ? PASS_VAL : FAIL_VAL);
                if (expectedVal.equals(TransitionGridYesNoEnum.INVALID.getName())) {
                    keyValue.put(PASS_FAIL, TransitionGridYesNoEnum.INVALID.getName());
                }
                results.add(keyValue);
            }
        }
        return results;
    }
}
