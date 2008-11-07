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
import org.drools.StatelessSession;
import org.drools.rule.Package;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.repository.drools.util.DroolsUtil;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.ruleexecution.exceptions.RuleSetExecutionException;
import org.kuali.student.rules.ruleexecution.runtime.DefaultExecutor;
import org.kuali.student.rules.ruleexecution.runtime.ExecutionResult;
import org.kuali.student.rules.ruleexecution.runtime.RuleSetExecutor;
import org.kuali.student.rules.ruleexecution.runtime.drools.logging.DroolsWorkingMemoryLogger;
import org.kuali.student.rules.ruleexecution.runtime.report.ast.GenerateRuleReport;
import org.kuali.student.rules.ruleexecution.util.LoggingStringBuilder;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.util.CurrentDateTime;
import org.kuali.student.rules.util.FactContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleSetExecutorDroolsImpl implements RuleSetExecutor {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(RuleSetExecutorDroolsImpl.class);

    private final static DroolsUtil droolsUtil = DroolsUtil.getInstance();
    
    private DefaultExecutor defaultExecutor = new DefaultExecutorDroolsImpl();
    
    private final static String DEFAULT_RULE_BASE = "defaultRuleBase1"; 

    private final static DroolsRuleBase ruleBaseCache = DroolsRuleBase.getInstance();

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
     * Adds a Drools package to the Drools rule base.
     * 
     * @param ruleSet Rule set
     */
    private void addPackage(RuleSetDTO ruleSet) {
    	Package pkg = droolsUtil.getPackage(ruleSet.getCompiledRuleSet());
        if (!pkg.getName().equals(ruleSet.getName())){
        	throw new RuleSetExecutionException(
        			"Cannot add package to rule base. " +
        			"Drools compiled package name '" + pkg.getName() + 
        			"' does not match rule set name '" + ruleSet.getName() +
        			"'.");
        }
        ruleBaseCache.addPackage(DEFAULT_RULE_BASE, pkg);
		if(logger.isDebugEnabled()) {
    		logger.debug("Added package: Package name=" + pkg.getName() + ", rule set uuid=" + ruleSet.getUUID());
    	}
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

    public synchronized ExecutionResult execute(RuleSetDTO ruleSet, String anchor, 
    		Map<String, RulePropositionDTO> propositionMap,
    		Map<String, Object> factMap) {
        String id = ""+System.nanoTime();
    	FactContainer factContainer =  new FactContainer(id, anchor, propositionMap, factMap);
    	addPackage(ruleSet);
    	
        if (logger.isDebugEnabled()) {
        	log(ruleSet, "Execute Rule");
        }
        
        ExecutionResult result = executeRule(true, factContainer);
        try {
	        PropositionReport report = generateReport(result.getResults());
	        result.setReport(report);
        } catch(RuleSetExecutionException e) {
        	result.setErrorMessage(e.getMessage());
        }
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
    public synchronized ExecutionResult execute(RuleSetDTO ruleSet, List<Object> facts) {
    	addPackage(ruleSet);

    	if (logger.isDebugEnabled()) {
        	log(ruleSet, "Execute Rule");
        }

        ExecutionResult result = executeRule(true, facts);
        return result;
    }

    /**
     * Generates a proposition report.
     * 
     * @param facts Facts for the proposition reports
     * @return A proposition report
     */
    private PropositionReport generateReport(List<?> facts) throws RuleSetExecutionException {
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
     * Assemble the list of facts.
     * 
     * @param factContainer Fact container
     * @return List of facts
     */
    private List<Object> assembleFacts(FactContainer factContainer) {
        List<Object> factList = new ArrayList<Object>();
        factList.add(new CurrentDateTime());
        factList.add(factContainer);
        return factList;
    }

    /**
     * Assemble the list of facts.
     * 
     * @param factList List of facts
     * @return List of facts
     */
    private List<Object> assembleFacts(List<Object> factList) {
        factList.add(new CurrentDateTime());
        return factList;
    }
    
    /**
     * Creates a string builder for logging.
     * 
     * @return A Logging string builder
     */
    private LoggingStringBuilder createLogging() {
    	LoggingStringBuilder builder = new LoggingStringBuilder();
    	builder.append("********************************");
    	builder.append("*   Drools Rule Set Executor   *");
    	builder.append("*        Execution Log         *");
    	builder.append("********************************");
    	builder.append("----- START -----");
    	return builder;
    }
    
    /**
     * Executes a <code>ruleBase</code> with a <code>fact</code>.
     * 
     * @param logRuleExecution If set to true, logs rule engine execution; otherwise no logging is done
     * @param ruleBase List of rules
     * @param fact Fact container for the <code>ruleBase</code>
     * @return An execution result
     */
    private ExecutionResult executeRule(boolean logRuleExecution, FactContainer fact) { 
    	RuleBase ruleBase = ruleBaseCache.getRuleBase(DEFAULT_RULE_BASE);
        StatelessSession session = ruleBase.newStatelessSession();
        DroolsWorkingMemoryLogger droolsLogger = null;
        LoggingStringBuilder executionLog = null;
        
        if(this.logExecution || logRuleExecution) {
            executionLog = createLogging();
        	droolsLogger = new DroolsWorkingMemoryLogger(session, executionLog);
        }
        
        List<Object> factList = assembleFacts(fact);
        ExecutionResult result = new ExecutionResult();
        
        @SuppressWarnings("unchecked") 
        Iterator<Object> it = session.executeWithResults(factList).iterateObjects();
    
        if(this.logExecution || logRuleExecution) {
        	executionLog.append("********************************");
        	executionLog.append("*   Execution Result Objects   *");
        	executionLog.append("********************************");
        }
        
        List<Object> list = new ArrayList<Object>();
        while(it != null && it.hasNext()) {
        	Object obj = it.next();
            if(this.logExecution || logRuleExecution) {
            	executionLog.append(obj.toString());
            }
        	if (obj instanceof FactContainer) {
        		FactContainer fc = (FactContainer) obj;
        		if (fc.getId().equals(fact.getId())){
                	list.add(obj);
        		}
        	}
        }

        result.setResults(list);
        result.setExecutionResult(Boolean.TRUE);
        
        if(this.logExecution || logRuleExecution) {
        	executionLog.append("----- END -----");
        	result.setExecutionLog(droolsLogger.getLog().toString());
        }
        
        return result;
    }

    /**
     * Executes a <code>ruleBase</code> with a list of <code>facts</code>.
     * 
     * @param logRuleExecution If set to true, logs rule engine execution; otherwise no logging is done
     * @param ruleBase List of rules
     * @param fact A list of facts for the <code>ruleBase</code>
     * @return An execution result
     */
    private ExecutionResult executeRule(boolean logRuleExecution, List<Object> facts) { 
    	RuleBase ruleBase = ruleBaseCache.getRuleBase(DEFAULT_RULE_BASE);
        StatelessSession session = ruleBase.newStatelessSession();
        DroolsWorkingMemoryLogger droolsLogger = null;
        LoggingStringBuilder executionLog = null;
        
        if(this.logExecution || logRuleExecution) {
            executionLog = createLogging();
        	droolsLogger = new DroolsWorkingMemoryLogger(session, executionLog);
        }

        List<Object> factList = assembleFacts(facts);
        ExecutionResult result = new ExecutionResult();

        @SuppressWarnings("unchecked") 
        Iterator<Object> it = session.executeWithResults(factList).iterateObjects();
        
        if(this.logExecution || logRuleExecution) {
        	executionLog.append("********************************");
        	executionLog.append("*   Execution Result Objects   *");
        	executionLog.append("********************************");
        }
        
        List<Object> list = new ArrayList<Object>();
        while(it != null && it.hasNext()) {
        	Object obj = it.next();
            if(this.logExecution || logRuleExecution) {
            	executionLog.append("Object: "+obj.toString());
            }
        	if (!(obj instanceof CurrentDateTime)) {
	        	list.add(obj);
        	}
        }

        result.setResults(list);
        result.setExecutionResult(Boolean.TRUE);
        
        if(this.logExecution || logRuleExecution) {
        	executionLog.append("----- END -----");
        	result.setExecutionLog(droolsLogger.getLog().toString());
        }
        
        return result;
    }
}
