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

import org.kuali.student.rules.ruleexecution.exceptions.RuleSetExecutionException;
import org.kuali.student.rules.ruleexecution.runtime.DefaultExecutor;
import org.kuali.student.rules.ruleexecution.runtime.ExecutionResult;
import org.kuali.student.rules.ruleexecution.runtime.drools.logging.DroolsWorkingMemoryLogger;
import org.kuali.student.rules.ruleexecution.util.LoggingStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultExecutorDroolsImpl implements DefaultExecutor {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(DefaultExecutorDroolsImpl.class);

    private final static String DEFAULT_RULE_BASE = "defaultRuleBase2"; 

    private final static DroolsRuleBase ruleBaseCache = DroolsRuleBase.getInstance();

    private boolean logExecution = false;

    public DefaultExecutorDroolsImpl() {}
    
    public DefaultExecutorDroolsImpl(boolean logExecution) {
    	this.logExecution = logExecution;
    }
    
    /**
     * Caches a rule set's source code by <code>id</code>.
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
        ruleBaseCache.addPackage(DEFAULT_RULE_BASE, pkg);
		if(logger.isDebugEnabled()) {
    		logger.debug("Added package '" + pkg.getName() + "'");
    	}
    }


    /**
     * Removes a package from the cache by <code>id</code>.
     * 
     * @param id Drools package name
     */
    public void removeRuleSet(String id) {
        ruleBaseCache.removePackage(DEFAULT_RULE_BASE, id);
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
     * Executes a rule set by id.
     * 
     * @param RuleSetId Rule set id
     * @param facts List of facts
     * @return
     */
    public synchronized ExecutionResult execute(String id, List<?> facts) {
    	RuleBase ruleBase = ruleBaseCache.getRuleBase(DEFAULT_RULE_BASE);
        StatelessSession session = ruleBase.newStatelessSession();
        DroolsWorkingMemoryLogger droolsLogger = null;
        LoggingStringBuilder executionLog = null;
        
        if(this.logExecution) {
            executionLog = createLogging();
        	droolsLogger = new DroolsWorkingMemoryLogger(session, executionLog);
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
