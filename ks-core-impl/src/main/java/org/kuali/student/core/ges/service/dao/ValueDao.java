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


import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

import org.kuali.student.core.constants.GesServiceConstants;
import org.kuali.student.core.ges.service.model.ValueEntity;
import org.kuali.student.r2.common.dao.GenericEntityDao;


public class ValueDao extends GenericEntityDao<ValueEntity> {

	public List<String> getIdsByType(String type) {
		Query query = em.createNamedQuery("ValueEntity.getIdsByType");
		query.setParameter("type", type);
		return query.getResultList();
	}

	public List<ValueEntity> getByParameter(String parameterKey) {
		Query query = em.createNamedQuery("ValueEntity.getByParameter");
		query.setParameter("parameterKey", parameterKey);
		return query.getResultList();
	}

    public List<ValueEntity> getValuesByParameters(List<String> parameterKeys) {
        if (!parameterKeys.isEmpty()) {
            Query query = em.createNamedQuery("ValueEntity.getValuesByParameters");
            query.setParameter("parameterKeys", parameterKeys);
            query.setParameter("stateKey", GesServiceConstants.GES_VALUE_ACTIVE_STATE_KEY);
            return query.getResultList();
        } else {
            return new ArrayList<ValueEntity>();
        }

    }

    public List<ValueEntity> getValuesByParametersWithAtpCriteria(List<String> parameterKeys, String atpId, String atpTypeKey) {
        if (!parameterKeys.isEmpty()) {
            Query query = em.createNamedQuery("ValueEntity.getValuesByParametersWithAtpCriteria");
            query.setParameter("parameterKeys", parameterKeys);
            query.setParameter("atpId", atpId);
            query.setParameter("atpTypeKey", atpTypeKey);
            query.setParameter("stateKey", GesServiceConstants.GES_VALUE_ACTIVE_STATE_KEY);
            return query.getResultList();
        } else {
            return new ArrayList<ValueEntity>();
        }

    }

}

