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
 * Meta Data - Work in Progress
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
@Embeddable
public class BusinessRuleEvaluation {

    String postCondition;
    boolean outcome;
    String[] advice;

    /**
     * Sets up an empty instance.
     */
    public BusinessRuleEvaluation() {
        postCondition = null;
        outcome = false;
        advice = null;
    }

    /**
     * Sets up a RuleProposition instance.
     * 
     * @param postCondition
     * @param outcome
     * @param advice
     */
    public BusinessRuleEvaluation(String postCondition, boolean outcome, String[] advice) {
        this.postCondition = postCondition;
        this.outcome = outcome;
        this.advice = advice;
    }

    /**
     * @return the postCondition
     */
    public final String getPostCondition() {
        return postCondition;
    }

    /**
     * @param postCondition
     *            the postCondition to set
     */
    public final void setPostCondition(String postCondition) {
        this.postCondition = postCondition;
    }

    /**
     * @return the outcome
     */
    public final boolean isOutcome() {
        return outcome;
    }

    /**
     * @param outcome
     *            the outcome to set
     */
    public final void setOutcome(boolean outcome) {
        this.outcome = outcome;
    }

    /**
     * @return the advice
     */
    public final String[] getAdvice() {
        return advice;
    }

    /**
     * @param advice
     *            the advice to set
     */
    public final void setAdvice(String[] advice) {
        this.advice = advice;
    }
}
