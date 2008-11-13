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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.drools.RuleBase;
import org.drools.StatelessSession;
import org.drools.rule.Package;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil;
import org.kuali.student.rules.repository.drools.util.DroolsUtil;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.ruleexecution.exceptions.RuleSetExecutionException;
import org.kuali.student.rules.ruleexecution.runtime.DefaultExecutor;
import org.kuali.student.rules.ruleexecution.runtime.ExecutionResult;
import org.kuali.student.rules.ruleexecution.runtime.RuleSetExecutor;
import org.kuali.student.rules.ruleexecution.runtime.drools.logging.DroolsWorkingMemoryLogger;
import org.kuali.student.rules.ruleexecution.runtime.report.ast.GenerateRuleReport;
import org.kuali.student.rules.ruleexecution.util.LoggingStringBuilder;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.RuntimeAgendaDTO;
import org.kuali.student.rules.util.CurrentDateTime;
import org.kuali.student.rules.util.FactContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleSetExecutorDroolsImpl implements RuleSetExecutor {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(RuleSetExecutorDroolsImpl.class);

	/** Class serial version uid */
    private static final long serialVersionUID = 1L;

    private final static DroolsUtil droolsUtil = DroolsUtil.getInstance();
    
    private DefaultExecutor defaultExecutor = new DefaultExecutorDroolsImpl();
    
    private final static DroolsRuleBase ruleBaseCache = DroolsRuleBase.getInstance();

    // Each rule base must have unique anchors
    private final static ConcurrentMap<String,String> anchorMap = new ConcurrentHashMap<String,String>();

    private final static ConcurrentMap<String,BusinessRuleInfoValue> businessRuleMap = new ConcurrentHashMap<String,BusinessRuleInfoValue>();
    
    private boolean logExecution = false;
    
    /**
     * Constructs a new rule set executor without a repository.
     */
    public RuleSetExecutorDroolsImpl() {
    	this.logExecution = false;
    }

    /**
     * Enables rule engine execution logging.
     */
    public void enableExecutionLogging() {
    	this.logExecution = true;
    }

    /**
     * disables rule engine execution logging.
     */
    public void disableExecutionLogging() {
    	this.logExecution = false;
    }

    /**
     * Logs a business rule.
     * 
     * @param businessRule business rule to log
     * @param message A message
     */
    private void logBusinessRule(BusinessRuleInfoDTO businessRule, String message) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("\n**************************************************");
    	sb.append("\n"+message);
    	sb.append("\nBusiness rule name:                "+businessRule.getName());
    	sb.append("\nBusiness rule id:                  "+businessRule.getBusinessRuleId());
    	sb.append("\nBusiness rule compiledId:          "+businessRule.getCompiledId());
    	sb.append("\nBusiness rule compiled version no: "+businessRule.getCompiledVersionNumber());
    	sb.append("\nBusiness rule anchor type key:     "+businessRule.getAnchorTypeKey());
    	sb.append("\nBusiness rule anchor value:        "+businessRule.getAnchorValue());
    	sb.append("\n**************************************************");
        logger.debug(sb.toString());
    }

    /**
     * Logs a rule set.
     * 
     * @param ruleSet Rule set to log
     * @param message A message
     */
    private void logRuleSet(RuleSetDTO ruleSet, String message) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("\n**************************************************");
    	sb.append("\n"+message);
    	sb.append("\nRule set name:                  "+ruleSet.getName());
    	sb.append("\nRule set uuid:                  "+ruleSet.getUUID());
    	sb.append("\nRule set version number:        "+ruleSet.getVersionNumber());
    	sb.append("\nRule set snapshot name:         "+ruleSet.getSnapshotName());
    	sb.append("\nRule set version snapshot uuid: "+ruleSet.getVersionSnapshotUUID());
    	sb.append("\nRule set status:                "+ruleSet.getStatus());
    	sb.append("\n-------------------------");
    	sb.append(ruleSet.getContent());
    	sb.append("\n**************************************************");
        logger.debug(sb.toString());
    }

    /**
     * Gets the business rule anchor key.
     * 
     * @param businessRule Business rule
     * @return Anchor key
     */
    private String getAnchorKey(BusinessRuleInfoDTO businessRule) {
    	return businessRule.getBusinessRuleTypeKey() + businessRule.getAnchorValue();
    }

    /**
     * Gets the business rule info key value.
     * 
     * @param businessRule Business rule
     * @return Business rule info key value
     */
    private BusinessRuleInfoValue getBusinessRuleInfoValue(BusinessRuleInfoDTO businessRule) {
    	return new BusinessRuleInfoValue(
			businessRule.getBusinessRuleId(), 
			businessRule.getCompiledId(), 
			businessRule.getCompiledVersionNumber(),
			businessRule.getAnchorValue(),
			businessRule.getAnchorTypeKey());
    }

    /**
     * Adds a Drools package to the Drools rule base.
     * 
     * @param ruleSet Rule set
     */
    private void addPackage(String ruleBaseType, RuleSetDTO ruleSet) {
    	Package pkg = droolsUtil.getPackage(ruleSet.getCompiledRuleSet());
        if (!pkg.getName().equals(ruleSet.getName())){
        	throw new RuleSetExecutionException(
        			"Cannot add package to rule base. " +
        			"Drools compiled package name '" + pkg.getName() + 
        			"' does not match rule set name '" + ruleSet.getName() +
        			"'.");
        }

        if (logger.isDebugEnabled()) {
        	if (ruleBaseCache.getRuleBase(ruleBaseType) == null) {
        		logger.debug("Adding rule base: rule base type="+ruleBaseType);
        	}
        	if (ruleBaseCache.getRuleBase(ruleBaseType) == null || ruleBaseCache.getRuleBase(ruleBaseType).getPackage(pkg.getName()) == null) {
        		logger.debug("Adding new package to rulebase: package name=" + 
        				pkg.getName() + ", uuid=" + ruleSet.getUUID() + 
        				", rule base type=" + ruleBaseType);
        	} else {
        		logger.debug("Replacing package in rulebase: package name=" + 
        				pkg.getName() + ", uuid=" + ruleSet.getUUID() + 
        				", rule base type=" + ruleBaseType);
        	}
        }

        ruleBaseCache.addPackage(ruleBaseType, pkg);
    }

    /**
     * Adds or replaces a rule set in the rule set execution cache.
     * This is a convenience method since rule sets are lazily loaded into the 
     * execution cache when <code>execute>/code> is performed. 
     * 
     * @param businessRule Functional business rule
     * @param ruleSet Rule set
     * @see #execute(BusinessRuleInfoDTO, RuleSetDTO, Map)
     */
    public void addRuleSet(BusinessRuleInfoDTO businessRule, RuleSetDTO ruleSet) {
    	String ruleBaseType = getRuleTypeKey(businessRule);
    	String anchorKey = getAnchorKey(businessRule);
    	if(anchorMap.containsKey(anchorKey)) {
    		throw new RuleSetExecutionException(
    				"Rule base already contains a business rule (id="+
    				businessRule.getBusinessRuleId() +
    				") with anchor value '" +businessRule.getAnchorValue() + "'");
    	}
    	addPackage(ruleBaseType, ruleSet);
    	anchorMap.put(anchorKey, businessRule.getBusinessRuleId());
    	BusinessRuleInfoValue value = getBusinessRuleInfoValue(businessRule);
    	businessRuleMap.put(value.getKey(), value);
    }

	/**
     * Removes a rule set from the rule set execution cache.
	 * 
     * @param businessRule Functional business rule
	 * @param ruleSet Rule set
	 */
    public void removeRuleSet(BusinessRuleInfoDTO businessRule, RuleSetDTO ruleSet) {
    	String ruleBaseType = businessRule.getBusinessRuleTypeKey();
    	String ruleSetName = ruleSet.getName();
    	String anchorKey = getAnchorKey(businessRule);
    	anchorMap.remove(anchorKey);
    	ruleBaseCache.removePackage(ruleBaseType, ruleSetName);
    	BusinessRuleInfoValue value = getBusinessRuleInfoValue(businessRule);
    	businessRuleMap.remove(value.getKey());
    }

    /**
     * Clears the rule set cache
     */
    public void clearRuleSetCache() {
    	ruleBaseCache.clearRuleBase();
    	anchorMap.clear();
    	businessRuleMap.clear();
    }

    /**
     * Returns true is the <code>ruleSetName</code> exists in the 
     * <code>businessRuleType</code> execution cache.
     * 
     * @param businessRule Functional business rule
	 * @param ruleSet Rule set
     * @return True if rule set exists in the business rule type execution cache; otherwise false
     */
    public boolean containsRuleSet(BusinessRuleInfoDTO businessRule) {
    	BusinessRuleInfoValue value = getBusinessRuleInfoValue(businessRule);
    	return businessRuleMap.containsKey(value.getKey());
    }

    /**
     * Gets the business rule type key.
     * 
     * @param brInfo Business rule
     * @return A key
     */
    private String getRuleTypeKey(BusinessRuleInfoDTO businessRule) {
        String ruleBaseType = businessRule.getBusinessRuleTypeKey();
        return ruleBaseType;
    }
    
    /**
     * <p>Executes an <code>agenda</code> with a map of <code>facts</code> and 
     * returns a list of execution results {@link ExecutionResult}.</p>
     * <p>The {@link ExecutionResult}'s id is set to the 
     * {@link BusinessRuleInfoDTO}'s business rule id.</p>
     * 
     * @param agenda Agenda to execute
     * @param facts List of Facts for the <code>agenda</code>
     * @return Result of executing the <code>agenda</code>
     */
    public synchronized List<ExecutionResult> execute(RuntimeAgendaDTO agenda, Map<String, Object> factMap) {
        logger.info("Executing agenda: businessRules="+agenda.getBusinessRules());
        List<ExecutionResult> resultList = new ArrayList<ExecutionResult>(agenda.getBusinessRules().size());
        for(BusinessRuleInfoDTO businessRule : agenda.getBusinessRules()) {
            ExecutionResult result = execute(businessRule, factMap);
            resultList.add(result);
        }
        return resultList;
    }

    /**
     * Executes a business rule <code>businessRule</code> in a rule set <code>ruleSet</code>.
     * 
     * @param businessRule Business rule to be executed
     * @param factMap Map of facts (data)
     * @return Result of execution 
     */
    public synchronized ExecutionResult execute(BusinessRuleInfoDTO businessRule, Map<String, Object> factMap) {
    	if (logger.isDebugEnabled()) {
    		logBusinessRule(businessRule, "Executing Business Rule");
    	}

    	String ruleBaseType = getRuleTypeKey(businessRule);
        String id = ""+System.nanoTime();
        String anchor = businessRule.getAnchorValue();
    	Map<String, RulePropositionDTO> propositionMap = BusinessRuleUtil.getRulePropositions(businessRule);
    	
        FactContainer factContainer =  new FactContainer(id, anchor, propositionMap, factMap);

        ExecutionResult result = executeRule(ruleBaseType, true, factContainer);
        result.setId(businessRule.getBusinessRuleId());
        try {
	        PropositionReport report = generateReport(result.getResults());
	        result.setReport(report);
        } catch(RuleSetExecutionException e) {
        	result.setErrorMessage(e.getMessage());
        }
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
    private ExecutionResult executeRule(String ruleBaseType, boolean logRuleExecution, FactContainer fact) { 
    	RuleBase ruleBase = ruleBaseCache.getRuleBase(ruleBaseType);
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
    private ExecutionResult executeRule(String ruleBaseType, boolean logRuleExecution, List<Object> facts) { 
    	RuleBase ruleBase = ruleBaseCache.getRuleBase(ruleBaseType);
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
    
    private class BusinessRuleInfoValue implements java.io.Serializable {
    	/** Class serial version uid */
        private static final long serialVersionUID = 1L;
        
        private String businessruleId; 
        private String compiledId;
        private Long compiledVersionNumber;
        private String anchorValue;
        private String anchorTypeKey;
        
		public BusinessRuleInfoValue(String businessruleId, String compiledId, Long compiledVersionNumber, String anchorValue, String anchorTypeKey) {
        	this.businessruleId = businessruleId;
        	this.compiledId = compiledId;
        	this.compiledVersionNumber = compiledVersionNumber;
        	this.anchorValue = anchorValue;
        	this.anchorTypeKey = anchorTypeKey;
        }

		public String getBusinessruleId() { return this.businessruleId; }

		public String getCompiledId() { return this.compiledId; }

		public Long getCompiledVersionNumber() { return this.compiledVersionNumber; }

		public String getAnchorValue() { return this.anchorValue; }
	    
        public String getAnchorTypeKey() { return this.anchorTypeKey; }

	    public String getKey() {
	    	return 
	    		this.businessruleId + "." + 
	    		this.compiledId + "." +
	    		this.compiledVersionNumber;
	    }
	    
	    public String toString() {
	    	return "[key=" + getKey() + 
	    		", anchorTypeKey=" + this.anchorTypeKey + 
	    		", anchorValue=" + this.anchorValue + "]";
	    }
    }
}
