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
import org.kuali.student.rules.ruleexecution.runtime.AgendaExecutionResult;
import org.kuali.student.rules.ruleexecution.runtime.ExecutionResult;
import org.kuali.student.rules.ruleexecution.runtime.RuleSetExecutor;
import org.kuali.student.rules.ruleexecution.runtime.drools.logging.DroolsExecutionStatistics;
import org.kuali.student.rules.ruleexecution.runtime.drools.logging.DroolsWorkingMemoryLogger;
import org.kuali.student.rules.ruleexecution.runtime.drools.logging.DroolsWorkingMemoryStatisticsLogger;
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
    
    private SimpleExecutorDroolsImpl defaultExecutor = new SimpleExecutorDroolsImpl();
    
    private DroolsRuleBase ruleBaseCache;

    // Each rule base must have unique anchors
    private final static ConcurrentMap<String,String> anchorMap = new ConcurrentHashMap<String,String>();

    private final static ConcurrentMap<String,BusinessRuleInfoValue> businessRuleMap = new ConcurrentHashMap<String,BusinessRuleInfoValue>();
    
    private boolean logExecution = false;

    private boolean statLogging = false;
    
    private LoggingStringBuilder executionLog = null;
    
    private final static DroolsExecutionStatistics executionStats = DroolsExecutionStatistics.getInstance();
    
    /**
     * Constructs a new rule set executor without a repository.
     */
    public RuleSetExecutorDroolsImpl() {
    	this.logExecution = false;
    }

    /**
     * Enables rule engine execution logging.
     */
    public void setEnableExecutionLogging(boolean enable) {
    	this.logExecution = enable;
    }

    /**
     * Enables rule engine execution statistics logging.
     */
    public void setEnableStatLogging(boolean enable) {
    	this.statLogging = enable;
    }

    /**
     * Gets Drools execution statistics.
     * 
     * @return Execution statistics
     */
    public DroolsExecutionStatistics getStatistics() {
    	return executionStats;
    }

    /**
     * Sets the Drools rule base cache.
     * 
     * @param ruleBase Drools rule base
     */
    public void setRuleBaseCache(DroolsRuleBase ruleBase) {
    	this.ruleBaseCache = ruleBase;
    }
    
    /**
     * Logs a business rule.
     * 
     * @param businessRule business rule to log
     * @param message A message
     */
    private String logBusinessRule(BusinessRuleInfoDTO businessRule, String message) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("\n**************************************************");
    	sb.append("\n"+message);
    	sb.append("\nBusiness rule name:                    "+businessRule.getName());
    	sb.append("\nBusiness rule id:                      "+businessRule.getBusinessRuleId());
    	sb.append("\nBusiness rule compiledId:              "+businessRule.getCompiledId());
//    	sb.append("\nBusiness rule compiled version number: "+businessRule.getCompiledVersionNumber());
    	sb.append("\nBusiness rule anchor type key:         "+businessRule.getAnchorTypeKey());
    	sb.append("\nBusiness rule anchor value:            "+businessRule.getAnchorValue());
    	sb.append("\n**************************************************");
        logger.debug(sb.toString());
        return sb.toString();
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
//			businessRule.getCompiledVersionNumber(),
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
     * Creates a string builder for logging.
     * 
     * @return A Logging string builder
     */
    private void startLogging(BusinessRuleInfoDTO businessRule, String ruleBaseType, String id) {
    	this.executionLog = new LoggingStringBuilder();
    	this.executionLog.append("********************************");
    	this.executionLog.append("*   Drools Rule Set Executor   *");
    	this.executionLog.append("*        Execution Log         *");
    	this.executionLog.append("********************************");
    	this.executionLog.append("----- START -----");
    	this.executionLog.append("Business rule name:                    "+businessRule.getName());
    	this.executionLog.append("Business rule id:                      "+businessRule.getBusinessRuleId());
    	this.executionLog.append("Business rule compiled ID:             "+businessRule.getCompiledId());
//    	this.executionLog.append("Business rule compiled version number: "+businessRule.getCompiledVersionNumber());
    	this.executionLog.append("Business rule anchor type key:         "+businessRule.getAnchorTypeKey());
    	this.executionLog.append("Business rule anchor value:            "+businessRule.getAnchorValue());
    	this.executionLog.append("Fact container ID:                     " + id);
    	this.executionLog.append("Execution rule base:                   " + ruleBaseType);
    	RuleBase ruleBase = ruleBaseCache.getRuleBase(ruleBaseType);
    	if (ruleBase == null) {
    		this.executionLog.append("Packages loaded in rule base:          rule base is null");
    	} else if (ruleBase.getPackages() == null ) {
    		this.executionLog.append("Packages loaded in rule base:          0");
    	} else {
    		this.executionLog.append("Packages loaded in rule base:          " + ruleBase.getPackages().length);
	    	for(Package pkg : ruleBase.getPackages()) {
	    		this.executionLog.append("\tPackage Name: " + pkg.getName());
	    	}
    	}
    }
    
    /**
     * Stop logging message.
     */
    private void stopLogging() {
    	this.executionLog.append("----- END -----");
    }
    
    /**
     * Log execution result message.
     */
    private void logResults() {
    	this.executionLog.append("********************************");
    	this.executionLog.append("*   Execution Result Objects   *");
    	this.executionLog.append("********************************");
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
    public synchronized AgendaExecutionResult execute(RuntimeAgendaDTO agenda, Map<String, Object> factMap) {
        logger.info("Executing agenda: businessRules="+agenda.getBusinessRules());
        AgendaExecutionResult agendaExecutionResult = new AgendaExecutionResult();
        for(BusinessRuleInfoDTO businessRule : agenda.getBusinessRules()) {
            ExecutionResult result = execute(businessRule, factMap);
            agendaExecutionResult.addExecutionResult(result);
        }
        agendaExecutionResult.setExecutionResult(Boolean.TRUE);
        return agendaExecutionResult;
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

        if(this.logExecution) {
            startLogging(businessRule, ruleBaseType, id);
        }
        
    	Map<String, RulePropositionDTO> propositionMap = BusinessRuleUtil.getRulePropositions(businessRule);
    	
        FactContainer factContainer =  new FactContainer(id, anchor, propositionMap, factMap);

        ExecutionResult result = executeRule(ruleBaseType, factContainer);
        result.setId(businessRule.getBusinessRuleId());
        try {
	        PropositionReport report = generateReport(result.getResults());
	        result.setReport(report);
        } catch(RuleSetExecutionException e) {
        	result.setErrorMessage(e.getMessage());
        }
        
    	//if (logger.isInfoEnabled()) {
    	//	droolsUtil().logStatistics(executionStats);
    	//}
        
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
     * Executes a <code>ruleBase</code> with a <code>fact</code>.
     * 
     * @param logRuleExecution If set to true, logs rule engine execution; otherwise no logging is done
     * @param ruleBase List of rules
     * @param fact Fact container for the <code>ruleBase</code>
     * @return An execution result
     */
    private ExecutionResult executeRule(String ruleBaseType, FactContainer fact) { 
    	RuleBase ruleBase = ruleBaseCache.getRuleBase(ruleBaseType);
        StatelessSession session = ruleBase.newStatelessSession();
        DroolsWorkingMemoryLogger droolsLogger = null;
        DroolsWorkingMemoryStatisticsLogger statLogger = null;
        
        if(this.logExecution) {
        	droolsLogger = new DroolsWorkingMemoryLogger(session, this.executionLog);
        }
        if(this.statLogging) {
        	statLogger = new DroolsWorkingMemoryStatisticsLogger(
        			session, ruleBaseType, executionStats);
        }
        
        List<Object> factList = assembleFacts(fact);
        ExecutionResult result = new ExecutionResult();
        
        @SuppressWarnings("unchecked") 
        Iterator<Object> it = session.executeWithResults(factList).iterateObjects();
    
        if(this.logExecution) {
        	logResults();
        }
        
        List<Object> list = new ArrayList<Object>();
        while(it != null && it.hasNext()) {
        	Object obj = it.next();
            if(this.logExecution) {
            	this.executionLog.append(obj.toString());
            }
        	if (obj instanceof FactContainer) {
        		FactContainer fc = (FactContainer) obj;
        		if (fc.getId().equals(fact.getId())){
                	list.add(obj);
        		}
        	}
        }

        result.setResults(list);
        
        if(this.logExecution) {
        	stopLogging();
        	result.setExecutionLog(droolsLogger.getLog().toString());
        }
        
        result.setExecutionResult(Boolean.TRUE);
        return result;
    }

    private class BusinessRuleInfoValue implements java.io.Serializable {
    	/** Class serial version uid */
        private static final long serialVersionUID = 1L;
        
        private String businessruleId; 
        private String compiledId;
//        private Long compiledVersionNumber;
        private String anchorValue;
        private String anchorTypeKey;
        
		public BusinessRuleInfoValue(String businessruleId, String compiledId, 
//				Long compiledVersionNumber, 
				String anchorValue, String anchorTypeKey) {
        	this.businessruleId = businessruleId;
        	this.compiledId = compiledId;
//        	this.compiledVersionNumber = compiledVersionNumber;
        	this.anchorValue = anchorValue;
        	this.anchorTypeKey = anchorTypeKey;
        }

		public String getBusinessruleId() { return this.businessruleId; }

		public String getCompiledId() { return this.compiledId; }

//		public Long getCompiledVersionNumber() { return this.compiledVersionNumber; }

		public String getAnchorValue() { return this.anchorValue; }
	    
        public String getAnchorTypeKey() { return this.anchorTypeKey; }

	    public String getKey() {
	    	return 
	    		this.businessruleId + "." + 
	    		this.compiledId; 
//	    		+ "." +
//	    		this.compiledVersionNumber;
	    }
	    
	    public String toString() {
	    	return "[key=" + getKey() + 
	    		", anchorTypeKey=" + this.anchorTypeKey + 
	    		", anchorValue=" + this.anchorValue + "]";
	    }
    }
}
