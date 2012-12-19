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
 */
package org.kuali.student.r2.core.class1.state.dao;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.state.model.StatePropagationEntity;

import java.util.List;

/**
 * This is the DAO for <code>StatePropagationEntity</code>
 *
 * @author Kuali Student Team
 */
public class StatePropagationDao extends GenericEntityDao<StatePropagationEntity> {

    public List<String> getStatePropagationIdsByType(String statePropagationTypeKey) {
        return (List<String>) em.createQuery("select spe.id from StatePropagationEntity spe where spe.typeKey = :statePropagationTypeKey")
                .setParameter("statePropagationTypeKey", statePropagationTypeKey)
                .getResultList();
    }

    public List<StatePropagationEntity> getStatePropagationsByTargetState(String targetStateKey) {
        return (List<StatePropagationEntity>) em.createQuery("select sp from StatePropagationEntity sp where sp.targetStateChangeId in (select sc.id from StateChangeEntity sc where sc.toStateKey = :targetStateKey)")
                .setParameter("targetStateKey", targetStateKey)
                .getResultList();
    }

}
