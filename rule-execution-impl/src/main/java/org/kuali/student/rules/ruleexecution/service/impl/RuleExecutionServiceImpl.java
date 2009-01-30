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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.jws.WebService;

import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.rules.factfinder.dto.FactParamDTO;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.factfinder.dto.FactTypeInfoDTO;
import org.kuali.student.rules.factfinder.service.FactFinderService;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.rules.internal.common.entity.FactParamDefTimeKey;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.internal.common.statement.report.RuleReport;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.repository.service.RuleRepositoryService;
import org.kuali.student.rules.ruleexecution.dto.AgendaExecutionResultDTO;
import org.kuali.student.rules.ruleexecution.dto.ExecutionResultDTO;
import org.kuali.student.rules.ruleexecution.dto.PropositionReportDTO;
import org.kuali.student.rules.ruleexecution.dto.RuleReportDTO;
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

    private RuleSetExecutor ruleSetExecutorTest;
    
    private RuleRepositoryService ruleRespositoryService;

    private RuleManagementService ruleManagementService;

    private FactFinderService factFinderService;
    
    private boolean ruleSetCachingEnabled = true;
    
    private ConcurrentMap<String, FactResultDTO> factFinderCache = new ConcurrentHashMap<String, FactResultDTO>();

    private ConcurrentMap<String, FactTypeInfoDTO> factFinderTypeCache = new ConcurrentHashMap<String, FactTypeInfoDTO>();
    
    public void setEnableRuleSetCaching(final boolean enableCaching) {
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
	public void setRuleSetExecutor(final RuleSetExecutor ruleSetExecutor) {
		this.ruleSetExecutor = ruleSetExecutor;
	}

	/**
     * Sets the rule execution engine used for testing.
	 * 
	 * @param ruleSetExecutor Rule execution engine
	 */
	public void setRuleSetExecutorTest(final RuleSetExecutor ruleSetExecutor) {
		this.ruleSetExecutorTest = ruleSetExecutor;
	}

	/**
	 * Sets the rule repository service.
	 * 
	 * @param ruleRespositoryService Rule repository service
	 */
	public void setRuleRespositoryService(final RuleRepositoryService ruleRespositoryService) {
		this.ruleRespositoryService = ruleRespositoryService;
	}

	/**
	 * Gets the rule management service.
	 * 
	 * @param ruleManagementService Rule management service
	 */
	public void setRuleManagementService(final RuleManagementService ruleManagementService) {
		this.ruleManagementService = ruleManagementService;
	}

	/**
	 * Sets the fact finder service.
	 * 
	 * @param factFinderService Fact finder service
	 */
	public void setFactFinderService(final FactFinderService factFinderService) {
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
		if (businessRule.getState().equals(BusinessRuleStatus.ACTIVE.toString())) {
			String SnapshotName = businessRule.getRepositorySnapshotName();
    		ruleSet = this.ruleRespositoryService.fetchRuleSetSnapshot(ruleSetUUID, SnapshotName);
    	} else {
    		ruleSet = this.ruleRespositoryService.fetchRuleSet(ruleSetUUID);
    	}
		return ruleSet;
	}
	
	/**
	 * Retrieves an executable rule set from the rule repository service and
	 * adds the rule set to the rule execution runtime.
	 * 
	 * @param businessRule Business rule
	 * @throws OperationFailedException
	 * @throws InvalidParameterException
	 */
	private void addRuleSet(BusinessRuleInfoDTO businessRule) throws OperationFailedException, InvalidParameterException {
		if (this.ruleSetCachingEnabled) {
    		if (!this.ruleSetExecutor.containsRuleSet(businessRule)) {
				RuleSetDTO ruleSet = getRuleSet(businessRule, businessRule.getCompiledId());
				this.ruleSetExecutor.addRuleSet(businessRule, ruleSet);
    		}
		} else {
			RuleSetDTO ruleSet = getRuleSet(businessRule, businessRule.getCompiledId());
			this.ruleSetExecutor.clearRuleSetCache();
			this.ruleSetExecutor.addRuleSet(businessRule, ruleSet);
		}
	}

	/**
	 * Creates the execution result DTO object which contains the rule execution
	 * log, any error messages, the rule execution result and the rule 
	 * proposition results.
	 * 
	 * @param executionResult Rule Execution result
	 * @return Rule execution result DTO
	 */
    private ExecutionResultDTO createExecutionResultDTO(ExecutionResult executionResult) {
    	ExecutionResultDTO exeDTO = new ExecutionResultDTO();
    	exeDTO.setExecutionLog(executionResult.getExecutionLog());
    	exeDTO.setErrorMessage(executionResult.getErrorMessage());
    	exeDTO.setExecutionSuccessful(executionResult.getExecutionResult());
    	
    	RuleReportDTO reportDTO = new RuleReportDTO();
    	RuleReport report = executionResult.getReport();
    	if (report != null) {
    		List<PropositionReportDTO> propositionReportList = new ArrayList<PropositionReportDTO>();
    		for(PropositionReport propositionReport : report.getPropositionReports()) {
	    		PropositionReportDTO prDTO = new PropositionReportDTO();
	    		prDTO.setPropositionName(propositionReport.getPropositionName());
	    		prDTO.setPropositionType(propositionReport.getPropositionType().toString());
	    		prDTO.setSuccessful(propositionReport.isSuccessful());
	    		prDTO.setFailureMessage(propositionReport.getFailureMessage());
	    		prDTO.setSuccessMessage(propositionReport.getSuccessMessage());
	    		prDTO.setCriteriaResult(propositionReport.getCriteriaResult());
	    		prDTO.setFactResult(propositionReport.getFactResult());
    			prDTO.setPropositionResult(propositionReport.getPropositionResult());
	    		propositionReportList.add(prDTO);
	    	}
	    	reportDTO.setSuccessful(report.isSuccessful());
	    	reportDTO.setFailureMessage(report.getFailureMessage());
	    	reportDTO.setSuccessMessage(report.getSuccessMessage());
	    	reportDTO.setPropositionReports(propositionReportList);
    	}
    	exeDTO.setReport(reportDTO);
    	
    	return exeDTO;
    }

    /**
     * Creates the agenda rule execution result.
     * 
     * @param executionResult Agenda execution result
     * @return Agenda execution result DTO
     */
    private AgendaExecutionResultDTO createAgendaExecutionResultDTO(AgendaExecutionResult executionResult) {
    	AgendaExecutionResultDTO executionResultDTO = new AgendaExecutionResultDTO();
    	for(ExecutionResult result : executionResult.getExecutionResultList()) {
    		executionResultDTO.addExecutionResult(createExecutionResultDTO(result));
    	}
    	return executionResultDTO;
    }

    /**
     * Finds fact from the fact finder service.
     * Find fact uses execution level caching to keep the data retrieve from
     * the fact finder consistent in one rule execution.
     * 
     * @param factTypeKey Fact type key
     * @param fs Fact structure
     * @return Fact result
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    private FactResultDTO findFact(String factTypeKey, FactStructureDTO fs) throws OperationFailedException, DoesNotExistException {
    	if (this.factFinderCache.containsKey(factTypeKey)) {
    		return this.factFinderCache.get(factTypeKey);
    	}
    	FactResultDTO factResult = this.factFinderService.fetchFact(factTypeKey, fs);
    	return factResult;
    }
    
    /**
     * Finds fact type meta data from the fact finder service.
     * Find fact uses execution level caching to keep the data retrieve from
     * the fact finder consistent in one rule execution.
     * 
     * @param factTypeKey Fact type key
     * @return  Fact type meta data
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    private FactTypeInfoDTO findFactType(String factTypeKey) throws OperationFailedException, DoesNotExistException {
		if (this.factFinderTypeCache.containsKey(factTypeKey)) {
			this.factFinderTypeCache.get(factTypeKey);
		}
		FactTypeInfoDTO factType = this.factFinderService.fetchFactType(factTypeKey);
		return factType;
    }

    /**
     * Creates a fact map which is used in rule execution.
     * 
     * @param businessRule Function business rule
     * @param exectionParamMap Execution parameter map
     * @return Map of data facts
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    private Map<String, Object> createFactMap(BusinessRuleInfoDTO businessRule, Map<String,String> exectionParamMap) 
    	throws OperationFailedException, DoesNotExistException {
    	// Clear execution level fact finder caching
    	this.factFinderCache.clear();
    	//this.factFinderTypeCache.clear();
    	
    	Map<String, Object> factMap = new HashMap<String, Object>();

    	if (businessRule.getBusinessRuleElementList() == null) {
    		return factMap;
    	}
    	
    	for(RuleElementDTO ruleElement : businessRule.getBusinessRuleElementList()) {
    		RulePropositionDTO ruleProposition = ruleElement.getBusinessRuleProposition();
    		if (ruleProposition != null) {
	    		List<FactStructureDTO> list = ruleProposition.getLeftHandSide().getYieldValueFunction().getFactStructureList();
	    		if (list != null) {
		    		for(FactStructureDTO factStructure : list) {
		    			if (factStructure == null || factStructure.isStaticFact()) {
		    				continue;
		    			}
		    			
		    			String factTypeKey = factStructure.getFactTypeKey();
		    			if (factTypeKey == null || factTypeKey.trim().isEmpty()) {
		    				throw new OperationFailedException("Fact type key is null or empty");
		    			}

		    			FactTypeInfoDTO factType = findFactType(factTypeKey);
		    			Map<String,FactParamDTO> factParamMap = factType.getFactCriteriaTypeInfo().getFactParamMap();

				        Map<String, String> paramMap = new HashMap<String, String>();
				        
		    			for(Entry<String, FactParamDTO> entry : factParamMap.entrySet()) {
		    				String key = entry.getValue().getKey();

			    			if (logger.isInfoEnabled()) {
			    				logger.info("Fact param defTime="+entry.getValue().getDefTime() + ", key="+key);
								logger.info("Fact structure paramValueMap="+factStructure.getParamValueMap());
			    			}

							switch(FactParamDefTimeKey.valueOf(entry.getValue().getDefTime())) {
								case KUALI_FACT_EXECUTION_TIME_KEY: {
			    					if (exectionParamMap == null ) {
			    						throw new OperationFailedException(
			    								"EXECUTION KEY: Execution parameter value map is null." +
			    								"Fact type key = " + factTypeKey);    		    					
			    					}
			    					if (!exectionParamMap.containsKey(key)) {
			    						throw new OperationFailedException(
			    								"EXECUTION KEY: Key '" + key + "' not found in execution parameter value map. " +    		    					
												"Fact type key = " + factTypeKey);    		    					
			    					}

			    					String value = exectionParamMap.get(key);
									paramMap.put(key, value);
									break;
								}
								case KUALI_FACT_DEFINITION_TIME_KEY: {
			    					if (factStructure.getParamValueMap() == null ) {
			    						throw new OperationFailedException("DEFINITION KEY: Fact structure parameter value map is null. " +
			    								"Fact structure id = " + factStructure.getFactStructureId() +
			    								", fact type key = " + factTypeKey);    		    					
			    					}
			    					if (!factStructure.getParamValueMap().containsKey(key)) {
			    						throw new OperationFailedException(
			    								"DEFINITION KEY: Key '" + key + "' not found in fact structure parameter value map. " +    		    					
												"Fact structure id = " + factStructure.getFactStructureId() +
												", fact type key = " + factTypeKey);    		    					
			    					}

									String value = factStructure.getParamValueMap().get(key);
									paramMap.put(key, value);
									break;
								}
								default: throw new OperationFailedException("Invalid definition time constant: " + entry.getValue().getDefTime());
							}
		    			}

		    			if (logger.isInfoEnabled()) {
							logger.info("\n---------- Create Fact Map ----------" +
									"\nfactStructureId=" + factStructure.getFactStructureId() +
									"\nfactTypeKey=" + factTypeKey +
									"\nparamMap="+paramMap +
									"\n-----------------------------------------");
		    			}

				        FactStructureDTO fs = new FactStructureDTO();
		    			fs.setFactTypeKey(factTypeKey);
		    			fs.setFactStructureId(factStructure.getFactStructureId());
		    			fs.setParamValueMap(paramMap);
		    			fs.setResultColumnKeyTranslations(factStructure.getResultColumnKeyTranslations());
						FactResultDTO factResult = findFact(factTypeKey, fs);
		    	    	String factKey = FactUtil.createCriteriaKey(fs);
		    			factMap.put(factKey, factResult);
		    		}
	    		}
    		}
    	}

		if (logger.isInfoEnabled()) {
	    	logger.info("\n---------- factMap ----------\n"+factMap+"\n");
		}

    	return factMap;
    }
    
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
    public AgendaExecutionResultDTO executeAgenda(final String agendaId, final Map<String,String> exectionParamMap) 
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
    			Map<String, Object> map = createFactMap(businessRule, exectionParamMap);
    			factMap.putAll(map);
    		}
    		AgendaExecutionResult executionResult = this.ruleSetExecutor.execute(agenda, factMap);
    		return createAgendaExecutionResultDTO(executionResult);
    	} catch(RuleSetExecutionException e) {
    		logger.error(e.getMessage(), e);
    		throw new OperationFailedException(e.getMessage());
    	}
    }

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
    public ExecutionResultDTO executeBusinessRule(final String businessRuleId, final Map<String,String> exectionParamMap)
		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException 
	{
    	if (businessRuleId == null || businessRuleId.trim().isEmpty()) {
    		throw new MissingParameterException("Business rule id is null or empty");
    	}

    	// Retrieve business rule
    	BusinessRuleInfoDTO businessRule = this.ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);
		addRuleSet(businessRule);
    	
		Map<String, Object> factMap = createFactMap(businessRule, exectionParamMap);
		
        try {
        	ExecutionResult result = this.ruleSetExecutor.execute(businessRule, factMap);
    		return createExecutionResultDTO(result);
    	} catch(RuleSetExecutionException e) {
    		logger.error(e.getMessage(), e);
    		throw new OperationFailedException(e.getMessage());
    	}
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
    public ExecutionResultDTO executeBusinessRuleTest(final BusinessRuleInfoDTO businessRule, final Map<String,String> exectionParamMap)
		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		if (businessRule == null) {
			throw new MissingParameterException("businessRule is null");
		}
		
		RuleSetDTO ruleSet = this.ruleRespositoryService.translateBusinessRule(businessRule);

		Map<String, Object> factMap = createFactMap(businessRule, exectionParamMap);
		
		try {
        	this.ruleSetExecutorTest.addRuleSet(businessRule, ruleSet);
        	ExecutionResult result = this.ruleSetExecutorTest.execute(businessRule, factMap);
    		return createExecutionResultDTO(result);
    	} catch(RuleSetExecutionException e) {
    		logger.error(e.getMessage(), e);
    		throw new OperationFailedException(e.getMessage());
    	}
    	finally {
    		this.ruleSetExecutorTest.removeRuleSet(businessRule, ruleSet);
    	}
    }

}
