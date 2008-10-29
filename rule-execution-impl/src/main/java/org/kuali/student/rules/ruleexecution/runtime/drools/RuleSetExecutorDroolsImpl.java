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
package org.kuali.student.rules.ruleexecution.runtime.drools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatelessSession;
import org.drools.rule.Package;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil;
import org.kuali.student.rules.repository.RuleEngineRepository;
import org.kuali.student.rules.repository.drools.util.DroolsUtil;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.ruleexecution.exceptions.RuleSetExecutionException;
import org.kuali.student.rules.ruleexecution.runtime.DefaultExecutor;
import org.kuali.student.rules.ruleexecution.runtime.ExecutionResult;
import org.kuali.student.rules.ruleexecution.runtime.RuleSetExecutor;
import org.kuali.student.rules.ruleexecution.runtime.drools.logging.DroolsWorkingMemoryLogger;
import org.kuali.student.rules.ruleexecution.runtime.report.ast.GenerateRuleReport;
import org.kuali.student.rules.util.FactContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleSetExecutorDroolsImpl implements RuleSetExecutor {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(RuleSetExecutorDroolsImpl.class);

    private final static DroolsUtil droolsUtil = DroolsUtil.getInstance();
    
    private RuleEngineRepository ruleEngineRepository;
    
    private DefaultExecutor defaultExecutor = new DefaultExecutorDroolsImpl();
    
    private boolean logExecution = false;
    
    /**
     * Constructs a new rule set executor without a repository.
     */
    public RuleSetExecutorDroolsImpl() {
    	this.logExecution = false;
    }
    
    public void addExecutionLogging() {
    	this.logExecution = true;
    }

    public void removeExecutionLogging() {
    	this.logExecution = false;
    }

    /**
     * Constructs a new rule set executor with a specific repository.
     * 
     * @param ruleEngineRepository Repository to load rule sets from
     */
    public RuleSetExecutorDroolsImpl( RuleEngineRepository ruleEngineRepository ) {
        this.ruleEngineRepository = ruleEngineRepository;
    }

    private void log(RuleSetDTO ruleSet, String message) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("\n**************************************************");
    	sb.append("\n"+message);
    	sb.append("\nuuid="+ruleSet.getUUID());
    	sb.append("\nname="+ruleSet.getName());
    	sb.append("\n-------------------------");
    	sb.append("\n"+ruleSet.getContent());
    	sb.append("\n**************************************************");
        logger.debug(sb.toString());
    }
    
    /**
     * Executes an <code>agenda</code> with <code>facts</code>.
     * 
     * @see org.kuali.student.rules.ruleexecution.runtime.RuleSetExecutor#execute(org.kuali.student.rules.internal.common.agenda.entity.Agenda, java.util.List)
     */
    /*public synchronized ExecutionResult execute(RuntimeAgendaDTO agenda, Map<String, Object> factMap) {
        FactContainer factContainer =  new FactContainer(anchor, factMap);
    	List<Package> packageList = new ArrayList<Package>();
        logger.info("Executing agenda: businessRules="+agenda.getBusinessRules());
        //for(BusinessRuleSet businessRuleSet : agenda.getBusinessRules()) {
        for(BusinessRuleInfoDTO businessRule : agenda.getBusinessRules()) {
            logger.info("Loading compiled rule set: businessRuleSet.id="+businessRule.getCompiledId());
            Package pkg = loadCompiledRuleSet(businessRule.getCompiledId());
            packageList.add(pkg);
            if (logger.isDebugEnabled()) {
            	RuleSet rs = this.ruleEngineRepository.loadRuleSet(businessRule.getCompiledId());
            	log(rs, "Execute Rule");
            }
        }
        RuleBase ruleBase = getRuleBase(packageList);
        //List<Object> iterator = executeRule(ruleBase, facts);
        ExecutionResult result = executeRule(agenda.getExecutionLogging(), ruleBase, facts);
        //generateReport(facts);
        return result;
    }*/

    public ExecutionResult execute(RuleSetDTO ruleSet, String anchor, Map<String, Object> factMap) {
        FactContainer factContainer =  new FactContainer(anchor, factMap);
    	List<Package> packageList = new ArrayList<Package>();
        Package pkg = droolsUtil.getPackage(ruleSet.getCompiledRuleSet());
        if (pkg == null) {
        	throw new RuleSetExecutionException("Drools Package is null.");
        }
        packageList.add(pkg);
        if (logger.isDebugEnabled()) {
        	log(ruleSet, "Execute Rule");
        }
        RuleBase ruleBase = getRuleBase(packageList);
        
        ExecutionResult result = executeRule(true, ruleBase, factContainer);
        PropositionReport report = generateReport(result.getResults());
        result.setReport(report);
        //return convertResult(factContainer.getId(), result);
        return result;
    }

    /**
     * Executes an <code>agenda</code> with <code>facts</code> and a 
     * <code>ruleSet</code>.
     * 
     * @param ruleSet Rule set to execute
     * @param facts List of Facts for the <code>agenda</code>
     * @return Result of executing the <code>agenda</code>
     */
    public ExecutionResult execute(RuleSetDTO ruleSet, List<?> facts) {
    	List<Package> packageList = new ArrayList<Package>();
        Package pkg = droolsUtil.getPackage(ruleSet.getCompiledRuleSet());
        if (pkg == null) {
        	throw new RuleSetExecutionException("Drools Package is null.");
        }
        packageList.add(pkg);
        if (logger.isDebugEnabled()) {
        	log(ruleSet, "Execute Rule");
        }
        RuleBase ruleBase = getRuleBase(packageList);
        //List<?> factList = convertFacts(facts);
        ExecutionResult result = executeRule(true, ruleBase, facts);
        //return convertResult(result);
        return result;
    }

    /**
     * Converts <code>fact</code> into a list of real Java data types.
     * 
     * @param <T> Data type
     * @param fact Fact to convert
     * @return A list of real data type
     */
    private List<?> convertFacts(List<?> facts) {
    	List<Object> list = new ArrayList<Object>(facts.size());
		for(Object fact : facts) {
			Object obj = BusinessRuleUtil.convertToDataType(fact);
			list.add(obj);
		}
		return list;
	}

    /*private List<?> convertFacts(FactDTO fact) {
    	List<Object> list = new ArrayList<Object>(fact.getFacts().size());
		for(ValueDTO value : fact.getFacts()) {
			Object val = BusinessRuleUtil.convertToDataType(value.getValue());
			list.add(val);
    	}
    	return list;
    }*/
    /*private FactResultDTO convertToProperDataType(FactResultDTO fact) {
    	FactResultDTO newFact = new FactResultDTO();
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		for(Map<String, Object> row : fact.getResultList()) {
	        Map<String, Object> rowMap = new HashMap<String, Object>();
	    	for(Entry<String, Object> entry : row.entrySet()) {
				Object val = BusinessRuleUtil.convertToDataType(entry.getValue());
				rowMap.put(entry.getKey(), val);
	    	}
	    	resultList.add(rowMap);
		}
		newFact.setResultList(resultList);
    	return newFact;
    }*/
    
    /**
     * Converts java objects into DTOs and returns the result.
     * 
     * @param id Result id
     * @param list List of result objects
     * @return A result DTO
     */
    private ExecutionResult convertResult(ExecutionResult executionResult) {
    	ExecutionResult result = new ExecutionResult();
    	List<Object> resultList = new ArrayList<Object>();
    	result.setExecutionLog(executionResult.getExecutionLog());
    	for(Object obj : executionResult.getResults()) {
			Object value = BusinessRuleUtil.convertToDataType(obj);
			resultList.add(value);
    	}
    	return result;
    }
    /*private ResultDTO convertResult(String id, ExecutionResult executionResult) {
    	ResultDTO result = new ResultDTO(id);
    	result.setExecutionLog(executionResult.getLog());
    	for(Object obj : executionResult.getResults()) {
			//Object value = BusinessRuleUtil.convertToDataType(obj);
    		result.addResult(null, obj);
    	}
    	return result;
    }*/

    /**
     * Loads a compiled rule set by <code>uuid</code>
     * 
     * @param uuid Rule set UUID 
     * @return A Drools package
     */
    private Package loadCompiledRuleSet(String uuid) {
        if (this.ruleEngineRepository == null) {
        	throw new RuleSetExecutionException("Rule engine repository has not been set.");
        }
        return (Package) this.ruleEngineRepository.loadRuleSet(uuid).getCompiledRuleSetObject();
    }

    private PropositionReport generateReport(List<?> facts) {
        GenerateRuleReport ruleReportBuilder = new GenerateRuleReport(defaultExecutor);
        for(int i=0; i<facts.size(); i++) {
            Object obj = facts.get(i);
            if (obj instanceof FactContainer) {
                FactContainer fc = (FactContainer) obj;
                return ruleReportBuilder.execute(fc.getPropositionContainer());
           }
        }
        return null;
    }

    /**
     * Gets the Drools rule base.
     * 
     * @param packages Packages to add to the rule base
     * @return A rule base
     * @throws RuleSetExecutionException If adding a package to the Drools rule base fails
     */
    private RuleBase getRuleBase( List<Package> packages ) {
        Thread currentThread = Thread.currentThread();
        ClassLoader oldClassLoader = currentThread.getContextClassLoader();
        ClassLoader newClassLoader = RuleSetExecutorDroolsImpl.class.getClassLoader();

        try
        {
            currentThread.setContextClassLoader( newClassLoader );
        
            //Add package to rulebase (deploy the rule package).
            RuleBase ruleBase = RuleBaseFactory.newRuleBase();
            try {
                for( Package pkg : packages ) {
                    if (pkg == null) {
                    	throw new RuleSetExecutionException("Cannot add a null Drools Package to the Drools RuleBase.");
                    }
                    ruleBase.addPackage(pkg);
                }
            } catch( Exception e ) {
                throw new RuleSetExecutionException( "Adding package to rule base failed", e );
            }
            return ruleBase;
        }
        finally
        {
            currentThread.setContextClassLoader( oldClassLoader );
        }
    }

    /**
     * Executes a <code>ruleBase</code> with a <code>fact</code>.
     * 
     * @param ruleBase List of rules
     * @param fact Facts for the <code>ruleBase</code>
     * @return A execution result
     */
    private ExecutionResult executeRule(boolean logRuleExecution, RuleBase ruleBase, Object fact) { 
        StatelessSession session = ruleBase.newStatelessSession();
        DroolsWorkingMemoryLogger logger = null;
        
        if(this.logExecution || logRuleExecution) {
        	logger = new DroolsWorkingMemoryLogger(session);
        }
        @SuppressWarnings("unchecked") 
        Iterator<Object> it = session.executeWithResults(fact).iterateObjects();
        
        List<Object> list = new ArrayList<Object>();
        while(it != null && it.hasNext()) {
        	Object obj = it.next();
        	list.add(obj);
        }
        
        ExecutionResult result = new ExecutionResult();
        result.setResults(list);
        if(this.logExecution || logRuleExecution) {
        	result.setExecutionLog(logger.getLog().toString());
        }
        
        return result;
    }
    
    private ExecutionResult executeRule(boolean logRuleExecution, RuleBase ruleBase, List<?> facts) { 
        StatelessSession session = ruleBase.newStatelessSession();
        DroolsWorkingMemoryLogger logger = null;
        
        if(this.logExecution || logRuleExecution) {
        	logger = new DroolsWorkingMemoryLogger(session);
        }

        @SuppressWarnings("unchecked") 
        Iterator<Object> it = session.executeWithResults(facts).iterateObjects();
        
        List<Object> list = new ArrayList<Object>();
        while(it != null && it.hasNext()) {
        	Object obj = it.next();
        	list.add(obj);
        }
        
        ExecutionResult result = new ExecutionResult();
        result.setResults(list);
        if(this.logExecution || logRuleExecution) {
        	result.setExecutionLog(logger.getLog().toString());
        }
        
        return result;
    }
    
}
