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
package org.kuali.student.rules.ruleexecution.service.impl;

import javax.jws.WebService;

import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.repository.service.RuleRepositoryService;
import org.kuali.student.rules.ruleexecution.dto.AgendaExecutionResultDTO;
import org.kuali.student.rules.ruleexecution.dto.ExecutionResultDTO;
import org.kuali.student.rules.ruleexecution.dto.PropositionReportDTO;
import org.kuali.student.rules.ruleexecution.exceptions.RuleSetExecutionException;
import org.kuali.student.rules.ruleexecution.runtime.AgendaExecutionResult;
import org.kuali.student.rules.ruleexecution.runtime.ExecutionResult;
import org.kuali.student.rules.ruleexecution.runtime.RuleSetExecutor;
import org.kuali.student.rules.ruleexecution.service.RuleExecutionService;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RuntimeAgendaDTO;
import org.kuali.student.rules.rulemanagement.service.RuleManagementService;
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
    
    private RuleRepositoryService ruleRespositoryService;

    private RuleManagementService ruleManagementService;

    private  static final String RULE_SNAPSHOT_SUFFIX = "_SNAPSHOT";

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
	 * Sets the rule repository service.
	 * 
	 * @param ruleRespositoryService Rule repository service
	 */
	public void setRuleRespositoryService(RuleRepositoryService ruleRespositoryService) {
		this.ruleRespositoryService = ruleRespositoryService;
	}

	/**
	 * Gets the rule repository service.
	 * 
	 * @return Rule repository service
	 */
	public RuleRepositoryService getRuleRespositoryService() {
		return this.ruleRespositoryService;
	}
	
	/**
	 * Sets the rule management service.
	 * 
	 * @return Rule management service
	 */
	public RuleManagementService getRuleManagementService() {
		return ruleManagementService;
	}

	/**
	 * Gets the rule management service.
	 * 
	 * @param ruleManagementService Rule management service
	 */
	public void setRuleManagementService(RuleManagementService ruleManagementService) {
		this.ruleManagementService = ruleManagementService;
	}

	private RuleSetDTO getRuleSet(BusinessRuleInfoDTO brInfo, String ruleSetUUID) 
		throws OperationFailedException, InvalidParameterException {
		RuleSetDTO ruleSet = null;
		if (brInfo.getStatus().equals(BusinessRuleStatus.ACTIVE.toString())) {
			String SnapshotName = brInfo.getRepositorySnapshotName();
    		ruleSet = this.ruleRespositoryService.fetchRuleSetSnapshot(ruleSetUUID, SnapshotName);
    	} else {
    		ruleSet = this.ruleRespositoryService.fetchRuleSet(ruleSetUUID);
    	}
		return ruleSet;
	}
	
    private ExecutionResultDTO createExecutionResultDTO(ExecutionResult executionResult) {
    	ExecutionResultDTO dto = new ExecutionResultDTO();
    	dto.setExecutionLog(executionResult.getExecutionLog());
    	dto.setErrorMessage(executionResult.getErrorMessage());
    	dto.setExecutionResult(executionResult.getExecutionResult());
    	
    	PropositionReportDTO reportDTO = new PropositionReportDTO();
    	PropositionReport report = executionResult.getReport();
    	if (report != null) {
	    	reportDTO.setSuccessful(report.isSuccessful());
	    	reportDTO.setFailureMessage(report.getFailureMessage());
	    	reportDTO.setSuccessMessage(report.getSuccessMessage());
    	}
    	dto.setReport(reportDTO);
    	
    	return dto;
    }

    private AgendaExecutionResultDTO createAgendaExecutionResultDTO(AgendaExecutionResult executionResult) {
    	AgendaExecutionResultDTO executionResultDTO = new AgendaExecutionResultDTO();
    	for(ExecutionResult result : executionResult.getExecutionResultList()) {
    		executionResultDTO.addExecutionResult(createExecutionResultDTO(result));
    	}
    	return executionResultDTO;
    }

    public AgendaExecutionResultDTO executeAgenda(String agendaId) 
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException 
    {
    	if (agendaId == null) {
    		throw new MissingParameterException("Agenda is null");
    	}

    	// Retrieve runtime agenda from rule management
    	RuntimeAgendaDTO agenda = null;
    	
    	try {
    		for(BusinessRuleInfoDTO businessRule : agenda.getBusinessRules()) {
    	    	if (!this.ruleSetExecutor.containsRuleSet(businessRule)) {
	    			RuleSetDTO ruleSet = getRuleSet(businessRule, businessRule.getCompiledId());
	    			this.ruleSetExecutor.addRuleSet(businessRule, ruleSet);
    	    	}
    		}
    		AgendaExecutionResult executionResult = this.ruleSetExecutor.execute(agenda, null);
    		return createAgendaExecutionResultDTO(executionResult);
    	} catch(RuleSetExecutionException e) {
    		throw new OperationFailedException(e.getMessage());
    	}
    }

    public ExecutionResultDTO executeBusinessRule(String businessRuleId)
		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException 
	{
    	if (businessRuleId == null || businessRuleId.trim().isEmpty()) {
    		throw new MissingParameterException("Business rule id is null or empty");
    	}

    	// Retrieve business rule
    	BusinessRuleInfoDTO brInfo = this.ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);
    	String ruleSetUUID = brInfo.getCompiledId();
    	
		//String ruleBaseType = brInfo.getBusinessRuleTypeKey();
    	if (!this.ruleSetExecutor.containsRuleSet(brInfo)) {
	    	RuleSetDTO ruleSet = getRuleSet(brInfo, ruleSetUUID);
        	this.ruleSetExecutor.addRuleSet(brInfo, ruleSet);
    	}
    	
        try {
        	ExecutionResult result = this.ruleSetExecutor.execute(brInfo, null);
    		return createExecutionResultDTO(result);
    	} catch(RuleSetExecutionException e) {
    		logger.error(e.getMessage(), e);
    		throw new OperationFailedException(e.getMessage());
    	}
    }

    /*private List<Object> getFactList(List<FactResultDTO> facts) {
		List<Object> factList = new ArrayList<Object>();
		for(FactResultDTO fact : facts) {
	    	Map<String, FactResultColumnInfoDTO> columnMetaData = fact.getFactResultTypeInfo().getResultColumnsMap();
			
			for( Map<String,String> map : fact.getResultList()) {
				for(Entry<String, String> entry : map.entrySet()) {
					// Get only the first column (column 1)
					String value = (String) entry.getValue();
					FactResultColumnInfoDTO info = columnMetaData.get(entry.getKey());
					String dataType = info.getDataType();
					Object obj = BusinessRuleUtil.convertToDataType(dataType, value);
					factList.add(obj);
				}
			}
		}
		return factList;
    }
    
    public ExecutionResultDTO executeRuleSet(RuleSetDTO ruleSet, List<FactResultDTO> facts)
		throws InvalidParameterException, MissingParameterException, OperationFailedException 
	{
		if (ruleSet == null) {
			throw new MissingParameterException("RuleSet is null");
		} else if (facts == null) {
			throw new MissingParameterException("Fact is null");
		}
	
		List<Object> factList = getFactList(facts);
	
	    try {
	    	String ruleBaseType = SimpleExecutor.DEFAULT_RULE_CACHE_KEY;
	    	this.defaultExecutor.addRuleSet(ruleBaseType, ruleSet);
			ExecutionResult result =  this.defaultExecutor.execute(ruleBaseType, factList);
			return createExecutionResultDTO(result);
		} catch(RuleSetExecutionException e) {
			logger.error(e.getMessage(), e);
			throw new OperationFailedException("RuleSetExecutionException:" + e.getMessage()+"\n"+e.getCause());
		}
	}

    public ExecutionResultDTO executeRuleSetByUUID(String ruleSetUUID, List<FactResultDTO> facts)
		throws InvalidParameterException, MissingParameterException, OperationFailedException 
	{
		if (ruleSetUUID == null) {
			throw new MissingParameterException("RuleSet UUID is null");
		} else if (facts == null) {
			throw new MissingParameterException("Fact is null");
		}
		
		List<Object> factList = getFactList(facts);
		
	    try {
	    	RuleSetDTO ruleSet = this.ruleRespositoryService.fetchRuleSet(ruleSetUUID);
	    	String ruleBaseType = SimpleExecutor.DEFAULT_RULE_CACHE_KEY;
	    	this.defaultExecutor.addRuleSet(ruleBaseType, ruleSet);
			ExecutionResult result =  this.defaultExecutor.execute(ruleBaseType, factList);
			return createExecutionResultDTO(result);
		} catch(RuleSetExecutionException e) {
			logger.error(e.getMessage(), e);
			throw new OperationFailedException("RuleSetExecutionException:" + e.getMessage()+"\n"+e.getCause());
		}
	}*/
}
