/**
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

package org.kuali.student.brms.rulemanagement.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Contains meta data about the right hand side of a Rule Proposition. For example, in "completed any 2 of (MATH101, MATH102,
 * MATH103)" the right hand side is a criterion '2'.
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@Embeddable
public class RightHandSide {

    @Column(name="EXPECTED_VALUE")
    String expectedValue;


    /**
     * @return the expectedValue
     */
    public final String getExpectedValue() {
        return expectedValue;
    }

    /**
     * @param expectedValue
     *            the expectedValue to set
     */
    public final void setExpectedValue(String expectedValue) {
        this.expectedValue = expectedValue;
    }
}
