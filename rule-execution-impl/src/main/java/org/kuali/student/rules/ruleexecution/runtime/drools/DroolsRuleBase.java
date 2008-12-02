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

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.rule.Package;
import org.kuali.student.rules.ruleexecution.exceptions.RuleSetExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DroolsRuleBase {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(DroolsRuleBase.class);

	/**
	 * Map of rule base types.
	 */
	private final static ConcurrentMap<String,RuleBase> ruleBaseTypeMap = new ConcurrentHashMap<String, RuleBase>();
	
	/**
	 * Constructor
	 */
	public DroolsRuleBase() {
	}

	/**
	 * Clears the rule base cache.
	 */
	public void clearRuleBase() {
		ruleBaseTypeMap.clear();
	}

	/**
	 * Creates a new rule base for the rule base type.
	 * 
	 * @param ruleBaseType Rule base type to clear
	 */
	public void clearRuleBase(String ruleBaseType) {
		RuleBase ruleBase = ruleBaseTypeMap.get(ruleBaseType);
		try {
			ruleBase.lock();
			ruleBase = RuleBaseFactory.newRuleBase();
		} finally {
        	if (ruleBase != null) {
        		ruleBase.unlock();
        	}
		}
	}

    /**
     * Returns the set of keys in this cache.
     * 
     * @return Cache key set
     */
	public Set<String> getCacheKeySet() {
    	return ruleBaseTypeMap.keySet();
    }

	/**
	 * Adds a Drools package to a rule base type.
	 * 
	 * @param ruleBaseType Rule base type
	 * @param pkg Drools package
	 * @throws RuleSetExecutionException
	 */
	public void addPackage(String ruleBaseType, Package pkg) throws RuleSetExecutionException {
        if (pkg == null) {
        	throw new RuleSetExecutionException("Cannot add a null Drools Package to the Drools RuleBase.");
        } else if (ruleBaseType == null || ruleBaseType.trim().isEmpty()) {
        	throw new RuleSetExecutionException("Rule base type cannot be null or empty.");
        }
		addPackageToRuleBase(ruleBaseType, pkg);
	}
	
	/**
	 * Removes a Drools package from a rule base type.
	 * 
	 * @param ruleBaseType Rule base type
	 * @param packageName Package name
	 */
	public void removePackage(String ruleBaseType, String packageName) {
        if (ruleBaseType == null || ruleBaseType.trim().isEmpty()) {
        	throw new RuleSetExecutionException("Rule base type cannot be null or empty.");
        } else if (packageName == null || packageName.trim().isEmpty()) {
            	throw new RuleSetExecutionException("Package name cannot be null or empty");
        }

        if(ruleBaseTypeMap.containsKey(ruleBaseType) && containsPackage(ruleBaseType, packageName)) {
    		ruleBaseTypeMap.get(ruleBaseType).removePackage(packageName);
        }
	}
	
    public boolean containsPackage(String ruleBaseType, String ruleSetName) {
    	return (ruleBaseTypeMap.get(ruleBaseType) == null || ruleBaseTypeMap.get(ruleBaseType).getPackage(ruleSetName) == null ? false : true);
    }
	/**
	 * Gets a rule base by rule base type.
	 * 
	 * @param ruleBaseType Rule base type
	 * @return A rule base
	 */
	public RuleBase getRuleBase(String ruleBaseType) {
        if (ruleBaseType == null || ruleBaseType.trim().isEmpty()) {
        	throw new RuleSetExecutionException("Rule base type cannot be null or empty.");
        }
		return ruleBaseTypeMap.get(ruleBaseType);
	}

    /**
     * Adds a Drools package to the rule base.
     * 
     * @param packages Packages to add to the rule base
     * @return A rule base
     * @throws RuleSetExecutionException If adding a package to the Drools rule base fails
     */
    private void addPackageToRuleBase(String ruleBaseType, Package pkg) {
        Thread currentThread = Thread.currentThread();
        ClassLoader oldClassLoader = currentThread.getContextClassLoader();
        ClassLoader newClassLoader = DroolsRuleBase.class.getClassLoader();

        try
        {
            currentThread.setContextClassLoader( newClassLoader );
        
            //Add package to rulebase (deploy the rule package).
            RuleBase ruleBase = null;
            try {
            	if (!ruleBaseTypeMap.containsKey(ruleBaseType)) {
            		ruleBase = RuleBaseFactory.newRuleBase();
    				ruleBaseTypeMap.put(ruleBaseType, ruleBase);
    			} else {
    				ruleBase = ruleBaseTypeMap.get(ruleBaseType);
    			}
            	ruleBase.lock();
            	ruleBase.addPackage(pkg);
            } catch(Exception e) {
    			throw new RuleSetExecutionException(
    					"Adding package to rule base failed: Package name=" + 
    					pkg.getName() + ", Error summary=" + pkg.getErrorSummary(), e);
            } finally {
            	if (ruleBase != null) {
            		ruleBase.unlock();
            	}
            }
        }
        finally {
            currentThread.setContextClassLoader(oldClassLoader);
        }
    }
}
