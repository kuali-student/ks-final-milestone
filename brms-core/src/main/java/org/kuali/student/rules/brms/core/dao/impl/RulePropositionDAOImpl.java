/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.brms.core.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.kuali.student.rules.brms.core.dao.RulePropositionDAO;
import org.kuali.student.rules.brms.core.entity.RuleProposition;
import org.springframework.stereotype.Repository;

/**
 * Implements DAO interface for operations on Rule Proposition using Spring JPA
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
@Repository
public class RulePropositionDAOImpl implements RulePropositionDAO {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Persists RuleProposition.
     * 
     * @see org.kuali.student.rules.brms.core.dao.RulePropositionDAO#createRuleProposition(RuleProposition ruleProposition)
     */
    public RuleProposition createRuleProposition(RuleProposition ruleProposition) {
        entityManager.persist(ruleProposition);
        return ruleProposition;
    }

    /**
     * Updates RuleProposition.
     * 
     * @see org.kuali.student.rules.brms.core.dao.RulePropositionDAO#updateRuleProposition(RuleProposition ruleProposition)
     */
    public RuleProposition updateRuleProposition(RuleProposition ruleProposition) {
        entityManager.merge(ruleProposition);
        return ruleProposition;
    }

    /**
     * Searches RuleProposition. Returns null if Rule Proposition not found.
     * 
     * @see org.kuali.student.rules.brms.core.dao.RulePropositionDAO#lookupRuleProposition(RuleProposition ruleProposition)
     */
    public RuleProposition lookupRuleProposition(String id) {
        return entityManager.find(RuleProposition.class, id);
    }

    /**
     * Deletes RuleProposition.
     * 
     * @see org.kuali.student.rules.brms.core.dao.RulePropositionDAO#deleteRuleProposition(RuleProposition ruleProposition)
     */
    public boolean deleteRuleProposition(RuleProposition ruleProposition) {
        entityManager.remove(ruleProposition);
        return true; // until I know better what needs to happen
    }
}
