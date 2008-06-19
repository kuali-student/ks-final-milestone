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

import java.util.ArrayList;
import java.util.List;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatelessSession;
import org.drools.StatelessSessionResult;
import org.drools.rule.Package;
import org.kuali.student.rules.brms.agenda.AgendaDiscovery;
import org.kuali.student.rules.brms.agenda.entity.Agenda;
import org.kuali.student.rules.brms.agenda.entity.BusinessRule;
import org.kuali.student.rules.brms.repository.RuleEngineRepository;
import org.kuali.student.rules.ruleexecution.RuleSetExecutor;

public class RuleSetExecutorDroolsImpl implements RuleSetExecutor{

    private RuleEngineRepository ruleEngineRepository;

    public RuleSetExecutorDroolsImpl( RuleEngineRepository ruleEngineRepository ) {
        this.ruleEngineRepository = ruleEngineRepository;
    }
    
    /**
     * Executes an <code>agenda</code> with <code>fact</code>
     * 
     * @see org.kuali.student.rules.ruleexecution.RuleSetExecutor#execute(org.kuali.student.rules.brms.agenda.entity.Agenda, java.lang.Object)
     * @return {@link java.util.Iterator}
     */
    public Object execute( Agenda agenda, List<Object> facts ) {
        RuleBase ruleBase = null;
        List<Package> packageList = new ArrayList<Package>();
        
        for( BusinessRule businessRule : agenda.getBusinessRules() ) {
            Package pkg = (Package) ruleEngineRepository.loadCompiledRuleSet( businessRule.getId() );
            packageList.add( (Package) pkg );
        }
        ruleBase = getRuleBase( packageList );
        
        return executeRule( ruleBase, facts ).iterateObjects();
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
        ClassLoader newClassLoader = AgendaDiscovery.class.getClassLoader();

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
