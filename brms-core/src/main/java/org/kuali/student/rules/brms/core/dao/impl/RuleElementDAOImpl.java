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

import org.kuali.student.rules.brms.core.dao.RuleElementDAO;
import org.kuali.student.rules.common.entity.RuleElement;
import org.springframework.stereotype.Repository;

/**
 * Implements DAO interface for operations on Rule Element using Spring JPA
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
@Repository
public class RuleElementDAOImpl implements RuleElementDAO {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Persists RuleElement in database.
     * 
     * @see org.kuali.student.rules.brms.core.dao.RuleElementDAO#createRuleElement(RuleElement ruleElement)
     */
    public RuleElement createRuleElement(RuleElement ruleElement) {
        entityManager.persist(ruleElement);
        return ruleElement;
    }

    /**
     * Updates RuleElement in database.
     * 
     * @see org.kuali.student.rules.brms.core.dao.RuleElementDAO#updateRuleElement(RuleElement ruleElement)
     */
    public RuleElement updateRuleElement(RuleElement ruleElement) {
        entityManager.merge(ruleElement);
        return ruleElement;
    }

    /**
     * Finds RuleElement in database.
     * 
     * @see org.kuali.student.rules.brms.core.dao.RuleElementDAO#lookupRuleElement(RuleElement ruleElement)
     */
    public RuleElement lookupRuleElement(String id) {
        return entityManager.find(RuleElement.class, id);
    }

    /**
     * Deletes RuleElement from database.
     * 
     * @see org.kuali.student.rules.brms.core.dao.RuleElementDAO#deleteRuleElement(RuleElement ruleElement)
     */
    public boolean deleteRuleElement(RuleElement ruleElement) {
        entityManager.remove(ruleElement);
        return true; // until I know better what needs to happen
    }
}
