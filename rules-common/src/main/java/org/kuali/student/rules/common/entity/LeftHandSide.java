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
package org.kuali.student.rules.common.entity;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

/**
 * Contains meta data about the left hand side of a Rule Proposition. For example, in "completed any 2 of (MATH101, MATH102,
 * MATH103)" the left hand side is "completed set of (MATH101, MATH102, MATH103).
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
@Embeddable
public class LeftHandSide {

    @OneToOne(optional = true, cascade = CascadeType.ALL, targetEntity = YieldValueFunction.class)
    private YieldValueFunction yieldValueFunction;
    private String criteria;

    /**
     * Sets up an empty instance.
     */
    public LeftHandSide() {
        criteria = null;
        yieldValueFunction = null;
    }

    /**
     * Sets up a LeftHandSide instance.
     * 
     * @param yieldValueFunction
     */
    public LeftHandSide(String criteria, YieldValueFunction yieldValueFunction) {
        this.criteria = criteria;
        this.yieldValueFunction = yieldValueFunction;
    }

    /**
     * @return the criteria
     */
    public final String getCriteria() {
        return criteria;
    }

    /**
     * @param criteria
     *            the criteria to set
     */
    public final void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    /**
     * @return the yieldValueFunction
     */
    public final YieldValueFunction getYieldValueFunction() {
        return yieldValueFunction;
    }

    /**
     * @param yieldValueFunction
     *            the yieldValueFunction to set
     */
    public final void setYieldValueFunction(YieldValueFunction yieldValueFunction) {
        this.yieldValueFunction = yieldValueFunction;
    }
}
