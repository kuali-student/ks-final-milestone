/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.validate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.kuali.student.rules.brms.core.dao.BusinessRuleDAO;
import org.kuali.student.rules.brms.repository.RuleEngineRepository;
import org.kuali.student.rules.brms.repository.rule.RuleSet;
import org.kuali.student.rules.brms.translators.drools.GenerateRuleSet;
import org.kuali.student.rules.internal.common.entity.BusinessRule;
import org.kuali.student.rules.internal.common.entity.BusinessRuleContainer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * BRMS utility functions used to populate database and repository with demo data.
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
public class UtilBRMSDatabase {

    public static final String FACT_CONTAINER = "AcademicRecord";

    @Autowired
    private BusinessRuleDAO businessRuleDAO;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private RuleEngineRepository droolsRepository;

    /**
     * Compiles 4 demo business rules and inserts them into Drools repository.
     */
    /*public final void compileDroolsRule() throws Exception {

        GenerateRuleSet grs = GenerateRuleSet.getInstance();

        BusinessRule rule1 = businessRuleDAO.lookupBusinessRuleUsingIdentifier("1");
        RuleSet rs1 = grs.parse(rule1);
        System.out.println("Rule set1:\n" + rs1.getContent());
        String rulesetUuid1 = droolsRepository.createRuleSet(rs1);
        rule1.setCompiledID(rulesetUuid1);
        droolsRepository.loadRuleSet(rulesetUuid1);
        em.merge(rule1);

        BusinessRule rule2 = businessRuleDAO.lookupBusinessRuleUsingIdentifier("2");
        RuleSet rs2 = grs.parse(rule2);
        System.out.println("Rule set2:\n" + rs2.getContent());
        String rulesetUuid2 = droolsRepository.createRuleSet(rs2);
        rule2.setCompiledID(rulesetUuid2);
        droolsRepository.loadRuleSet(rulesetUuid2);
        em.merge(rule2);

        BusinessRule rule3 = businessRuleDAO.lookupBusinessRuleUsingIdentifier("3");
        RuleSet rs3 = grs.parse(rule3);
        System.out.println("Rule set3:\n" + rs3.getContent());
        String rulesetUuid3 = droolsRepository.createRuleSet(rs3);
        rule3.setCompiledID(rulesetUuid3);
        droolsRepository.loadRuleSet(rulesetUuid3);
        em.merge(rule3);

        BusinessRule rule4 = businessRuleDAO.lookupBusinessRuleUsingIdentifier("4");
        RuleSet rs4 = grs.parse(rule4);
        System.out.println("Rule set4:\n" + rs4.getContent());
        String rulesetUuid4 = droolsRepository.createRuleSet(rs4);
        rule4.setCompiledID(rulesetUuid4);
        droolsRepository.loadRuleSet(rulesetUuid4);
        em.merge(rule4);
    }*/

    public final void compileDroolsRule() throws Exception {

        GenerateRuleSet grs = GenerateRuleSet.getInstance();

        BusinessRuleContainer container = new BusinessRuleContainer("course.co.req", "Cource Co-Requisites");
        BusinessRule rule1 = businessRuleDAO.lookupBusinessRuleUsingIdentifier("1");
        BusinessRule rule2 = businessRuleDAO.lookupBusinessRuleUsingIdentifier("2");
        BusinessRule rule3 = businessRuleDAO.lookupBusinessRuleUsingIdentifier("3");
        BusinessRule rule4 = businessRuleDAO.lookupBusinessRuleUsingIdentifier("4");

        container.addBusinessRule(rule1);
        container.addBusinessRule(rule2);
        container.addBusinessRule(rule3);
        container.addBusinessRule(rule4);

        RuleSet ruleSet = grs.parse(container);

        // System.out.println("Rule set1:\n" + ruleSet.getContent());

        String rulesetUuid = droolsRepository.createRuleSet(ruleSet).getUUID();
        droolsRepository.loadRuleSet(rulesetUuid);

        rule1.setCompiledID(rulesetUuid);
        em.merge(rule1);

        rule2.setCompiledID(rulesetUuid);
        em.merge(rule2);

        rule3.setCompiledID(rulesetUuid);
        em.merge(rule3);

        rule4.setCompiledID(rulesetUuid);
        em.merge(rule4);
    }

    public final BusinessRuleDAO getBusinessRuleDAO() {
        return businessRuleDAO;
    }

    /**
     * @param businessRuleDAO
     *            the businessRuleDAO to set
     */
    public final void setBusinessRuleDAO(BusinessRuleDAO businessRuleDAO) {
        this.businessRuleDAO = businessRuleDAO;
    }

    /**
     * @return the em
     */
    public final EntityManager getEm() {
        return em;
    }

    /**
     * @param em
     *            the em to set
     */
    public final void setEm(EntityManager em) {
        this.em = em;
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
}
