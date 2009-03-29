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
package org.kuali.student.brms.internal.common.statement.propositions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.kuali.student.brms.internal.common.entity.ComparisonOperator;
import org.kuali.student.brms.internal.common.statement.MessageContextConstants;

public class AverageProposition<E extends Number> extends SumProposition<E> {
	private BigDecimal average;
    private BigDecimal listSize;
    
	public AverageProposition(String id, 
							  String propositionName, 
    						  ComparisonOperator operator, 
    						  BigDecimal expectedValue, 
    						  List<E> factSet) {
    	super(id, propositionName, operator, expectedValue, factSet);
    	super.propositionType = PropositionType.AVERAGE;
    	if (factSet == null || factSet.size() == 0) {
    		throw new IllegalArgumentException("Fact set cannot be null");
    	}
    	listSize = new BigDecimal(factSet.size());
    }

    @Override
    public Boolean apply() {
        average = sum().divide(listSize);

        result = checkTruthValue(average, super.expectedValue);

        resultValues = new ArrayList<BigDecimal>();
        resultValues.add(average);

        return result;
    }

    @Override
    public void buildMessageContextMap() {
    	addMessageContext(MessageContextConstants.PROPOSITION_AVERAGE_MESSAGE_CTX_KEY, average.toString());
        BigDecimal needed = expectedValue.subtract(average);
        addMessageContext(MessageContextConstants.PROPOSITION_AVERAGE_MESSAGE_CTX_KEY_DIFF, needed.toString());
    }
}
