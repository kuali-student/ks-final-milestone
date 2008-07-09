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

import org.kuali.student.rules.brms.core.dao.YieldValueFunctionDAO;
import org.kuali.student.rules.common.entity.YieldValueFunction;
import org.springframework.stereotype.Repository;

/**
 * Implements DAO interface for operations on YieldValueFunction using Spring JPA
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
@Repository
public class YieldValueFunctionDAOImpl implements YieldValueFunctionDAO {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Persists YieldValueFunction.
     * 
     * @see org.kuali.student.rules.brms.core.dao.RulePropositionDAO#createYieldValueFunction(YieldValueFunction
     *      yieldValueFunction)
     */
    public YieldValueFunction createYieldValueFunction(YieldValueFunction yieldValueFunction) {
        entityManager.persist(yieldValueFunction);
        return yieldValueFunction;
    }

    /**
     * Updates YieldValueFunction.
     * 
     * @see org.kuali.student.rules.brms.core.dao.YieldValueFunctionDAO#updateYieldValueFunction(YieldValueFunction
     *      yieldValueFunction)
     */
    public YieldValueFunction updateYieldValueFunction(YieldValueFunction yieldValueFunction) {
        entityManager.merge(yieldValueFunction);
        return yieldValueFunction;
    }

    /**
     * Searches YieldValueFunction. Returns null if Computation Assistant not found.
     * 
     * @see org.kuali.student.rules.brms.core.dao.YieldValueFunctionDAO#lookupYieldValueFunction(YieldValueFunction
     *      yieldValueFunction)
     */
    public YieldValueFunction lookupYieldValueFunction(String id) {
        return entityManager.find(YieldValueFunction.class, id);
    }

    /**
     * Deletes YieldValueFunction.
     * 
     * @see org.kuali.student.rules.brms.core.dao.YieldValueFunctionDAO#deleteYieldValueFunction(YieldValueFunction
     *      yieldValueFunction)
     */
    public boolean deleteYieldValueFunction(YieldValueFunction yieldValueFunction) {
        entityManager.remove(yieldValueFunction);
        return true; // until I know better what needs to happen
    }
}
