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

import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.drools.RuleBase;
import org.drools.StatelessSession;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;

import org.kuali.student.rules.repository.drools.util.DroolsUtil;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.ruleexecution.exceptions.RuleSetExecutionException;
import org.kuali.student.rules.ruleexecution.runtime.SimpleExecutor;
import org.kuali.student.rules.ruleexecution.runtime.ExecutionResult;
import org.kuali.student.rules.ruleexecution.runtime.drools.logging.DroolsExecutionStatistics;
import org.kuali.student.rules.ruleexecution.runtime.drools.logging.DroolsWorkingMemoryLogger;
import org.kuali.student.rules.ruleexecution.runtime.drools.logging.DroolsWorkingMemoryStatisticsLogger;
import org.kuali.student.rules.ruleexecution.util.LoggingStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleExecutorDroolsImpl implements SimpleExecutor {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(SimpleExecutorDroolsImpl.class);

    /** Drools general utility */
    private final static DroolsUtil droolsUtil = DroolsUtil.getInstance();
    
    /** Drools rule base cache */
    private DroolsRuleBase ruleBaseCache = new DroolsRuleBase();

    /** Execution logging */
    private boolean logExecution = false;
    
    /** Statistics logging */
    private boolean statlogging = false;
    
    private final static DroolsExecutionStatistics executionStats = DroolsExecutionStatistics.getInstance();

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
    public void setRuleBaseCache(DroolsRuleBase ruleBase) {
    	this.ruleBaseCache = ruleBase;
    }
    
    /**
     * Add a rule set's source code by <code>id</code> to the default 
     * execution cache.
     * 
     * @param id Drools package name
     * @param source Rule set source code
     */
    public void addRuleSet(String id, Reader source) {
    	Package pkg = null;
		try {
			pkg = buildPackage(source);
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
    public void addRuleSet(String ruleBaseType, RuleSetDTO ruleSet) {
    	Package pkg = droolsUtil.getPackage(ruleSet.getCompiledRuleSet());
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
    public void removeRuleSet(String ruleBaseType, RuleSetDTO ruleSet) {
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
    public boolean containsRuleSet(String ruleBaseType, RuleSetDTO ruleSet) {
    	return ruleBaseCache.containsPackage(ruleBaseType, ruleSet.getName());
	}

    /**
     * Clears the rule set execution cache.
     */
    public void clearRuleSetCache() {
    	ruleBaseCache.clearRuleBase();
    }
    
    /**
     * Clears the <code>ruleBaseType</code> rule set execution cache.
     * 
     * @param ruleBaseType Rule base type
     */
    public void clearRuleSetCache(String ruleBaseType) {
    	ruleBaseCache.clearRuleBase(ruleBaseType);
    }
    
    /**
     * Builds a Drools package from <code>source</code>
     * 
     * @param source Drools source code
     * @return A Drools Package
     * @throws Exception
     */
    private Package buildPackage(Reader source) throws Exception {
        PackageBuilder builder = new PackageBuilder();
        builder.addPackageFromDrl(source);
        Package pkg = builder.getPackage();
        return pkg;
    }

    /**
     * Creates a string builder for logging.
     * 
     * @return A Logging string builder
     */
    private LoggingStringBuilder createLogging() {
    	LoggingStringBuilder builder = new LoggingStringBuilder();
    	builder.append("*******************************");
    	builder.append("*   Default Drools Executor   *");
    	builder.append("*        Execution Log        *");
    	builder.append("*******************************");
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
    public synchronized ExecutionResult execute(RuleSetDTO ruleSet, List<?> facts) {
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
     * @see #addRuleSet(String, RuleSetDTO)
     * @see #containsRuleSet(String, String)
     * @see #removeRuleSet(String, String)
     */
    public synchronized ExecutionResult execute(String ruleBaseType, List<?> facts) {
    	RuleBase ruleBase = ruleBaseCache.getRuleBase(ruleBaseType);
    	return execute(ruleBase, facts);
    }

    /**
     * Executes a rule base with a list of facts.
     * 
     * @param ruleBase Rule base
     * @param facts List of facts
     * @return An execution result
     */
    private ExecutionResult execute(RuleBase ruleBase, List<?> facts) {
        StatelessSession session = ruleBase.newStatelessSession();
        DroolsWorkingMemoryLogger droolsLogger = null;
        DroolsWorkingMemoryStatisticsLogger statLogger = null;
        LoggingStringBuilder executionLog = null;
        
        if(this.logExecution) {
            executionLog = createLogging();
        	droolsLogger = new DroolsWorkingMemoryLogger(session, executionLog);
        }
        if(this.statlogging) {
        	statLogger = new DroolsWorkingMemoryStatisticsLogger(
        			session, "SimpleExecutorDroolsImpl", executionStats);
        }

        @SuppressWarnings("unchecked") 
        Iterator<Object> it = session.executeWithResults(facts).iterateObjects();
        
        if(this.logExecution) {
        	executionLog.append("********************************");
        	executionLog.append("*   Execution Result Objects   *");
        	executionLog.append("********************************");
        }
        
        List<Object> list = new ArrayList<Object>();
        while(it != null && it.hasNext()) {
        	Object obj = it.next();
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
