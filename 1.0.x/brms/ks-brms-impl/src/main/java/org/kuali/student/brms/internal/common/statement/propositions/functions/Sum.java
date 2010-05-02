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

package org.kuali.student.brms.internal.common.statement.propositions.functions;

import java.math.BigDecimal;
import java.util.Map;

import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.internal.common.statement.exceptions.IllegalFunctionStateException;

public class Sum<T extends Number> extends AbstractFunction<Number> {

	private Number result;

    private FactResultInfo factDTO;
    private String factColumnKey;
    
    public Sum() {
	}

    public Sum(FactResultInfo factDTO, String factColumnKey) {
    	this.factDTO = factDTO;
    	this.factColumnKey = factColumnKey;
	}

    public void setFact(FactResultInfo factDTO, String factColumnKey) {
    	this.factDTO = factDTO;
    	this.factColumnKey = factColumnKey;
    }

    public Number compute() {
    	if(this.factDTO == null) {
    		throw new IllegalFunctionStateException("Fact is null: " + this.factDTO);
    	} else if(this.factColumnKey == null) {
    		throw new IllegalFunctionStateException("Fact column key is null: " + this.factColumnKey);
    	} else if(!containsFactColumnKey(this.factDTO, this.factColumnKey)) {
    		throw new IllegalFunctionStateException("Fact column key not found: " + this.factColumnKey);
    	}
    	this.result = sum();
		return this.result;
	}

    /**
     * This method sums all the elements in the fact list.
     * 
     * @return Sum of all the element in the fact list
     */
    protected Number sum() {
        BigDecimal sum = new BigDecimal("0.0");

        for (Map<String, String> row : this.factDTO.getResultList()) {
            String value = row.get(this.factColumnKey);
        	sum = sum.add(new BigDecimal(value));
        }

        return sum;
    }
}
