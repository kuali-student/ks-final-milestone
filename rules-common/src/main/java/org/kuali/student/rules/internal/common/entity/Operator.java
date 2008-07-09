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
package org.kuali.student.rules.internal.common.entity;

import javax.persistence.Embeddable;


/**
 * Contains meta data about the operator of a Rule Proposition. For example, in "completed any 2 of (MATH101, MATH102,
 * MATH103)", the Drool rule operator is '='.
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
@Embeddable
public class Operator {

    ComparisonOperator operator;

    /**
     * Sets up an empty instance.
     */
    public Operator() {
        operator = null;
    }

    /**
     * Sets up a Operator instance.
     * 
     * @param businessEntity
     * @param facts
     * @param className
     */
    public Operator(ComparisonOperator value) {
        this.operator = value;
    }

    /**
     * @return the value
     */
    public final ComparisonOperator getValue() {
        return operator;
    }

    /**
     * @return the operator
     */
    public final ComparisonOperator getOperator() {
        return operator;
    }

    /**
     * @param operator
     *            the operator to set
     */
    public final void setOperator(ComparisonOperator operator) {
        this.operator = operator;
    }
}
