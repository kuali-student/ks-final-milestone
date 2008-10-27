package org.kuali.student.rules.ruleexecution.service.impl;

import java.io.IOException;
import java.util.List;

import javax.jws.WebService;

import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.repository.util.ObjectUtil;
import org.kuali.student.rules.ruleexecution.dto.FactDTO;
import org.kuali.student.rules.ruleexecution.dto.ResultDTO;
import org.kuali.student.rules.ruleexecution.exceptions.RuleSetExecutionException;
import org.kuali.student.rules.ruleexecution.runtime.RuleSetExecutor;
import org.kuali.student.rules.ruleexecution.service.RuleExecutionService;
import org.kuali.student.rules.rulemanagement.dto.RuntimeAgendaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.rules.ruleexecution.service.RuleExecutionService", 
		serviceName = "RuleExecutionService", 
		portName = "RuleExecutionService", 
		targetNamespace = "http://student.kuali.org/wsdl/brms/RuleExecution")
@Transactional
public class RuleExecutionServiceImpl implements RuleExecutionService {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(RuleExecutionServiceImpl.class);
    
    private RuleSetExecutor ruleSetExecutor;

    /**
     * Gets the rule execution engine.
     * 
     * @return Rule execution engine
     */
	public RuleSetExecutor getRuleSetExecutor() {
		return ruleSetExecutor;
	}

	/**
     * Sets the rule execution engine.
	 * 
	 * @param ruleSetExecutor Rule execution engine
	 */
	public void setRuleSetExecutor(RuleSetExecutor ruleSetExecutor) {
		this.ruleSetExecutor = ruleSetExecutor;
	}

    /**
     * Executes an <code>agenda</code> with <code>fact</code>.
     * 
     * @param agenda Agenda to execute
     * @param fact List of Facts for the <code>agenda</code>
     * @return Result of executing the <code>agenda</code>
     * @throws DoesNotExistException If rule set to be executed cannot be found
     * @throws InvalidParameterException If method parameters arei nvalid
     * @throws MissingParameterException If method parameters are missing
     * @throws OperationFailedException If the rule engine execution fails
     */
    public Object executeAgenda(RuntimeAgendaDTO agenda, Object fact) 
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException 
    {
    	List<?> factList = (List<?>) fact;
    	
    	if (agenda == null) {
    		throw new MissingParameterException("Agenda is null");
    	} else if (fact == null) {
    		throw new MissingParameterException("Fact are null");
    	} else if (factList.isEmpty()) {
    		throw new InvalidParameterException("Fact list contains no elements");
    	}

    	try {
    		return this.ruleSetExecutor.execute(agenda, factList);
    	} catch(RuleSetExecutionException e) {
    		throw new OperationFailedException(e.getMessage());
    	}
    }

    /**
     * Executes an <code>agenda</code> with <code>fact</code> and a 
     * <code>ruleSet</code>.
     * 
     * @param ruleSet Rule set to execute
     * @param fact List of Facts for the <code>agenda</code>
     * @return Result of executing the <code>agenda</code>
     */
    public ResultDTO executeRuleSet(RuleSetDTO ruleSet, FactDTO fact)
		throws InvalidParameterException, MissingParameterException, OperationFailedException 
	{
    	if (ruleSet == null) {
    		throw new MissingParameterException("RuleSet is null");
    	} else if (fact == null) {
    		throw new MissingParameterException("Fact is null");
    	} else if (fact.getFacts() == null || fact.getFacts().isEmpty()) {
    		throw new MissingParameterException("Fact list is null");
    	}
    	
    	try {
    		return this.ruleSetExecutor.execute(ruleSet, fact);
    	} catch(RuleSetExecutionException e) {
    		throw new OperationFailedException("RuleSetExecutionException:" + e.getMessage()+"\n"+e.getCause());
    	}
    }
}
