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

package org.kuali.student.enrollment.class2.courseoffering.dao;

import org.kuali.student.enrollment.class2.courseoffering.model.ActivityOfferingClusterEntity;
import org.kuali.student.r2.common.dao.GenericEntityDao;

import java.util.List;

/**
 * This class provides data accessor methods for ActivityOfferingCluster entities
 *
 * @author Kuali Student Team
 */
public class ActivityOfferingClusterDao
        extends GenericEntityDao<ActivityOfferingClusterEntity>
        implements ActivityOfferingClusterDaoApi {

    @Override
    public List<ActivityOfferingClusterEntity> getByFormatOffering(String formatOfferingId) {
        return em.createQuery("from ActivityOfferingClusterEntity a where a.formatOfferingId=:formatOfferingId").setParameter("formatOfferingId", formatOfferingId)
                .getResultList();
    }

    @Override
    public List<String> getActivityOfferingIdsByClusterId(String activityOfferingClusterId) {
        return em.createQuery("SELECT ids from ActivityOfferingClusterEntity a, IN(a.aoSets) aosets, IN(aosets.aoIds) ids where a.id=:activityOfferingClusterId").setParameter("activityOfferingClusterId", activityOfferingClusterId)
                .getResultList();
    }

    /**
     * Returns all ActivityOfferingClusterEntities that map back to a single course offering
     *
     * @param courseOfferingId
     * @return
     */
    @Override
    public List<ActivityOfferingClusterEntity> getByCourseOffering(String courseOfferingId) {
        return (List<ActivityOfferingClusterEntity>) em.createNamedQuery("ActivityOfferingClusterENR.getAOCsByCourseOfferingId").setParameter("coId", courseOfferingId).getResultList();
    }

    /**
     * Returns all ActivityOfferingClusterEntities that map back to a single course offering
     *
     * @param activityOfferingId
     * @return
     */
    @Override
    public ActivityOfferingClusterEntity getByActivityOffering(String activityOfferingId) {
        return ( ActivityOfferingClusterEntity) em.createNamedQuery("ActivityOfferingClusterENR.getAOCsByActivityOfferingId").setParameter("aoId", activityOfferingId).getSingleResult();
    }
}
