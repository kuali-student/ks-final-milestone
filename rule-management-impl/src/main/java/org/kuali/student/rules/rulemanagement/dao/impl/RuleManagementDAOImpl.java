/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.rulemanagement.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.rules.rulemanagement.dao.RuleManagementDAO;
import org.kuali.student.rules.rulemanagement.entity.AgendaInfo;
import org.kuali.student.rules.rulemanagement.entity.BusinessRule;
import org.springframework.stereotype.Repository;

/**
 * Implements DAO interface for operations on Functional Business Rule entity using Spring JPA
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@Repository
public class RuleManagementDAOImpl implements RuleManagementDAO {

    private EntityManager entityManager;

    @PersistenceContext(name="RulesManagement")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    /**
     * 
     * This method persists AgendaInfo in database.
     * 
     * @param agenda
     * @return
     */
    public AgendaInfo createAgendaInfo(AgendaInfo agenda) {
        entityManager.persist(agenda);
        return agenda;
    }

    
    /**
     * 
     * This method updates AgendaInfo in database.
     * 
     * @param agenda
     * @return
     */
    public AgendaInfo updateAgendaInfo(AgendaInfo agenda) {
        entityManager.merge(agenda);
        return agenda;
    }
    
    /**
     * 
     * This method deletes AgendaInfo from database
     * 
     * @param agenda
     * @return
     */
    public boolean deleteAgendaInfo(AgendaInfo agenda) {
        entityManager.remove(agenda);
        return true;
    }
    
    /**
     * Persists BusinessRule in database.
     * 
     * @see org.kuali.student.rules.brms.core.dao.FunctionalBusinessRuleDAO#createBusinessRule(BusinessRule rule)
     */
    public BusinessRule createBusinessRule(BusinessRule rule) {
        entityManager.persist(rule);
        return rule;
    }

    /**
     * Updates BusinessRule in database.
     * 
     * @see org.kuali.student.rules.brms.core.dao.FunctionalBusinessRuleDAO#updateBusinessRule(BusinessRule rule)
     */
    public BusinessRule updateBusinessRule(BusinessRule rule) {
        entityManager.merge(rule);
        return rule;
    }

    /**
     * Deletes BusinessRule from database.
     * 
     * @see org.kuali.student.rules.brms.core.dao.FunctionalBusinessRuleDAO#deleteBusinessRule(BusinessRule rule)
     */
    public boolean deleteBusinessRule(BusinessRule rule) {
        entityManager.remove(rule);
        return true;
    }

    /**
     * Deletes BusinessRule from database.
     * 
     * @see org.kuali.student.rules.brms.core.dao.FunctionalBusinessRuleDAO#deleteBusinessRule(BusinessRule rule)
     */
    public boolean deleteBusinessRule(String id) {
        entityManager.remove(lookupBusinessRuleUsingId(id));
        return true;
    }

    /**
     * Finds BusinessRule in database.
     * 
     * @see org.kuali.student.rules.brms.core.dao.FunctionalBusinessRuleDAO#lookupBusinessRuleUsingId(String id)
     */
    public BusinessRule lookupBusinessRuleUsingId(String id) {
        return entityManager.find(BusinessRule.class, id);
    }

    /**
     * Finds BusinessRule in database.
     * 
     * @see org.kuali.student.rules.brms.core.dao.FunctionalBusinessRuleDAO#lookupBusinessRuleUsingId(String ruleIdentifier)
     */
    @SuppressWarnings(value = {"unchecked"})
    public BusinessRule lookupBusinessRuleUsingRuleId(String ruleIdentifier) {
        Query query = entityManager.createNamedQuery("BusinessRule.findByRuleID");
        query.setParameter("ruleID", ruleIdentifier);
        BusinessRule functionalBusinessRule = (BusinessRule) query.getSingleResult();
        return functionalBusinessRule;
    }

    /**
     * Finds one or more BusinessRule in database based on given parameters
     * 
     * @see org.kuali.student.rules.brms.core.dao.FunctionalBusinessRuleDAO#lookupCompiledRuleIDs(String ruleIdentifier)
     */
    @SuppressWarnings(value = {"unchecked"})
    public List<BusinessRule> lookupCompiledIDs(String businessRuleTypeKey, String anchor) {
        Query query = entityManager.createNamedQuery("BusinessRule.findByAgendaType");
        query.setParameter("businessRuleType", businessRuleTypeKey);
        query.setParameter("anchor", anchor);
        List<BusinessRule> functionalBusinessRule = query.getResultList();
        return functionalBusinessRule;
    }
}
