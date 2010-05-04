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

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.drools.KnowledgeBase;
import org.drools.command.Command;
import org.drools.command.CommandFactory;
import org.drools.definition.KnowledgePackage;
import org.drools.runtime.ExecutionResults;
import org.drools.runtime.StatelessKnowledgeSession;

import org.kuali.student.brms.repository.drools.util.DroolsUtil;
import org.kuali.student.brms.repository.dto.RuleSetInfo;
import org.kuali.student.brms.ruleexecution.exceptions.RuleSetExecutionException;
import org.kuali.student.brms.ruleexecution.runtime.SimpleExecutor;
import org.kuali.student.brms.ruleexecution.runtime.ExecutionResult;
import org.kuali.student.brms.ruleexecution.runtime.drools.logging.DroolsExecutionStatistics;
import org.kuali.student.brms.ruleexecution.runtime.drools.logging.DroolsWorkingMemoryLogger;
import org.kuali.student.brms.ruleexecution.runtime.drools.logging.DroolsWorkingMemoryStatisticsLogger;
import org.kuali.student.brms.util.LoggingStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleExecutorDroolsImpl implements SimpleExecutor {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(SimpleExecutorDroolsImpl.class);

    /** Drools general utility */
    private final DroolsUtil droolsUtil = DroolsUtil.getInstance();
    
    /** Drools rule base cache */
    private DroolsKnowledgeBase ruleBaseCache = new DroolsKnowledgeBase();

    /** Execution logging */
    private boolean logExecution = false;
    
    /** Statistics logging */
    private boolean statlogging = false;

    /** Drools execution statistics */
    private final DroolsExecutionStatistics executionStats = DroolsExecutionStatistics.getInstance();
    
    /** Rule engine's global objects */
    private Map<String, Object> globalObjectMap;

    public SimpleExecutorDroolsImpl() {}
    
    /**
     * Constructor.
     * 
     * @param logExecution True starts rule execution logging, false stops rule execution logging
     */
    public SimpleExecutorDroolsImpl(boolean logExecution) {
    	this.logExecution = logExecution;
    }
    
    /**
     * Enables rule engine execution statistics logging.
     */
    public void setEnableStatisticsLogging(boolean enable) {
    	this.statlogging = enable;
    }

    /**
     * Gets rule execution statistics.
     * 
     * @return Drools execution statistics
     */
    public DroolsExecutionStatistics getStatistics() {
    	return executionStats;
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
     * Adds a global read only object to the rule engine. 
     * These objects are usually constants, Hibernate sessions, 
     * JPA session etc.
     * 
     * @param id Identifier associated with the object
     * @param object Object to add to the rule engine
     */
    public void addGlobalObject(String id, Object object) {
    	if(this.globalObjectMap == null) {
    		this.globalObjectMap = new HashMap<String, Object>();
    	}
    	this.globalObjectMap.put(id, object);
    }

    /**
     * Add a rule set's source code by <code>id</code> to the default 
     * execution cache.
     * 
     * @param id Drools package name
     * @param source Rule set source code
     */
    public void addRuleSet(String id, Reader source) {
    	KnowledgePackage pkg = null;
		try {
			pkg = droolsUtil.buildKnowledgePackage(source);
        } catch(Exception e) {
            throw new RuleSetExecutionException("Building Drools Package failed",e);
        }            
        
        if (!pkg.getName().equals(id)){
        	throw new RuleSetExecutionException(
        			"Cannot add package to rule base. " +
        			"Drools compiled package name '" + pkg.getName() + 
        			"' does not match package name '" + id +
        			"'.");
        }

        ruleBaseCache.addPackage(DEFAULT_RULE_CACHE_KEY, pkg);

        if(logger.isDebugEnabled()) {
    		logger.debug("Added package '" + pkg.getName() + "'");
    	}
    }

    /**
     * Adds a <code>ruleSet</code> to the <code>ruleBaseType</code> 
     * execution cache.
     * 
     * @param ruleBaseType Rule base type cache
     * @param ruleSet Rule set
     */
    public void addRuleSet(String ruleBaseType, RuleSetInfo ruleSet) {
    	KnowledgePackage pkg = droolsUtil.getKnowledgePackage(ruleSet.getCompiledRuleSet());
    	
    	if(pkg == null) {
    		try {
				pkg = droolsUtil.buildKnowledgePackage(new StringReader(ruleSet.getContent()));
            } catch(Exception e) {
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
        ruleBaseCache.addPackage(ruleBaseType, pkg);
    }

    /**
     * Removes a rule set from the default execution cache by <code>id</code>.
     * 
     * @param id Drools package name
     */
    public void removeRuleSet(String id) {
        ruleBaseCache.removePackage(DEFAULT_RULE_CACHE_KEY, id);
    }

    /**
     * Removes a rule set from the <code>ruleBaseType</code> execution cache by
     * <code>id</code>.
     * 
     * @param ruleBaseType Rule base type cache
     * @param id Rule set id
     */
    public void removeRuleSet(String ruleBaseType, RuleSetInfo ruleSet) {
        ruleBaseCache.removePackage(ruleBaseType, ruleSet.getName());
    }

    /**
     * Returns true if the rule set <code>id</code> exists in the 
     * default execution cache.
     * 
     * @param id Rule set id
     * @return True if rule set exists execution cache; otherwise false
     */
    public boolean containsRuleSet(String id) {
    	return ruleBaseCache.containsPackage(DEFAULT_RULE_CACHE_KEY, id);
    }

    /**
     * Returns true if the <code>ruleSetName</code> exists in the 
     * <code>ruleBaseType</code> execution cache.
     *  
     * @param ruleBaseType Rule base type cache
     * @param ruleSetName Rule set name
     * @return
     */
    public boolean containsRuleSet(String ruleBaseType, RuleSetInfo ruleSet) {
    	return ruleBaseCache.containsPackage(ruleBaseType, ruleSet.getName());
	}

    /**
     * Clears the rule set execution cache.
     */
    public void clearRuleSetCache() {
    	ruleBaseCache.clearKnowledgeBase();
    }
    
    /**
     * Clears the <code>ruleBaseType</code> rule set execution cache.
     * 
     * @param ruleBaseType Rule base type
     */
    public void clearRuleSetCache(String ruleBaseType) {
    	ruleBaseCache.clearKnowledgeBase(ruleBaseType);
    }
    
    /**
     * Creates a string builder for logging.
     * 
     * @return A Logging string builder
     */
    private LoggingStringBuilder createLogging() {
    	LoggingStringBuilder builder = new LoggingStringBuilder();
    	builder.append("-------------------------------");
    	builder.append("|   Default Drools Executor   |");
    	builder.append("|        Execution Log        |");
    	builder.append("-------------------------------");
    	builder.append("----- START -----");
    	return builder;
    }

    /**
     * Executes a rule set with a list of facts.
     * 
     * @param ruleSet Rule set
     * @param facts List of facts
     * @return An execution result
     */
    public synchronized ExecutionResult execute(RuleSetInfo ruleSet, List<?> facts) {
    	final String tempCache = "TEMP_CACHE";
    	try {
	    	addRuleSet(tempCache, ruleSet);
	    	return execute(tempCache, facts);
    	} finally {
	    	removeRuleSet(tempCache, ruleSet);
    	}
    }

    /**
     * Executes rules in the default execution cache with a list of 
     * <code>facts</code>.
     * 
     * @param facts list of facts.
     * @return An execution result
     * @see #addRuleSet(String, Reader)
     * @see #containsRuleSet(String)
     * @see #removeRuleSet(String)
     */
    public synchronized ExecutionResult execute(List<?> facts) {
    	return execute(DEFAULT_RULE_CACHE_KEY, facts);
    }

    /**
     * Executes a rule set in the <code>ruleBaseType</code> execution cache 
     * by <code>id</code> with a list of <code>facts</code>.
     * 
     * @param ruleBaseType Rule base type cache
     * @param id Rule set id
     * @param facts List of facts
     * @return An execution result
     * @see #addRuleSet(String, RuleSetInfo)
     * @see #containsRuleSet(String, String)
     * @see #removeRuleSet(String, String)
     */
    public synchronized ExecutionResult execute(String ruleBaseType, List<?> facts) {
    	KnowledgeBase knowledgeBase = ruleBaseCache.getKnowledgeBase(ruleBaseType);
    	return execute(knowledgeBase, facts);
    }

    /**
     * Executes a rule base with a list of facts.
     * 
     * @param ruleBase Rule base
     * @param facts List of facts
     * @return An execution result
     */
    private ExecutionResult execute(KnowledgeBase knowledgeBase, List<?> facts) {
        StatelessKnowledgeSession session = knowledgeBase.newStatelessKnowledgeSession();
        DroolsWorkingMemoryLogger droolsLogger = null;
        LoggingStringBuilder executionLog = null;
        
        if(this.logExecution) {
            executionLog = createLogging();
        	droolsLogger = new DroolsWorkingMemoryLogger(session, executionLog);
        }
        if(this.statlogging) {
        	new DroolsWorkingMemoryStatisticsLogger(session, "SimpleExecutorDroolsImpl", executionStats);
        }

        if(this.globalObjectMap != null && !this.globalObjectMap.isEmpty()) {
	        for(Entry<String, Object> entry : this.globalObjectMap.entrySet()) {
	        	session.setGlobal(entry.getKey(), entry.getValue());
	        }
        }
        
        List<Command<?>> commands = new ArrayList<Command<?>>();
        int i = 0;
        for(Object fact : facts) {
        	String id = "id." + i++;
        	Command<?> cmd = CommandFactory.newInsert(fact, id);
            commands.add(cmd);
        }
        Command<?> cmd = CommandFactory.newBatchExecution(commands);
        ExecutionResults results = session.execute(cmd);
        
        if(this.logExecution) {
        	executionLog.append("--------------------------------");
        	executionLog.append("|   Execution Result Objects   |");
        	executionLog.append("--------------------------------");
        }
        
        List<Object> list = new ArrayList<Object>();
        for(String id : results.getIdentifiers()) {
        	Object obj = results.getValue(id);
        	list.add(obj);
            if(this.logExecution) {
            	executionLog.append("Object: "+obj.toString());
            }
        }
        
        ExecutionResult result = new ExecutionResult();
        result.setResults(list);
        if(this.logExecution) {
        	executionLog.append("--- END ---");
        	result.setExecutionLog(droolsLogger.getLog().toString());
        }
        
        return result;
    }
}
