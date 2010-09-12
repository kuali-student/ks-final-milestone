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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.definition.KnowledgePackage;
import org.kuali.student.brms.ruleexecution.exceptions.RuleSetExecutionException;
import org.kuali.student.common.util.LRUMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Drools RuleBase cache.
 * Default cache is an LRU cache with a maximum of 20 entries.
 */
public class DroolsKnowledgeBase implements java.io.Serializable {
	/** Class serial version uid */
    private static final long serialVersionUID = 1L;

    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(DroolsKnowledgeBase.class);

	/**
	 * Cache of rule base types.
	 */
	private Map<String, KnowledgeBase> ruleBaseTypeCache = new LRUMap<String,KnowledgeBase>();
	
	/**
	 * Constructor
	 */
	public DroolsKnowledgeBase() {
	}

	/**
	 * Sets the rule base cache implementation. 
	 * Default cache is an LRU cache with a maximum of 20 entries.
	 * 
	 * @param cache 
	 */
	public void setCache(Map<String,KnowledgeBase> cache) {
		this.ruleBaseTypeCache = cache;
	}

	/**
	 * Clears the rule base cache.
	 */
	public void clearKnowledgeBase() {
		ruleBaseTypeCache.clear();
	}

	/**
	 * Creates a new rule base for the rule base type.
	 * 
	 * @param ruleBaseType Rule base type to clear
	 */
	public void clearKnowledgeBase(String ruleBaseType) {
		KnowledgeBase knowledgeBase = ruleBaseTypeCache.get(ruleBaseType);
		knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
	}

    /**
     * Returns the set of keys in this cache.
     * 
     * @return Cache key set
     */
	public Set<String> getCacheKeySet() {
    	return ruleBaseTypeCache.keySet();
    }

	/**
	 * Adds a Drools package to a rule base type.
	 * 
	 * @param ruleBaseType Rule base type
	 * @param pkg Drools package
	 * @throws RuleSetExecutionException
	 */
	public void addPackage(String ruleBaseType, KnowledgePackage pkg) throws RuleSetExecutionException {
        if (pkg == null) {
        	throw new RuleSetExecutionException("Cannot add a null Drools KnowledgePackage to the Drools KnowledgeBase.");
        } else if (ruleBaseType == null || ruleBaseType.trim().isEmpty()) {
        	throw new RuleSetExecutionException("Rule base type cannot be null or empty.");
        }
		addPackageToKnowledgeBase(ruleBaseType, pkg);
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

        if(ruleBaseTypeCache.containsKey(ruleBaseType) && containsPackage(ruleBaseType, packageName)) {
    		ruleBaseTypeCache.get(ruleBaseType).removeKnowledgePackage(packageName);
        }
	}
	
    public boolean containsPackage(String ruleBaseType, String ruleSetName) {
    	return (ruleBaseTypeCache.get(ruleBaseType) == null || ruleBaseTypeCache.get(ruleBaseType).getKnowledgePackage(ruleSetName) == null ? false : true);
    }
	/**
	 * Gets a rule base by rule base type.
	 * 
	 * @param ruleBaseType Rule base type
	 * @return A rule base
	 */
	public KnowledgeBase getKnowledgeBase(String ruleBaseType) {
        if (ruleBaseType == null || ruleBaseType.trim().isEmpty()) {
        	throw new RuleSetExecutionException("Rule base type cannot be null or empty.");
        }
		return ruleBaseTypeCache.get(ruleBaseType);
	}

    /**
     * Adds a Drools package to the rule base.
     * 
     * @param packages Packages to add to the rule base
     * @return A rule base
     * @throws RuleSetExecutionException If adding a package to the Drools rule base fails
     */
    private void addPackageToKnowledgeBase(String ruleBaseType, KnowledgePackage pkg) {
        Thread currentThread = Thread.currentThread();
        ClassLoader oldClassLoader = currentThread.getContextClassLoader();
        ClassLoader newClassLoader = DroolsKnowledgeBase.class.getClassLoader();

        try
        {
            currentThread.setContextClassLoader( newClassLoader );
        
            //Add package to knowledgeBase (deploy the rule package).
            KnowledgeBase knowledgeBase = null;
            try {
            	if (!ruleBaseTypeCache.containsKey(ruleBaseType)) {
            		knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
    				ruleBaseTypeCache.put(ruleBaseType, knowledgeBase);
    			} else {
    				knowledgeBase = ruleBaseTypeCache.get(ruleBaseType);
    			}
            	Collection<KnowledgePackage> pkgs = new ArrayList<KnowledgePackage>();
            	pkgs.add(pkg);
            	knowledgeBase.addKnowledgePackages(pkgs);
            } catch(Exception e) {
    			throw new RuleSetExecutionException("Adding knowledge packages to knowledge base failed", e); 
    					//"Adding package to rule base failed: Package name=" + pkg.getName() 
    					//+ ", Error summary=" + pkg.getErrorSummary(), e);
            }
        }
        finally {
            currentThread.setContextClassLoader(oldClassLoader);
        }
    }
}
