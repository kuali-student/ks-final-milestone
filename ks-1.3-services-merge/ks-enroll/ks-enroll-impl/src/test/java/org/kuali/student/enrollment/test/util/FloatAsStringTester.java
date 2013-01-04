/**
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.test.util;


import static org.junit.Assert.*;

/**
 * @author ocleirig
 *
 */
public class FloatAsStringTester {

    /**
     * 
     */
    public FloatAsStringTester() {
    }

    public void check(String expected, String actual) {

        if (expected == null && actual == null) {
            return;
        }
        if (expected == null) {
            fail("expected null but found " + actual);
        }
        if (actual == null) {
            fail("expected " + expected + " but found null");
        }
        Float expFloat = Float.parseFloat(expected);
        Float actFloat = Float.parseFloat(actual);
        assertEquals(expFloat, actFloat);
    }
}
