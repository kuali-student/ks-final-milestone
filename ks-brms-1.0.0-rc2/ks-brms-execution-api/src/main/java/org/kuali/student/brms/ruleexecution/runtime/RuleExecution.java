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

package org.kuali.student.brms.ruleexecution.runtime;

import java.util.List;
import java.util.Map;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.brms.ruleexecution.dto.AgendaExecutionResultInfo;
import org.kuali.student.brms.ruleexecution.dto.ExecutionResultInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleAnchorInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;

/**
 * This is the rule execution runtime interface.
 * 
 * @author lcarlsen
 *
 */
public interface RuleExecution {

	/**
     * Executes an <code>agenda</code> with <code>exectionParamMap</code>.
     * <code>exectionParamMap</code> must match the fact criteria type meta data.
	 * 
     * @param agenda Agenda to execute
     * @param exectionParamMap Execution fact parameter map
	 * @return Result of executing the agenda
     * @throws DoesNotExistException Thrown if agenda does not exist
     * @throws InvalidParameterException Thrown if agenda is invalid
     * @throws MissingParameterException Thrown if agenda is null or has missing parameters
     * @throws OperationFailedException Thrown if execution fails
	 */
    public AgendaExecutionResultInfo executeAgenda(List<BusinessRuleAnchorInfo> businessRuleAnchorInfoList, Map<String,String> exectionParamMap)
		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Executes a business rule by <code>businessRuleId</code> with a 
     * <code>exectionParamMap</code>.
     * <code>exectionParamMap</code> must match the fact criteria type meta data.
     * <code>exectionParamMap</code> can be null for static facts. </p>
     * 
     * @param businessRule A Business rule
     * @param exectionParamMap Execution fact parameter map
	 * @return Result of executing the business rule
     * @throws DoesNotExistException Thrown if business rule id does not exist
     * @throws InvalidParameterException Thrown if business rule id is invalid
     * @throws MissingParameterException Thrown if business rule id is null or empty
     * @throws OperationFailedException Thrown if execution fails
     */
    public ExecutionResultInfo executeBusinessRule(String businessRuleId, Map<String,String> exectionParamMap)
		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Executes a business rule by <code>businessRuleId</code> with a 
     * <code>exectionParamMap</code>. Returns true if rule was successful; 
     * otherwise false if rule failed.
     * 
     * @param businessRuleId A Business rule id
     * @param exectionParamMap Execution fact parameter map
     * @return True if rule was successful; otherwise false if rule failed
     * @throws DoesNotExistException Thrown if business rule id does not exist
     * @throws InvalidParameterException Thrown if business rule id is invalid
     * @throws MissingParameterException Thrown if business rule id is null or empty
     * @throws OperationFailedException Thrown if execution fails
     */
//    public Boolean executeBusinessRuleWithNoReport(String businessRuleId, Map<String,String> exectionParamMap)
//		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * <p>Executes a business rule with a <code>factStructure</code> to test 
     * that it executes properly.<br/> 
     * <code>exectionParamMap</code> must match the fact criteria type meta data.
     * <code>exectionParamMap</code> can be null for static facts. </p>
     * <p><b>Note:</b> The business rule is <b>not</b> stored in the 
     * rule repository <b>nor</b> cached in rule execution memory.</p>
     * 
     *  
     * @param businessRule Functional business rule
     * @param exectionParamMap Execution fact parameter map
	 * @return Result of executing the business rule
     * @throws DoesNotExistException Thrown if business rule id does not exist
	 * @throws MissingParameterException Thrown if parameter is missing
     * @throws InvalidParameterException Thrown if method parameters are invalid
     * @throws OperationFailedException Thrown if business rule translation or execution fails
     */
    public ExecutionResultInfo executeBusinessRuleTest(BusinessRuleInfo businessRule, Map<String,String> exectionParamMap)
		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
}
