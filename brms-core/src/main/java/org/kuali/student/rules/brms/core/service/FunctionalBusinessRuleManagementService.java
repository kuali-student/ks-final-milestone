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
package org.kuali.student.rules.brms.core.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.kuali.student.rules.brms.core.dao.FunctionalBusinessRuleDAO;
import org.kuali.student.rules.brms.core.entity.FunctionalBusinessRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * An interface to BRMS Meta Data that allows to store and retrieve a business rule
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
@Repository
@Transactional
public class FunctionalBusinessRuleManagementService {

    protected static FunctionalBusinessRuleManagementService _instance = new FunctionalBusinessRuleManagementService();

    protected FunctionalBusinessRuleManagementService() {}

    public static synchronized FunctionalBusinessRuleManagementService getInstance() {
        return _instance;
    }

    @Autowired
    private FunctionalBusinessRuleDAO businessRuleDAO;

    /* public void retrieveCompiledRuleIDs(Agenda agenda, Anchor anchor) {
         AgendaType agendaType = agenda.getAgendaType();
         for (BusinessRuleType ruleType : agendaType.getBusinessRuleTypes()) {
             // find rule based on agenda type, rule type, anchor type and anchor
             FunctionalBusinessRule busRule = businessRuleDAO.lookupCompiledRuleID(agendaType.getName(), ruleType
                     .getName(), anchor.getAnchorType().getName(), anchor.getName());
             agenda.addBusinessRule(new BusinessRule(busRule.getCompiledRuleID(), ruleType));
         }
     } */

    // returns an empty collection if no business rule found
    public List<FunctionalBusinessRule> retrieveFunctionalBusinessRules(String agendaType, List<String> ruleTypes,
            String anchorType, String anchor) {
        List<FunctionalBusinessRule> businessRulesOfSameType = new ArrayList<FunctionalBusinessRule>();
        List<FunctionalBusinessRule> allBusinessRules = new ArrayList<FunctionalBusinessRule>();

        for (Iterator<String> iter = ruleTypes.iterator(); iter.hasNext();) {
            String ruleType = iter.next();

            try {
                businessRulesOfSameType = businessRuleDAO.lookupCompiledIDs(agendaType, ruleType, anchorType, anchor);
                if (businessRulesOfSameType != null) {
                    allBusinessRules.addAll(businessRulesOfSameType);
                }
            } catch (EmptyResultDataAccessException emptySetException) {
                System.out.println("No rules exist for rule type:" + ruleType);
                // continue
            }
        }
        return allBusinessRules;
    }

    /**
     * Retrieves a functional business rule from database based on Rule ID.
     * 
     * @param ruleID
     *            Functional business rule ID.
     * @return Returns functional business rule with Rule ID
     */
    public FunctionalBusinessRule getFunctionalBusinessRule(String ruleID) {

        FunctionalBusinessRule rule = businessRuleDAO.lookupBusinessRuleID(ruleID);

        // force Hibernate to load the RuleElements collection as well
        rule.getRuleElements().size();

        return rule;
    }

    /**
     * @return the businessRuleDAO
     */
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
}
