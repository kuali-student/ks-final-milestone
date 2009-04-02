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
import java.util.ArrayList;

import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.MessageContextConstants;
import org.kuali.student.rules.internal.common.statement.propositions.functions.Sum;

/**
 * A constraint that specifies that sum of a list of values is less than the required amount.
 * 
 * @author <a href="mailto:randy@berkeley.edu">Randy Ballew</a>
 */
public class SumProposition<E extends Number> extends AbstractProposition<BigDecimal> {

    private Sum<Number> sumFunction;
	private BigDecimal sum;

    public SumProposition(String id,
    					  String propositionName,
    					  ComparisonOperator operator,
    					  BigDecimal expectedValue,
    					  FactResultDTO factDTO, String factColumn) {
        super(id, propositionName, PropositionType.SUM, operator, expectedValue, 
        		null, null, factDTO, factColumn);
		sumFunction = new Sum<Number>(factDTO, factColumn);
    }

    @Override
    public Boolean apply() {
    	Number num = sumFunction.compute();
    	sum = new BigDecimal(num.toString());

        result = checkTruthValue(sum, this.expectedValue);

        resultValues = new ArrayList<BigDecimal>();
        resultValues.add(sum);

        return result;
    }

    @Override
    public void buildMessageContextMap() {
    	addMessageContext(MessageContextConstants.PROPOSITION_SUM_MESSAGE_CTX_KEY_SUM, sum.toString());
    	BigDecimal ev = (BigDecimal) expectedValue;
    	BigDecimal difference = ev.subtract(sum);
        addMessageContext(MessageContextConstants.PROPOSITION_SUM_MESSAGE_CTX_KEY_SUM_DIFF, difference.toString());
    }
}
