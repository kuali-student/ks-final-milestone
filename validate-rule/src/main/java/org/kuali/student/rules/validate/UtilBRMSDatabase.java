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

import org.kuali.student.rules.brms.core.dao.FunctionalBusinessRuleDAO;
import org.kuali.student.rules.brms.core.entity.FunctionalBusinessRule;
import org.kuali.student.rules.brms.drools.translator.GenerateRuleSet;
import org.kuali.student.rules.brms.repository.RuleEngineRepository;
import org.kuali.student.rules.brms.repository.rule.RuleSet;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * BRMS utility functions used to populate database and repository with demo data.
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
public class UtilBRMSDatabase {

    public static final String FACT_CONTAINER = "AcademicRecord";

    @Autowired
    private FunctionalBusinessRuleDAO businessRuleDAO;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private RuleEngineRepository droolsRepository;

    /**
     * Compiles 4 demo business rules and inserts them into Drools repository.
     */
    public final void compileDroolsRule() throws Exception {

        GenerateRuleSet grs = GenerateRuleSet.getInstance();

        FunctionalBusinessRule rule1 = businessRuleDAO.lookupBusinessRuleUsingRuleId("1");
        RuleSet rs1 = grs.parse(rule1);
        System.out.println("Rule set1:\n" + rs1.getContent());
        String rulesetUuid1 = droolsRepository.createRuleSet(rs1);
        rule1.setCompiledID(rulesetUuid1);
        droolsRepository.loadRuleSet(rulesetUuid1);
        em.merge(rule1);

        FunctionalBusinessRule rule2 = businessRuleDAO.lookupBusinessRuleUsingRuleId("2");
        RuleSet rs2 = grs.parse(rule2);
        System.out.println("Rule set2:\n" + rs2.getContent());
        String rulesetUuid2 = droolsRepository.createRuleSet(rs2);
        rule2.setCompiledID(rulesetUuid2);
        droolsRepository.loadRuleSet(rulesetUuid2);
        em.merge(rule2);

        FunctionalBusinessRule rule3 = businessRuleDAO.lookupBusinessRuleUsingRuleId("3");
        RuleSet rs3 = grs.parse(rule3);
        System.out.println("Rule set3:\n" + rs3.getContent());
        String rulesetUuid3 = droolsRepository.createRuleSet(rs3);
        rule3.setCompiledID(rulesetUuid3);
        droolsRepository.loadRuleSet(rulesetUuid3);
        em.merge(rule3);

        FunctionalBusinessRule rule4 = businessRuleDAO.lookupBusinessRuleUsingRuleId("4");
        RuleSet rs4 = grs.parse(rule4);
        System.out.println("Rule set4:\n" + rs4.getContent());
        String rulesetUuid4 = droolsRepository.createRuleSet(rs4);
        rule4.setCompiledID(rulesetUuid4);
        droolsRepository.loadRuleSet(rulesetUuid4);
        em.merge(rule4);
    }

    public final FunctionalBusinessRuleDAO getBusinessRuleDAO() {
        return businessRuleDAO;
    }

    /**
     * @param businessRuleDAO
     *            the businessRuleDAO to set
     */
    public final void setBusinessRuleDAO(FunctionalBusinessRuleDAO businessRuleDAO) {
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
        System.out.println("Setting EM");
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
        System.out.println("Setting DRepo");
        this.droolsRepository = droolsRepository;
    }
}
