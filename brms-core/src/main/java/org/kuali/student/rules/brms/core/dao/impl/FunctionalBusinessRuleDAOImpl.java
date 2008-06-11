/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.brms.core.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.rules.brms.core.dao.FunctionalBusinessRuleDAO;
import org.kuali.student.rules.brms.core.entity.FunctionalBusinessRule;
import org.springframework.stereotype.Repository;

/**
 * Implements DAO interface for operations on Functional Business Rule entity using Spring JPA
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
@Repository
public class FunctionalBusinessRuleDAOImpl implements FunctionalBusinessRuleDAO {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Persists FunctionalBusinessRule in database.
     * 
     * @see org.kuali.student.rules.brms.core.dao.FunctionalBusinessRuleDAO#createBusinessRule(FunctionalBusinessRule rule)
     */
    public FunctionalBusinessRule createBusinessRule(FunctionalBusinessRule rule) {
        entityManager.persist(rule);
        return rule;
    }

    /**
     * Updates FunctionalBusinessRule in database.
     * 
     * @see org.kuali.student.rules.brms.core.dao.FunctionalBusinessRuleDAO#updateBusinessRule(FunctionalBusinessRule rule)
     */
    public FunctionalBusinessRule updateBusinessRule(FunctionalBusinessRule rule) {
        entityManager.merge(rule);
        return rule;
    }

    /**
     * Deletes FunctionalBusinessRule from database.
     * 
     * @see org.kuali.student.rules.brms.core.dao.FunctionalBusinessRuleDAO#deleteBusinessRule(FunctionalBusinessRule rule)
     */
    public boolean deleteBusinessRule(FunctionalBusinessRule ruleSet) {
        entityManager.remove(ruleSet);
        return true; // until I know better what needs to happen
    }

    /**
     * Finds FunctionalBusinessRule in database.
     * 
     * @see org.kuali.student.rules.brms.core.dao.FunctionalBusinessRuleDAO#lookupBusinessRule(String id)
     */
    public FunctionalBusinessRule lookupBusinessRule(String id) {
        return entityManager.find(FunctionalBusinessRule.class, id);
    }

    /**
     * Finds FunctionalBusinessRule in database.
     * 
     * @see org.kuali.student.rules.brms.core.dao.FunctionalBusinessRuleDAO#lookupBusinessRule(String ruleIdentifier)
     */
    @SuppressWarnings(value = {"unchecked"})
    public FunctionalBusinessRule lookupBusinessRuleID(String ruleIdentifier) {

        Query query = entityManager.createNamedQuery("FunctionalBusinessRule.findByRuleID");
        query.setParameter("ruleID", ruleIdentifier);
        FunctionalBusinessRule functionalBusinessRule = (FunctionalBusinessRule) query.getSingleResult();
        return functionalBusinessRule;
    }

    /**
     * Finds one or more FunctionalBusinessRule in database based on given parameters
     * 
     * @see org.kuali.student.rules.brms.core.dao.FunctionalBusinessRuleDAO#lookupCompiledRuleIDs(String ruleIdentifier)
     */
    @SuppressWarnings(value = {"unchecked"})
    public List<FunctionalBusinessRule> lookupCompiledRuleIDs(String agendaType, String businessRuleType,
            String anchorType, String anchor) {
        Query query = entityManager.createNamedQuery("FunctionalBusinessRule.findByAgendaType");
        query.setParameter("agendaType", agendaType);
        query.setParameter("businessRuleType", businessRuleType);
        query.setParameter("anchorType", anchorType);
        query.setParameter("anchor", anchor);
        List<FunctionalBusinessRule> functionalBusinessRule = query.getResultList();
        return functionalBusinessRule;
    }

    /**
     * @return the entityManager
     */
    public EntityManager getEm() {
        return entityManager;
    }

    /**
     * @param em
     *            the entityManager to set
     */
    public void setEm(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
