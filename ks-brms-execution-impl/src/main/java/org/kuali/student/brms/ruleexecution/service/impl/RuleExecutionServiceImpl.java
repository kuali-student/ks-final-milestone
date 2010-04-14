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

package org.kuali.student.brms.ruleexecution.service.impl;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.kuali.student.brms.ruleexecution.dto.AgendaExecutionResultInfo;
import org.kuali.student.brms.ruleexecution.dto.ExecutionResultInfo;
import org.kuali.student.brms.ruleexecution.dto.ParameterMapInfo;
import org.kuali.student.brms.ruleexecution.runtime.RuleExecution;
import org.kuali.student.brms.ruleexecution.service.RuleExecutionService;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleAnchorInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.brms.ruleexecution.service.RuleExecutionService", 
		serviceName = "RuleExecutionService", 
		portName = "RuleExecutionService", 
		targetNamespace = "http://student.kuali.org/wsdl/brms/RuleExecution")
@Transactional
public class RuleExecutionServiceImpl implements RuleExecutionService {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(RuleExecutionServiceImpl.class);
    
    private RuleExecution ruleExecution;
    
    public void setRuleExecution(RuleExecution ruleExecution) {
		this.ruleExecution = ruleExecution;
	}

	/**
     * <p>Executes an agenda (list of business rules) with 
     * <code>businessRuleAnchorInfoList</code> and <code>exectionParamMap</code>.
     * </p>
     * <code>businessRuleAnchorInfoList</code> contains all the information 
     * to get actual business rule to execute.
     * </br>
     * <code>exectionParamMap</code> must match the fact criteria type 
     * meta data for the business rules.
	 * 
     * @param businessRuleAnchorInfoList Business rule anchor list
     * @param exectionParamMap Execution fact parameter map
	 * @return Result of executing the agenda
     * @throws DoesNotExistException Thrown if business rules do not exist
     * @throws InvalidParameterException Thrown if parameters are invalid
     * @throws MissingParameterException Thrown if parameters are missing
     * @throws OperationFailedException Thrown if execution fails
	 */
    public AgendaExecutionResultInfo executeAgenda(
    		final List<BusinessRuleAnchorInfo> businessRuleAnchorInfoList,
    		final ParameterMapInfo executionParamMap)
		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	Map<String,String> paramMap = (executionParamMap == null ? null : executionParamMap.getParameterMap());
    	return this.ruleExecution.executeAgenda(businessRuleAnchorInfoList, paramMap);
	}

    /**
     * Executes a business rule by <code>businessRuleId</code> with a 
     * <code>exectionParamMap</code> and returns an execution report.
     * <code>exectionParamMap</code> must match the fact criteria type meta data.
     * <code>exectionParamMap</code> can be null for static facts. </p>
     * 
     * @param businessRuleId A Business rule id
     * @param exectionParamMap Execution fact parameter map
	 * @return Result of executing the business rule
     * @throws DoesNotExistException Thrown if business rule id does not exist
     * @throws InvalidParameterException Thrown if business rule id is invalid
     * @throws MissingParameterException Thrown if business rule id is null or empty
     * @throws OperationFailedException Thrown if execution fails
     */
    public ExecutionResultInfo executeBusinessRule(final String businessRuleId, final ParameterMapInfo executionParamMap)
		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	Map<String,String> paramMap = (executionParamMap == null ? null : executionParamMap.getParameterMap());
    	return this.ruleExecution.executeBusinessRule(businessRuleId, paramMap);
	}

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
    public ExecutionResultInfo executeBusinessRuleTest(final BusinessRuleInfo businessRule, final ParameterMapInfo executionParamMap)
		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	Map<String,String> paramMap = (executionParamMap == null ? null : executionParamMap.getParameterMap());
    	return this.ruleExecution.executeBusinessRuleTest(businessRule, paramMap);
    }
}
