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

package org.kuali.student.brms.ruleexecution.runtime.drools;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.drools.KnowledgeBase;
import org.drools.command.Command;
import org.drools.command.CommandFactory;
import org.drools.definition.KnowledgePackage;
import org.drools.runtime.ExecutionResults;
import org.drools.runtime.StatelessKnowledgeSession;
import org.kuali.student.brms.internal.common.utils.BusinessRuleUtil;
import org.kuali.student.brms.repository.drools.util.DroolsUtil;
import org.kuali.student.brms.repository.dto.RuleSetInfo;
import org.kuali.student.brms.ruleexecution.exceptions.RuleSetExecutionException;
import org.kuali.student.brms.ruleexecution.runtime.ExecutionResult;
import org.kuali.student.brms.ruleexecution.runtime.RuleExecutor;
import org.kuali.student.brms.ruleexecution.runtime.drools.logging.DroolsExecutionStatistics;
import org.kuali.student.brms.ruleexecution.runtime.drools.logging.DroolsWorkingMemoryLogger;
import org.kuali.student.brms.ruleexecution.runtime.drools.logging.DroolsWorkingMemoryStatisticsLogger;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;
import org.kuali.student.brms.rulemanagement.dto.RulePropositionInfo;
import org.kuali.student.brms.util.CurrentDateTime;
import org.kuali.student.brms.util.FactContainer;
import org.kuali.student.brms.util.LoggingStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleSetExecutorDroolsImpl implements RuleExecutor, java.io.Serializable {
	/** Class serial version uid */
    private static final long serialVersionUID = 1L;

    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(RuleSetExecutorDroolsImpl.class);

    private final DroolsUtil droolsUtil = DroolsUtil.getInstance();
    
    private DroolsKnowledgeBase ruleBaseCache;

    /** Rule engine's global objects */
    private Map<String, Object> globalObjectMap;

    // Each rule base must have unique anchors
    private final ConcurrentMap<String,String> anchorMap = new ConcurrentHashMap<String,String>();

    private final ConcurrentMap<String,BusinessRuleInfoValue> businessRuleMap = new ConcurrentHashMap<String,BusinessRuleInfoValue>();
    
    private boolean logExecution = false;

    private boolean statLogging = false;
    
    private LoggingStringBuilder executionLog = null;
    
    private final DroolsExecutionStatistics executionStats = DroolsExecutionStatistics.getInstance();
    
    /**
     * Constructs a new rule set executor with no logging.
     */
    public RuleSetExecutorDroolsImpl() {
    	this.logExecution = false;
    }

    /**
     * Constructs a new rule set executor.
     * 
     * @param logExecution True execution logging is enabled; otherwise disabled
     * @param statLogging True statistics logging is enabled; otherwise disabled
     */
    public RuleSetExecutorDroolsImpl(boolean logExecution, boolean statLogging) {
    	this.logExecution = logExecution;
    	this.statLogging = statLogging;
    }

    /**
     * Gets Drools execution statistics.
     * 
     * @return Execution statistics
     */
    public DroolsExecutionStatistics getStatistics() {
    	return this.executionStats;
    }

    /**
     * Sets the Drools rule base cache.
     * 
     * @param ruleBase Drools rule base
     */
    public void setRuleBaseCache(DroolsKnowledgeBase ruleBase) {
    	this.ruleBaseCache = ruleBase;
    }

    /**
     * Sets global read only objects for the rule engine. 
     * These objects are usually constants, Hibernate sessions, 
     * JPA session etc.
     * 
     * @param globalObjectMap Global key,value map
     */
    public void setGlobalObjects(Map<String, Object> globalObjectMap) {
    	this.globalObjectMap = globalObjectMap;
    }
    
    /**
     * Logs a business rule.
     * 
     * @param businessRule business rule to log
     * @param message A message
     */
    private String logBusinessRule(BusinessRuleInfo businessRule, String message) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("\n--------------------------------------------------");
    	sb.append("\n| "+message);
    	sb.append("\n| Business rule name:                    "+businessRule.getName());
    	sb.append("\n| Business rule id:                      "+businessRule.getId());
    	sb.append("\n| Business rule compiledId:              "+businessRule.getCompiledId());
    	sb.append("\n| Business rule anchor type key:         "+businessRule.getAnchorTypeKey());
    	sb.append("\n| Business rule anchor value:            "+businessRule.getAnchor());
    	sb.append("\n--------------------------------------------------");
        logger.debug(sb.toString());
        return sb.toString();
    }

    /**
     * Gets the business rule anchor key.
     * 
     * @param businessRule Business rule
     * @return Anchor key
     */
    private String getAnchorKey(BusinessRuleInfo businessRule) {
    	return businessRule.getType() + businessRule.getAnchor();
    }

    /**
     * Gets the business rule info key value.
     * 
     * @param businessRule Business rule
     * @return Business rule info key value
     */
    private BusinessRuleInfoValue getBusinessRuleInfoValue(BusinessRuleInfo businessRule) {
    	return new BusinessRuleInfoValue(
			businessRule.getId(), 
			businessRule.getCompiledId(), 
			businessRule.getAnchor(),
			businessRule.getAnchorTypeKey());
    }

    /**
     * Adds a Drools package to the Drools rule base.
     * 
     * @param ruleSet Rule set
     */
    private void addPackage(String ruleBaseType, RuleSetInfo ruleSet) {
    	KnowledgePackage pkg = this.droolsUtil.getKnowledgePackage(ruleSet.getCompiledRuleSet());

    	if(pkg == null) {
    		logger.warn("RuleSet was not compiled: ruleSet UUID="+ruleSet.getUUID());
    		logger.warn("Compiling RuleSet: ruleSet UUID="+ruleSet.getUUID());
    		try {
				pkg = droolsUtil.buildKnowledgePackage(new StringReader(ruleSet.getContent()));
            } catch(RuntimeException e) {
                throw new RuleSetExecutionException("Building Drools Package failed",e);
            }
    	}
    	
    	if (!pkg.getName().equals(ruleSet.getName())){
        	throw new RuleSetExecutionException(
        			"Cannot add package to rule base. " +
        			"Drools compiled package name '" + pkg.getName() + 
        			"' does not match rule set name '" + ruleSet.getName() +
        			"'.");
        }

        if (logger.isDebugEnabled()) {
        	if (ruleBaseCache.getKnowledgeBase(ruleBaseType) == null) {
        		logger.debug("Adding rule base: rule base type="+ruleBaseType);
        	}
        	if (ruleBaseCache.getKnowledgeBase(ruleBaseType) == null || ruleBaseCache.getKnowledgeBase(ruleBaseType).getKnowledgePackage(pkg.getName()) == null) {
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
     * execution cache when <code>execute</code> is performed. 
     * 
     * @param businessRule Functional business rule
     * @param ruleSet Rule set
     * @see #execute(BusinessRuleInfo, RuleSetInfo, Map)
     */
    public void addRuleSet(BusinessRuleInfo businessRule, RuleSetInfo ruleSet) {
    	String ruleBaseType = getRuleTypeKey(businessRule);
    	String anchorKey = getAnchorKey(businessRule);
    	if(this.anchorMap.containsKey(anchorKey)) {
    		throw new RuleSetExecutionException(
    				"Rule base already contains a business rule (id="+
    				businessRule.getId() +
    				") with anchor value '" + businessRule.getAnchor() + "'");
    	}
    	addPackage(ruleBaseType, ruleSet);
    	this.anchorMap.put(anchorKey, businessRule.getId());
    	BusinessRuleInfoValue value = getBusinessRuleInfoValue(businessRule);
    	this.businessRuleMap.put(value.getKey(), value);
    }

	/**
     * Removes a rule set from the rule set execution cache.
	 * 
     * @param businessRule Functional business rule
	 * @param ruleSet Rule set
	 */
    public void removeRuleSet(BusinessRuleInfo businessRule, RuleSetInfo ruleSet) {
    	String ruleBaseType = businessRule.getType();
    	String ruleSetName = ruleSet.getName();
    	String anchorKey = getAnchorKey(businessRule);
    	this.anchorMap.remove(anchorKey);
    	this.ruleBaseCache.removePackage(ruleBaseType, ruleSetName);
    	BusinessRuleInfoValue value = getBusinessRuleInfoValue(businessRule);
    	this.businessRuleMap.remove(value.getKey());
    }

    /**
     * Clears the rule set cache
     */
    public void clearRuleSetCache() {
    	this.ruleBaseCache.clearKnowledgeBase();
    	this.anchorMap.clear();
    	this.businessRuleMap.clear();
    }

    /**
     * Returns true is the <code>ruleSetName</code> exists in the 
     * <code>businessRuleType</code> execution cache.
     * 
     * @param businessRule Functional business rule
	 * @param ruleSet Rule set
     * @return True if rule set exists in the business rule type execution cache; otherwise false
     */
    public boolean containsRuleSet(BusinessRuleInfo businessRule) {
    	BusinessRuleInfoValue value = getBusinessRuleInfoValue(businessRule);
    	return this.businessRuleMap.containsKey(value.getKey());
    }

    /**
     * Gets the business rule type key.
     * 
     * @param brInfo Business rule
     * @return A key
     */
    private String getRuleTypeKey(BusinessRuleInfo businessRule) {
        String ruleBaseType = businessRule.getType();
        return ruleBaseType;
    }
    
    /**
     * Creates a string builder for logging.
     * 
     * @return A Logging string builder
     */
    private void startLogging(BusinessRuleInfo businessRule, String ruleBaseType, String id) {
    	this.executionLog = new LoggingStringBuilder();
    	this.executionLog.append("--------------------------------");
    	this.executionLog.append("|   Drools Rule Set Executor   |");
    	this.executionLog.append("|        Execution Log         |");
    	this.executionLog.append("--------------------------------");
    	this.executionLog.append("----- START -----");
    	this.executionLog.append("Business rule name:                    "+businessRule.getName());
    	this.executionLog.append("Business rule id:                      "+businessRule.getId());
    	this.executionLog.append("Business rule compiled ID:             "+businessRule.getCompiledId());
//    	this.executionLog.append("Business rule compiled version number: "+businessRule.getCompiledVersionNumber());
    	this.executionLog.append("Business rule anchor type key:         "+businessRule.getAnchorTypeKey());
    	this.executionLog.append("Business rule anchor value:            "+businessRule.getAnchor());
    	this.executionLog.append("Fact container ID:                     "+id);
    	this.executionLog.append("Execution rule base:                   "+ruleBaseType);
    	KnowledgeBase ruleBase = ruleBaseCache.getKnowledgeBase(ruleBaseType);
    	if (ruleBase == null) {
    		this.executionLog.append("Packages loaded in rule base:          rule base is null");
    	} else if (ruleBase.getKnowledgePackages() == null ) {
    		this.executionLog.append("Packages loaded in rule base:          0");
    	} else {
    		this.executionLog.append("Packages loaded in rule base:          "+ruleBase.getKnowledgePackages().size());
	    	for(KnowledgePackage pkg : ruleBase.getKnowledgePackages()) {
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
    	this.executionLog.append("--------------------------------");
    	this.executionLog.append("|   Execution Result Objects   |");
    	this.executionLog.append("--------------------------------");
    }

    /**
     * Executes a business rule <code>businessRule</code> in a rule set <code>ruleSet</code>.
     * 
     * @param businessRule Business rule to be executed
     * @param factMap Map of facts (data)
     * @return Result of execution 
     */
    public synchronized ExecutionResult execute(BusinessRuleInfo businessRule, Map<String, Object> factMap) {
    	if (logger.isDebugEnabled()) {
    		logBusinessRule(businessRule, "Executing Business Rule");
    	}

    	String ruleBaseType = getRuleTypeKey(businessRule);
        String id = ""+System.nanoTime();
        String anchor = businessRule.getAnchor();
        String anchorTypeKey = businessRule.getAnchorTypeKey();

        if(this.logExecution) {
            startLogging(businessRule, ruleBaseType, id);
        }
        
    	Map<String, RulePropositionInfo> propositionMap = BusinessRuleUtil.getRulePropositions(businessRule);
    	
        FactContainer factContainer =  new FactContainer(id, anchor, anchorTypeKey, propositionMap, factMap);

        ExecutionResult result = executeRule(ruleBaseType, factContainer);
        result.setId(businessRule.getId());
        
    	//if (logger.isInfoEnabled()) {
    	//	droolsUtil().logStatistics(this.executionStats);
    	//}
        
        return result;
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
    	KnowledgeBase knowledgeBase = ruleBaseCache.getKnowledgeBase(ruleBaseType);
        StatelessKnowledgeSession session = knowledgeBase.newStatelessKnowledgeSession();
        DroolsWorkingMemoryLogger droolsLogger = null;
        
        if(this.logExecution) {
        	droolsLogger = new DroolsWorkingMemoryLogger(session, this.executionLog);
        }
        if(this.statLogging) {
        	new DroolsWorkingMemoryStatisticsLogger(session, ruleBaseType, this.executionStats);
        }
        
        if(this.globalObjectMap != null && !this.globalObjectMap.isEmpty()) {
	        for(Entry<String, Object> entry : this.globalObjectMap.entrySet()) {
	        	session.setGlobal(entry.getKey(), entry.getValue());
	        }
        }
        
        List<Object> factList = assembleFacts(fact);
        ExecutionResult result = new ExecutionResult();
        
        List<Command<?>> commands = new ArrayList<Command<?>>();
        int i = 0;
        for(Object f : factList) {
        	String id = "id." + i++;
            Command<?> cmd = CommandFactory.newInsert(f, id);
            commands.add(cmd);
        }
        Command<?> cmd = CommandFactory.newBatchExecution( commands );
        ExecutionResults results = session.execute(cmd);
        
    
        if(this.logExecution) {
        	logResults();
        }
        
        List<Object> list = new ArrayList<Object>();
        for(String id : results.getIdentifiers()) {
        	Object obj = results.getValue(id);
            if (obj instanceof FactContainer) {
        		FactContainer fc = (FactContainer) obj;
        		if (fc.getId().equals(fact.getId())){
                	list.add(obj);
        		}
                if(this.logExecution) {
                	this.executionLog.append(obj.toString());
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

    private static class BusinessRuleInfoValue implements java.io.Serializable {
    	/** Class serial version uid */
        private static final long serialVersionUID = 1L;
        
        private String businessRuleId; 
        private String compiledId;
//        private Long compiledVersionNumber;
        private String anchorValue;
        private String anchorTypeKey;
        
		public BusinessRuleInfoValue(String businessruleId, String compiledId, 
//				Long compiledVersionNumber, 
				String anchorValue, String anchorTypeKey) {
        	this.businessRuleId = businessruleId;
        	this.compiledId = compiledId;
//        	this.compiledVersionNumber = compiledVersionNumber;
        	this.anchorValue = anchorValue;
        	this.anchorTypeKey = anchorTypeKey;
        }

		public String getBusinessRuleId() { return this.businessRuleId; }

		public String getCompiledId() { return this.compiledId; }

//		public Long getCompiledVersionNumber() { return this.compiledVersionNumber; }

		public String getAnchorValue() { return this.anchorValue; }
	    
        public String getAnchorTypeKey() { return this.anchorTypeKey; }

	    public String getKey() {
	    	return this.businessRuleId + "." + this.compiledId; 
	    }
	    
	    public String toString() {
	    	return "[key=" + getKey() + 
	    		", anchorTypeKey=" + this.anchorTypeKey + 
	    		", anchorValue=" + this.anchorValue + "]";
	    }
    }
}
