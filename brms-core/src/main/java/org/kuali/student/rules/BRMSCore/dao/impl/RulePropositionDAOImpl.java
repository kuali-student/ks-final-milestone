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
package org.kuali.student.rules.BRMSCore.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.kuali.student.rules.BRMSCore.dao.RulePropositionDAO;
import org.kuali.student.rules.BRMSCore.entity.RuleProposition;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * Implements DAO interface for operations on Rule Proposition using Spring JPA
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
@Repository
public class RulePropositionDAOImpl extends JpaDaoSupport implements RulePropositionDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * Persists RuleProposition.
     * 
     * @see org.kuali.student.rules.BRMSCore.dao.RulePropositionDAO#createRuleProposition(RuleProposition ruleProposition)
     */
    public RuleProposition createRuleProposition(RuleProposition ruleProposition) {
        em.persist(ruleProposition);
        return ruleProposition;
    }

    /**
     * Updates RuleProposition.
     * 
     * @see org.kuali.student.rules.BRMSCore.dao.RulePropositionDAO#updateRuleProposition(RuleProposition ruleProposition)
     */
    public RuleProposition updateRuleProposition(RuleProposition ruleProposition) {
        em.merge(ruleProposition);
        return ruleProposition;
    }

    /**
     * Searches RuleProposition. Returns null if Rule Proposition not found.
     * 
     * @see org.kuali.student.rules.BRMSCore.dao.RulePropositionDAO#lookupRuleProposition(RuleProposition ruleProposition)
     */
    public RuleProposition lookupRuleProposition(long id) {
        return em.find(RuleProposition.class, id);
    }

    /**
     * Deletes RuleProposition.
     * 
     * @see org.kuali.student.rules.BRMSCore.dao.RulePropositionDAO#deleteRuleProposition(RuleProposition ruleProposition)
     */
    public boolean deleteRuleProposition(RuleProposition ruleProposition) {
        em.remove(ruleProposition);
        return true; // until I know better what needs to happen
    }

    /**
     * @return the em
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     * @param em
     *            the em to set
     */
    public void setEm(EntityManager em) {
        this.em = em;
    }
}
