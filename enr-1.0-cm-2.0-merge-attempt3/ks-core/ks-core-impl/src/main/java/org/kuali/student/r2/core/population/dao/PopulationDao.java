/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by Charles on 7/16/12
 */
package org.kuali.student.r2.core.population.dao;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.core.population.model.PopulationEntity;

import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class PopulationDao extends GenericEntityDao<PopulationEntity> {
    public List<PopulationEntity> getPopulationsForPopulationRule(String popRuleId) {
        String query = "SELECT entity FROM PopulationEntity entity WHERE entity.populationRuleId = :popRuleId";
        return em.createQuery(query)
                .setParameter("popRuleId", popRuleId).getResultList();
    }

    // Not currently being used (more of an optimization)
    public void applyPopulationRuleToPopulation(String popRuleId, String popId) {
        String query = "UPDATE PopulationEntity entity SET entity.populationRuleId = :popRuleId WHERE entity.id = :popId";
        em.createQuery(query).setParameter("popRuleType", popRuleId).executeUpdate();
    }
}
