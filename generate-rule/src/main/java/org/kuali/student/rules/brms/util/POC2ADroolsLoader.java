package org.kuali.student.rules.brms.util;

import java.util.List;

import org.kuali.student.rules.brms.core.entity.FunctionalBusinessRule;
import org.kuali.student.rules.brms.core.entity.FunctionalBusinessRuleContainer;
import org.kuali.student.rules.brms.core.service.FunctionalBusinessRuleManagementService;
import org.kuali.student.rules.brms.drools.translator.GenerateRuleSet;
import org.kuali.student.rules.brms.repository.RuleEngineRepository;
import org.kuali.student.rules.brms.repository.rule.RuleSet;

public class POC2ADroolsLoader {

    private FunctionalBusinessRuleManagementService brmsService;
    
    private RuleEngineRepository droolsRepository;

    /**
     * @return the brmsService
     */
    public FunctionalBusinessRuleManagementService getBrmsService() {
        return brmsService;
    }

    /**
     * @param brmsService the brmsService to set
     */
    public void setBrmsService(FunctionalBusinessRuleManagementService brmsService) {
        this.brmsService = brmsService;
    }

    /**
     * @return the droolsRepository
     */
    public RuleEngineRepository getDroolsRepository() {
        return droolsRepository;
    }

    /**
     * @param droolsRepository the droolsRepository to set
     */
    public void setDroolsRepository(RuleEngineRepository droolsRepository) {
        this.droolsRepository = droolsRepository;
    }

    public void init() throws Exception {
        GenerateRuleSet grs = GenerateRuleSet.getInstance();

        FunctionalBusinessRuleContainer container = new FunctionalBusinessRuleContainer("course.co.req", "Cource Co-Requisites");
        FunctionalBusinessRule rule1 = brmsService.getBusinessRuleUsingRuleId("1");
        FunctionalBusinessRule rule2 = brmsService.getBusinessRuleUsingRuleId("2");
        FunctionalBusinessRule rule3 = brmsService.getBusinessRuleUsingRuleId("3");
        FunctionalBusinessRule rule4 = brmsService.getBusinessRuleUsingRuleId("4");

        container.addFunctionalBusinessRule(rule1);
        container.addFunctionalBusinessRule(rule2);
        container.addFunctionalBusinessRule(rule3);
        container.addFunctionalBusinessRule(rule4);

        RuleSet ruleSet = grs.parse(container);
       
        System.out.println("Rule set1:\n" + ruleSet.getContent());

        String rulesetUuid = droolsRepository.createRuleSet(ruleSet);
        droolsRepository.loadRuleSet(rulesetUuid);
        
        rule1.setCompiledID(rulesetUuid);
        brmsService.getBusinessRuleDAO().updateBusinessRule(rule1);            

        rule2.setCompiledID(rulesetUuid);
        brmsService.getBusinessRuleDAO().updateBusinessRule(rule2);            

        rule3.setCompiledID(rulesetUuid);
        brmsService.getBusinessRuleDAO().updateBusinessRule(rule3);            

        rule4.setCompiledID(rulesetUuid);
        brmsService.getBusinessRuleDAO().updateBusinessRule(rule4);            

        
        /*FunctionalBusinessRule rule1 = brmsService.getBusinessRuleUsingId("1");
        RuleSet rs1 = grs.parse(rule1);
        System.out.println("Rule set1:\n" + rs1.getContent());
        String rulesetUuid1 = droolsRepository.createRuleSet(rs1);
        rule1.setCompiledID(rulesetUuid1);
        droolsRepository.loadRuleSet(rulesetUuid1);
        brmsService.getBusinessRuleDAO().updateBusinessRule(rule1);            

        FunctionalBusinessRule rule2 = brmsService.getBusinessRuleUsingId("2");
        RuleSet rs2 = grs.parse(rule2);
        System.out.println("Rule set2:\n" + rs2.getContent());
        String rulesetUuid2 = droolsRepository.createRuleSet(rs2);
        rule2.setCompiledID(rulesetUuid2);
        droolsRepository.loadRuleSet(rulesetUuid2);
        brmsService.getBusinessRuleDAO().updateBusinessRule(rule2);

        FunctionalBusinessRule rule3 = brmsService.getBusinessRuleUsingId("3");
        RuleSet rs3 = grs.parse(rule3);
        System.out.println("Rule set3:\n" + rs3.getContent());
        String rulesetUuid3 = droolsRepository.createRuleSet(rs3);
        rule3.setCompiledID(rulesetUuid3);
        droolsRepository.loadRuleSet(rulesetUuid3);
        brmsService.getBusinessRuleDAO().updateBusinessRule(rule3);

        FunctionalBusinessRule rule4 = brmsService.getBusinessRuleUsingId("4");
        RuleSet rs4 = grs.parse(rule4);
        System.out.println("Rule set4:\n" + rs4.getContent());
        String rulesetUuid4 = droolsRepository.createRuleSet(rs4);
        rule4.setCompiledID(rulesetUuid4);
        droolsRepository.loadRuleSet(rulesetUuid4);
        brmsService.getBusinessRuleDAO().updateBusinessRule(rule4);*/
    }    
}
