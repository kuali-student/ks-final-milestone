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

import org.kuali.student.rules.brms.core.dao.ComputationAssistantDAO;
import org.kuali.student.rules.brms.core.entity.ComputationAssistant;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * Implements DAO interface for operations on Computation Assistant using Spring JPA
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
@Repository
public class ComputationAssistantDAOImpl extends JpaDaoSupport implements ComputationAssistantDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * Persists ComputationAssistant.
     * 
     * @see org.kuali.student.rules.brms.core.dao.RulePropositionDAO#createComputationAssistant(ComputationAssistant
     *      computationAssistant)
     */
    public ComputationAssistant createComputationAssistant(ComputationAssistant computationAssistant) {
        em.persist(computationAssistant);
        return computationAssistant;
    }

    /**
     * Updates ComputationAssistant.
     * 
     * @see org.kuali.student.rules.brms.core.dao.ComputationAssistantDAO#updateComputationAssistant(ComputationAssistant
     *      computationAssistant)
     */
    public ComputationAssistant updateComputationAssistant(ComputationAssistant computationAssistant) {
        em.merge(computationAssistant);
        return computationAssistant;
    }

    /**
     * Searches ComputationAssistant. Returns null if Computation Assistant not found.
     * 
     * @see org.kuali.student.rules.brms.core.dao.ComputationAssistantDAO#lookupComputationAssistant(ComputationAssistant
     *      computationAssistant)
     */
    public ComputationAssistant lookupComputationAssistant(String id) {
        return em.find(ComputationAssistant.class, id);
    }

    /**
     * Deletes ComputationAssistant.
     * 
     * @see org.kuali.student.rules.brms.core.dao.ComputationAssistantDAO#deleteComputationAssistant(ComputationAssistant
     *      computationAssistant)
     */
    public boolean deleteComputationAssistant(ComputationAssistant computationAssistant) {
        em.remove(computationAssistant);
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
