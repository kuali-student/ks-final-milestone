package org.kuali.student.ruleexecution.drools;

import java.util.ArrayList;
import java.util.List;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatelessSession;
import org.drools.StatelessSessionResult;
import org.drools.rule.Package;
import org.kuali.student.brms.agenda.AgendaDiscovery;
import org.kuali.student.brms.agenda.entity.Agenda;
import org.kuali.student.ruleexecution.RuleSetExecutor;

public class RuleSetExecutorDroolsImpl implements RuleSetExecutor{

    public RuleSetExecutorDroolsImpl() {
    }
    
    public Object execute( Agenda agenda, Object ruleSet, Object fact ) {
        RuleBase ruleBase = null;
        try {
            List<Package> packageList = new ArrayList<Package>();
            packageList.add( (Package) ruleSet );
            /*for( BusinessRule rule : agenda.getBusinessRules() ) {
                Package pkg = (Package) ruleSetCache.getObject( rule.getId() );
                if ( pkg == null ) {
                    throw new RuntimeException( "org.drools.rule.Package not found" );
                }
                packageList.add( pkg );
            }*/
    
            ruleBase = getRuleBase( packageList );
        } catch( Exception e ) {
            throw new RuntimeException( "Loading rule base failed", e );
        }
        
        try {
            return executeRule( ruleBase, fact ).iterateObjects();
        } catch( Exception e ) {
            throw new RuntimeException( "Executing rule failed", e );
        }
    }

    private RuleBase getRuleBase( List<Package> packages ) throws Exception {
        Thread currentThread = Thread.currentThread();
        ClassLoader oldClassLoader = currentThread.getContextClassLoader();
        ClassLoader newClassLoader = AgendaDiscovery.class.getClassLoader();

        try
        {
            currentThread.setContextClassLoader( newClassLoader );
        
            //Add the package to a rulebase (deploy the rule package).
            RuleBase ruleBase = RuleBaseFactory.newRuleBase();
            for( Package pkg : packages ) {
                ruleBase.addPackage(pkg);
            }
      
            return ruleBase;
        }
        finally
        {
            currentThread.setContextClassLoader( oldClassLoader );
        }
    }

    private StatelessSessionResult executeRule( RuleBase ruleBase, Object fact ) throws Exception {
        StatelessSession session = ruleBase.newStatelessSession();
        return session.executeWithResults( fact );
    }
}
