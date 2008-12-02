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

public class AverageProposition<E extends Number> extends SumProposition<E> {

    private BigDecimal listSize;
    
	public AverageProposition(String id, 
							  String propositionName, 
    						  ComparisonOperator operator, 
    						  BigDecimal expectedValue, 
    						  List<E> factSet) {
    	super(id, propositionName, operator, expectedValue, factSet);
    	if (factSet == null || factSet.size() == 0) {
    		throw new IllegalArgumentException("Fact set cannot be null");
    	}
    	listSize = new BigDecimal(factSet.size());
    }

    @Override
    public Boolean apply() {
        BigDecimal average = sum().divide(listSize);

        result = checkTruthValue(average, super.expectedValue);

        cacheReport("Average is short by %s", average, super.expectedValue);

        return result;
    }

    @Override
    protected void cacheReport(String format, Object... args) {
        if (result) {
            report.setSuccessMessage("Average constraint fulfilled");
            return;
        }

        BigDecimal sum = (BigDecimal) args[0];
        BigDecimal expectedValue = (BigDecimal) args[1];

        // TODO: Use the operator to compute exact message
        BigDecimal needed = expectedValue.subtract(sum);
        String advice = String.format(format, needed.toString());
        report.setFailureMessage(advice);
    }
}
