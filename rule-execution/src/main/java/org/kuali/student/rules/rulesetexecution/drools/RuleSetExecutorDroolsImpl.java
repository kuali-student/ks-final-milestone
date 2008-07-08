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
package org.kuali.student.rules.rulesetexecution.drools;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatelessSession;
import org.drools.StatelessSessionResult;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.kuali.student.rules.brms.repository.RuleEngineRepository;
import org.kuali.student.rules.common.agenda.entity.Agenda;
import org.kuali.student.rules.common.agenda.entity.BusinessRuleSet;
import org.kuali.student.rules.rulesetexecution.RuleSetExecutor;
import org.kuali.student.rules.rulesetexecution.RuleSetExecutorInternal;
import org.kuali.student.rules.rulesetexecution.exceptions.RuleSetExecutionException;

public class RuleSetExecutorDroolsImpl implements RuleSetExecutor, RuleSetExecutorInternal {

    private RuleEngineRepository ruleEngineRepository;
    private Map<String, List<Package>> ruleSetMap = new HashMap<String, List<Package>>();

    public RuleSetExecutorDroolsImpl() {
    }
    
    public RuleSetExecutorDroolsImpl( RuleEngineRepository ruleEngineRepository ) {
        this.ruleEngineRepository = ruleEngineRepository;
    }
    
    /**
     * Executes an <code>agenda</code> with <code>fact</code>
     * 
     * @see org.kuali.student.rules.rulesetexecution.RuleSetExecutor#execute(org.kuali.student.rules.common.agenda.entity.Agenda, java.lang.Object)
     * @return {@link java.util.Iterator}
     */
    public Object execute(Agenda agenda, List facts) {
        List<Package> packageList = new ArrayList<Package>();
        
        for( BusinessRuleSet businessRule : agenda.getBusinessRules() ) {
            Package pkg = (Package) ruleEngineRepository.loadCompiledRuleSet( businessRule.getId() );
            packageList.add( (Package) pkg );
        }
        RuleBase ruleBase = getRuleBase( packageList );
        
        return executeRule(ruleBase, facts).iterateObjects();
    }

    /**
     * Executes a list of rule sets.
     * 
     * @param RuleSetId List of rule set ids
     * @param facts List of facts
     * @return
     */
    public Object execute(String ruleSetId, List facts) {
        RuleBase ruleBase = getRuleBase(this.ruleSetMap.get(ruleSetId));
        return executeRule(ruleBase, facts).iterateObjects();
    }

    /**
     * Caches a rule set's source code.
     * 
     * @param ruleSetId Rule set cache id
     * @param source Rule set source code
     */
    public void add(String ruleSetId, Reader source) {
        try {
            if (this.ruleSetMap.containsKey(ruleSetId)) {
                this.ruleSetMap.get(ruleSetId).add(buildPackage(source));
            }
            else {
                List<Package> list = new ArrayList<Package>();
                list.add(buildPackage(source));
                this.ruleSetMap.put(ruleSetId, list);
            }
        } catch(Exception e) {
            throw new RuleSetExecutionException("Building Drools Package failed",e);
        }
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
     * Gets the Drools' rule base.
     * 
     * @param packages Packages to add to the rule base
     * @return A rule base
     */
    private RuleBase getRuleBase( List<Package> packages ) {
        Thread currentThread = Thread.currentThread();
        ClassLoader oldClassLoader = currentThread.getContextClassLoader();
        ClassLoader newClassLoader = RuleSetExecutorDroolsImpl.class.getClassLoader();

        try
        {
            currentThread.setContextClassLoader( newClassLoader );
        
            //Add the package to a rulebase (deploy the rule package).
            RuleBase ruleBase = RuleBaseFactory.newRuleBase();
            try {
                for( Package pkg : packages ) {
                    ruleBase.addPackage(pkg);
                }
            } catch( Exception e ) {
                throw new RuntimeException( "Adding package to rule base failed", e );
            }
            return ruleBase;
        }
        finally
        {
            currentThread.setContextClassLoader( oldClassLoader );
        }
    }

    /**
     * Executes a <code>ruleBase</code> with a <code>fact</code>.
     * 
     * @param ruleBase List of rules
     * @param fact Facts for the <code>ruleBase</code>
     * @return
     */
    private StatelessSessionResult executeRule( RuleBase ruleBase, List<Object> fact ) { 
        StatelessSession session = ruleBase.newStatelessSession();
        return session.executeWithResults( fact );
    }
    
}
