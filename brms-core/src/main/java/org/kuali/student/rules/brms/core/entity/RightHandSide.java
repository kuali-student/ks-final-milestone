/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.rules.brms.core.entity;

import javax.persistence.Embeddable;

/**
 * Contains meta data about the right hand side of a Rule Proposition. For example, in "completed any 2 of (MATH101, MATH102,
 * MATH103)" the right hand side is a criterion '2'.
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
@Embeddable
public class RightHandSide {

    String expectedValueType;
    String expectedValue;

    /**
     * Sets up an empty instance.
     */
    public RightHandSide() {
        expectedValueType = null;
        expectedValue = null;
    }

    /**
     * Sets up a RightHandSide instance.
     * 
     * @param businessEntity
     * @param facts
     * @param className
     */
    public RightHandSide(String criterionType, String criterionValue) {
        this.expectedValueType = criterionType;
        this.expectedValue = criterionValue;
    }

    /**
     * @return the criterionType
     */
    public final String getCriterionType() {
        return expectedValueType;
    }

    /**
     * @param criterionType
     *            the criterionType to set
     */
    public final void setCriterionType(String criterionType) {
        this.expectedValueType = criterionType;
    }

    /**
     * @return the criterionValues
     */
    public final String getCriterionValue() {
        return expectedValue;
    }

    /**
     * @param criterionValues
     *            the criterionValues to set
     */
    public final void setCriterionValues(String criterionValues) {
        this.expectedValue = criterionValues;
    }
}
