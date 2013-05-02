/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by venkat on 1/22/13
 */
package org.kuali.student.enrollment.class1.lui.dao;

import org.kuali.student.enrollment.class1.lui.model.LuiSetEntity;
import org.kuali.student.r2.common.dao.GenericEntityDao;

import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class LuiSetDao  extends GenericEntityDao<LuiSetEntity> {

    public List<LuiSetEntity> getLuiSetsByLui(String luiId){
        return (List<LuiSetEntity>) em.createNamedQuery("LuiSet.getLuiSetsByLui").setParameter("lui", luiId).getResultList();
    }

    public List<String> getLuiSetIdsByType(String typeKey){
        return (List<String>) em.createNamedQuery("LuiSet.getLuiSetIdsByType").setParameter("typeKey", typeKey).getResultList();
    }

}
