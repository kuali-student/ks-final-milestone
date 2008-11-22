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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.factfinder.service.FactFinderService;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.internal.common.utils.FactUtil;
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
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
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

    private FactFinderService factFinderService;
    
    private boolean ruleSetCachingEnabled = true;

    public void setEnableRuleSetCaching(boolean enableCaching) {
    	this.ruleSetCachingEnabled = enableCaching;
    }
    
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
	 * Gets the rule management service.
	 * 
	 * @param ruleManagementService Rule management service
	 */
	public void setRuleManagementService(RuleManagementService ruleManagementService) {
		this.ruleManagementService = ruleManagementService;
	}

	/**
	 * Sets the fact finder service.
	 * 
	 * @param factFinderService Fact finder service
	 */
	public void setFactFinderService(FactFinderService factFinderService) {
		this.factFinderService = factFinderService;
	}

	/**
	 * Gets a rule set from the repository from rule set UUID.
	 * 
	 * @param businessRule Business rule
	 * @param ruleSetUUID Rule set UUID
	 * @return A rule set
	 * @throws OperationFailedException
	 * @throws InvalidParameterException
	 */
	private RuleSetDTO getRuleSet(BusinessRuleInfoDTO businessRule, String ruleSetUUID) 
		throws OperationFailedException, InvalidParameterException {
		RuleSetDTO ruleSet = null;
		if (businessRule.getStatus().equals(BusinessRuleStatus.ACTIVE.toString())) {
			String SnapshotName = businessRule.getRepositorySnapshotName();
    		ruleSet = this.ruleRespositoryService.fetchRuleSetSnapshot(ruleSetUUID, SnapshotName);
    	} else {
    		ruleSet = this.ruleRespositoryService.fetchRuleSet(ruleSetUUID);
    	}
		return ruleSet;
	}
	
	private void addRuleSet(BusinessRuleInfoDTO businessRule) throws OperationFailedException, InvalidParameterException {
		if (this.ruleSetCachingEnabled) {
    		if (!this.ruleSetExecutor.containsRuleSet(businessRule)) {
				RuleSetDTO ruleSet = getRuleSet(businessRule, businessRule.getCompiledId());
				this.ruleSetExecutor.addRuleSet(businessRule, ruleSet);
    		}
		} else {
			RuleSetDTO ruleSet = getRuleSet(businessRule, businessRule.getCompiledId());
			this.ruleSetExecutor.removeRuleSet(businessRule, ruleSet);
			this.ruleSetExecutor.addRuleSet(businessRule, ruleSet);
		}
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
    
    private Map<String, Object> getFactMap(BusinessRuleInfoDTO businessRule) throws OperationFailedException, DoesNotExistException {
    	if (businessRule.getRuleElementList() == null) {
    		return null;
    	}
    	
    	Map<String, Object> factMap = new HashMap<String, Object>();

    	for(RuleElementDTO ruleElement : businessRule.getRuleElementList()) {
    		RulePropositionDTO ruleProposition = ruleElement.getRuleProposition();
    		if (ruleProposition != null) {
	    		List<FactStructureDTO> list = ruleProposition.getLeftHandSide().getYieldValueFunction().getFactStructureList();
	    		if (list != null) {
		    		for(FactStructureDTO factStructure : list) {
		    			if (factStructure.isStaticFact()) {
		    				continue;
		    			}
		    			String factTypeKey = factStructure.getFactTypeKey();
		    			FactResultDTO factResult = this.factFinderService.fetchFact(factTypeKey, factStructure);
		    	    	String factKey = FactUtil.createFactKey(factStructure);
		    			factMap.put(factKey, factResult);
		    		}
	    		}
    		}
    	}
    	
    	return factMap;
    }

    public AgendaExecutionResultDTO executeAgenda(String agendaId) 
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException 
    {
    	if (agendaId == null) {
    		throw new MissingParameterException("Agenda is null");
    	}

    	// Retrieve runtime agenda from rule management
    	RuntimeAgendaDTO agenda = null;
		Map<String, Object> factMap = new HashMap<String, Object>();
    	
    	try {
    		for(BusinessRuleInfoDTO businessRule : agenda.getBusinessRules()) {
    			addRuleSet(businessRule);
    			Map<String, Object> map = getFactMap(businessRule);
    			factMap.putAll(map);
    		}
    		AgendaExecutionResult executionResult = this.ruleSetExecutor.execute(agenda, factMap);
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
    	BusinessRuleInfoDTO businessRule = this.ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);
		addRuleSet(businessRule);
    	
		Map<String, Object> factMap = getFactMap(businessRule);
		
        try {
        	ExecutionResult result = this.ruleSetExecutor.execute(businessRule, factMap);
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
