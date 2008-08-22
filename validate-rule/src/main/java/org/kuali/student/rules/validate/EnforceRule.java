package org.kuali.student.rules.validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatelessSession;
import org.kuali.student.rules.brms.agenda.AgendaDiscovery;
import org.kuali.student.rules.brms.agenda.AgendaRequest;
import org.kuali.student.rules.brms.agenda.entity.Agenda;
import org.kuali.student.rules.brms.agenda.entity.Anchor;
import org.kuali.student.rules.brms.agenda.entity.AnchorType;
import org.kuali.student.rules.brms.agenda.entity.BusinessRule;
import org.kuali.student.rules.brms.repository.RuleEngineRepository;
import org.kuali.student.rules.common.util.CourseEnrollmentRequest;
import org.kuali.student.rules.runtime.ast.GenerateRuleReport;
import org.kuali.student.rules.statement.PropositionContainer;
import org.kuali.student.rules.util.FactContainer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class is responsibile for extracting rule from the BRMS, Generating the required constraints and excecuting the rules
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
public class EnforceRule {

    @Autowired
    AgendaDiscovery agendaDiscovery;
        
    @Autowired
    private RuleEngineRepository droolsRepository;

    public ValidationResult validateLuiPersonRelation(String personID, String luiID, String luiPersonRelationType,
            String relationState) {
        
        GenerateRuleReport ruleReportBuilder = new GenerateRuleReport();
        ValidationResult result = new ValidationResult();

        //1. Discover Agenda
        AgendaRequest agendaRequest = new AgendaRequest( luiPersonRelationType, "course", "offered", relationState );
        AnchorType type = new AnchorType( "course", "clu.type.course" );
        Anchor anchor = new Anchor( luiID.replaceAll("\\s", "_"), luiID, type );
        
        Agenda agenda = agendaDiscovery.getAgenda( agendaRequest, anchor );
        
        Iterator<BusinessRule> itr = agenda.getBusinessRules().iterator();
        
        while(itr.hasNext()) {
            BusinessRule rule = itr.next();
            String ruleID = rule.getId();
            
            System.out.println("\n\n");

            // 2. Extract compiled rule from drools repository
            org.drools.rule.Package binPkg = (org.drools.rule.Package) droolsRepository.loadCompiledRuleSet(ruleID);
            //List<Object> factList = new ArrayList<Object>();

            // 3. Inject facts
            PropositionContainer props =  new PropositionContainer();
            props.setFunctionalRuleString(rule.getFunctionalRuleString());
            CourseEnrollmentRequest request = new CourseEnrollmentRequest();
            request.setLuiIds(parseList("CPR 106,CPR 110,CPR 201,Math 105,Art 55"));

            //factList.add(props);
            //factList.add(request);
            FactContainer factContainer = new FactContainer(rule.getBusinessRuleName(), request, props);

            // 4. Execute the compiled rule
            try {
                RuleBase rb = RuleBaseFactory.newRuleBase();
                rb.addPackage(binPkg);
                StatelessSession sess = rb.newStatelessSession();
                //sess.execute(factList.toArray());
                sess.execute(factContainer);

            } catch (Exception e) {
                System.out.println("Exception while executing rule:" + rule.getId());
                e.printStackTrace(System.out);
                // Eating exception here. BAD BAD Code!
            }

            ruleReportBuilder.executeRule(props);
            
            // 5. Process rule outcome
            if (props.getRuleResult()) {
                result.setSuccess(true);
                result.setRuleMessage(props.getRuleReport().getSuccessMessage());
                System.out.println(props.getRuleReport().getSuccessMessage());
            } else {
                result.setRuleMessage(props.getRuleReport().getFailureMessage());
                System.out.println(props.getRuleReport().getFailureMessage());
            }

            
            // In POC we only process one rule
            break;
        }
        

        System.out.println("\n\n");

        return result;
    }

    /**
     * This method generates a Set of String from a comma separated list of string elements
     * 
     * @param list
     * @return
     */
    private Set<String> parseList(String list) {
        return new HashSet<String>(Arrays.asList(list.split(",")));
    }

    /**
     * @return the droolsRepository
     */
    public RuleEngineRepository getDroolsRepository() {
        return droolsRepository;
    }

    /**
     * @param droolsRepository
     *            the droolsRepository to set
     */
    public void setDroolsRepository(RuleEngineRepository droolsRepository) {
        this.droolsRepository = droolsRepository;
    }

    /**
     * @return the agendaDiscovery
     */
    public AgendaDiscovery getAgendaDiscovery() {
        return agendaDiscovery;
    }

    /**
     * @param agendaDiscovery the agendaDiscovery to set
     */
    public void setAgendaDiscovery(AgendaDiscovery agendaDiscovery) {
        this.agendaDiscovery = agendaDiscovery;
    }

 }
