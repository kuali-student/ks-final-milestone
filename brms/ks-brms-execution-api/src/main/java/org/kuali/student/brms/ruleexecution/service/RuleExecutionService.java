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

package org.kuali.student.brms.ruleexecution.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.brms.ruleexecution.dto.AgendaExecutionResultInfo;
import org.kuali.student.brms.ruleexecution.dto.ExecutionResultInfo;
import org.kuali.student.brms.ruleexecution.dto.ParameterMapInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleAnchorInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;

/**
 * <b>IMPORTANT:</b> This service contract is currently under development. If you are planning to implement the Kuali Student System or parts thereof, <b>please do not consider this service to be final!</b> Consult this page for status before making any plans that rely on specific implementations of these services.</p>
 * 
 * <h3><a name="KSDOC-ServiceDescriptions-Description"></a>Description</h3>
 * 
 * <p>The Execution Service provides applications with the ability to execute rules remotely or locally through a common toolset. Providing an executable rule from the Repository Service and the necessary contextual and definition facts for execution, the service will run the rule and return a result. In the case of validation rules, the result will consist of a tree of results for each of the propositions contained in the agenda (see the Business Rules Management Service (BRMS) for more information regarding propositions and agendas).</p>
 * 
 * @author Kuali Student Team
 *
 */
@WebService(name = "RuleExecutionService",
		targetNamespace = "http://student.kuali.org/wsdl/brms/RuleExecution")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, 
		 use = SOAPBinding.Use.LITERAL, 
		 parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface RuleExecutionService {

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
    @WebMethod
    public AgendaExecutionResultInfo executeAgenda(
			@WebParam(name="businessRuleAnchorInfoList") List<BusinessRuleAnchorInfo> businessRuleAnchorInfoList,
			@WebParam(name="exectionParamMap") ParameterMapInfo exectionParamMap)
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
    @WebMethod
    public ExecutionResultInfo executeBusinessRule(
    		@WebParam(name="businessRuleId")String businessRuleId, 
			@WebParam(name="exectionParamMap") ParameterMapInfo exectionParamMap)
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
//    @WebMethod
//    public Boolean executeBusinessRuleWithNoReport(
//    		@WebParam(name="businessRuleId")String businessRuleId, 
//    		@WebParam(name="exectionParamMap") @XmlJavaTypeAdapter(value=JaxbAttributeMapListAdapter.class, type=Map.class) Map<String,String> exectionParamMap)
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
    @WebMethod
    public ExecutionResultInfo executeBusinessRuleTest(
    		@WebParam(name="businessRule")BusinessRuleInfo businessRule, 
			@WebParam(name="exectionParamMap") ParameterMapInfo exectionParamMap)
		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
}
