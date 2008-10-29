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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatelessSession;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;

import org.kuali.student.rules.ruleexecution.exceptions.RuleSetExecutionException;
import org.kuali.student.rules.ruleexecution.runtime.DefaultExecutor;
import org.kuali.student.rules.ruleexecution.runtime.ExecutionResult;
import org.kuali.student.rules.ruleexecution.runtime.drools.logging.DroolsWorkingMemoryLogger;

public class DefaultExecutorDroolsImpl implements DefaultExecutor {

    private ConcurrentMap<String, List<Package>> packageMap = new ConcurrentHashMap<String, List<Package>>();

    private boolean logExecution = false;

    public DefaultExecutorDroolsImpl() {}
    
    public DefaultExecutorDroolsImpl(boolean logExecution) {
    	this.logExecution = logExecution;
    }
    
    /**
     * Caches a rule set's source code by <code>id</code>.
     * 
     * @param ruleSetId Rule set cache id
     * @param source Rule set source code
     */
    public void addRuleSet(String id, Reader source) {
        try {
            if (this.packageMap.containsKey(id)) {
                this.packageMap.get(id).add(buildPackage(source));
            }
            else {
                List<Package> list = new ArrayList<Package>();
                list.add(buildPackage(source));
                this.packageMap.put(id, list);
            }
        } catch(Exception e) {
            throw new RuleSetExecutionException("Building Drools Package failed",e);
        }
    }

    /**
     * Removes a rule set from the cache by <code>id</code>.
     * 
     * @param id Rule set id
     */
    public boolean removeRuleSet(String id) {
        this.packageMap.remove(id);
        return !this.packageMap.containsKey(id);
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
     * Executes a rule set by id.
     * 
     * @param RuleSetId Rule set id
     * @param facts List of facts
     * @return
     */
    public synchronized ExecutionResult execute(String id, List<?> facts) {
        RuleBase ruleBase = getRuleBase(this.packageMap.get(id));
        StatelessSession session = ruleBase.newStatelessSession();
        DroolsWorkingMemoryLogger logger = null;
        
        if(this.logExecution) {
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
        if(this.logExecution) {
        	result.setExecutionLog(logger.getLog().toString());
        }
        
        return result;
    }
}
