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
package org.kuali.student.rules.ruleexecution.drools;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatelessSession;
import org.drools.StatelessSessionResult;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.kuali.student.rules.repository.RuleEngineRepository;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.ruleexecution.RuleSetExecutor;
import org.kuali.student.rules.ruleexecution.RuleSetExecutorInternal;
import org.kuali.student.rules.ruleexecution.exceptions.RuleSetExecutionException;
import org.kuali.student.rules.ruleexecution.runtime.ast.GenerateRuleReport;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RuntimeAgendaDTO;
import org.kuali.student.rules.util.FactContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleSetExecutorDroolsImpl implements RuleSetExecutor, RuleSetExecutorInternal {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(RuleSetExecutorDroolsImpl.class);
            
    private RuleEngineRepository ruleEngineRepository;
    private ConcurrentMap<String, List<Package>> ruleSetMap = new ConcurrentHashMap<String, List<Package>>();
    
    /**
     * Constructs a new rule set executor without a repository.
     */
    public RuleSetExecutorDroolsImpl() {
    }

    /**
     * Constructs a new rule set executor with a specific repository.
     * 
     * @param ruleEngineRepository Repository to load rule sets from
     */
    public RuleSetExecutorDroolsImpl( RuleEngineRepository ruleEngineRepository ) {
        this.ruleEngineRepository = ruleEngineRepository;
    }
    
    /**
     * Gets the rule engine repository.
     * 
     * @return Rule engine repository
     */
    public RuleEngineRepository getRuleEngineRepository() {
    	return this.ruleEngineRepository;
    }

    /**
     * Executes an <code>agenda</code> with <code>facts</code>.
     * 
     * @see org.kuali.student.rules.ruleexecution.RuleSetExecutor#execute(org.kuali.student.rules.internal.common.agenda.entity.Agenda, java.util.List)
     */
    public synchronized Object execute(RuntimeAgendaDTO agenda, List<?> facts) {
    	List<Package> packageList = new ArrayList<Package>();
        
        logger.info("Executing agenda: businessRules="+agenda.getBusinessRules());
        //for(BusinessRuleSet businessRuleSet : agenda.getBusinessRules()) {
        for(BusinessRuleInfoDTO businessRule : agenda.getBusinessRules()) {
            logger.info("Loading compiled rule set: businessRuleSet.id="+businessRule.getCompiledId());
            Package pkg = loadCompiledRuleSet(businessRule.getCompiledId());
            packageList.add((Package) pkg);
            if (logger.isDebugEnabled()) {
                RuleSet rs = this.ruleEngineRepository.loadRuleSet(businessRule.getCompiledId());
                logger.debug("\n\n**************************************************");
                logger.debug("uuid="+rs.getUUID());
                logger.debug("name="+rs.getName());
                logger.debug("\n\n-------------------------");
                logger.debug("\n\n"+rs.getContent());
                logger.debug("\n\n**************************************************");
            }
        }
        RuleBase ruleBase = getRuleBase(packageList);
        Iterator<?> iterator = executeRule(ruleBase, facts).iterateObjects();
        generateReport(facts);
        return iterator;
    }

    /**
     * Executes a production snapshot <code>agenda</code> with <code>facts</code>.
     * 
     * @see org.kuali.student.rules.ruleexecution.RuleSetExecutor#executeSnapshot(org.kuali.student.rules.internal.common.agenda.entity.Agenda, java.util.List)
     */
    public synchronized Object executeSnapshot(RuntimeAgendaDTO agenda, List<?> facts) {
        throw new RuleSetExecutionException("Method not yet implemented");
    }

    /**
     * Loads a compiled rule set by <code>uuid</code>
     * 
     * @param uuid Rule set UUID 
     * @return A Drools package
     */
    private Package loadCompiledRuleSet(String uuid) {
        return (Package) this.ruleEngineRepository.loadCompiledRuleSet(uuid);
    }

    /**
     * Loads a compiled rule set snapshot by <code>uuid</code>
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Rule set's snapshot name
     * @return A Drools package
     */
    private Package loadCompiledRuleSetSnapshot(String ruleSetName, String snapshotName) {
        return (Package) this.ruleEngineRepository.loadCompiledRuleSetSnapshot(ruleSetName, snapshotName);
    }
    
    private void generateReport(List<?> facts) {
        GenerateRuleReport ruleReportBuilder = new GenerateRuleReport(this);
        for(int i=0; i<facts.size(); i++) {
            Object obj = facts.get(i);
            if (obj instanceof FactContainer) {
                FactContainer fc = (FactContainer) obj;
                ruleReportBuilder.execute(fc.getPropositionContainer());
           }
        }
    }

    /**
     * Executes a list of rule sets.
     * 
     * @param RuleSetId List of rule set ids
     * @param facts List of facts
     * @return
     */
    public synchronized Object execute(String ruleSetId, List<?> facts) {
        RuleBase ruleBase = getRuleBase(this.ruleSetMap.get(ruleSetId));
        return executeRule(ruleBase, facts).iterateObjects();
    }

    /**
     * Caches a rule set's source code by <code>ruleSetId</code>.
     * 
     * @param ruleSetId Rule set cache id
     * @param source Rule set source code
     */
    public void addRuleSet(String ruleSetId, Reader source) {
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
     * Removes a rule set form the cache by <code>ruleSetId</code>.
     * 
     * @see org.kuali.student.rules.ruleexecution.RuleSetExecutorInternal#removeRuleSet(java.lang.String)
     */
    public boolean removeRuleSet(String ruleSetId) {
        this.ruleSetMap.remove(ruleSetId);
        return !this.ruleSetMap.containsKey(ruleSetId);
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
     * Executes a <code>ruleBase</code> with a <code>fact</code>.
     * 
     * @param ruleBase List of rules
     * @param fact Facts for the <code>ruleBase</code>
     * @return A stateless session result
     */
    private StatelessSessionResult executeRule( RuleBase ruleBase, List<?> fact ) { 
        StatelessSession session = ruleBase.newStatelessSession();
        return session.executeWithResults( fact );
    }

}
