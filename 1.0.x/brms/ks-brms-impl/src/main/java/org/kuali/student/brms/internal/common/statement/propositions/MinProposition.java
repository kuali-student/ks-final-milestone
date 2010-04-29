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

package org.kuali.student.brms.internal.common.statement.propositions;

import java.util.ArrayList;

import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.internal.common.entity.ComparisonOperator;
import org.kuali.student.brms.internal.common.statement.MessageContextConstants;
import org.kuali.student.brms.internal.common.statement.propositions.functions.Min;

public class MinProposition<T extends Comparable<T>> extends AbstractProposition<T> {
    private Min<T> minFunction;
    private T min;

    public MinProposition() {
    }
    
    public MinProposition(String id, String propositionName, 
    		ComparisonOperator operator, T expectedValue, 
    		FactResultInfo factDTO, String factColumn) {
        super(id, propositionName, PropositionType.MIN, operator, expectedValue, 
        		null, null, factDTO, factColumn);
        this.minFunction = new Min<T>(factDTO, factColumn);
	}

    @Override
    public Boolean apply() {
    	this.min = this.minFunction.compute();

        result = checkTruthValue(this.min);

        resultValues = new ArrayList<T>();
        resultValues.add(this.min);

        return result;
    }

    @Override
    public void buildMessageContextMap() {
        String minStr = getTypeAsString(this.min);
        addMessageContext(MessageContextConstants.PROPOSITION_MIN_MESSAGE_CTX_KEY_MIN, minStr);
    }
}
