/*
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.core.ges.service.dao;


import java.util.List;
import javax.persistence.Query;

import org.kuali.student.core.ges.service.model.ParameterEntity;
import org.kuali.student.r2.common.dao.GenericEntityDao;


public class ParameterDao extends GenericEntityDao<ParameterEntity> {

	public List<String> getIdsByType(String type) {
		Query query = em.createNamedQuery(ParameterEntity.GES_PARAMETER_GET_IDS_BY_TYPE);
		query.setParameter("type", type);
		return query.getResultList();
	}

    public List<String> getParameterKeysForParameterGroup(String parameterGroupKey) {
        Query query = em.createNamedQuery(ParameterEntity.GES_PARAMETER_GET_PARAM_KEYS_FOR_PARAM_GRP);
        query.setParameter("paramGrpKey", parameterGroupKey);
        return query.getResultList();
    }

}

