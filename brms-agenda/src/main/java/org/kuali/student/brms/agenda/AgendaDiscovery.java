package org.kuali.student.brms.agenda;

import java.io.Reader;
import java.util.Collection;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatelessSession;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.kuali.student.brms.agenda.entity.Agenda;
import org.kuali.student.brms.agenda.entity.AgendaType;
import org.kuali.student.brms.agenda.entity.Anchor;
import org.kuali.student.brms.agenda.entity.BusinessRule;
import org.kuali.student.brms.agenda.entity.BusinessRuleType;

public class AgendaDiscovery {

    public Agenda getAgenda( AgendaType agendaType, 
                             BusinessRuleType businessRuleType, 
                             Anchor anchor ) {
        // This is for demo purposes only
        // A decision table rule - should be using a rules engine to do this
        if ( agendaType.getType().equals( "Student Enrolment" ) &&
             businessRuleType.getType().equals( "Pre-Requisite" ) &&
             anchor.getAnchorType().equals( "CLU.course" ) ) {
            
            BusinessRule rule = new BusinessRule("uuid-123-456-789", anchor, businessRuleType);
            Agenda agenda = new Agenda( "Student Enrolment Agenda", agendaType );
            agenda.addBusinessRule( rule );
            return agenda;
        }
        return null;
    }

    public Agenda getAgenda2( AgendaType agendaType, 
                              Anchor anchor ) {
        // This is for demo purposes only
        // A decision table rule - should be using a rules engine to do this
        if ( agendaType.getType().equals( "Student Enrolment" ) &&
             agendaType.getBusinessRuleTypes().contains( "Pre-Requisite" ) &&
             anchor.getAnchorType().equals( "CLU.course" ) ) {
            
            BusinessRuleType ruleType = getPreReqType( agendaType.getBusinessRuleTypes() );
            BusinessRule rule = new BusinessRule("uuid-123-456-789", anchor, ruleType);
            Agenda agenda = new Agenda( "Student Enrolment Agenda", agendaType );
            agenda.addBusinessRule( rule );
            return agenda;
        }
        return null;
    }
    
    private BusinessRuleType getPreReqType( Collection<BusinessRuleType> col ) {
        for( BusinessRuleType type : col ) {
            if ( type.equals( "Pre-Requisite" ) ) {
                return type;
            }
        }
        return null;
    }


    public static RuleBase loadRuleBase( Reader drl ) throws Exception
    {
        // Drools will not work if the current thread's context 
        // class loader is not set
        Thread currentThread = Thread.currentThread();
        ClassLoader oldClassLoader = currentThread.getContextClassLoader();
        ClassLoader newClassLoader = AgendaDiscovery.class.getClassLoader();

        try
        {
            currentThread.setContextClassLoader( newClassLoader );
        
            //PackageBuilderConfiguration cfg = setConfiguration( currentThread );
            
            //Use package builder to build up a rule package.
            //An alternative lower level class called "DrlParser" can also be used...
            //PackageBuilder builder = new PackageBuilder(cfg);
            PackageBuilder builder = new PackageBuilder();

            
            //PackageBuilder builder = new PackageBuilder();
            builder.addPackageFromDrl(drl);
      
            //Get the compiled package (which is serializable)
            Package pkg = builder.getPackage();
      
            //Add the package to a rulebase (deploy the rule package).
            RuleBase ruleBase = RuleBaseFactory.newRuleBase();
            ruleBase.addPackage(pkg);
      
            return ruleBase;
        }
        finally
        {
            currentThread.setContextClassLoader( oldClassLoader );
        }
    }

    /**
     * Executes a Drools package (rule set).
     * 
     * @param pkg Drools package (rule set)
     * @param fact Facts to assert
     * @throws Exception
     */
    public void executeRule( org.drools.rule.Package pkg, Object[] fact )
        throws Exception
    {
        RuleBase rb = RuleBaseFactory.newRuleBase();
        rb.addPackage( pkg );
        StatelessSession sess = rb.newStatelessSession();
        sess.execute( fact );
    }
}
