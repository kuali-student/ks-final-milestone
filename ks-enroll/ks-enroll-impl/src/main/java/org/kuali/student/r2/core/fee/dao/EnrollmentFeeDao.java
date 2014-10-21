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
 * Created by Charles on 4/25/12
 */
package org.kuali.student.r2.core.fee.dao;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.core.fee.model.EnrollmentFeeEntity;

import java.util.List;

/**
 * This class provides data accessor methods for EnrollmentFee entities
 *
 * @author Kuali Student Team
 */
public class EnrollmentFeeDao  extends GenericEntityDao<EnrollmentFeeEntity> {
    public List<EnrollmentFeeEntity> getFeesByRefObjectURIAndId(String refObjURI, String refObjId) {
        return em.createQuery("FROM EnrollmentFeeEntity a WHERE a.refObjectURI = :refObjectURI AND a.refObjectId = :refObjectId")
                .setParameter("refObjectURI", refObjURI).setParameter("refObjectId", refObjId).getResultList();
    }
}
