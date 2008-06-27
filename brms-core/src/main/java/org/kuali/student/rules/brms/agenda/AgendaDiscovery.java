package org.kuali.student.rules.brms.agenda;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatelessSession;
import org.drools.StatelessSessionResult;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.kuali.student.rules.brms.agenda.entity.Agenda;
import org.kuali.student.rules.brms.agenda.entity.AgendaType;
import org.kuali.student.rules.brms.agenda.entity.Anchor;
import org.kuali.student.rules.brms.agenda.entity.BusinessRule;
import org.kuali.student.rules.brms.agenda.entity.BusinessRuleType;
import org.kuali.student.rules.brms.core.entity.FunctionalBusinessRule;
import org.kuali.student.rules.brms.core.service.FunctionalBusinessRuleManagementService;

public class AgendaDiscovery {

    FunctionalBusinessRuleManagementService ruleMgmtService;
    
    public Agenda getAgenda(AgendaRequest request, Anchor anchor) {
        InputStream is = AgendaDiscovery.class.getResourceAsStream("/agenda.drl");
        StatelessSessionResult result = null;

        try {
            RuleBase ruleBase = loadRuleBase(new InputStreamReader(is));

            // Get the agenda type
            result = executeRule(ruleBase, request);
        } catch (Exception e) {
            throw new RuntimeException("Loading rule base or executing rules failed", e);
        }

        // Retrieve agenda type from rule set based on AgendaRequest
        // Iterate through returned rule engine objects
        // This should not be done in production
        List<BusinessRuleType> ruleTypes = new ArrayList<BusinessRuleType>();
        Iterator it = result.iterateObjects();
        AgendaType agendaType = null;
        while (it != null && it.hasNext()) {
            Object obj = it.next();
            if (obj instanceof AgendaType) {
                agendaType = (AgendaType) obj;
                break;
            }
        }
        // Create actual agenda with actual rules
        Agenda agenda = new Agenda(agendaType.getName(), agendaType);
        ruleTypes = agendaType.getBusinessRuleTypes();
        // retrieveRulesFromDatabase( agenda, anchor );
        List<FunctionalBusinessRule> businessRules = ruleMgmtService.retrieveFunctionalBusinessRules(agenda
                .getAgendaType().getName(), ruleTypes, anchor.getAnchorType().getName(), anchor.getName());

        for (Iterator<FunctionalBusinessRule> iter = businessRules.iterator(); iter.hasNext();) {
            FunctionalBusinessRule businessRule = iter.next();
            // for now, we have BusinessRuleType in Agenda object in case we need BusinessRuleType to determine
            // rules execution sequence when executing rules
            agenda.addBusinessRule(new BusinessRule(
                    businessRule.getCompiledID(), businessRule.getName(), new BusinessRuleType(businessRule
                    .getBusinessRuleType(), businessRule.getBusinessRuleType()), 
                    businessRule.createAdjustedRuleFunctionString()));
        }

        return agenda;
    }

    private RuleBase loadRuleBase(Reader drl) throws Exception {
        Thread currentThread = Thread.currentThread();
        ClassLoader oldClassLoader = currentThread.getContextClassLoader();
        ClassLoader newClassLoader = AgendaDiscovery.class.getClassLoader();

        try {
            currentThread.setContextClassLoader(newClassLoader);

            PackageBuilder builder = new PackageBuilder();
            builder.addPackageFromDrl(drl);
            // Get the compiled package (which is serializable)
            Package pkg = builder.getPackage();
            // Add the package to a rulebase (deploy the rule package).
            RuleBase ruleBase = RuleBaseFactory.newRuleBase();
            ruleBase.addPackage(pkg);

            return ruleBase;
        } finally {
            currentThread.setContextClassLoader(oldClassLoader);
        }
    }

    private StatelessSessionResult executeRule(RuleBase ruleBase, Object fact) throws Exception {
        StatelessSession session = ruleBase.newStatelessSession();
        return session.executeWithResults(fact);
    }

    /**
     * @return the ruleMgmtService
     */
    public FunctionalBusinessRuleManagementService getRuleMgmtService() {
        return ruleMgmtService;
    }

    /**
     * @param ruleMgmtService the ruleMgmtService to set
     */
    public void setRuleMgmtService(FunctionalBusinessRuleManagementService ruleMgmtService) {
        this.ruleMgmtService = ruleMgmtService;
    }


}
