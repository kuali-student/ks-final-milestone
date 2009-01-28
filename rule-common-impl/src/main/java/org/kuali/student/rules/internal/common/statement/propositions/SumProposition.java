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
package org.kuali.student.rules.internal.common.statement.propositions;

import java.math.BigDecimal;
import java.util.List;

import org.kuali.student.rules.internal.common.entity.ComparisonOperator;

/**
 * A constraint that specifies that sum of a list of values is less than the required amount.
 * 
 * @param <E>
 *            the type of elements being constrained
 * @author <a href="mailto:randy@berkeley.edu">Randy Ballew</a>
 */
public class SumProposition<E extends Number> extends AbstractProposition<BigDecimal> {
    // ~ Instance fields --------------------------------------------------------

    List<E> factSet;

    // ~ Constructors -----------------------------------------------------------

    public SumProposition() {
        super();
    }

    public SumProposition(String id, String propositionName, ComparisonOperator operator, BigDecimal expectedValue, List<E> factSet) {
        super(id, propositionName, PropositionType.SUM, operator, expectedValue);
    	if (factSet == null || factSet.size() == 0) {
    		throw new IllegalArgumentException("Fact set cannot be null");
    	}
        this.factSet = factSet;
    }

    // ~ Methods ----------------------------------------------------------------

    @Override
    public Boolean apply() {
        BigDecimal sum = sum();

        result = checkTruthValue(sum, super.expectedValue);

        cacheReport("Sum is short by %s", sum, super.expectedValue);

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.kuali.rules.constraint.AbstractConstraint#cacheAdvice(java.lang.String, java.lang.Object[])
     */
    @Override
    protected void cacheReport(String format, Object... args) {
    	if (result) {
            report.setSuccessMessage("Sum constraint fulfilled");
            return;
        }

        BigDecimal sum = (BigDecimal) args[0];
        BigDecimal expectedValue = (BigDecimal) args[1];

        // TODO: Use the operator to compute exact message
        BigDecimal needed = expectedValue.subtract(sum);
        String advice = String.format(format, needed.toString());
        report.setFailureMessage(advice);
    }

    /**
     * This method sums all the element in the fact list
     * 
     * @return
     */
    protected BigDecimal sum() {
        BigDecimal sum = new BigDecimal("0.0");

        for (E element : factSet) {
            sum = sum.add(new BigDecimal(element.toString()));
        }

        return sum;
    }

	/**
     * @return the factSet
     */
    public List<E> getFactSet() {
        return factSet;
    }

    /**
     * @param factSet
     *            the factSet to set
     */
    public void setFactSet(List<E> factSet) {
        this.factSet = factSet;
    }
    
}
