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
import org.kuali.student.r2.core.population.model.PopulationRuleEntity;

import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class PopulationRuleDao extends GenericEntityDao<PopulationRuleEntity> {
    public List<String> getPopulationRuleIdsByType(String populationRuleType) {
        String query = "SELECT entity.id FROM PopulationRuleEntity entity WHERE entity.populationRuleType = :popRuleType";
        return em.createQuery(query)
                .setParameter("popRuleType", populationRuleType).getResultList();
    }

}
