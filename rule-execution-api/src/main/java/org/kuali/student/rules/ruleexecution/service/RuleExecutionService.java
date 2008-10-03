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
package org.kuali.student.rules.ruleexecution.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.ruleexecution.dto.FactDTO;
import org.kuali.student.rules.ruleexecution.dto.ResultDTO;
import org.kuali.student.rules.rulemanagement.dto.RuntimeAgendaDTO;

/**
 * This is the rule execution runtime service interface.
 * 
 * @author lcarlsen
 *
 */
@WebService(name = "RuleExecutionService",
		targetNamespace = "http://student.kuali.org/wsdl/brms/RuleExecution")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, 
		 use = SOAPBinding.Use.LITERAL, 
		 parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface RuleExecutionService {

    /**
     * Executes an <code>agenda</code> with <code>fact</code>.
     * 
     * @param agenda Agenda to execute
     * @param fact List of Facts for the <code>agenda</code>
     * @return Result of executing the <code>agenda</code>
     */
    @WebMethod
    public Object executeAgenda(@WebParam(name="agenda")RuntimeAgendaDTO agenda, @WebParam(name="fact")Object fact)
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Executes a production snapshot <code>agenda</code> with <code>fact</code>.
     * 
     * @param agenda Agenda to execute
     * @param fact List of Facts for the <code>agenda</code>
     * @return Result of executing the <code>agenda</code>
     */
    //@WebMethod
    //public Object executeAgendaSnapshot(@WebParam(name="agenda")RuntimeAgendaDTO agenda, @WebParam(name="fact")List<?> fact);

    /**
     * Executes an <code>agenda</code> with <code>fact</code> and a 
     * <code>ruleSet</code>.
     * 
     * @param ruleSet Rule set to execute
     * @param fact Facts for the <code>agenda</code>
     * @return Result of executing the <code>agenda</code>
     */
    @WebMethod
    public ResultDTO executeRuleSet(@WebParam(name="ruleSet")RuleSetDTO ruleSet, @WebParam(name="fact")FactDTO fact)
		throws InvalidParameterException, MissingParameterException, OperationFailedException;
}
