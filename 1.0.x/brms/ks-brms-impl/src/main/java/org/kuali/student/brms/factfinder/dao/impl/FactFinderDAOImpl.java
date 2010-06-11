/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.brms.factfinder.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.brms.factfinder.dao.FactFinderDAO;
import org.kuali.student.brms.factfinder.entity.LUIPerson;
import org.springframework.stereotype.Repository;

/**
 * This is a description of what this class does - Kamal don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 *
 */
@Repository
public class FactFinderDAOImpl implements FactFinderDAO {

    @PersistenceContext(unitName = "FactFinder")
    private EntityManager entityManager;
    
    /**
     * @return the entityManager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.brms.factfinder.dao.FactFinderDAO#lookupCreditsByStudentId(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<LUIPerson> lookupByStudentId(String studentId) {
        Query query = entityManager.createNamedQuery("LUIPerson.findByStudentId");
        query.setParameter("studentID", studentId);
        List<LUIPerson> LUIPersonList = query.getResultList();
        return LUIPersonList;
    }
}
